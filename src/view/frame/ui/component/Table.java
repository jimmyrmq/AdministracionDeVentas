package view.frame.ui.component;

import com.djm.ui.themes.panel.IPanelUI;
import com.djm.ui.themes.table.ITableUI;
import util.table.ModeloTabla;
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

public class Table <E> extends JTable{
    //private JTable tabla;
    private TableRowSorter<TableModel> sorter;
    private ITableUI tableUI = GlobalUI.getInstance().getTheme().getTableUI();
    private IPanelUI panelUI = GlobalUI.getInstance().getTheme().getPanelUI();
    private ModeloTabla modeloTabla;
    public Table(ModeloTabla modelo, int height){
        super(modelo);
        this.modeloTabla = modelo;

        //tabla = new JTable(modelo);

        //setAutoCreateColumnsFromModel(false);
        setShowGrid(false);//Mostrar las lineas
        setFillsViewportHeight(false);
        setShowHorizontalLines(true);
        setShowVerticalLines(false);
        setBackground(tableUI.getBackground());
        setRowSelectionAllowed(true);
        //setCellSelectionEnabled(true);
        setOpaque(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//Ajustarlo al tama�o del JScrollPane
        //setSelectionForeground( Color.white );
        setGridColor(panelUI.getColorBorder());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setRowHeight(21);

        sorter = new TableRowSorter<TableModel>(modelo);
        setRowSorter(sorter);

        /*KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        t1.getTable().getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "ENTER");
        t1.getTable().getActionMap().put("ENTER",new EnterAction());*/
        int dimX = 0;
        TableColumn column=null;

        int anchoColum [] = modeloTabla.getModelCustom().getWidthCell();

        for (int i = 0; i <anchoColum.length; i++) {//getColumnCount()
            sorter.setSortable(i, false);
            column = getColumnModel().getColumn(i);
            column.setMinWidth(anchoColum[i]);
            column.setPreferredWidth(anchoColum[i]);
            dimX +=anchoColum[i];
        }
        setPreferredScrollableViewportSize(new Dimension(dimX, height));

        lookColumn(tableUI.getBackgroundHeader(),tableUI.getForegroundHeader(),panelUI.getColorBorder(),tableUI.getFont());

    }

    public void lookColumn(Color back, Color fore, Color border, Font font) {
        JTableHeader th = getTableHeader();
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

    /*public TableRowSorter getSorter(){
        return sorter;
    }*/

    public JScrollPane getPanel(){
        JScrollPane jsp = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setViewportBorder(null);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jsp.getViewport().setOpaque(true);
        jsp.setOpaque(false);
        jsp.setBorder(null);
        jsp.getViewport().setBackground(tableUI.getBackground());
        /*jsp.updateUI();
        jsp.repaint();
        jsp.revalidate();
        jsp.getViewport().updateUI();
        jsp.getViewport().repaint();
        jsp.getViewport().revalidate();*/

        return jsp;
    }

    public E getSelectedItem(){
        setEnabled(false);
        E object = null;
        int indexTableProductSelect = getSelectionModel().getLeadSelectionIndex();
        //System.out.println(">> "+index);
        if (indexTableProductSelect != -1) {
            int[] selection = getSelectedRows();
            if (selection.length == 1) {
                int row = convertRowIndexToModel(selection[0]);
                if (row != -1) {
                    Object obj = this.modeloTabla.getValue(row);
                    object = (E) obj;
                    //System.out.println(">> " + producto.getCategoria() + " " + producto.getNombre() + " " + producto.getNota());
                }
            }
        }

        setEnabled(true);
        return object;
    }

    public int getSelectedIndex(){
        int i = getSelectionModel().getLeadSelectionIndex();

        return i;
    }

    public void setRowSelectionInterval(int row){
        setRowSelectionInterval(row,row);
    }


    public void soter(String text){
        if (text==null||text.trim().isEmpty())
            sorter.setRowFilter(null);
        else
            sorter.setRowFilter(RowFilter.regexFilter(text));

        updateUI();
    }

    public void clearTable(){
        modeloTabla.clearTable();
    }

    public void addRow(E e){
        modeloTabla.addProduct(e);
    }
    public void editRow(E e, int row){
        modeloTabla.editProduct(e,row);
    }

    public void removeRow(int row){
        modeloTabla.removeRow(row);
    }
    /*
    public void setEnabled(boolean e){
        setEnabled(e);
    }

    public boolean isEnabled(){
        return isEnabled();
    }

    public void updateUI(){
        updateUI();
    }

    public void addMouseListener(MouseListener ml){
        addMouseListener(ml);
    }*/
}
