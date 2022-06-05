package interfaces.icontrol.pcontrol;

import clases.Compania;
import clases.Perito;
import clases.Registro;
import conexion.DataTables;
import interfaces.Config;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InterfazFiltroRegistro extends JFrame {

    private static ArrayList<Registro> temp = new ArrayList<Registro>();

    public InterfazFiltroRegistro(){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PeritControl - Filtro Registro");
        setBounds((Config.anchoPantalla/4), (Config.alturaPantalla/4),400,400); setResizable(true);

        //LAYOUT
        setLayout(new BorderLayout());

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                Config.filtro = null;
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
        });

        PanelFiltro contenedor = new PanelFiltro();
        add(contenedor, BorderLayout.CENTER);
        setResizable(false);
        setVisible(true);
    }

    public static ArrayList<Registro> getTemp() {
        return temp;
    }

    public static void setTemp(ArrayList<Registro> temp) {
        InterfazFiltroRegistro.temp = temp;
    }
}

class PanelFiltro extends JPanel{

    public JLabel labTFiltrar, labCompania, labPerito, labPago, labFechaDesde, labFechaHasta,
                labEstadoReg, labTipoFecha, imgFiltro;
    public static JSpinner spinDesde, spinHasta;
    public SpinnerDateModel modelFDesde, modelFHasta;
    public static JComboBox boxCompania, boxPerito, boxEstado, boxPago;
    public static JRadioButton rbFechaApertura, rbFechaCierre;
    public static ButtonGroup rbGroup;
    public static JCheckBox checkFecha;
    public JButton filtrar;

