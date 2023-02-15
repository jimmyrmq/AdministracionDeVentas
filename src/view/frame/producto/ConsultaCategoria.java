package view.frame.producto;

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
    private List<Categoria> list;
    public List<Categoria> getListCategoria(){
        list = new ArrayList<>();

        String query = "select * from Categoria;";
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
            String desc = "Error en getListCategoria ["+e.getMessage()+"]";
            System.out.println(desc);
        }

        conn.cerrarConexion();

        return list;
    }

    public List<Categoria> getList() {
        return list;
    }
}
