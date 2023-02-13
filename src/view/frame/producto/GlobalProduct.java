package view.frame.producto;

import model.Producto;

import javax.swing.*;

public class GlobalProduct {
    protected JTable table;
    protected ModeloTabla modelTable;
    protected Producto producto;
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
}
