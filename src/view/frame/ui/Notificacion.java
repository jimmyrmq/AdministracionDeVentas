package view.frame.ui;

import com.djm.util.LayoutPanel;
import view.frame.ui.component.NotificacionUI;
import view.frame.main.FrameMain;

import javax.swing.*;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;

public class Notificacion {
    private NotificacionUI notificacion;
    private boolean start = false;
    private int time = 3000;
    public Notificacion(){
        this(null,null,new Point(10,470));
    }

    public Notificacion(String title){
        this(title,null,new Point(10,470));
    }
    public Notificacion(String title, String messaje){
        this(title,messaje,new Point(10,470));

    }
    public Notificacion(String title, String messaje, Point p){
        notificacion = new NotificacionUI(title,messaje);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        panel.add(notificacion, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, p.y, p.x, 0, 0, 1.0f, 1.0f));
        FrameMain.frame.setGlassPane(panel);
    }

    public void setTime(int timeSec){
        time = timeSec * 1000;
    }

    public synchronized void setVisible(boolean visible) {
        if(FrameMain.frame.getGlassPane()!=null) {
            Component glassPane= FrameMain.frame.getGlassPane();
            if(visible && glassPane.isVisible()) {

                glassPane.repaint();
                glassPane.revalidate();

                runThread();

            }else {
                glassPane.setVisible(visible);
                if(visible) {
                    runThread();
                }
            }
        }
    }

    public synchronized void start(){
        if((!start) && notificacion != null) {
            start = true;
            setVisible(true);
            notificacion.runShow();
        }
    }

    public synchronized void show(){
        setVisible(true);
    }

    private synchronized void runThread(){
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(time);
            } catch (InterruptedException exc) {
            }
            boolean inside ;

            do{
                try{
                    Thread.sleep(100);
                }catch(InterruptedException exc){}
                inside = notificacion.isMouseIn();//inx && iny;
            }while(inside);

            start = false;
            setVisible(false);
        });
        thread.start();
    }
   public void setMensaje(String msg){
        notificacion.setMessage(msg);
   }
   public void setTitle(String title){
        notificacion.setTitle(title);
   }
}
