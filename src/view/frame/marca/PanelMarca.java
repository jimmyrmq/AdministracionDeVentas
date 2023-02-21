package view.frame.marca;

import com.djm.ui.component.TextField;
import com.djm.util.LayoutPanel;
import model.Marca;
import util.SystemProperties;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelMarca extends JPanel{
    private Marca marca = null;
    private TextField tDescripcion;
    private final SystemProperties sp = SystemProperties.getInstance();
    private ActionListenerMarca actionListenerMarca = null;

    public PanelMarca(){
        super(new GridBagLayout());
        setOpaque(false);

        createGUI();
    }

    private void createGUI(){
        tDescripcion = new TextField(20,150);

        if(actionListenerMarca!=null)
            tDescripcion.addActionListener(actionListenerMarca);

        JLabel lDescripcion = new JLabel(sp.getValue("marca.label.descripcion")+":");

        add(lDescripcion, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        add(tDescripcion, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
    }

    public Marca getMarca(){
        if(marca == null)
            marca = new Marca();

        marca.setDesrcripcion(tDescripcion.getText());

        return marca;
    }

    public void setMarca(Marca marca){
        this.marca = marca;
        if(this.marca!=null) {
            tDescripcion.setText(marca.getDesrcripcion());
        }
    }

    public void setActionListenerMarca(ActionListenerMarca actionListenerMarca) {
        this.actionListenerMarca = actionListenerMarca;
    }
}
