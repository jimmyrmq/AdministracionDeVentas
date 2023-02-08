package view.frame.ui.themes;


import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class BlackTabbedPaneUI extends BasicTabbedPaneUI {
    private Color colorSel = GlobalUI.getInstance().getTheme().getBackgroundButtonSelected();//new Color(98, 165, 206);//GlobalUI.getInstance().getBackgroundButton();//new Color(127,147,170);//ColorSystem.PanelPrincipal;
    private Color colorBack = GlobalUI.getInstance().getTheme().getBackgroundButton();//new Color(127,147,170);//ColorSystem.PanelPrincipal;
    private Color colorUnSel = GlobalUI.getInstance().getTheme().getBackgroundButtonSelected();
    private Color colorContentBorder = new Color(98,123,151);//ColorSystem.BorderLabel.getColor();

    public BlackTabbedPaneUI(){}

    @Override
    protected void paintTabBackground( Graphics g, int tabPlacement,
                                       int tabIndex, int x, int y, int w, int h, boolean isSelected ) {
        int y2 = 0;
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(colorBack);
        if(isSelected) {
            y2 = 1;
            //g2.setColor(colorUnSel );
            g2.fillRoundRect(rects[tabIndex].x, rects[tabIndex].y-1, rects[tabIndex].width, y2+1,5,5);

            g2.setPaint(null);
        }

        g2.fillRect(rects[tabIndex].x, y2-1,rects[tabIndex].width, rects[tabIndex].height+1);
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2 = (Graphics2D)g;
        int l = x + w;
        g2.setColor(colorSel);
        if(isSelected) {
            //g2.drawArc(x, y, 5, 5, 90, 90);
            //g2.drawArc(l-5, y, 5, 5, 90, -90);

            /*g2.drawLine(x, y+3, x, h);
            g2.drawLine(x+3, 0, l-2, 0);
            g2.drawLine(l, y+3, l, h);*/

            g2.setStroke(new BasicStroke(7));
            g2.drawLine(x+3, h-2, l-2, h-2);
        }else {
            g2.drawLine(x + 3, h - 2, l - 2, h - 2);
        }


    }

    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex ){
        if ( tabPane.getTabCount() > 0  ){
            Graphics2D g2 = (Graphics2D)g;
            int width = tabPane.getWidth();
            //int height = tabPane.getHeight();
            Insets insets = tabPane.getInsets();

            int x = insets.left;
            int y = 25;
            int w = width - insets.right - insets.left;
            //int h = height - insets.top - insets.bottom;
            g2.setColor(GlobalUI.getInstance().getTheme().getBackgroundButton());
            g.fillRect(0,0,w-1,y-1);
            g2.setColor(GlobalUI.getInstance().getTheme().getBackgroundButton());//new Color(180, 188, 204));//colorContentBorder);
            g2.drawLine(x,y,w,y);//Linea Entera
            g2.setStroke(new BasicStroke(1));
        }
    }
}
