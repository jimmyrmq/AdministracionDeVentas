package view.frame.ui.component;

import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Notificaciones extends JComponent {
    private final int width = 300;
    private final int height = 200;
    private String message;
    private int posx_tt;
    private int posy_tt;
    private Font font0 = GlobalUI.getInstance().getTheme().getPanelUI().getFont();
    private Font font1 = GlobalUI.getInstance().getTheme().getPanelUI().getFont();

    public Notificaciones(String title,String message){
        setOpaque(false);
        setFocusable(false);
        setPreferredSize(new Dimension(width,height));

        this.message = message;
        String fnme = font0.getFontName();
        font1 = new Font(fnme,1,12);
        calculateTextXY();
    }

    private void calculateTextXY(){
        FontMetrics fmt = getFontMetrics(font0);
        int padding = 20;
        posx_tt = 10;//((this.width-fmt.stringWidth(message))/2);
        posy_tt = (((height - (fmt.getHeight()-padding)) / 2) + fmt.getHeight())+padding;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setColor(Color.RED);
        g2.fillRoundRect(1, 1, getWidth()-1 , getHeight()-1 , 4, 4);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(0, 0, getWidth() , getHeight() , 4, 4);

        if(message!=null) {
            g2.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2.setFont(font0);
            g2.setColor(Color.WHITE);
            g2.drawString(message, posx_tt, posy_tt);
        }

        g2.dispose();
        g.dispose();
    }
}
