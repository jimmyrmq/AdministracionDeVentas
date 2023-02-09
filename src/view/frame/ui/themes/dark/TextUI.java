package view.frame.ui.themes.dark;

import com.djm.ui.themes.text.ITextUI;

import java.awt.Color;

public class TextUI implements ITextUI {
    private Color background = new Color(38, 36, 36);
    private Color colorCaret = new Color(0,0,0);
    private Color backgroundEnabled = new Color(38, 36, 36);
    private Color colorTextEnabled = new Color(70,70,70);
    private Color  colorBorder= new Color(154, 154, 154);

    @Override
    public Color getBackground() {
        return background;
    }

    @Override
    public Color getColorCaret() {
        return colorCaret;
    }

    @Override
    public Color getColorTextDisabled() {
        return colorTextEnabled;
    }

    @Override
    public Color getBackgroundDisabled() {
        return backgroundEnabled;
    }

    @Override
    public Color getColorBorder() {
        return colorBorder;
    }

    @Override
    public Color getColorBorderFocus() {
        return GlobalDark.colorBorderFocus;
    }
}
