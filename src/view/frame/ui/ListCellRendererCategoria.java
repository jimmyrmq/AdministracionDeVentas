package view.frame.ui;

import view.frame.ui.component.CategoriaUI;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;

public class ListCellRendererCategoria implements ListCellRenderer {

    public Component getListCellRendererComponent(JList jlist, Object value, int cellIndex,
                                                  boolean isSelected, boolean cellHasFocus){
        Component component;
        if (value != null && value instanceof CategoriaUI){
            component = (Component) value;
        }
        else{
            component = new CategoriaUI(-1,"Sin Categoria",Color.RED);
        }
        return component;
    }
}
