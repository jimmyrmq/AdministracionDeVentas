package view.frame.producto;

import com.djm.util.LayoutPanel;
import util.IPanel;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelProducto  implements IPanel {

    private boolean open = false;
    private JPanel pPrincipal;
    public PanelProducto(){
        pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setOpaque(false);

        DetalleProducto dp = new DetalleProducto();
        PCategoria pcat = new PCategoria();

        pPrincipal.add(pcat.getPanel(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 1.0f));
        pPrincipal.add(dp.getPanel(), LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 1.0f, 1.0f));

    }
    @Override
    public void init() {
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
