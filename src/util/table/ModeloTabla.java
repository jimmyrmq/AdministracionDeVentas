package util.table;
/**
 * Javier Abellán, 26 Oct 2003
 *
 * ModeloTabla.java
 *
 * Modelo de tabla para el ejmplo de uso del JTable
 */

import javax.swing.table.*;
import javax.swing.event.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Modelo de personas. Cada fila es una persona y las columnas son los datos de la persona.
 * Implementa TableModel y dos métodos para añadir y eliminar Personas del modelo
 */
public class ModeloTabla<E> implements TableModel {//extends DefaultTableModel {

    private LinkedList listeners = new LinkedList();

    private IModelTableCustom modelCustom;

    public ModeloTabla(IModelTableCustom modelCustom){
        this.modelCustom = modelCustom;
    }

    public int getColumnCount() {
        // Devuelve el número de columnas del modelo, que coincide con el
        // número de datos que tenemos de cada persona.
        return modelCustom.getCountCoulumn();
    }

    public int getRowCount() {
        return modelCustom.getListData().size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return modelCustom.getValueAt(rowIndex,columnIndex);
    }

    /**
     * Borra del modelo la persona en la fila indicada
     */
    public void removeRow(int fila){
        // Se borra la fila
        modelCustom.getListData().remove(fila);

        TableModelEvent evento = new TableModelEvent (this, fila, fila, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);

        avisaSuscriptores (evento);
    }

     public void addProduct (E object) {
        // Añade la persona al modelo 
         modelCustom.getListData().add (object);

        // Avisa a los suscriptores creando un TableModelEvent...
        TableModelEvent evento = new TableModelEvent (this, this.getRowCount()-1, this.getRowCount()-1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);

        // ... y avisando a los suscriptores
        avisaSuscriptores (evento);
    }

    public void editProduct(E editProduct, int row){
        if(row!=-1) {
            modelCustom.editObject(editProduct,row);
            List<ObjectModelTable> objList = modelCustom.getValueObject();
            for(ObjectModelTable omt:objList){
                setValueAt(omt.getObject(), row, omt.getColumn());
            }
            //DefaultTableModel model = (DefaultTableModel)GlobalProduct.getInstance().table.getModel();

        }
    }

    public void addTableModelListener(TableModelListener l) {
        listeners.add (l);
    }

    public Class getColumnClass(int columnIndex) {
        return modelCustom.getColumnClass(columnIndex);
    }


    public String getColumnName(int columnIndex) {
        return modelCustom.getColumnName(columnIndex);
    }


    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Permite que la celda sea editable.
        return false;
    }

    public void removeTableModelListener(TableModelListener l) {
        // Elimina los suscriptores.
        listeners.remove(l);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        // Obtiene la persona de la fila indicada
        modelCustom.setValueAt(aValue,rowIndex,columnIndex);

        // Avisa a los suscriptores del cambio, creando un TableModelEvent ...
        TableModelEvent evento = new TableModelEvent (this, rowIndex, rowIndex, columnIndex);

        // ... y pasándoselo a los suscriptores.
        avisaSuscriptores (evento);
    }


    private void avisaSuscriptores (TableModelEvent evento) {
        int i;

        // Bucle para todos los suscriptores en la lista, se llama al metodo
        // tableChanged() de los mismos, pasándole el evento.
        for (i=0; i<listeners.size(); i++)
            ((TableModelListener)listeners.get(i)).tableChanged(evento);
    }

    public E getValue(int row){
        E item = (E) modelCustom.getValue(row);

        return item;
    }

    public void clearTable(){
        int rowCount = getRowCount();
        if (rowCount> 0) {
            for (int i = rowCount - 1; i >=0; i--) {
                removeRow(i);
            }
        }
    }

    public IModelTableCustom getModelCustom(){
        return modelCustom;
    }
}