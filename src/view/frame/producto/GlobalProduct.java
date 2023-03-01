package view.frame.producto;

import model.Categoria;
import model.Marca;
import model.Producto;
import util.SystemProperties;
import view.frame.categoria.PCategoria;
import view.frame.main.FrameMain;
import view.frame.main.LoadData;
import view.frame.ui.component.Table;

import java.awt.Cursor;
import java.util.List;

public class GlobalProduct {
    protected Table table;
    protected int indexTableProductSelect = -1;

    protected Producto producto;
    protected DetalleProducto detalleProducto;
    protected PCategoria pCategoria;
    protected PTablaProducto pTablaProducto;
    protected final ActionListenerProduct actionListener = new ActionListenerProduct();
    private static GlobalProduct globalProduct;

    private GlobalProduct(){}

    public static GlobalProduct getInstance(){
        if(globalProduct == null)
            globalProduct = new GlobalProduct();

        return globalProduct;
    }

    protected void init(){
        Thread t = new Thread(()-> {
            FrameMain.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            LoadData.getInstance().loadAll();

            detalleProducto.init();
            pCategoria.init();
            pTablaProducto.fillTableProduct();

            FrameMain.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        });
        t.start();

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
        List<Marca> lmarca = LoadData.getInstance().getConsultaMarca().getList();
        boolean isMar = lmarca!=null && !lmarca.isEmpty();
        detalleProducto.getCBMarca().setEnabled(isMar);
        detalleProducto.getDCBMarca().removeAllElements();

        SystemProperties sp = SystemProperties.getInstance();
        Marca mc1 = new Marca();
        mc1.setDesrcripcion(sp.getValue("label.ninguno"));
        detalleProducto.getDCBMarca().addElement(mc1);

        if(isMar){
            for(Marca mc: lmarca)
                detalleProducto.getDCBMarca().addElement(mc);
        }
    }

    public void addCBCarga(Marca marca,boolean edit){
        if(marca!=null) {
            if(edit){
                int sz = detalleProducto.getDCBMarca().getSize();
                Marca mccb;
                cont:for(int i = 0 ; i < sz ;i++) {
                    mccb = detalleProducto.getDCBMarca().getElementAt(i);
                    if((marca.getID() !=null && mccb.getID()!=null) &&
                        (marca.getID().intValue() == mccb.getID().intValue())) {
                        mccb.setDesrcripcion(marca.getDesrcripcion());
                        break cont;
                    }
                }
            }
            else
                detalleProducto.getDCBMarca().addElement(marca);
        }
    }

    public void addCBCategoria(Categoria categoria,boolean edit){
        if(categoria!=null) {
            if(edit){
                int sz = detalleProducto.getDCBCategoria().getSize();
                Categoria catcb;
                cont:for(int i = 0 ; i < sz ;i++) {
                    catcb = detalleProducto.getDCBCategoria().getElementAt(i);
                    if((categoria.getID() !=null && catcb.getID()!=null) &&
                        (categoria.getID().intValue() == catcb.getID().intValue())) {
                        catcb.setDesrcripcion(categoria.getDesrcripcion());
                        pCategoria.getPanelList().updateData(categoria);
                        break cont;
                    }
                }
            }
            else {
                detalleProducto.getDCBCategoria().addElement(categoria);
                pCategoria.getPanelList().addCategoria(categoria);
            }
        }
    }

    public void updateCategoria(Categoria categoria){
        pCategoria.getPanelList().updateData(categoria);
    }

    public void deleteCategoria(Categoria categoria){
        pCategoria.getPanelList().delCategoria(categoria);
    }
    public void deleteMarca(Marca marca){
        int sz = detalleProducto.getDCBMarca().getSize();
        Marca mccb;
        cont:for(int i = 0 ; i < sz ;i++) {
            mccb = detalleProducto.getDCBMarca().getElementAt(i);
            if((marca.getID() !=null && mccb.getID()!=null) &&
                (marca.getID().intValue() == mccb.getID().intValue())) {
                detalleProducto.getDCBMarca().removeElementAt(i);
                break cont;
            }
        }
    }
    public void reorganizarListCat(){
        pCategoria.repaintPanel();
    }
}
