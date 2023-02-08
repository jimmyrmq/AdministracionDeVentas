package view.frame.producto;

import model.Impuesto;

public class ImpuestoProducto {
    private Integer ID;
    private Impuesto impuesto;

    @Override
    public String toString() {
        return impuesto.getNombre();
    }
}
