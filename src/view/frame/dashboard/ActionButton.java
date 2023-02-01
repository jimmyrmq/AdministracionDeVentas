package view.frame.dashboard;

import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.main.PanelTarea;
import view.frame.main.Salir;
import view.frame.producto.PanelProducto;

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
        if(action.equalsIgnoreCase(sp.getValue("produtos.buttom.dashboard.id"))){
            if(pp==null)
                pp = new PanelProducto();

            panel.setTarea(pp);
        }
        else if(action.equalsIgnoreCase("Salir")){
            Salir.getInstance().exitSystem(FrameMain.frame);
        }
    }
}
