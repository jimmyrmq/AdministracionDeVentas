package view.frame.marca;

import com.djm.ui.component.TextField;
import com.djm.util.LayoutPanel;
import model.Marca;
import util.SystemProperties;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelMarca {
    private Marca marca = null;
    private TextField tDescripcion;
    private final SystemProperties sp = SystemProperties.getInstance();

    public PanelMarca(){}

    public JPanel createGUI(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        tDescripcion = new TextField(15,150);
        tDescripcion.requestFocus();
        JLabel lDescripcion = new JLabel(sp.getValue("label.descripcion")+":");

        panel.add(lDescripcion, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tDescripcion, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    public Marca getValueMarca(){
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

    public Marca getMarca(){
        return marca;
    }

    public void clear(){
        marca = null;
        tDescripcion.setText(null);
        tDescripcion.requestFocus();
    }
}
