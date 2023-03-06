package view.frame.producto.importar;

public class ColumnSelected {
    private String nameColumn;
    private int index;

    public ColumnSelected(String nameColumn, int index){
        this.nameColumn = nameColumn;
        this.index = index;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return nameColumn;
    }
}
