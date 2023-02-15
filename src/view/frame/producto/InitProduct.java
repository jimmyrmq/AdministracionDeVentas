package view.frame.producto;

import view.frame.main.Init;

public class InitProduct {
    public InitProduct(){
        Thread t = new Thread(()->{
            GlobalProduct.getInstance().consultaCategoria.listarCategoria();
            GlobalProduct.getInstance().consultaMarca.listarMarca();
            GlobalProduct.getInstance().consultaProducto.listarProducto();
        });
        t.start();
    }
}
