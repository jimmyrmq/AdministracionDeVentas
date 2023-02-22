package view.frame.marca;

import com.djm.util.LayoutPanel;
import model.Marca;
import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.ui.component.Button;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class PanelListMarca implements ActionListener,WindowListener {
    private JDialog dialog;
    private Marca marca;
    private boolean acept = false;
    private Button bAceptar;
    private Button bCancelar;
    private JTable table;
    private final SystemProperties sp = SystemProperties.getInstance();

    public PanelListMarca(){
        dialog = new JDialog(FrameMain.frame,"Buscar Marca",true);
        dialog.addWindowListener(this);

        bAceptar = new Button(sp.getValue("button.aceptar"));
        bCancelar = new Button(sp.getValue("button.cancelar"));

        bAceptar.addActionListener(this);
        bCancelar.addActionListener(this);

        Container container = dialog.getContentPane();
        container.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        container.setLayout(new GridBagLayout());

        container.add(bCancelar, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 10, 0, 5, 0, 1.0f, 0.0f));
        container.add(bAceptar, LayoutPanel.constantePane(1, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 5, 5, 0.0f, 0.0f));

        Dimension dim = new Dimension(340,410);
        //dialog.setUndecorated(true);
        dialog.setPreferredSize(dim);
        dialog.setSize(dim);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(0);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public Marca getMarca() {
        return marca;
    }

    public boolean isAcept() {
        return acept;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        acept = false;
        marca = null;
        dialog.setVisible(false);
        dialog.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if(action.equalsIgnoreCase("Aceptar")){
            acept = true;
            dialog.setVisible(false);
            dialog.dispose();
        }
        else if(action.equalsIgnoreCase("Cancelar")){
            acept = false;
            dialog.setVisible(false);
            dialog.dispose();
        }
    }
}
