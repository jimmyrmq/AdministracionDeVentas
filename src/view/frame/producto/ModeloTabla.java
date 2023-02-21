package view.frame.producto;
/**
 * Javier Abellán, 26 Oct 2003
 *
 * ModeloTabla.java
 *
 * Modelo de tabla para el ejmplo de uso del JTable
 */
import model.Categoria;
import model.Marca;
import model.Producto;
import util.SystemProperties;
import view.frame.ui.component.EtiquetaComponent;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.LinkedList;

/**
 * Modelo de personas. Cada fila es una persona y las columnas son los datos
 * de la persona.
 * Implementa TableModel y dos métodos para añadir y eliminar Personas del
 * modelo 
 */
public class ModeloTabla<E> implements TableModel {//extends DefaultTableModel {

    /** Lista con los datos. Cada elemento de la lista es una instancia de
     * Producto */
    private LinkedList<E> datos = new LinkedList();

    /** Lista de suscriptores. El JTable será un suscriptor de este modelo de
     * datos */
    private LinkedList listeners = new LinkedList();

    private Class[] columnClass = {String.class,String.class,Marca.class,Categoria.class, Integer.class, EtiquetaComponent.class};

    private SystemProperties sp = SystemProperties.getInstance();
    private String [] columnName ={sp.getValue("productos.label.codigo"),
            sp.getValue("productos.label.nombre"),
            sp.getValue("productos.label.marca"),
            sp.getValue("productos.label.categoria"),
            sp.getValue("productos.label.stock"),
            ""};
    private final int COLUMN_COUNT = 6;
    /** Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     *
     */
    public int getColumnCount() {
        // Devuelve el número de columnas del modelo, que coincide con el
        // número de datos que tenemos de cada persona.
        return COLUMN_COUNT;
    }

    /** Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     *
     */
    public int getRowCount() {
        return datos.size();
    }

    /** Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param	rowIndex	the row whose value is to be queried
     * @param	columnIndex 	the column whose value is to be queried
     * @return	the value Object at the specified cell
     *
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto aux;

        // Se obtiene la persona de la fila indicada
        aux = (Producto)(datos.get(rowIndex));

        // Se obtiene el campo apropiado según el valor de columnIndex
        switch (columnIndex){
            case 0:
                return aux.getCodigo();
            case 1:
                return aux.getNombre();
            case 2:
                return aux.getMarca().toString();
            case 3:
                return aux.getCategoria().toString();
            case 4:
                return aux.isNoRequiereStock()?"-":aux.getStock();
            case 5:
                return aux.isDisponible()+"@"+aux.isNoRequiereStock()+"@"+aux.getStock()+"@"+aux.getStockCritico();//
            default:
                return null;
        }
    }

    /**
     * Borra del modelo la persona en la fila indicada
     */
    public void removeRow(int fila){
        // Se borra la fila
        datos.remove(fila);

        TableModelEvent evento = new TableModelEvent (this, fila, fila,
                TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);

        avisaSuscriptores (evento);
    }

     public void addProduct (E newProduct) {
        // Añade la persona al modelo 
        datos.add (newProduct);

        // Avisa a los suscriptores creando un TableModelEvent...
        TableModelEvent evento = new TableModelEvent (this, this.getRowCount()-1,
                this.getRowCount()-1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);

        // ... y avisando a los suscriptores
        avisaSuscriptores (evento);
    }

    public void editProduct(E editProduct, int row){
        if(row!=-1) {
            Producto prod = (Producto) editProduct;
            //DefaultTableModel model = (DefaultTableModel)GlobalProduct.getInstance().table.getModel();
            setValueAt(prod.getCodigo(), row, 0);
            setValueAt(prod.getNombre(), row, 1);
            setValueAt(prod.getMarca(), row, 2);
            setValueAt(prod.getCategoria(), row, 3);
            setValueAt(prod.getStock(), row, 4);
            setValueAt(prod.getStock(), row, 5);
        }
    }

    /** Adds a listener to the list that is notified each time a change
     * to the data model occurs.
     *
     * @param	l		the TableModelListener
     *
     */
    public void addTableModelListener(TableModelListener l) {
        // Añade el suscriptor a la lista de suscriptores
        listeners.add (l);
    }

    /** Returns the most specific superclass for all the cell values
     * in the column.  This is used by the <code>JTable</code> to set up a
     * default renderer and editor for the column.
     *
     * @param columnIndex  the index of the column
     * @return the common ancestor class of the object values in the model.
     *
     */
    public Class getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    /** Returns the name of the column at <code>columnIndex</code>.  This is used
     * to initialize the table's column header name.  Note: this name does
     * not need to be unique; two columns in a table can have the same name.
     *
     * @param	columnIndex	the index of the column
     * @return  the name of the column
     *
     */
    public String getColumnName(int columnIndex) {
        return columnName[columnIndex];
    }

    /** Returns true if the cell at <code>rowIndex</code> and
     * <code>columnIndex</code>
     * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
     * change the value of that cell.
     *
     * @param	rowIndex	the row whose value to be queried
     * @param	columnIndex	the column whose value to be queried
     * @return	true if the cell is editable
     * @see #setValueAt
     *
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Permite que la celda sea editable.
        return false;
    }

    /** Removes a listener from the list that is notified each time a
     * change to the data model occurs.
     *
     * @param	l		the TableModelListener
     *
     */
    public void removeTableModelListener(TableModelListener l) {
        // Elimina los suscriptores.
        listeners.remove(l);
    }

    /** Sets the value in the cell at <code>columnIndex</code> and
     * <code>rowIndex</code> to <code>aValue</code>.
     *
     * @param	aValue		 the new value
     * @param	rowIndex	 the row whose value is to be changed
     * @param	columnIndex 	 the column whose value is to be changed
     * @see #getValueAt
     * @see #isCellEditable
     *
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        // Obtiene la persona de la fila indicada
        Producto aux;
        aux = (Producto)(datos.get(rowIndex));

        // Cambia el campo de Producto que indica columnIndex, poniendole el 
        // aValue que se nos pasa.
        switch (columnIndex){
            case 0:
                aux.setCodigo ((String)aValue);
                break;
            case 1:
                aux.setNombre ((String) aValue);
                break;
            case 2:
                aux.setMarca ((Marca)aValue);
                break;
            case 3:
                aux.setCategoria ((Categoria) aValue);
                break;
            case 4:
                aux.setStock ((Integer) aValue);
                break;
            default:
                break;
        }

        // Avisa a los suscriptores del cambio, creando un TableModelEvent ...
        TableModelEvent evento = new TableModelEvent (this, rowIndex, rowIndex,
                columnIndex);

        // ... y pasándoselo a los suscriptores.
        avisaSuscriptores (evento);
    }

    /**
     * Pasa a los suscriptores el evento.
     */
    private void avisaSuscriptores (TableModelEvent evento) {
        int i;

        // Bucle para todos los suscriptores en la lista, se llama al metodo
        // tableChanged() de los mismos, pasándole el evento.
        for (i=0; i<listeners.size(); i++)
            ((TableModelListener)listeners.get(i)).tableChanged(evento);
    }

    public E getValue(int row){
        E producto = (E) datos.get(row);

        return producto;
    }

    public void clearTable(){
        int rowCount = getRowCount();
        if (rowCount> 0) {
            for (int i = rowCount - 1; i >=0; i--) {
                removeRow(i);
            }
        }
    }

}