package view.frame.producto;

import com.djm.ui.component.TextField;
import com.djm.ui.themes.panel.IPanelUI;
import com.djm.ui.themes.table.ITableUI;
import com.djm.util.LayoutPanel;
import model.Producto;
import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.ui.component.Button;
import view.frame.ui.component.EtiquetaComponent;
import view.frame.ui.component.Notificaciones;
import view.frame.ui.component.TipoEtiqueta;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PTablaProducto {

    private JPanel pPrincipal;
    private JTable tabla;
    private TableRowSorter<TableModel> sorter;
    private TextField tBuscar;
    private Button bEditar;
    private Button bEliminar;

    private ActionListenerProduct actionListener = GlobalProduct.getInstance().actionListener;

    private final SystemProperties sp = SystemProperties.getInstance();
    public PTablaProducto() {
        pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setOpaque(false);


        JLayeredPane layered = new JLayeredPane();

        layered.add(new Notificaciones("Productos","Esto es una prueba"));
        FrameMain.frame.getContentPane().add(layered);

        bEditar = new Button(sp.getValue("button.editar"),new ImageIcon("icon/edit.png"));
        bEliminar = new Button(sp.getValue("button.eliminar"),new ImageIcon("icon/delete.png"));
        bEliminar.setColorImage(Color.RED);
        bEliminar.setForeground(Color.RED);

        bEditar.setActionCommand("EDIT_PRODUCT");
        bEliminar.setActionCommand("DROP_PRODUCT");

        bEditar.addActionListener(actionListener);
        bEliminar.addActionListener(actionListener);

        tBuscar = new TextField(25);
        tBuscar.setHint(sp.getValue("productos.label.buscar_producto"));
        tBuscar.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent ke){
                int key = ke.getKeyCode();
                if(key==40){
                    tabla.setRowSelectionInterval(0,0);
                    tabla.requestFocus();
                }
            }
            public void keyReleased(KeyEvent ke){
                soter(tBuscar.getText());
            }
            public 	void keyTyped(KeyEvent ke){}
        });

        IPanelUI panelUI = GlobalUI.getInstance().getTheme().getPanelUI();
        ModeloTabla<Producto> modelo = new ModeloTabla<Producto>();
        tabla = new JTable(modelo);

        GlobalProduct.getInstance().modelTable = modelo;
        GlobalProduct.getInstance().table = tabla;
        ITableUI tableUI = GlobalUI.getInstance().getTheme().getTableUI();

        //tabla.setAutoCreateColumnsFromModel(false);
        tabla.setShowGrid(false);//Mostrar las lineas
        tabla.setFillsViewportHeight(false);
        tabla.setShowHorizontalLines(true);
        tabla.setShowVerticalLines(false);
        tabla.setBackground(tableUI.getBackground());
        tabla.setRowSelectionAllowed(true);
        //tabla.setCellSelectionEnabled(true);
        tabla.setOpaque(true);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//Ajustarlo al tama�o del JScrollPane
        //tabla.setSelectionForeground( Color.white );
        tabla.setGridColor(panelUI.getColorBorder());
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(21);
        sorter = new TableRowSorter<TableModel>(modelo);
        tabla.setRowSorter(sorter);

        /*KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        t1.getTable().getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "ENTER");
        t1.getTable().getActionMap().put("ENTER",new EnterAction());*/
        int dimX = 0;
        TableColumn column=null;
        int []anchoColum={80,140,140,150,40,90};
        for (int i = 0; i <anchoColum.length; i++) {//tabla.getColumnCount()
            sorter.setSortable(i, false);
            column = tabla.getColumnModel().getColumn(i);
            column.setMinWidth(anchoColum[i]);
            column.setPreferredWidth(anchoColum[i]);
            dimX +=anchoColum[i];
        }
        tabla.setPreferredScrollableViewportSize(new Dimension(dimX, 100));

        lookColumn(tableUI.getBackgroundHeader(),tableUI.getForegroundHeader(),panelUI.getColorBorder(),tableUI.getFont());

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    enabledPane(false);
                    Producto prod = GlobalProduct.getInstance().getProductTableSelected();
                    if(prod!=null) {
                        GlobalProduct.getInstance().producto = prod;
                        GlobalProduct.getInstance().detalleProducto.fillerProducto();
                    }
                }
            }
        });

        //tabla.getColumn("").setCellRenderer(new BooleanIconRenderer());

        tabla.setDefaultRenderer(EtiquetaComponent.class, new BooleanIconRenderer());

        JScrollPane jsp = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setViewportBorder(null);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jsp.getViewport().setOpaque(true);
        jsp.setOpaque(false);
        jsp.setBorder(null);
        jsp.getViewport().setBackground(tableUI.getBackground());

        pPrincipal.add(tBuscar, LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 10, 0, 10, 0.0f, 0.0f));
        pPrincipal.add(jsp, LayoutPanel.constantePane(0, 1, 2, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 5, 10, 0, 10, 1.0f, 1.0f));
        pPrincipal.add(bEditar, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        pPrincipal.add(bEliminar, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 1.0f, 0.0f));

    }

    public void lookColumn(Color back, Color fore, Color border, Font font) {
        JTableHeader th = tabla.getTableHeader();
        th.setOpaque(false);
        //th.setFont(new Font("Tahoma",1,15));
        //th.setBorder(BorderFactory.createLineBorder(border));
        //th.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, border));
        //th.setBorder(new LineBorder(Color.BLACK));//new MatteBorder(0,0,1,0, border));
        th.setEnabled(false);
        th.setPreferredSize(new Dimension(0, 25));
        th.setReorderingAllowed(false);
        th.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                c.setFont(font);
                c.setHorizontalAlignment(SwingConstants.CENTER);
                c.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, border));
                c.setBackground(back);
                c.setForeground(fore);
                return c;
            }
        });

    }


    public void soter(String text){
        if (text==null||text.trim().isEmpty())
            sorter.setRowFilter(null);
        else
            sorter.setRowFilter(RowFilter.regexFilter(text));

        tabla.updateUI();
    }

    public JPanel getPanel(){
        return pPrincipal;
    }

    protected void enabledPane(boolean e){
        tBuscar.setEnabled(e);
        tabla.setEnabled(e);
        bEliminar.setEnabled(e);
        bEditar.setEnabled(e);
        GlobalProduct.getInstance().pCategoria.getPanelList().setEnabled(e);
    }

   private class BooleanIconRenderer extends EtiquetaComponent implements TableCellRenderer {

        public BooleanIconRenderer() {}
        private String sdisp,snorstock,sstock,sstockcrit;//
        private String valueSplit[];
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int col) {
            //System.out.println(row+" "+col+" "+value);
            if(col == 5) {
                //return aux.isDisponible()+"@"+aux.isNoRequiereStock()+"@"+aux.getStock()+"@"+aux.getStockCritico();
                valueSplit = String.valueOf(value).split("@");
                sdisp = valueSplit[0];//Disponible
                snorstock = valueSplit[1];//No requiere stock
                sstock = valueSplit[2];//Stock
                sstockcrit = valueSplit[3];//Stock Critico

                setSelected(isSelected);

                if(Boolean.valueOf(sdisp)){
                    if(Boolean.valueOf(snorstock)){
                        if (Integer.parseInt(sstock) == 0) {
                            setTipoEtiqueta(TipoEtiqueta.SinStock);
                        }
                        else if (Integer.parseInt(sstock) <= Integer.parseInt(sstockcrit)) {
                            setTipoEtiqueta(TipoEtiqueta.StockCritico);
                        }
                    }
                    else{
                        setTipoEtiqueta(TipoEtiqueta.Disponible);
                    }
                }else{
                    setTipoEtiqueta(TipoEtiqueta.NoDisponible);
                }
            }
            return this;
        }
    }
}
