package view.frame.themes;

import java.awt.Color;

public class GlobalUI {
    private Color col1 = new Color(211, 217, 220);
    private Color col2 = new Color(0,0,0);
    private Color col3 = new Color(159, 158, 158);//Color Action
    private Color col4 = new Color(49, 49, 56);//Color Border
    private Color col5 = new Color(134, 134, 154);//Color Border Button
    private Color col6 = new Color(140, 140, 140);//Color text key button
    private Color col7 = new Color(217, 214, 214);//Color selected text keybutton

    private static GlobalUI globalUI = null;

    private GlobalUI(){}

    public static GlobalUI getInstance(){
        if(globalUI == null)
            globalUI = new GlobalUI();
        return globalUI;
    }

    public Color getBackgroundButton() {
        return col1;
    }

    public void setBackgroundButton(Color col1) {
        this.col1 = col1;
    }

    public Color getForeground() {
        return col2;
    }

    public void setForeground(Color col2) {
        this.col2 = col2;
    }

    public Color getColorSelected() {
        return col3;
    }

    public void setColorSelected(Color col3) {
        this.col3 = col3;
    }

    public Color getColorBorder() {
        return col4;
    }

    public void setColorBorder(Color col4) {
        this.col4 = col4;
    }

    public Color getColorBorderButton() {
        return col5;
    }

    public void setColorBorderButton(Color col5) {
        this.col5 = col5;
    }

    public void setColorTextButtonKey(Color col6){
        this.col6 = col6;
    }

    public Color getColorTextKeyButton(){
        return this.col6;
    }

    public void setColorTextButtonKeySelected(Color col7){
        this.col7 = col7;
    }

    public Color getColorTextButtonKeySelected(){
        return this.col7;
    }
}
