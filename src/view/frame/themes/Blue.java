package view.frame.themes;

import com.djm.ui.themes.ITheme;

import java.awt.Color;
import java.awt.Font;

public class Blue implements ITheme {

    private Color colBack = new Color(61, 145, 213);
    private Color colBackButton = new Color(29, 71, 101);
    private Color colBackAction = new Color(152, 199, 232);
    private Color colFore = new Color(238, 243, 248);
    private Color colSelected =  new Color(78, 78, 122);//(157, 157, 161);
    private Color colBorder =  new Color(2, 74, 124);
    private Color colBorderButton =  new Color(2, 37, 59);

    private Color colForeKey = new Color(140, 140, 140);//Color text key button
    private Color colForeKeySelected = new Color(217, 214, 214);//Color selected text keybutton
    //Text
    private Color backgroundField = new Color(4, 90, 138);
    private Color colorCaretField = new Color(0,0,0);
    private Color backgroundFieldEnabled = new Color(94, 94, 192);
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

    @Override
    public Color getColorBorder() {
        return colBorder;
    }

    @Override
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
