package view.frame.producto;

import model.Producto;
import view.frame.main.FrameMain;

import javax.swing.*;
import java.awt.Cursor;
import java.util.List;

public class GlobalProduct {
    protected JTable table;
    protected ModeloTabla <Producto> modelTable;
    protected Producto producto;
    protected DetalleProducto detalleProducto;

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
        modelTable.clearTable();



        detalleProducto.init();

        Thread thread = new Thread(()-> {
            List<Producto> list = GlobalProduct.getInstance().consultaProducto.getList();
            boolean e = list != null && !list.isEmpty();
            table.setEnabled(e);
            if (e) {
                for (Producto prod : list)
                    modelTable.addProduct(prod);
            }
        });
        thread.start();

        FrameMain.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    protected Producto getProductTableSelected(){
        int index = table.getSelectionModel().getLeadSelectionIndex();
        //System.out.println(">> "+index);
        Producto producto = null;
        if(index != -1) {
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
