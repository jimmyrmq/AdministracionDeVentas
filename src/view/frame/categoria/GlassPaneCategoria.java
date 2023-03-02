package view.frame.categoria;

import com.djm.ui.component.TextField;
import com.djm.util.LayoutPanel;
import model.Categoria;
import model.Marca;
import util.SystemProperties;
import util.table.ModeloTabla;
import view.frame.main.FrameMain;
import view.frame.main.LoadData;
import view.frame.producto.GlobalProduct;
import view.frame.ui.IPanelGlass;
import view.frame.ui.component.Button;
import view.frame.ui.component.OptionPane;
import view.frame.ui.component.SelectedColor;
import view.frame.ui.component.SelectedColorGroup;
import view.frame.ui.component.Table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GlassPaneCategoria  implements ActionListener, IPanelGlass {
    private Categoria categoria;
    private Table table;
    private JPanel panelPrincipal;
    private TextField tDescripcion;
    private final SystemProperties sp = SystemProperties.getInstance();
    private Button bAceptar,bCancelar,bEliminar,bNuevo;
    private SelectedColorGroup scg;
    private SelectedColor[] selectedColors;
    private Color col[] = {new Color(213, 24, 24),
            new Color(234, 57, 23),
            new Color(80, 164, 49),
            new Color(255, 213, 0),
            new Color(229, 113, 44),
            new Color(104, 61, 187),
            new Color(49, 119, 175),
            new Color(150, 65, 145),
            new Color(18, 75, 10),
            new Color(81, 6, 162),
            new Color(75, 73, 77),
            new Color(13, 22, 30)};
    private boolean edit = false;

    public GlassPaneCategoria(){
        createGUI();

        tDescripcion.requestFocus();
    }

    @Override
    public void createGUI() {
        panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setOpaque(false);

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

    public JPanel createGUIDescrip() {
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
        tDescripcion = new TextField(25,150);
        tDescripcion.requestFocus();
        tDescripcion.setActionCommand("ACEPT");
        tDescripcion.addActionListener(this);

        //JLabel lNombre = new JLabel(sp.getValue("label.nombre")+":");
        JLabel lDescripcion = new JLabel(sp.getValue("label.descripcion")+":");
        JLabel lColor = new JLabel(sp.getValue("label.color")+":");

        panel.add(lDescripcion, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tDescripcion, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(lColor, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        panel.add(pColor, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 10, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    public JPanel createGUITable() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        bEliminar = new Button(new ImageIcon("icon/delete.png"));//sp.getValue("button.eliminar"));//,
        Color red = new Color(253, 7, 7);
        bEliminar.setColorImage(red);
        bEliminar.setForeground(red);
        bEliminar.setActionCommand("DELETE");
        bEliminar.addActionListener(this);
        bEliminar.setButtonIcon(true);

        ModelTableCategoriaCustom mpc = new ModelTableCategoriaCustom();
        ModeloTabla<Categoria> modelo = new ModeloTabla(mpc);

        table = new Table(modelo, 280);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (table.isEnabled() && e.getClickCount() == 2) {
                    getCategoriaSelected();
                }
            }
        });

        table.setDefaultRenderer(JLabel.class, new StatusIconRenderer());


        panel.add(table.getPanel(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.PAGE_START, 10, 10, 5, 0, 0.0f, 1.0f));
        panel.add(bEliminar, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 3, 5, 0, 1.0f, 1.0f));

        return panel;
    }

    public boolean fillTable(){
        table.setEnabled(false);
        LoadData.getInstance().getConsultaCategoria().loadDBCategoria();
        List<Categoria> cateList = LoadData.getInstance().getConsultaCategoria().getList();
        int sz = cateList.size();
        for(int i = 0;i<sz;i++){
            try {
                Categoria cate = cateList.get(i);
                table.addRow(cate);
            }catch (IndexOutOfBoundsException exc){
                System.out.println("Error fillTable Categoria: "+exc);
            }
        }

        boolean e = !cateList.isEmpty();
        table.setEnabled(e);
        bEliminar.setEnabled(e);
        return e;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if(action.equals("ACEPT")){
            AdministracionCategoria admin = new AdministracionCategoria();
            Categoria cat = getValueCategoria();
            boolean rtn = admin.guardar(cat);
            if(rtn){
                FrameMain.frame.getGlassPane().setVisible(false);

                LoadData.getInstance().getConsultaCategoria().loadDBCategoria();
                GlobalProduct.getInstance().addCBCategoria(getCategoria(),edit);

                FrameMain.notificacion.start(sp.getValue("categoria.label.title"),sp.getValue("categoria.message.marca_registrada_exito"));

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
            Categoria mdel = (Categoria) table.getSelectedItem();
            if (mdel != null) {
                OptionPane.warning(FrameMain.frame, sp.getValue("categoria.message.warning_delete"));
                int yes = OptionPane.questionYesOrKey(FrameMain.frame, sp.getValue("categoria.message.delete"));
                if (yes == OptionPane.OK) {
                    int index = table.getSelectedIndex();
                    if (index != -1) {
                        AdministracionCategoria adminCategoria = new AdministracionCategoria();
                        boolean rtn = adminCategoria.eliminar(mdel.getID());
                        if(rtn){
                            table.removeRow(index);
                            GlobalProduct.getInstance().deleteCategoria(mdel);
                            //GlobalProduct.getInstance().reorganizarListCat();
                        }
                    }
                }
            }
        }
    }

    public void getCategoriaSelected() {
        Categoria cate = (Categoria) table.getSelectedItem();
        if(cate==null) {
            OptionPane.error(FrameMain.frame, sp.getValue("categoria.message.selected"));
        }
        else{
            setCategoria(cate);
        }
    }

    private Categoria getValueCategoria(){
        if(categoria == null)
            categoria = new Categoria();

        categoria.setDesrcripcion(tDescripcion.getText());

        if(scg.getButtonSelected()!=null)
            categoria.setColor(scg.getButtonSelected().getColor());

        return categoria;
    }

    private void setCategoria(Categoria categoria){
        this.categoria = categoria;
        if(this.categoria!=null) {

            tDescripcion.setText(null);

            for(SelectedColor sc : selectedColors){
                sc.setSelected(false);
            }

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

    public Categoria getCategoria(){
        return categoria;
    }

    @Override
    public JPanel getPanel() {
        return panelPrincipal;
    }

    @Override
    public void init() {
        clear();

        fillTable();
    }

    public void clear(){
        categoria = null;
        tDescripcion.setText(null);
        if(table!=null)
            table.clearSelection();

        for(SelectedColor sc : selectedColors){
            sc.setSelected(false);
        }

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

    private class StatusIconRenderer extends JLabel implements TableCellRenderer {
        private Color color;
        public StatusIconRenderer() {}
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int col) {

            //System.out.println(row+" "+col+" "+value);
            if(col == 1) {
                //return aux.isDisponible()+"@"+aux.isNoRequiereStock()+"@"+aux.getStock()+"@"+aux.getStockCritico();

                color =(Color)value;
                setOpaque(true);
                setSize(new Dimension(20,20));
                setPreferredSize(new Dimension(20,20));
                setForeground(color);
                setBackground(color);
                //setText("Color");
            }
            return this;
        }
    }
}
