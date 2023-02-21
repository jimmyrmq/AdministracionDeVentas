package view.frame.producto;

import model.Producto;
import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.marca.DialogMarca;
import view.frame.ui.Notificacion;
import view.frame.ui.component.Button;
import view.frame.ui.component.CategoriaUI;
import view.frame.ui.component.OptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerProduct implements ActionListener {
    private final SystemProperties sp = SystemProperties.getInstance();

    public ActionListenerProduct(){}

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if((e.getSource() instanceof CategoriaUI)){
            GlobalProduct.getInstance().fillTableProduct();
        }
        else  if((e.getSource() instanceof Button)) {
            if (action.equals("EDIT_PRODUCT")) {
                Producto prod = GlobalProduct.getInstance().getProductTableSelected();
                if (prod != null) {
                    GlobalProduct.getInstance().pTablaProducto.enabledPane(false);
                    GlobalProduct.getInstance().producto = prod;
                    GlobalProduct.getInstance().detalleProducto.fillerProducto();
                } else {
                    OptionPane.error(FrameMain.frame, sp.getValue("productos.message.selected_product_editar"));
                }
            } else if (action.equals("DROP_PRODUCT")) {
                Producto prod = GlobalProduct.getInstance().getProductTableSelected();
                if (prod != null) {
                    OptionPane.warning(FrameMain.frame, sp.getValue("productos.message.warning_delete"));
                    int yes = OptionPane.questionYesOrKey(FrameMain.frame, sp.getValue("productos.message.delete"));
                    if (yes == OptionPane.OK) {
                        int index = GlobalProduct.getInstance().table.getSelectionModel().getLeadSelectionIndex();
                        if (index != -1)
                            GlobalProduct.getInstance().modelTable.removeRow(index);
                        //GlobalProduct.getInstance().modelTable.removeRow(3);
                    }
                } else {
                    OptionPane.error(FrameMain.frame, sp.getValue("productos.message.selected_product_delete"));
                }
            } else if (action.equals("UPDATE_DATA_PRODUCTO")) {
                GlobalProduct.getInstance().init();
            }
            else if (action.equals("MARCA_PRODUCTO_DIALOG")) {
                Notificacion notificacion2 = new Notificacion();
                notificacion2.setTitle(sp.getValue("marca.label.title"));
                DialogMarca dialogMarca = new DialogMarca();
                if(dialogMarca.isAcept()){
                    notificacion2.setMensaje(sp.getValue("marca.message.marca_registrada_exito"));
                    notificacion2.start();
                }
            }
        }
    }

}
