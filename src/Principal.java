import util.Global;
import util.Init;

public class Principal {
    public static void main(String args[]){
        //javax.swing.SwingUtilities.invokeLater(()-> {
        Thread t = new Thread(()-> {
            Init i = new Init();
        });
        t.start();
    }
}
