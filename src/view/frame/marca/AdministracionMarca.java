package view.frame.marca;

import com.djm.db.connection.Connection;
import com.djm.db.result.TipoOperacion;
import model.Marca;
import util.Global;
import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.producto.ConsultaProducto;
import view.frame.ui.component.OptionPane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

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
            } else if (consMarca.existeDescripcionMarca(marca.getDesrcripcion())) {
                mensaje = sp.getValue("marca.message.marca_is_registrada");
            } else {
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

    public String getMensaje(){
        return mensaje;
    }

    public Marca getMarca() {
        return marca;
    }
}
