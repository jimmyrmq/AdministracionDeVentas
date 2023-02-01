package view.frame.dashboard;

import com.djm.util.LayoutPanel;
import util.SystemProperties;
import view.frame.component.Button;
import view.frame.component.ButtonGroup;
import view.frame.themes.GlobalUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Dashboard {
    private JPanel panel;
    public Dashboard(ActionButton actionListener){
        panel = new JPanel(new GridBagLayout());
        SystemProperties sp = SystemProperties.getInstance();

        Button bProducto = new Button(sp.getValue("produtos.buttom.dashboard"), "F3",new ImageIcon("icon/product.png"));
        Button bStock = new Button(sp.getValue("stock.buttom.dashboard"), new ImageIcon("icon/stock.png"));
        Button bCliente = new Button(sp.getValue("cliente.buttom.dashboard"), new ImageIcon("icon/cliente.png"));
        Button bInforme = new Button(sp.getValue("informe.buttom.dashboard"), new ImageIcon("icon/informe.png"));
        Button bFormaPago = new Button(sp.getValue("formapago.buttom.dashboard"), new ImageIcon("icon/fpago.png"));
        Button bImpuesto = new Button(sp.getValue("impuesto.buttom.dashboard"), new ImageIcon("icon/impuesto.png"));
        Button bPromociones = new Button(sp.getValue("promociones.buttom.dashboard"), new ImageIcon("icon/promo.png"));
        Button bConfiguracion = new Button(sp.getValue("configuracion.buttom.dashboard"), new ImageIcon("icon/config.png"));
        Button bUsuario = new Button(sp.getValue("usuario.buttom.dashboard"), new ImageIcon("icon/user.png"));
        Button bSalir = new Button(sp.getValue("salir.buttom.dashboard"), new ImageIcon("icon/exit.png"));
        bProducto.setActionCommand(sp.getValue("produtos.buttom.dashboard.id"));

        bSalir.setNotSelect(true);
        bSalir.setColorBack(Color.RED);
        bSalir.setColorFore(Color.WHITE);

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
        bSalir.addActionListener(actionListener);

        panel.add(bProducto, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 10, 10, 0, 10, 0.0f, 0.0f));
        panel.add(bStock, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 10, 0, 10, 0.0f, 0.0f));
        panel.add(bCliente, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 10, 0, 10, 0.0f, 0.0f));
        panel.add(bInforme, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 10, 0, 10, 0.0f, 0.0f));
        panel.add(bFormaPago, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 10, 0, 10, 0.0f, 0.0f));
        panel.add(bImpuesto, LayoutPanel.constantePane(0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 10, 0, 10, 0.0f, 0.0f));
        panel.add(bPromociones, LayoutPanel.constantePane(0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 10, 0, 10, 0.0f, 0.0f));
        panel.add(bConfiguracion, LayoutPanel.constantePane(0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 10, 0, 10, 0.0f, 0.0f));
        panel.add(bUsuario, LayoutPanel.constantePane(0, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.PAGE_START, 5, 10, 0, 10, 0.0f, 1.0f));
        panel.add(bSalir, LayoutPanel.constantePane(0, 9, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 0, 10, 10, 0.0f, 0.0f));

        Border roundedBorder = new LineBorder(GlobalUI.getInstance().getColorBorder(), 1, false);
        panel.setBorder(roundedBorder);
    }

    public JPanel getPanel(){
        return panel;
    }

}
