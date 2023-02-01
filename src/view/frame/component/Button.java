package view.frame.component;

import view.frame.themes.GlobalUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.accessibility.Accessible;
import javax.swing.*;

public class Button extends JComponent implements  MouseMotionListener, MouseListener  , Accessible{
    private String actionCommand;
    private String title;
    private String textKey;
    private boolean in =false;
    private boolean out =true;
    private boolean pressed = false;
    private boolean sizeDefault = false;
    private Font font = new Font("Tahoma",0,14);
    private Font fontKey = new Font("Tahoma",1,12);
    private Color colorBack = null;
    private Color colorFore = new Color(0,0,0);
    private Color colorForeKey = new Color(140, 140, 140);
    private Color colorBorder = new Color(0,0,0);
    private Color colorSelected = new Color(0,0,0);
    private Color cbackPaint = colorBack;
    private Color cborderPaint = colorBorder;
    private Color cfPaint = colorFore;
    private Color cfkPaint = colorForeKey;
    private Color cbackIn = new Color(82,6,140);
    private Color cfkIn = new Color(217, 214, 214);
    private Color cborderIn = new Color(82,6,140);
    private ButtonGroup buttonGroup;
    private Image image0 =null;
    private Image image1=null;
    private Image imagePaint=null;
    private int width;
    private int height;
    private int dimx_ii;
    private int dimy_ii;
    private int posy_ii;
    private int posx_ii;
    private int posx_tt;
    private int posy_tt;
    private boolean paintSelected = false;
    private boolean notSelect = false;
    public static final byte NONE = -1;
    public static final byte CENTER = 0;
    public static final byte TOP = 1;
    public static final byte BUTTOM = 2;
    public static final byte LEFT = 3;
    public static final byte RIGHT = 4;
    private byte orientationText = LEFT;
    private byte orientationImageX = RIGHT;
    private byte orientationImageY = CENTER;
    private boolean paintBack = true;
    private boolean paintBorder = false;
    private int posx_tk = 0;
    private int posy_tk = 0;

    public Button(ImageIcon ii) {
        this(null,null,ii,NONE);
    }
    public Button(String title) {
        this(title,null,null,NONE);
    }
    public Button(String title, String textKey,ImageIcon ii){
        this(title,textKey,ii,NONE);
    }
    public Button(String title,ImageIcon ii){
        this(title,null,ii,NONE);
    }
    public Button(String title, String textKey, ImageIcon ii,byte orientationText){
        this.textKey = textKey;
        this.title = title;
        this.actionCommand = title!=null?title:ii.getDescription();

        this.orientationText = orientationText;
        if(title != null) {
            this.orientationImageX = (orientationText == NONE) ? LEFT : RIGHT;
        }else
            this.orientationImageX = CENTER;

        this.orientationImageY = CENTER;

        this.paintBack = true;
        this.paintBorder = false;

        if(ii!=null) {
            image0 = ii.getImage();
            imagePaint = ii.getImage();
        }

        setOpaque(false);
        /*setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
*/
        Color cb = GlobalUI.getInstance().getBackgroundButton();
        Color cf = GlobalUI.getInstance().getForeground();
        Color cs = GlobalUI.getInstance().getColorSelected();
        Color cbrd = GlobalUI.getInstance().getColorBorderButton();
        Color cftk = GlobalUI.getInstance().getColorTextKeyButton();
        Color cftks = GlobalUI.getInstance().getColorTextButtonKeySelected();

        cborderIn = cbrd.darker();

        if(cfkIn==null)
            cfkIn = cftks;
        else
            cfkIn = new Color(91, 91, 91);

        if(cb==null)
            cb = new Color(211, 217, 220);

        if(cf==null)
            cf = new Color(0,0,0);

        if(cs==null)
            cs = new Color(0,0,0);

        if(cbrd == null)
            cbrd = new Color(210, 19, 19);

        if(cftk==null)
            colorForeKey =  new Color(38, 36, 36);
        else
            colorForeKey =cftk;

        setColorBack(cb);
        setColorFore(cf);
        setColorBackSelected(cs);
        setColorBorder(cbrd);

        setDimension();

        addMouseMotionListener(this);
        addMouseListener(this);
    }

