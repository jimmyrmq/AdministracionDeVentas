package view.frame.ui.themes;

import com.djm.ui.themes.button.IButtonUI;

import java.awt.Color;
import java.awt.Font;

public class ButtonExitUI implements IButtonUI {
    @Override
    public Color getBackground() {
        return new Color(255,68,58);
    }

    @Override
    public Color getBackgroundAction() {
        return new Color(255,98,89);
    }

    @Override
    public Color getBackgroundDisabled() {
        return null;
    }

    @Override
    public Color getBackgroundSelected() {//Cuando hace clic
        return new Color(243, 119, 114);
    }

    @Override
    public Color getBackgroundMouseEntered() {
        return new Color(255,83,73);
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
        return new Color(255,69,58);
    }

    @Override
    public Color getColorSelected() {
        return null;
    }

    @Override
    public Color getColorBorderSelected() {
        return new Color(255,69,58);
    }

    @Override
    public Color getColorBorderDisabled() {
        return  null;
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
