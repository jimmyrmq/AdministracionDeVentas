package view.frame.producto;

import model.Producto;

public class AdministracionProducto {
    public AdministracionProducto(){}
    public boolean guardar(Producto prod){
        //boolean rtn = false;
        GlobalProduct.getInstance().modelTable.addProduct(prod);
        return true;
    }
}
