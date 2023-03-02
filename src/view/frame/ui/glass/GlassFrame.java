package view.frame.ui.glass;

import view.frame.main.FrameMain;

import javax.swing.*;
import java.awt.Component;

public class GlassFrame {
    private Component aux;

    public GlassFrame(){}

    public void show(IPanelGlass panel){
        if(FrameMain.frame.getGlassPane()!=null){
            aux = FrameMain.frame.getGlassPane();
        }

        PanelGlass panelGlass = new PanelGlass(panel);

        FrameMain.frame.setGlassPane(panelGlass);
        FrameMain.frame.getGlassPane().setVisible(true);
        panel.init();
    }

    public void close(){
        FrameMain.frame.getGlassPane().setVisible(false);
        if(aux!=null)
            FrameMain.frame.setGlassPane(aux);
    }
}
