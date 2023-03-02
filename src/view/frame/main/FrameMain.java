package view.frame.main;


import view.frame.ui.glass.Notificacion;

import javax.swing.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FrameMain {
    public static final Notificacion notificacion = new Notificacion();
    public static JFrame frame;
    private Container content;
    public FrameMain(){
        init();
    }
    private void init(){
        frame = new JFrame("Inventa - Administracion");

        content = frame.getContentPane();
        content.setLayout(new GridBagLayout());

        frame.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
               Salir.getInstance().exitSystem(frame);
            }

            public void windowClosed(WindowEvent e) {
                Salir.getInstance().exitSystem(frame);
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
        });

        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        frame.setSize((int)width, (int)height - 30);*/

        //frame.add(panel);
        //Dimension dim = new Dimension(1300, 690);
        Dimension dim = new Dimension(1370, 680);
        //frame.addMouseMotionListener(this);
        frame.setUndecorated(false);
        frame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(0);//Deshabilitar el botton  de cerrar
        frame.setResizable(true);
        Image icon = Toolkit.getDefaultToolkit().getImage("icon/main.png");
        frame.setIconImage(icon);//Icono.getInstance().loadIcon(soft.getNameIcon()).getImage());
        frame.setExtendedState(6);//6: Expandir la ventana
        //frame.setSize(dim);
        frame.setMinimumSize(dim);
        //frame.setBackground(new Color(158,162,144));
        frame.setLocationRelativeTo(null);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);//6 Expandir la ventana
        //frame.setUndecorated(true);
        //frame.setAlwaysOnTop(true);
        //frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }


    public Container getContainer(){
        return content;

    }
}
