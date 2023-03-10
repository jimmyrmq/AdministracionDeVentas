package view.frame.producto.importar;

import com.djm.util.LayoutPanel;
import util.SystemProperties;
import view.frame.main.FrameMain;
import view.frame.ui.component.Button;
import view.frame.ui.component.ComboBox;
import view.frame.ui.component.OptionPane;
import view.frame.ui.glass.Notificacion;
import view.frame.ui.themes.GlobalUI;

import javax.swing.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class ImportProductWindows implements ActionListener {
    private JDialog dialog;
    private Notificacion notificacion;
    private SystemProperties sp = SystemProperties.getInstance();
    private Button bSelectFiled,bImportar,bCancelar;
    private JLabel lFile;
    private JProgressBar progressBar;
    private ComboBox<ColumnSelected> cbCodigo;
    private ComboBox<ColumnSelected> cbCodigoBarra;
    private ComboBox<ColumnSelected> cbNombre;
    private ComboBox<ColumnSelected> cbUnidad;
    private ComboBox<ColumnSelected> cbDisponible;
    private ComboBox<ColumnSelected> cbCosto;
    private ComboBox<ColumnSelected> cbPrecio1;
    private ComboBox<ColumnSelected> cbPrecio2;
    private ComboBox<ColumnSelected> cbPrecio3;
    private ComboBox<ColumnSelected> cbIncluyeImpuesto;
    private ComboBox<ColumnSelected> cbStockCritico;
    private ComboBox<ColumnSelected> cbRequiereStock;
    private ComboBox<ColumnSelected> cbCantidadDisponible;

    private DefaultComboBoxModel<ColumnSelected> dcbCodigo;
    private DefaultComboBoxModel<ColumnSelected> dcbCodigoBarra;
    private DefaultComboBoxModel<ColumnSelected> dcbNombre;
    private DefaultComboBoxModel<ColumnSelected> dcbUnidad;
    private DefaultComboBoxModel<ColumnSelected> dcbDisponible;
    private DefaultComboBoxModel<ColumnSelected> dcbCosto;
    private DefaultComboBoxModel<ColumnSelected> dcbPrecio1;
    private DefaultComboBoxModel<ColumnSelected> dcbPrecio2;
    private DefaultComboBoxModel<ColumnSelected> dcbPrecio3;
    private DefaultComboBoxModel<ColumnSelected> dcbIncluyeImpuesto;
    private DefaultComboBoxModel<ColumnSelected> dcbStockCritico;
    private DefaultComboBoxModel<ColumnSelected> dcbRequiereStock;
    private DefaultComboBoxModel<ColumnSelected> dcbCantidadDisponible;
    private ReadFileCVS rf = null;

    public ImportProductWindows() {

        notificacion = new Notificacion();

        createDialog();

        Container content = dialog.getContentPane();
        content.setLayout(new GridBagLayout());
        content.setBackground(GlobalUI.getInstance().getTheme().getPanelUI().getBackground());
        content.add(pPrincipal(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));

        dialog.setVisible(true);
    }
    public JPanel pPrincipal(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        bSelectFiled = new Button(sp.getValue("importar.button.selected_file"));
        bSelectFiled.setActionCommand("SELECT_FILE");
        bSelectFiled.addActionListener(this);

        bImportar = new Button(sp.getValue("importar.button.import"), new ImageIcon("icon/import16.png"));
        bImportar.setActionCommand("IMPORTAR");
        bImportar.addActionListener(this);
        bImportar.setEnabled(false);

        bCancelar = new Button(sp.getValue("button.cancelar"));
        bCancelar.setActionCommand("CANCELAR");
        bCancelar.addActionListener(this);

        progressBar = new JProgressBar(0,0);
        progressBar.setBorderPainted(false);
        progressBar.setVisible(false);

        Dimension dim = new Dimension(146, 14);
        progressBar.setSize(dim);
        progressBar.setPreferredSize(dim);

        lFile = new JLabel();
        lFile.setFont(GlobalUI.getInstance().getTheme().getPanelUI().getFont());
        lFile.setPreferredSize(new Dimension(400,21));
        lFile.setForeground(GlobalUI.getInstance().getTheme().getPanelUI().getForeground());
        //lFile.setOpaque(true);
        //lFile.setBackground(Color.RED);

        JLabel lCodigo = new JLabel(sp.getValue("productos.label.codigo")+":");
        JLabel lCodigoBarra = new JLabel(sp.getValue("productos.label.codigobarra")+":");
        JLabel lNombre = new JLabel(sp.getValue("productos.label.nombre")+":");
        JLabel lUnidad = new JLabel(sp.getValue("productos.label.unidadmedida")+":");
        JLabel lDisponible = new JLabel(sp.getValue("productos.label.disponible")+":");
        JLabel lCosto = new JLabel(sp.getValue("productos.label.costo")+":");
        JLabel lPrecio1 = new JLabel(sp.getValue("productos.label.precio1")+":");
        JLabel lPrecio2 = new JLabel(sp.getValue("productos.label.precio2")+":");
        JLabel lPrecio3 = new JLabel(sp.getValue("productos.label.precio3")+":");
        JLabel lIncluyeImpuesto = new JLabel(sp.getValue("productos.label.precioIncluyeImpuesto")+":");
        JLabel lStockCritico = new JLabel(sp.getValue("productos.label.advertenciaStockCritico")+":");
        JLabel lRequiereStock = new JLabel(sp.getValue("productos.label.requiere_stock")+":");
        JLabel lCantidadDisponible = new JLabel(sp.getValue("productos.label.cantidad_disponible")+":");

        dcbCodigo = new DefaultComboBoxModel<ColumnSelected> ();
        dcbCodigoBarra = new DefaultComboBoxModel<ColumnSelected> ();
        dcbNombre = new DefaultComboBoxModel<ColumnSelected> ();
        dcbUnidad = new DefaultComboBoxModel<ColumnSelected> ();
        dcbDisponible = new DefaultComboBoxModel<ColumnSelected> ();
        dcbCosto = new DefaultComboBoxModel<ColumnSelected> ();
        dcbPrecio1 = new DefaultComboBoxModel<ColumnSelected> ();
        dcbPrecio2 = new DefaultComboBoxModel<ColumnSelected> ();
        dcbPrecio3 = new DefaultComboBoxModel<ColumnSelected> ();
        dcbIncluyeImpuesto = new DefaultComboBoxModel<ColumnSelected> ();
        dcbStockCritico = new DefaultComboBoxModel<ColumnSelected> ();
        dcbRequiereStock = new DefaultComboBoxModel<ColumnSelected> ();
        dcbCantidadDisponible = new DefaultComboBoxModel<ColumnSelected> ();
        Dimension cbdim = new Dimension(210,21);

        cbCodigo = new ComboBox<ColumnSelected>(dcbCodigo);
        cbCodigo.setPreferredSize(cbdim);
        cbCodigoBarra = new ComboBox<ColumnSelected>(dcbCodigoBarra);
        cbCodigoBarra.setPreferredSize(cbdim);
        cbNombre = new ComboBox<ColumnSelected>(dcbNombre);
        cbNombre.setPreferredSize(cbdim);
        cbUnidad = new ComboBox<ColumnSelected>(dcbUnidad);
        cbUnidad .setPreferredSize(cbdim);
        cbDisponible = new ComboBox<ColumnSelected>(dcbDisponible);
        cbDisponible.setPreferredSize(cbdim);
        cbCosto = new ComboBox<ColumnSelected>(dcbCosto);
        cbCosto.setPreferredSize(cbdim);
        cbPrecio1 = new ComboBox<ColumnSelected>(dcbPrecio1);
        cbPrecio1.setPreferredSize(cbdim);
        cbPrecio2 = new ComboBox<ColumnSelected>(dcbPrecio2);
        cbPrecio2.setPreferredSize(cbdim);
        cbPrecio3 = new ComboBox<ColumnSelected>(dcbPrecio3);
        cbPrecio3.setPreferredSize(cbdim);
        cbIncluyeImpuesto = new ComboBox<ColumnSelected>(dcbIncluyeImpuesto);
        cbIncluyeImpuesto.setPreferredSize(cbdim);
        cbStockCritico = new ComboBox<ColumnSelected>(dcbStockCritico);
        cbStockCritico.setPreferredSize(cbdim);
        cbRequiereStock = new ComboBox<ColumnSelected>(dcbRequiereStock);
        cbRequiereStock.setPreferredSize(cbdim);
        cbCantidadDisponible = new ComboBox<ColumnSelected>(dcbCantidadDisponible);
        cbCantidadDisponible.setPreferredSize(cbdim);

        clearcb();

        panel.add(bSelectFiled, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(lFile, LayoutPanel.constantePane(1, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 1.0f, 0.0f));

        panel.add(lCodigo, LayoutPanel.constantePane(0, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbCodigo, LayoutPanel.constantePane(2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCodigoBarra, LayoutPanel.constantePane(0, 2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbCodigoBarra, LayoutPanel.constantePane(2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lNombre, LayoutPanel.constantePane(0, 3, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbNombre, LayoutPanel.constantePane(2, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lUnidad, LayoutPanel.constantePane(0, 4, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbUnidad, LayoutPanel.constantePane(2, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lDisponible, LayoutPanel.constantePane(0, 5, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbDisponible, LayoutPanel.constantePane(2, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCosto, LayoutPanel.constantePane(0, 6, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbCosto, LayoutPanel.constantePane(2, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio1, LayoutPanel.constantePane(0, 7, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbPrecio1, LayoutPanel.constantePane(2, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio2, LayoutPanel.constantePane(0, 8, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbPrecio2, LayoutPanel.constantePane(2, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio3, LayoutPanel.constantePane(0, 9, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbPrecio3, LayoutPanel.constantePane(2, 9, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lIncluyeImpuesto, LayoutPanel.constantePane(0, 10, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbIncluyeImpuesto, LayoutPanel.constantePane(2, 10, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lStockCritico, LayoutPanel.constantePane(0, 11, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbStockCritico, LayoutPanel.constantePane(2, 11, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lRequiereStock, LayoutPanel.constantePane(0, 12, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbRequiereStock, LayoutPanel.constantePane(2, 12, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCantidadDisponible, LayoutPanel.constantePane(0, 13, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbCantidadDisponible, LayoutPanel.constantePane(2, 13, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(bImportar, LayoutPanel.constantePane(2, 14, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 5, 0, 0, 0.0f, 1.0f));
        panel.add(bCancelar, LayoutPanel.constantePane(2, 15, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 0, 0, 5, 10, 0.0f, 0.0f));
        panel.add(progressBar, LayoutPanel.constantePane(0, 15, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 10, 5, 0, 0.0f, 0.0f));
        return panel;

    }

    private void createDialog(){

        dialog = new JDialog(FrameMain.frame, sp.getValue("importar.label.title"), true);


        Dimension dim = new Dimension(700,490);

        //dialog.setUndecorated(true);
        dialog.setPreferredSize(dim);
        dialog.setSize(dim);
        dialog.setResizable(false);
        //dialog.setDefaultCloseOperation(0);
        dialog.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("IMPORTAR".equals(command)){
            if(rf !=null ){
                System.out.println("Iniciando lectura");
                dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));

                rf.runExport();

                Thread t2 = new Thread(()-> {
                    progressBar.setVisible(true);
                    progressBar.repaint();
                    progressBar.updateUI();
                    while (rf.isAlive()) {
                        progressBar.setValue(rf.getCurrentCount());
                        progressBar.repaint();
                    }
                    progressBar.setIndeterminate(true);
                    //System.out.println("Fin de la lectura");
                    eText(true);
                    try{
                        Thread.sleep(2000);
                    }catch(InterruptedException exc){}

                    progressBar.setIndeterminate(false);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                });

                t2.start();

                eText(false);

                Thread t1 = new Thread(()-> {
                    progressBar.setIndeterminate(true);
                    int codigo = dcbCodigo.getElementAt(cbCodigo.getSelectedIndex()).getIndex();
                    int codigoBarra = dcbCodigoBarra.getElementAt(cbCodigoBarra.getSelectedIndex()).getIndex();
                    int nombre = dcbNombre.getElementAt(cbNombre.getSelectedIndex()).getIndex();
                    int unidad = dcbUnidad.getElementAt(cbUnidad.getSelectedIndex()).getIndex();
                    int disponible = dcbDisponible.getElementAt(cbDisponible.getSelectedIndex()).getIndex();
                    int costo = dcbCosto.getElementAt(cbCosto.getSelectedIndex()).getIndex();
                    int precio1 = dcbPrecio1.getElementAt(cbPrecio1.getSelectedIndex()).getIndex();
                    int precio2 = dcbPrecio2.getElementAt(cbPrecio2.getSelectedIndex()).getIndex();
                    int precio3 = dcbPrecio3.getElementAt(cbPrecio3.getSelectedIndex()).getIndex();
                    int incluyeImpuesto = dcbIncluyeImpuesto.getElementAt(cbIncluyeImpuesto.getSelectedIndex()).getIndex();
                    int stockCritico = dcbStockCritico.getElementAt(cbStockCritico.getSelectedIndex()).getIndex();
                    int requiereStock = dcbRequiereStock.getElementAt(cbRequiereStock.getSelectedIndex()).getIndex();
                    int cantidadDisponible = dcbCantidadDisponible.getElementAt(cbCantidadDisponible.getSelectedIndex()).getIndex();
                    if (codigo == -1) {
                        OptionPane.error(FrameMain.frame,sp.getValue("importar.message.error_codigo"));
                        rf.stopExport();
                    } else if (nombre == -1) {
                        OptionPane.error(FrameMain.frame,sp.getValue("importar.message.error_nombre"));
                        rf.stopExport();
                    } else {
                        notificacion.start(sp.getValue("productos.label.title"),sp.getValue("importar.iniciando"));

                        progressBar.setIndeterminate(false);
                        rf.getData(codigo, codigoBarra, nombre, unidad, disponible, costo, precio1, precio2, precio3,
                                incluyeImpuesto, stockCritico, requiereStock, cantidadDisponible);

                        clearcb();
                        bImportar.setEnabled(false);
                        lFile.setText(null);
                        lFile.setIcon(null);
                    }
                    dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                });

                t1.start();
            }
        }
        else if("CANCELAR".equals(command)){
            dialog.setVisible(false);
            dialog.dispose();
        }
        else if("SELECT_FILE".equals(command)){
            final JFileChooser fc = new JFileChooser();
            fc.setDialogTitle(sp.getValue("label.buscar_archivo_csv"));
            fc.setFileFilter(new OpenFileFilter("csv",sp.getValue("extension.csv")) );
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setMultiSelectionEnabled(false);
            Color background = UIManager.getColor("Label.background");
            fc.setBackground(background);
            fc.setOpaque(true);
            int seleccion = fc.showOpenDialog(dialog);
            if(seleccion == JFileChooser.APPROVE_OPTION){
                dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                File fd = fc.getSelectedFile();
                if(fd.canRead()) {

                    clearcb();
                    
                    /*System.out.println(fd.getName());
                    System.out.println(fd.getParent());
                    System.out.println(fd.getAbsolutePath());*/
                    lFile.setText(fd.getAbsolutePath());
                    rf = new ReadFileCVS(fd);
                    progressBar.setMaximum(rf.getAmountProduct());
                    System.out.println(rf.getAmountProduct());
                    //List<ColumnSelected> listCols = rf.getNameColumns();
                    String listCols[] = rf.getNameColumns();
                    if(listCols == null || listCols.length==0){
                        lFile.setIcon(new ImageIcon("icon/error_file.png"));
                    }
                    else {
                        lFile.setIcon(new ImageIcon("icon/ok.png"));

                        for (int i = 0;i<listCols.length;i++) {
                            ColumnSelected cl = new ColumnSelected(listCols[i], i );
                            dcbCodigo.addElement(cl);
                            dcbCodigoBarra.addElement(cl);
                            dcbNombre.addElement(cl);
                            dcbUnidad.addElement(cl);
                            dcbDisponible.addElement(cl);
                            dcbCosto.addElement(cl);
                            dcbPrecio1.addElement(cl);
                            dcbPrecio2.addElement(cl);
                            dcbPrecio3.addElement(cl);
                            dcbIncluyeImpuesto.addElement(cl);
                            dcbStockCritico.addElement(cl);
                            dcbRequiereStock.addElement(cl);
                            dcbCantidadDisponible.addElement(cl);
                        }
                        bImportar.setEnabled(true);
                    }
                    dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }else{
                    dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    lFile.setIcon(new ImageIcon("icon/error_file.png"));
                    lFile.setText(fd.getAbsolutePath());
                    OptionPane.error(FrameMain.frame,sp.getValue("importar.error_leer_archivo"));
                }
            }

            //FileDialog fd = new FileDialog(FrameMain.frame, sp.getValue("label.buscar_archivo_csv"), FileDialog.LOAD);
            //fd.setDirectory("C:\\");
            /*fd.setFilenameFilter((dir, name)-> {
                    return name.endsWith("*.csv");
                }
            );*

            fd.setFile("*.csv");
            fd.setVisible(true);

            String filename = fd.getFile();

            if (filename != null) {
                String dir = fd.getDirectory();
                System.out.println("You chose " + filename);
                System.out.println("You Directory " + dir);
            }*/

        }
    }
    private void clearcb(){
        dcbCodigo.removeAllElements();
        dcbCodigoBarra.removeAllElements();
        dcbNombre.removeAllElements();
        dcbUnidad.removeAllElements();
        dcbDisponible.removeAllElements();
        dcbCosto.removeAllElements();
        dcbPrecio1.removeAllElements();
        dcbPrecio2.removeAllElements();
        dcbPrecio3.removeAllElements();
        dcbIncluyeImpuesto.removeAllElements();
        dcbStockCritico.removeAllElements();
        dcbRequiereStock.removeAllElements();
        dcbCantidadDisponible.removeAllElements();

        dcbCodigo.addElement(new ColumnSelected(" ",-1));
        dcbCodigoBarra.addElement(new ColumnSelected(" ",-1));
        dcbNombre.addElement(new ColumnSelected(" ",-1));
        dcbUnidad.addElement(new ColumnSelected(" ",-1));
        dcbDisponible.addElement(new ColumnSelected(sp.getValue("label.si"),-2));
        dcbDisponible.addElement(new ColumnSelected(sp.getValue("label.no"),-3));
        dcbCosto.addElement(new ColumnSelected(" ",-1));
        dcbPrecio1.addElement(new ColumnSelected(" ",-1));
        dcbPrecio2.addElement(new ColumnSelected(" ",-1));
        dcbPrecio3.addElement(new ColumnSelected(" ",-1));
        dcbIncluyeImpuesto.addElement(new ColumnSelected(sp.getValue("label.si"),-2));
        dcbIncluyeImpuesto.addElement(new ColumnSelected(sp.getValue("label.no"),-3));
        dcbStockCritico.addElement(new ColumnSelected(" ",-1));
        dcbRequiereStock.addElement(new ColumnSelected(sp.getValue("label.si"),-2));
        dcbRequiereStock.addElement(new ColumnSelected(sp.getValue("label.no"),-3));
        dcbCantidadDisponible.addElement(new ColumnSelected(" ",-1));

        cbRequiereStock.setSelectedIndex(1);
    }
    
    private void eText(boolean e){
        cbCodigo.setEnabled(e);
        cbCodigoBarra.setEnabled(e);
        cbNombre.setEnabled(e);
        cbUnidad.setEnabled(e);
        cbDisponible.setEnabled(e);
        cbCosto.setEnabled(e);
        cbPrecio1.setEnabled(e);
        cbPrecio2.setEnabled(e);
        cbPrecio3.setEnabled(e);
        cbIncluyeImpuesto.setEnabled(e);
        cbStockCritico.setEnabled(e);
        cbRequiereStock.setEnabled(e);
        cbCantidadDisponible.setEnabled(e);
        bImportar.setEnabled(e);
        bCancelar.setEnabled(e);
        bSelectFiled.setEnabled(e);
    }
}
