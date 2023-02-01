package view.frame.producto;

import com.djm.ui.TextArea;
import com.djm.ui.TextField;
import com.djm.ui.themes.GlobalUI;
import com.djm.util.LayoutPanel;
import util.IPanel;
import util.SystemProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelProducto implements IPanel {
    private JPanel pPrincipal;
    private SystemProperties sp = SystemProperties.getInstance();
    private boolean open = false;
    private TextField tCodigo,tCodigoBarra,tNombre,tDescripcion,tUnidadMedida;
    private TextArea tNota;
    private JCheckBox disponible;
    public PanelProducto(){
        pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setOpaque(false);
        pPrincipal.add(pDetalle(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 1.0f, 1.0f));

    }

    private JPanel pDetalle(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JLabel lCodigo = new JLabel(sp.getValue("produtos.label.codigo"));
        JLabel lCodigoBarra = new JLabel(sp.getValue("produtos.label.codigobarra"));
        JLabel lNombre = new JLabel(sp.getValue("produtos.label.nombre"));
        JLabel lDescripcion = new JLabel(sp.getValue("produtos.label.descripcion"));
        JLabel lUnidadMedida = new JLabel(sp.getValue("produtos.label.unidadmedida"));
        JLabel lCategoria = new JLabel(sp.getValue("produtos.label.categoria"));
        //JLabel lDisponible= new JLabel(sp.getValue("produtos.label.disponible"));
        JLabel lNota= new JLabel(sp.getValue("produtos.label.nota"));

        tCodigo = new TextField(15,10);
        tCodigoBarra = new TextField(25);
        tNombre = new TextField(20);
        tDescripcion = new TextField(25);
        tUnidadMedida = new TextField(5);

        tNota = new TextArea(5,25);
        tNota.setTabSize(1);
        tNota.setOpaque(false);
        tNota.setBorder(new EmptyBorder(5, 5, 0, 4));
        tNota.setLineWrap(true);
        tNota.setLimitText(512);
        tNota.nullBorder();

        JScrollPane jsp = new JScrollPane(tNota, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setViewportBorder(BorderFactory.createLineBorder(GlobalUI.getInstance().getColorBorderField()));
        jsp.getViewport().setOpaque(false);
        jsp.setOpaque(false);
        jsp.setBorder(null);

        disponible = new JCheckBox(sp.getValue("produtos.label.disponible"));
        disponible.setOpaque(false);

        panel.add(lCodigo, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigo, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(lCodigoBarra, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigoBarra, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(lNombre, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tNombre, LayoutPanel.constantePane(0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(lDescripcion, LayoutPanel.constantePane(0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tDescripcion, LayoutPanel.constantePane(0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(lUnidadMedida, LayoutPanel.constantePane(0, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tUnidadMedida, LayoutPanel.constantePane(0, 9, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(lCategoria, LayoutPanel.constantePane(0, 10, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        //panel.add(tCategoria, LayoutPanel.constantePane(0, 11, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(disponible, LayoutPanel.constantePane(0, 12, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        //panel.add(disponible, LayoutPanel.constantePane(0, 13, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(lNota, LayoutPanel.constantePane(0, 14, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        panel.add(jsp, LayoutPanel.constantePane(0, 15, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));

        return panel;
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
