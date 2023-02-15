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
import java.util.List;

public class PCategoria {
    private JPanel pPrincipal;
    private JList listCategoria;
    private DefaultListModel dlmCategoria;
    private PanelList pl;

    public PCategoria(){
        pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setOpaque(false);

        pl = new PanelList();

        init();

        JScrollPane jspi = new JScrollPane(pl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jspi.setViewportBorder(null);
        jspi.getViewport().setOpaque(false);
        jspi.setOpaque(false);
        jspi.setBorder(BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder()));
        jspi.setPreferredSize(new Dimension(235,500));
        //jspi.setSize(new Dimension(70,500));

        pPrincipal.add(jspi, LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 0, 10, 20, 0, 1.0f, 1.0f));
    }

    private void init(){
        Thread thread = new Thread(()-> {
            List<Categoria> lCat = GlobalProduct.getInstance().consultaCategoria.getListCategoria();
            pl.setListCategoria(lCat);
        });
        thread.start();
    }

    public JPanel getPanel() {
        return pPrincipal;
    }
}