package view.frame.ui.component;

import com.djm.util.LayoutPanel;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;

public class PanelList extends JPanel {
    private final Color colorBack = GlobalUI.getInstance().getTheme().getTextUI().getBackground();
    //private final Color colorBorder = GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder();
    private JPanel panel;
    byte x = 0;
    byte y = 0;
    int index = 0;

    public PanelList(){
        setBorder(new EmptyBorder(10, 0, 10, 10));
        setOpaque(true);
        setLayout(new GridBagLayout());
        //setMinimumSize(new Dimension(300,100));
        //setPreferredSize(new Dimension(300,200));
        setBackground(colorBack);
        //setBorder(BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder()));
        panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        /*for(int i = 0;i<2;i++) {
            add(new CategoriaUI(1,"Servicio "+i,Color.ORANGE));
        }*/

        add(panel, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));
    }

    public void add(CategoriaUI cate){
        float wx =(x == 1)?1.0f: 0.0f;

        panel.add(cate, LayoutPanel.constantePane(x, y, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 10, 10, 0, wx, 0.0f),index);
        x++;
        if(x==2){
            x = 0;
            y++;
        }
        index++;
        panel.repaint();
        panel.updateUI();
        panel.revalidate();
    }

    /*protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(colorBack);

        g2.fillRoundRect(0, 0, getWidth() , getHeight() , 2, 2);

        g2.setColor(colorBorder);

        g2.drawRoundRect(0, 0, getWidth()-1 , getHeight()-1 , 2, 2);

        g2.dispose();
        g.dispose();

    }*/
}
