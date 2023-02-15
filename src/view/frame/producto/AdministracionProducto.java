package view.frame.producto;

import com.djm.db.connection.Connection;
import com.djm.db.connection.DataConnection;
import com.djm.db.result.TipoOperacion;
import model.Producto;
import util.Global;
import util.SystemProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class AdministracionProducto {
    private String mensaje;
    private SystemProperties sp = SystemProperties.getInstance();
    private TipoOperacion tipoOperacion = TipoOperacion.INSERT;
    private Producto producto;
    public AdministracionProducto(){}

    public boolean guardar(Producto prod){
        producto = prod;

        boolean rtn = false;

        if(prod.getCodigo()== null || prod.getCodigo().trim().isEmpty()) {
            mensaje = sp.getValue("produtos.message.error_codigo");
        }
        else if(prod.getNombre()==null || prod.getNombre().trim().isEmpty()){
            mensaje = sp.getValue("produtos.message.error_nombre");
        }
        else{
            Integer id = prod.getID();
            tipoOperacion = id==null?TipoOperacion.INSERT:TipoOperacion.UPDATE;
            rtn = savedb();
            if(rtn) {
                if (tipoOperacion == TipoOperacion.INSERT) {
                    GlobalProduct.getInstance().modelTable.addProduct(prod);
                    GlobalProduct.getInstance().consultaProducto.getList().add(prod);
                }else
                    System.out.println("Editar Producto...");
            }else
                mensaje = sp.getValue("produtos.message.error_guardar_bd");
        }
        return rtn;
    }

    private boolean savedb(){
        boolean rtn = false;
        Connection conn = Global.getInstance().getConnection();

        String query;
        if(tipoOperacion == TipoOperacion.INSERT){
            query = "INSERT INTO PRODUCTO (Codigo,CodigoBarra,Nombre,PrecioCosto,Precio1,Precio2,Precio3,Nota,UnidadMedida,PrecioIncluyeImpuesto,Disponible,Stock,StockCritico,noRequiereStock,IDMarca, IDCategoria) VALUES " +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        }
        else{
            query = "UPDATE PRODUCTO SET Codigo = ?, CodigoBarra = ?, Nombre = ?, PrecioCosto = ?, Precio1 = ?, Precio2 = ?, " +
                    "Precio3 =? , Nota =? , UnidadMedida =? , PrecioIncluyeImpuesto =? , Disponible =? , Stock =? , StockCritico =? , " +
                    "noRequiereStock = ?, IDMarca =? ,  IDCategoria = ? WHERE ID=?  ";
        }
        try {
            PreparedStatement pstmt = conn.getPreparedStatementID(query);

            pstmt.setString(1,producto.getCodigo());

            if(producto.getCodigoBarra()!=null)
                pstmt.setString(2, producto.getCodigoBarra());
            else
                pstmt.setNull(2, Types.VARCHAR);

            pstmt.setString(3, producto.getNombre());
            pstmt.setDouble(4,producto.getPrecioCosto());
            pstmt.setDouble(5,producto.getPrecio1());
            pstmt.setDouble(6,producto.getPrecio2());
            pstmt.setDouble(7,producto.getPrecio3());

            if(producto.getNota()!=null)
                pstmt.setString(8, producto.getNota());
            else
                pstmt.setNull(8, Types.VARCHAR);

            if(producto.getUnidadMedida()!=null)
                pstmt.setString(9, producto.getUnidadMedida());
            else
                pstmt.setNull(9, Types.VARCHAR);

            pstmt.setBoolean(10,producto.isPrecioIncluyeImpuesto());
            pstmt.setBoolean(11,producto.isDisponible());
            pstmt.setInt(12,producto.getStock());
            pstmt.setInt(13,producto.getStockCritico());
            pstmt.setBoolean(14,producto.isNoRequiereStock());

            if(producto.getMarca()!=null && producto.getMarca().getID()!=null)
                pstmt.setInt(15,producto.getMarca().getID());
            else
                pstmt.setNull(15,Types.INTEGER);

            if(producto.getCategoria()!=null && producto.getCategoria().getID()!=null)
                pstmt.setInt(16,producto.getCategoria().getID());
            else
                pstmt.setNull(16,Types.INTEGER);

            if(tipoOperacion == TipoOperacion.UPDATE) {
                pstmt.setInt(17, producto.getID());
            }

            //String statementText = pstmt.toString();
            //System.out.println(statementText.substring( statementText.indexOf( ": " ) + 2 ));

            int val = pstmt.executeUpdate();
            rtn = val >0;

            if(rtn) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    producto.setID(rs.getInt(1));
                }
            }

            conn.cerrarConexion();
        }catch (SQLException exc) {
            mensaje = "Error en BD "+exc;
            System.out.println(mensaje);
        }

        return rtn;
    }

    public String getMensaje(){
        return mensaje;
    }
}
