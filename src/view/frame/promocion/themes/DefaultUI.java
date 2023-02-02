package view.frame.promocion.themes;

import com.djm.ui.themes.ITheme;

import java.awt.Color;
import java.awt.Font;

public class DefaultUI implements ITheme {

    @Override
    public Color getBackground() {
        return new Color(211, 217, 220);
    }

    @Override
    public Color getBackgroundButton() {
        return new Color(211, 217, 220);
    }

    @Override
    public Color getBackgroundButtonAction() {
        return null;
    }

    @Override
    public Color getBackgroundButtonSelected() {
        return new Color(159, 158, 158);
    }

    @Override
    public Color getForeground() {
        return new Color(0,0,0);
    }

    @Override
    public Color getColorBorder() {
        return new Color(49, 49, 56);
    }

    @Override
    public Color getColorBorderButton() {
        return new Color(134, 134, 154);
    }

    @Override
    public Font getFontLabel() {
        return null;
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
        return null;
    }

    @Override
    public Color getColorBorderFocusField() {
        return null;
    }

    @Override
    public String pathIcon() {
        return null;
    }
}
