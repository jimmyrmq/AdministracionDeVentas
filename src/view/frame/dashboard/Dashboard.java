package view.frame.dashboard;

import com.djm.util.LayoutPanel;
import util.SystemProperties;
import view.frame.ui.component.Button;
import view.frame.ui.component.ButtonTabbed;
import view.frame.ui.component.ButtonGroup;
import view.frame.ui.themes.ButtonExitUI;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Dashboard {
    private JPanel panel;
    public Dashboard(ActionButton actionListener){
        panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        SystemProperties sp = SystemProperties.getInstance();
        //String dirIcon = GlobalUI.getInstance().getTheme().pathIcon();
        //System.out.println(dirIcon);
        ButtonTabbed bProducto = new ButtonTabbed(sp.getValue("produtos.buttom.dashboard"), new ImageIcon("icon/product.png"));
        ButtonTabbed bStock = new ButtonTabbed(sp.getValue("stock.buttom.dashboard"), new ImageIcon("icon/stock.png"));
        ButtonTabbed bCliente = new ButtonTabbed(sp.getValue("cliente.buttom.dashboard"), new ImageIcon("icon/cliente.png"));
        ButtonTabbed bInforme = new ButtonTabbed(sp.getValue("informe.buttom.dashboard"), new ImageIcon("icon/informe.png"));
        ButtonTabbed bFormaPago = new ButtonTabbed(sp.getValue("formapago.buttom.dashboard"), new ImageIcon("icon/fpago.png"));
        ButtonTabbed bImpuesto = new ButtonTabbed(sp.getValue("impuesto.buttom.dashboard"), new ImageIcon("icon/impuesto.png"));
        ButtonTabbed bPromociones = new ButtonTabbed(sp.getValue("promociones.buttom.dashboard"), new ImageIcon("icon/promo.png"));
        ButtonTabbed bConfiguracion = new ButtonTabbed(sp.getValue("configuracion.buttom.dashboard"), new ImageIcon("icon/config.png"));
        ButtonTabbed bUsuario = new ButtonTabbed(sp.getValue("usuario.buttom.dashboard"), new ImageIcon("icon/user.png"));
        Button bSalir = new Button(sp.getValue("salir.buttom.dashboard"), new ImageIcon("icon/closed.png"));

        bProducto.setActionCommand(sp.getValue("produtos.buttom.dashboard.id"));

        bSalir.setButtonUI(new ButtonExitUI());

        ButtonGroup bg = new ButtonGroup();
        bg.add(bProducto);
        bg.add(bStock);
        bg.add(bCliente);
        bg.add(bInforme);
        bg.add(bFormaPago);
        bg.add(bImpuesto);
        bg.add(bPromociones);
        bg.add(bConfiguracion);
        bg.add(bUsuario);

        bProducto.addActionListener(actionListener);
        bStock.addActionListener(actionListener);
        bCliente.addActionListener(actionListener);
        bInforme.addActionListener(actionListener);
        bFormaPago.addActionListener(actionListener);
        bImpuesto.addActionListener(actionListener);
        bPromociones.addActionListener(actionListener);
        bConfiguracion.addActionListener(actionListener);
        bUsuario.addActionListener(actionListener);
        bSalir.addActionListener(actionListener);

        panel.add(bProducto, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(bStock, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 3, 0, 0, 0.0f, 0.0f));
        panel.add(bCliente, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 3, 0, 0, 0.0f, 0.0f));
        panel.add(bInforme, LayoutPanel.constantePane(3, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 3, 0, 0, 0.0f, 0.0f));
        panel.add(bFormaPago, LayoutPanel.constantePane(4, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 3, 0, 0, 0.0f, 0.0f));
        panel.add(bImpuesto, LayoutPanel.constantePane(5, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 3, 0, 0, 0.0f, 0.0f));
        panel.add(bPromociones, LayoutPanel.constantePane(6, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 3, 0, 0, 0.0f, 0.0f));
        panel.add(bConfiguracion, LayoutPanel.constantePane(7, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 3, 0, 0, 0.0f, 0.0f));
        panel.add(bUsuario, LayoutPanel.constantePane(9, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 3, 0, 0, 0.0f, 0.0f));
        panel.add(bSalir, LayoutPanel.constantePane(10, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 0, 0, 0, 10, 1.0f, 0.0f));

        //Border roundedBorder = new LineBorder(GlobalUI.getInstance().getTheme().getColorBorder(), 1, false);
        //Border roundedBorder  = BorderFactory.createMatteBorder(0, 0, 0, 1,GlobalUI.getInstance().getTheme().getColorBorder());// GlobalUI.getInstance().getTheme().getColorBorderField());

        //panel.setBorder(roundedBorder);
    }

    public JPanel getPanel(){
        return panel;
    }

}
