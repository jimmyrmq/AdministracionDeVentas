package view.frame.ui.component;

import com.djm.ui.themes.panel.IPanelUI;
import com.djm.util.LayoutPanel;
import util.SystemProperties;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class OptionPane {
    private static JDialog dialog;
    private static Button buttons[];
    private static int rtn = -1;
    private static ActionListenerButton actionListenerButton = new ActionListenerButton();
    public final static int OK = 0;
    private static int buttonCancel = -1;
    private static int buttonRequest = -1;

    private final static int QUESTION = 0;
    private final static int INFORMATION = 1;
    private final static int WARNING = 2;
    private final static int ERROR = 3;
    private final static SystemProperties sp = SystemProperties.getInstance();


    public OptionPane(){}

    public static void information(JFrame frame, String message){
        String [] buttons = {sp.getValue("button.aceptar")};
        buttonCancel = 0;
        createDialog(frame,message,INFORMATION,buttons);
    }

    public static void error(JFrame frame, String message){
        String [] buttons = {sp.getValue("button.cerrar")};
        buttonCancel = 0;
        buttonRequest = 0;
        createDialog(frame,message,ERROR,buttons);

    }

    public static int questionYesOrKey(JFrame frame, String message){
        String [] options = {sp.getValue("button.si"),sp.getValue("button.no")};
        buttonCancel = 1;
        buttonRequest = 1;
        createDialog(frame,message,QUESTION,options);
        return rtn;
    }

    public static int questionAcceptOrCancel(JFrame frame, String message){
        String [] options = {sp.getValue("button.aceptar"),sp.getValue("button.cancelar")};
        buttonCancel = 1;
        buttonRequest = 1;
        createDialog(frame,message,QUESTION,options);
        return rtn;
    }

    public static void warning(JFrame frame, String message){
        String [] options = {sp.getValue("button.aceptar")};
        buttonCancel = 0;
        buttonRequest = 0;
        createDialog(frame,message,WARNING,options);
    }

    private static void createDialog(JFrame frame, String message, int type,String[] optionButtons){
        dialog = new JDialog(frame,"Administración",true);
        /*if(buttonCancel == -1) {
            if (optionButtons.length == 1)
                buttonCancel = 0;
        }*/

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        IPanelUI panelUI = GlobalUI.getInstance().getTheme().getPanelUI();
        String icon []={"icon/question.png","icon/information.png","icon/warning.png","icon/error.png"};

        JLabel lIcon = new JLabel(new ImageIcon(icon [type]));

        JLabel lMessage = new JLabel(message);
        lMessage.setBackground(panelUI.getBackground());
        lMessage.setForeground(panelUI.getForeground());

        JPanel pButton = new JPanel(new FlowLayout());
        panel.setOpaque(false);

        int l = optionButtons.length;
        buttons = new Button[l];
        for(int i = 0;i<l;i++){
            buttons[i] = new Button(optionButtons[i]);
            buttons[i].setActionCommand(String.valueOf(i));
            buttons[i].addActionListener(actionListenerButton);
            pButton.add(buttons[i]);
        }

        Container content = dialog.getContentPane();
        content.setLayout(new GridBagLayout());
        content.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());

        content.add(lIcon, LayoutPanel.constantePane(0, 0, 1, 2, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 1.0f));
        content.add(lMessage, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 20, 20, 0, 20, 1.0f, 1.0f));
        content.add(pButton, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 15, 0, 10, 10, 1.0f, 0.0f));

        init();
    }

    private static void init(){
        Dimension dim = new Dimension(290,130);
        dialog.pack();
        Dimension dim2 = dialog.getPreferredSize();

        if(dim2.width > dim.width)
            dim.width = dim2.width;

        if(dim2.height > dim.height)
            dim.height = dim2.height;

        //dialog.setUndecorated(true);
        dialog.setPreferredSize(dim);
        dialog.setSize(dim);
        dialog.setResizable(false);
        //dialog.setDefaultCloseOperation(0);
        dialog.setLocationRelativeTo(null);

        if(buttonCancel!=-1) {
            buttons[buttonRequest].requestFocus();

            InputMap inputMap = buttons[buttonCancel].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            KeyStroke space = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
            inputMap.put(space, "ACTION_KEY");

            ActionMap actionMap = buttons[buttonCancel].getActionMap();
            actionMap.put("ACTION_KEY", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    dialog.setVisible(false);
                    dialog.dispose();
                }
            });

            buttons[buttonCancel].setActionMap(actionMap);
        }

    }

    private static class ActionListenerButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            rtn = Integer.parseInt(e.getActionCommand());
            dialog.setVisible(false);
            dialog.dispose();
        }
    }

}
