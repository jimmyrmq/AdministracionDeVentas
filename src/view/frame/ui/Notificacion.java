package view.frame.ui;

import view.frame.main.FrameMain;
import view.frame.ui.component.NotificacionUI;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Notificacion {
    private NotificacionUI notificacion;
    private boolean start = false;
    private Point cursorPoint;
    public Notificacion(String title){
        this(title,null);
    }
    public Notificacion(String title, String messaje){
        notificacion = new NotificacionUI(title,messaje);
        FrameMain.frame.setGlassPane(notificacion);
        cursorPoint = new Point();
        FrameMain.frame.getGlassPane().addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cursorPoint.x = e.getX();
                cursorPoint.y = e.getY();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
            }
        });

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
            notificacion.runShow();
        }
    }

    private synchronized void runThread(){
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException exc) {
            }
            boolean inside ;
            Point point = notificacion.getPoint();
            int pxend = (point.x+ notificacion.getDimX());
            int pyend = (point.y+ notificacion.getDimY());
            boolean inx,iny;
            do{
                //point = notificacion.getPoint();
                //System.out.println("Cursor: "+cursorPoint.x+","+cursorPoint.y+" "+point.x+", "+point.y);
                try{
                    Thread.sleep(100);
                }catch(InterruptedException exc){}
                inx = cursorPoint.x >= point.x && cursorPoint.x <= pxend;
                iny = cursorPoint.y >= point.y && cursorPoint.y <= pyend;
                inside = inx && iny;
            }while(inside);

            start = false;
            setVisible(false);
        });
        thread.start();
    }
   public void setMensaje(String msg){
        notificacion.setMessage(msg);
   }
}
