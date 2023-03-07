package view.frame.producto;

import com.djm.ui.component.TextField;
import com.djm.util.LayoutPanel;
import model.Categoria;
import model.Producto;
import util.table.ModeloTabla;
import util.SystemProperties;
import view.frame.main.LoadData;
import view.frame.ui.component.Button;
import view.frame.ui.component.EtiquetaComponent;
import view.frame.ui.component.Table;
import view.frame.ui.component.TipoEtiqueta;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PTablaProducto {
    private Table<Producto> tabla;
    private JPanel pPrincipal;
    private TextField tBuscar;
    private Button bEditar;
    private Button bEliminar;
    private JLabel lCantidad;
    private ActionListenerProduct actionListener = GlobalProduct.getInstance().actionListener;

    private final SystemProperties sp = SystemProperties.getInstance();
    public PTablaProducto() {
        pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setOpaque(false);

        bEditar = new Button(sp.getValue("button.editar"), new ImageIcon("icon/edit.png"));
        bEliminar = new Button(sp.getValue("button.eliminar"), new ImageIcon("icon/delete.png"));
        Color red = new Color(201, 34, 34);
        bEliminar.setColorImage(red);
        bEliminar.setForeground(red);

        bEditar.setActionCommand("EDIT_PRODUCT");
        bEliminar.setActionCommand("DROP_PRODUCT");

        bEditar.addActionListener(actionListener);
        bEliminar.addActionListener(actionListener);

        tBuscar = new TextField(25);
        tBuscar.setHint(sp.getValue("productos.label.buscar_producto"));

        ModelTableProductoCustom mpc = new ModelTableProductoCustom();
        ModeloTabla<Producto> modelo = new ModeloTabla(mpc);

        this.tabla = new Table(modelo, 300);

        GlobalProduct.getInstance().table = tabla;

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tabla.isEnabled() && e.getClickCount() == 2) {
                    enabledPane(false);
                    Producto prod = tabla.getSelectedItem();//GlobalProduct.getInstance().getProductTableSelected();
                    if(prod!=null) {
                        GlobalProduct.getInstance().producto = prod;
                        GlobalProduct.getInstance().detalleProducto.fillerProducto();
                    }
                }
            }
        });

        tabla.setDefaultRenderer(EtiquetaComponent.class, new StatusIconRenderer());

        tBuscar.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ke) {
                int key = ke.getKeyCode();
                if (key == 40) {
                    tabla.setRowSelectionInterval(0);
                    tabla.requestFocus();
                }
            }

            public void keyReleased(KeyEvent ke) {
                tabla.soter(tBuscar.getText());
            }

            public void keyTyped(KeyEvent ke) {
            }
        });

        lCantidad = new JLabel();

        pPrincipal.add(tBuscar, LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 10, 0, 10, 0.0f, 0.0f));
        pPrincipal.add(lCantidad, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 0, 0, 0, 20, 1.0f, 0.0f));
        pPrincipal.add(tabla.getPanel(), LayoutPanel.constantePane(0, 1, 3, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 5, 10, 0, 10, 0.0f, 1.0f));
        pPrincipal.add(bEditar, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        pPrincipal.add(bEliminar, LayoutPanel.constantePane(1, 2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 1.0f, 0.0f));

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

   private class StatusIconRenderer extends EtiquetaComponent implements TableCellRenderer {

        public StatusIconRenderer() {}
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

                if(sstock==null||sstock.equals("null"))
                    sstock = "0";

                if(sstockcrit==null||sstockcrit.equals("null"))
                    sstockcrit="0";

                setSelected(isSelected);
                //System.out.println(sdisp+" "+snorstock+" "+sstock+" "+sstockcrit+" "+(Integer.parseInt(sstock) <= Integer.parseInt(sstockcrit)));
                if(Boolean.valueOf(sdisp)){
                    if(!Boolean.valueOf(snorstock)){
                        if (Integer.parseInt(sstock) == 0) {
                            setTipoEtiqueta(TipoEtiqueta.SinStock);
                        }
                        else if (Integer.parseInt(sstock) <= Integer.parseInt(sstockcrit)) {
                            setTipoEtiqueta(TipoEtiqueta.StockCritico);
                        }else
                            setTipoEtiqueta(TipoEtiqueta.Disponible);
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
    public void setCantidad(int cant){
        StringBuffer sb = new StringBuffer();
        sb.append(sp.getValue("productos.label.cantidad_producto"));
        sb.append(": ");
        sb.append(cant);

        lCantidad.setText(sb.toString());
    }

    protected void fillTableProduct(){

        tabla.setEnabled(false);
        //Clear tiene que estar afuera
        tabla.clearTable();

        Thread thread = new Thread(()-> {
            //Vemos que categoria esta seleccionada
            List<Categoria> listCatSel = GlobalProduct.getInstance().pCategoria.getPanelList().getItemSelected();//LoadData.getInstance().getConsultaCategoria().getList();//
            List<Producto> list = LoadData.getInstance().getConsultaProducto().getList();
            boolean e = list != null && !list.isEmpty();
            if (e) {
                boolean add = listCatSel.isEmpty();
                boolean reviewCat = !add;
                int count = 0;
                //System.out.println(add+" "+reviewCat);
                for (Producto prod : list) {
                    if(reviewCat) {
                        add = false;
                        for (Categoria cat : listCatSel) {
                            if (prod.getCategoria().getID()!=null &&
                                    (prod.getCategoria().getID().intValue() == cat.getID().intValue())){
                                add = true;
                                break;
                            }
                        }
                    }

                    if(add){
                        count++;
                        tabla.addRow(prod);
                    }
                }
                setCantidad(count);
                e = count > 0;
                tabla.setEnabled(e);
            }
        });
        thread.start();
    }
}
