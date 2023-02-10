package view.frame.ui.themes;


import com.djm.ui.themes.button.IButtonUI;
import com.djm.ui.themes.global.ITheme;
import com.djm.ui.themes.global.ManagerTextlUI;
import com.djm.ui.themes.panel.IPanelUI;
import view.frame.ui.themes.DefaultUI;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

public class LookAndFeel {
    public LookAndFeel(){
        this(null);
    }

    public LookAndFeel(ITheme theme){

        if(theme==null) {
            theme = new DefaultUI();
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        GlobalUI.getInstance().setTheme(theme);
        setTheme(theme);
    }

    /*public void getUIDefaults(String theme){

        try {
            //String ths [] = {"Metal","Nimbus","CDE/Motif","Windows","Windows Classic"};
            /*UIDefaults defaults = UIManager.getDefaults();
            Enumeration<Object> keysEnumeration = defaults.keys();
            ArrayList<Object> keysList = Collections.list(keysEnumeration);
            for (Object key : keysList) {
                System.out.println(key);
            }//*

            boolean e = false;
            cont:for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                //System.out.println(info.getName());
                if (info.getName().equals(theme)) {
                    UIManager.setLookAndFeel(info.getClassName());
                    e = true;
                    break cont;
                }
            }
            if(!e)
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            GlobalUI.getInstance().setTheme(new DefaultUI());
            setTheme(GlobalUI.getInstance().getTheme());
        } catch (Exception e) {
            System.out.println("Imposible modificar el tema visual");
        }
    }*/

    public void setTheme(ITheme theme){

        try {
            IPanelUI panelUI = theme.getPanelUI();
            Font font = panelUI.getFont();//new Font("Tahoma",0,12);

                //font = theme.getFontLabel();
                IButtonUI buttonUI = theme.getButtonUI();

                Color colBack = panelUI.getBackground();
                //Color colBackButton = theme.getBackgroundButton();
                Color colBackAction = buttonUI.getBackgroundAction();
                Color colBackSelected = buttonUI.getBackgroundSelected();
                Color colFore = panelUI.getForeground();
                /*Color cborder = theme.getColorBorder();
                Color cborderbutton = theme.getColorBorderButton();
                Color colForeTextKey = theme.getColorTextKeyButton();
                Color colForeTextKeySelect = theme.getColorTextButtonKeySelected();

                GlobalUI.getInstance().setBackgroundButton(colBackButton);//Color.RED);//new Color(211, 217, 220));
                GlobalUI.getInstance().setColorButtonSelected(colBackSelected);//Color.RED);//new Color(211, 217, 220));
                GlobalUI.getInstance().setForeground(colFore);//Color.RED);//new Color(211, 217, 220));
                GlobalUI.getInstance().setColorBorder(cborder);
                GlobalUI.getInstance().setColorBorderButton(cborderbutton);
                GlobalUI.getInstance().setColorTextButtonKey(colForeTextKey);
                GlobalUI.getInstance().setColorTextButtonKeySelected(colForeTextKeySelect);*/

                ManagerTextlUI.setTheme(theme);
                //UIManager.put("Application.useSystemFontSettings", false);

                UIManager.put("control",colBack);

                UIManager.put("Panel.background", colBack);
                UIManager.put("OptionPane.background", colBack);
                UIManager.put("OptionPane.foreground", colFore);
                UIManager.put("OptionPane.messageForeground", colFore);
                //UIManager.put("OptionPane.font", font);
                UIManager.put("OptionPane.buttonAreaBorder", null);

                UIManager.put("Label.foreground", colFore);

                UIManager.put("CheckBox.foreground", colFore);
                UIManager.put("CheckBox.background", colBack);

                /*UIManager.put("ComboBox.foreground", colFore);
                UIManager.put("ComboBox.background", colBack);
                UIManager.put("ComboBox.selectionForeground", colBackSelected);
                UIManager.put("ComboBox.selectionBackground", colBack);
                UIManager.put("ComboBox.buttonDarkShadow", colBack);
                UIManager.put("ComboBox.buttonBackground", colFore);
                UIManager.put("ComboBox.buttonHighlight",  colFore);
                UIManager.put("ComboBox.buttonShadow",     colFore);*/
                UIManager.put("ComboBox.border", BorderFactory.createEmptyBorder());

                UIManager.put("TextArea.foreground", colFore);
                UIManager.put("TextArea.background", colBack);
                UIManager.put("TextArea.caretForeground", colFore);

                UIManager.put("TextField.foreground", colFore);
                UIManager.put("TextField.background", colBack);
                UIManager.put("TextField.caretForeground", colFore);

                UIManager.put("ScrollPane.foreground",Color.red);
                UIManager.put("ScrollPane.foreground",Color.BLUE);
                UIManager.put("ScrollPane.border",BorderFactory.createLineBorder(new Color(241, 11, 11)));

                UIManager.put("List.foreground",Color.BLUE);
                UIManager.put("List.background",Color.BLUE);

                UIManager.put("TabbedPane.foreground",colFore);
                UIManager.put("TabbedPane.background",colBack);
                UIManager.put("TabbedPane.shadow",Color.RED);
                UIManager.put("TabbedPane.tabAreaBackground",Color.yellow);
                UIManager.put("TabbedPane.selected",Color.GREEN);
                UIManager.put("TabbedPane.focus",Color.BLUE);

                UIManager.put("Button.defaultButtonFollowsFocus", false);
                UIManager.put("Button.foreground", colFore);
                UIManager.put("Button.background", colBack);
                UIManager.put("Button.darkShadow", Color.RED);
                UIManager.put("Button.shadow", colBack.darker());
                UIManager.put("Button.highlight", Color.RED);//JScrolPane
                UIManager.put("Button.light", Color.YELLOW);
                UIManager.put("Button.select", colBackAction);
                UIManager.put("Button.margin", null);
                UIManager.put("Button.border", null);
                UIManager.put("Button.rollover", false);
                UIManager.put("Button.defaultButtonFollowsFocus", true);
                UIManager.put("Button.dashedRectGapWidth", null);


            UIManager.put("Label.font", font);
            UIManager.put("Combox.font", font);
            UIManager.put("TabbedPane.font",font);
            UIManager.put("Button.font", font);
            UIManager.put("CheckBox.font", font);
            UIManager.put("TextArea.font", font);
            UIManager.put("TextField.font", font);
            UIManager.put("ScrollPane.font", font);
            UIManager.put("List.font", font);


            UIManager.put("TabbedPane.tabAreaInsets", new Insets(1,1,1,1));//Afuera
            //UIManager.put("TabbedPane.tabInsets", new Insets(0,0,0,0));
            UIManager.put("TabbedPane.contentBorderInsets", new Insets(0,0,1,0));
            //UIManager.put("TabbedPane.selectedTabPadInsets", new Insets(0, 0, 0, 0));
            UIManager.put("TabbedPane.tabsOverlapBorder", true);
            UIManager.put("TabbedPane.tabInsets", new Insets(5, 0, 1, 20));

			/*UIManager.put("MenuItem.borderPainted", new Boolean(false));
            //UIManager.put("Menu.font",new Font("Tahoma",0,11));
            //UIManager.put("MenuItem.font",new Font("Tahoma",0,11));*/

        } catch (Exception e) {
            System.out.println("Imposible modificar el tema visual");
        }
    }
}
