package view.frame.ui.themes;

import com.djm.ui.themes.ITheme;

import java.awt.Color;
import java.awt.Font;

public class Dark implements ITheme {
    //Button
    private Color colBack = new Color(60,63,65);//47, 47, 47);
    private Color colBackButton = new Color(37, 37, 37);
    private Color colBackAction = new Color(159, 158, 158);
    private Color colSelected =  new Color(30,104,179);//70,106,146);//98, 165, 206);//(94, 154, 85);//
    private Color colFore = new Color(192, 194, 203);//165,165,165);//
    private Color colBorder = new Color(97, 100, 101);
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
    private Color  colorChekSelected = new Color(168,168,168);
    private Color  colorChekDisabled = new Color(112, 110, 110);

    @Override
    public Color getBackground() {
        return colBack;
    }
    @Override
    public Color getBackgroundButton() {
        return colBackButton;
    }
    @Override
    public Color getBackgroundButtonAction() {
        return colBackAction;
    }

    @Override
    public Color getColorImageButton() {
        return new Color(251, 252, 250);
    }

    @Override
    public Color getBackgroundButtonSelected() {
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
        return new Font("Segoe UI",0,12);
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

    @Override
    public String pathIcon() {
        return "icon/";
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
