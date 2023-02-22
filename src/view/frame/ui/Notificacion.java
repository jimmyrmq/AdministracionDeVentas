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
    private JPanel panel;
    private Point point;
    private boolean initialized = false;

    public Notificacion(){
        this(new Point(10,470));
    }


    public Notificacion(Point p){
        notificacion = new NotificacionUI();
        this.point  = p;
    }

    public void setTime(int timeSec){
        time = timeSec * 1000;
    }

    private synchronized void setVisible(boolean visible) {
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
            if(!initialized) {
                initialized = true;
                panel = new JPanel(new GridBagLayout());
                panel.setOpaque(false);
                FrameMain.frame.setGlassPane(panel);
                //System.out.println(FrameMain.frame.getGlassPane());

                panel.add(notificacion, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, point.y, point.x, 0, 0, 1.0f, 1.0f));
            }

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

   public void start(String title,String msg){
        notificacion.setTitle(title);
        notificacion.setMessage(msg);
        start();
   }

   public void setPoint(Point point){
        this.point = point;
        panel.removeAll();
        panel.add(notificacion, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, point.y, point.x, 0, 0, 1.0f, 1.0f));
        panel.repaint();
        panel.updateUI();
    }
}
