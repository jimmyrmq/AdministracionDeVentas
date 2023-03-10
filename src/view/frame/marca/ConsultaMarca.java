package view.frame.marca;

import com.djm.db.connection.Connection;
import model.Marca;
import util.Global;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaMarca {
    private List<Marca> list = null;
    public synchronized void loadDBMarca(){
        if(list!=null)
            list.clear();
        else
            list = new ArrayList<>();

        String query = "select ID,Descripcion from Marca;";
        Connection conn = Global.getInstance().getConnection();

        try {
            PreparedStatement pstmt = conn.getPreparedStatementID(query);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Marca marca = new Marca();
                marca.setID(rs.getInt(1));
                marca.setDesrcripcion(rs.getString(2));

                list.add(marca);
            }
        } catch (SQLException e) {
            String desc = "Error en loadDBMarca ["+e.getMessage()+"]";
            System.out.println(desc);
        }

        conn.cerrarConexion();
    }

    public boolean existeDescripcion(String desc, Integer id){
        boolean rtn = false;

        String query = "select ID from Marca where Descripcion = ?";
        if(id !=null){
            query +=" and ID != ?";
        }
        Connection conn = Global.getInstance().getConnection();

        try {
            PreparedStatement pstmt = conn.getPreparedStatement(query);
            pstmt.setString(1,desc);
            if(id !=null)
                pstmt.setInt(2,id);

            ResultSet rs = pstmt.executeQuery();
            rtn = rs.next();
        } catch (SQLException e) {
            String msg = "Error en existeDescripcion ["+e.getMessage()+"]";
            System.out.println(msg);
        }

        conn.cerrarConexion();
        return rtn;
    }

    public Marca getMarca(int id){
        Marca marca = null;

        //System.out.println(id+" "+(list !=null));
        if(list !=null) {
            cont:for (Marca m : list) {
                if(m.getID() == id){
                    marca = m;
                    break cont;
                }
            }
        }
        //System.out.println(id+" "+marca);

        return marca;
    }

    public List<Marca> getList() {
        return list;
    }

    public boolean isListNull(){
        return list == null;
    }
}
