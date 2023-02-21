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

public class DialogMarca implements ActionListener, WindowListener {
    private JDialog dialog;
    private PanelMarca panelMarca;
    private Button bAceptar,bCancelar;
    private boolean acept =  true;
    private final SystemProperties sp = SystemProperties.getInstance();

    public DialogMarca(){
        dialog = new JDialog(FrameMain.frame,"Marca",true);
        dialog.addWindowListener(this);

        ActionListenerMarca actionListenerMarca = new ActionListenerMarca(dialog);

        panelMarca = new PanelMarca();
        panelMarca.setActionListenerMarca(actionListenerMarca);
        actionListenerMarca.setPanelMarca(panelMarca);

        bAceptar = new Button(sp.getValue("button.aceptar"));
        bCancelar = new Button(sp.getValue("button.cancelar"));

        bAceptar.addActionListener(actionListenerMarca);
        bCancelar.addActionListener(this);

        Container container = dialog.getContentPane();
        container.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        container.setLayout(new GridBagLayout());

        container.add(panelMarca, LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 20, 10, 0, 0, 1.0f, 1.0f));
        container.add(bCancelar, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 10, 0, 5, 0, 1.0f, 0.0f));
        container.add(bAceptar, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 5, 5, 0.0f, 0.0f));

        Dimension dim = new Dimension(340,130);
        //dialog.setUndecorated(true);
        dialog.setPreferredSize(dim);
        dialog.setSize(dim);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(0);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        dialog.setVisible(false);
        dialog.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //System.out.println(dialog.getSize());
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

        if(action.equalsIgnoreCase("Cancelar")){
            acept = false;
            dialog.setVisible(false);
            dialog.dispose();
        }
    }

    public boolean isAcept(){
        return acept;
    }
}