package view.frame.themes;

import com.djm.ui.themes.ITheme;

import java.awt.Color;
import java.awt.Font;

public class Dark implements ITheme {
    //Button
    private Color colBack = new Color(47, 47, 30);
    private Color colBackButton = new Color(37, 37, 37);
    private Color colBackAction = new Color(159, 158, 158);
    private Color colFore = new Color(238, 243, 248);
    private Color colSelected =  new Color(94, 154, 85);
    private Color colBorder = new Color(154, 154, 154);
    private Color colBorderButton = new Color(89,94,87);
    private Color colForeKey = new Color(140, 140, 140);//Color text key button
    private Color colForeKeySelected = new Color(217, 214, 214);//Color selected text keybutton

    //Texto
    private Color backgroundField = new Color(38, 36, 36);
    private Color colorCaretField = new Color(0,0,0);
    private Color backgroundFieldEnabled = new Color(38, 36, 36);
    private Color colorTextEnabled = new Color(70,70,70);
    private Color  colorBorderField= new Color(154, 154, 154);
    private Color  colorBorderFocusField = new Color(72,216,251);

    @Override
    public Color getBackground() {
        return colBack;
    }
    @Override
    public Color getBackgroundButton() {
        return colBackButton;
    }
    @Override
    public Color getBackgroundAction() {
        return colBackAction;
    }

    @Override
    public Color getBackgroundSelection() {
        return colSelected;
    }

    @Override
    public Color getForeground() {
        return colFore;
    }

    public Color getColorBorder() {
        return colBorder;
    }
    public Color getColorBorderButton() {
        return colBorderButton;
    }

    @Override
    public Font getFontLabel() {
        return new Font("Tahoma",0,12);
    }

    @Override
    public Color getColorTextKeyButton() {
        return colForeKey;
    }

    @Override
    public Color getColorTextButtonKeySelected() {
        return colForeKeySelected;
    }

    @Override
    public Color getBackgroundField() {
        return backgroundField;
    }

    @Override
    public Color getColorCaretField() {
        return colorCaretField;
    }

    @Override
    public Color getColorTextEnabled() {
        return colorTextEnabled;
    }

    @Override
    public Color getBackgroundFieldEnabled() {
        return backgroundFieldEnabled;
    }

    @Override
    public Color getColorBorderField() {
        return colorBorderField;
    }

    @Override
    public Color getColorBorderFocusField() {
        return colorBorderFocusField;
    }
}
