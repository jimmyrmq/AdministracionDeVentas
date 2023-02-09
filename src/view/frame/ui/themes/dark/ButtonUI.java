package view.frame.ui.themes.dark;

import com.djm.ui.themes.button.IButtonUI;

import java.awt.Color;
import java.awt.Font;

public class ButtonUI implements IButtonUI {
    private Color colBack = new Color(37, 37, 37);
    private Color colBackEntered = new Color(55, 55, 55);
    private Color colBackDisable = new Color(122, 121, 121);
    private Color colBackAction = new Color(159, 158, 158);
    private Color colSelected =  new Color(38, 56, 66);//70,106,146);//98, 165, 206);//(94, 154, 85);//
    private Color colBorder = new Color(97, 100, 101);
    private Color colBorderDisabled = new Color(121, 125, 126);
    private Color colForeKey = new Color(140, 140, 140);//Color text key button
    private Color colForeKeySelected = new Color(217, 214, 214);//Color selected text keybutton

    @Override
    public Color getBackground() {
        return colBack;
    }

    @Override
    public Color getBackgroundAction() {
        return colBackAction;
    }

    @Override
    public Color getBackgroundDisabled() {
        return colBackDisable;
    }

    @Override
    public Color getBackgroundSelected() {
        return colSelected;
    }

    @Override
    public Color getBackgroundMouseEntered() {
        return colBackEntered;
    }

    @Override
    public Color getColorImage() {
        return GlobalDark.colFore;
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
    public Color getColorSelected() {
        return new Color(30,104,179);
    }

    @Override
    public Color getColorBorderSelected() {
        return GlobalDark.colorBorderFocus;
    }

    @Override
    public Color getColorBorderDisabled() {
        return colBorderDisabled;
    }

    @Override
    public Font getFont() {
        return null;
    }

    @Override
    public Color getColorTextKey() {
        return colForeKey;
    }

    @Override
    public Color getColorTexteySelected() {
        return colForeKeySelected;
    }

    @Override
    public String pathIcon() {
        return "icon/";
    }
}
