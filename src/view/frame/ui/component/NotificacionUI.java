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
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NotificacionUI extends JComponent implements MouseListener {//MouseMotionListener{
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

    public NotificacionUI() {
        setOpaque(false);
        setFocusable(false);

        String fnme = font0.getFontName();
        font1 = new Font(fnme, 1, 14);

        point = new Point();
        point.x = 5;//this.width - getWidth();
        point.y = 0;//screenSize.height - (this.height - getHeight());

        calculateDimension();

        //Dimension screenSize = FrameMain.frame.getSize();//java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        calculateTextXY();

        addMouseListener(this);
        //addMouseMotionListener(this);
    }

    private void calculateDimension() {
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
        point.x = -100;
        Thread t = new Thread(() -> {
            for (int i = 14; i >= 0; i--) {
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

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2.setColor(Color.WHITE);
        g2.setFont(font1);

        if (title != null) {
            g2.drawString(title, posx_tt, posy_tt);
        }

        if (message != null) {
            g2.setFont(font0);
            g2.drawString(message, posx_msg, posy_msg);
        }

        //g2.dispose();
        //g.dispose();
    }

    public void setMessage(String message) {
        this.message = message;
        calculateDimension();
    }

    public void setTitle(String tile){
        this.title = tile;
    }

    public boolean isMouseIn() {
        return mouseIn;
    }

   /* @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {
        //boolean inx = e.getX() >= point.x && e.getX() <= (point.x+width);
        //boolean iny = e.getY() >= point.y && e.getY() <= (point.y+height);
        mouseIn = true;//inx && iny;
        //System.out.println(e.getX()+" "+e.getY()+" "+point.x+" "+point.y+" "+inx+" "+iny+" "+mouseIn);
    }
*/
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseIn = true;
        System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseIn = false;
        System.out.println("mouseExited");
    }
}
