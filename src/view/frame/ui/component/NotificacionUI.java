package view.frame.ui.component;

import view.frame.main.FrameMain;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class NotificacionUI extends JComponent {//implements MouseMotionListener{//MouseListener {
    private int width = 250;
    private final int height = 60;
    private String message;
    private String title;
    private int posx_tt;
    private int posy_tt;
    private int posx_msg;
    private int posy_msg;
    private Font font0 = GlobalUI.getInstance().getTheme().getPanelUI().getFont();
    private Font font1;//= GlobalUI.getInstance().getTheme().getPanelUI().getFont();
    private Point point;

    private boolean mouseIn = false;

    public NotificacionUI(String title, String message) {
        setOpaque(false);
        setFocusable(false);

        this.title = title;
        this.message = message;

        String fnme = font0.getFontName();
        font1 = new Font(fnme, 1, 14);

        recalculate();

        //Dimension screenSize = FrameMain.frame.getSize();//java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        point = new Point();
        point.x = -100;//this.width - getWidth();
        point.y = 500;//screenSize.height - (this.height - getHeight());

        //addMouseListener(this);
        //addMouseMotionListener(this);
    }

    private void recalculate() {
        FontMetrics fmt = getFontMetrics(font0);
        if(message!=null) {
            int w = fmt.stringWidth(message);
            if (w > width)
                width += w + 10;
        }

        setPreferredSize(new Dimension(width, height));
        revalidate();
    }

    private void calculateTextXY() {
        FontMetrics fmt = getFontMetrics(font0);
        posx_tt = 10 + point.x;//((this.width-fmt.stringWidth(message))/2);
        posy_tt = point.y + fmt.getHeight();
        posx_msg = posx_tt;
        posy_msg = posy_tt + fmt.getHeight() + 12;
    }

    public void runShow() {
        point.x = 0;
        Thread t = new Thread(() -> {
            for (int i = 10; i >= 0; i--) {
                calculateTextXY();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException exc) {
                }
                point.x += i;
                repaint();
            }
        });
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setColor(GlobalUI.getInstance().getTheme().getButtonUI().getColorSelected());
        g2.fillRoundRect(point.x, point.y, width, height, 4, 4);
        g2.setColor(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder());
        g2.drawRoundRect(point.x, point.y, width, height, 4, 4);

        if (message != null) {
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2.setColor(Color.WHITE);
            g2.setFont(font1);
            g2.drawString(title, posx_tt, posy_tt);
            g2.setFont(font0);
            g2.drawString(message, posx_msg, posy_msg);
        }

        g2.dispose();
        g.dispose();
    }

    public void setMessage(String message) {
        this.message = message;
        recalculate();
    }


   /* @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked");

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
        mouseIn = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered");
        mouseIn = true;

    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited");
        mouseIn = false;
    }*/

    /*public boolean isMouseIn() {
        return mouseIn;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        *Point p = MouseInfo.getPointerInfo().getLocation();
        System.out.println(p.x+" "+p.y+" "+point.x+" "+point.y);

        boolean inx = p.x >= point.x && p.x <= (point.x+width);
        boolean iny = p.y >= point.y && p.y <= (point.y+height);
        mouseIn = inx && iny;*
    }*/

    public int getDimX(){
        return this.width;
    }
    public int getDimY(){
        return this.height;
    }
    public Point getPoint(){
        return point;
    }
}
