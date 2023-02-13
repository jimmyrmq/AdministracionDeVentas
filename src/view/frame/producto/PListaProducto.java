package view.frame.producto;

import com.djm.ui.TextField;
import com.djm.ui.themes.panel.IPanelUI;
import com.djm.util.LayoutPanel;
import model.Categoria;
import model.Marca;
import model.Producto;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PListaProducto extends JPanel{

    private JTable tabla;
    private TableRowSorter<TableModel> sorter;
    private TextField tBuscar;

    public PListaProducto() {
        super(new GridBagLayout());
        tBuscar = new TextField(25);

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


        ModeloTabla modelo = new ModeloTabla();
        tabla = new JTable(modelo);

        GlobalProduct.getInstance().modelTable = modelo;
        GlobalProduct.getInstance().table = tabla;

        //tabla.setAutoCreateColumnsFromModel(false);
        tabla.setFillsViewportHeight(true);
        tabla.setShowGrid(false);//Mostrar las lineas
        tabla.setShowHorizontalLines(false);
        tabla.setShowVerticalLines(false);
        tabla.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        //tabla.setRowSelectionAllowed(true);
        //tabla.setCellSelectionEnabled(true);
        //tabla.setGridColor(new Color(170,170,170));
        tabla.setOpaque(true);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//Ajustarlo al tama�o del JScrollPane
        //tabla.setSelectionForeground( Color.white );

        sorter = new TableRowSorter<TableModel>(modelo);
        tabla.setRowSorter(sorter);

        TableColumn column=null;
        int []anchoColum={100,140,140,150,40};
        for (int i = 0; i <anchoColum.length; i++) {//tabla.getColumnCount()
            column = tabla.getColumnModel().getColumn(i);
            column.setMinWidth(anchoColum[i]);
            column.setPreferredWidth(anchoColum[i]);
        }

        addProductPrueba(modelo);

        IPanelUI panelUI = GlobalUI.getInstance().getTheme().getPanelUI();
        lookColumn(panelUI.getBackground(),panelUI.getForeground(),panelUI.getColorBorder(),Color.BLUE,panelUI.getFont());

        JScrollPane jsp = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setViewportBorder(null);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jsp.getViewport().setOpaque(false);
        jsp.setOpaque(false);
        jsp.setBorder(null);

        add(tBuscar, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 10, 20, 10, 0.0f, 0.0f));
        add(jsp, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 0, 10, 20, 10, 1.0f, 1.0f));

    }

    private void addProductPrueba(ModeloTabla modelo) {
        for(int i=1;i<=700;i++) {
            Marca mrc0 = new Marca();
            mrc0.setDesrcripcion("Fiat "+i);
            Categoria cat0 = new Categoria();
            cat0.setDesrcripcion("Automovil "+i);

            Producto prod1 = new Producto();
            prod1.setCodigo("000"+i);
            prod1.setNombre("Carro "+i);
            prod1.setCodigoBarra("78945112"+i);
            prod1.setMarca(mrc0);
            prod1.setCategoria(cat0);
            prod1.setStock(1);
            prod1.setNota("Esto es una nota del producto prueba; proudcto: "+i);

            modelo.addProduct(prod1);
        }
    }

    public void lookColumn(Color back, Color fore, Color border, Color gridColor, Font font) {
        JTableHeader th = tabla.getTableHeader();
        th.setOpaque(false);
        th.setFont(font);
        th.setBackground(back);
        th.setForeground(fore);
        th.setBorder(BorderFactory.createLineBorder(border));
        th.setPreferredSize(new Dimension(0, 21));

        /*tabla.setShowGrid(true);
        tabla.setShowHorizontalLines(false);
        tabla.setShowVerticalLines(true);*/
        tabla.setGridColor(gridColor);
    }


    public void soter(String text){
        if (text==null||text.trim().isEmpty())
            sorter.setRowFilter(null);
        else
            sorter.setRowFilter(RowFilter.regexFilter(text));

        tabla.updateUI();
    }
}
