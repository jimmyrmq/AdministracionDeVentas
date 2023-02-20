package view.frame.ui.component;

import util.SystemProperties;

import java.awt.Color;

public enum TipoEtiqueta {
    NONE("",new Color(42, 66, 183)),
    Disponible(SystemProperties.getInstance().getValue("productos.etiquetacomponent.disponible"),new Color(82, 176, 48)),
    NoDisponible(SystemProperties.getInstance().getValue("productos.etiquetacomponent.no_disponible"),new Color(239, 9, 9)),
    StockCritico(SystemProperties.getInstance().getValue("productos.etiquetacomponent.stock_critico"),new Color(225, 43, 43)),
    SinStock(SystemProperties.getInstance().getValue("productos.etiquetacomponent.sin_stock"),new Color(239, 9, 9));
    private String title;
    private Color color;
    TipoEtiqueta(String title,Color color){
        this.title = title;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public Color getColor() {
        return color;
    }
}