    private void setDimension(){
        if(image0 !=null) {
            dimx_ii = image0.getWidth(null);
            dimy_ii = image0.getHeight(null);
        }else{
            dimx_ii = 3;
            dimy_ii = 3;
        }

        int fw;
        int w = 10;
        int h = 10;

        if(title!=null) {
            FontMetrics fmt = getFontMetrics(font);
            fw = fmt.stringWidth(title);
            h = fmt.getHeight()+10;
            w += fw + 10;
        }

        if(image0 !=null) {
            w += dimy_ii + 10;
            h = dimy_ii+10;
        }

        if(image0 !=null && title!=null){
            if(w<180)
                w = 180;
        }
        this.width = w;
        this.height = h;
        calculePosicion();

        //System.out.println(w);

        setPreferredSize(new Dimension(width,height));

    }
    private void calculePosicion(){
        posx_tk = 3;
        if(textKey!=null) {
            FontMetrics fmt = getFontMetrics(fontKey);
            posy_tk = fmt.getHeight()-3;
        }
        if(title!=null) {
            FontMetrics fmt = getFontMetrics(font);
            posy_tt = (((height - fmt.getHeight()) / 2) + fmt.getHeight()) - 3;
            if(image0!=null) {
                if(sizeDefault && (orientationText == TOP || orientationText == BUTTOM)){
                    int h = fmt.getHeight() + dimy_ii;
                    if (h > this.height)
                        this.height = h + 10;

                    if (dimx_ii > fmt.stringWidth(title)) {
                        width = dimx_ii;
                    } else
                        width = fmt.stringWidth(title);
                    width += 10;
                }

                if (orientationText == LEFT) {
                    if(textKey!=null)
                        posx_tk = width-fmt.stringWidth(textKey)-5;

                    posx_tt = 5;
                }
                else if (orientationText == RIGHT) {
                    if(textKey!=null)
                        posx_tk = 5;
                    posx_tt = width - fmt.stringWidth(title) - 5;
                }
                else if(orientationText == TOP) {
                    posx_tt = (width-fmt.stringWidth(title))/2;
                    posy_tt = fmt.getHeight();
                }
                else if(orientationText == BUTTOM) {
                    posx_tt = (width-fmt.stringWidth(title))/2;
                    posy_tt = height-3;//(dimy_ii+fmt.getHeight())+2;
                }
                else if(orientationText == NONE){
                    if(textKey!=null)
                        posx_tk = width-fmt.stringWidth(textKey)-5;
                    posx_tt = dimx_ii+15;
                }

            }
            else if(orientationText == CENTER || orientationText == TOP) {
                posx_tt = (width-fmt.stringWidth(title))/2;
            }
        }

        if(image0!=null){


            if(orientationImageX == CENTER)
                posx_ii = (width-dimx_ii)/2;
            else if(orientationImageX == RIGHT)
                posx_ii = (width - dimx_ii) - 10;
            else/* if(orientationImageX == LEFT)
                    posx_ii = 5;
                else*/
                posx_ii = 5;

            if(orientationImageY== CENTER)
                posy_ii= (height -dimy_ii)/2;
            else if(orientationImageY == TOP)
                posy_ii= 3;
            else if(orientationImageY == BUTTOM)
                posy_ii= height - dimy_ii;
        }
        //System.out.println( this.orientationImageX+" "+this.orientationImageY+" "+actionCommand);
    }

