package view.frame.ui.component;

import javax.swing.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class ColorCategoria extends JComponent {
    private Color color = new Color(0,0,0);
    private final int width = 24;
    private final int heigth = 24;
    //private int width = 20;
    //private int heght = 20;

    public ColorCategoria(){
        this(new Color(0,0,0));
    }
    public ColorCategoria(Color color){
        if(color!=null)
            this.color = color;

        Dimension dim = new Dimension(width, heigth);
        setPreferredSize(dim);
        setSize(dim);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        g2.setColor(color);
        g2.fillRoundRect(0, 0, width , heigth,4, 4);
    }

    public void setColor(Color color){
        this.color = color;
    }

    public int getHeigth() {
        return heigth;
    }
}
