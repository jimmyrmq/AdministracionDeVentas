package view.frame.main;

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
        Object[] opcion = new Object[]{"Si", "No"};
        int n = JOptionPane.showOptionDialog(frame, "¿Deseas salir del sistema?","Administracion", 0, 3, (Icon)null, opcion, "No");
        if(n == 0){
            frame.setVisible(false);
            frame.dispose();
            System.gc();
            System.exit(0);
        }
    }

}
