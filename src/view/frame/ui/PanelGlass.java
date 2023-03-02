package view.frame.ui;

import com.djm.ui.themes.panel.IPanelUI;
import com.djm.util.LayoutPanel;
import view.frame.main.FrameMain;
import view.frame.ui.component.Button;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PanelGlass extends JPanel  {
    private Button bCerrar;
    private JPanel pTarea;
    private Component aux;
    public JLabel lTitle = null;
    private JPanel  principal;
    private IPanelGlass panelGlass;

    public PanelGlass(){
        super(new GridBagLayout());
        setOpaque(false);

        pTarea = new JPanel();
        pTarea.setOpaque(true);

        IPanelUI panelUI = GlobalUI.getInstance().getTheme().getPanelUI();
        //pTarea.setBackground(panelUI.getBackground());//Color.RED);//
        //setBackground(panelUI.getBackground());//Color.RED);//

        lTitle = new JLabel();
        Font f0 = panelUI.getFont();
        Font f1 = new Font(f0.getName(),1,14);
        lTitle.setFont(f1);
        principal= new JPanel(new GridBagLayout())/*{
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
        principal.setSize(400,0);
        principal.setPreferredSize(new Dimension(300,100));
        principal.setBackground(panelUI.getBackground());
        principal.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, panelUI.getColorBorder()));

        bCerrar = new Button(new ImageIcon("icon/closed.png"));
        bCerrar.addActionListener((ae)->{
            close();
        });

        bCerrar.setButtonIcon(true);

        addMouseListener(new ListenerGlass());
        principal.addMouseListener(new ListenerPanel());

        principal.add(lTitle, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 5, 1.0f, 0.0f));
        principal.add(bCerrar, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 0, 0, 5, 0.0f, 0.0f));
        principal.add(pTarea, LayoutPanel.constantePane(0, 1, 2, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_END, 15, 0, 0, 0, 1.0f, 1.0f));

        add(principal, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 37, 0, 0, 0, 1.0f, 1.0f));

        KeyStroke SR = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        };

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(SR, "CERRAR_DIALOG");
        ActionMap actionMap = getActionMap();
        actionMap.put("CERRAR_DIALOG", action);
    }

    private void setVisible(){
        if(FrameMain.frame.getGlassPane()!=null){
            aux = FrameMain.frame.getGlassPane();
        }
        FrameMain.frame.setGlassPane(this);
        FrameMain.frame.getGlassPane().setVisible(true);
        panelGlass.init();
    }

    public void start(String title, IPanelGlass panel){
        setPanel(title, panel);
        setVisible();
    }

    public void setPanel(String title, IPanelGlass panel){
        this.panelGlass =  panel;

        lTitle.setText(title);
        lTitle.repaint();
        Dimension dim0 = panel.getPanel().getPreferredSize();
        Dimension dim1 = principal.getPreferredSize();

        if(dim0.getWidth() > dim1.getWidth() ) {
            Dimension dim = new Dimension((int) dim0.getWidth()+20, (int) dim0.getHeight());
            /*setPreferredSize(dim);
            setSize(dim);
            pTarea.setPreferredSize(dim);
            pTarea.setSize(dim);*/
            principal.setPreferredSize(dim);
            principal.setSize(dim);
        }

        /*System.out.println(panel.getPreferredSize()+" "+panel.getSize());
        System.out.println(pTarea.getPreferredSize()+" "+pTarea.getSize());
        System.out.println(principal.getPreferredSize()+" "+principal.getSize());*/

        pTarea.removeAll();
        pTarea.add(panel.getPanel());
        //pTarea.updateUI();
        pTarea.repaint();
        pTarea.revalidate();
        repaint();
        revalidate();
        bCerrar.transferFocus();

    }

    private class ListenerGlass implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {close();}

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
