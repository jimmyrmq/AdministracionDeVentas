package view.frame.producto;

import com.djm.util.LayoutPanel;
import model.Categoria;
import view.frame.main.LoadData;
import view.frame.ui.component.PanelList;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

public class PCategoria {
    private JPanel pPrincipal;
    //private JList listCategoria;
    //private DefaultListModel dlmCategoria;
    private PanelList pl;

    public PCategoria(){
        pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setOpaque(false);

        ActionListenerProduct actionListenerProduct = new ActionListenerProduct();

        pl = new PanelList(actionListenerProduct);

        JScrollPane jspi = new JScrollPane(pl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jspi.setViewportBorder(null);
        jspi.getViewport().setOpaque(false);
        jspi.setOpaque(false);
        jspi.setBorder(BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder()));
        jspi.setPreferredSize(new Dimension(235,500));
        //jspi.setSize(new Dimension(70,500));

        pPrincipal.add(jspi, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));
    }

    public void init(){
        //Thread thread = new Thread(()-> {
            List<Categoria> lCat = LoadData.getInstance().getConsultaCategoria().getList();
            pl.setListCategoria(lCat);
        /*});
        thread.start();*/
    }


    public JPanel getPanel() {
        return pPrincipal;
    }

    protected PanelList getPanelList(){
        return pl;
    }
}
