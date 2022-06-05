package interfaces.icontrol.pconfig;

import clases.*;
import com.toedter.calendar.JDateChooser;
import conexion.DataTables;
import interfaces.Config;
import interfaces.icontrol.pconfig.icompania.InterfazEditCompania;
import interfaces.icontrol.pconfig.iramo.InterfazEditRamo;
import interfaces.icontrol.pconfig.itipointervencion.InterfazEditTipoInt;
import interfaces.icontrol.pconfig.itiporeapertura.InterfazEditTipoReap;
import interfaces.icontrol.pconfig.itiposiniestro.InterfazEditTipoSin;
import interfaces.icontrol.pconfig.iuser.InterfazEditUsr;
import interfaces.icontrol.pconfig.iuser.InterfazVerifyUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;

public class PanelConfig extends JPanel {

    private static ConfigCli configuracion;

    private JLabel labTConfig, labConexion, labIpSrv, labIpCli, labPuertoSrv, labTCampos, labTValores, labPeriodoRegistros,
            labFechaDesde, labFechaHasta, imgConfig;
    public JTextField textIpSrv, textIpCli, textPuertoSrv;
    public JButton guardarConexion, addCom, editCom, addRamo, editRamo,
            addTipoSin, editTipoSin, addTipoInt, editTipoInt,
            addTipoR, editTipoR, addUsr, editUsr, unlock;
    public JTable tabCompania, tabRamo, tabTipoSin, tabTipoInt, tabTipoReap,  tabUser;
    public static DefaultTableModel modelCom, modelRamo, modelTipoSin, modelTipoInt, modelTipoReap, modelUsr;
    public JScrollPane scrollCom, scrollRamo, scrollTipoSin, scrollTipoInt, scrollTipoReap, scrollUsr;
    public JDateChooser dateFechaDesde, dateFechaHasta;
    public JCheckBox check;


