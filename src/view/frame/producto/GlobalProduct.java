package view.frame.producto;

import model.Categoria;
import model.Marca;
import model.Producto;
import view.frame.main.FrameMain;
import view.frame.marca.ConsultaMarca;
import view.frame.ui.Notificacion;
import view.frame.ui.component.Table;

import java.awt.Cursor;
import java.util.List;

public class GlobalProduct {
    protected Table table;
    protected int indexTableProductSelect = -1;

    protected Producto producto;
    protected DetalleProducto detalleProducto;
    protected  PCategoria pCategoria;
    protected PTablaProducto pTablaProducto;

    protected final ConsultaCategoria consultaCategoria = new ConsultaCategoria();
    protected final ConsultaProducto consultaProducto = new ConsultaProducto();
    protected final ConsultaMarca consultaMarca = new ConsultaMarca();

    protected final Notificacion notificacion = new Notificacion();
    protected final ActionListenerProduct actionListener = new ActionListenerProduct();
    private static GlobalProduct globalProduct;

    private GlobalProduct(){}

    public static GlobalProduct getInstance(){
        if(globalProduct == null)
            globalProduct = new GlobalProduct();

        return globalProduct;
    }

    protected void init(){
        FrameMain.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        new InitProduct();

        detalleProducto.init();

        fillTableProduct();

        FrameMain.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    protected void fillTableProduct(){
        table.clearTable();
        List<Categoria> listCatSel = pCategoria.getPanelList().getItemSelected();

        Thread thread = new Thread(()-> {
            List<Producto> list = GlobalProduct.getInstance().consultaProducto.getList();
            boolean e = list != null && !list.isEmpty();
            table.setEnabled(e);
            if (e) {
                boolean add = listCatSel.isEmpty();
                boolean reviewCat = !add;
                int count = 0;
                //System.out.println(add+" "+reviewCat);
                for (Producto prod : list) {
                    if(reviewCat) {
                        add = false;
                        for (Categoria cat : listCatSel) {
                            if (prod.getCategoria().getID().intValue() == cat.getID().intValue()) {
                                add = true;
                                break;
                            }
                        }
                    }

                    if(add){
                        count++;
                        table.addRow(prod);
                    }
                }
                pTablaProducto.setCantidad(count);
            }
        });
        thread.start();
    }

    protected Producto getProductTableSelected(){
        FrameMain.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        Producto producto = (Producto) table.getSelectedItem();//table;
        FrameMain.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        /*indexTableProductSelect = table.getSelectionModel().getLeadSelectionIndex();
        //System.out.println(">> "+index);
        Producto producto = null;
        if(indexTableProductSelect != -1) {
            int[] selection = table.getSelectedRows();
            if(selection.length == 1) {
                int row = table.convertRowIndexToModel(selection[0]);
                if (row != -1) {
                    producto = modelTable.getValue(row);
                    //System.out.println(">> " + producto.getCategoria() + " " + producto.getNombre() + " " + producto.getNota());
                }
            }
        }*/

        return producto;
    }

    public void cargarCBMarca(){
        List<Marca> lmarca = GlobalProduct.getInstance().consultaMarca.getList();
        boolean isMar = lmarca!=null && !lmarca.isEmpty();
        detalleProducto.getCBMarca().setEnabled(isMar);
        if(isMar){
            for(Marca mc: lmarca)
                detalleProducto.getDCBMarca().addElement(mc);
        }
    }

    public void addCBCarga(Marca marca){
        if(marca!=null)
            detalleProducto.getDCBMarca().addElement(marca);
    }
}
