package view.frame.ui;

import view.frame.main.FrameMain;
import view.frame.ui.component.NotificacionUI;

import java.awt.Component;

public class Notificacion {
    private NotificacionUI notificacion;
    private boolean start = false;
    public Notificacion(String title){
        this(title,null);
    }
    public Notificacion(String title, String messaje){
        notificacion = new NotificacionUI(title,messaje);
        FrameMain.frame.setGlassPane(notificacion);

        //java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        //System.out.println(screenSize);
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
        }
    }

    private synchronized void runThread(){
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException exc) {
            }
            start = false;
            setVisible(false);
        });
        thread.start();
    }
   public void setMensaje(String msg){
        notificacion.setMessage(msg);
   }
}
