package view.frame.producto;

import model.Producto;
import util.Global;
import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.ui.component.OptionPane;
import view.frame.ui.themes.GlobalUI;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerProduct implements ActionListener {

    public ActionListenerProduct(){}

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if(action.equals("EDIT_PRODUCT")){
            Producto prod = GlobalProduct.getInstance().getProductTableSelected();
            if(prod!=null) {
                GlobalProduct.getInstance().producto = prod;
                GlobalProduct.getInstance().detalleProducto.fillerProducto();
            }
        }
        else if (action.equals("DROP_PRODUCT")){
            SystemProperties sp = SystemProperties.getInstance();
            Producto prod = GlobalProduct.getInstance().getProductTableSelected();
            if (prod != null) {
                OptionPane.warning(FrameMain.frame, sp.getValue("produtos.message.warning_delete"));
                int yes = OptionPane.questionYesOrKey(FrameMain.frame,sp.getValue("produtos.message.delete"));
                if(yes == OptionPane.OK) {
                    int index = GlobalProduct.getInstance().table.getSelectionModel().getLeadSelectionIndex();
                    if (index != -1)
                        GlobalProduct.getInstance().modelTable.removeRow(index);
                    //GlobalProduct.getInstance().modelTable.removeRow(3);
                }
            } else {
                OptionPane.error(FrameMain.frame, sp.getValue("produtos.message.selected_product_delete"));
            }
        }
        else if (action.equals("UPDATE_DATA_PRODUCTO")){
            GlobalProduct.getInstance().init();
        }

    }

}
