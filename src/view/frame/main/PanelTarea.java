package view.frame.main;

import com.djm.util.LayoutPanel;
import util.IPanel;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelTarea implements ActionListener {
    private JPanel panelTarea;
    private IPanel panel;
    //private Button bClose;
    public PanelTarea(){
        panelTarea = new JPanel(new GridBagLayout());
        panelTarea.setOpaque(false);
        //panelTarea.setBackground(Color.RED);
        /*bClose = new Button(new ImageIcon(("icon/closewb.png")));
        //bClose.setImageAux(new ImageIcon("icon/closer.png"));
        bClose.setDimension(30,30);
        bClose.setPaintBack(false);
        bClose.setNotSelect(false);
        bClose.addActionListener(this);*/
    }

    public void setTarea(IPanel p){
        panel = p;
        panelTarea.removeAll();
        //panelTarea.add(bClose, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 0, 0, 0, 10, 0.0f, 0.0f));

        if(panel!=null)
            panelTarea.add(panel.getPanel(), LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));

        panelTarea.updateUI();
        panelTarea.repaint();

        if(p.isClosed())
            p.init();
    }

    public JPanel getPanel() {
        return panelTarea;
    }

    public IPanel getPanelTarea(){
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panelTarea.removeAll();
        panelTarea.updateUI();
        panelTarea.repaint();
    }
}
