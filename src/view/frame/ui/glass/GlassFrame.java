package view.frame.ui.glass;

import view.frame.main.FrameMain;

import javax.swing.*;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.Kernel;

public class GlassFrame {
    private Component aux;

    public GlassFrame(){}

    public void show(IPanelGlass panel){
        if(FrameMain.frame.getGlassPane()!=null){
            aux = FrameMain.frame.getGlassPane();
        }

        /*JRootPane rootPane = FrameMain.frame.getRootPane();
        rootPane.setGlassPane(new PanelGlass(panel));
        rootPane.getGlassPane().setVisible(true);*/

        FrameMain.frame.setGlassPane(new PanelGlass(panel));
        FrameMain.frame.getGlassPane().setVisible(true);

        Thread t = new Thread(()-> {
            boolean repeat = false;
            do {
                if(FrameMain.frame.getGlassPane().isVisible()) {
                    repeat = false;
                    panel.init();
                    FrameMain.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }while (repeat);

        });
        t.start();
    }

    public void close(){
        FrameMain.frame.getGlassPane().setVisible(false);
        if(aux!=null)
            FrameMain.frame.setGlassPane(aux);
    }
}
