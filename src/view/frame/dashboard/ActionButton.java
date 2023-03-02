package view.frame.dashboard;

import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.main.Salir;
import view.frame.producto.PanelProducto;
import view.frame.ui.component.ButtonTabbed;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionButton  implements ActionListener {
    private PanelTarea panel;
    private PanelProducto pp = null;
    private SystemProperties sp = SystemProperties.getInstance();
    public ActionButton(PanelTarea panel){
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        Object ob = ae.getSource();
        if(ButtonTabbed.class == ob.getClass() ){
            clear();

            if(action.equalsIgnoreCase("PRODUCTO")){
                if(pp==null) {
                    pp = new PanelProducto();
                    pp.init();
                }

                panel.setTarea(pp);
            }
        }
        else if(action.equalsIgnoreCase("Salir")){
            Salir.getInstance().exitSystem(FrameMain.frame);
        }
    }

    private void clear(){
        panel.clear();

        if(pp!=null) {
            pp.runBeforeClose();
            //pp = null;
        }
    }
}
