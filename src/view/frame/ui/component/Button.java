package view.frame.ui.component;

import com.djm.ui.themes.button.IButtonUI;
import view.frame.ui.themes.GlobalUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.accessibility.Accessible;
import javax.swing.*;

public class Button extends JComponent implements  FocusListener,MouseMotionListener, MouseListener , Accessible {
    private String actionCommand;
    private String title;
    private String textKey;
    private String rutaImage1;
    //private String rutaImage2;
    private boolean focus =false;
    private boolean in =false;
    private boolean out =true;
    private boolean pressed = false;
    private boolean sizeDefault = false;
    private Font font = new Font("Segoe UI",0,12);
    private Font fontKey = new Font("Segoe UI",1,10);
    private Color colorBack = null;
    private Color colorFore = new Color(0,0,0);
    private Color colorForeKey;// = new Color(140, 140, 140);
    private Color colorBorder = new Color(0,0,0);
    private Color cbSelected = new Color(0,0,0);
    private Color cbackPaint = colorBack;
    private Color cborderPaint = colorBorder;
    private Color cfPaint = colorFore;
    private Color cfkPaint = colorForeKey;
    private Color cbackIn = new Color(82,6,140);
    private Color cfkIn = new Color(217, 214, 214);
    private Color cborderIn ;//= new Color(82,6,140);
    private Color colorAccion;//= new Color(82,6,140);
    private Color colorBorderSelect ;//= new Color(82,6,140);
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
    //private boolean paintSelected = false;
    //private boolean paintClickSelected = true;
    public static final byte NONE = -1;
    public static final byte CENTER = 0;
    public static final byte TOP = 1;
    public static final byte BUTTOM = 2;
    public static final byte LEFT = 3;
    public static final byte RIGHT = 4;
    private byte orientationText = LEFT;
    private byte orientationImageX = RIGHT;
    private byte orientationImageY = CENTER;
    private int posx_tk = 0;
    private int posy_tk = 0;
    private boolean isFilterImage ;

    public Button(ImageIcon ii) {
        this(null,null,ii,NONE,true);
    }
    public Button(ImageIcon ii, boolean filter) {
        this(null,null,ii,NONE,filter);
    }
    public Button(String title) {
        this(title,null,null,NONE,false);
    }
    public Button(String title, String textKey, ImageIcon ii){
        this(title,textKey,ii,NONE,true);
    }
    public Button(String title, String textKey, ImageIcon ii, boolean filter){
        this(title,textKey,ii,NONE,filter);
    }

    public Button(String title, ImageIcon ii){
        this(title,null,ii,NONE,true);
    }

    public Button(String title, String textKey, ImageIcon ii, byte orientationText, boolean filter){
        setOpaque(false);

        this.textKey = textKey;
        this.title = title;
        this.actionCommand = title!=null?title:ii.getDescription();
        this.isFilterImage = filter;
        //FontHelper fh = new FontHelper();
        //font = fh.font(12);

        this.orientationText = orientationText;
        if(title != null) {
            this.orientationImageX = (orientationText == NONE) ? LEFT : RIGHT;
        }else
            this.orientationImageX = CENTER;

        this.orientationImageY = CENTER;

        /*Color cb = buttonUI.getBackground();//GlobalUI.getInstance().getTheme().getBackgroundButton();
        Color cme = buttonUI.getBackgroundMouseEntered();
        Color cf = buttonUI.getForeground();//GlobalUI.getInstance().getTheme().getForeground();
        Color cbs = buttonUI.getBackgroundSelected();//GlobalUI.getInstance().getTheme().getBackgroundButtonSelected();
        Color cbrd = buttonUI.getColorBorder();//GlobalUI.getInstance().getTheme().getColorBorderButton();
        Color cftk = buttonUI.getColorTextKey();//GlobalUI.getInstance().getTheme().getColorTextKeyButton();
        Color cftks = buttonUI.getColorTexteySelected();//GlobalUI.getInstance().getTheme().getColorTextButtonKeySelected();
        *///Color cs = buttonUI.getColorSelected();

        IButtonUI buttonUI = GlobalUI.getInstance().getTheme().getButtonUI();
        setButtonUI(buttonUI);

        if(ii!=null) {
            //boolean rtn = setColorImage(GlobalUI.getInstance().getTheme().getColorImageButton());
            if (this.isFilterImage) {
                rutaImage1 = ii.getDescription();
                //rutaImage2 = rutaImage1;
                image0 = ColorFilter.filterImage(rutaImage1,buttonUI.getColorImage(),false);//ii.getImage();
                image1 = image0;//filterImage(ii.getDescription(),cbs,true);//ii.getImage();
                imagePaint = image0;//filterImage(ii.getDescription(),true);//ii.getImage();
            }else {
                image0 = ii.getImage();
                image1 = ii.getImage();
                imagePaint = ii.getImage();
            }
        }

        //setColorBack(cb);
        /*setColorFore(cf);
        setColorBackSelected(cbs);
        setColorBorder(cbrd);*/

        //setRequestFocusEnabled(true);
        setDimension();

        addMouseMotionListener(this);
        addMouseListener(this);
        addFocusListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        if (in) {
            cbackPaint = cbackIn;
            cborderPaint =  cborderIn;
        }
        else if (out) {
            cbackPaint = colorBack;
            cborderPaint = focus?colorBorderSelect:colorBorder;
            //cfkPaint = colorForeKey;
            //cfPaint = colorFore;
        }

        if(pressed) {
            //cfPaint = colorSelected;
            imagePaint = image1;
            cborderPaint = colorBorderSelect;
            cbackPaint = cbSelected;
        }
        else {
            cfPaint = colorFore;
            imagePaint = image0;
        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(cbackPaint);
        g2.fillRoundRect(0, 0, getWidth() , getHeight() , 4, 4);

        g2.fillRoundRect(0, 0, getWidth() , getHeight() , 2, 2);

        /*if(paintSelected) {
            g2.setColor(cbackPaint);
            g2.fillRoundRect(0, getHeight() -3, getWidth() , getHeight() , 2, 2);
        }*/

        g2.setColor(cborderPaint);
        g2.drawRoundRect(0, 0, getWidth()-1 , getHeight()-1 , 4, 4);

        if(imagePaint!=null)
            g2.drawImage(imagePaint,posx_ii,posy_ii,null);

        //g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        if(title!=null) {
            g2.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2.setFont(font);
            g2.setColor(cfPaint);
            g2.drawString(title, posx_tt, posy_tt);
        }

        if(textKey!=null) {
            g2.setFont(fontKey);
            g2.setColor(cfkPaint);
            g2.drawString(textKey, posx_tk, posy_tk);
        }

        g2.dispose();
        g.dispose();
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
            h = fmt.getHeight()+5;
            w += fw + 20;
        }

