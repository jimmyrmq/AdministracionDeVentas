package view.frame.ui.component;

import com.djm.ui.themes.button.IButtonUI;
import com.djm.ui.themes.checkbox.ICheckBoxUI;
import com.djm.ui.themes.panel.IPanelUI;
import com.djm.ui.themes.text.ITextUI;
import view.frame.ui.themes.GlobalUI;
import view.frame.ui.themes.blue.CheckBoxUI;

import javax.swing.*;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
//import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CheckBox extends JComponent implements MouseListener{//, FocusListener {
    private boolean selected;
    //private boolean pressed = false;
    private boolean enabled = true;
    private boolean clic = false;
    private boolean in = false;
    //private boolean focus = false;
    private String title;
    private final int WIDTH_BOX = 15;
    private final int HEIGHT_BOX = 15;

    private Color colorIn = null;
    private Color colorBack = null;//new Color(97,99,101);
    private Color colorBorderFocus= null;// GlobalUI.getInstance().getTheme().getColorBorderFocusField();
    private Color colorBorder;//= GlobalUI.getInstance().getTheme().getColorBorderField();//new Color(97,99,101);
    private Color colorPaint = null;
    private Color colorFore;//=  = GlobalUI.getInstance().getTheme().getForeground();
    private Color colorCheck;//=  = GlobalUI.getInstance().getTheme().getColorChekSelected();
    private Color colorDisabled;//=  = GlobalUI.getInstance().getTheme().getColorChekDisabled();
    private int width = 120;
    private int heght = 18;
    private Font font;
    private int posFontY = 0;
    public CheckBox(String title){
        this(title,false);
    }

    public CheckBox(String title, boolean selected){
        this.title = title;
        this.selected = selected;
        this.font = GlobalUI.getInstance().getTheme().getPanelUI().getFont();

        ICheckBoxUI checkBoxUI = GlobalUI.getInstance().getTheme().getCheckBox();

        colorIn = checkBoxUI.getBackgroundAction();
        colorBack = checkBoxUI.getBackground();
        colorBorderFocus= checkBoxUI.getColorBorderFocus();
        colorBorder= checkBoxUI.getColorBorder();//new Color(97,99,101);
        colorFore = checkBoxUI.getForeground();
        colorCheck = checkBoxUI.getColorChekSelected();
        colorDisabled = checkBoxUI.getColorChekDisabled();



        calulateDimension();
        Dimension dim = new Dimension(width,heght);
        setPreferredSize(dim);
        setSize(dim);

        //addFocusListener(this);
        addMouseListener(this);
    }

    private void calulateDimension(){
        FontMetrics fmt = getFontMetrics(font);

        int h = fmt.getHeight();
        posFontY = ((heght -h)/2)+h-4;
        this.width = fmt.stringWidth(this.title) + WIDTH_BOX + 12;

    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
        //Background
        colorIn = in? colorIn:colorBack;

        g2.setColor(colorIn);
        g2.fillRoundRect(2, 2, WIDTH_BOX-2 , HEIGHT_BOX-2 , 1, 1);

        if(selected){
            g2.setColor(enabled?colorCheck:colorDisabled);
            //g2.drawLine(2,2,WIDTH_BOX,HEIGHT_BOX);
            g2.setStroke(new BasicStroke(2));//, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
            g2.drawLine(7,12,12,5);
            g2.drawLine(5,9,7,12);
        }

        if(in && enabled  ){//&& !focus){
            colorPaint = colorBorderFocus;
        }else
            colorPaint = colorBorder;

        g2.setColor(colorPaint);
        g2.setStroke( new BasicStroke( 1 ) );
        g2.drawRoundRect(2, 2, WIDTH_BOX-2 , HEIGHT_BOX-2 ,2,2);

        if(enabled && clic) {
            g2.setColor(GlobalUI.getInstance().getTheme().getTextUI().getColorBorderFocus());
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, WIDTH_BOX, HEIGHT_BOX, 2, 2);
        }

        g2.setColor(enabled?colorFore:colorDisabled);
        g2.setFont(font);
        if(title!=null){
            g2.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2.drawString(title, WIDTH_BOX+4, posFontY);
        }

        //g2.setColor(Color.BLACK);
        //g2.drawRoundRect(0, 0, getWidth()-1 , getHeight()-1, 0, 0);
    }

    public void setSelected(boolean selected) {
        this.selected = enabled && selected;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clic = false;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //pressed = true;
        clic = true;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //pressed = false;
        clic = false;
        if(enabled && in){
            selected = !selected;
            repaint();
        }

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
/*

    @Override
    public void focusGained(FocusEvent e) {
        focus = true;
    }

    @Override
    public void focusLost(FocusEvent e) {
        focus = false;
    }
*/

    public void setFont(Font font){
        this.font = font;
        calulateDimension();
        revalidate();
        repaint();
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
