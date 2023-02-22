package view.frame.ui.component;

import com.djm.ui.themes.panel.IPanelUI;
import com.djm.ui.themes.table.ITableUI;
import model.Producto;
import util.table.ModeloTabla;
import view.frame.producto.GlobalProduct;
import view.frame.producto.ModelTableProductoCustom;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class Table <E>{
    private JTable tabla;
    private TableRowSorter<TableModel> sorter;
    private ITableUI tableUI = GlobalUI.getInstance().getTheme().getTableUI();
    private IPanelUI panelUI = GlobalUI.getInstance().getTheme().getPanelUI();

    public Table(ModeloTabla modelo,int [] anchoColum, int height){

        tabla = new JTable(modelo);


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

        for (int i = 0; i <anchoColum.length; i++) {//tabla.getColumnCount()
            sorter.setSortable(i, false);
            column = tabla.getColumnModel().getColumn(i);
            column.setMinWidth(anchoColum[i]);
            column.setPreferredWidth(anchoColum[i]);
            dimX +=anchoColum[i];
        }
        tabla.setPreferredScrollableViewportSize(new Dimension(dimX, height));

        lookColumn(tableUI.getBackgroundHeader(),tableUI.getForegroundHeader(),panelUI.getColorBorder(),tableUI.getFont());

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

    public JTable getTable(){
        return tabla;
    }

    public JScrollPane getPanel(){
        JScrollPane jsp = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setViewportBorder(null);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jsp.getViewport().setOpaque(true);
        jsp.setOpaque(false);
        jsp.setBorder(null);
        jsp.getViewport().setBackground(tableUI.getBackground());
        return jsp;
    }
}
