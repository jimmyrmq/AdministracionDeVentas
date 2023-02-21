package view.frame.producto;

import model.Categoria;
import model.Producto;
import view.frame.main.FrameMain;

import javax.swing.*;
import java.awt.Cursor;
import java.util.List;

public class GlobalProduct {
    protected JTable table;
    protected int indexTableProductSelect = -1;
    protected ModeloTabla <Producto> modelTable;
    protected Producto producto;
    protected DetalleProducto detalleProducto;
    protected  PCategoria pCategoria;
    protected PTablaProducto pTablaProducto;

    protected final ConsultaCategoria consultaCategoria = new ConsultaCategoria();
    protected final ConsultaProducto consultaProducto = new ConsultaProducto();
    protected final ConsultaMarca consultaMarca = new ConsultaMarca();

    protected final ActionListenerProduct actionListener = new ActionListenerProduct();
    private static GlobalProduct globalProduct;
    private GlobalProduct(){}

    public static GlobalProduct getInstance(){
        if(globalProduct == null)
            globalProduct = new GlobalProduct();

        return globalProduct;
    }

    /*protected JTable getTable() {
        return table;
    }

    protected void setTable(JTable table) {
        this.table = table;
    }

    public ModeloTabla getModelTable() {
        return modelTable;
    }

    public void setModelTable(ModeloTabla modelTable) {
        this.modelTable = modelTable;
    }*/
    protected void init(){
        FrameMain.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        new InitProduct();

        detalleProducto.init();

        fillTableProduct();

        FrameMain.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    protected void fillTableProduct(){
        modelTable.clearTable();
        List<Categoria> listCatSel = pCategoria.getPanelList().getItemSelected();

        Thread thread = new Thread(()-> {
            List<Producto> list = GlobalProduct.getInstance().consultaProducto.getList();
            boolean e = list != null && !list.isEmpty();
            table.setEnabled(e);
            if (e) {
                boolean add = listCatSel.isEmpty();
                boolean reviewCat = !add;
                int count = 0;
                //System.out.println(add+" "+reviewCat);
                for (Producto prod : list) {
                    if(reviewCat) {
                        add = false;
                        cont:
                        for (Categoria cat : listCatSel) {
                            if (prod.getCategoria().getID() == cat.getID()) {
                                add = true;
                                break cont;
                            }
                        }
                    }

                    if(add){
                        count++;
                        modelTable.addProduct(prod);
                    }
                }
                pTablaProducto.setCantidad(count);
            }
        });
        thread.start();
    }

    protected Producto getProductTableSelected(){
        indexTableProductSelect = table.getSelectionModel().getLeadSelectionIndex();
        //System.out.println(">> "+index);
        Producto producto = null;
        if(indexTableProductSelect != -1) {
            int[] selection = table.getSelectedRows();
            if(selection.length == 1) {
                int row = table.convertRowIndexToModel(selection[0]);
                if (row != -1) {
                    producto = modelTable.getValue(row);
                    //System.out.println(">> " + producto.getCategoria() + " " + producto.getNombre() + " " + producto.getNota());
                }
            }
        }

        return producto;
    }
}
