package util;

import com.djm.db.connection.Connection;
import view.frame.ui.PanelGlass;

import javax.swing.*;

public class Global {
    private static Global global = null;
    private Connection conn = null;
    private PanelGlass panelGlass = new PanelGlass();
    private Global(){}

    public static Global getInstance(){

        if(global == null){
            global = new Global();
        }

        return global;
    }

    public Connection getConnection(){
        return conn;
    }

    public void setConnection(Connection conn){
        this.conn = conn;
    }

    public void startPanelGlass(String title,JPanel panel){
        panelGlass.start(title,panel);
    }
}
