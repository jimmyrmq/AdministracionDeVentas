package view.frame.producto;

import com.djm.db.connection.Connection;
import model.Categoria;
import model.Marca;
import model.Producto;
import util.Global;
import util.SystemProperties;
import view.frame.main.LoadData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaProducto {
    private List<Producto> list = null;

    public synchronized void listarProducto(){
        if(list!=null)
            list.clear();
        else
            list = new ArrayList<>();

        String ninguno = SystemProperties.getInstance().getValue("label.ninguno");

        String query = "select ID,Codigo,CodigoBarra,Nombre,PrecioCosto," +
                "Precio1,Precio2,Precio3,Nota,UnidadMedida," +
                "PrecioIncluyeImpuesto,Disponible,Stock," +
                "StockCritico,noRequiereStock,IDMarca,IDCategoria" +
                " from Producto;";
        Connection conn = Global.getInstance().getConnection();

        try {
            PreparedStatement pstmt = conn.getPreparedStatementID(query);
            ResultSet rs = pstmt.executeQuery();
            int idcat, idmarca;

            while(rs.next()){
                Producto prod = new Producto();
                prod.setID(rs.getInt(1));
                prod.setCodigo(rs.getString(2));
                prod.setCodigoBarra(rs.getString(3));
                prod.setNombre(rs.getString(4));
                prod.setPrecioCosto(rs.getDouble(5));
                prod.setPrecio1(rs.getDouble(6));
                prod.setPrecio2(rs.getDouble(7));
                prod.setPrecio3(rs.getDouble(8));
                prod.setNota(rs.getString(9));
                prod.setUnidadMedida(rs.getString(10));
                prod.setPrecioIncluyeImpuesto(rs.getBoolean(11));
                prod.setDisponible(rs.getBoolean(12));
                prod.setStock(rs.getInt(13));
                prod.setStockCritico(rs.getInt(14));
                prod.setNoRequiereStock(rs.getBoolean(15));
                idmarca = rs.getInt(16);
                idcat = rs.getInt(17);

                Categoria cat = LoadData.getInstance().getConsultaCategoria().getCategoria(idcat);

                if(cat==null) {
                    cat = new Categoria();
                    cat.setDesrcripcion(ninguno);
                }
                prod.setCategoria(cat);

                Marca marca = LoadData.getInstance().getConsultaMarca().getMarca(idmarca);
                if(marca == null) {
                    marca = new Marca();
                    marca.setDesrcripcion(ninguno);
                }
                prod.setMarca(marca);

                list.add(prod);
            }
        } catch (SQLException e) {
            String desc = "Error en listarProducto ["+e.getMessage()+"]";
            System.out.println(desc);
        }

        conn.cerrarConexion();
    }

    public boolean existeCodigoProducto(String cod){
        boolean rtn = false;

        String query = "select ID from Producto where Codigo = ?;";
        Connection conn = Global.getInstance().getConnection();

        try {
            PreparedStatement pstmt = conn.getPreparedStatement(query);
            pstmt.setString(1,cod);

            ResultSet rs = pstmt.executeQuery();
            rtn = rs.next();
        } catch (SQLException e) {
            String msg = "Error en isProducto ["+e.getMessage()+"]";
            System.out.println(msg);
        }

        conn.cerrarConexion();
        return rtn;
    }
    /*public void listarProducto(){
        ResultData<Producto> resultData = new ResultData<>(Producto.class, Global.getInstance().getConnection());

        lProduct = resultData.getDataList("select * from Producto");
        for(Producto prod : lProduct) {
            if(prod.getMarca()==null) {
                Marca marca = new Marca();
                marca.setDesrcripcion("Ninguno");
                prod.setMarca(marca);
            }

            if(prod.getCategoria()==null) {
                Categoria categoria = new Categoria();
                categoria.setDesrcripcion("Ninguno");
                prod.setCategoria(categoria);
            }
        }
    }*/

    public List<Producto> getList() {
        return list;
    }

    public boolean isListNull(){
        return list == null;
    }
}
