package interfaces.icontrol.pcontrol;

import clases.*;
import conexion.hilos.SentTablePacket;
import interfaces.Config;
import conexion.DataTables;
import interfaces.iregistro.eventos.HiloNReapertura;
import interfaces.iregistro.eventos.HiloNRegistro;
import interfaces.iregistro.eventos.HiloORegistro;
import interfaces.iregistro.eventos.HiloSRegistro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class PanelControl extends JPanel {

    public boolean isFilter = false;

    //FALTA INDICADOR DE REGISTROS EN TABLA BUSQUEDA

    //VALORES
    public int numRegistros;
    public JButton nuevoRegistro, nuevaReapertura, filtrar, abrirRegistro,
            abrirBusqueda, actualizar, buscar;
    public JRadioButton rbtnAbiertos, rbtnTodos;
    public ButtonGroup groupEstado;
    public JLabel labTBusqueda, labTRegistro, labNRegistros, labTitulo, labNReferencia,
            labNPoliza, labNTelefono, labNFactura, imgRegistro, imgBusqueda, ultimaAccion;
    public JTextField textReferencia, textPoliza, textTelf, textFactura;
    public JComboBox cbCompania;

    //TABLAS
    public DefaultTableModel modelRegistro, modelBusqueda;
    public JTable tabRegistro, tabBusqueda;
    private JScrollPane scrollRegistro, scrollBusqueda;

    public PanelControl(){
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10,10,10,10));

        //LAYOUT
        GridBagLayout gbl_content = new GridBagLayout();
        gbl_content.columnWidths = new int[]{0};
        gbl_content.rowHeights = new int[]{0};
        gbl_content.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_content.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_content);

        //CARGA DE DATOS

        //INICIALIZACION DE COMPONENTES
        initComponents();

        //COMPONENTES REGISTROS ////////////////////////////////////////////////////////////////////////////////////////

        //LABEL REGISTROS ACTIVOS
        GridBagConstraints grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,0,0);
        grid.insets = new Insets(20,10,10,0);
        grid.gridwidth = 2;
        add(labTitulo, grid);

        //IMG REGISTRO
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE,0,0);
        add(imgRegistro, grid);

        //BOTON NUEVO REGISTRO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.VERTICAL,2,0);
        add(nuevoRegistro, grid);

        //BOTON NUEVA REAPERTURA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.VERTICAL,4,0);
        grid.insets = new Insets(0,0,0,10);
        add(nuevaReapertura, grid);

        //BOTON R ABIERTOS ESTADO
        grid = newPosicion(GridBagConstraints.NORTH,
                GridBagConstraints.NONE,5,0);
        add(rbtnAbiertos, grid);

        //BOTON R TODOS ESTADO
        grid = newPosicion(GridBagConstraints.SOUTH,
                GridBagConstraints.NONE,5,0);
        add(rbtnTodos, grid);

        //BOTON ACTUALIZAR
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.VERTICAL,6,0);
        grid.insets = new Insets(0,10,0,0);
        add(actualizar, grid);

        //TABLA REGISTROS ACTIVOS
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,0,1);
        grid.insets = new Insets(10,0,10,0);
        grid.gridwidth = 7;
        grid.gridheight = 2;
        grid.weighty = 0.09;
        add(scrollRegistro, grid);

        //LABEL NUMERO DE REGISTROS
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.BOTH,0,3);
        grid.insets = new Insets(0,5,30,0);
        grid.gridwidth = 2;
        add(labNRegistros, grid);

        //BOTON FILTRAR
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.BOTH,4,3);
        grid.insets = new Insets(0,15,30,0);
        add(filtrar, grid);

        //BOTON ABRIR REGISTRO
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.BOTH,6,3);
        grid.insets = new Insets(0,0,30,0);
        add(abrirRegistro, grid);

        //COMPONENTES BUSQUEDA /////////////////////////////////////////////////////////////////////////////////////////

        //LABEL BUSQUEDA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,0,4);
        grid.insets = new Insets(50,10,0,0);
        grid.gridheight = 2;
        add(labTBusqueda, grid);

        //IMG REGISTRO
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,0,4);
        grid.insets = new Insets(0,20,0,0);
        grid.gridheight = 3;
        add(imgBusqueda, grid);

        //LABEL COMPANIA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,4);
        grid.insets = new Insets(20,0,0,18);
        add(labTRegistro, grid);

        //COMBO BOX COMPANIA
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,2,4);
        grid.insets = new Insets(20,0,0,0);
        add(cbCompania, grid);

        //LABEL NUM REFERENCIA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,5);
        grid.insets = new Insets(8,0,0,18);
        add(labNReferencia, grid);

        //CAMPO NUM REFERENCIA
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,2,5);
        grid.insets = new Insets(8,0,0,0);
        add(textReferencia, grid);

        //LABEL NUM POLIZA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,6);
        grid.insets = new Insets(8,0,0,18);
        add(labNPoliza, grid);

        //CAMPO NUM POLIZA
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,2,6);
        grid.insets = new Insets(8,0,0,0);
        add(textPoliza, grid);


        //LABEL TELEFONO
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,3,5);
        grid.insets = new Insets(8,5,0,0);
        add(labNTelefono, grid);

        //CAMPO TELEFONO
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,4,5);
        grid.insets = new Insets(8,10,0,0);
        add(textTelf, grid);

        //LABEL NUM FACTURA
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE,3,6);
        grid.insets = new Insets(8,5,0,0);
        add(labNFactura, grid);

        //CAMPO NUM FACTURA
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,4,6);
        grid.insets = new Insets(8,10,0,0);
        add(textFactura, grid);

        //BOTON BUSCAR
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,5,4);
        grid.insets = new Insets(20,10,0,0);
        grid.gridwidth = 2;
        grid.gridheight = 3;
        add(buscar, grid);

        //TABLA BUSQUEDA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,0,9);
        grid.insets = new Insets(10,0,0,0);
        grid.gridwidth = 7;
        grid.gridheight = 2;
        grid.weighty = 0.09;
        add(scrollBusqueda, grid);

        //BOTON ABRIR BUSQUEDA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,6,11);
        grid.insets = new Insets(10,0,0,0);
        add(abrirBusqueda, grid);

        //LABEL ULTIMA ACCION
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,0,11);
        grid.insets = new Insets(10,10,0,0);
        grid.gridwidth = 7;
        add(ultimaAccion, grid);

    }

    //METODO QUE INICIALIZA LOS COMPONENTES DEL PANEL CONTROL
    public void initComponents(){
        //LABEL TITULO REGISTRO
        labTitulo = new JLabel("REGISTROS ACTIVOS");
        labTitulo.setFont(Config.FUENTETITULO);
        Font fontt= labTitulo.getFont();

        //SUBRAYADO DE TEXTO
        Map attributest = fontt.getAttributes();
        attributest.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        labTitulo.setFont(fontt.deriveFont(attributest));

        //LABEL TITULO REGISTRO
        labNRegistros = new JLabel("Nº Registros: "+numRegistros);
        labNRegistros.setFont(Config.FUENTE14);

        //IMG REGISTRO
        imgRegistro = new JLabel();
        imgRegistro.setIcon( new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logo.png"));

        //IMG BUSQUEDA
        imgBusqueda = new JLabel();
        imgBusqueda.setIcon( new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\busq.png"));

        //LABEL BUSQUEDA
        labTBusqueda = new JLabel("BÚSQUEDA");
        labTBusqueda.setFont(Config.FUENTETITULO);
        Font fontb= labTBusqueda.getFont();
        Map attributesb = fontb.getAttributes();
        attributesb.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        labTBusqueda.setFont(fontb.deriveFont(attributesb));

        //LABEL TITULO REGISTRO
        labTRegistro = new JLabel("Compañia:");

        //LABEL NUMERO REFERENCIA CIA
        labNReferencia = new JLabel("Referencia Cia:");

        //LABEL NUMERO POLIZA
        labNPoliza = new JLabel("Nº Póliza:");


        //LABEL TELEFONO
        labNTelefono = new JLabel("Nº Telefono:");

        //LABEL TELEFONO
        labNFactura = new JLabel("Nº Factura:");

        //LABEL ULTIMA ACCION
        ultimaAccion = new JLabel("ÚLTIMA ACCIÓN -- ");

        //CAMPO REF CIA
        textReferencia = new JTextField();
        textReferencia.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    DataTables.cleanBusqueda();
                    searchRegistro();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //CAMPO POLIZA
        textPoliza = new JTextField();
        textPoliza.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    DataTables.cleanBusqueda();
                    searchRegistro();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //CAMPO TELEFONO
        textTelf = new JTextField();
        textTelf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    DataTables.cleanBusqueda();
                    searchRegistro();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        //CAMPO TELEFONO
        textFactura = new JTextField();
        textFactura.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    DataTables.cleanBusqueda();
                    searchRegistro();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //BOTON NUEVO REGISTRO
        nuevoRegistro = new JButton("NUEVO REGISTRO");
        nuevoRegistro.setBackground(Config.AUX2);
        nuevoRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread hiloNRegistro = new Thread(new HiloNRegistro());
                hiloNRegistro.start();
            }
        });

        //BOTON ACTUALIZAR
        actualizar = new JButton();
        actualizar.setBackground(Config.AUX5);
        actualizar.setIcon(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\act.png"));
        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Config.pControl.pControl.isFilter){
                    DataTables.actTablasPanelControl();
                    Config.pControl.pControl.actualizar.setBackground(Config.AUX5);
                    Config.pControl.pControl.isFilter = false;
                } else {
                    actualizar.setBackground(Config.AUX2);
                    Config.pControl.pControl.changeUltimaAccion("ACTUALIZANDO...");
                    Thread sendPacket = new Thread(new SentTablePacket());
                    sendPacket.start();
                }
            }
        });

        //BOTON NUEVA REAPERTURA
        nuevaReapertura = new JButton("NUEVA REAPERTURA");
        nuevaReapertura.setBackground(Config.AUX6);
        nuevaReapertura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Registro nReap = new Registro();
                    int codigo = (int) modelRegistro.getValueAt(tabRegistro.getSelectedRow(), 0);
                    for (Registro r : DataTables.getRegistros()){
                        if (r.getCodRegistro() == codigo){
                            nReap = r;
                        }
                    }
                    if (nReap.getEstadoRegistro().equals("CERRADO")){
                        if (nReap.getNumeroReaperturas() > 0){
                            //PROBAR
                            int opt = Config.option("Este Registro ya ha sido Reaperturado,\n" +
                                    "¿Quiere visualizar el Registro Base con sus Reaperturas?");
                            if (opt == 0){
                                //VER
                                Thread reg = new Thread(new HiloORegistro());
                                reg.start();
                            } else {
                                HiloNReapertura hilo = new HiloNReapertura();
                                Thread reg = new Thread(hilo);
                                reg.start();
                            }
                        } else {
                            HiloNReapertura hilo = new HiloNReapertura();
                            Thread reg = new Thread(hilo);
                            reg.start();
                        }
                    } else Config.error("Seleccione un Registro CERRADO de la tabla de registros.");

                } catch (Exception ex){
                    Config.error("Seleccione un Registro CERRADO de la tabla de registros.");
                }
            }
        });

        //BOTON FILTRAR
        filtrar = new JButton("FILTRAR");
        filtrar.setBackground(Config.COLOR5);
        filtrar.setForeground(Color.WHITE);
        filtrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Config.filtro == null){
                    Config.filtro = new InterfazFiltroRegistro();
                } else Config.warning("Existe una ventana de Filtro Abierta.");
            }
        });

        //BOTON ABRIR REGISTRO
        abrirRegistro = new JButton("ABRIR");
        abrirRegistro.setBackground(Config.AUX4);
        abrirRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread reg = new Thread(new HiloORegistro());
                reg.start();
            }
        });


        //BOTON ABRIR REGISTRO BUSQUEDA
        abrirBusqueda = new JButton("ABRIR");
        abrirBusqueda.setBackground(Config.AUX4);
        abrirBusqueda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread bus = new Thread(new HiloSRegistro());
                bus.start();
            }
        });

        //BOTON BUSCAR
        buscar = new JButton(" BUSCAR");
        buscar.setBackground(Config.COLOR4);
        buscar.setForeground(Color.WHITE);
        ImageIcon search =new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\search.png");
        buscar.setIcon(search);
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataTables.cleanBusqueda();
                searchRegistro();
                Config.pControl.pControl.changeUltimaAccion("BÚSQUEDA.");
            }
        });

        //RADIO BUTTONS ESTADO
        rbtnAbiertos = new JRadioButton("Abiertos");
        rbtnAbiertos.setBackground(Config.COLOR2);
        rbtnTodos = new JRadioButton("Todos");
        rbtnTodos.setBackground(Config.COLOR2);

        //GRUPO RADIO BUTTONS
        groupEstado = new ButtonGroup();
        groupEstado.add(rbtnAbiertos);
        groupEstado.add(rbtnTodos);
        rbtnAbiertos.setSelected(true);

        //COMBO BOX COMPANIA
        cbCompania = new JComboBox();
        cbCompania.addItem("Default");
        cbCompania.setBackground(Color.WHITE);

        //TABLA REGISTROS ACTIVOS
        modelRegistro = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        modelRegistro.addColumn("Codigo");
        modelRegistro.addColumn("Compañia");
        modelRegistro.addColumn("Referencia Cia");
        modelRegistro.addColumn("Estado");
        modelRegistro.addColumn("Perito");
        modelRegistro.addColumn("Fecha");
        modelRegistro.addColumn("Pago");
        modelRegistro.addColumn("Tipo");
        tabRegistro = new JTable(modelRegistro);
        tabRegistro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabRegistro.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabRegistro.getColumnModel().getColumn(1).setPreferredWidth(40);
        tabRegistro.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabRegistro.getColumnModel().getColumn(3).setPreferredWidth(25);
        tabRegistro.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabRegistro.getColumnModel().getColumn(5).setPreferredWidth(20);
        tabRegistro.getColumnModel().getColumn(6).setPreferredWidth(10);
        tabRegistro.getColumnModel().getColumn(7).setPreferredWidth(20);
        tabRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Thread reg = new Thread(new HiloORegistro());
                    reg.start();
                }
            }
        });
        scrollRegistro = new JScrollPane(tabRegistro);

        //TABLA BUSQUEDA
        modelBusqueda = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        modelBusqueda.addColumn("Codigo");
        modelBusqueda.addColumn("Compañia");
        modelBusqueda.addColumn("Referencia Cia");
        modelBusqueda.addColumn("Poliza");
        modelBusqueda.addColumn("Estado");
        modelBusqueda.addColumn("Telefono");
        modelBusqueda.addColumn("Factura");
        modelBusqueda.addColumn("Tipo");
        tabBusqueda = new JTable(modelBusqueda);
        tabBusqueda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabBusqueda.getColumnModel().getColumn(0).setPreferredWidth(15);
        tabBusqueda.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabBusqueda.getColumnModel().getColumn(2).setPreferredWidth(100);
        scrollBusqueda = new JScrollPane(tabBusqueda);
        tabBusqueda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Thread bus = new Thread(new HiloSRegistro());
                    bus.start();
                }
            }
        });
    }

    public void searchRegistro(){
        boolean emptySearchBox = false;
        DataTables.setSearch( new ArrayList<Registro>());

        int filasBusqueda = Config.pControl.pControl.modelBusqueda.getRowCount();
        for (int i = 0; i < filasBusqueda; i++){
            Config.pControl.pControl.modelBusqueda.removeRow(0);
        }
        String compania, referencia, poliza, factura;
        int telefono, codCompania = 0;
        compania = (String) cbCompania.getSelectedItem();

        if(!compania.equals("Default")){
            //TENGO COMPANIA
            for (Compania com : DataTables.getCompanias()){
                if (com.getNombreCompania().equals(compania)){
                    codCompania = com.getCodCompania();
                }
            }
            if(!textReferencia.getText().isEmpty()){
                //TENGO COMPANIA Y REFERENCIA
                if (!textPoliza.getText().isEmpty()){
                    //TENGO COMPANIA, REFERENCIA Y POLIZA
                    if (!textTelf.getText().isEmpty()){
                        //TENGO COMPANIA, REFERENCIA, CON POLIZA
                        if(!textFactura.getText().isEmpty()) {
                            //TODOS LOS CAMPOS

                            referencia = textReferencia.getText();
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                poliza = textPoliza.getText();
                                telefono = Integer.parseInt(textTelf.getText());
                                factura = textFactura.getText();

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if(reg.getSiniestro().getCompania() == codCompania){
                                                if (reg.getReferenciaCom().contains(referencia)){
                                                    if (reg.getSiniestro().getNumPoliza().contains(poliza)){
                                                        if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                                            DataTables.getSearch().add(reg);

                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }

                        }else {
                            //TODOS SIN FACTURA
                            referencia = textReferencia.getText();
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                poliza = textPoliza.getText();
                                telefono = Integer.parseInt(textTelf.getText());

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if(reg.getSiniestro().getCompania() == codCompania){
                                                if (reg.getReferenciaCom().contains(referencia)){
                                                    if (reg.getSiniestro().getNumPoliza().contains(poliza)){

                                                        DataTables.getSearch().add(reg);

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }else {
                        //SIN TELEFONO
                        if(!textFactura.getText().isEmpty()) {
                            //SIN TELEFONO Y CON FACTURA
                            referencia = textReferencia.getText();
                            poliza = textPoliza.getText();
                            factura = textFactura.getText();

                            for (Registro reg : DataTables.getRegistros()){

                                if(reg.getSiniestro().getCompania() == codCompania){
                                    if (reg.getReferenciaCom().contains(referencia)){
                                        if (reg.getSiniestro().getNumPoliza().contains(poliza)){
                                            if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                                DataTables.getSearch().add(reg);

                                            }
                                        }
                                    }
                                }
                            }

                        }else {
                            //SIN TELEFONO NI FACTURA
                            referencia = textReferencia.getText();
                            poliza = textPoliza.getText();

                            for (Registro reg : DataTables.getRegistros()){

                                if(reg.getSiniestro().getCompania() == codCompania){
                                    if (reg.getReferenciaCom().contains(referencia)){
                                        if (reg.getSiniestro().getNumPoliza().contains(poliza)){

                                            DataTables.getSearch().add(reg);

                                        }
                                    }
                                }
                            }

                        }
                    }

                }else {
                    //CON COMPANIA, REFERENCIA Y SIN POLIZA
                    if (!textTelf.getText().isEmpty()){
                        //TENGO COMPANIA, REFERENCIA, SIN POLIZA
                        if(!textFactura.getText().isEmpty()) {
                            //TODOS SIN POLIZA

                            referencia = textReferencia.getText();
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                telefono = Integer.parseInt(textTelf.getText());
                                factura = textFactura.getText();

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){

                                            if(reg.getSiniestro().getCompania() == codCompania){
                                                if (reg.getReferenciaCom().contains(referencia)){
                                                    if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                                        DataTables.getSearch().add(reg);

                                                    }

                                                }

                                            }
                                        }
                                    }
                                }
                            }

                        }else {
                            //TODOS SIN POLIZA NI FACTURA

                            referencia = textReferencia.getText();
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                telefono = Integer.parseInt(textTelf.getText());

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if(reg.getSiniestro().getCompania() == codCompania){
                                                if (reg.getReferenciaCom().contains(referencia)){

                                                    DataTables.getSearch().add(reg);

                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else {
                        //SIN TELEFONO Y SIN POLIZA
                        if (!textFactura.getText().isEmpty()) {
                            //SIN TELEFONO, SIN POLIZA Y CON FACTURA

                            referencia = textReferencia.getText();
                            factura = textFactura.getText();

                            for (Registro reg : DataTables.getRegistros()){

                                if(reg.getSiniestro().getCompania() == codCompania){
                                    if (reg.getReferenciaCom().contains(referencia)){
                                        if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                            DataTables.getSearch().add(reg);

                                        }
                                    }
                                }
                            }

                        } else {
                            //COMPANIA Y REFERENCIA

                            referencia = textReferencia.getText();

                            for (Registro reg : DataTables.getRegistros()){

                                if(reg.getSiniestro().getCompania() == codCompania){
                                    if (reg.getReferenciaCom().contains(referencia)){

                                        DataTables.getSearch().add(reg);

                                    }
                                }
                            }

                        }
                    }
                }

            }else {
                //CON COMPANIA Y SIN REFERENCIA
                if (!textPoliza.getText().isEmpty()){
                    //CON COMPANIA, SIN REFERENCIA Y CON POLIZA
                    if (!textTelf.getText().isEmpty()){
                        //TENGO COMPANIA, SIN REFERENCIA, CON POLIZA
                        if(!textFactura.getText().isEmpty()) {
                            //TODOS SIN REFERENCIA

                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                poliza = textPoliza.getText();
                                telefono = Integer.parseInt(textTelf.getText());
                                factura = textFactura.getText();

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if(reg.getSiniestro().getCompania() == codCompania){
                                                if (reg.getSiniestro().getNumPoliza().contains(poliza)){
                                                    if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                                        DataTables.getSearch().add(reg);

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }else {
                            //TODOS SIN REFERENCIA NI FACTURA

                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                poliza = textPoliza.getText();
                                telefono = Integer.parseInt(textTelf.getText());

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if(reg.getSiniestro().getCompania() == codCompania){
                                                if (reg.getSiniestro().getNumPoliza().contains(poliza)){

                                                    DataTables.getSearch().add(reg);

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else {
                        //SIN TELEFONO Y CON POLIZA
                        if(!textFactura.getText().isEmpty()) {
                            //SIN TELEFONO, SIN REFERENCIA CON POLIZA Y CON FACTURA
                            poliza = textPoliza.getText();
                            factura = textFactura.getText();

                            for (Registro reg : DataTables.getRegistros()){
                                if(reg.getSiniestro().getCompania() == codCompania){
                                    if (reg.getSiniestro().getNumPoliza().contains(poliza)){
                                        if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                            DataTables.getSearch().add(reg);

                                        }
                                    }
                                }
                            }

                        }else {
                            //COMPANIA Y POLIZA

                            poliza = textPoliza.getText();

                            for (Registro reg : DataTables.getRegistros()){
                                if(reg.getSiniestro().getCompania() == codCompania){
                                    if (reg.getSiniestro().getNumPoliza().contains(poliza)){

                                        DataTables.getSearch().add(reg);

                                    }
                                }
                            }
                        }
                    }

                }else {
                    //CON COMPANIA, SIN REFERENCIA Y SIN POLIZA
                    if (!textTelf.getText().isEmpty()){
                        //TENGO COMPANIA, SIN REFERENCIA, SIN POLIZA Y CON TELEFONO
                        if(!textFactura.getText().isEmpty()) {
                            //TODOS SIN REFERENCIA Y SIN POLIZA

                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                telefono = Integer.parseInt(textTelf.getText());
                                factura = textFactura.getText();

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if(reg.getSiniestro().getCompania() == codCompania){
                                                if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                                    DataTables.getSearch().add(reg);

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }else {
                            //TODOS SIN REFERENCIA, POLIZA Y SIN FACTURA
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){

                                telefono = Integer.parseInt(textTelf.getText());

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if(reg.getSiniestro().getCompania() == codCompania){

                                                DataTables.getSearch().add(reg);

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else {
                        //SIN TELEFONO, SIN POLIZA, SIN REFERENCIA Y SIN TELEFONO
                        if(!textFactura.getText().isEmpty()) {
                            //COMPANIA Y FACTURA
                            factura = textFactura.getText();
                            for (Registro reg : DataTables.getRegistros()){
                                if(reg.getSiniestro().getCompania() == codCompania){
                                    if (reg.getFacturaRegistro().getNumFactura().contains(factura)){
                                        DataTables.getSearch().add(reg);

                                    }
                                }
                            }

                        }else {
                            //SOLO COMPANIA
                            for (Registro reg : DataTables.getRegistros()){
                                if(reg.getSiniestro().getCompania() == codCompania){
                                    DataTables.getSearch().add(reg);

                                }
                            }
                        }
                    }
                }
            }

        }else {
            //SIN COMPANIA
            if(!textReferencia.getText().isEmpty()){
                //SIN COMPANIA Y CON REFERENCIA
                if (!textPoliza.getText().isEmpty()){
                    //SIN COMPANIA, CON REFERENCIA Y CON POLIZA
                    if (!textTelf.getText().isEmpty()){
                        //SIN COMPANIA, CON REFERENCIA, CON POLIZA
                        if(!textFactura.getText().isEmpty()) {
                            //TODOS LOS CAMPOS SIN COMPANIA

                            referencia = textReferencia.getText();
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                poliza = textPoliza.getText();
                                telefono = Integer.parseInt(textTelf.getText());
                                factura = textFactura.getText();

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if (reg.getReferenciaCom().contains(referencia)){
                                                if (reg.getSiniestro().getNumPoliza().contains(poliza)){
                                                    if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                                        DataTables.getSearch().add(reg);

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }else {
                            //TODOS SIN FACTURA Y SIN COMPANIA

                            referencia = textReferencia.getText();
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                poliza = textPoliza.getText();
                                telefono = Integer.parseInt(textTelf.getText());

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if (reg.getReferenciaCom().contains(referencia)){
                                                if (reg.getSiniestro().getNumPoliza().contains(poliza)){


                                                    DataTables.getSearch().add(reg);


                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else {
                        //SIN TELEFONO NI COMPANIA
                        if(!textFactura.getText().isEmpty()) {
                            //SIN TELEFONO, SIN COMPANIA Y CON FACTURA

                            referencia = textReferencia.getText();
                            poliza = textPoliza.getText();
                            factura = textFactura.getText();

                            for (Registro reg : DataTables.getRegistros()){
                                if (reg.getReferenciaCom().contains(referencia)){
                                    if (reg.getSiniestro().getNumPoliza().contains(poliza)){
                                        if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                            DataTables.getSearch().add(reg);

                                        }
                                    }
                                }
                            }


                        }else {
                            //SOLO POLIZA Y REFERENCIA
                            referencia = textReferencia.getText();
                            poliza = textPoliza.getText();

                            for (Registro reg : DataTables.getRegistros()){
                                if (reg.getReferenciaCom().contains(referencia)){
                                    if (reg.getSiniestro().getNumPoliza().contains(poliza)){

                                            DataTables.getSearch().add(reg);

                                    }
                                }
                            }
                        }
                    }

                }else {
                    //SIN COMPANIA, CON REFERENCIA Y SIN POLIZA
                    if (!textTelf.getText().isEmpty()){
                        //SIN COMPANIA, CON REFERENCIA, SIN POLIZA
                        if(!textFactura.getText().isEmpty()) {
                            //SIN COMPANIA Y SIN POLIZA
                            referencia = textReferencia.getText();
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                telefono = Integer.parseInt(textTelf.getText());
                                factura = textFactura.getText();

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if (reg.getReferenciaCom().contains(referencia)){
                                                if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                                    DataTables.getSearch().add(reg);

                                                }

                                            }
                                        }
                                    }
                                }
                            }

                        }else {
                            //SIN POLIZA NI FACTURA NI COMPANIA
                            referencia = textReferencia.getText();
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                telefono = Integer.parseInt(textTelf.getText());

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if (reg.getReferenciaCom().contains(referencia)){

                                                DataTables.getSearch().add(reg);

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else {
                        //SIN TELEFONO SIN POLIZA Y SIN COMPANIA
                        if(!textFactura.getText().isEmpty()) {
                            //REFERENCIA Y FACTURA
                            referencia = textReferencia.getText();
                            factura = textFactura.getText();

                            for (Registro reg : DataTables.getRegistros()){

                                if (reg.getReferenciaCom().contains(referencia)){
                                    if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                        DataTables.getSearch().add(reg);

                                    }
                                }
                            }

                        }else {
                            //SOLO REFERENCIA
                            referencia = textReferencia.getText();

                            for (Registro reg : DataTables.getRegistros()){
                                if (reg.getReferenciaCom().contains(referencia)){

                                    DataTables.getSearch().add(reg);

                                }
                            }
                        }
                    }


                }

            }else {
                //SIN COMPANIA Y SIN REFERENCIA
                if (!textPoliza.getText().isEmpty()){
                    //SIN COMPANIA, SIN REFERENCIA Y CON POLIZA
                    if (!textTelf.getText().isEmpty()){
                        //SIN COMPANIA, SIN REFERENCIA, CON POLIZA Y CON TELEFONO
                        if(!textFactura.getText().isEmpty()) {
                            //POLIZA, TELEFONO Y FACTURA
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                poliza = textPoliza.getText();
                                telefono = Integer.parseInt(textTelf.getText());
                                factura = textFactura.getText();

                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if (reg.getSiniestro().getNumPoliza().contains(poliza)){
                                                if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                                    DataTables.getSearch().add(reg);

                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }else {
                            //POLIZA Y TELEFONO
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf){
                                poliza = textPoliza.getText();
                                telefono = Integer.parseInt(textTelf.getText());


                                for (Registro reg : DataTables.getRegistros()){

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg){
                                        if (t.getTelefono() == telefono){
                                            if (reg.getSiniestro().getNumPoliza().contains(poliza)){

                                                DataTables.getSearch().add(reg);

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else {
                        //SIN TELEFONO Y CON POLIZA
                        if(!textFactura.getText().isEmpty()) {
                            //POLIZA Y FACTURA
                                poliza = textPoliza.getText();
                                factura = textFactura.getText();

                                for (Registro reg : DataTables.getRegistros()){

                                    if (reg.getSiniestro().getNumPoliza().contains(poliza)){
                                        if (reg.getFacturaRegistro().getNumFactura().contains(factura)){

                                            DataTables.getSearch().add(reg);

                                        }
                                    }
                                }

                        }else {
                            //SOLO POLIZA
                            poliza = textPoliza.getText();

                            for (Registro reg : DataTables.getRegistros()){
                                if (reg.getSiniestro().getNumPoliza().contains(poliza)){
                                    DataTables.getSearch().add(reg);
                                }
                            }
                        }
                    }

                }else {
                    //SIN COMPANIA, SIN REFERENCIA Y SIN POLIZA
                    if (!textTelf.getText().isEmpty()){
                        //TENGO TELEFONO
                        if(!textFactura.getText().isEmpty()) {
                            //TELEFONO Y FACTURA
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf) {
                                telefono = Integer.parseInt(textTelf.getText());
                                factura = textFactura.getText();

                                for (Registro reg : DataTables.getRegistros()) {

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg) {
                                        if (t.getTelefono() == telefono) {
                                            if (reg.getFacturaRegistro().getNumFactura().contains(factura)) {

                                                DataTables.getSearch().add(reg);

                                            }
                                        }
                                    }
                                }
                            }

                        }else {
                            //SOLO TELEFONO
                            boolean validTelf = verificarTelefono(textTelf.getText());
                            if (validTelf) {

                                telefono = Integer.parseInt(textTelf.getText());

                                for (Registro reg : DataTables.getRegistros()) {

                                    ArrayList<TelefonoAsegurado> telefonosAseg = new ArrayList<>();
                                    telefonosAseg = reg.getSiniestro().getAsegurado().getTelefonosAseg();
                                    for (TelefonoAsegurado t : telefonosAseg) {
                                        if (t.getTelefono() == telefono) {

                                            DataTables.getSearch().add(reg);

                                        }
                                    }
                                }
                            }
                        }
                    }else {
                        //SOLO FACTURA
                        if(!textFactura.getText().isEmpty()) {
                            //CON FACTURA
                                factura = textFactura.getText();

                                for (Registro reg : DataTables.getRegistros()) {
                                    if (reg.getFacturaRegistro().getNumFactura().contains(factura)) {

                                        DataTables.getSearch().add(reg);

                                    }
                                }

                        }else {
                            emptySearchBox = true;
                        }
                    }
                }
            }


        }
        ArrayList<Object[]> busqueda = new ArrayList<Object[]>();
        for(Registro r : DataTables.getSearch()){
            String nc = "";
            int tt = 0;
            for (Compania com : DataTables.getCompanias()) {
                if (r.getSiniestro().getCompania() == com.getCodCompania()) {
                    nc = com.getNombreCompania();
                }
            }

            ArrayList<TelefonoAsegurado> telefonosAseg = null;
            telefonosAseg = r.getSiniestro().getAsegurado().getTelefonosAseg();
            if (!telefonosAseg.isEmpty()){
                tt = telefonosAseg.get(0).getTelefono();

            }

            Object[] a;
            a = new Object[]{r.getCodRegistro(), nc , r.getReferenciaCom(),r.getSiniestro().getNumPoliza(),
                    r.getEstadoRegistro(), tt, r.getFacturaRegistro().getNumFactura(), r.getTipoRegistro()};
            busqueda.add(a);
        }

        if (busqueda.size() > 0){
            for (Object[] row : busqueda) Config.pControl.pControl.modelBusqueda.addRow(row);
        } else {
            if (!emptySearchBox){
                DataTables.cleanBusqueda();
                Config.warning("No se han encontrado resultados en la Búsqueda.");
            } else Config.error("Introduzca algún dato en los campos de Búsqueda.");
        }


    }

    public void actComboCompania(){
        cbCompania.removeAllItems();
        cbCompania.addItem("Default");
        for (Compania com : DataTables.getCompanias()){
            if (com.getEstadoCompania().equals("ACTIVO")){
                cbCompania.addItem(com.getNombreCompania());
            }
        }
    }

    public void actNumeroRegistros(){
        numRegistros = modelRegistro.getRowCount();
        labNRegistros.setText("Nº Registros: "+numRegistros);
    }

    public static boolean verificarTelefono(String telf){
        boolean isValid = false;
        try{
            int telefono = Integer.parseInt(telf);
            isValid = true;

        }catch (Exception e){
            isValid = false;
            Config.error("El Nº Telefónico en la Búsqueda es erroneo.");
        }
        return isValid;
    }

    public void changeUltimaAccion(String accion){
        ultimaAccion.setText("ÚLTIMA ACCIÓN -- "+accion);
    }
    /*
     * Metodo que posiciona el GridBagConstrain del Layout para introducir el componente en la ventana.
     */
    public static GridBagConstraints newPosicion(int anchor, int fill, int gridx, int gridy){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        return gbc;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Config.COLOR6);
        g.fillRect(Config.anchoPantalla -80, 0, 100, Config.alturaPantalla);

    }
}
