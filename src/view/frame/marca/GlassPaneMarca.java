package view.frame.marca;

import com.djm.util.LayoutPanel;
import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.ui.component.Button;
import view.frame.ui.component.OptionPane;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GlassPaneMarca extends JPanel implements ActionListener {

    private Button bAceptar,bCancelar,bNuevo;
    private final SystemProperties sp = SystemProperties.getInstance();
    private PanelMarca panelMarca;
    private PanelListMarca panelListMarca;

    public GlassPaneMarca(){
        super(new GridBagLayout());
        createGUI();
    }

    private void createGUI(){
        panelMarca = new PanelMarca();
        panelListMarca = new PanelListMarca();
        Thread t = new Thread(()->{
            panelListMarca.fillTable();
        });
        t.start();

        bAceptar = new Button(sp.getValue("button.aceptar"));
        bAceptar.setActionCommand("ACEPT");
        bCancelar = new Button(sp.getValue("button.cancelar"));
        bCancelar.setActionCommand("CANCEL");

        bNuevo = new Button("Nuevo",new ImageIcon("icon/new.png"));
        bNuevo.setActionCommand("NUEVO");
        bNuevo.setEnabled(false);

        bAceptar.addActionListener(this);
        bCancelar.addActionListener(this);
        bNuevo.addActionListener(this);

        add(panelMarca.createGUI(), LayoutPanel.constantePane(0, 0, 3, 1, GridBagConstraints.VERTICAL, GridBagConstraints.PAGE_START, 10, 10, 5, 10, 1.0f, 0.0f));
        add(panelListMarca, LayoutPanel.constantePane(0, 1, 3, 1, GridBagConstraints.VERTICAL, GridBagConstraints.PAGE_START, 10, 10, 5, 10, 1.0f, 1.0f));
        add(bNuevo, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 5, 0, 1.0f, 0.0f));
        add(bCancelar, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 5, 0, 0.0f, 0.0f));
        add(bAceptar, LayoutPanel.constantePane(2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 5, 10, 0.0f, 0.0f));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if(action.equals("ACEPT")){
            AdministracionMarca admin = new AdministracionMarca();
            boolean rtn = admin.guardar(panelMarca.getValueMarca());
            if(rtn){
                FrameMain.frame.getGlassPane().setVisible(false);
            }else
                OptionPane.error(FrameMain.frame,admin.getMensaje());
        }
        else if(action.equals("CANCEL")){
            panelMarca.clear();
            FrameMain.frame.getGlassPane().setVisible(false);
        }
        else if(action.equals("NUEVO")){
            bNuevo.setEnabled(false);
            panelMarca.clear();
        }

    }
}
