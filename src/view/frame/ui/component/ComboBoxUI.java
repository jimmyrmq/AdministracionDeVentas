package view.frame.ui.component;

import view.frame.ui.component.border.BottomRoundedCornerBorder;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class ComboBoxUI extends BasicComboBoxUI {
    @Override
    protected void installDefaults() {
        super.installDefaults();
        LookAndFeel.uninstallBorder(comboBox);
    }

   /* @Override
    public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus){
        Graphics2D g2 = (Graphics2D) g;
        if(hasFocus) {
            g2.setColor(GlobalUI.getInstance().getTheme().getTextUI().getBackground());
            g2.fillRoundRect(0, 0, bounds.width + 1, bounds.height + 1, 4, 4);
        }

    }*/
    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(GlobalUI.getInstance().getTheme().getTextUI().getBackground());
        g2.fillRoundRect(0, 0, bounds.width+1, bounds.height+1,4,4);
    }
    @Override
    protected JButton createArrowButton() {
        return new ArrowButton();
    }

    private class ArrowButton extends JButton {
        private int width;
        private int height;

        public ArrowButton() {
            setContentAreaFilled(false);
            setBorder(new EmptyBorder(5, 5, 5, 5));
            //setBackground(new Color(241, 13, 13));

        }

        @Override
        public void paint(Graphics grphcs) {
            //super.paint(grphcs);

            width = getWidth();
            height = getHeight();
            //System.out.println(width+" "+height);

            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(GlobalUI.getInstance().getTheme().getPanelUI().getColorBackgoundControl());

            g2.fillRoundRect(0, 0, width, height, 1, 1);
            g2.setColor(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder());
            g2.setStroke(new BasicStroke(1));//, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

            g2.drawLine(0, 0, 0, height);
            g2.drawLine(6, 8, 10, 12);
            g2.drawLine(14, 8, 10, 12);

            g2.dispose();
        }
    }

    @Override
    protected ComboPopup createPopup() {
        BasicComboPopup pop = new BasicComboPopup(comboBox) /* {
           @Override
            protected JScrollPane createScroller() {
                list.setFixedCellHeight(20);
                JScrollPane scroll = new JScrollPane(list);
                scroll.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
                ScrollBarCustom sb = new ScrollBarCustom();
                sb.setUnitIncrement(30);
                sb.setForeground(GlobalUI.getInstance().getTheme().getPanelUI().getForeground());
                scroll.setVerticalScrollBar(sb);
                return scroll;
            }
        }*/;

        pop.setBorder(new BottomRoundedCornerBorder());//LineBorder(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder(), 1));
        return pop;
    }

    /*@Override
    public void paint(Graphics grphcs, JComponent jc) {
        //super.paint(grphcs, jc);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        int width = jc.getWidth();
        int height = jc.getHeight();

        g2.setColor(new Color(245, 204, 43));
        g2.fillRect(1, 1, width-100 , height);
        //createHintText(g2);
        //createLineStyle(g2);
        g2.dispose();
    }*/
}