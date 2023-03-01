package view.frame.categoria;

import com.djm.util.LayoutPanel;
import model.Categoria;
import util.SystemProperties;
import util.table.ModeloTabla;
import view.frame.main.FrameMain;
import view.frame.main.LoadData;
import view.frame.producto.GlobalProduct;
import view.frame.ui.component.Button;
import view.frame.ui.component.OptionPane;
import view.frame.ui.component.Table;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class PanelTableCategoria implements ActionListener, WindowListener {
    private JDialog dialog;
    private Categoria categoria;
    private boolean acept = false;
    private Button bAceptar;
    private Button bCancelar;
    private Button bEliminar;
    private Table table;
    private final SystemProperties sp = SystemProperties.getInstance();

    public PanelTableCategoria(){
        dialog = new JDialog(FrameMain.frame,sp.getValue("categoria.label.buscar"),true);
        dialog.addWindowListener(this);

        bAceptar = new Button(sp.getValue("button.aceptar"));
        bCancelar = new Button(sp.getValue("button.cancelar"));
        bEliminar = new Button(sp.getValue("button.eliminar"), new ImageIcon("icon/delete.png"));
        Color red = new Color(201, 34, 34);
        bEliminar.setColorImage(red);
        bEliminar.setForeground(red);

        bAceptar.setActionCommand("ACEPT");
        bCancelar.setActionCommand("CANCEL");
        bEliminar.setActionCommand("DELETE");

        bAceptar.addActionListener(this);
        bCancelar.addActionListener(this);
        bEliminar.addActionListener(this);

        ModelTableCategoriaCustom mpc = new ModelTableCategoriaCustom();
        ModeloTabla<Categoria> modelo = new ModeloTabla(mpc);

        table = new Table(modelo, 280);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (table.isEnabled() && e.getClickCount() == 2) {
                    aceptCategoria();
                }
            }
        });

        table.setDefaultRenderer(JLabel.class, new StatusIconRenderer());

        Container container = dialog.getContentPane();
        container.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        container.setLayout(new GridBagLayout());

        container.add(table.getPanel(), LayoutPanel.constantePane(0, 0, 3, 1, GridBagConstraints.VERTICAL, GridBagConstraints.PAGE_START, 10, 10, 5, 10, 1.0f, 1.0f));
        container.add(bEliminar, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 5, 0, 1.0f, 0.0f));
        container.add(bCancelar, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 5, 0, 0.0f, 0.0f));
        container.add(bAceptar, LayoutPanel.constantePane(2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 5, 10, 0.0f, 0.0f));

        //dialog.pack();
        Dimension dim = new Dimension(450,410);//dialog.getSize();//
        //dim.setSize(dim.width+30,dim.height);
        //dialog.setUndecorated(true);
        dialog.setPreferredSize(dim);
        dialog.setSize(dim);
        dialog.setResizable(true);
        dialog.setMinimumSize(dim);
        dialog.setDefaultCloseOperation(0);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void fillTable(){
        table.setEnabled(false);
        LoadData.getInstance().getConsultaCategoria().loadDBCategoria();
        List<Categoria> marcaList = LoadData.getInstance().getConsultaCategoria().getList();
        for(Categoria mc:marcaList){
            table.addRow(mc);
        }

        boolean e = !marcaList.isEmpty();
        table.setEnabled(e);
        bEliminar.setEnabled(e);
        bAceptar.setEnabled(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if(action.equals("ACEPT")){
            aceptCategoria();
        }
        else if(action.equals("CANCEL")){
            acept = false;
            dialog.setVisible(false);
            dialog.dispose();
        }
        else if(action.equals("DELETE")){
            Categoria mdel = (Categoria) table.getSelectedItem();
            if (mdel != null) {
                OptionPane.warning(FrameMain.frame, sp.getValue("categoria.message.warning_delete"));
                int yes = OptionPane.questionYesOrKey(FrameMain.frame, sp.getValue("categoria.message.delete"));
                if (yes == OptionPane.OK) {
                    int index = table.getSelectedIndex();
                    if (index != -1) {
                        AdministracionCategoria adminCategoria = new AdministracionCategoria();
                        boolean rtn = adminCategoria.eliminar(mdel.getID());
                        if(rtn){
                            table.removeRow(index);
                            GlobalProduct.getInstance().deleteCategoria(mdel);
                            //GlobalProduct.getInstance().reorganizarListCat();
                        }
                    }
                }
            }
        }
    }

    private void aceptCategoria() {
        categoria = (Categoria) table.getSelectedItem();
        if(categoria !=null) {
            String msg = sp.getValue("categoria.message.editar")+"["+categoria.getDesrcripcion()+"]";
            FrameMain.notificacion.start(sp.getValue("categoria.label.title"), msg);
            acept = true;
            dialog.setVisible(false);
            dialog.dispose();
        }else
            OptionPane.error(FrameMain.frame,sp.getValue("categoria.message.selected"));
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public boolean isAcept() {
        return acept;
    }


    private class StatusIconRenderer extends JLabel implements TableCellRenderer {
        private Color color;
        public StatusIconRenderer() {}
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int col) {

            //System.out.println(row+" "+col+" "+value);
            if(col == 1) {
                //return aux.isDisponible()+"@"+aux.isNoRequiereStock()+"@"+aux.getStock()+"@"+aux.getStockCritico();

                color =(Color)value;
                setOpaque(true);
                setSize(new Dimension(20,20));
                setPreferredSize(new Dimension(20,20));
                setForeground(color);
                setBackground(color);
                //setText("Color");
            }
            return this;
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        fillTable();
        //dialog.repaint();
        dialog.revalidate();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //System.out.println(dialog.getSize());
        acept = false;
        categoria = null;
        dialog.setVisible(false);
        dialog.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
