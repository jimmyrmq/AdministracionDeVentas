package view.frame.marca;

import model.Marca;
import view.frame.main.FrameMain;
import view.frame.ui.component.OptionPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerMarca implements ActionListener {
    private PanelMarca panelMarca;
    private JDialog dialog;
    public ActionListenerMarca(JDialog dialog){
        this.dialog = dialog;
    }

    public void setPanelMarca(PanelMarca panelMarca) {
        this.panelMarca = panelMarca;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Marca marca = panelMarca.getMarca();
        AdministracionMarca admin = new AdministracionMarca();
        boolean rtn = admin.guardar(marca);
        if(rtn && dialog !=null){
            dialog.setVisible(false);
            dialog.dispose();
        }else
            OptionPane.error(FrameMain.frame,admin.getMensaje());
    }
}
