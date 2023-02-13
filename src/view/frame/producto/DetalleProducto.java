package view.frame.producto;

import com.djm.ui.TextArea;
import com.djm.ui.TextField;
import com.djm.util.LayoutPanel;
import model.Categoria;
import model.Impuesto;
import util.SystemProperties;
import view.frame.ui.component.Button;
import view.frame.ui.component.ButtonTabbed;
import view.frame.ui.component.ButtonGroup;
import view.frame.ui.component.CheckBox;
import view.frame.ui.component.ComboBox;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class DetalleProducto implements ActionListener {
    private JPanel pPrincipal;
    private JPanel pActividad;
    private SystemProperties sp = SystemProperties.getInstance();
    private boolean open = false;
    private TextField tCodigo,tCodigoBarra,tNombre,tDescripcion,tUnidadMedida,
                        tCosto,tPrecio1,tPrecio2,tPrecio3,tUtilidad,tStockBajo, tDisponible;
    private ComboBox<Categoria> cbCategoria;
    private TextArea tNota;
    private CheckBox disponible, servicio, precioImpuesto;
    private DefaultComboBoxModel<Categoria> dcbCategoria;
    private JScrollPane jsp = null;
    private JList<Impuesto> lImpuesto;
    private DefaultListModel dlmImpuestp;
    private boolean producto = true;
    private boolean precio = false;
    private boolean stock = false;
    private JPanel lPanel[] = new JPanel[3];

    public DetalleProducto(){
        pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setOpaque(false);

        Button bAceptar = new Button(sp.getValue("button.guardar"));//,new ImageIcon("icon/ok.png"));
        Button bCancelar = new Button(sp.getValue("button.cancelar"));//,new ImageIcon("icon/close.png"));

        //bAceptar.setDimension(100,32);
        //bCancelar.setDimension(100,32);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        lPanel[0] = pDetalle();
        lPanel[1] = pPrecioImpuesto();
        lPanel[2] = pStock();

        panel.add(pTabbed(), LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(bAceptar, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(bCancelar, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));

        JScrollPane jspi = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jspi.setViewportBorder(null);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jspi.getViewport().setOpaque(false);
        jspi.setOpaque(false);
        jspi.setBorder(null);


        pPrincipal.add(jspi, LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 1.0f, 1.0f));
    }

    private JPanel pTabbed() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(410,440));
        //Color cb = GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder();
        //panel.setBorder(BorderFactory.createLineBorder(cb));
        //panel.setBackground(Color.RED);

        ButtonTabbed bDetalle = new ButtonTabbed(sp.getValue("produtos.tabbedpane.title.producto"));
        ButtonTabbed bPrecioImpuesto = new ButtonTabbed(sp.getValue("produtos.tabbedpane.title.precio"));
        ButtonTabbed bStock = new ButtonTabbed(sp.getValue("produtos.tabbedpane.title.stock"));
        ButtonGroup bg = new ButtonGroup();
        bg.add(bDetalle);
        bg.add(bPrecioImpuesto);
        bg.add(bStock);

        JSeparator sep = new JSeparator();
        sep.setOrientation(SwingConstants.HORIZONTAL);
        sep.setForeground(GlobalUI.getInstance().getTheme().getPanelUI().getColorBorder());
        sep.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());

        pActividad = new JPanel(new GridBagLayout());
        pActividad.setOpaque(false);
        //pActividad.setBackground(Color.RED);

        setActividad(lPanel[0]);

        bDetalle.setActionCommand("PRODUCTO");
        bPrecioImpuesto.setActionCommand("PRECIO");
        bStock.setActionCommand("STOCK");

        bDetalle.addActionListener(this);
        bPrecioImpuesto.addActionListener(this);
        bStock.addActionListener(this);

        panel.add(bDetalle, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(bPrecioImpuesto, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 10, 0, 0, 0.0f, 0.0f));
        panel.add(bStock, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 10, 0, 0, 0.0f, 0.0f));
        panel.add(sep, LayoutPanel.constantePane(0, 1, 3, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 0, 0, 0, 0, 1.0f, 0.0f));
        panel.add(pActividad, LayoutPanel.constantePane(0, 2, 3, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));

        bDetalle.selected();
        return panel;
    }

    private JPanel pDetalle(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        //panel.setBorder(getBorder("Detalles del Producto"));
        //panel.setBackground(Color.RED);

        JLabel lCodigo = new JLabel(sp.getValue("produtos.label.codigo")+":");
        JLabel lCodigoBarra = new JLabel(sp.getValue("produtos.label.codigobarra")+":");
        JLabel lNombre = new JLabel(sp.getValue("produtos.label.nombre")+":");
        JLabel lDescripcion = new JLabel(sp.getValue("produtos.label.descripcion")+":");
        JLabel lUnidadMedida = new JLabel(sp.getValue("produtos.label.unidadmedida")+":");
        JLabel lCategoria = new JLabel(sp.getValue("produtos.label.categoria")+":");
        //JLabel lDisponible= new JLabel(sp.getValue("produtos.label.disponible"));
        JLabel lNota= new JLabel(sp.getValue("produtos.label.nota")+":");

        tCodigo = new TextField(10,7);
        tCodigoBarra = new TextField(25);
        tNombre = new TextField(20);
        tDescripcion = new TextField(25);
        tUnidadMedida = new TextField(5);

        tNota = new TextArea(3,25);
        tNota.setTabSize(0);
        tNota.setOpaque(false);
        //tNota.setBorder(new EmptyBorder(5, 5, 0, 4));
        tNota.setLineWrap(true);
        tNota.setLimitText(512);
        tNota.nullBorder();

        tCodigo.numericTextOnly();
        tNota.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Border matte  = BorderFactory.createMatteBorder(1, 1, 1, 1,GlobalUI.getInstance().getTheme().getTextUI().getColorBorderFocus());// GlobalUI.getInstance().getTheme().getColorBorderField());
                jsp.setViewportBorder(matte);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
                jsp.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                Border matte  = BorderFactory.createMatteBorder(1, 1, 1, 1,GlobalUI.getInstance().getTheme().getTextUI().getColorBorder());// GlobalUI.getInstance().getTheme().getColorBorderField());
                jsp.setViewportBorder(matte);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
                jsp.repaint();
            }
        });

        Dimension cbdim = new Dimension(210,21);
        dcbCategoria = new DefaultComboBoxModel<Categoria> ();
        Categoria cat1 =  new Categoria();
        cat1.setDesrcripcion("Producto");
        Categoria cat2 =  new Categoria();
        cat2.setDesrcripcion("Servicio");
        dcbCategoria.addElement(cat1);
        dcbCategoria.addElement(cat2);
        cbCategoria = new ComboBox<>(dcbCategoria);
        cbCategoria.setOpaque(false);
        cbCategoria.setPreferredSize(cbdim);
        cbCategoria.setMaximumRowCount(20);
        cbCategoria.setEditable(false);
        //cbCategoria.getEditor().getEditorComponent().setBackground(Color.RED);
        //cbMediero.setBackground(new Color(255,255,255));
        cbCategoria.setEnabled(true);
        //cbCategoria.setUI(new MyComboBoxUI());

        jsp = new JScrollPane(tNota, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Border matte  = BorderFactory.createMatteBorder(1, 1, 1, 1,GlobalUI.getInstance().getTheme().getTextUI().getColorBorder());// GlobalUI.getInstance().getTheme().getColorBorderField());
        jsp.setViewportBorder(matte);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jsp.getViewport().setOpaque(false);
        jsp.setOpaque(false);
        jsp.setBorder(null);

        disponible = new CheckBox(sp.getValue("produtos.label.disponible"));
        disponible.setOpaque(false);
        disponible.setSelected(true);

        servicio = new CheckBox(sp.getValue("produtos.label.servicio"));
        servicio.setOpaque(false);

        panel.add(lCodigo, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigo, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCodigoBarra, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigoBarra, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lNombre, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tNombre, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lDescripcion, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tDescripcion, LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lUnidadMedida, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tUnidadMedida, LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCategoria, LayoutPanel.constantePane(0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbCategoria, LayoutPanel.constantePane(1, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(disponible, LayoutPanel.constantePane(1, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(servicio, LayoutPanel.constantePane(1, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        //panel.add(disponible, LayoutPanel.constantePane(0, 13, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(lNota, LayoutPanel.constantePane(0, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 0, 0, 0, 0.0f, 1.0f));
        panel.add(jsp, LayoutPanel.constantePane(1, 8, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 5, 0, 0, 1.0f, 1.0f));

        return panel;
    }

    private JPanel pPrecioImpuesto() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        //panel.setBorder(getBorder("Precio & Impuesto"));

        Button agregarImpuesto = new Button(sp.getValue("produtos.button.agregarImpuesto"));

        JLabel lCosto = new JLabel(sp.getValue("produtos.label.costo")+":");
        JLabel lPrecio1 = new JLabel(sp.getValue("produtos.label.precio1")+":");
        JLabel lPrecio2 = new JLabel(sp.getValue("produtos.label.precio2")+":");
        JLabel lPrecio3 = new JLabel(sp.getValue("produtos.label.precio3")+":");
        JLabel lUtilidad = new JLabel(sp.getValue("produtos.label.utilidad")+":");

        dlmImpuestp = new DefaultListModel<Impuesto>();
        lImpuesto = new JList<>(dlmImpuestp);
        lImpuesto.setPreferredSize(new Dimension(200, 100));

        precioImpuesto = new CheckBox(sp.getValue("produtos.label.precioImpuesto"));
        precioImpuesto.setSelected(true);
        precioImpuesto.setOpaque(false);

        tCosto = new TextField(10,10);
        tPrecio1 = new TextField(10,10);
        tPrecio2 = new TextField(10,10);
        tPrecio3 = new TextField(10,10);
        tUtilidad = new TextField(10,10);

        tCosto.textDecimal(",");
        tPrecio1.textDecimal(",");
        tPrecio2.textDecimal(",");
        tPrecio3.textDecimal(",");

        JScrollPane jspi = new JScrollPane(lImpuesto, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Border matte  = BorderFactory.createMatteBorder(1, 1, 1, 1,GlobalUI.getInstance().getTheme().getTextUI().getColorBorder());// GlobalUI.getInstance().getTheme().getColorBorderField());
        jspi.setViewportBorder(matte);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jspi.getViewport().setOpaque(false);
        jspi.setOpaque(false);
        jspi.setBorder(null);

        panel.add(lCosto, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCosto, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lUtilidad, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tUtilidad, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio1, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio1, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio2, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio2, LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio3, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio3, LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(precioImpuesto, LayoutPanel.constantePane(1, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(agregarImpuesto, LayoutPanel.constantePane(1, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(jspi, LayoutPanel.constantePane(1, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 5, 0, 0, 1.0f, 1.0f));

        return panel;
    }

    private JPanel pStock() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        //panel.setBackground(Color.RED);
        //panel.setBorder(getBorder("Stock"));

        JLabel lAdvertencia = new JLabel(sp.getValue("produtos.label.advertenciaStock")+":");
        JLabel lDisponible = new JLabel(sp.getValue("produtos.label.cantidad_disponible")+":");

        tStockBajo = new TextField(5,5,true);
        tDisponible = new TextField(5);
        tDisponible.setEditable(false);
        //tDisponible.setEnabled(false);

        panel.add(lAdvertencia, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tStockBajo, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lDisponible, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tDisponible, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 1.0f, 1.0f));

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if(action.equals("PRODUCTO")){
            precio = false;
            stock = false;
            if(!producto) {
                producto = true;
                setActividad(lPanel[0]);
            }
        }
        else if(action.equals("PRECIO")){
            producto= false;
            stock = false;
            if(!precio) {
                precio = true;
                setActividad(lPanel[1]);
            }
        }
        else if(action.equals("STOCK")){
            producto= false;
            precio= false;
            if(!stock) {
                stock = true;
                setActividad(lPanel[2]);
            }
        }
    }
    private void setActividad(JPanel panel){
        pActividad.removeAll();
        pActividad.add(panel, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 1.0f, 1.0f));
        pActividad.revalidate();
        pActividad.repaint();
    }

    public JPanel getPanel() {
        return pPrincipal;
    }
}
