package view.frame.producto;

import model.Producto;
import util.Global;
import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.ui.component.OptionPane;
import view.frame.ui.themes.GlobalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerProduct implements ActionListener {


    public ActionListenerProduct(){}

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if(action.equals("EDIT_PRODUCT")){
            Producto prod = selectedProduct();
            if(prod!=null) {
                GlobalProduct.getInstance().producto = prod;
                GlobalProduct.getInstance().detalleProducto.fillerProducto();
            }
        }
        else if (action.equals("DROP_PRODUCT")){
            Producto prod = selectedProduct();
            if(prod!=null) {
                int index = GlobalProduct.getInstance().table.getSelectionModel().getLeadSelectionIndex();
                if(index!=-1)
                    GlobalProduct.getInstance().modelTable.delProduct(index);
                //GlobalProduct.getInstance().modelTable.removeRow(3);
            }else{
                SystemProperties sp = SystemProperties.getInstance();
                OptionPane.error(FrameMain.frame,sp.getValue("produtos.message.selected_product_delete"));
            }
        }

    }

    private Producto selectedProduct(){
        int index = GlobalProduct.getInstance().table.getSelectionModel().getLeadSelectionIndex();
        //System.out.println(">> "+index);
        Producto producto = null;
        if(index != -1) {
            int[] selection = GlobalProduct.getInstance().table.getSelectedRows();
            if(selection.length == 1) {
                int row = GlobalProduct.getInstance().table.convertRowIndexToModel(selection[0]);
                if (row != -1) {
                    producto = (Producto) GlobalProduct.getInstance().modelTable.getValue(row);
                    //System.out.println(">> " + producto.getCategoria() + " " + producto.getNombre() + " " + producto.getNota());
                }
            }
        }

        return producto;
    }
}
