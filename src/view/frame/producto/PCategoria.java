package view.frame.producto;

import com.djm.util.LayoutPanel;
import model.Categoria;
import view.frame.ui.ListCellRendererCategoria;
import view.frame.ui.component.CategoriaUI;
import view.frame.ui.component.PanelList;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PCategoria {
    private JPanel pPrincipal;
    private JList listCategoria;
    private DefaultListModel dlmCategoria;

    public PCategoria(){
        pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setOpaque(false);

        PanelList pl = new PanelList();

        pl.add(new CategoriaUI(1,"Producto",Color.ORANGE));

        JScrollPane jspi = new JScrollPane(pl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jspi.setViewportBorder(null);
        jspi.getViewport().setOpaque(false);
        jspi.setOpaque(false);
        jspi.setBorder(BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder()));
        jspi.setPreferredSize(new Dimension(250,500));
        //jspi.setSize(new Dimension(70,500));

        pPrincipal.add(jspi, LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 20, 0, 1.0f, 1.0f));

    }

    public JPanel getPanel() {
        return pPrincipal;
    }
}
