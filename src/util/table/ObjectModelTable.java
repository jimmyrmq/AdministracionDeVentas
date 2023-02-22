package util.table;

public class ObjectModelTable {
    private Object object;
    private int column;

    public ObjectModelTable(Object object, int column) {
        this.object = object;
        this.column = column;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
