package view.frame.categoria;

import model.Categoria;
import model.Marca;
import util.SystemProperties;
import util.table.IModelTableCustom;
import util.table.ObjectModelTable;

import javax.swing.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModelTableCategoriaCustom  implements IModelTableCustom<Categoria> {

    private LinkedList<Categoria> datos = new LinkedList();
    private List<ObjectModelTable> listObject;
    private final SystemProperties sp = SystemProperties.getInstance();
    private String [] columnName ={sp.getValue("label.descripcion"),sp.getValue("label.color")};
    private Class[] columnClass = {String.class, JLabel.class};

    @Override
    public Class[] getColumnClass() {
        return columnClass;
    }

    @Override
    public String[] getColumnName() {
        return columnName;
    }

    @Override
    public LinkedList<Categoria> getListData() {
        return datos;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Categoria aux = datos.get(rowIndex);

        // Se obtiene el campo apropiado según el valor de columnIndex
        if (columnIndex == 0) {
            return aux.getDesrcripcion();
        }
        if (columnIndex == 1) {
            return aux.getColor();
        }
        else return null;
    }

    @Override
    public void editObject(Categoria object, int row) {

        if(listObject!=null)
            listObject.clear();
        else
            listObject = new ArrayList<>();

        if(row!=-1) {
            //DefaultTableModel model = (DefaultTableModel)GlobalProduct.getInstance().table.getModel();
            listObject.add(new ObjectModelTable(object.getDesrcripcion(),0));
            listObject.add(new ObjectModelTable(object.getColor(),1));
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

        Categoria aux = datos.get(rowIndex);

        if(columnIndex == 0) {
            aux.setDesrcripcion((String) aValue);
        }
        else if(columnIndex == 1) {
            aux.setColor((Color) aValue);
        }
    }

    @Override
    public Categoria getValue(int row) {
        Categoria item = datos.get(row);
        return item;
    }

    @Override
    public List<ObjectModelTable> getValueObject() {
        return listObject;
    }

    @Override
    public int[] getWidthCell() {
        int []anchoColum={260,120};
        return anchoColum;
    }
}
