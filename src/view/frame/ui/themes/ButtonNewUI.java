package view.frame.ui.themes;

import com.djm.ui.themes.button.IButtonUI;

import java.awt.Color;
import java.awt.Font;

public class ButtonNewUI implements IButtonUI {
    @Override
    public Color getBackground() {
        return new Color(40, 53, 234);
    }

    @Override
    public Color getBackgroundAction() {
        return new Color(89, 122, 255);
    }

    @Override
    public Color getBackgroundDisabled() {
        return GlobalUI.getInstance().getTheme().getPanelUI().getBackground();
    }

    @Override
    public Color getBackgroundSelected() {//Cuando hace clic
        return new Color(114, 140, 243);
    }

    @Override
    public Color getForegroundSelected() {
        return null;
    }

    @Override
    public Color getForegroundDisabled() {
        return new Color(114, 114, 122);
    }

    @Override
    public Color getBackgroundMouseEntered() {
        return new Color(73, 109, 255);
    }

    @Override
    public Color getColorImage() {
        return Color.WHITE;
    }

    @Override
    public Color getForeground() {
        return Color.WHITE;
    }

    @Override
    public Color getColorBorder() {
        return new Color(58, 61, 255);
    }

    @Override
    public Color getColorSelected() {
        return null;
    }

    @Override
    public Color getColorBorderSelected() {
        return new Color(58, 74, 255);
    }

    @Override
    public Color getColorBorderDisabled() {
        return new Color(114, 114, 122);
    }

    @Override
    public Font getFont() {
        return new Font("Segoe UI",0,11);
    }

    @Override
    public Color getColorTextKey() {
        return null;
    }

    @Override
    public Color getColorTexteySelected() {
        return null;
    }

    @Override
    public String pathIcon() {
        return "icon/";
    }
}
