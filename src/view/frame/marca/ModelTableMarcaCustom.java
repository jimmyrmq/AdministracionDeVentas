package view.frame.marca;

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

public class ModelTableMarcaCustom implements IModelTableCustom<Marca> {

    private LinkedList<Marca> datos = new LinkedList();
    private List<ObjectModelTable> listObject;
    private final SystemProperties sp = SystemProperties.getInstance();
    private String [] columnName ={sp.getValue("label.descripcion")};
    private Class[] columnClass = {String.class};

    @Override
    public Class[] getColumnClass() {
        return columnClass;
    }

    @Override
    public String[] getColumnName() {
        return columnName;
    }

    @Override
    public LinkedList<Marca> getListData() {
        return this.datos;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Marca aux = datos.get(rowIndex);

        // Se obtiene el campo apropiado según el valor de columnIndex
        if (columnIndex == 0) {
            return aux.getDesrcripcion();
        }
        else return null;
    }

    @Override
    public void editObject(Marca object, int row) {
        if(listObject!=null)
            listObject.clear();
        else
            listObject = new ArrayList<>();

        if(row!=-1) {
            //DefaultTableModel model = (DefaultTableModel)GlobalProduct.getInstance().table.getModel();
            listObject.add(new ObjectModelTable(object.getDesrcripcion(),0));
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

        Marca aux = datos.get(rowIndex);

        if(columnIndex == 0) {
            aux.setDesrcripcion((String) aValue);
        }
    }

    @Override
    public Marca getValue(int row){
        Marca item = datos.get(row);
        return item;
    }

    @Override
    public List<ObjectModelTable> getValueObject(){
        return listObject;
    }

    @Override
    public int[] getWidthCell() {
        int []anchoColum={260};
        return anchoColum;
    }
}
