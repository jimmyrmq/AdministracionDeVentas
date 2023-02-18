package view.frame.producto;

import com.djm.util.LayoutPanel;
import util.IPanel;
import view.frame.ui.component.Button;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelProducto  implements IPanel {

    private boolean open = false;
    private JPanel pPrincipal;
    public PanelProducto(){}
    private void createPanel(){
        pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setOpaque(false);

        JSeparator sep1 = new JSeparator(JSeparator.VERTICAL);
        sep1.setForeground(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder());
        sep1.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());

        PListaProducto plist = new PListaProducto();
        GlobalProduct.getInstance().detalleProducto = new DetalleProducto();
        PCategoria pcat = new PCategoria();

        GlobalProduct.getInstance().init();

        pPrincipal.add(pToolBar(), LayoutPanel.constantePane(0, 0, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        pPrincipal.add(GlobalProduct.getInstance().detalleProducto.getPanel(), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 10, 0.0f, 1.0f));
        pPrincipal.add(sep1, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 10, 10, 10, 0, 0.0f, 1.0f));
        pPrincipal.add(plist.getPanel(), LayoutPanel.constantePane(2, 1, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 10, 10, 20, 0, 0.0f, 1.0f));
        pPrincipal.add(pcat.getPanel(), LayoutPanel.constantePane(3, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 10, 10, 20, 10, 1.0f, 1.0f));

    }

    private JPanel pToolBar(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        ActionListenerProduct actionListenerProduct = new ActionListenerProduct();

        Button importar = new Button("Importar",new ImageIcon("icon/import.png"));
        importar.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        importar.setOrientationText(Button.BUTTOM);
        importar.setOrientationImage(Button.CENTER,Button.TOP);

        Button export = new Button("Exportar",new ImageIcon("icon/export.png"));
        export.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        export.setOrientationText(Button.BUTTOM);
        export.setOrientationImage(Button.CENTER,Button.TOP);

        Button category = new Button("Categoria",new ImageIcon("icon/category.png"));
        category.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        category.setOrientationText(Button.BUTTOM);
        category.setOrientationImage(Button.CENTER,Button.TOP);

        Button marca = new Button("Marca",new ImageIcon("icon/marca.png"));
        marca.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        marca.setOrientationText(Button.BUTTOM);
        marca.setOrientationImage(Button.CENTER,Button.TOP);

        Button printer = new Button("Imprimir",new ImageIcon("icon/printer.png"));
        printer.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        printer.setOrientationText(Button.BUTTOM);
        printer.setOrientationImage(Button.CENTER,Button.TOP);

        Button actualizar = new Button("Actualizar",new ImageIcon("icon/update.png"));
        actualizar.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        actualizar.setOrientationText(Button.BUTTOM);
        actualizar.setOrientationImage(Button.CENTER,Button.TOP);
        actualizar.setActionCommand("UPDATE_DATA_PRODUCTO");
        actualizar.addActionListener(actionListenerProduct);

        JSeparator sep0 = new JSeparator(JSeparator.VERTICAL);
        sep0.setForeground(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder());
        sep0.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());

        JSeparator sep1 = new JSeparator(JSeparator.VERTICAL);
        sep1.setForeground(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder());
        sep1.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());

        JSeparator sep2 = new JSeparator(JSeparator.VERTICAL);
        sep2.setForeground(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder());
        sep2.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());

        panel.add(marca, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(category, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 10, 0, 0, 0.0f, 0.0f));
        panel.add(sep0, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 5, 10, 5, 0, 0.0f, 0.0f));
        panel.add(printer, LayoutPanel.constantePane(3, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 10, 0, 0, 0.0f, 0.0f));
        panel.add(sep1, LayoutPanel.constantePane(4, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 5, 10, 5, 0, 0.0f, 0.0f));
        panel.add(importar, LayoutPanel.constantePane(5, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 10, 0, 0, 0.0f, 0.0f));
        panel.add(export, LayoutPanel.constantePane(6, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 10, 0, 0, 0.0f, 0.0f));
        panel.add(sep2, LayoutPanel.constantePane(7, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 5, 10, 5, 0, 0.0f, 0.0f));
        panel.add(actualizar, LayoutPanel.constantePane(8, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 10, 0, 0, 1.0f, 0.0f));

        return panel;
    }
    @Override
    public void init(){

        createPanel();

        open = true;
    }

    @Override
    public String getTitle() {
        return "Producto";
    }

    @Override
    public JPanel getPanel() {
        return pPrincipal;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean isClosed() {
        return !open;
    }

    @Override
    public void close() {
        open = false;
    }
}
