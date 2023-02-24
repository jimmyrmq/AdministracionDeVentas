package view.frame.producto;

import model.Producto;
import util.Global;
import util.SystemProperties;
import view.frame.categoria.WindowCategoria;
import view.frame.main.FrameMain;
import view.frame.main.LoadData;
import view.frame.marca.WindowMarca;
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
            GlobalProduct.getInstance().pTablaProducto.fillTableProduct();
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
            }
            else if (action.equals("DROP_PRODUCT")) {
                Producto prod = GlobalProduct.getInstance().getProductTableSelected();
                if (prod != null) {
                    OptionPane.warning(FrameMain.frame, sp.getValue("productos.message.warning_delete"));
                    int yes = OptionPane.questionYesOrKey(FrameMain.frame, sp.getValue("productos.message.delete"));
                    if (yes == OptionPane.OK) {
                        int index = GlobalProduct.getInstance().table.getSelectedIndex();
                        if (index != -1){
                            AdministracionProducto ap = new AdministracionProducto();
                            boolean rtn = ap.eliminar(prod.getID());
                            if(rtn) {
                                GlobalProduct.getInstance().table.removeRow(index);
                            }

                            FrameMain.notificacion.start(sp.getValue("productos.label.title"), ap.getMensaje());

                        }

                    }
                } else {
                    OptionPane.error(FrameMain.frame, sp.getValue("productos.message.selected_product_delete"));
                }
            } else if (action.equals("UPDATE_DATA_PRODUCTO")) {
                GlobalProduct.getInstance().init();
                GlobalProduct.getInstance().reorganizarListCat();
            }
            else if (action.equals("MARCA_PRODUCTO_DIALOG")) {
                WindowMarca windowMarca = new WindowMarca();
                if(windowMarca.isAcept()){
                    boolean edit = windowMarca.isEdit();
                    LoadData.getInstance().getConsultaMarca().loadDBMarca();
                    GlobalProduct.getInstance().addCBCarga(windowMarca.getMarca(),edit);

                    FrameMain.notificacion.start(sp.getValue("marca.label.title"),sp.getValue("marca.message.marca_registrada_exito"));
                }
            }
            else if (action.equals("CATEGORIA_PRODUCTO_DIALOG")) {
                WindowCategoria windowCategoria = new WindowCategoria();
                if(windowCategoria.isAcept()){
                    boolean edit = windowCategoria.isEdit();
                    LoadData.getInstance().getConsultaCategoria().loadDBCategoria();
                    GlobalProduct.getInstance().addCBCategoria(windowCategoria.getCategoria(),edit);

                    FrameMain.notificacion.start(sp.getValue("categoria.label.title"),sp.getValue("categoria.message.marca_registrada_exito"));
                }
            }
        }
    }

}
