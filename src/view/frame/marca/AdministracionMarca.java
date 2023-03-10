package view.frame.marca;

import com.djm.db.connection.Connection;
import com.djm.db.result.TipoOperacion;
import model.Marca;
import util.Global;
import util.SystemProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministracionMarca {
    private String mensaje;
    private SystemProperties sp = SystemProperties.getInstance();
    private TipoOperacion tipoOperacion = TipoOperacion.INSERT;
    private Marca marca;

    public boolean guardar(Marca marca){
        boolean rtn = false;
        if(marca!=null) {
            this.marca = marca;
            ConsultaMarca consMarca = new ConsultaMarca();

            if (marca.getDesrcripcion() == null || marca.getDesrcripcion().trim().isEmpty()) {
                mensaje = sp.getValue("marca.message.error_descripcion");
            }
            else if (consMarca.existeDescripcion(marca.getDesrcripcion(),marca.getID())) {
                mensaje = sp.getValue("marca.message.marca_is_registrada");
            }
            else {
                rtn = savedb();
                if (rtn) ;
                else
                    mensaje = sp.getValue("marca.message.error_guardar_bd");
            }
        }
        else{
            mensaje = sp.getValue("marca.message.marca_null");
        }
        return rtn;
    }

    private boolean savedb(){
        boolean rtn = false;
        Connection conn = Global.getInstance().getConnection();
        tipoOperacion = marca.getID()==null?TipoOperacion.INSERT:TipoOperacion.UPDATE;
        boolean newMarca = tipoOperacion == TipoOperacion.INSERT;
        String query;
        if(newMarca){
            query = "INSERT INTO Marca (Descripcion) VALUES (?)";
        }
        else{
            query = "UPDATE MARCA SET DESCRIPCION = ? WHERE ID=?  ";
        }

        try {
            PreparedStatement pstmt = conn.getPreparedStatementID(query);

            pstmt.setString(1,marca.getDesrcripcion());

            if(!newMarca) {
                pstmt.setInt(2, marca.getID());
            }

            //String statementText = pstmt.toString();
            //System.out.println(statementText.substring( statementText.indexOf( ": " ) + 2 ));

            int val = pstmt.executeUpdate();
            rtn = val >0;

            if(rtn) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next() && newMarca) {
                    marca.setID(rs.getInt(1));
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
            //Actualizamos los productos con esa marca
            String query = "update producto set IDMarca = null where IDMarca = ?";

            PreparedStatement pstmt = conn.getPreparedStatement(query);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            //Eliminamos los productos de la marca
            query = "delete from Marca where id = ?";
            pstmt = conn.getPreparedStatement(query);

            pstmt.setInt(1, id);

            int val = pstmt.executeUpdate();
            rtn = val >0;

            if(rtn) {
                mensaje = sp.getValue("marca.message.delete_exito");
            }
            else{
                mensaje = sp.getValue("marca.message.delete_error");
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

    public Marca getMarca() {
        return marca;
    }
}
