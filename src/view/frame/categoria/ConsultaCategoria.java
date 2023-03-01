package view.frame.categoria;

import com.djm.db.connection.Connection;
import model.Categoria;
import util.Global;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaCategoria {
    private List<Categoria> list = null;

    public void loadDBCategoria(){
        if(list!=null)
            list.clear();
        else
            list = new ArrayList<>();

        String query = "select ID,Descripcion,ColorR,ColorG,ColorB from Categoria;";
        Connection conn = Global.getInstance().getConnection();

        try {
            PreparedStatement pstmt = conn.getPreparedStatementID(query);
            ResultSet rs = pstmt.executeQuery();
            int id,r,g,b;
            String desc;

            while(rs.next()){
                id = rs.getInt(1);
                desc = rs.getString(2);
                r = rs.getInt(3);
                g = rs.getInt(4);
                b = rs.getInt(5);

                Color col = new Color(r,g,b);
                Categoria cat = new Categoria();
                cat.setDesrcripcion(desc);
                cat.setID(id);
                cat.setColor(col);

                list.add(cat);
            }
        } catch (SQLException e) {
            String desc = "Error en loadDBCategoria ["+e.getMessage()+"]";
            System.out.println(desc);
        }catch (NullPointerException e) {
            String desc = "Error en loadDBCategoria ["+e.getMessage()+"]";
            System.out.println(desc);
        }

        conn.cerrarConexion();
    }

    public boolean existeDescripcion(String desc,Integer id){
        boolean rtn = false;

        String query = "select ID from Categoria where Descripcion = ?";

        if(id != null)
            query += " and ID != ?";

        Connection conn = Global.getInstance().getConnection();

        try {
            PreparedStatement pstmt = conn.getPreparedStatement(query);
            pstmt.setString(1,desc);

            if(id != null)
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

    public Categoria getCategoria(int id){
        Categoria cat = null;
        if(list !=null) {
            cont:for (Categoria c : list) {
                if(c.getID() == id){
                    cat = c;
                    break cont;
                }
            }
        }

        return cat;
    }

    public List<Categoria> getList() {
        return list;
    }

    public boolean isListNull(){
        return list == null;
    }
}
