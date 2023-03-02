package util;

import com.djm.db.connection.Connection;
import view.frame.main.FrameMain;
import view.frame.ui.glass.GlassFrame;
import view.frame.ui.glass.IPanelGlass;
import view.frame.ui.glass.PanelGlass;

import javax.swing.*;
import java.awt.Cursor;

public class Global {
    private static Global global = null;
    private Connection conn = null;
    private GlassFrame glassFrame = null;

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

    public void startPanelGlass(IPanelGlass panel){
        FrameMain.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(glassFrame == null)
            glassFrame = new GlassFrame();

        glassFrame.show(panel);

    }

    public void closeGlassPane(){
        glassFrame.close();
    }

}
