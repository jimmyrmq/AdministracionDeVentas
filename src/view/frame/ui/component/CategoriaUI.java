package view.frame.ui.component;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CategoriaUI extends JComponent implements MouseListener, FocusListener, Accessible {
    private Integer ID;
    private String title;
    private Color color;
    private Color colorClic;
    private Font font = new Font("Segoe UI",0,12);
    private int posx_tt;
    private int posy_tt;
    private int width = 100;
    private int height = 100;
    private boolean in = false;
    private boolean selected = false;
    private boolean focus = false;
    public CategoriaUI(Integer ID,String title, Color color){
        this.ID = ID;
        setOpaque(false);
        this.title = title;
        this.color = color;
        this.colorClic = ColorFilter.getColor(color);

        Dimension dim = new Dimension(width,height);
        setPreferredSize(dim);
        setSize(dim);
        calculate();
        addMouseListener(this);
        addFocusListener(this);
    }

    private void calculate(){
        FontMetrics fmt = getFontMetrics(font);
        posx_tt = (width-fmt.stringWidth(title))/2;
        posy_tt = (height-fmt.getHeight());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //System.out.println("Title: "+title+" "+ getWidth()+" , "+getHeight()+" "+color+" "+focus);
        g2.setColor(in?colorClic:color);

        g2.fillRoundRect(1, 1, getWidth()-1 , getHeight()-1 , 2, 2);

        if(focus){
            g2.setColor(colorClic);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, getWidth()-1 , getHeight()-1 , 2, 2);

        }/*else{
            g2.setColor(color.darker());
            g2.drawRoundRect(0, 0, getWidth()-2 , getHeight()-2 , 2, 2);
        }*/

        if(title!=null) {
            g2.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2.setFont(font);
            g2.setColor(Color.white);
            g2.drawString(title, posx_tt, posy_tt);
        }

        g2.dispose();
        g.dispose();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(in){
            selected = true;
            requestFocus();
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        in = true;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        in = false;
        repaint();
    }

    @Override
    public void focusGained(FocusEvent e) {
        focus = true;
        selected = true;
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        focus = false;
        selected = false;
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }

    public Integer getID() {
        return ID;
    }
}
