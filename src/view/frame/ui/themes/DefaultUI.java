package view.frame.ui.themes;

import com.djm.ui.themes.ITheme;

import java.awt.Color;
import java.awt.Font;

public class DefaultUI implements ITheme {

    @Override
    public Color getBackground() {
        return new Color(240, 240, 240);
    }

    @Override
    public Color getBackgroundButton() {
        return new Color(211, 217, 220);
    }

    @Override
    public Color getBackgroundButtonAction() {
        return new Color(247,247,247);//158, 162, 164);
    }

    @Override
    public Color getColorImageButton() {
        return new Color(31, 31, 31);
    }

    @Override
    public Color getBackgroundButtonSelected() {
        return new Color(121,182,249);//106, 106, 201);//159, 158, 158
    }

    @Override
    public Color getForeground() {
        return new Color(0,0,0);
    }

    @Override
    public Color getColorBorder() {
        return new Color(139, 139, 139);
    }

    @Override
    public Color getColorBorderButton() {
        return new Color(134, 134, 154);
    }

    @Override
    public Font getFontLabel() {
        return new Font("Segoe UI",0,12);
    }

    @Override
    public Color getColorTextKeyButton() {
        return new Color(140, 140, 140);
    }

    @Override
    public Color getColorTextButtonKeySelected() {
        return new Color(217, 214, 214);
    }

    @Override
    public Color getBackgroundField() {
        return null;
    }

    @Override
    public Color getColorCaretField() {
        return null;
    }

    @Override
    public Color getColorTextEnabled() {
        return null;
    }

    @Override
    public Color getBackgroundFieldEnabled() {
        return null;
    }

    @Override
    public Color getColorBorderField() {
        return new Color(173, 173, 173);
    }

    @Override
    public Color getColorBorderFocusField() {
        return new Color(72,216,251);
    }

    @Override
    public String pathIcon() {
        return "icon/";
    }

    @Override
    public Color getColorChekSelected() {
        return new Color(79, 92, 100);
    }

    @Override
    public Color getColorChekDisabled() {
        return new Color(136, 151, 159);
    }
}
