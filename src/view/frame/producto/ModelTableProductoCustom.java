package view.frame.producto;

import model.Categoria;
import model.Marca;
import model.Producto;
import util.SystemProperties;
import util.table.IModelTableCustom;
import util.table.ObjectModelTable;
import view.frame.ui.component.EtiquetaComponent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModelTableProductoCustom implements IModelTableCustom<Producto> {

    private LinkedList<Producto> datos = new LinkedList();
    private List<ObjectModelTable> listObject;
    private final SystemProperties sp = SystemProperties.getInstance();
    private String [] columnName ={sp.getValue("productos.label.codigo"),
            sp.getValue("productos.label.nombre"),
            sp.getValue("productos.label.marca"),
            sp.getValue("productos.label.categoria"),
            sp.getValue("productos.label.stock"),
            ""};
    private Class[] columnClass = {String.class,String.class, Marca.class, Categoria.class, Integer.class, EtiquetaComponent.class};

    @Override
    public Class[] getColumnClass() {
        return columnClass;
    }

    @Override
    public String[] getColumnName() {
        return columnName;
    }

    @Override
    public LinkedList<Producto> getListData() {
        return this.datos;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(datos.size() > 0) {
            try {
                Producto aux = datos.get(rowIndex);

                // Se obtiene el campo apropiado según el valor de columnIndex
                if (columnIndex == 0) {
                    return aux.getCodigo();
                } else if (columnIndex == 1) {
                    return aux.getNombre();
                } else if (columnIndex == 2) {
                    return aux.getMarca()!=null?aux.getMarca().toString():null;
                } else if (columnIndex == 3) {
                    return aux.getCategoria()!=null?aux.getCategoria().toString():null;
                } else if (columnIndex == 4) {
                    return aux.isNoRequiereStock() ? "-" : aux.getStock();
                } else if (columnIndex == 5) {
                    return aux.isDisponible() + "@" + aux.isNoRequiereStock() + "@" + aux.getStock() + "@" + aux.getStockCritico();
                } else return null;
            } catch (IndexOutOfBoundsException exc) {
                System.out.println("rowIndex: " + rowIndex + ", columnIndex:" + columnIndex + " -> " + exc);
                return null;
            }
        }else return null;
    }
    @Override
    public void editObject(Producto object, int row) {
        if(listObject!=null)
            listObject.clear();
        else
            listObject = new ArrayList<>();

        if(row!=-1) {
            String col5 = object.isDisponible()+"@"+object.isNoRequiereStock()+"@"+object.getStock()+"@"+object.getStockCritico();

            //DefaultTableModel model = (DefaultTableModel)GlobalProduct.getInstance().table.getModel();
            listObject.add(new ObjectModelTable(object.getCodigo(),0));
            listObject.add(new ObjectModelTable(object.getNombre(),1));
            listObject.add(new ObjectModelTable(object.getMarca(),2));
            listObject.add(new ObjectModelTable(object.getCategoria(),3));
            listObject.add(new ObjectModelTable(object.getStock(),4));
            listObject.add(new ObjectModelTable(col5,5));
        }

    }

    @Override
    public int getCountCoulumn() {
        return columnName.length;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnName[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        Producto aux = datos.get(rowIndex);

        if(columnIndex == 0) {
            aux.setCodigo((String) aValue);
        }
        else if(columnIndex == 1) {
           aux.setNombre ((String) aValue);
        }
        else if(columnIndex == 2) {
           aux.setMarca ((Marca)aValue);
        }
        else if(columnIndex == 3) {
           aux.setCategoria ((Categoria) aValue);
        }
        else if(columnIndex == 4) {
            aux.setStock((Integer) aValue);
        }
    }

    @Override
    public Producto getValue(int row){
        Producto item = datos.get(row);
        return item;
    }

    @Override
    public List<ObjectModelTable> getValueObject(){
        return listObject;
    }

    @Override
    public int[] getWidthCell() {
        int []anchoColum={80,140,140,150,40,90};
        return anchoColum;
    }

    /*public LinkedList<Producto> getDatos() {
        return datos;
    }*/
}
