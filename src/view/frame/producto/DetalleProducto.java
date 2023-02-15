package view.frame.producto;

import com.djm.ui.component.TextArea;
import com.djm.ui.component.TextField;
import com.djm.util.LayoutPanel;
import model.Categoria;
import model.Impuesto;
import model.Marca;
import model.Producto;
import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.ui.component.Button;
import view.frame.ui.component.ButtonTabbed;
import view.frame.ui.component.ButtonGroup;
import view.frame.ui.component.CheckBox;
import view.frame.ui.component.ComboBox;
import view.frame.ui.component.OptionPane;
import view.frame.ui.themes.ButtonNewUI;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class DetalleProducto implements ActionListener {
    private JPanel pPrincipal;
    private JPanel pActividad;
    private final SystemProperties sp = SystemProperties.getInstance();
    //private Button bNuevo;
    private Button bGuardar;
    private Button bCancelar;
    private TextField tCodigo,tCodigoBarra,tNombre,tUnidadMedida,//,tDescripcion,tUtilidad
                        tCosto,tPrecio1,tPrecio2,tPrecio3, tStockCritico, tStock;
    private ComboBox<Categoria> cbCategoria;
    private ComboBox<Marca> cbMarca;
    private TextArea tNota;
    private CheckBox disponible, requiereStock, precioImpuesto;
    private DefaultComboBoxModel<Categoria> dcbCategoria;
    private DefaultComboBoxModel<Marca> dcbMarca;
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

        bGuardar = new Button(sp.getValue("button.guardar"));//,new ImageIcon("icon/ok.png"));
        bCancelar = new Button(sp.getValue("button.cancelar"));//,new ImageIcon("icon/close.png"));
        //bNuevo = new Button(sp.getValue("produtos.buttom.nuevo_producto"),new ImageIcon("icon/new.png"));

        bGuardar.addActionListener(this);
        bCancelar.addActionListener(this);
        //bNuevo.addActionListener(this);

        bGuardar.setActionCommand("BUTTON_GUARDAR");
        bCancelar.setActionCommand("BUTTON_CANCELAR");
        //bNuevo.setActionCommand("BUTTON_NUEVO");

        //bNuevo.setButtonUI(new ButtonNewUI());
        //bGuardar.setEnabled(false);
        //bCancelar.setEnabled(false);
        //bAceptar.setDimension(100,32);
        //bCancelar.setDimension(100,32);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        lPanel[0] = pDetalle();
        lPanel[1] = pPrecioImpuesto();
        lPanel[2] = pStock();

        panel.add(pTabbed(), LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        //panel.add(bNuevo, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 1.0f, 0.0f));
        panel.add(bGuardar, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(bCancelar, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 1.0f, 0.0f));

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
        //JLabel lDescripcion = new JLabel(sp.getValue("produtos.label.descripcion")+":");
        JLabel lUnidadMedida = new JLabel(sp.getValue("produtos.label.unidadmedida")+":");
        JLabel lCategoria = new JLabel(sp.getValue("produtos.label.categoria")+":");
        JLabel lMarca= new JLabel(sp.getValue("produtos.label.marca")+":");
        JLabel lNota= new JLabel(sp.getValue("produtos.label.nota")+":");

        tCodigo = new TextField(10,7);
        tCodigoBarra = new TextField(25);
        tNombre = new TextField(20);
        //tDescripcion = new TextField(25);
        tUnidadMedida = new TextField(5);

        tNota = new TextArea(3,30);
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
        cat1.setDesrcripcion(sp.getValue("produtos.label.ninguno"));

        dcbCategoria.addElement(cat1);

        cbCategoria = new ComboBox<>(dcbCategoria);
        cbCategoria.setOpaque(false);
        cbCategoria.setPreferredSize(cbdim);
        cbCategoria.setMaximumRowCount(20);
        cbCategoria.setEditable(false);
        //cbCategoria.getEditor().getEditorComponent().setBackground(Color.RED);
        //cbMediero.setBackground(new Color(255,255,255));
        cbCategoria.setEnabled(true);
        //cbCategoria.setUI(new MyComboBoxUI());


        dcbMarca = new DefaultComboBoxModel<Marca> ();
        Marca mc1 =  new Marca();
        mc1.setDesrcripcion(sp.getValue("produtos.label.ninguno"));

        dcbMarca.addElement(mc1);
        cbMarca = new ComboBox<>(dcbMarca);
        cbMarca.setOpaque(false);
        cbMarca.setPreferredSize(cbdim);
        cbMarca.setMaximumRowCount(20);
        cbMarca.setEditable(false);
        //cbMarca.getEditor().getEditorComponent().setBackground(Color.RED);
        //cbMarca.setBackground(new Color(255,255,255));
        cbMarca.setEnabled(true);
        //cbMarca.setUI(new MyComboBoxUI());

        jsp = new JScrollPane(tNota, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Border matte  = BorderFactory.createMatteBorder(1, 1, 1, 1,GlobalUI.getInstance().getTheme().getTextUI().getColorBorder());// GlobalUI.getInstance().getTheme().getColorBorderField());
        jsp.setViewportBorder(matte);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jsp.getViewport().setOpaque(false);
        jsp.setOpaque(false);
        jsp.setBorder(null);

        disponible = new CheckBox(sp.getValue("produtos.label.disponible"));
        disponible.setOpaque(false);
        disponible.setSelected(true);

        requiereStock = new CheckBox(sp.getValue("produtos.label.servicio"));
        requiereStock.setOpaque(false);

        panel.add(lCodigo, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigo, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCodigoBarra, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigoBarra, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lNombre, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tNombre, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        //panel.add(lDescripcion, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        //panel.add(tDescripcion, LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lUnidadMedida, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tUnidadMedida, LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCategoria, LayoutPanel.constantePane(0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbCategoria, LayoutPanel.constantePane(1, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lMarca, LayoutPanel.constantePane(0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbMarca, LayoutPanel.constantePane(1, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(disponible, LayoutPanel.constantePane(1, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(requiereStock, LayoutPanel.constantePane(1, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        //panel.add(disponible, LayoutPanel.constantePane(0, 13, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(lNota, LayoutPanel.constantePane(0, 9, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 0, 0, 0, 0.0f, 1.0f));
        panel.add(jsp, LayoutPanel.constantePane(1, 9, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 5, 0, 0, 1.0f, 1.0f));

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
        //JLabel lUtilidad = new JLabel(sp.getValue("produtos.label.utilidad")+":");

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
        //tUtilidad = new TextField(10,10);

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
        //panel.add(lUtilidad, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        //panel.add(tUtilidad, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio1, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio1, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio2, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio2, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio3, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio3, LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(precioImpuesto, LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(agregarImpuesto, LayoutPanel.constantePane(1, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(jspi, LayoutPanel.constantePane(1, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 5, 0, 0, 1.0f, 1.0f));

        return panel;
    }

    private JPanel pStock() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        //panel.setBackground(Color.RED);
        //panel.setBorder(getBorder("Stock"));

        JLabel lAdvertencia = new JLabel(sp.getValue("produtos.label.advertenciaStock")+":");
        JLabel lDisponible = new JLabel(sp.getValue("produtos.label.cantidad_disponible")+":");

        tStockCritico = new TextField(5,5,true);
        tStock = new TextField(5);
        tStock.setEditable(false);
        //tDisponible.setEnabled(false);

        panel.add(lAdvertencia, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tStockCritico, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lDisponible, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tStock, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 1.0f, 1.0f));

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
        /*else if(action.equals("BUTTON_NUEVO")){
            bGuardar.setEnabled(true);
            bCancelar.setEnabled(true);
            bNuevo.setEnabled(false);
            String cod = tCodigo.getText();
            if(cod == null || cod.trim().isEmpty()) {
                tCodigo.setText("1");
                tCodigo.requestFocus();
            }
        }*/
        else if(action.equals("BUTTON_GUARDAR")){
            FrameMain.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            String cod = tCodigo.getText();
            String codBarra = tCodigoBarra.getText();
            String nombre = tNombre.getText();
            String unidMed = tUnidadMedida.getText();
            boolean disp = disponible.isSelected();
            boolean reqStock = requiereStock.isSelected();
            String nota = tNota.getText();
            String costo = tCosto.getText();
            String precio1 = tPrecio1.getText();
            String precio2 = tPrecio2.getText();
            String precio3 = tPrecio3.getText();
            boolean isImpuesto = precioImpuesto.isSelected();

            Marca marca = (Marca)dcbMarca.getSelectedItem();
            Categoria categoria = (Categoria) dcbCategoria.getSelectedItem();

            String stockCrititco = tStockCritico.getText();
            String stock = tStock.getText();
            Double c = 0.0;
            if(costo!=null && !costo.trim().isEmpty())
                c = Double.parseDouble(costo);

            Double p1 = 0.0;
            if(precio1!= null && !precio1.trim().isEmpty())
                p1 = Double.parseDouble(precio1);

            Double p2 = 0.0;
            if(precio2!= null && !precio2.trim().isEmpty())
                p2 = Double.parseDouble(precio2);

            Double p3 = 0.0;
            if(precio3!= null && !precio3.trim().isEmpty())
                p3 = Double.parseDouble(precio3);

            Integer scritico = 0;
            if(stockCrititco !=null && !stockCrititco.trim().isEmpty())
                scritico = Integer.parseInt(stockCrititco);

            Integer stck = 0;
            if(stock!=null && !stock.trim().isEmpty())
                stck = Integer.parseInt(stock);

            Producto prod = new Producto();

            if(GlobalProduct.getInstance().producto!=null)
                prod = GlobalProduct.getInstance().producto;

            prod.setCodigo(cod);
            prod.setCodigoBarra(codBarra);
            prod.setNombre(nombre);
            prod.setUnidadMedida(unidMed);
            prod.setMarca(marca);
            prod.setCategoria(categoria);
            prod.setDisponible(disp);
            prod.setNoRequiereStock(reqStock);
            prod.setNota(nota);
            prod.setPrecioCosto(c);
            prod.setPrecio1(p1);
            prod.setPrecio2(p2);
            prod.setPrecio3(p3);
            prod.setPrecioIncluyeImpuesto(isImpuesto);
            prod.setStockCritico(scritico);
            prod.setStock(stck);

            AdministracionProducto administracionProducto = new AdministracionProducto();
            boolean rtn = administracionProducto.guardar(prod);

            FrameMain.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            if(rtn)
                clear();
            else
                OptionPane.error(FrameMain.frame,administracionProducto.getMensaje());
        }
        else if(action.equals("BUTTON_CANCELAR")){
            clear();
        }
    }
    private void setActividad(JPanel panel){
        pActividad.removeAll();
        pActividad.add(panel, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 1.0f, 1.0f));
        pActividad.revalidate();
        pActividad.repaint();
    }

    public void fillerProducto(){
        Producto prod = GlobalProduct.getInstance().producto;
        if(prod!=null) {
            tCodigo.setText(prod.getCodigo());
            tCodigoBarra.setText(prod.getCodigoBarra());
            tNombre.setText(prod.getNombre());
            tUnidadMedida.setText(prod.getUnidadMedida());
            disponible.setSelected(prod.isDisponible());
            requiereStock.setSelected(prod.isNoRequiereStock());
            tNota.setText(prod.getNota());
            tCosto.setText(String.valueOf(prod.getPrecioCosto()));
            tPrecio1.setText(String.valueOf(prod.getPrecio1()));
            tPrecio2.setText(String.valueOf(prod.getPrecio2()));
            tPrecio3.setText(String.valueOf(prod.getPrecio3()));
            precioImpuesto.setSelected(prod.isPrecioIncluyeImpuesto());
            List<Impuesto> impuestoList = prod.getImpuestoList();
            Marca marca = prod.getMarca();
            Categoria categoria = prod.getCategoria();
            tStockCritico.setText(String.valueOf(prod.getStockCritico()));
            tStock.setText(String.valueOf(prod.getStock()));

            //bNuevo.setEnabled(false);
            //bGuardar.setEnabled(true);
            //bCancelar.setEnabled(true);
        }
    }

    private void clear(){
        FrameMain.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        GlobalProduct.getInstance().producto = null;

        tCodigo.setText(null);
        tCodigoBarra.setText(null);
        tNombre.setText(null);
        tUnidadMedida.setText(null);
        disponible.setSelected(true);
        requiereStock.setSelected(false);
        tNota.setText(null);
        tCosto.setText(null);
        tPrecio1.setText(null);
        tPrecio2.setText(null);
        tPrecio3.setText(null);
        precioImpuesto.setSelected(false);

        cbCategoria.setSelectedIndex(0);
        cbMarca.setSelectedIndex(0);

        /*List<Impuesto> impuestoList = prod.getImpuestoList();*/

        tStockCritico.setText(null);
        tStock.setText(null);

        //bNuevo.setEnabled(true);
        //bGuardar.setEnabled(false);
        //bCancelar.setEnabled(false);
        FrameMain.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public JPanel getPanel() {
        return pPrincipal;
    }
}
