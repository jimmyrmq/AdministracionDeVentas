package view.frame.ui;

import com.djm.ui.themes.global.ITheme;
import com.djm.ui.themes.panel.IPanelUI;
import com.djm.util.LayoutPanel;
import view.frame.main.FrameMain;
import view.frame.ui.component.Button;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PanelGlass extends JPanel  {
    private Button bCerrar;
    private JPanel pTarea = new JPanel();
    private Component aux;
    public JLabel lTitle = null;
    public PanelGlass(){
        super(new GridBagLayout());
        setOpaque(false);

        pTarea.setOpaque(false);

        lTitle = new JLabel();
        IPanelUI panelUI = GlobalUI.getInstance().getTheme().getPanelUI();
        Font f0 = panelUI.getFont();
        Font f1 = new Font(f0.getName(),0,14);
        lTitle.setFont(f1);
        JPanel  principal= new JPanel(new GridBagLayout())/*{
            protected void paintComponent(Graphics g){
                super.paintComponent(g); // paint the background image and scale it to fill the entire s
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    //g.drawImage();
                g2.setColor(Color.BLUE);
                g2.fillRect(0,0,getWidth(),getHeight());

                g.dispose();
                g2.dispose();
            }
        }*/;


        principal.setOpaque(true);
        principal.setSize(300,0);
        principal.setPreferredSize(new Dimension(300,100));
        principal.setBackground(panelUI.getBackground());
        principal.setBorder(BorderFactory.createMatteBorder(
                0, 0, 0, 1, panelUI.getColorBorder()));

        bCerrar = new Button("Cerrar");
        bCerrar.addActionListener((ae)->{
            close();
        });

        addMouseListener(new ListenerGlass());
        principal.addMouseListener(new ListenerPanel());

        principal.add(lTitle, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 10, 0, 5, 1.0f, 0.0f));
        principal.add(bCerrar, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 5, 0, 0, 5, 0.0f, 0.0f));
        principal.add(pTarea, LayoutPanel.constantePane(0, 1, 2, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_END, 10, 0, 0, 0, 1.0f, 1.0f));

        add(principal, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 37, 0, 0, 0, 1.0f, 1.0f));
    }

    private void setVisible(){
        if(FrameMain.frame.getGlassPane()!=null){
            aux = FrameMain.frame.getGlassPane();
        }
        FrameMain.frame.setGlassPane(this);
        FrameMain.frame.getGlassPane().setVisible(true);
    }

    public void start(String title, JPanel panel){
        setPanel(title, panel);
        setVisible();
    }

    public void setPanel(String title, JPanel panel){
        lTitle.setText(title);
        lTitle.repaint();

        pTarea.removeAll();
        pTarea.add(panel);
        //pTarea.updateUI();
        pTarea.repaint();
        pTarea.revalidate();
        repaint();
        revalidate();
    }

    private class ListenerGlass implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    private class ListenerPanel implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
/*
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paint the background image and scale it to fill the entire s
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        //g.drawImage();
        g2.setColor(Color.BLUE);
        g2.fillRect(0,0,getWidth(),getHeight());

        g.dispose();
        g2.dispose();

    }*/

    private void close(){
        FrameMain.frame.getGlassPane().setVisible(false);
        FrameMain.frame.setGlassPane(aux);
    }
}
