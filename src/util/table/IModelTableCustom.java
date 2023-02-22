package util.table;

import java.util.LinkedList;
import java.util.List;

public interface IModelTableCustom<E>{
    Class [] getColumnClass();
    String [] getColumnName();
    LinkedList<E> getListData();
    Object getValueAt(int rowIndex, int columnIndex);
    void editObject(E object, int row);
    int getCountCoulumn();
    Class getColumnClass(int columnIndex);
    String getColumnName(int columnIndex);
    void setValueAt(Object aValue,int rowIndex, int columnIndex);
    E getValue(int row);
    List<ObjectModelTable> getValueObject();

    int [] getWidthCell();
}
