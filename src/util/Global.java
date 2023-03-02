package util;

import com.djm.db.connection.Connection;
import view.frame.ui.glass.GlassFrame;
import view.frame.ui.glass.IPanelGlass;
import view.frame.ui.glass.PanelGlass;

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
        if(glassFrame==null)
            glassFrame = new GlassFrame();
        glassFrame.show(panel);
        /*PanelGlass panelGlass = new PanelGlass();
        panelGlass.start(title,panel);*/
    }

    public void closeGlassPane(){
        glassFrame.close();
    }

}
