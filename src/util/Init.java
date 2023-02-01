package util;

import com.djm.db.connection.Connection;
import com.djm.db.connection.DataConnection;
import com.djm.util.LayoutPanel;
import view.frame.dashboard.ActionButton;
import view.frame.themes.Blue;
import view.frame.themes.LookAndFeel;
import view.frame.dashboard.Dashboard;
import view.frame.main.FrameMain;
import view.frame.main.PanelTarea;
import view.frame.themes.Dark;

import java.awt.GridBagConstraints;

public class Init {
    private Connection conn;
    public Init (){

        //LoadExtension lext = new LoadExtension();
        SystemProperties.getInstance().setLanguaje("es.properties");
        LookAndFeel lf = new LookAndFeel();
        lf.setTheme(new Dark());//"Windows");//
        FrameMain fm = new FrameMain();

        PanelTarea panelTarea = new PanelTarea();
        //panelTarea.setBackground(Color.RED);

        //Actiones del menu
        ActionButton ab = new ActionButton(panelTarea);
        Dashboard ds = new Dashboard(ab);

        fm.getContainer().add(ds.getPanel(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 0.0f, 1.0f));
        fm.getContainer().add(panelTarea.getPanel(), LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));

        //Inicializamos la conexion con la base de datos.
        initConnBD();
        fm.getFrame().setVisible(true);
    }

    private void initConnBD() {
        DataConnection dconn = new DataConnection();
        dconn.setDBName("ventas");
        this.conn = new  Connection(dconn);
        //conn.testConnection();
    }

    public Connection getConnectionBD(){
        return conn;
    }
}
