package view.frame.ui.component;

import com.djm.util.LayoutPanel;
import model.Categoria;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
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
    private ActionListener actionListener;

    public PanelList(ActionListener actionListener){
        this.actionListener = actionListener;

        setBorder(new EmptyBorder(10, 0, 10, 0));
        setOpaque(true);
        setLayout(new GridBagLayout());
        //setMinimumSize(new Dimension(300,100));
        //setPreferredSize(new Dimension(300,200));
        setBackground(colorBack);
        //setBorder(BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder()));
        panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        listCategoriaUI = new ArrayList<>();

        add(panel, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));
    }

    private void addCatUI(CategoriaUI cate){
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

    public void updateData(Categoria categoria){
        //boolean nuevo = true;
        cont:for(CategoriaUI catui:listCategoriaUI){
            Categoria cat = catui.getCategoria();
            if(cat.getID().intValue() == categoria.getID().intValue()){
                //nuevo = false;
                catui.getCategoria().setDesrcripcion(categoria.getDesrcripcion());
                catui.getCategoria().setColor(categoria.getColor());
                catui.updateCategoria(categoria);
                catui.repaint();
                break cont;
            }
        }
    }

    public void setListCategoria(List<Categoria> listCategoria) {
        if(listCategoria!=null) {
            rpaint = false;
            x = 0;
            y = 0;
            index = 0;
            listCategoriaUI.clear();
            for(Categoria categoria:listCategoria){
                addCategoria(categoria);
            }
            rpaint = true;
        }
    }

    public void addCategoria(Categoria categoria){

        CategoriaUI categoriaUI = new CategoriaUI(categoria);
        if(actionListener!=null)
            categoriaUI.addActionListener(actionListener);
        addCatUI(categoriaUI);
        listCategoriaUI.add(categoriaUI);
    }

    public void delCategoria(Categoria categoria){
        int l = listCategoriaUI.size();
        cont:for(int i = 0;i<l;i++) {
            if(listCategoriaUI.get(i).getCategoria().getID().intValue() == categoria.getID().intValue()) {
                this.listCategoriaUI.remove(i);
                repaintPanelList();
                /*panel.remove(i);
                panel.updateUI();
                panel.repaint();
                panel.revalidate();*/
                break cont;
            }
        }
    }

    public List<Categoria> getItemSelected(){
        List<Categoria> aux = new ArrayList<>();
        //System.out.println("Cantidad de Categoria: "+this.listCategoriaUI.size());
        cont:for(CategoriaUI cate : this.listCategoriaUI){
            //System.out.println(cate.getCategoria().getID()+" "+cate.isSelected());
            if(cate.isSelected()){
                aux.add(cate.getCategoria());
            }
        }
        return aux;
    }

    public void setEnabled(boolean e){
        for(CategoriaUI cate : this.listCategoriaUI){
            cate.setEnabled(e);
        }
    }

    public void repaintPanelList(){
        panel.removeAll();
        panel.updateUI();
        panel.revalidate();
        panel.repaint();

        this.index = 0;
        this.x = 0;
        this.y = 0;
        if(listCategoriaUI!=null) {
            rpaint = false;
            for(CategoriaUI categoria:listCategoriaUI){
                //System.out.println(categoria.getCategoria().getDesrcripcion());
                addCatUI(categoria);
            }
            rpaint = true;
        }
        panel.repaint();
        panel.revalidate();
    }
}
