package view.frame.categoria;

import com.djm.ui.component.TextField;
import com.djm.util.LayoutPanel;
import model.Categoria;
import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.ui.component.Button;
import view.frame.ui.component.OptionPane;
import view.frame.ui.component.SelectedColor;
import view.frame.ui.component.SelectedColorGroup;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowCategoria implements ActionListener, WindowListener {
    private Categoria categoria;
    private JDialog dialog;
    //private TextField tNombre;
    private TextField tDescripcion;
    private final SystemProperties sp = SystemProperties.getInstance();
    private Button bAceptar,bCancelar,bBuscar,bNuevo;
    private boolean acept =  true;
    private boolean edit = false;
    private SelectedColorGroup scg;
    private SelectedColor [] selectedColors;
    private Color col[] = {new Color(213, 24, 24),
            new Color(234, 57, 23),
            new Color(80, 164, 49),
            new Color(255, 213, 0),
            new Color(229, 113, 44),
            new Color(104, 61, 187),
            new Color(49, 119, 175),
            new Color(150, 65, 145)};
    public  WindowCategoria(){
        dialog = new JDialog(FrameMain.frame,sp.getValue("categoria.label.title"),true);
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


        KeyStroke SR = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        };

        InputMap inputMap = bCancelar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(SR, "CERRAR_DIALOG");
        ActionMap actionMap = bCancelar.getActionMap();
        actionMap.put("CERRAR_DIALOG", action);


        Container container = dialog.getContentPane();
        container.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        container.setLayout(new GridBagLayout());

        container.add(createGUI(), LayoutPanel.constantePane(0, 0, 3, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 20, 10, 0, 0, 0.0f, 1.0f));
        container.add(bNuevo, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 5, 0, 1.0f, 0.0f));
        container.add(bCancelar, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 10, 0, 5, 0, 0.0f, 0.0f));
        container.add(bAceptar, LayoutPanel.constantePane(2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 5, 5, 0.0f, 0.0f));

        Dimension dim = new Dimension(340,170);
        //dialog.setUndecorated(true);
        dialog.setPreferredSize(dim);
        dialog.setSize(dim);
        dialog.setMinimumSize(dim);
        dialog.setResizable(true);
        dialog.setDefaultCloseOperation(0);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private JPanel createGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        JPanel pColor = new JPanel(new FlowLayout());
        pColor.setOpaque(false);

        scg = new SelectedColorGroup();

        selectedColors = new SelectedColor[col.length];
        for (int i = 0; i < col.length; i++){
            selectedColors[i] = new SelectedColor(col[i]);
            scg.add(selectedColors[i]);
            pColor.add(selectedColors[i]);
        }

        //tNombre = new TextField(15,15);
        tDescripcion = new TextField(15,17);

        bBuscar = new Button(new ImageIcon("icon/search.png"));
        bBuscar.setActionCommand("BUSCAR");
        bBuscar.addActionListener(this);

        //JLabel lNombre = new JLabel(sp.getValue("label.nombre")+":");
        JLabel lDescripcion = new JLabel(sp.getValue("label.descripcion")+":");
        JLabel lColor = new JLabel(sp.getValue("label.color")+":");

        //panel.add(lNombre, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        //panel.add(tNombre, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bBuscar, LayoutPanel.constantePane(2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 1.0f, 0.0f));
        panel.add(lDescripcion, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tDescripcion, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lColor, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(pColor, LayoutPanel.constantePane(1, 2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if(action.equals("ACEPT")){
            AdministracionCategoria admin = new AdministracionCategoria();
            Categoria cat = getValueCategoria();
            boolean rtn = admin.guardar(cat);
            if(rtn && dialog !=null){
                acept = true;
                //GlobalProduct.getInstance().updateCategoria(cat);
                dialog.setVisible(false);
                dialog.dispose();
            }else
                OptionPane.error(FrameMain.frame,admin.getMensaje());
        }
        else if(action.equals("CANCEL")){
            close();
        }
        else if(action.equals("BUSCAR")){
            PanelTableCategoria plm = new PanelTableCategoria();
            if(plm.isAcept()){
                edit = true;
                dialog.setTitle(sp.getValue("categoria.label.title")+" - "+sp.getValue("label.editando")+"...");
                setCategoria(plm.getCategoria());
            }
        }
        else if(action.equals("NUEVO")){
            dialog.setTitle(sp.getValue("categoria.label.title"));
            bNuevo.setEnabled(false);
            categoria = null;
            tDescripcion.setText(null);
            tDescripcion.requestFocus();
        }

    }

    public void close(){
        acept = false;
        dialog.setVisible(false);
        dialog.dispose();
    }

    public boolean isAcept(){
        return acept;
    }

    public boolean isEdit() {
        return edit;
    }

    public Categoria getValueCategoria(){
        if(categoria == null)
            categoria = new Categoria();

        categoria.setDesrcripcion(tDescripcion.getText());

        if(scg.getButtonSelected()!=null)
            categoria.setColor(scg.getButtonSelected().getColor());

        /*cont:for (int i = 0; i < col.length; i++) {
            if(selectedColors[i].isSelected()) {
                categoria.setColor(selectedColors[i].getColor());
                break cont;
            }
        }*/

        return categoria;
    }
    
    public Categoria getCategoria(){
        return categoria;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
        if(this.categoria!=null) {
            edit = true;
            bNuevo.setEnabled(true);
            tDescripcion.setText(categoria.getDesrcripcion());
            cont:for(SelectedColor sc : selectedColors){
                if(categoria.getColor().equals(sc.getColor())){
                    sc.setSelected(true);
                    break cont;
                }
            }
        }
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {

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

}
