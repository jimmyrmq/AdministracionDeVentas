package view.frame.ui;

import model.Categoria;
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
            Categoria cat = new Categoria();
            cat.setID(-1);
            cat.setDesrcripcion("Sin Categoria");
            cat.setColor(Color.RED);
            component = new CategoriaUI(cat);
        }
        return component;
    }
}
