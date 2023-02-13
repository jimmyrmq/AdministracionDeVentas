package view.frame.ui.themes.dark;

import com.djm.ui.themes.table.ITableUI;

import java.awt.Color;
import java.awt.Font;

public class TableUI implements ITableUI {
    @Override
    public Color getBackground() {
        return GlobalDark.colBack;
    }

    @Override
    public Color getForeground() {
        return GlobalDark.colFore;
    }

    @Override
    public Color getBackgroundSelected() {
        return GlobalDark.colorSelected;
    }

    @Override
    public Color getForegroundSelected() {
        return new Color(253,254,252);
    }

    @Override
    public Color getBackgroundHeader() {
        return new Color(38, 36, 36);
    }

    @Override
    public Color getForegroundHeader() {
        return GlobalDark.colFore;
    }

    @Override
    public Font getFont() {
        return GlobalDark.font;
    }
}