    public PanelConfig(){
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10,10,10,10));

        initComponents();
        chargeConfigCli();

        //LAYOUT
        GridBagLayout gbl_content = new GridBagLayout();
        gbl_content.columnWidths = new int[]{0};
        gbl_content.rowHeights = new int[]{0};
        gbl_content.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_content.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_content);


        //INICIALIZACION DE COMPONENTES
        ///////// LABELS
        //LABEL TITULO
        GridBagConstraints grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,0);
        grid.insets = new Insets(20,10,10,0);
        grid.gridwidth = 2;
        add(labTConfig, grid);

        //IMAGEN CONFIG
        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE,2,0);
        grid.insets = new Insets(0,0,0,30);
        add(imgConfig, grid);

        //LABEL CONEXION
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,1);
        grid.gridwidth = 2;
        add(labConexion, grid);

        //LABEL IP SERVER
        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,0,2);
        add(labIpSrv, grid);

        //LABEL IP CLIENTE
        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,0,3);
        add(labIpCli, grid);

        //LABEL PUERTO SERVER
        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,0,4);
        add(labPuertoSrv, grid);

        ///////// CAMPOS
        //CAMPO IP SERVER
        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,1,2);
        grid.weightx = 0.01;
        add(textIpSrv, grid);

        //CAMPO IP CLIENTE
        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,1,3);
        add(textIpCli, grid);

        //CAMPO PUERTO SERVER
        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,1,4);
        add(textPuertoSrv, grid);

        //LABEL CONFIGURACION CAMPOS
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,0,5);
        grid.weighty = 0.01;
        grid.gridwidth = 2;
        add(labTCampos, grid);


        ///////// TABLAS
        //TABLA COMPANIAS
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,0,7);
        grid.insets = new Insets(0,0,10,3);
        grid.gridheight = 3;
        grid.weighty = 0.05;
        grid.weightx = 0.05;
        add(scrollCom, grid);

        //TABLA RAMOS
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,2,7);
        grid.insets = new Insets(0,0,10,3);
        grid.gridheight = 3;
        grid.weightx = 0.03;
        grid.weighty = 0.01;
        add(scrollRamo, grid);

        //TABLA TIPO SINIESTRO
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,0,13);
        grid.insets = new Insets(0,0,10,3);
        grid.gridheight = 3;
        grid.weighty = 0.05;
        add(scrollTipoSin, grid);

        //TABLA TIPO INTERVENCION
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,2,13);
        grid.insets = new Insets(0,0,10,3);
        grid.gridheight = 3;
        add(scrollTipoInt, grid);

        //TABLA TIPO REAPERTURA
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,2,17);
        grid.insets = new Insets(0,0,10,3);
        grid.gridheight = 3;
        add(scrollTipoReap, grid);

        //TABLA USUARIOS
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,0,17);
        grid.insets = new Insets(0,0,10,3);
        grid.gridheight = 3;
        grid.weighty = 0.05;
        add(scrollUsr, grid);


        ///////// BOTONES
        //BOTON GUARDAR CONFIG CONEXION
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,3,2);
        grid.insets = new Insets(0,5,0,5);
        grid.gridheight = 2;
        //grid.weightx = 0.05;
        add(guardarConexion, grid);
        //BOTON ADD COMPANIA
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,7);
        grid.insets = new Insets(0,0,0,20);
        add(addCom, grid);

        //BOTON EDIT COMPANIA
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,8);
        grid.insets = new Insets(0,0,0,20);
        add(editCom, grid);

        //BOTON ADD RAMO
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,3,7);
        add(addRamo, grid);

        //BOTON EDIT RAMO
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,3,8);
        add(editRamo, grid);

        //BOTON ADD TIPO SINIESTRO
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,13);
        grid.insets = new Insets(0,0,0,20);
        add(addTipoSin, grid);

        //BOTON EDIT TIPO SINIESTRO
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,14);
        grid.insets = new Insets(0,0,0,20);
        add(editTipoSin, grid);


        //BOTON ADD TIPO INTERVENCION
        grid = Config.newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL,3,13);
        add(addTipoInt, grid);

        //BOTON EDIT TIPO INTERVENCION
        grid = Config.newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL,3,14);
        add(editTipoInt, grid);

        //BOTON ADD TIPO REAPERTURA
        grid = Config.newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL,3,17);
        add(addTipoR, grid);

        //BOTON EDIT TIPO REAPERTURA
        grid = Config.newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL,3,18);
        add(editTipoR, grid);

        //BOTON ADD USUARIOS
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,17);
        grid.insets = new Insets(0,0,0,20);
        add(addUsr, grid);

        //BOTON EDIT USUARIOS
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,18);
        grid.insets = new Insets(0,0,0,20);
        add(editUsr, grid);


        //BOTON UNLOCK INTERFAZ
        grid = Config.newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.BOTH,3,22);
        grid.gridheight = 2;
        add(unlock, grid);

        //LAB PERIODO
        grid = Config.newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL,2,1);
        grid.insets = new Insets(0,5,0,20);
        add(labPeriodoRegistros, grid);

        //LAB DATE DESDE
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,2,2);
        grid.insets = new Insets(0,5,0,20);
        add(labFechaDesde, grid);

        //LAB DATE HASTA
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,2,4);
        grid.insets = new Insets(0,5,0,20);
        add(labFechaHasta, grid);

        //DATE DESDE
        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,2,3);
        grid.insets = new Insets(0,5,0,20);
        add(dateFechaDesde, grid);

        //DATE HASTA
        grid = Config.newPosicion(GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,2,5);
        grid.insets = new Insets(0,5,0,20);
        add(dateFechaHasta, grid);

        //DATE HASTA
        grid = Config.newPosicion(GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,1,5);
        grid.insets = new Insets(0,5,0,20);
        add(check, grid);

    }

    public void initComponents(){
        ///////// LABELS
        //LABEL TITULO CONFIGURACION
        labTConfig = new JLabel("CONFIGURACIÓN");
        labTConfig.setFont(Config.FUENTETITULO);
        Font fontr= labTConfig.getFont();
        Map attributesr = fontr.getAttributes();
        attributesr.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        labTConfig.setFont(fontr.deriveFont(attributesr));

        //LABEL SERVER
        labConexion = new JLabel("CONFIGURACIÓN SERVIDOR:");
        labConexion.setFont(Config.FUENTE14);

        //LABEL CAMPOS
        labTCampos = new JLabel("CONFIGURACIÓN CAMPOS REGISTRO:");
        labTCampos.setFont(Config.FUENTE14);

        //LABEL CONEXION
        labTValores = new JLabel("CONFIGURACIÓN VALORES REGISTRO:");
        labTValores.setFont(Config.FUENTE14);

        //LABEL IP SERVER
        labIpSrv = new JLabel("Ip Servidor:");

        //LABEL IP CLIENTE
        labIpCli = new JLabel("Ip Cliente:");

        //LABEL PUERTO SERVER
        labPuertoSrv = new JLabel("Puerto Servidor:");

        //LABEL PERIODO
        labPeriodoRegistros = new JLabel("Periodo de Registros:");

        //LABEL FECHA DESDE
        labFechaDesde = new JLabel("Desde: ");

        //LABEL FECHA HASTA
        labFechaHasta = new JLabel("Hasta: ");

        //IMAGEN CONFIG
        imgConfig = new JLabel();
        imgConfig.setIcon( new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\conf.png"));


        ///////// CAMPOS
        //CAMPO IP SERVER
        textIpSrv = new JTextField();

        //CAMPO IP CLIENTE
        textIpCli = new JTextField(Config.getIpClient());

        //CAMPO PUERTO SERVER
        textPuertoSrv = new JTextField();

        ///////// DATE CHOOSER
        dateFechaDesde = new JDateChooser();
        dateFechaHasta = new JDateChooser();

        ///////// CHECK
        check = new JCheckBox("Ampliar Campos");
        check.setBackground(Config.COLOR2);


        ///////// TABLAS
        //TABLA COMPANIA
        modelCom = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        modelCom.addColumn("Compañia");
        modelCom.addColumn("Estado");
        tabCompania = new JTable();
        tabCompania.setModel(modelCom);
        tabCompania.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollCom = new JScrollPane(tabCompania);
        ListSelectionModel rowSM = tabCompania.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if (!lsm.isSelectionEmpty()) {
                    int filasRamo = modelRamo.getRowCount();
                    for (int i = 0; i < filasRamo; i++){
                        modelRamo.removeRow(0);
                    }
                    String comp = (String) modelCom.getValueAt(tabCompania.getSelectedRow(), 0);
                    for (Compania c : DataTables.getCompanias()){
                        if (c.getNombreCompania().equalsIgnoreCase(comp)){
                            for (RamoCompania ramo : c.getRamoCompanias()){
                                Object [] ram = {ramo.getDescripcionRamo(), ramo.getEstadoRam()};
                                modelRamo.addRow(ram);
                            }
                            break;
                        }
                    }

                }
            }
        });

        //TABLA RAMO
        modelRamo = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        modelRamo.addColumn("Ramo");
        modelRamo.addColumn("Estado");
        tabRamo = new JTable();
        tabRamo.setModel(modelRamo);
        tabRamo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollRamo = new JScrollPane(tabRamo);

        //TABLA TIPO SINIESTRO
        modelTipoSin = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        modelTipoSin.addColumn("Tipo Siniestro");
        modelTipoSin.addColumn("Estado");
        tabTipoSin = new JTable();
        tabTipoSin.setModel(modelTipoSin);
        tabTipoSin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollTipoSin = new JScrollPane(tabTipoSin);

        //TABLA TIPO INTERVECION
        modelTipoInt = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        modelTipoInt.addColumn("Intervención");
        modelTipoInt.addColumn("Estado");
        tabTipoInt = new JTable();
        tabTipoInt.setModel(modelTipoInt);
        tabTipoInt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollTipoInt = new JScrollPane(tabTipoInt);

        //TABLA TIPO REAPERTURA
        modelTipoReap = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        modelTipoReap.addColumn("Reapertura");
        modelTipoReap.addColumn("Estado");
        tabTipoReap = new JTable();
        tabTipoReap.setModel(modelTipoReap);
        tabTipoReap.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollTipoReap = new JScrollPane(tabTipoReap);

        //TABLA USUARIO
        modelUsr = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        modelUsr.addColumn("Usuario");
        modelUsr.addColumn("Estado");
        tabUser = new JTable();
        tabUser.setModel(modelUsr);
        tabUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollUsr = new JScrollPane(tabUser);


        ///////// BOTONES
        //BOTON GUARDAR CONFIGURACION
        guardarConexion = new JButton("Guardar Config");
        guardarConexion.setBackground(Config.COLOR5);
        guardarConexion.setForeground(Color.WHITE);
        guardarConexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipCli, ipSrv, port;
                ipCli = textIpCli.getText();
                ipSrv = textIpSrv.getText();
                port = textPuertoSrv.getText();
                ConfigCli conf = new ConfigCli(ipCli, ipSrv, port);
                conf.setFechaDesde(dateFechaDesde.getDate());
                conf.setFechaHasta(dateFechaHasta.getDate());
                if (check.isSelected()) conf.setCheckAmpliacion(true);
                else conf.setCheckAmpliacion(false);
                boolean isValid = ConfigCli.setConfig(conf);
                if(isValid){
                    Config.configuracion = ConfigCli.getConfig();
                    Config.info("Se ha modificado el archivo de conexión.\n" +
                            "Recuerde pulsar el botón actualizar en el Panel de Control.");
                }
                else Config.info("La configuración no ha cambiado.");

            }
        });

        //BOTON ADD COMPANIA
        addCom = new JButton("Añadir Compañia");
        addCom.setBackground(Config.COLOR4);
        addCom.setForeground(Color.WHITE);
        addCom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Compania c = new Compania();
                if (Config.pEditCompania == null){
                    Config.pEditCompania = new InterfazEditCompania(1, c);
                }else Config.warning("Existe una ventana Edición de Compañia Abierta.");

            }
        });
        //BOTON EDIT COMPANIA
        editCom = new JButton("Editar Compañia");
        editCom.setBackground(Config.COLOR4);
        editCom.setForeground(Color.WHITE);
        editCom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String comp = (String) modelCom.getValueAt(tabCompania.getSelectedRow(), 0);
                    Compania c = new Compania();
                    for (Compania com : DataTables.getCompanias()){
                        if (com.getNombreCompania().equalsIgnoreCase(comp)){
                            c.setCodCompania(com.getCodCompania());
                            c.setNombreCompania(com.getNombreCompania());
                            c.setEstadoCompania(com.getEstadoCompania());
                            break;
                        }
                    }
                    if (Config.pEditCompania == null){
                        Config.pEditCompania = new InterfazEditCompania(2, c);
                    }else Config.warning("Existe una ventana Edición de Compañia Abierta.");

                }catch (Exception ex){
                    Config.error("Seleccione una compañia de la tabla.");
                }

            }
        });

        //BOTON ADD RAMO
        addRamo = new JButton("Añadir Ramo");
        addRamo.setBackground(Config.COLOR4);
        addRamo.setForeground(Color.WHITE);
        addRamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String comp = (String) modelCom.getValueAt(tabCompania.getSelectedRow(), 0);
                    RamoCompania ramo = new RamoCompania();
                    for (Compania com : DataTables.getCompanias()){
                        if (com.getNombreCompania().equalsIgnoreCase(comp)){
                            ramo.setCompania(com.getCodCompania());
                            break;
                        }
                    }

                    if (Config.pEditRamo == null){
                        Config.pEditRamo = new InterfazEditRamo(3, ramo);
                    }else Config.warning("Existe una ventana Edición de Ramo Abierta.");


                }catch (Exception ex){
                    Config.error("Seleccione una Compañia de la tabla Compañias.");
                }

            }
        });

        //BOTON EDIT RAMO
        editRamo = new JButton("Editar Ramo");
        editRamo.setBackground(Config.COLOR4);
        editRamo.setForeground(Color.WHITE);
        editRamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String comp = (String) modelCom.getValueAt(tabCompania.getSelectedRow(), 0);
                    String ram = (String) modelRamo.getValueAt(tabRamo.getSelectedRow(), 0);
                    RamoCompania ramo = new RamoCompania();
                    for (Compania com : DataTables.getCompanias()){
                        if (com.getNombreCompania().equalsIgnoreCase(comp)){
                            for (RamoCompania r : com.getRamoCompanias()){
                                if (r.getDescripcionRamo().equalsIgnoreCase(ram)){
                                    ramo.setCodRamo(r.getCodRamo());
                                    ramo.setDescripcionRamo(r.getDescripcionRamo());
                                    ramo.setCompania(r.getCompania());
                                    ramo.setEstadoRam(r.getEstadoRam());
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    if (Config.pEditRamo == null){
                        Config.pEditRamo = new InterfazEditRamo(4, ramo);
                    }else Config.warning("Existe una ventana Edición de Ramo Abierta.");

                }catch (Exception ex){
                    Config.error("Seleccione un Ramo de la tabla.");
                }

            }
        });

        //BOTON ADD TIPO SINIESTRO
        addTipoSin = new JButton("Añadir Tipo Siniestro");
        addTipoSin.setBackground(Config.COLOR4);
        addTipoSin.setForeground(Color.WHITE);
        addTipoSin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoSiniestro tipoSin = new TipoSiniestro();
                if (Config.pEditTSin == null) Config.pEditTSin = new InterfazEditTipoSin(5, tipoSin);
                else Config.warning("Existe una ventana Edición Tipo Siniestro Abierta.");

            }
        });

        //BOTON EDIT TIPO SINIESTRO
        editTipoSin = new JButton("Editar Tipo Siniestro");
        editTipoSin.setBackground(Config.COLOR4);
        editTipoSin.setForeground(Color.WHITE);
        editTipoSin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String descTipoSin = (String) modelTipoSin.getValueAt(tabTipoSin.getSelectedRow(), 0);
                    TipoSiniestro tipoSin = new TipoSiniestro();
                    for (TipoSiniestro ts : DataTables.getTipoSiniestros()){
                        if (ts.getDescripTipoSiniestro().equals(descTipoSin)){
                            tipoSin.setCodTipoSiniestro(ts.getCodTipoSiniestro());
                            tipoSin.setDescripTipoSiniestro(ts.getDescripTipoSiniestro());
                            tipoSin.setEstadoTipoSin(ts.getEstadoTipoSin());
                            break;
                        }
                    }
                    if (Config.pEditTSin == null) Config.pEditTSin = new InterfazEditTipoSin(6, tipoSin);
                    else Config.warning("Existe una ventana Edición Tipo Siniestro Abierta.");

                }catch (Exception ex){
                    Config.error("Seleccione un Tipo de Siniestro de la tabla.");
                }

            }
        });


        //BOTON ADD TIPO INTERVENCION
        addTipoInt = new JButton("Añadir Intervención");
        addTipoInt.setBackground(Config.COLOR4);
        addTipoInt.setForeground(Color.WHITE);
        addTipoInt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoIntervencion tipoInt = new TipoIntervencion();
                if (Config.pEditTInt == null) Config.pEditTInt = new InterfazEditTipoInt(7, tipoInt);
                else Config.warning("Existe una ventana Edición Tipo Intervención Abierta.");

            }
        });

        //BOTON EDIT TIPO INTERVENCION
        editTipoInt = new JButton("Editar Intervención");
        editTipoInt.setBackground(Config.COLOR4);
        editTipoInt.setForeground(Color.WHITE);
        editTipoInt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String descTipoInt = (String) modelTipoInt.getValueAt(tabTipoInt.getSelectedRow(), 0);
                    TipoIntervencion tipoInt = new TipoIntervencion();
                    for (TipoIntervencion ti : DataTables.getTipoInt()){
                        if (ti.getTipoIntervencion().equals(descTipoInt)){
                            tipoInt.setCodTipoInt(ti.getCodTipoInt());
                            tipoInt.setTipoIntervencion(ti.getTipoIntervencion());
                            tipoInt.setEstadoIntervencion(ti.getEstadoIntervencion());
                            break;
                        }
                    }
                    if (Config.pEditTInt == null) Config.pEditTInt = new InterfazEditTipoInt(8, tipoInt);
                    else Config.warning("Existe una ventana Edición Tipo Intervención Abierta.");

                }catch (Exception ex){
                    Config.error("Seleccione un Tipo de Intervención de la tabla.");
                }

            }
        });

        //BOTON ADD TIPO REAPERTURA
        addTipoR = new JButton("Añadir Reapertura");
        addTipoR.setBackground(Config.COLOR4);
        addTipoR.setForeground(Color.WHITE);
        addTipoR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoReapertura tipoReap = new TipoReapertura();
                if (Config.pEditTReap == null) Config.pEditTReap = new InterfazEditTipoReap(9, tipoReap);
                else Config.warning("Existe una ventana Edición Tipo Reapertura Abierta.");
            }
        });

        //BOTON EDIT TIPO REAPERTURA
        editTipoR = new JButton("Editar Reapertura");
        editTipoR.setBackground(Config.COLOR4);
        editTipoR.setForeground(Color.WHITE);
        editTipoR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String descTipoReap = (String) modelTipoReap.getValueAt(tabTipoReap.getSelectedRow(), 0);
                    TipoReapertura tipoReap = new TipoReapertura();
                    for (TipoReapertura tr : DataTables.getTipoReaperturas()){
                        if (tr.getTipoReapertura().equals(descTipoReap)){
                            tipoReap.setCodTipoReapertura(tr.getCodTipoReapertura());
                            tipoReap.setTipoReapertura(tr.getTipoReapertura());
                            tipoReap.setEstadoTipoReapertura(tr.getEstadoTipoReapertura());
                            break;
                        }
                    }
                    if (Config.pEditTReap == null) Config.pEditTReap = new InterfazEditTipoReap(10, tipoReap);
                    else Config.warning("Existe una ventana Edición Tipo Reapertura Abierta.");

                }catch (Exception ex){
                    Config.error("Seleccione un Tipo de Reapertura de la tabla.");
                }

            }
        });

        //BOTON ADD USUARIOS
        addUsr = new JButton("Añadir Usuario");
        addUsr.setBackground(Config.COLOR4);
        addUsr.setForeground(Color.WHITE);
        addUsr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usr = new Usuario();
                if (Config.pEditUsr == null) Config.pEditUsr = new InterfazEditUsr(11, usr);
                else Config.warning("Existe una ventana Edición de Usuario Abierta.");
            }
        });

        //BOTON EDIT USUARIOS
        editUsr = new JButton("Editar Usuario");
        editUsr.setBackground(Config.COLOR4);
        editUsr.setForeground(Color.WHITE);
        editUsr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombreUsr = (String) modelUsr.getValueAt(tabUser.getSelectedRow(), 0);
                    Usuario usr = new Usuario();
                    for (Usuario u : DataTables.getUsuarios()){
                        if (u.getNombreUsr().equals(nombreUsr)){
                            usr.setCodUsuario(u.getCodUsuario());
                            usr.setNombreUsr(u.getNombreUsr());
                            usr.setPassUsr(u.getPassUsr());
                            usr.setEstadoUsr(u.getEstadoUsr());
                            usr.setTipoUsr(u.getTipoUsr());
                            break;
                        }
                    }
                    if (Config.pEditUsr == null) Config.pEditUsr = new InterfazEditUsr(12, usr);
                    else Config.warning("Existe una ventana Edición de Usuario Abierta.");

                }catch (Exception ex){
                    Config.error("Seleccione un Usuario de la tabla.");
                }

            }
        });

        //BOTON UNLOCK INTERFAZ
        unlock = new JButton("Desbloquear");
        unlock.setBackground(Config.AUX2);
        unlock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Config.getUser().getEstadoUsr().equals("ACTIVO")){
                    if(Config.getUser().getTipoUsr().equals("ADMIN")){
                        chargeTablesConfig();
                        estadoBotonesConfig(true);
                    }else {
                        if (Config.pVerifyUsr == null){
                            Config.pVerifyUsr = new InterfazVerifyUser();
                        } else Config.warning("Existe una ventana de Verificación Abierta.");
                    }
                }
                //desbloquear botones
                //variable isuseradmin
            }
        });


        estadoBotonesConfig(false);
        check.setSelected(Config.configuracion.isCheckAmpliacion());
    }

    public void estadoBotonesConfig(boolean estado){
        addCom.setEnabled(estado);
        editCom.setEnabled(estado);
        addRamo.setEnabled(estado);
        editRamo.setEnabled(estado);
        addTipoSin.setEnabled(estado);
        editTipoSin.setEnabled(estado);
        addTipoInt.setEnabled(estado);
        editTipoInt.setEnabled(estado);
        addTipoR.setEnabled(estado);
        editTipoR.setEnabled(estado);
        addUsr.setEnabled(estado);
        editUsr.setEnabled(estado);

    }

    public void chargeTablesConfig(){
        int filasCom = modelCom.getRowCount();
        for (int i = 0; i < filasCom; i++){
            modelCom.removeRow(0);
        }
        int filasRamo = modelRamo.getRowCount();
        for (int i = 0; i < filasRamo; i++){
            modelRamo.removeRow(0);
        }
        int filasTipoSin = modelTipoSin.getRowCount();
        for (int i = 0; i < filasTipoSin; i++){
            modelTipoSin.removeRow(0);
        }
        int filasTipoInt = modelTipoInt.getRowCount();
        for (int i = 0; i < filasTipoInt; i++){
            modelTipoInt.removeRow(0);
        }
        int filasTipoReap = modelTipoReap.getRowCount();
        for (int i = 0; i < filasTipoReap; i++){
            modelTipoReap.removeRow(0);
        }
        int filasUser = modelUsr.getRowCount();
        for (int i = 0; i < filasUser; i++){
            modelUsr.removeRow(0);
        }
        for (Compania compania : DataTables.getCompanias()){
            Object [] comp = {compania.getNombreCompania(), compania.getEstadoCompania()};
            modelCom.addRow(comp);
        }
        for (TipoSiniestro tipoSiniestro : DataTables.getTipoSiniestros()){
            Object [] tipoS = {tipoSiniestro.getDescripTipoSiniestro(), tipoSiniestro.getEstadoTipoSin()};
            modelTipoSin.addRow(tipoS);
        }
        for (Usuario usuario : DataTables.getUsuarios()){
            Object [] usr = {usuario.getNombreUsr(), usuario.getEstadoUsr()};
            modelUsr.addRow(usr);
        }
        for (TipoIntervencion ti : DataTables.getTipoInt()){
            Object [] tipoI = {ti.getTipoIntervencion(), ti.getEstadoIntervencion()};
            modelTipoInt.addRow(tipoI);
        }
        for (TipoReapertura tr : DataTables.getTipoReaperturas()){
            Object [] tipoR = {tr.getTipoReapertura(), tr.getEstadoTipoReapertura()};
            modelTipoReap.addRow(tipoR);
        }
        modelCom.fireTableDataChanged();
        modelRamo.fireTableDataChanged();
        modelTipoSin.fireTableDataChanged();
        modelUsr.fireTableDataChanged();
        modelTipoInt.fireTableDataChanged();
        modelTipoReap.fireTableDataChanged();


    }

    public void chargeConfigCli(){
        ConfigCli conf = Config.configuracion;
        textIpSrv.setText(conf.getIpServer());
        textPuertoSrv.setText(conf.getPortServer());
        dateFechaDesde.setDate(conf.getFechaDesde());
        dateFechaHasta.setDate(conf.getFechaHasta());
    }

    /*
     * Metodo que pinta el panel del color indicado.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Config.COLOR6);
        g.fillRect(Config.anchoPantalla -200, 0, 300, Config.alturaPantalla);

    }
}
