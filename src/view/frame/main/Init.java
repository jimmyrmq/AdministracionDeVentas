package view.frame.main;

import com.djm.db.connection.Connection;
import com.djm.db.connection.DataConnection;
import com.djm.util.LayoutPanel;
import util.Global;
import util.SystemProperties;
import view.frame.dashboard.ActionButton;
import view.frame.ui.component.ComboBox;
import view.frame.ui.themes.GlobalUI;
import view.frame.ui.themes.LookAndFeel;
import view.frame.dashboard.Dashboard;
import view.frame.ui.themes.dark.Dark;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

public class Init {
    private Connection conn;
    public Init (){

        //LoadExtension lext = new LoadExtension();
        //GlobalUI.getInstance().setTheme(new Dark());
        new LookAndFeel();//new Dark());//
        SystemProperties.getInstance().setLanguaje("es.properties");
        //LookAndFeel lf = new LookAndFeel();
        //lf.setTheme("Windows");//GlobalUI.getInstance().getTheme());//

        FrameMain fm = new FrameMain();

        PanelTarea panelTarea = new PanelTarea();
        //panelTarea.setBackground(Color.RED);

        //Actiones del menu
        ActionButton ab = new ActionButton(panelTarea);
        Dashboard ds = new Dashboard(ab);

        JSeparator sep = new JSeparator();
        sep.setOrientation(SwingConstants.HORIZONTAL);

        sep.setForeground(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder());
        sep.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());

        /*ComboBox combobox1 = new ComboBox();
        combobox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item1", "Item2", "Item3", "Item4", "Item5", "Item6", "Item7", "Item8", "Item9","Item1", "Item2", "Item3", "Item4", "Item5", "Item6", "Item7", "Item8", "Item9" }));
        combobox1.setSelectedIndex(-1);

        fm.getContainer().add(combobox1,LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        */fm.getContainer().add(ds.getPanel(), LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 1.0f, 0.0f));
        fm.getContainer().add(sep, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 10, 0, 10, 1.0f, 0.0f));
        fm.getContainer().add(panelTarea.getPanel(), LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));

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