        if(image0 !=null) {
            w += dimy_ii + 5;
            h = dimy_ii+5;
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
            requestFocus();

            if(buttonGroup!=null)
                buttonGroup.clearSelection();

            /*if(paintBack && paintClickSelected)
                paintSelected =!paintSelected;*/
        }
        repaint();
    }

/*
    public void selected(){
        //in = true;
        //pressed = true;
        paintSelected = true;
        repaint();
    }
*/

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
        this.cbSelected = cbSelected;
    }
/*
    private void setColorBack(Color colorBack) {
        this.colorBack = colorBack;
        cbackIn = filterColor(colorBack);//new Color(r,g,b);
    }*/


    private void setColorFore(Color colorFore) {
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


    private void actionPerformed(ActionEvent event) {
        fireActionPerformed(event);
    }

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

    /*//Pintar de fondo el buton cuando se pasa el mouse
    protected void setPaintBackEnteredMouse(boolean s){
        paintSelected = s;
        repaint();
    }*/


    /*public boolean isSelected(){
        return paintSelected;
    }*/

    public void setGroup(ButtonGroup bg){
        buttonGroup = bg;
    }

    //Fijar el button cuando se hace click
    /*public void setPaintSelected(boolean paintClickSelected){
        this.paintClickSelected = paintClickSelected;
    }*/

    public void setFont(Font font){
        this.font = font;
    }


    public void setOrientationImageX(byte orientationImage){
        this.orientationImageX = orientationImage;
        calculePosicion();
    }

    public void setOrientationImageY(byte orientationImage){
        this.orientationImageX = orientationImage;
        calculePosicion();
    }


    @Override
    public void setBackground(Color colorBack){
        this.colorBack = colorBack;
    }

    @Override
    public void setForeground(Color colorFore){
        this.colorFore = colorFore;
    }

    public void setButtonUI(IButtonUI buttonUI){
        this.colorBack = buttonUI.getBackground();
        this.colorFore = buttonUI.getForeground();
        this.colorBorder = buttonUI.getColorBorder();
        this.cborderIn = buttonUI.getColorBorderSelected();
        this.colorForeKey = buttonUI.getColorTextKey();
        this.cfkIn = buttonUI.getColorTexteySelected();
        this.cbackIn = buttonUI.getBackgroundMouseEntered();
        //this.colorSelect = buttonUI.getColorSelected();
        this.colorAccion = buttonUI.getBackgroundAction();
        this.colorBorderSelect = buttonUI.getColorBorderSelected();
        this.cbSelected =  buttonUI.getBackgroundSelected();

        if(image0!=null && this.isFilterImage) {
            image0 = ColorFilter.filterImage(rutaImage1 ,buttonUI.getColorImage(),false);//ii.getImage();
            image1 = image0;//filterImage(ii.getDescription(),cbs,true);//ii.getImage();
            imagePaint = image0;//filterImage(ii.getDescription(),true);//ii.getImage();
        }

        repaint();
    }

    @Override
    public void focusGained(FocusEvent e) {
        focus = true;
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        focus = false;
        repaint();
    }
}
