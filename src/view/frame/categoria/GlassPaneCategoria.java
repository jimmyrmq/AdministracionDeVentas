package view.frame.categoria;

import com.djm.ui.component.TextField;
import com.djm.util.LayoutPanel;
import model.Categoria;
import util.Global;
import util.SystemProperties;
import util.table.ModeloTabla;
import view.frame.main.FrameMain;
import view.frame.main.LoadData;
import view.frame.producto.GlobalProduct;
import view.frame.ui.component.ColorCategoria;
import view.frame.ui.glass.IPanelGlass;
import view.frame.ui.component.Button;
import view.frame.ui.component.OptionPane;
import view.frame.ui.component.SelectedColor;
import view.frame.ui.component.SelectedColorGroup;
import view.frame.ui.component.Table;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GlassPaneCategoria  implements ActionListener, IPanelGlass {
    private Categoria categoria;
    //private Table table;
    private DefaultListModel<Categoria> modelCat;
    private JList<Categoria> listCategoria;
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
    private final int widthList = 270;

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

        modelCat = new DefaultListModel<>();
        listCategoria = new JList<>(modelCat);
        listCategoria.setBackground(GlobalUI.getInstance().getTheme().getTextUI().getBackground());
        listCategoria.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        Dimension dim0 =  new Dimension(widthList,340);
        Dimension dim1 =  new Dimension(widthList+10,350);
        listCategoria.setSize(dim0);
        listCategoria.setPreferredSize(dim0);
        listCategoria.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e) {
               if (listCategoria.isEnabled() && e.getClickCount() == 2) {
                   getCategoriaSelected();
               }
           }
       });

        JScrollPane jsp = new JScrollPane(listCategoria, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setViewportBorder(BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getTextUI().getColorBorder()));
        jsp.getViewport().setOpaque(true);
        jsp.setOpaque(false);
        jsp.setBorder(null);
        jsp.getViewport().setBackground(GlobalUI.getInstance().getTheme().getTextUI().getBackground());
        jsp.setSize(dim1);
        jsp.setPreferredSize(dim1);

        // set cell renderer
        listCategoria.setCellRenderer(new CategoriaRenderer());

        panel.add(jsp, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.PAGE_START, 10, 10, 5, 0, 0.0f, 1.0f));
        panel.add(bEliminar, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 3, 5, 0, 1.0f, 1.0f));

        return panel;
    }

    public boolean fillTable(){
        //table.setEnabled(false);
        listCategoria.setEnabled(false);
        LoadData.getInstance().getConsultaCategoria().loadDBCategoria();
        List<Categoria> cateList = LoadData.getInstance().getConsultaCategoria().getList();
        int sz = cateList.size();
        for(int i = 0;i<sz;i++){
            try {
                Categoria cate = cateList.get(i);
                //table.addRow(cate);
                modelCat.addElement(cate);
            }catch (IndexOutOfBoundsException exc){
                System.out.println("Error fillTable Categoria: "+exc);
            }
        }

        boolean e = !cateList.isEmpty();
        //table.setEnabled(e);
        listCategoria.setEnabled(e);
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
                LoadData.getInstance().getConsultaCategoria().loadDBCategoria();
                GlobalProduct.getInstance().addCBCategoria(getCategoria(),edit);

                if(edit) {
                    listCategoria.repaint();
                    clear();
                }else{
                    FrameMain.frame.getGlassPane().setVisible(false);
                    FrameMain.notificacion.start(sp.getValue("categoria.label.title"), sp.getValue("categoria.message.marca_registrada_exito"));
                }

            }else
                OptionPane.error(FrameMain.frame,admin.getMensaje());
        }
        else if(action.equals("CANCEL")){
            boolean close = !edit;
            clear();
            if(close) {
                Global.getInstance().closeGlassPane();
            }
        }
        else if(action.equals("NUEVO")){
            bNuevo.setEnabled(false);
            tDescripcion.requestFocus();
            clear();
        }

        else if(action.equals("DELETE")){
            int index = listCategoria.getSelectedIndex();
            if(index!=-1) {
                Categoria mdel = modelCat.get(index);//(Categoria) table.getSelectedItem();
                if (mdel != null) {
                    OptionPane.warning(FrameMain.frame, sp.getValue("categoria.message.warning_delete"));
                    int yes = OptionPane.questionYesOrKey(FrameMain.frame, sp.getValue("categoria.message.delete"));
                    if (yes == OptionPane.OK) {
                        AdministracionCategoria adminCategoria = new AdministracionCategoria();
                        boolean rtn = adminCategoria.eliminar(mdel.getID());
                        if (rtn) {
                            modelCat.remove(index);
                            GlobalProduct.getInstance().deleteCategoria(mdel);
                        }
                    }
                }
            }
        }
    }

    public void getCategoriaSelected() {
        int index = listCategoria.getSelectedIndex();
        if(index!=-1) {
            Categoria cate = modelCat.get(index);//(Categoria) table.getSelectedItem();
            setCategoria(cate);
        }
        else{
            OptionPane.error(FrameMain.frame, sp.getValue("categoria.message.selected"));
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
    public String getTitle() {
        return sp.getValue("categoria.label.title.registro");
    }

    @Override
    public void init() {
        clear();

        fillTable();
    }

    public void clear(){
        edit = false;
        categoria = null;
        tDescripcion.setText(null);

        if(listCategoria!=null)
            listCategoria.clearSelection();

        for(SelectedColor sc : selectedColors){
            sc.setSelected(false);
        }

        tDescripcion.requestFocus();
    }

   /* private class StatusIconRenderer extends JLabel implements TableCellRenderer {
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
    }*/

    public class CategoriaRenderer extends JPanel implements ListCellRenderer<Categoria> {
        private ColorCategoria colorCategoria = new ColorCategoria();
        private JLabel lCate = new JLabel();
        private Color colSel = GlobalUI.getInstance().getTheme().getButtonUI().getBackgroundSelected();
        private Color colUnsel = GlobalUI.getInstance().getTheme().getTextUI().getBackground();
        public CategoriaRenderer(){
            int height = colorCategoria.getHeigth()+6;
            setSize(widthList,height);
            setLayout(new GridBagLayout());

            lCate.setForeground(GlobalUI.getInstance().getTheme().getPanelUI().getForeground());
            lCate.setFont(GlobalUI.getInstance().getTheme().getPanelUI().getFont());

            add(lCate, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 1.0f, 0.0f));
            add(colorCategoria, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 3, 5, 3, 5, 0.0f, 0.0f));
        }
        @Override
        public Component getListCellRendererComponent(JList<? extends Categoria> list, Categoria value, int index, boolean isSelected, boolean cellHasFocus) {
            lCate.setText(value.getDesrcripcion());
            colorCategoria.setColor(value.getColor());
            if(isSelected){
                setBackground(colSel);
            }
            else
                setBackground(colUnsel);
            return this;
        }
    }
}