    public PanelFiltro(){
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10,10,10,10));

        //LAYOUT
        GridBagLayout gbl_content = new GridBagLayout();
        gbl_content.columnWidths = new int[]{0};
        gbl_content.rowHeights = new int[]{0};
        gbl_content.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_content.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_content);

        initComponentes();

        //LABEL REGISTROS ACTIVOS
        GridBagConstraints grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.NONE,0,0);
        grid.insets = new Insets(10,10,0,0);
        grid.gridwidth = 2;
        add(labTFiltrar, grid);

        //IMAGEN FILTRO
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE,2,0);
        grid.insets = new Insets(0,0,40,0);
        add(imgFiltro, grid);

        //LABEL COMPANIA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,0,1);
        grid.insets = new Insets(0,0,10,0);
        add(labCompania, grid);

        //COMBO BOX COPANIA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,1);
        grid.insets = new Insets(0,15,10,0);
        grid.gridwidth = 2;
        add(boxCompania, grid);

        //LABEL PERITO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,0,2);
        grid.insets = new Insets(0,0,10,0);
        add(labPerito, grid);

        //COMBO BOX PERITO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,2);
        grid.insets = new Insets(0,15,10,0);
        grid.gridwidth = 2;
        add(boxPerito, grid);

        //LABEL ESTADO PAGO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,0,3);
        grid.insets = new Insets(0,0,10,0);
        add(labPago, grid);

        //COMBO ESTADO PAGO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,3);
        grid.insets = new Insets(0,15,10,0);
        grid.weightx = 0.09;
        add(boxPago, grid);

        //LABEL ESTADO REGISTRO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,0,4);
        add(labEstadoReg, grid);

        //COMBO ESTADO REGISTRO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,4);
        grid.insets = new Insets(0,15,10,0);
        add(boxEstado, grid);

        //LABEL TIPO FECHA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,0,5);
        grid.insets = new Insets(0,0,10,0);
        add(labTipoFecha, grid);

        //CHECK PERIODO FECHA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,5);
        grid.insets = new Insets(0,0,10,0);
        add(checkFecha, grid);

        //RB FECHA APERTURA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,0,6);
        grid.insets = new Insets(0,40,10,0);
        grid.gridwidth = 2;
        add(rbFechaApertura, grid);

        //RB FECHA CIERRE
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,0,7);
        grid.insets = new Insets(0,40,10,0);
        grid.gridwidth = 2;
        add(rbFechaCierre, grid);

        //LABEL FECHA DESDE
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE,1,6);
        grid.insets = new Insets(0,0,10,0);
        add(labFechaDesde, grid);

        //CAMPO FECHA DESDE
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.BOTH,2,6);
        grid.insets = new Insets(0,15,10,0);
        add(spinDesde, grid);

        //LABEL FECHA HASTA
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE,1,7);
        grid.insets = new Insets(0,0,10,0);
        add(labFechaHasta, grid);

        //CAMPO FECHA HASTA
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.BOTH,2,7);
        grid.insets = new Insets(0,15,10,0);
        add(spinHasta, grid);

        //BOTON FILTRAR
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,2,8);
        grid.insets = new Insets(0,15,0,0);
        add(filtrar, grid);
    }

    public void initComponentes(){

        //LABEL COMPANIA
        labTFiltrar = new JLabel("FILTRAR REGISTROS");
        labTFiltrar.setForeground(Color.WHITE);
        labTFiltrar.setFont(Config.FUENTETITULO);
        labCompania = new JLabel("Compañia:");
        labPerito = new JLabel("Perito:");
        labEstadoReg = new JLabel("Estado Registro:");
        labTipoFecha = new JLabel("Período Fechas:");
        labPago = new JLabel("Estado de Pago:");
        labFechaDesde= new JLabel("Desde:");
        labFechaHasta = new JLabel("Hasta:");
        imgFiltro = new JLabel();
        imgFiltro.setIcon( new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\filtro.png"));

        modelFDesde = new SpinnerDateModel();
        modelFHasta = new SpinnerDateModel();
        modelFDesde.setCalendarField(Calendar.DATE);
        modelFHasta.setCalendarField(Calendar.DATE);
        spinDesde = new JSpinner(modelFDesde);
        spinDesde.setEditor(new JSpinner.DateEditor(spinDesde, "dd/MM/yyyy"));
        spinDesde.setEnabled(false);
        spinHasta = new JSpinner(modelFHasta);
        spinHasta.setEditor(new JSpinner.DateEditor(spinHasta, "dd/MM/yyyy"));
        spinHasta.setEnabled(false);


        //COMBO BOX COMPANIA
        boxCompania = new JComboBox();
        boxCompania.setBackground(Color.WHITE);
        boxCompania.addItem("Default");
        for (Compania com : DataTables.getCompanias()){
           if (com.getEstadoCompania().equals("ACTIVO")) boxCompania.addItem(com.getNombreCompania());
        }

        //COMBO BOX PERITO
        boxPerito = new JComboBox();
        boxPerito.setBackground(Color.WHITE);
        boxPerito.addItem("Default");
        for (Perito per : DataTables.getPeritos()){
            if (per.getEstadoPerito().equals("ACTIVO")) boxPerito.addItem(per.getNombrePerito());
        }

        //COMBO BOX ESTADO PAGO
        String [] cbPago = {"Default","PAGO", "IMPAGO"};
        boxPago = new JComboBox(cbPago);
        boxPago.setBackground(Color.WHITE);

        //COMBO BOX ESTADO REGISTRO
        String [] cbEstado = {"Default", "ABIERTO", "CERRADO"};
        boxEstado = new JComboBox(cbEstado);
        boxEstado.setBackground(Color.WHITE);
        boxEstado.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(boxEstado.getSelectedItem().equals("ABIERTO")) rbFechaApertura.setSelected(true);
                else if(boxEstado.getSelectedItem().equals("CERRADO")) rbFechaCierre.setSelected(true);
            }
        });

        //CHECK PERIODO FECHA
        checkFecha = new JCheckBox();
        checkFecha.setForeground(Color.WHITE);
        checkFecha.setBackground(Config.COLOR2);
        checkFecha.setSelected(false);
        checkFecha.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!checkFecha.isSelected()){
                    rbFechaApertura.setEnabled(false);
                    rbFechaCierre.setEnabled(false);
                    spinDesde.setEnabled(false);
                    spinHasta.setEnabled(false);
                }else {
                    rbFechaApertura.setEnabled(true);
                    rbFechaCierre.setEnabled(true);
                    spinDesde.setEnabled(true);
                    spinHasta.setEnabled(true);

                }
            }
        });

        //RADIO BUTTONS
        rbFechaApertura = new JRadioButton("Fecha Apertura ");
        rbFechaApertura.setEnabled(false);
        rbFechaApertura.setBackground(Config.COLOR2);
        rbFechaApertura.setSelected(true);
        rbFechaCierre = new JRadioButton("Fecha Cierre ");
        rbFechaCierre.setEnabled(false);
        rbFechaCierre.setBackground(Config.COLOR2);
        rbFechaCierre.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (rbFechaCierre.isSelected()){
                    boxEstado.setSelectedItem("CERRADO");
                }
            }
        });
        rbGroup = new ButtonGroup();
        rbGroup.add(rbFechaApertura);
        rbGroup.add(rbFechaCierre);


        //BOTON FILTRAR
        filtrar = new JButton("Filtrar");
        filtrar.setBackground(Config.COLOR4);
        filtrar.setForeground(Color.WHITE);
        filtrar.setPreferredSize( new Dimension(100,23));
        filtrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataTables.cleanRegistros();
                chargeFiltro();
                Config.filtro.dispose();
                Config.filtro = null;
                Config.pControl.pControl.isFilter = true;
                Config.pControl.pControl.changeUltimaAccion("FILTRO DE REGISTROS.");
                Config.pControl.pControl.actualizar.setBackground(Config.AUX3);
                DataTables.setRegActivos(new ArrayList<>());
            }

        });
    }

    public static void chargeFiltro(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatS = new SimpleDateFormat("dd/MM/yyyy");
        int codCompania = 0;
        int codPerito = 0;
        Date desde = (Date) spinDesde.getValue();
        String fechaDesde = format.format(desde);
        Date hasta = (Date) spinHasta.getValue();
        String fechaHasta = format.format(hasta);
        String compania = (String) boxCompania.getSelectedItem();
        String perito = (String) boxPerito.getSelectedItem();
        String estadoReg = (String) boxEstado.getSelectedItem();
        String estadoPago = (String) boxPago.getSelectedItem();
        boolean noSelection = false;
        for (Perito per : DataTables.getPeritos()) {
            if(per.getNombrePerito().equals(perito)) {
                codPerito = per.getCodPerito();
            }
        }
        if (estadoReg.equals("Default")){
            if (!checkFecha.isSelected()) {
                // SIN ESTADO REGISTRO Y SIN FECHAS
                if (!compania.equals("Default")) {
                    //COMPANIA
                    for (Compania com : DataTables.getCompanias()) {
                        if (com.getNombreCompania().equals(compania)) {
                            codCompania = com.getCodCompania();
                            break;
                        }
                    }
                    if (!perito.equals("Default")) {
                        //COMPANIA Y PERITO
                        System.out.println(codPerito);
                        if (!estadoPago.equals("Default")) {
                            //COMPANIA, PERITO Y ESTADO PAGO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getSiniestro().getCompania() == codCompania) {
                                    if (reg.getPerito() == codPerito) {
                                        if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)) {
                                            DataTables.getRegActivos().add(reg);
                                        }
                                    }
                                }
                            }
                        } else {
                            //COMPANIA Y PERITO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getSiniestro().getCompania() == codCompania) {
                                    if (reg.getPerito() == codPerito) {
                                        DataTables.getRegActivos().add(reg);
                                    }
                                }
                            }
                        }
                    } else {
                        //SIN PERITO
                        if (!estadoPago.equals("Default")) {
                            //COMPANIA Y ESTADO PAGO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getSiniestro().getCompania() == codCompania) {
                                    if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)) {
                                        DataTables.getRegActivos().add(reg);
                                    }
                                }
                            }
                        } else {
                            //SOLO COMPANIA
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getSiniestro().getCompania() == codCompania)
                                    DataTables.getRegActivos().add(reg);
                            }
                        }
                    }
                } else {
                    //SIN COMPANIA Y SIN ESTADO REGISTRO
                    if (!perito.equals("Default")) {
                        //PERITO
                        if (!estadoPago.equals("Default")) {
                            //PERITO Y ESTADO PAGO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getPerito() == codPerito) {
                                    if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)) {
                                        DataTables.getRegActivos().add(reg);
                                    }
                                }
                            }
                        } else {
                            //SOLO PERITO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getPerito() == codPerito) DataTables.getRegActivos().add(reg);
                            }
                        }
                    } else {
                        //SOLO ESTADO PAGO
                        if (!estadoPago.equals("Default")) {
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago))
                                    DataTables.getRegActivos().add(reg);
                            }
                        } else noSelection = true;
                    }
                }
            } else {
                //CON FECHAS
                // SIN ESTADO REGISTRO
                if (!compania.equals("Default")) {
                    //COMPANIA
                    for (Compania com : DataTables.getCompanias()) {
                        if (com.getNombreCompania().equals(compania)) {
                            codCompania = com.getCodCompania();
                            break;
                        }
                    }
                    if (!perito.equals("Default")) {
                        //COMPANIA Y PERITO
                        if (!estadoPago.equals("Default")) {
                            //COMPANIA, PERITO Y ESTADO PAGO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getSiniestro().getCompania() == codCompania) {
                                    if (reg.getPerito() == codPerito) {
                                        if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)) {
                                            if (rbFechaApertura.isSelected()){
                                                if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                                    if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                        DataTables.getRegActivos().add(reg);
                                                    }
                                                }
                                            } else if (rbFechaCierre.isSelected()){
                                                if (reg.getFechaCierre() != null) {
                                                    if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                        if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                            DataTables.getRegActivos().add(reg);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            //COMPANIA Y PERITO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getSiniestro().getCompania() == codCompania) {
                                    if (reg.getPerito() == codPerito) {
                                        if (rbFechaApertura.isSelected()){
                                            if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                                if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                    DataTables.getRegActivos().add(reg);
                                                }
                                            }
                                        } else if (rbFechaCierre.isSelected()){
                                            if (reg.getFechaCierre() != null) {
                                                if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                    if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                        DataTables.getRegActivos().add(reg);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //SIN PERITO
                        if (!estadoPago.equals("Default")) {
                            //COMPANIA Y ESTADO PAGO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getSiniestro().getCompania() == codCompania) {
                                    if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)) {
                                        if (rbFechaApertura.isSelected()){
                                            if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                                if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                    DataTables.getRegActivos().add(reg);
                                                }
                                            }
                                        } else if (rbFechaCierre.isSelected()){
                                            if (reg.getFechaCierre() != null) {
                                                if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                    if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                        DataTables.getRegActivos().add(reg);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            //SOLO COMPANIA
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getSiniestro().getCompania() == codCompania)
                                    if (rbFechaApertura.isSelected()){
                                        if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                            if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                DataTables.getRegActivos().add(reg);
                                            }
                                        }
                                    } else if (rbFechaCierre.isSelected()){
                                        if (reg.getFechaCierre() != null) {
                                            if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                    DataTables.getRegActivos().add(reg);
                                                }
                                            }
                                        }
                                    }
                            }
                        }
                    }
                } else {
                    //SIN COMPANIA Y SIN ESTADO REGISTRO
                    if (!perito.equals("Default")) {
                        //PERITO
                        if (!estadoPago.equals("Default")) {
                            //PERITO Y ESTADO PAGO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getPerito() == codPerito) {
                                    if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)) {
                                        if (rbFechaApertura.isSelected()){
                                            if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                                if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                    DataTables.getRegActivos().add(reg);
                                                }
                                            }
                                        } else if (rbFechaCierre.isSelected()){
                                            if (reg.getFechaCierre() != null) {
                                                if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                    if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                        DataTables.getRegActivos().add(reg);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            //SOLO PERITO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getPerito() == codPerito){
                                    if (rbFechaApertura.isSelected()){
                                        if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                            if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                DataTables.getRegActivos().add(reg);
                                            }
                                        }
                                    } else if (rbFechaCierre.isSelected()){
                                        if (reg.getFechaCierre() != null) {
                                            if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                    DataTables.getRegActivos().add(reg);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //SOLO ESTADO PAGO
                        if (!estadoPago.equals("Default")) {
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago))
                                    if (rbFechaApertura.isSelected()){
                                        if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                            if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                DataTables.getRegActivos().add(reg);
                                            }
                                        }
                                    } else if (rbFechaCierre.isSelected()){
                                        if (reg.getFechaCierre() != null) {
                                            if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                    DataTables.getRegActivos().add(reg);
                                                }
                                            }
                                        }
                                    }
                            }
                        } else {
                            //SOLO FECHAS
                                System.out.println("AQUI");
                            if (desde.equals(hasta)){
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(new Date());
                                calendar.add(Calendar.DAY_OF_MONTH, -1);
                                Date fechad = calendar.getTime();
                                calendar.add(Calendar.DAY_OF_MONTH, 2);
                                Date fechah = calendar.getTime();
                                System.out.println(fechad);
                                for (Registro reg : DataTables.getRegistros()) {
                                    if (rbFechaApertura.isSelected()) {
                                        if (reg.getFechaApertura().after(fechad) && reg.getFechaApertura().before(fechah)) {
                                            DataTables.getRegActivos().add(reg);
                                        }
                                    }else if (rbFechaCierre.isSelected()) {
                                        if (reg.getFechaCierre().after(fechad) && reg.getFechaCierre().before(fechah)){
                                            DataTables.getRegActivos().add(reg);
                                        }
                                    }
                                }
                            }else {
                                for (Registro reg : DataTables.getRegistros()) {
                                    if (rbFechaApertura.isSelected()) {
                                        if (reg.getFechaApertura().after(desde) | reg.getFechaApertura().equals(desde) | reg.getFechaApertura().equals(new Date())) {
                                            if (reg.getFechaApertura().before(hasta) | reg.getFechaApertura().equals(hasta) | reg.getFechaApertura().equals(new Date())) {
                                                DataTables.getRegActivos().add(reg);
                                            }
                                        }
                                    } else if (rbFechaCierre.isSelected()) {
                                        if (reg.getFechaCierre() != null) {
                                            if (reg.getFechaCierre().after(desde) | reg.getFechaCierre().equals(desde) | reg.getFechaApertura().equals(new Date())) {
                                                if (reg.getFechaCierre().before(hasta) | reg.getFechaCierre().equals(hasta) | reg.getFechaApertura().equals(new Date())) {
                                                    DataTables.getRegActivos().add(reg);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            //ESTADO REGISTRO ABIERTO O CERRADO
            if (!checkFecha.isSelected()){
                // SIN FECHAS
                if(!compania.equals("Default")){
                    //COMPANIA
                    for(Compania com : DataTables.getCompanias()){
                        if (com.getNombreCompania().equals(compania)){
                            codCompania = com.getCodCompania();
                            break;
                        }
                    }
                    if (!perito.equals("Default")){
                        //COMPANIA, PERITO Y ESTADO REGISTRO
                        if (!estadoPago.equals("Default")){
                            //COMPANIA, PERITO, ESTADO PAGO Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getSiniestro().getCompania() == codCompania){
                                        if (reg.getPerito() == codPerito){
                                            if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)){
                                                DataTables.getRegActivos().add(reg);
                                            }
                                        }
                                    }
                                }
                            }
                        }else {
                            //COMPANIA, PERITO Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getSiniestro().getCompania() == codCompania){
                                        if (reg.getPerito() == codPerito){
                                            DataTables.getRegActivos().add(reg);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //SIN PERITO
                        if (!estadoPago.equals("Default")){
                            //COMPANIA, ESTADO PAGO Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getSiniestro().getCompania() == codCompania){
                                        if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)){
                                            DataTables.getRegActivos().add(reg);
                                        }
                                    }
                                }
                            }
                        }else {
                            //COMPANIA Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)) {
                                    if (reg.getSiniestro().getCompania() == codCompania) {
                                        DataTables.getRegActivos().add(reg);
                                    }
                                }
                            }
                        }
                    }
                }else {
                    //SIN COMPANIA
                    if (!perito.equals("Default")){
                        //PERITO Y ESTADO REGISTRO
                        if (!estadoPago.equals("Default")){
                            //PERITO, ESTADO PAGO Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getPerito() == codPerito){
                                        if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)){
                                            DataTables.getRegActivos().add(reg);
                                        }
                                    }
                                }
                            }
                        }else {
                            //PERITO Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getPerito() == codPerito) DataTables.getRegActivos().add(reg);
                                }
                            }
                        }
                    } else {
                        //ESTADO PAGO Y ESTADO REGISTRO
                        if (!estadoPago.equals("Default")){
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)) DataTables.getRegActivos().add(reg);
                                }
                            }
                        }else {
                            //SOLO ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)) DataTables.getRegActivos().add(reg);
                            }
                        }
                    }
                }

            } else {
                // CON FECHAS
                if(!compania.equals("Default")){
                    //COMPANIA
                    for(Compania com : DataTables.getCompanias()){
                        if (com.getNombreCompania().equals(compania)){
                            codCompania = com.getCodCompania();
                            break;
                        }
                    }
                    if (!perito.equals("Default")){
                        //COMPANIA, PERITO Y ESTADO REGISTRO
                        if (!estadoPago.equals("Default")){
                            //COMPANIA, PERITO, ESTADO PAGO Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getSiniestro().getCompania() == codCompania){
                                        if (reg.getPerito() == codPerito){
                                            if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)){

                                                if (rbFechaApertura.isSelected()){
                                                    if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                                        if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                            DataTables.getRegActivos().add(reg);
                                                        }
                                                    }
                                                } else if (rbFechaCierre.isSelected()){
                                                    if (reg.getFechaCierre() != null) {
                                                        if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                            if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                                DataTables.getRegActivos().add(reg);
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }else {
                            //COMPANIA, PERITO Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getSiniestro().getCompania() == codCompania){
                                        if (reg.getPerito() == codPerito){
                                            if (rbFechaApertura.isSelected()){
                                                if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                                    if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                        DataTables.getRegActivos().add(reg);
                                                    }
                                                }
                                            } else if (rbFechaCierre.isSelected()){
                                                if (reg.getFechaCierre() != null) {
                                                    if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                        if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                            DataTables.getRegActivos().add(reg);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //SIN PERITO
                        if (!estadoPago.equals("Default")){
                            //COMPANIA, ESTADO PAGO Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getSiniestro().getCompania() == codCompania){
                                        if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)){
                                            if (rbFechaApertura.isSelected()){
                                                if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                                    if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                        DataTables.getRegActivos().add(reg);
                                                    }
                                                }
                                            } else if (rbFechaCierre.isSelected()){
                                                if (reg.getFechaCierre() != null) {
                                                    if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                        if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                            DataTables.getRegActivos().add(reg);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }else {
                            //COMPANIA Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)) {
                                    if (reg.getSiniestro().getCompania() == codCompania) {
                                        if (rbFechaApertura.isSelected()){
                                            if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                                if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                    DataTables.getRegActivos().add(reg);
                                                }
                                            }
                                        } else if (rbFechaCierre.isSelected()){
                                            if (reg.getFechaCierre() != null) {
                                                if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                    if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                        DataTables.getRegActivos().add(reg);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else {
                    //SIN COMPANIA
                    if (!perito.equals("Default")){
                        //PERITO Y ESTADO REGISTRO
                        if (!estadoPago.equals("Default")){
                            //PERITO, ESTADO PAGO Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getPerito() == codPerito){
                                        if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)){
                                            if (rbFechaApertura.isSelected()){
                                                if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                                    if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                        DataTables.getRegActivos().add(reg);
                                                    }
                                                }
                                            } else if (rbFechaCierre.isSelected()){
                                                if (reg.getFechaCierre() != null) {
                                                    if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                        if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                            DataTables.getRegActivos().add(reg);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }else {
                            //PERITO Y ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getPerito() == codPerito) {
                                        if (rbFechaApertura.isSelected()){
                                            if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                                if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                    DataTables.getRegActivos().add(reg);
                                                }
                                            }
                                        } else if (rbFechaCierre.isSelected()){
                                            if (reg.getFechaCierre() != null) {
                                                if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                    if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                        DataTables.getRegActivos().add(reg);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //ESTADO PAGO Y ESTADO REGISTRO
                        if (!estadoPago.equals("Default")){
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (reg.getFacturaRegistro().getEstadoFactura().equals(estadoPago)){
                                        if (rbFechaApertura.isSelected()){
                                            if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                                if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                    DataTables.getRegActivos().add(reg);
                                                }
                                            }
                                        } else if (rbFechaCierre.isSelected()){
                                            if (reg.getFechaCierre() != null) {
                                                if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                    if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                        DataTables.getRegActivos().add(reg);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }else {
                            //SOLO ESTADO REGISTRO
                            for (Registro reg : DataTables.getRegistros()) {
                                if (reg.getEstadoRegistro().equals(estadoReg)){
                                    if (rbFechaApertura.isSelected()){
                                        if (reg.getFechaApertura().after(desde) || reg.getFechaApertura().equals(desde)) {
                                            if (reg.getFechaApertura().before(hasta) || reg.getFechaApertura().equals(hasta)) {
                                                DataTables.getRegActivos().add(reg);
                                            }
                                        }
                                    } else if (rbFechaCierre.isSelected()){
                                        if (reg.getFechaCierre() != null) {
                                            if (reg.getFechaCierre().after(desde) || reg.getFechaCierre().equals(desde)) {
                                                if (reg.getFechaCierre().before(hasta) || reg.getFechaCierre().equals(hasta)) {
                                                    DataTables.getRegActivos().add(reg);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        DataTables.cleanRegistros();
        if (DataTables.getRegActivos().size() > 0){
            for(Registro r : DataTables.getRegActivos()){
                String np = "";
                String nc = "";
                for (Compania com : DataTables.getCompanias()) {
                    if (r.getSiniestro().getCompania() == com.getCodCompania()) {
                        nc = com.getNombreCompania();
                    }
                }
                for(Perito p : DataTables.getPeritos()){
                    if (p.getCodPerito() == r.getPerito()){
                        np = p.getNombrePerito();
                    }
                }

                Object[] a;
                a = new Object[]{r.getCodRegistro(), nc , r.getReferenciaCom(),r.getEstadoRegistro(), np,
                        formatS.format(r.getFechaApertura()), r.getFacturaRegistro().getEstadoFactura(), r.getTipoRegistro()};
                Config.pControl.pControl.modelRegistro.addRow(a);

            }
            Config.pControl.pControl.actNumeroRegistros();

        } else if (noSelection) Config.error("Debe seleccionar al menos un filtro.");
        else Config.warning("No se han encontrado Registros asociados al/a los filtro/s.");

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
        g.setColor(Config.COLOR4);
        g.fillRect(0, 0, 330, 55);

    }
}