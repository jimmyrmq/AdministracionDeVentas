package view.frame.ui.component.border;

import view.frame.ui.themes.GlobalUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class BorderPopupMenuComboBoxComboBox extends BorderComboBox {//AbstractBorder {//
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int r = 4;
        int w = width - 1;
        int h = height - 1;

        Path2D p = new Path2D.Double();
        p.moveTo(x, y);
        p.lineTo(x, y + h - r);
        p.quadTo(x, y + h, x + r, y + h);
        p.lineTo(x + w - r, y + h);
        p.quadTo(x + w, y + h, x + w, y + h - r);
        p.lineTo(x + w, y);
        p.closePath();
        Area round = new Area(p);

        //Pitamos el corner del round
        Container parent = c.getParent();
        if (parent != null) {
            g2.setPaint(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
            Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
            corner.subtract(round);
            g2.fill(corner);
        }

        g2.setPaint(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder());//c.getForeground());
        g2.draw(round);
        g2.setPaint(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());//c.getBackground());
        g2.drawLine(x + 1, y, x + width - 2, y);
        g2.dispose();
    }
}