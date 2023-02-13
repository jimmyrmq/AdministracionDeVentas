package view.frame.main;

import util.SystemProperties;
import view.frame.ui.component.OptionPane;

import javax.swing.*;

public class Salir {
    private static Salir salir;
    private Salir(){}

    public static Salir getInstance(){
        if(salir == null)
            salir = new Salir();
        return salir;
    }

    public void exitSystem(JFrame frame){
        int n0 = OptionPane.questionYesOrKey(frame,SystemProperties.getInstance().getValue("label.mensaje.salir"));
         if(n0 == OptionPane.OK){
            try {
                Thread.sleep(100);
            }catch (InterruptedException exc){}
            frame.setVisible(false);
            frame.dispose();
            System.gc();
            System.exit(0);
        }
    }

}
