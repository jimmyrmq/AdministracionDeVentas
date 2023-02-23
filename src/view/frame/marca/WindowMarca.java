package view.frame.marca;

import com.djm.ui.component.TextField;
import com.djm.util.LayoutPanel;
import model.Marca;
import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.ui.component.Button;
import view.frame.ui.component.OptionPane;
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

public class WindowMarca implements ActionListener, WindowListener {
    private Marca marca = null;
    private JDialog dialog;
    private TextField tDescripcion;
    private final SystemProperties sp = SystemProperties.getInstance();
    private Button bAceptar,bCancelar,bBuscar,bNuevo;
    private boolean acept =  true;
    private boolean edit = false;

    public WindowMarca(){
        dialog = new JDialog(FrameMain.frame,sp.getValue("marca.label.title"),true);
        dialog.addWindowListener(this);

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

        Container container = dialog.getContentPane();
        container.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        container.setLayout(new GridBagLayout());

        container.add(createGUI(), LayoutPanel.constantePane(0, 0, 3, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 20, 10, 0, 0, 0.0f, 1.0f));
        container.add(bNuevo, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 5, 0, 1.0f, 0.0f));
        container.add(bCancelar, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 10, 0, 5, 0, 0.0f, 0.0f));
        container.add(bAceptar, LayoutPanel.constantePane(2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 5, 5, 0.0f, 0.0f));

        Dimension dim = new Dimension(340,130);
        //dialog.setUndecorated(true);
        dialog.setPreferredSize(dim);
        dialog.setSize(dim);
        dialog.setResizable(true);
        dialog.setDefaultCloseOperation(0);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private JPanel createGUI(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        tDescripcion = new TextField(15,150);

        bBuscar = new Button(new ImageIcon("icon/search.png"));
        bBuscar.setActionCommand("BUSCAR");
        bBuscar.addActionListener(this);

        JLabel lDescripcion = new JLabel(sp.getValue("label.descripcion")+":");

        panel.add(lDescripcion, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tDescripcion, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bBuscar, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 1.0f, 0.0f));

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if(action.equals("ACEPT")){
            AdministracionMarca admin = new AdministracionMarca();
            boolean rtn = admin.guardar(getValueMarca());
            if(rtn && dialog !=null){
                acept = true;
                dialog.setVisible(false);
                dialog.dispose();
            }else
                OptionPane.error(FrameMain.frame,admin.getMensaje());
        }
        else if(action.equals("CANCEL")){
            acept = false;
            dialog.setVisible(false);
            dialog.dispose();
        }
        else if(action.equals("BUSCAR")){
            PanelListMarca plm = new PanelListMarca();
            if(plm.isAcept()){
                edit = true;
                dialog.setTitle(sp.getValue("marca.label.title")+" - "+sp.getValue("marca.message.editar"));
                setMarca(plm.getMarca());
            }
        }
        else if(action.equals("NUEVO")){
            dialog.setTitle(sp.getValue("marca.label.title"));
            bNuevo.setEnabled(false);
            marca = null;
            tDescripcion.setText(null);
            tDescripcion.requestFocus();
        }
    }
    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        acept = false;
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

    public boolean isAcept(){
        return acept;
    }


    public boolean isEdit() {
        return edit;
    }

    public Marca getValueMarca(){
        if(marca == null)
            marca = new Marca();

        marca.setDesrcripcion(tDescripcion.getText());

        return marca;
    }
    public Marca getMarca(){
        return marca;
    }

    public void setMarca(Marca marca){
        this.marca = marca;
        if(this.marca!=null) {
            edit = true;
            bNuevo.setEnabled(true);
            tDescripcion.setText(marca.getDesrcripcion());
        }
    }
}