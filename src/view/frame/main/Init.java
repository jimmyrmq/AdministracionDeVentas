package view.frame.main;

import com.djm.db.connection.Connection;
import com.djm.db.connection.DataConnection;
import com.djm.util.LayoutPanel;
import util.SystemProperties;
import view.frame.dashboard.ActionButton;
import view.frame.ui.component.CheckBox;
import view.frame.ui.themes.Dark;
import view.frame.ui.themes.GlobalUI;
import view.frame.ui.themes.LookAndFeel;
import view.frame.dashboard.Dashboard;

import javax.swing.*;
import java.awt.GridBagConstraints;

public class Init {
    private Connection conn;
    public Init (){

        //LoadExtension lext = new LoadExtension();
        GlobalUI.getInstance().setTheme(new Dark());
        SystemProperties.getInstance().setLanguaje("es.properties");
        LookAndFeel lf = new LookAndFeel();
        lf.setTheme(GlobalUI.getInstance().getTheme());//"Windows");//

        FrameMain fm = new FrameMain();

        PanelTarea panelTarea = new PanelTarea();
        //panelTarea.setBackground(Color.RED);

        //Actiones del menu
        ActionButton ab = new ActionButton(panelTarea);
        Dashboard ds = new Dashboard(ab);

        JSeparator sep = new JSeparator();
        sep.setOrientation(SwingConstants.HORIZONTAL);
        sep.setForeground(GlobalUI.getInstance().getTheme().getColorBorder());
        sep.setBackground(GlobalUI.getInstance().getTheme().getBackground());

        //fm.getContainer().add(new CheckBox("Prueba"),LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        fm.getContainer().add(ds.getPanel(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        fm.getContainer().add(sep, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 10, 0, 10, 1.0f, 0.0f));
        fm.getContainer().add(panelTarea.getPanel(), LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));

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
