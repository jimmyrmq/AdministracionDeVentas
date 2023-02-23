package view.frame.ui.component;

import com.djm.ui.themes.checkbox.ICheckBoxUI;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SelectedColor  extends JComponent implements MouseListener {//, FocusListener {
    private SelectedColorGroup buttonGroup;
    private boolean selected;
    //private boolean pressed = false;
    private boolean enabled = true;
    private boolean clic = false;
    private boolean in = false;
    //private boolean focus = false;
    private final int WIDTH_BOX = 24;
    private final int HEIGHT_BOX = 24;

    private Color colorIn = null;
    private Color colorBack = null;//new Color(97,99,101);
    private Color colorBorderFocus = null;// GlobalUI.getInstance().getTheme().getColorBorderFocusField();
    private int width = 20;
    private int heght = 20;

    public SelectedColor(Color color) {

        ICheckBoxUI checkBoxUI = GlobalUI.getInstance().getTheme().getCheckBoxUI();

        colorIn = checkBoxUI.getBackgroundAction();
        colorBack = color;
        colorBorderFocus = GlobalUI.getInstance().getTheme().getButtonUI().getBackgroundSelected();

        Dimension dim = new Dimension(width, heght);
        setPreferredSize(dim);
        setSize(dim);

        //addFocusListener(this);
        addMouseListener(this);
    }


    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        if (selected || (in && enabled)) {
            g2.setColor(colorBorderFocus);
            g2.setStroke(new BasicStroke(6));
            g2.drawRect(0, 0, WIDTH_BOX-5, HEIGHT_BOX-5);
        }

        //Background
        colorIn = in ? colorIn : colorBack;

        g2.setColor(colorIn);
        g2.fillRoundRect(3, 3, 14 , 14 ,4, 4);

    }

    public void setSelected(boolean selected) {
        this.selected = enabled && selected;
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clic = false;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //pressed = true;
        clic = true;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //pressed = false;
        if (e.getButton() == MouseEvent.BUTTON1 && enabled && in && clic) {

            if(buttonGroup!=null)
                buttonGroup.clearSelection();

            requestFocus();
            selected = !selected;
            repaint();
        }
        clic = false;

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        in = true;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        in = false;
        repaint();
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Color getColor(){
        return colorBack;
    }

    public void setGroup(SelectedColorGroup bg){
        buttonGroup = bg;
    }
}
