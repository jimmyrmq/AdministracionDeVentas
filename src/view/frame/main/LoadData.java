package view.frame.main;

import view.frame.marca.ConsultaMarca;
import view.frame.categoria.ConsultaCategoria;
import view.frame.producto.ConsultaProducto;

public class LoadData {
    private final ConsultaCategoria consultaCategoria = new ConsultaCategoria();
    private final ConsultaProducto consultaProducto = new ConsultaProducto();
    private final ConsultaMarca consultaMarca = new ConsultaMarca();
    private static LoadData loadData = null;
    private LoadData(){}

    public static LoadData getInstance(){
        if(loadData == null){
            loadData = new LoadData();
        }

        return loadData;
    }

    public void loadCategoria(){
        consultaCategoria.loadDBCategoria();
    }

    public void loadMarca(){
        consultaMarca.loadDBMarca();
    }

    public void loadProductos(){
        //Tiene que ser de ese orden para que en la lista de los productos pueda tomar la marca y la categoria
        //if(consultaCategoria.isListNull())
            loadCategoria();

        //if(consultaMarca.isListNull())
            loadMarca();

        consultaProducto.listarProducto();
    }

    public void loadAll() {
        consultaCategoria.loadDBCategoria();
        consultaMarca.loadDBMarca();
        consultaProducto.listarProducto();
    }

    public ConsultaCategoria getConsultaCategoria(){
        return consultaCategoria;
    }

    public ConsultaMarca getConsultaMarca(){
        return consultaMarca;
    }

    public ConsultaProducto getConsultaProducto() {
        return consultaProducto;
    }
}
