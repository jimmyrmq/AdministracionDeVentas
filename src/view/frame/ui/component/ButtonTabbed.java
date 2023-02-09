package view.frame.ui.component;

import com.djm.ui.themes.button.IButtonUI;
import view.frame.ui.themes.GlobalUI;

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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.accessibility.Accessible;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ButtonTabbed extends JComponent implements  MouseMotionListener, MouseListener  , Accessible{
    private String actionCommand;
    private String title;
    private String textKey;
    private boolean in =false;
    private boolean out =true;
    private boolean pressed = false;
    private boolean sizeDefault = false;
    private Font font = new Font("Segoe UI",0,12);
    private Font fontKey = new Font("Segoe UI",1,11);
    private Color colorBack = null;
    private Color colorFore = new Color(0,0,0);
    private Color colorForeKey;// = new Color(140, 140, 140);
    private Color colorBorder = new Color(0,0,0);
    private Color cBackSelected = new Color(0,0,0);
    private Color cbackPaint = colorBack;
    private Color cborderPaint = colorBorder;
    private Color cfPaint = colorFore;
    private Color cfkPaint = colorForeKey;
    private Color cbackIn = new Color(82,6,140);
    private Color cfkIn = new Color(217, 214, 214);
    private Color coloSelect ;//= new Color(82,6,140);
    private Color cborderIn ;//= new Color(82,6,140);
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
    private boolean paintClickSelected = true;
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
    private boolean isFilterImage ;

    public ButtonTabbed(ImageIcon ii) {
        this(null,null,ii,NONE,true);
    }
    public ButtonTabbed(ImageIcon ii, boolean filter) {
        this(null,null,ii,NONE,filter);
    }
    public ButtonTabbed(String title) {
        this(title,null,null,NONE,false);
    }
    public ButtonTabbed(String title, String textKey, ImageIcon ii){
        this(title,textKey,ii,NONE,true);
    }
    public ButtonTabbed(String title, String textKey, ImageIcon ii, boolean filter){
        this(title,textKey,ii,NONE,filter);
    }

    public ButtonTabbed(String title, ImageIcon ii){
        this(title,null,ii,NONE,true);
    }

    public ButtonTabbed(String title, String textKey, ImageIcon ii, byte orientationText, boolean filter){
        this.textKey = textKey;
        this.title = title;
        this.actionCommand = title!=null?title:ii.getDescription();
        this.isFilterImage = filter;
        //FontHelper fh = new FontHelper();
        //font = fh.font(12);

        IButtonUI buttonUI = GlobalUI.getInstance().getTheme().getButtonUI();
        this.colorBack = buttonUI.getBackground();
        this.colorFore = buttonUI.getForeground();
        this.cBackSelected = buttonUI.getBackgroundSelected();
        this.colorBorder = buttonUI.getColorBorder();
        this.cborderIn = buttonUI.getColorBorderSelected();
        this.colorForeKey = buttonUI.getColorTextKey();
        this.cfkIn = buttonUI.getColorTexteySelected();
        this.cbackIn = buttonUI.getBackgroundMouseEntered();
        this.coloSelect = buttonUI.getColorSelected();

        this.orientationText = orientationText;
        if(title != null) {
            this.orientationImageX = (orientationText == NONE) ? LEFT : RIGHT;
        }else
            this.orientationImageX = CENTER;

        this.orientationImageY = CENTER;

        this.paintBack = true;
        this.paintBorder = false;

        setOpaque(false);
        /*setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);*/

        if(ii!=null) {
            //boolean rtn = setColorImage(GlobalUI.getInstance().getTheme().getColorImageButton());
            if (this.isFilterImage) {
                image0 = filterImage(ii.getDescription(),buttonUI.getColorImage(),false);//ii.getImage();
                image1 = null;//filterImage(ii.getDescription(),cborderIn,true);//ii.getImage();
                imagePaint = image0;//filterImage(ii.getDescription(),true);//ii.getImage();
            }else {
                image0 = ii.getImage();
                image1 = ii.getImage();
                imagePaint = ii.getImage();
            }
        }

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

        /*if(image0 !=null && title!=null){
            if(w<180)
                w = 180;
        }*/

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
                    width += 5;
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
            else if(orientationText == NONE || orientationText == CENTER || orientationText == TOP) {
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
            cbackPaint = cBackSelected;
            cborderPaint = colorBorder;
            //cfPaint = colorFore;
            cfkPaint = cfkIn;
            if(image1!=null)
                imagePaint = image0;
        }else {
            if(pressed) {
                //cfPaint = colorSelected;
                if(image1!=null)
                    imagePaint = image1;
            }
            else {
                //cfPaint = colorFore;
                imagePaint = image0;
            }

            if (in) {
                cbackPaint = cbackIn;
                cborderPaint = cborderIn;
                //cfPaint = colorSelected;
                cfkPaint = cfkIn;

            } else if (out) {
                cbackPaint = colorBack;
                cborderPaint = colorBorder;
                cfkPaint = colorForeKey;
                //cfPaint = colorFore;
            }
        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(in) {
            g2.setColor(cbackIn);
            g2.fillRoundRect(0, 0, getWidth() , getHeight() , 2, 2);
        }

        if(paintSelected) {
            g2.setColor(coloSelect);
            g2.fillRoundRect(0, getHeight() -3, getWidth() , getHeight() , 2, 2);
        }

        if(paintBorder) {
            g2.setColor(cborderPaint);
            g2.drawRoundRect(0, 0, getWidth()-1 , getHeight()-1 , 2, 2);
        }

        if(imagePaint!=null)
            g2.drawImage(imagePaint,posx_ii,posy_ii,null);

        if(title!=null) {
            g2.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2.setFont(font);
            g2.setColor(colorFore);
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
        if(in){
            ActionEvent ae=new ActionEvent(e.getSource(), e.getID(),actionCommand);//me.paramString());
            fireActionPerformed(ae);

            if(buttonGroup!=null)
                buttonGroup.clearSelection();
            //System.out.println(title+" "+paintBack+" && "+paintClickSelected+" "+(paintBack && paintClickSelected));
            if(paintBack && paintClickSelected)
                paintSelected =!paintSelected;
        }
        repaint();
    }

    public void selected(){
        //in = true;
        //pressed = true;
        paintSelected = true;
        repaint();
    }

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
        this.cBackSelected = cbSelected;
    }

    private void setColorBack(Color colorBack) {
        this.colorBack = colorBack;
    }


    public void setColorFore(Color colorFore) {
        this.colorFore = colorFore;
    }

    public void setColorBorder(Color colorBorder) {
        this.colorBorder = colorBorder;
    }

    /*public void setImage(ImageIcon i) {
        image0 =i.getImage();
        imagePaint = image0;

        setDimension();
        repaint();
        revalidate();
    }*/

/*    public void setTitle(String title){
        this.title = title;
        setDimension();
        //updateUI();
        repaint();
        revalidate();
    }*/

    public void addActionListener(ActionListener al){
        listenerList.add(ActionListener.class, al);
    }

    public void setActionCommand(String actionCommand){
        this.actionCommand = actionCommand;
    }

    /*private void actionPerformed(ActionEvent event) {
        fireActionPerformed(event);
    }*/

    private void fireActionPerformed(ActionEvent event) {
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

    //Pintar de fondo el buton cuando se pasa el mouse
    protected void setPaintBackEnteredMouse(boolean s){
        paintSelected = s;
        repaint();
    }


    /*public boolean isSelected(){
        return paintSelected;
    }*/

    public void setGroup(ButtonGroup bg){
        buttonGroup = bg;
    }

    //Fijar el button cuando se hace click
    public void setPaintSelected(boolean paintClickSelected){
        this.paintClickSelected = paintClickSelected;
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
    /*private boolean setColorImage(Color col) {
        boolean filter = col != null;
        if (filter){
            red = col.getRed();
            green = col.getGreen();
            blue = col.getBlue();
        }

        return filter;
    }*/

    private BufferedImage filterImage(String fileName,Color color,boolean filterCol){

        boolean filter = color!=null;

        BufferedImage image = null;

        if(filter) {
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            File fime = new File(fileName);
            if (fime.exists()) {
                try {
                    image = ImageIO.read(fime);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (image != null) {
                    int alto = image.getHeight();
                    int ancho = image.getHeight();
                    //Color array[][] = new Color[ancho][alto];
                    Color col;
                    for (int y = 0; y < alto; y++) {
                        for (int x = 0; x < ancho; x++) {
                            int srcPixel = image.getRGB(x, y);
                            //int p = (col.getBlue()+col.getGreen()+col.getRed())/3;
                            //System.out.println(col.getAlpha());

                            int alpha = (srcPixel >> 24) & 0xff;
                        /*int red = (srcPixel >> 16) & 0xff;
                        int green = (srcPixel >> 8) & 0xff;
                        int blue = (srcPixel >> 0) & 0xff;

                       /*if(srcPixel!=0){
                           array[y][x]=new Color(255,255,255,1);
                       }else*/
                            if (filterCol)
                                col = filterColor(new Color(red, green, blue, alpha));
                            else
                                col = new Color(red, green, blue, alpha);

                            image.setRGB(x, y, col.getRGB());
                        }
                    }

                }
            }
        }
        return image;
    }

    private Color filterColor(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
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
        Color rtn = new Color(r,g,b,color.getAlpha());

        return rtn;
    }
}
