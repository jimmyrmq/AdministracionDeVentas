package view.frame.dashboard;

import com.djm.util.LayoutPanel;
import util.SystemProperties;
import view.frame.ui.component.Button;
import view.frame.ui.component.ButtonGroup;
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
        Button bProducto = new Button(sp.getValue("produtos.buttom.dashboard"), new ImageIcon("icon/product.png"));
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

        //bProducto.setPaintBack(false);
        bProducto.setColorBack(GlobalUI.getInstance().getTheme().getBackground());
        bStock.setColorBack(GlobalUI.getInstance().getTheme().getBackground());
        bCliente.setColorBack(GlobalUI.getInstance().getTheme().getBackground());
        bFormaPago.setColorBack(GlobalUI.getInstance().getTheme().getBackground());
        bPromociones.setColorBack(GlobalUI.getInstance().getTheme().getBackground());
        bInforme.setColorBack(GlobalUI.getInstance().getTheme().getBackground());
        bUsuario.setColorBack(GlobalUI.getInstance().getTheme().getBackground());
        bImpuesto.setColorBack(GlobalUI.getInstance().getTheme().getBackground());
        bConfiguracion.setColorBack(GlobalUI.getInstance().getTheme().getBackground());

        bSalir.setPaintSelected(true);
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
        //panel.add(bSalir, LayoutPanel.constantePane(10, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 0, 10, 10, 0.0f, 0.0f));

        //Border roundedBorder = new LineBorder(GlobalUI.getInstance().getTheme().getColorBorder(), 1, false);
        //Border roundedBorder  = BorderFactory.createMatteBorder(0, 0, 0, 1,GlobalUI.getInstance().getTheme().getColorBorder());// GlobalUI.getInstance().getTheme().getColorBorderField());

        //panel.setBorder(roundedBorder);
    }

    public JPanel getPanel(){
        return panel;
    }

}
