package view.frame.marca;

import com.djm.util.LayoutPanel;
import model.Marca;
import util.SystemProperties;
import util.table.ModeloTabla;
import view.frame.main.FrameMain;
import view.frame.main.LoadData;
import view.frame.ui.component.Button;
import view.frame.ui.component.OptionPane;
import view.frame.ui.component.Table;

import javax.swing.*;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PanelListMarca extends JPanel implements ActionListener {
    private Marca marca;
    private Table table;
    private final SystemProperties sp = SystemProperties.getInstance();
    private Button bEliminar;
    public PanelListMarca(){
        super(new GridBagLayout());
        createGUI();
    }
    private void createGUI(){

        ModelTableMarcaCustom mpc = new ModelTableMarcaCustom();
        ModeloTabla<Marca> modelo = new ModeloTabla(mpc);

        table = new Table(modelo, 280);

        bEliminar = new Button(new ImageIcon("icon/delete.png"));//sp.getValue("button.eliminar"));//,
        Color red = new Color(201, 34, 34);
        bEliminar.setColorImage(red);
        bEliminar.setForeground(red);
        bEliminar.setActionCommand("DELETE");
        bEliminar.addActionListener(this);
        bEliminar.backgroundTransparent();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (table.isEnabled() && e.getClickCount() == 2) {
                    getMarca();
                }
            }
        });

        add(table.getPanel(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.PAGE_START, 10, 10, 5, 0, 0.0f, 1.0f));
        add(bEliminar, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 3, 5, 0, 1.0f, 1.0f));
    }

    public boolean fillTable(){
        table.setEnabled(false);
        LoadData.getInstance().getConsultaMarca().loadDBMarca();
        List<Marca> marcaList = LoadData.getInstance().getConsultaMarca().getList();
        for(Marca mc:marcaList){
            table.addRow(mc);
        }

        boolean e = !marcaList.isEmpty();
        table.setEnabled(e);
        bEliminar.setEnabled(e);
        return e;
    }

    public Marca getMarca() {
        marca = (Marca) table.getSelectedItem();
        if(marca==null) {
            OptionPane.error(FrameMain.frame, sp.getValue("marca.message.selected"));
        }

        return marca;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if(action.equals("DELETE")){
            Marca mdel = (Marca) table.getSelectedItem();
            if (mdel != null) {
                OptionPane.warning(FrameMain.frame, sp.getValue("marca.message.warning_delete"));
                int yes = OptionPane.questionYesOrKey(FrameMain.frame, sp.getValue("marca.message.delete"));
                if (yes == OptionPane.OK) {
                    int index = table.getSelectedIndex();
                    if (index != -1) {
                        AdministracionMarca adminMarca = new AdministracionMarca();
                        boolean rtn = adminMarca.eliminar(mdel.getID());
                        if(rtn){
                            table.removeRow(index);
                        }
                    }
                }
            }
        }
    }
}
