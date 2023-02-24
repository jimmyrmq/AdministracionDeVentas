package view.frame.categoria;

import com.djm.db.connection.Connection;
import com.djm.db.result.TipoOperacion;
import model.Categoria;
import util.Global;
import util.SystemProperties;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministracionCategoria {
    private String mensaje;
    private SystemProperties sp = SystemProperties.getInstance();
    private TipoOperacion tipoOperacion = TipoOperacion.INSERT;
    private Categoria categoria;

    public boolean guardar(Categoria categoria){
        boolean rtn = false;
        if(categoria!=null) {
            this.categoria = categoria;
            ConsultaCategoria consCat = new ConsultaCategoria();

            if ( categoria.getDesrcripcion() == null || categoria.getDesrcripcion().trim().isEmpty()){
                mensaje = sp.getValue("categoria.message.error_descripcion");
            }if (categoria.getColor() == null) {
                mensaje = sp.getValue("categoria.message.error_color");
            } else if (consCat.existeDescripcion(categoria.getDesrcripcion(),categoria.getID())) {
                mensaje = sp.getValue("categoria.message.marca_is_registrada");
            } else {
                rtn = savedb();
                if (rtn) ;
                else
                    mensaje = sp.getValue("categoria.message.error_guardar_bd");
            }
        }
        else{
            mensaje = sp.getValue("categoria.message.marca_null");
        }
        return rtn;
    }

    private boolean savedb(){
        boolean rtn = false;
        Connection conn = Global.getInstance().getConnection();
        tipoOperacion = categoria.getID()==null?TipoOperacion.INSERT:TipoOperacion.UPDATE;
        Color col = categoria.getColor();
        int cr = col.getRed();
        int cg = col.getGreen();
        int cb = col.getBlue();

        boolean newMarca = tipoOperacion == TipoOperacion.INSERT;
        String query;
        if(newMarca){
            query = "insert into Categoria (Descripcion,ColorR,ColorG,ColorB) values (?,?,?,?)";
        }
        else{
            query = "update categoria set descripcion = ?, ColorR = ?, ColorG = ?, ColorB = ? where ID=?  ";
        }

        try {
            PreparedStatement pstmt = conn.getPreparedStatementID(query);

            pstmt.setString(1,categoria.getDesrcripcion());
            pstmt.setInt(2,cr);
            pstmt.setInt(3,cg);
            pstmt.setInt(4,cb);

            if(!newMarca) {
                pstmt.setInt(5, categoria.getID());
            }

            //String statementText = pstmt.toString();
            //System.out.println(statementText.substring( statementText.indexOf( ": " ) + 2 ));

            int val = pstmt.executeUpdate();
            rtn = val >0;

            if(rtn) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next() && newMarca) {
                    categoria.setID(rs.getInt(1));
                }
            }

            conn.cerrarConexion();
        }catch (SQLException exc) {
            mensaje = "Error en BD "+exc;
            System.out.println(mensaje);
        }

        return rtn;
    }

    public boolean eliminar(Integer id){
        boolean rtn = false;
        Connection conn = Global.getInstance().getConnection();
        try{
            //Actualizamos los productos con esa categoria
            String query = "update producto set IDCategoria = null where IDCategoria = ?";

            PreparedStatement pstmt = conn.getPreparedStatement(query);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            //Eliminamos los productos de la categoria
            query = "delete from categoria where id = ?";
            pstmt = conn.getPreparedStatement(query);

            pstmt.setInt(1, id);

            int val = pstmt.executeUpdate();
            rtn = val >0;

            if(rtn) {
                mensaje = sp.getValue("categoria.message.delete_exito");
            }
            else{
                mensaje = sp.getValue("categoria.message.delete_error");
            }
        }
        catch (SQLException exc) {
            rtn = false;
            mensaje = "Error en BD "+exc;
            System.out.println(mensaje);
        }
        finally {
            conn.cerrarConexion();
        }
        return rtn;
    }

    public String getMensaje(){
        return mensaje;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
