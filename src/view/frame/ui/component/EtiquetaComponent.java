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

public class EtiquetaComponent extends JComponent {
    private TipoEtiqueta tipoEtiqueta;
    private final int width = 90;
    private final int height = 14;
    private String title ;
    private Font font = GlobalUI.getInstance().getTheme().getPanelUI().getFont();
    private int posx_tt, posy_tt;
    private boolean selected;
    private Color colBack;
    private Color colBackSelected;
    private Color colBackNoSelected;

    public EtiquetaComponent(){
        setOpaque(false);
        setFocusable(true);
        setPreferredSize(new Dimension(width,height));
        colBackNoSelected = GlobalUI.getInstance().getTheme().getTableUI().getBackground();
        colBackSelected = GlobalUI.getInstance().getTheme().getTableUI().getBackgroundSelected();

        String fnme = font.getFontName();
        font = new Font(fnme,0,11);
        //setTipoEtiqueta(tipoEtiqueta);
        this.tipoEtiqueta = TipoEtiqueta.NONE;
    }

    public void setTipoEtiqueta(TipoEtiqueta tipoEtiqueta) {
        this.tipoEtiqueta = tipoEtiqueta;
        title = tipoEtiqueta.getTitle();
        //System.out.println(">>>>>"+title+"<<<<<");
        calculateTextXY();
        repaint();
    }

    private void calculateTextXY(){
        FontMetrics fmt = getFontMetrics(font);
        posx_tt = ((this.width-fmt.stringWidth(title))/2);
        posy_tt = (((height - fmt.getHeight()) / 2) + fmt.getHeight())-1;
    }

    /*public TipoEtiqueta getTipoEtiqueta() {
        return tipoEtiqueta;
    }*/

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(title!=null) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            colBack = selected ? colBackSelected : colBackNoSelected;
            g2.setColor(colBack);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(tipoEtiqueta.getColor());
            g2.fillRoundRect(3, 2, getWidth() - 5, getHeight() - 4, 4, 4);

            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2.setFont(font);
            g2.setColor(Color.WHITE);
            g2.drawString(title, posx_tt, posy_tt);
        }

        g2.dispose();
        g.dispose();
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }
}
