package view.frame.ui.component;

import com.djm.util.LayoutPanel;
import model.Categoria;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

public class PanelList extends JPanel {
    private final Color colorBack = GlobalUI.getInstance().getTheme().getTextUI().getBackground();
    //private final Color colorBorder = GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder();
    private JPanel panel;
    byte x = 0;
    byte y = 0;
    int index = 0;
    private List<CategoriaUI> listCategoriaUI;
    private boolean rpaint = true;

    public PanelList(){
        setBorder(new EmptyBorder(10, 0, 10, 10));
        setOpaque(true);
        setLayout(new GridBagLayout());
        //setMinimumSize(new Dimension(300,100));
        //setPreferredSize(new Dimension(300,200));
        setBackground(colorBack);
        //setBorder(BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder()));
        panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        listCategoriaUI = new ArrayList<>();

        /*for(int i = 0;i<2;i++) {
            add(new CategoriaUI(1,"Servicio "+i,Color.ORANGE));
        }*/

        add(panel, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));
    }

    private void add(CategoriaUI cate){
        float wx =(x == 1)?1.0f: 0.0f;

        panel.add(cate, LayoutPanel.constantePane(x, y, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 10, 10, 0, wx, 0.0f),index);
        x++;
        if(x==2){
            x = 0;
            y++;
        }
        index++;
        if(rpaint) {
            panel.repaint();
            panel.updateUI();
            panel.revalidate();
        }
    }

    public void setListCategoria(List<Categoria> listCategoria) {
        if(listCategoria!=null) {
            rpaint = false;
            for(Categoria categoria:listCategoria){
                addCategoria(categoria);
            }
            rpaint = true;
        }
    }

    public void addCategoria(Categoria categoria){

        CategoriaUI categoriaUI = new CategoriaUI(categoria);

        add(categoriaUI);
    }

    public Categoria getItemSelected(){
        Categoria aux = null;
        cont:for(CategoriaUI cate : this.listCategoriaUI){
            if(cate.isSelected()){
                aux = cate.getCategoria();
                break cont;
            }
        }
        return aux;
    }
}
