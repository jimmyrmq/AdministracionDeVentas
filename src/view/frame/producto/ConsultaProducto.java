package view.frame.producto;

import com.djm.db.result.ResultData;
import model.Categoria;
import model.Marca;
import model.Producto;
import util.Global;

import java.util.List;

public class ConsultaProducto {
    private List<Producto> lProduct;
    public void listarProducto(){
        ResultData<Producto> resultData = new ResultData<>(Producto.class, Global.getInstance().getConnection());

        lProduct = resultData.getDataList("select * from Producto");
        for(Producto prod : lProduct) {
            if(prod.getMarca()==null) {
                Marca marca = new Marca();
                marca.setDesrcripcion("Ninguno");
                prod.setMarca(marca);
            }

            if(prod.getCategoria()==null) {
                Categoria categoria = new Categoria();
                categoria.setDesrcripcion("Ninguno");
                prod.setCategoria(categoria);
            }
        }
    }

    public List<Producto> getList() {
        return lProduct;
    }
}
