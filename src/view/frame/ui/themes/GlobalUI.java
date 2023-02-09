package view.frame.ui.themes;

import com.djm.ui.themes.global.ITheme;

public class GlobalUI {

    private static GlobalUI globalUI = null;
    private ITheme theme = new DefaultUI();
    private GlobalUI(){}

    public static GlobalUI getInstance(){
        if(globalUI == null)
            globalUI = new GlobalUI();
        return globalUI;
    }

    public void setTheme(ITheme theme) {
        this.theme = theme;
    }

    public ITheme getTheme() {
        return theme;
    }

}
