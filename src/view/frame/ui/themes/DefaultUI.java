package view.frame.ui.themes;

import com.djm.ui.themes.button.ButtonDefaultUI;
import com.djm.ui.themes.button.IButtonUI;
import com.djm.ui.themes.checkbox.CheckBoxDefaultUI;
import com.djm.ui.themes.checkbox.ICheckBoxUI;
import com.djm.ui.themes.global.ITheme;
import com.djm.ui.themes.panel.IPanelUI;
import com.djm.ui.themes.panel.PanelDefaultUI;
import com.djm.ui.themes.text.ITextUI;
import com.djm.ui.themes.text.TextDefaultUI;


public class DefaultUI implements ITheme {
    @Override
    public IButtonUI getButtonUI() {
        return new ButtonDefaultUI();
    }

    @Override
    public ITextUI getTextUI() {
        return new TextDefaultUI();
    }

    @Override
    public IPanelUI getPanelUI() {
        return new PanelDefaultUI();
    }

    @Override
    public ICheckBoxUI getCheckBox() {
        return new CheckBoxDefaultUI();
    }
/*
    @Override
    public Color getBackground() {
        return new Color(242, 242, 242);
    }

    @Override
    public Color getBackgroundButton() {
        return  new Color(253, 255, 254);
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
        return new Color(194, 194, 194);
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
        return new Color(140, 140, 140);
    }

    @Override
    public Color getBackgroundField() {
        return new Color(255,255,255);
    }

    @Override
    public Color getColorCaretField() {
        return new Color(40,38,40);
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
    }*/
}
