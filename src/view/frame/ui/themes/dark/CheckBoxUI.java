package view.frame.ui.themes.dark;

import com.djm.ui.themes.checkbox.ICheckBoxUI;

import java.awt.Color;

public class CheckBoxUI implements ICheckBoxUI {
    private Color  colorChekSelected = new Color(168,168,168);
    private Color  colorChekDisabled = new Color(112, 110, 110);
    private Color  colorBorder= new Color(154, 154, 154);
    private Color  colorBorderFocus = new Color(72,216,251);
    @Override
    public Color getBackgroundAction() {
        return GlobalDark.colBack;
    }

    @Override
    public Color getBackground() {
        return GlobalDark.colBack;
    }

    @Override
    public Color getForeground() {
        return GlobalDark.colFore;
    }

    @Override
    public Color getColorBorder() {
        return colorBorder;
    }

    @Override
    public Color getColorBorderFocus() {
        return colorBorderFocus;
    }

    @Override
    public Color getColorChekSelected() {
        return colorChekSelected;
    }

    @Override
    public Color getColorChekDisabled() {
        return colorChekDisabled;
    }
}
