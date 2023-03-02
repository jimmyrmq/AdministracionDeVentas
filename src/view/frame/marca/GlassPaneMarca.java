package view.frame.marca;

import com.djm.ui.component.TextField;
import com.djm.util.LayoutPanel;
import model.Marca;
import util.SystemProperties;
import util.table.ModeloTabla;
import view.frame.main.FrameMain;
import view.frame.main.LoadData;
import view.frame.producto.GlobalProduct;
import view.frame.ui.IPanelGlass;
import view.frame.ui.component.Button;
import view.frame.ui.component.OptionPane;
import view.frame.ui.component.Table;

import javax.swing.*;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GlassPaneMarca implements ActionListener, IPanelGlass {;
    private final SystemProperties sp = SystemProperties.getInstance();
    private Button bAceptar,bCancelar,bNuevo,bEliminar;
    private Marca marca = null;
    private TextField tDescripcion;
    private Table table;
    private boolean edit;
    private JPanel panelPrincipal;
    public GlassPaneMarca(){
        createGUI();

        tDescripcion.requestFocus();
    }

    public void createGUI(){
        panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setOpaque(false);

        //IPanelUI panelUI = GlobalUI.getInstance().getTheme().getPanelUI();
        //panelPrincipal.setBackground(panelUI.getBackground());

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

        panelPrincipal.add(createGUIDescrip(), LayoutPanel.constantePane(0, 0, 3, 1, GridBagConstraints.VERTICAL, GridBagConstraints.PAGE_START, 10, 10, 5, 10, 1.0f, 0.0f));
        panelPrincipal.add(createGUITable(), LayoutPanel.constantePane(0, 1, 3, 1, GridBagConstraints.VERTICAL, GridBagConstraints.PAGE_START, 10, 10, 5, 10, 1.0f, 1.0f));
        panelPrincipal.add(bNuevo, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 5, 0, 1.0f, 0.0f));
        panelPrincipal.add(bCancelar, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 5, 0, 0.0f, 0.0f));
        panelPrincipal.add(bAceptar, LayoutPanel.constantePane(2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 5, 10, 0.0f, 0.0f));
    }

    public JPanel createGUIDescrip(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        tDescripcion = new TextField(25,150);
        tDescripcion.requestFocus();
        tDescripcion.setActionCommand("ACEPT");
        tDescripcion.addActionListener(this);
        JLabel lDescripcion = new JLabel(sp.getValue("label.descripcion")+":");

        panel.add(lDescripcion, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tDescripcion, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    private JPanel createGUITable(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        ModelTableMarcaCustom mpc = new ModelTableMarcaCustom();
        ModeloTabla<Marca> modelo = new ModeloTabla(mpc);

        table = new Table(modelo, 280);

        bEliminar = new Button(new ImageIcon("icon/delete.png"));//sp.getValue("button.eliminar"));//,
        Color red = new Color(253, 7, 7);
        bEliminar.setColorImage(red);
        bEliminar.setForeground(red);
        bEliminar.setActionCommand("DELETE");
        bEliminar.addActionListener(this);
        bEliminar.setButtonIcon(true);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (table.isEnabled() && e.getClickCount() == 2) {
                    getMarcaSelected();
                }
            }
        });


        panel.add(table.getPanel(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.PAGE_START, 10, 10, 5, 0, 0.0f, 1.0f));
        panel.add(bEliminar, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 3, 5, 0, 1.0f, 1.0f));

        return panel;
    }

    public boolean fillTable(){
        table.setEnabled(false);
        LoadData.getInstance().getConsultaMarca().loadDBMarca();
        List<Marca> marcaList = LoadData.getInstance().getConsultaMarca().getList();
        int sz = marcaList.size();
        for(int i = 0;i<sz;i++){
            try {
                Marca mc = marcaList.get(i);
                table.addRow(mc);
            }catch (IndexOutOfBoundsException exc){
                System.out.println("Error fillTable Marca: "+exc);
            }
        }

        boolean e = !marcaList.isEmpty();
        table.setEnabled(e);
        bEliminar.setEnabled(e);
        return e;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if(action.equals("ACEPT")){

            AdministracionMarca admin = new AdministracionMarca();
            boolean rtn = admin.guardar(getValueMarca());

            if(rtn){
                FrameMain.frame.getGlassPane().setVisible(false);

                LoadData.getInstance().getConsultaMarca().loadDBMarca();
                GlobalProduct.getInstance().addCBCarga(getMarca(),edit);//windowMarca.getMarca(),edit);

                FrameMain.notificacion.start(sp.getValue("marca.label.title"),sp.getValue("marca.message.marca_registrada_exito"));

            }else
                OptionPane.error(FrameMain.frame,admin.getMensaje());
        }
        else if(action.equals("CANCEL")){
            clear();
            FrameMain.frame.getGlassPane().setVisible(false);
        }
        else if(action.equals("NUEVO")){
            bNuevo.setEnabled(false);
            tDescripcion.requestFocus();
            clear();
        }
        else if(action.equals("DELETE")){
            Marca mdel = (Marca) table.getSelectedItem();
            if (mdel != null) {
                OptionPane.warning(FrameMain.frame, sp.getValue("marca.message.warning_delete"));
                int yes = OptionPane.questionYesOrKey(FrameMain.frame, sp.getValue("marca.message.delete"));
                if (yes == OptionPane.OK) {
                    int index = table.getSelectedIndex();
                    if (index != -1) {
                        AdministracionMarca adminMarca = new AdministracionMarca();
                        boolean rtn = adminMarca.eliminar(mdel.getID());
                        if(rtn){
                            table.removeRow(index);
                            GlobalProduct.getInstance().deleteMarca(mdel);
                        }
                    }
                }
            }
            else{
                OptionPane.information(FrameMain.frame, sp.getValue("marca.message.info_delete"));
            }
        }
    }

    public void getMarcaSelected() {
        Marca marc = (Marca) table.getSelectedItem();
        if(marc==null) {
            OptionPane.error(FrameMain.frame, sp.getValue("marca.message.selected"));
        }
        else{
            setMarca(marc);
        }
    }

    public Marca getValueMarca(){
        if(marca == null)
            marca = new Marca();

        marca.setDesrcripcion(tDescripcion.getText());

        return marca;
    }

    public void setMarca(Marca marca){
        this.marca = marca;
        if(this.marca!=null) {
            edit = true;
            tDescripcion.setText(marca.getDesrcripcion());
            bNuevo.setEnabled(true);
            tDescripcion.requestFocus();
        }
    }

    public Marca getMarca(){
        return marca;
    }

    public void clear(){
        marca = null;
        tDescripcion.setText(null);
        if(table!=null)
            table.clearSelection();

        Thread t = new Thread(()-> {
            boolean repeat = true;
            do {
                if(FrameMain.frame.getGlassPane().isVisible()) {
                    tDescripcion.requestFocus();
                    repeat = false;
                }
            }while (repeat);
        });
        t.start();
    }

    public void init(){
        clear();

        fillTable();
    }

    public JPanel getPanel(){
        return panelPrincipal;
    }
}
