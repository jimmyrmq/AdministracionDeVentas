package view.frame.ui.themes.dark;

import com.djm.ui.themes.panel.IPanelUI;

import java.awt.Color;
import java.awt.Font;

public class PanelUI implements IPanelUI {
    //private Color colBack = new Color(60,63,65);//47, 47, 47);
    //private Color colFore = new Color(192, 194, 203);//165,165,165);//
    private Color colBorder = new Color(97, 100, 101);
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
        return colBorder;
    }

    @Override
    public Font getFont() {
        return GlobalDark.font;
    }

    @Override
    public Color getColorBackgoundControl() {
        return new Color(65,68,70);
    }
}
