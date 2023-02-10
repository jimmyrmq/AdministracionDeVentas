package view.frame.ui.component;

import view.frame.ui.component.border.RoundedCornerBorder;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ComboBox<E> extends JComboBox<E> {
    public ComboBox() {
        //UIManager.put("ComboBox.border", BorderFactory.createLineBorder(Color.red));
        UIManager.put("ComboBox.selectionBackground", GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        UIManager.put("ComboBox.selectionForeground", GlobalUI.getInstance().getTheme().getPanelUI().getForeground());
        UIManager.put("ComboBox.font", GlobalUI.getInstance().getTheme().getPanelUI().getFont());

        setPreferredSize(new Dimension(140,21));
        setForeground(GlobalUI.getInstance().getTheme().getPanelUI().getForeground());
        setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        setBorder(new RoundedCornerBorder());//new EmptyBorder(2, 2, 2, 2));
        setUI(new ComboBoxUI());
        //getEditor().getEditorComponent().setBackground(Color.BLACK);

        ((JTextField) getEditor().getEditorComponent()).setOpaque(false);

        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                setBorder(new EmptyBorder(3, 2, 3, 2));
                if (isSelected) {
                    c.setBackground(GlobalUI.getInstance().getTheme().getButtonUI().getColorSelected());
                    c.setForeground(GlobalUI.getInstance().getTheme().getButtonUI().getForeground());
                }else{
                    c.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
                    c.setForeground(GlobalUI.getInstance().getTheme().getPanelUI().getForeground());
                }
                return c;
            }
        });
    }

}
