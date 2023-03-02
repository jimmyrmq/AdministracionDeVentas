package view.frame.ui.glass;

import com.djm.ui.themes.panel.IPanelUI;
import com.djm.util.LayoutPanel;
import util.Global;
import view.frame.main.FrameMain;
import view.frame.ui.component.Button;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
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
    private JPanel  principal;

    public PanelGlass(IPanelGlass panelGlass){
        super(new GridBagLayout());

        setOpaque(false);

        IPanelUI panelUI = GlobalUI.getInstance().getTheme().getPanelUI();

        JLabel lTitle = new JLabel(panelGlass.getTitle());
        Font f0 = panelUI.getFont();
        Font f1 = new Font(f0.getName(),1,14);
        lTitle.setFont(f1);
        principal= new JPanel(new GridBagLayout());

        Dimension dim0 = panelGlass.getPanel().getPreferredSize();
        Dimension dim1 = new Dimension(200,0);
        if (dim0.getWidth() > dim1.getWidth()) {
            dim1 = new Dimension((int) dim0.getWidth() + 20, (int) dim0.getHeight());
        }

        principal.setOpaque(true);
        principal.setSize(dim1.getSize());
        principal.setPreferredSize(dim1);
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
        principal.add(panelGlass.getPanel(), LayoutPanel.constantePane(0, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 15, 0, 10, 0, 1.0f, 1.0f));

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

    private class ListenerGlass implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {}

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
    private void close(){
        Global.getInstance().closeGlassPane();
    }
}
