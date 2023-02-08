package view.frame.producto;

import com.djm.ui.TextArea;
import com.djm.ui.TextField;
import com.djm.util.LayoutPanel;
import model.Categoria;
import model.Impuesto;
import util.IPanel;
import util.SystemProperties;
import view.frame.ui.FontHelper;
import view.frame.ui.component.Button;
import view.frame.ui.component.ButtonGroup;
import view.frame.ui.component.CheckBox;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PanelProducto implements IPanel , ActionListener {
    private JPanel pPrincipal;
    private JPanel pActividad;
    private SystemProperties sp = SystemProperties.getInstance();
    private boolean open = false;
    private TextField tCodigo,tCodigoBarra,tNombre,tDescripcion,tUnidadMedida,
                        tCosto,tPrecio1,tPrecio2,tPrecio3,tUtilidad,tStockBajo;
    private JComboBox<Categoria> cbCategoria;
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

    public PanelProducto(){
        pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setOpaque(false);

        Button bAceptar = new Button("Aceptar");//,new ImageIcon("icon/ok.png"));
        Button bCancelar = new Button("Cancelar");//,new ImageIcon("icon/close.png"));
        bAceptar.setPaintSelected(false);

        bCancelar.setPaintSelected(false);
        //bAceptar.setDimension(100,32);
        //bCancelar.setDimension(100,32);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        lPanel[0] = pDetalle();
        lPanel[1] = pPrecioImpuesto();
        lPanel[2] = pStock();

        panel.add(pTabbed(), LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        //panel.add(bCancelar, LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));

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
        //panel.setBorder(BorderFactory.createLineBorder(Color.red));
        //panel.setBackground(Color.RED);

        Button bDetalle = new Button (sp.getValue("produtos.tabbedpane.title.producto"));
        Button bPrecioImpuesto = new Button (sp.getValue("produtos.tabbedpane.title.precio"));
        Button bStock = new Button (sp.getValue("produtos.tabbedpane.title.stock"));
        ButtonGroup bg = new ButtonGroup();
        bg.add(bDetalle);
        bg.add(bPrecioImpuesto);
        bg.add(bStock);

        JSeparator sep = new JSeparator();
        sep.setOrientation(SwingConstants.HORIZONTAL);
        sep.setForeground(GlobalUI.getInstance().getTheme().getColorBorder());
        sep.setBackground(GlobalUI.getInstance().getTheme().getBackground());

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
                Border matte  = BorderFactory.createMatteBorder(1, 1, 1, 1,GlobalUI.getInstance().getTheme().getColorBorderFocusField());// GlobalUI.getInstance().getTheme().getColorBorderField());
                jsp.setViewportBorder(matte);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
                jsp.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                Border matte  = BorderFactory.createMatteBorder(1, 1, 1, 1,GlobalUI.getInstance().getTheme().getColorBorderField());// GlobalUI.getInstance().getTheme().getColorBorderField());
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
        cbCategoria = new JComboBox<>(dcbCategoria);
        cbCategoria.setOpaque(false);
        cbCategoria.setPreferredSize(cbdim);
        cbCategoria.setMaximumRowCount(20);
        cbCategoria.setEditable(false);
        //cbCategoria.getEditor().getEditorComponent().setBackground(Color.RED);
        //cbMediero.setBackground(new Color(255,255,255));
        cbCategoria.setEnabled(true);
        //cbCategoria.setUI(new MyComboBoxUI());

        jsp = new JScrollPane(tNota, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Border matte  = BorderFactory.createMatteBorder(1, 1, 1, 1,GlobalUI.getInstance().getTheme().getColorBorderField());// GlobalUI.getInstance().getTheme().getColorBorderField());
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
        agregarImpuesto.setPaintSelected(true);
        agregarImpuesto.setFont(new Font("Tahoma",0,12));

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
        Border matte  = BorderFactory.createMatteBorder(1, 1, 1, 1,GlobalUI.getInstance().getTheme().getColorBorderField());// GlobalUI.getInstance().getTheme().getColorBorderField());
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

        JLabel lAdvertencia = new JLabel(sp.getValue("produtos.label.advertenciaStock"));

        tStockBajo = new TextField(10,10,true);

        panel.add(lAdvertencia, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tStockBajo, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 20, 0, 0, 1.0f, 1.0f));

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
    public static class MyComboBoxUI extends BasicComboBoxUI {
        @Override
        protected void installDefaults() {
            super.installDefaults();
            LookAndFeel.uninstallBorder(comboBox); //Uninstalls the LAF border for both button and label of combo box.
        }
        @Override
        public void update(Graphics g, JComponent c) {
            g.setColor(Color.WHITE);
            g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(),12, 12);
            paint(g, c);
        }
        @Override
        protected JButton createArrowButton() {
            //Feel free to play with the colors:
            final Color background = GlobalUI.getInstance().getTheme().getBackgroundButton();     //Default is UIManager.getColor("ComboBox.buttonBackground").
            final Color pressedButtonBorderColor = GlobalUI.getInstance().getTheme().getBackgroundButton();; //Default is UIManager.getColor("ComboBox.buttonShadow"). The color of the border of the button, while it is pressed.
            final Color triangle = Color.red;//GlobalUI.getInstance().getBackgroundButton();              //Default is UIManager.getColor("ComboBox.buttonDarkShadow"). The color of the triangle.
            final Color highlight = GlobalUI.getInstance().getTheme().getBackgroundButton();              //Default is UIManager.getColor("ComboBox.buttonHighlight"). Another color to show the button as highlighted.
            final JButton button = new BasicArrowButton(BasicArrowButton.SOUTH, background, pressedButtonBorderColor, triangle, highlight);
            button.setName("ComboBox.arrowButton"); //Mandatory, as per BasicComboBoxUI#createArrowButton().
            return button;
        }
    }


    /*protected Border getBorder(String title){
        FontHelper fh = new FontHelper();
        Color cfore = GlobalUI.getInstance().getTheme().getForeground();
        Color cborder = GlobalUI.getInstance().getTheme().getColorBorder();
        TitledBorder tbor= BorderFactory.createTitledBorder(BorderFactory.createLineBorder(cborder),title,TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.ABOVE_TOP,fh.font(11),cfore);//0,54,77

        javax.swing.border.Border border=BorderFactory.createCompoundBorder(tbor,BorderFactory.createEmptyBorder(5,5,5,5));

        return border;
    }*/

}