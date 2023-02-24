package view.frame.ui.component;

import model.Categoria;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CategoriaUI extends JComponent implements MouseListener, Accessible {

    private String actionCommand;
    private Integer ID;
    private String title;
    private Color color;
    private Color colorClic;
    private Color colDisable = new Color(192, 190, 190);
    private Font font = new Font("Segoe UI",0,12);
    private int posx_tt;
    private int posy_tt;
    private int width = 100;
    private int height = 100;
    private boolean in = false;
    private boolean selected = false;
    //private boolean focus = false;
    private Color colorCheck=Color.white;//  = GlobalUI.getInstance().getTheme().getColorChekSelected();
    //private Color colorDisabled;//=  = GlobalUI.getInstance().getTheme().getColorChekDisabled();
    private Categoria categoria;
    private boolean enabled = true;

    public CategoriaUI(Categoria categoria){//Integer ID,String title, Color color
        this.categoria =  categoria;
        this.enabled = true;

        //ICheckBoxUI checkBoxUI = GlobalUI.getInstance().getTheme().getCheckBoxUI();
        //colorCheck = checkBoxUI.getColorChekSelected();
        //colorDisabled = checkBoxUI.getColorChekDisabled();

        this.ID = categoria.getID();
        actionCommand = String.valueOf(categoria.getID());
        setOpaque(false);

        this.title = categoria.getDesrcripcion();
        this.color = categoria.getColor();
        this.colorClic = ColorFilter.getColor(color);

        Dimension dim = new Dimension(width,height);
        setPreferredSize(dim);
        setSize(dim);
        calculate();
        addMouseListener(this);
        //addFocusListener(this);
    }

    public void updateCategoria(Categoria categoria){
        this.categoria = categoria;
        this.title = categoria.getDesrcripcion();
        this.color = categoria.getColor();
        this.colorClic = ColorFilter.getColor(color);
        calculate();
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
        if(enabled)
            g2.setColor(in?colorClic:color);
        else
            g2.setColor(colDisable);

        g2.fillRoundRect(1, 1, getWidth()-1 , getHeight()-1 , 2, 2);

        if(selected){
            g2.setColor(colorCheck);
            //g2.drawLine(2,2,WIDTH_BOX,HEIGHT_BOX);
            g2.setStroke(new BasicStroke(2));//, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
            g2.drawLine(90,12,95,5);
            g2.drawLine(88,9,90,12);
        }

        if(enabled && in){
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
        if(e.getButton() == MouseEvent.BUTTON1 && enabled && in){
            selected = !selected;
            //requestFocus();

            //System.out.println(e.getSource()+" "+e.getID()+" "+actionCommand);
            ActionEvent ae = new ActionEvent(e.getSource(), e.getID(), actionCommand);//me.paramString());
            fireActionPerformed(ae);
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(enabled) {
            in = true;
            repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(enabled) {
            in = false;
            repaint();
        }
    }/*

    @Override
    public void focusGained(FocusEvent e) {
        focus = true;
        //selected = true;
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        focus = false;
        //selected = false;
        repaint();
    }*/

    public boolean isSelected() {
        return selected;
    }

    public Integer getID() {
        return ID;
    }

    public Categoria getCategoria(){
        return this.categoria;
    }

    @Override
    public void setEnabled(boolean e){
        enabled = e;
        repaint();
    }

    public void addActionListener(ActionListener al){
        listenerList.add(ActionListener.class, al);
    }

    public void setActionCommand(String actionCommand){
        this.actionCommand = actionCommand;
    }

/*
    private void actionPerformed(ActionEvent event) {
        fireActionPerformed(event);
    }
*/

    private void fireActionPerformed(ActionEvent event) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        ActionEvent e;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class){
                //System.out.println(listeners[i]+" "+actionCommand);
				  /*String actionCommand0 = event.getActionCommand();
				  if(actionCommand0 == null)
					 actionCommand0 = getActionCommand();*/
                e = new ActionEvent(this,
                        ActionEvent.ACTION_PERFORMED,
                        actionCommand,
                        event.getWhen(),
                        event.getModifiers());

                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }

}