    public void setDimension(int width, int height){
        sizeDefault = false;
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width,height));
        calculePosicion();
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //g2.setStroke ( new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND ));
        if(paintSelected){
            cbackPaint = colorSelected;//new Color(157, 157, 161);//(94, 154, 85);
            cborderPaint = colorBorder;//new Color(157, 157, 161);//(94, 154, 85);
            cfPaint = colorFore;
            cfkPaint = cfkIn;
            if(image1!=null)
                imagePaint = image0;
        }else {

            if(pressed)
                cfPaint = colorSelected;
            else
                cfPaint = colorFore;

            if (in) {
                cbackPaint = cbackIn;
                cborderPaint = cborderIn;
                //cfPaint = colorSelected;
                cfkPaint = cfkIn;
                if(image1!=null)
                    imagePaint = image1;
            } else if (out) {
                cbackPaint = colorBack;
                cborderPaint = colorBorder;
                cfkPaint = colorForeKey;
                //cfPaint = colorFore;
                imagePaint = image0;
            }
        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(paintBack) {
            g2.setColor(cbackPaint);
            g2.fillRoundRect(0, 0, getWidth() , getHeight() , 2, 2);
        }

        if(paintBorder) {
            g2.setColor(cborderPaint);
            g2.drawRoundRect(0, 0, getWidth()-1 , getHeight()-1 , 2, 2);
        }

        if(imagePaint!=null)
            g2.drawImage(imagePaint,posx_ii,posy_ii,null);

        if(title!=null) {
            g2.setFont(font);
            g2.setColor(cfPaint);
            g2.drawString(title, posx_tt, posy_tt);
        }

        if(textKey!=null) {
            g2.setFont(fontKey);
            g2.setColor(cfkPaint);
            g2.drawString(textKey, posx_tk, posy_tk);
        }

        super.paintComponent(g);
    }
    public void setOrientationText(byte orientationText){
        this.orientationText = orientationText;
        calculePosicion();
        setPreferredSize(new Dimension(width,height));
        revalidate();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(buttonGroup!=null)
            buttonGroup.clearSelection();

        if(paintBack && !notSelect)
            paintSelected =!paintSelected;

        ActionEvent ae=new ActionEvent(e.getSource(), e.getID(),actionCommand);//me.paramString());
        actionPerformed(ae);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
        repaint();}

    @Override
    public void mouseEntered(MouseEvent e) {
        in = true;
        out = false;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        in = false;
        out = true;
        repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    public void setColorBackSelected(Color cbSelected) {
        this.colorSelected = cbSelected;
    }

    public void setColorBack(Color colorBack) {
        this.colorBack = colorBack;
        int r = colorBack.getRed();
        int g = colorBack.getGreen();
        int b = colorBack.getBlue();
        //System.out.print(r+" "+g+" "+b+" ");
        if(r == 0)
            r=50;
        if(g == 0)
            g=50;
        if(b == 0)
            b=50;
        r += (r*5)/100;
        g += (g*5)/100;
        b += (b*5)/100;

        if(r<0)
            r = 0;
        else if(r>255)
            r=255;

        if(g<0)
            g = 0;
        else if(g>255)
            g=255;

        if(b<0)
            b = 0;
        else if(b>255)
            b=255;

        //System.out.println(r+" "+g+" "+b+" ");
        cbackIn = new Color(r,g,b);
    }

    public void setColorFore(Color colorFore) {
        this.colorFore = colorFore;
    }

    public void setColorBorder(Color colorBorder) {
        this.colorBorder = colorBorder;
    }

    public void setImage(ImageIcon i) {
        image0 =i.getImage();
        imagePaint = image0;

        setDimension();
        repaint();
        revalidate();
    }

    public void setImageAux(ImageIcon i) {
        image1 =i.getImage();
    }

    public void setTitle(String title){
        this.title = title;
        setDimension();
        //updateUI();
        repaint();
        revalidate();
    }

    public void addActionListener(ActionListener al){
        listenerList.add(ActionListener.class, al);
    }

    public void setActionCommand(String actionCommand){
        this.actionCommand = actionCommand;
    }
    public void actionPerformed(ActionEvent event) {
        fireActionPerformed(event);
    }

    protected void fireActionPerformed(ActionEvent event) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        ActionEvent e;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class){
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

    public void setSelected(boolean s){
        paintSelected = s;
        repaint();
    }

    public boolean isSelected(){
        return paintSelected;
    }

    public void setGroup(ButtonGroup bg){
        buttonGroup = bg;
    }

    public void setNotSelect(boolean notSelect){
        this.notSelect = notSelect;
    }

    public void setFont(Font font){
        this.font = font;
    }

    public void setPaintBack(boolean paintBack){
        this.paintBack = paintBack;
    }
    public void setPaintBorder(boolean paintBorder){
        this.paintBorder = paintBorder;
    }

    public void setOrientationImageX(byte orientationImage){
        this.orientationImageX = orientationImage;
        calculePosicion();
    }

    public void setOrientationImageY(byte orientationImage){
        this.orientationImageX = orientationImage;
        calculePosicion();
    }
}
