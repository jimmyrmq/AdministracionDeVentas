package view.frame.ui.themes.dark;

import com.djm.ui.themes.button.IButtonUI;
import com.djm.ui.themes.checkbox.ICheckBoxUI;
import com.djm.ui.themes.global.ITheme;
import com.djm.ui.themes.panel.IPanelUI;
import com.djm.ui.themes.text.ITextUI;

public class Dark implements ITheme {
    @Override
    public IButtonUI getButtonUI() {
        return new ButtonUI();
    }

    @Override
    public ITextUI getTextUI() {
        return new TextUI();
    }

    @Override
    public IPanelUI getPanelUI() {
        return new PanelUI();
    }

    @Override
    public ICheckBoxUI getCheckBox() {
        return new CheckBoxUI();
    }
}
