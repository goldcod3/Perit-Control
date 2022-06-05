package interfaces.iregistro;


import clases.*;
import com.toedter.calendar.JDateChooser;
import conexion.DataTables;
import conexion.hilos.SentNewTelf;
import conexion.hilos.SentSafePacket;
import conexion.paquetes.SafePacket;
import conexion.paquetes.TelfPacket;
import interfaces.Config;
import interfaces.iregistro.eventos.HiloNR;
import interfaces.iregistro.eventos.HiloNReaperturaBtn;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * Metodo constructor del PRegistro.
 * Este es el panel base (contenedor) de la ventana InterfazRegistro.
 */

public class PRegistro extends JPanel {

    // VALORES
    private int idInterfaz;
    public boolean safe = false;
    public Registro registro = new Registro();
    public Registro temporal = new Registro();
    public int codReg;
    public int mesReg, anoReg;
    public ArrayList<TelefonoAsegurado> listaTelf = new ArrayList<>();
    public ArrayList<TelefonoAsegurado> telfAseg = new ArrayList<>();
    public static int porcentajeIVA = 21;
    public AmpliacionCampos ampliacion;
    public int ampliaciones =0;

    // COMPONENTES
    public JLabel labTRegistro, labPerito, labFechaAper, fechaApert, labEstado, estadoReg, labCompania, labRamo,
            labRefCia, labTipoSin, labNPoliza, labContacto, labTSiniestro, labAsegurado,
            labFechaEPer, labDirCont, labDirRies, labSContacto, labDescrip, labTReaperturas, labTFacturacion,
            labTGabinete, labTPerito, labGHonor, labGLocomoc,
            labGVarios, labGSuma, labGIva, labGTotalMin, labPHonor, labPLocomoc,
            labPAjustes, labPSuma, labPIva, labPIrpf, labPTotal, labNFactura,
            labEstPago, labFCierre, labFFacPerito, labFAno, labFMes, labObserv, labUsuario, labTipoInt;
    public JComboBox boxPerito, boxCompania, boxRamo, boxTipoSin, boxEstadoPago, boxTipoInt;
    public JTextField textRefCia, textNPoliza, textRContacto, textAsegurado, textDirCont,
            textDirRies, textSContacto, textNFactura, textFMes, textFAno, textGHonor,
            textGLocom, textGVarios, textGSuma, textGIva,
            textGTotal, textPHonor, textPLocom, textPAjustes, textPSuma, textPIva,
            textPIrpf, textPTotal, textUsuario, textFCierre;
    public JTextArea areaDescrip, areaObserv;
    public JDateChooser textFechaEPer;
    public JTable tabTelefonos, tabReapert;
    public DefaultTableModel modelTelf, modelReapert;
    public JScrollPane scrollDescrip, scrollObserv, scrollTelf, scrollReapert;
    public JButton verificar, cerrarReg, guardar, reaperturarReg, abrirReapert,
            entregaPerito, anadirTelefono, modifTelfono, elimTelefono;

    /*
     * Metodo constructor del panel
     */
    public PRegistro(Registro reg, int codVent){
        registro = reg;
        setIdInterfaz(codVent);
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10,10,10,10));

        //LAYOUT
        GridBagLayout gbl_contenedor = new GridBagLayout();
        gbl_contenedor.columnWidths = new int[]{0};
        gbl_contenedor.rowHeights = new int[]{0};
        gbl_contenedor.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_contenedor.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_contenedor);

        //INICIALIZACION DE COMPONENTES
        codVent = codVent;
        codReg = registro.getCodRegistro();
        mesReg = Integer.parseInt(new SimpleDateFormat("MM").format(registro.getFechaApertura()));
        anoReg = Integer.parseInt(new SimpleDateFormat("yy").format(registro.getFechaApertura()));
        initComponents();
        chargeCamposBox();
        chargeOpenRegistro();

        //LAYOUT COMPONENTS
        ////////////////// REGISTRO

        //LABEL TITULO REGISTRO
        GridBagConstraints grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,0,0);
        grid.insets = new Insets(0,10,10,0);
        grid.gridwidth = 2;
        add(labTRegistro, grid);

        //LABEL PERITO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,1);
        grid.weightx = 0.02;
        add(labPerito, grid);

        //COMBO BOX PERITO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,2);
        grid.gridwidth = 2;
        add(boxPerito, grid);

        //LABEL FECHA APERTURA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,2,1);
        grid.insets = new Insets(0,10,0,0);
        grid.weightx = 0.03;
        add(labFechaAper, grid);

        //FECHA APERTURA
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,2,2);
        grid.insets = new Insets(0,40,0,0);
        add(fechaApert, grid);

        //LABEL ESTADO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,4,1);
        add(labEstado, grid);

        //ESTADO REGISTRO
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,4,2);
        grid.insets = new Insets(0,40,0,0);
        add(estadoReg, grid);

        //LABEL COMPANIA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,3);
        add(labCompania, grid);

        //COMBO BOX COMPANIA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,4);
        grid.insets = new Insets(0,0,0,10);
        add(boxCompania, grid);

        //LABEL RAMO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,3);
        grid.weightx = 0.02;
        add(labRamo, grid);

        //COMBO BOX RAMO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,4);
        add(boxRamo, grid);

        //LABEL REFERENCIA CIA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,2,3);
        grid.insets = new Insets(0,10,0,0);
        add(labRefCia, grid);

        //CAMPO REFERENCIA CIA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,2,4);
        grid.insets = new Insets(0,10,0,0);
        add(textRefCia, grid);

        //LABEL TIPO SINIESETRO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,5);
        add(labTipoSin, grid);

        //COMBO BOX TIPO SINIESTRO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,6);
        add(boxTipoSin, grid);

        //LABEL NUMERO DE POLIZA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,2,5);
        grid.insets = new Insets(0,10,0,0);
        add(labNPoliza, grid);

        //CAMPO NUMERO DE POLIZA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,2,6);
        grid.insets = new Insets(0,10,0,0);
        add(textNPoliza, grid);

        //BOTON VERIFICAR POLIZA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,3,6);
        grid.weightx = 0.02;
        grid.gridheight = 2;
        grid.insets = new Insets(0,10,5,10);
        add(verificar, grid);

        //LABEL CONTACTO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,4,3);
        add(labContacto, grid);

        //CAMPO CONTACTO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,4,4);
        add(textRContacto, grid);

        //LABEL TIPO INTERVENCION
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,3,3);
        grid.insets = new Insets(0,10,0,0);
        add(labTipoInt, grid);

        //CAMPO TIPO INTERVENCION
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,3,4);
        grid.insets = new Insets(0,10,0,10);
        add(boxTipoInt, grid);


        ////////////////// SINIESTRO

        //LABEL TITULO SINIESTRO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,7);
        grid.weighty = 0.02;
        add(labTSiniestro, grid);

        //LABEL ASEGURADO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,8);
        add(labAsegurado, grid);

        //CAMPO ASEGURADO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,8);
        grid.gridwidth = 2;
        add(textAsegurado, grid);

        //LABEL DIRECCION CONTACTO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,9);
        add(labDirCont, grid);

        //CAMPO DIRECCION CONTACTO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,9);
        grid.gridwidth = 2;
        add(textDirCont, grid);

        //LABEL DIRECCION RIESGO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,10);
        add(labDirRies, grid);

        //CAMPO DIRECCION RIESGO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,10);
        grid.gridwidth = 2;
        add(textDirRies, grid);

        //LABEL CONTACTO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,11);
        add(labSContacto, grid);

        //CAMPO CONTACTO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,11);
        grid.gridwidth = 2;
        add(textSContacto, grid);

        //LABEL TELEFONO
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,3,8);
        grid.insets = new Insets(0,10,0,0);
        //add(labATelefonos, grid);

        //TABLA TELEFONOS ASEGURADO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,3,8);
        grid.gridheight = 4;
        grid.insets = new Insets(0,10,0,10);
        add(scrollTelf, grid);

        //BOTON ANADIR TELEFONO:
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,3,12);
        grid.insets = new Insets(3,9,3,9);
        add(anadirTelefono, grid);

        //BOTON MODIFICAR TELEFONO:
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,3,12);
        grid.insets = new Insets(3,9,3,9);
        add(modifTelfono, grid);

        //BOTON ELIMINAR TELEFONO:
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE,3,12);
        grid.insets = new Insets(3,9,3,9);
        add(elimTelefono, grid);

        //LABEL DESCRIPCION
        grid = newPosicion(GridBagConstraints.SOUTHWEST,
                GridBagConstraints.HORIZONTAL,0,12);
        grid.insets = new Insets(10,0,5,0);
        add(labDescrip, grid);

        //AREA DESCRIPCION
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,0,13);
        grid.gridwidth = 5;
        grid.weighty = 0.6;
        grid.gridheight = 3;
        add(scrollDescrip, grid);


        ////////////////// FACTURACION

        //LABEL TITULO FACTURACION
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,17);
        grid.insets = new Insets(10,0,0,0);
        grid.weighty = 0.06;
        add(labTFacturacion, grid);

        //LABEL NUMERO DE FACTURA
        grid = newPosicion(GridBagConstraints.SOUTHWEST,
                GridBagConstraints.HORIZONTAL,1,17);
        add(labNFactura, grid);

        //CAMPO NUMERO DE FACTURA
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,18);
        add(textNFactura, grid);

        //LABEL ESTADO DE PAGO
        grid = newPosicion(GridBagConstraints.SOUTHWEST,
                GridBagConstraints.HORIZONTAL,3,17);
        add(labEstPago, grid);

        //COMBO BOX ESTADO DE PAGO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,3,18);
        grid.insets = new Insets(2,0,2,0);
        add(boxEstadoPago, grid);

        //LABEL FECHA ENTREGA PERITO
        grid = newPosicion(GridBagConstraints.SOUTH,
                GridBagConstraints.HORIZONTAL,4,17);
        grid.weightx = 0.03;
        add(labFechaEPer, grid);

        //CAMPO FECHA ENTREGA PERITO
        grid = newPosicion(GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,4,18);
        add(textFechaEPer, grid);

        //LABEL TITULO FACTURACION GABINETE
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,19);
        grid.insets = new Insets(10,10,0,0);
        add(labTGabinete, grid);

        //LABEL HONORARIOS GAB
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,0,20);
        add(labGHonor, grid);

        //CAMPO HONORARIOS GAB
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,20);
        grid.insets = new Insets(0,0,0,80);
        add(textGHonor, grid);

        //LABEL LOCOMOCION GAB
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,0,21);
        add(labGLocomoc, grid);

        //CAMPO LOCOMOCION GAB
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,21);
        grid.insets = new Insets(0,0,0,80);
        add(textGLocom, grid);

        //LABEL VARIOS GAB
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,0,22);
        add(labGVarios, grid);

        //CAMPO VARIOS GAB
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,22);
        grid.insets = new Insets(0,0,0,80);
        add(textGVarios, grid);

        //LABEL SUMA GAB
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,0,23);
        add(labGSuma, grid);

        //CAMPO SUMA GAB
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,23);
        grid.insets = new Insets(0,0,0,80);
        add(textGSuma, grid);

        //LABEL IVA GAB
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,0,24);
        add(labGIva, grid);

        //CAMPO IVA GAB
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,24);
        grid.insets = new Insets(0,0,0,80);
        add(textGIva, grid);

        //LABEL TOTAL GAB
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,0,25);
        add(labGTotalMin, grid);

        //CAMPO TOTAL GAB
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,25);
        grid.insets = new Insets(0,0,0,80);
        add(textGTotal, grid);

        //SIMBOLOS EURO GAB
        for (int i = 20; i < 26; i++){
            JLabel euro = new JLabel("€");
            grid = newPosicion(GridBagConstraints.EAST,
                    GridBagConstraints.NONE,1,i);
            grid.insets = new Insets(0,0,0,70);
            add(euro, grid);
        }


        //LABEL TITULO FACTURACION PERITO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,2,19);
        grid.insets = new Insets(10,20,0,0);
        add(labTPerito, grid);

        //LABEL HONORARIOS PER
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,2,20);
        add(labPHonor, grid);

        //CAMPO HONORARIOS PER
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,3,20);
        grid.insets = new Insets(0,0,0,50);
        add(textPHonor, grid);

        //LABEL LOCOMOCION PER
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,2,21);
        add(labPLocomoc, grid);

        //CAMPO LOCOMOCION PER
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,3,21);
        grid.insets = new Insets(0,0,0,50);
        add(textPLocom, grid);

        //LABEL AJUSTES PER
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,2,22);
        add(labPAjustes, grid);

        //CAMPO AJUSTES PER
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,3,22);
        grid.insets = new Insets(0,0,0,50);
        add(textPAjustes, grid);

        //LABEL SUMA PER
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,2,23);
        add(labPSuma, grid);

        //CAMPO SUMA PER
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,3,23);
        grid.insets = new Insets(0,0,0,50);
        add(textPSuma, grid);

        //LABEL IVA PER
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,2,24);
        add(labPIva, grid);

        //CAMPO IVA PER
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,3,24);
        grid.insets = new Insets(0,0,0,50);
        add(textPIva, grid);

        //LABEL IRPF PER
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,2,25);
        add(labPIrpf, grid);

        //CAMPO IRPF PER
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,3,25);
        grid.insets = new Insets(0,0,0,50);
        add(textPIrpf, grid);

        //LABEL TOTAL PER
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,2,26);
        add(labPTotal, grid);

        //CAMPO TOTAL PER
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,3,26);
        grid.insets = new Insets(0,0,0,50);
        add(textPTotal, grid);

        //SIMBOLOS EURO PER
        for (int i = 20; i < 27; i++){
            JLabel euro = new JLabel("€");
            grid = newPosicion(GridBagConstraints.EAST,
                    GridBagConstraints.NONE,3,i);
            grid.insets = new Insets(0,0,0,40);
            add(euro, grid);
        }

        //LABEL OBSERVACIONES
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,0,28);
        grid.insets = new Insets(12,0,0,0);
        add(labObserv, grid);


        //AREA OBSERVACIONES
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,0,29);
        grid.gridwidth = 3;
        grid.gridheight = 2;
        grid.weighty = 0.02;
        grid.insets = new Insets(0,0,10,0);
        scrollObserv.setPreferredSize(new Dimension(300, 50));
        add(scrollObserv, grid);

        //LABEL FECHA FACTURACION PERITO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,4,24);
        add(labFFacPerito, grid);

        //LABEL MES FACTURACION PERITO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,4,25);
        add(labFMes, grid);

        //CAMPO MES FACTURACION PERITO
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,4,25);
        grid.insets = new Insets(0,50,0,70);
        add(textFMes, grid);

        //LABEL ANO FACTURACION PERITO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,4,26);
        add(labFAno, grid);

        //CAMPO ANO FACTURACION PERITO
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,4,26);
        grid.insets = new Insets(0,50,0,70);
        add(textFAno, grid);

        //BOTON ENTREGA PERITO
        grid = newPosicion(GridBagConstraints.NORTH,
                GridBagConstraints.BOTH,4,20);
        grid.gridheight = 3;
        grid.insets = new Insets(0,0,0,0);
        add(entregaPerito, grid);

        //LABEL USUARIO
        grid = newPosicion(GridBagConstraints.SOUTHWEST,
                GridBagConstraints.NONE,4,28);
        add(labUsuario, grid);

        //COMBO BOX USUARIO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,4,29);
        //grid.insets = new Insets(0,10,0,10);
        add(textUsuario, grid);

        //LABEL FECHA CIERRE REGISTRO
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,0,31);
        grid.insets = new Insets(3,40,0,10);
        add(labFCierre, grid);

        //CAMPO FECHA CIERRE
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,31);
        grid.insets = new Insets(3,0,0,10);
        add(textFCierre, grid);

        //BOTON CERRAR REGISTRO
        grid = newPosicion(GridBagConstraints.NORTH,
                GridBagConstraints.BOTH,2,31);
        grid.gridheight = 2;
        grid.insets = new Insets(0,0,0,0);
        add(cerrarReg, grid);

        //BOTON GUARDAR
        grid = newPosicion(GridBagConstraints.SOUTH,
                GridBagConstraints.BOTH,4,30);
        grid.insets = new Insets(50,0,0,0);
        grid.gridheight = 2;
        add(guardar, grid);

        //BOTON REAPERTURA
        grid = newPosicion(GridBagConstraints.NORTH,
                GridBagConstraints.BOTH,4,8);
        grid.gridheight = 2;
        add(reaperturarReg, grid);

        //SI NO EXITEN REAPERTURAS
        if (registro.getNumeroReaperturas() > 0){

            //AREA DESCRIPCION
            grid = newPosicion(GridBagConstraints.WEST,
                    GridBagConstraints.BOTH,0,13);
            grid.insets = new Insets(0,0,0,5);
            grid.gridwidth = 4;
            grid.weighty = 0.6;
            grid.gridheight = 3;
            add(scrollDescrip, grid);

            //LABEL REAPERTURAS:
            grid = newPosicion(GridBagConstraints.NORTH,
                    GridBagConstraints.BOTH,4,13);
            add(labTReaperturas, grid);

            //TABLA REAPERTURAS:
            grid = newPosicion(GridBagConstraints.NORTH,
                    GridBagConstraints.BOTH,4,14);
            grid.gridheight = 2;
            add(scrollReapert, grid);

            //BOTON ABRIR REAPERTURA:
            grid = newPosicion(GridBagConstraints.NORTH,
                    GridBagConstraints.BOTH,4,16);
            grid.insets = new Insets(3,0,3,0);
            add(abrirReapert, grid);


            chargeReaperturasReg();
        }

    }

    /*
     * Metodo que inicializa los componentes de la ventana..
     */
    public void initComponents() {

        /////////////// LABELS
        //LABELS DINAMICAS
        fechaApert = new JLabel();
        fechaApert.setFont(Config.FUENTE14);
        estadoReg = new JLabel();
        estadoReg.setFont(Config.FUENTE14);

        //REGISTRO
        labTRegistro = new JLabel();
        labTRegistro.setFont(Config.FUENTETITULO);
        labPerito = new JLabel("Perito:");
        labFechaAper = new JLabel("Fecha Apertura:");
        labEstado = new JLabel("Estado:");
        labCompania = new JLabel("Compañia:");
        labRamo = new JLabel("Ramo:");
        labRefCia = new JLabel("Referencia Cia:");
        labTipoSin = new JLabel("Tipo Siniestro:");
        labNPoliza = new JLabel("Nº Póliza:");
        labContacto = new JLabel("Contacto/Tramitador:");
        labTipoInt = new JLabel("Tipo Intervención:");
        labTReaperturas = new JLabel("Reaperturas:");

        //SINIESTRO
        labTSiniestro = new JLabel("SINIESTRO");
        labAsegurado = new JLabel("Asegurado:");
        labFechaEPer = new JLabel("Fecha Entrega Perito:");
        labDirCont = new JLabel("Dirección de Contacto:");
        labDirRies = new JLabel("Dirección de Riesgo:");
        labSContacto = new JLabel("Contacto:");
        labDescrip = new JLabel("Descripción:");

        //HONORARIOS GABINETE
        labTFacturacion = new JLabel("FACTURACIÓN");
        labTGabinete = new JLabel("GABINETE");
        labTPerito = new JLabel("PERITO");
        labGHonor = new JLabel("Honorarios:");
        labGLocomoc = new JLabel("Locomoción:");
        labGVarios = new JLabel("Varios:");
        labGSuma = new JLabel("Suma:");
        labGIva = new JLabel("IVA 21%:");
        labGTotalMin = new JLabel("Total Minuta:");

        //HONORARIOS PERITO
        labPHonor = new JLabel("Honorarios:");
        labPLocomoc = new JLabel("Locomoción:");
        labPAjustes = new JLabel("Ajustes:");
        labPSuma = new JLabel("Suma:");
        labPIva = new JLabel("IVA "+porcentajeIVA+"%:");
        labPIrpf = new JLabel("IRPF:");
        labPTotal = new JLabel("Total Minuta:");

        //FACTURACION
        labNFactura = new JLabel("Nº Factura:");
        labEstPago = new JLabel("Estado Pago:");
        labFCierre = new JLabel("Fecha Cierre:");
        labFFacPerito = new JLabel("Facturación Perito:");
        labFAno = new JLabel("Año:");
        labFMes = new JLabel("Mes:");
        labObserv = new JLabel("Observaciones:");
        labObserv.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                areaObserv.requestFocus();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        labUsuario = new JLabel("Usuario:");

        /////////////// COMBO BOX
        boxPerito = new JComboBox();
        boxPerito.setBackground(Color.WHITE);
        boxCompania = new JComboBox();
        boxCompania.setBackground(Color.WHITE);
        boxCompania.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxRamo.removeAllItems();
                if (!boxCompania.getSelectedItem().equals("Default")){
                    String nombreCom = (String) boxCompania.getSelectedItem();
                    for (Compania com : DataTables.getCompanias()){
                        if(com.getEstadoCompania().equals("ACTIVO")){
                            if(com.getNombreCompania().equals(nombreCom)){
                                int codCompania = com.getCodCompania();
                                for (RamoCompania ram : DataTables.getRamos()){
                                    if (ram.getCompania() == codCompania && ram.getEstadoRam().equals("ACTIVO")){
                                        boxRamo.addItem(ram.getDescripcionRamo());
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        });

        boxRamo = new JComboBox();
        boxRamo.setBackground(Color.WHITE);
        boxTipoSin = new JComboBox();
        boxTipoSin.setBackground(Color.WHITE);
        boxTipoInt = new JComboBox();
        boxTipoInt.setBackground(Color.WHITE);

        //COMBO BOX ESTADO PAGO FACTURA
        String [] headEPago = {"IMPAGO", "PAGO"};
        boxEstadoPago = new JComboBox(headEPago);
        boxEstadoPago.setBackground(Color.WHITE);


        /////////////// CAMPOS JTEXTFIELD
        textRefCia = new JTextField();
        limitarCaracteres(textRefCia,45);
        seleccionarTexto(textRefCia);
        textRefCia.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textRefCia.getText().length() > 0) {
                    for (Registro r : DataTables.getRegistros()) {
                        if (r.getReferenciaCom().equals(textRefCia.getText())){
                            if (r.getEstadoRegistro().equals("NUEVO")) Config.warning("La Referencia ya existe. Pulse el Botón Verificar.");
                            verificar.setBackground(Config.AUX1);
                            verificar.setForeground(Color.WHITE);
                            if (registro.getEstadoRegistro().equals("NUEVO")) Config.warning("La Referencia introducida ya existe en la Base de Datos.\nPulse el botón verificar para ver mas información.");
                           break;
                        }
                    }
                }
            }
        });
        textNPoliza = new JTextField();
        limitarCaracteres(textNPoliza,45);
        seleccionarTexto(textNPoliza);
        textNPoliza.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textNPoliza.getText().length() > 0) {
                    for (Registro r : DataTables.getRegistros()) {
                        if (r.getSiniestro().getNumPoliza().equals(textNPoliza.getText())){
                            if (r.getEstadoRegistro().equals("NUEVO")) Config.warning("El Número de Póliza ya existe. Pulse el Botón Verificar.");
                            verificar.setBackground(Config.AUX1);
                            verificar.setForeground(Color.WHITE);
                            if (registro.getEstadoRegistro().equals("NUEVO")) Config.warning("La Póliza introducida ya existe en la Base de Datos.\nPulse el botón verificar para ver mas información.");break;
                        }
                    }
                }
            }
        });
        textRContacto = new JTextField();
        limitarCaracteres(textRContacto,150);
        textAsegurado = new JTextField();
        limitarCaracteres(textAsegurado,150);
        textDirCont = new JTextField();
        limitarCaracteres(textDirCont,400);
        textDirRies = new JTextField();
        limitarCaracteres(textDirRies,400);
        textSContacto = new JTextField();
        limitarCaracteres(textSContacto,150);
        textNFactura = new JTextField();
        limitarCaracteres(textNFactura,45);
        seleccionarTexto(textNFactura);
        textNFactura.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textNFactura.getText().length() > 0) {
                    for (Registro r : DataTables.getRegistros()) {
                        if (r.getFacturaRegistro().getNumFactura().equals(textNFactura.getText())){
                            if (!registro.getEstadoRegistro().equals("CERRADO")) Config.warning("El Número de Factura introducido ya existe en la Base de Datos.");
                            break;
                        }
                    }
                }
            }
        });
        textFCierre = new JTextField();
        textFCierre.setEditable(false);
        textFechaEPer = new JDateChooser();
        textFMes = new JTextField();
        seleccionarTexto(textFMes);
        limitarCaracteres(textFMes, 2);
        textFAno = new JTextField();
        seleccionarTexto(textFAno);
        limitarCaracteres(textFAno, 2);
        textUsuario = new JTextField();
        textUsuario.setEditable(false);

        //GABINETE
        textGHonor = new JTextField("0.0");
        seleccionarTextoNumerico(textGHonor);
        textGHonor.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textGHonor.requestFocus();
            }
        });
        textGLocom = new JTextField("0.0");
        seleccionarTextoNumerico(textGLocom);
        textGVarios = new JTextField("0.0");
        seleccionarTextoNumerico(textGVarios);
        textGSuma = new JTextField("0.0");
        textGSuma.setEditable(false);
        textGSuma.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                textPIrpf.requestFocus();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        textGIva = new JTextField("0.0");
        textGIva.setEditable(false);
        seleccionarTextoNumerico(textGIva);
        textGTotal = new JTextField("0.0");
        textGTotal.setEditable(false);

        //PERITO
        textPHonor = new JTextField("0.0");
        seleccionarTextoNumerico(textPHonor);
        textPLocom = new JTextField("0.0");
        seleccionarTextoNumerico(textPLocom);
        textPAjustes = new JTextField("0.0");
        seleccionarTextoNumerico(textPAjustes);
        textPSuma = new JTextField("0.0");
        textPSuma.setEditable(false);
        textPIva = new JTextField("0.0");
        textPIva.setEditable(false);
        textPIva.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                textPIrpf.requestFocus();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        textPIrpf = new JTextField("0.0");
        seleccionarTextoNumerico(textPIrpf);
        textPTotal = new JTextField("0.0");
        textPTotal.setEditable(false);
        textPTotal.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                textFAno.requestFocus();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        //CAMPOS JTEXTAREA
        areaDescrip = new JTextArea();
        scrollDescrip = new JScrollPane(areaDescrip);
        areaDescrip.setLineWrap(true);
        areaDescrip.addKeyListener(new KeyListener() {
            int contador = 0;

            @Override
            public void keyTyped(KeyEvent e) {
                contador++;
                if (areaDescrip.getText().length() >= 3000){
                    e.consume();
                }
                if (contador >= 120){
                    areaDescrip.append("\n");
                    contador = 0;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        areaObserv = new JTextArea();
        scrollObserv = new JScrollPane(areaObserv);
        areaObserv.setLineWrap(true);
        areaObserv.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (areaObserv.getText().length() >= 800){
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        /////////////// TABLAS
        //TABLA TELEFONOS
        modelTelf = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        modelTelf.addColumn("Teléfonos");
        tabTelefonos = new JTable();
        tabTelefonos.setModel(modelTelf);
        tabTelefonos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabTelefonos.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textDirCont.requestFocus();
            }

            @Override
            public void focusLost(FocusEvent e) {


            }
        });
        modelTelf.isCellEditable(0,0);
        scrollTelf = new JScrollPane(tabTelefonos);

        //TABLA REAPERTURAS
        modelReapert = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        modelReapert.addColumn("Código");
        modelReapert.addColumn("Fecha");
        tabReapert = new JTable(modelReapert);
        tabReapert.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabReapert.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabReapert.isCellEditable(0,0);
        scrollReapert = new JScrollPane(tabReapert);

        /////////////// BOTONES
        //BOTON GUARDAR
        guardar = new JButton("Guardar");
        guardar.setBackground(Config.COLOR4);
        guardar.setForeground(Color.WHITE);
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro t = getRegistroFrame();
                if (t.getEstadoEntrega().equals("ENTREGADO")) {
                    validarFacturaPerito();
                    t.getFacturaRegistro().setMesFactPerito(Integer.parseInt(textFMes.getText()));
                    t.getFacturaRegistro().setAnoFactPerito(Integer.parseInt(textFAno.getText()));
                }
                if (compareRegistro(t)){
                    Config.warning("No se detectan modificaciones en el Registro.");

                }else{
                    //Sent packet Safe
                    if (!boxPerito.getSelectedItem().equals("Default")) {
                        if (textNPoliza.getText().length() > 1) {
                            if (textRefCia.getText().length() > 1) {
                                if (!boxCompania.getSelectedItem().equals("Default")) {
                                    if (!boxRamo.getSelectedItem().equals("Default")) {
                                        if (!boxTipoSin.getSelectedItem().equals("Default")) {
                                            if (!boxTipoInt.getSelectedItem().equals("Default")) {


                                                if (registro.getEstadoRegistro().equals("NUEVO")){
                                                    SafePacket packet = new SafePacket(1, Config.getUser(), Config.getIpClient());
                                                    packet.setCodVentana(getIdInterfaz());
                                                    packet.setRegistro(t);
                                                    Thread saveReg = new Thread(new SentSafePacket(packet));
                                                    saveReg.start();
                                                } else {
                                                    if (t.getEstadoEntrega().equals("ENTREGADO")) {
                                                        validarFacturaPerito();
                                                        t.getFacturaRegistro().setMesFactPerito(Integer.parseInt(textFMes.getText()));
                                                        t.getFacturaRegistro().setAnoFactPerito(Integer.parseInt(textFAno.getText()));
                                                    }
                                                    SafePacket packet = new SafePacket(2, Config.getUser(), Config.getIpClient());
                                                    packet.setCodVentana(getIdInterfaz());
                                                    packet.setRegistro(t);
                                                    Thread saveReg = new Thread(new SentSafePacket(packet));
                                                    saveReg.start();
                                                }
                                                Config.pControl.pControl.changeUltimaAccion("GUARDANDO...");

                                            } else Config.warning("Debe seleccionar una opción para el campo Tipo Intervención.");
                                        } else Config.warning("Debe seleccionar una opción para el campo Tipo Siniestro.");
                                    } else Config.warning("Debe seleccionar una opción para el campo Ramo Compañia.");
                                } else Config.warning("Debe seleccionar una opción para el campo Compañia.");
                            } else Config.warning("Debe introducir un Número de Referencia.");
                        } else Config.warning("Debe introducir un Número de Póliza.");
                    } else Config.warning("Debe seleccionar una opción para el campo Perito.");
                }
            }
        });

        //BOTON VERIFICAR POLIZA
        verificar = new JButton("Verificar");
        verificar.setBackground(Config.COLOR5);
        verificar.setForeground(Color.WHITE);
        verificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Config.pVerifyPol == null){
                    if (!textNPoliza.getText().isEmpty() && textRefCia.getText().isEmpty()) {

                        String pol = textNPoliza.getText();
                        Config.pVerifyPol = new InterfazVerifPoliza(1, pol);

                    }else if (textNPoliza.getText().isEmpty() && !textRefCia.getText().isEmpty()){

                        String ref = textRefCia.getText();
                        Config.pVerifyPol = new InterfazVerifPoliza(2,ref);

                    }else if (!textNPoliza.getText().isEmpty() && !textRefCia.getText().isEmpty()){

                        String pol = textNPoliza.getText();
                        String ref = textRefCia.getText();
                        Config.pVerifyPol = new InterfazVerifPoliza(pol,ref);

                    }else{
                        Config.error( "El campo Nº Póliza o Nº Referencia no puede estar vacío para realizar esta acción.\n " +
                                "Introduzca un valor válido.");
                    }

                }else {
                    Config.warning("Cierre la Ventana de verificación en ejecución para realizar la acción.");
                }
                verificar.setBackground(Config.COLOR5);
            }
        });

        //BOTON ANADIR TELEFONO
        anadirTelefono = new JButton();
        anadirTelefono.setPreferredSize(new Dimension(30,30));
        anadirTelefono.setIcon(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\anadirt.png"));
        anadirTelefono.setBackground(Config.COLOR4);
        anadirTelefono.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

                if (Config.configuracion.isCheckAmpliacion()){
                    if (ampliacion == null) ampliacion = new AmpliacionCampos(idInterfaz, registro);
                }else areaDescrip.requestFocus();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        anadirTelefono.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(registro.getEstadoRegistro().equals("NUEVO")){
                    Config.warning("Debe crear el Registro para añadir teléfonos.");
                }else {
                    String numeroNuevo = Config.value("Introduce un número telefónico.", "PeritControl - Nuevo Teléfono.");
                    if (verificarTelf(numeroNuevo) && duplicatedTelf(numeroNuevo)) {
                        int nuevoTelefono = Integer.parseInt(numeroNuevo);
                        TelefonoAsegurado tel = new TelefonoAsegurado(0, nuevoTelefono, registro.getSiniestro().getAsegurado().getCodAsegurado());
                        TelfPacket packet = new TelfPacket(1, Config.getUser(), Config.getIpClient());
                        packet.setRegistro(registro);
                        packet.setCodVentana(getIdInterfaz());
                        packet.setTelefonoAsegurado(tel);
                        SentNewTelf hiloTelf1 = new SentNewTelf();
                        hiloTelf1.pack = packet;
                        Thread hilo = new Thread(hiloTelf1);
                        hilo.start();

                    } else {
                        Config.error("El formato introducido es incorrecto o ya existe.\n" +
                                "Un número telefonico se compone de 9 números enteros -999999999-.");
                    }
                }
            }
        });

        //BOTON MODIFICAR TELEFONO
        modifTelfono = new JButton();
        modifTelfono.setPreferredSize(new Dimension(30,30));
        modifTelfono.setIcon(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\modift.png"));
        modifTelfono.setBackground(Config.COLOR4);
        modifTelfono.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int telefono = (int) modelTelf.getValueAt(tabTelefonos.getSelectedRow(), 0);
                    String modifTelf = Config.value("Introduce el número a modificar por: "+telefono+".","PeritControl - Modificar Teléfono.");
                    if (verificarTelf(modifTelf) && duplicatedTelf(modifTelf)){
                        TelfPacket packet = new TelfPacket(2, Config.getUser(), Config.getIpClient());
                        TelefonoAsegurado tel = new TelefonoAsegurado(0, 0, 0);
                        for (TelefonoAsegurado tt : listaTelf){
                            if(tt.getTelefono() == telefono){
                                tel = tt;
                                break;
                            }
                        }
                        tel.setTelefono(Integer.parseInt(modifTelf));
                        packet.setTelefonoAsegurado(tel);
                        packet.setRegistro(registro);
                        packet.setCodVentana(getIdInterfaz());
                        SentNewTelf hiloTelf2 = new SentNewTelf();
                        hiloTelf2.pack = packet;
                        Thread hilo = new Thread(hiloTelf2);
                        hilo.start();

                    }else {
                        Config.error("El formato introducido es incorrecto o ya existe.\n" +
                                "Un número telefonico se compone de 9 números enteros -999999999-.");
                    }

                }catch (Exception ex){
                    Config.error("Seleccione un teléfono de la lista.");
                }

            }
        });

        //BOTON ELIMINAR TELEFONO
        elimTelefono = new JButton();
        elimTelefono.setPreferredSize(new Dimension(30,30));
        elimTelefono.setIcon(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\cerrart.png"));
        elimTelefono.setBackground(Config.COLOR4);
        elimTelefono.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int telefono = (int) modelTelf.getValueAt(tabTelefonos.getSelectedRow(), 0);
                    int opt = Config.option("¿Realmente quiere eliminar el teléfono seleccionado?");
                    if (opt == 0){
                        TelefonoAsegurado tel = new TelefonoAsegurado(0, 0, 0);
                        for (TelefonoAsegurado tt : listaTelf){
                            if (tt.getTelefono() == telefono){
                                tel = tt;
                                break;
                            }
                        }
                        TelfPacket packet = new TelfPacket(3, Config.getUser(), Config.getIpClient());
                        packet.setTelefonoAsegurado(tel);
                        packet.setRegistro(registro);
                        packet.setCodVentana(getIdInterfaz());
                        SentNewTelf hiloTelf3 = new SentNewTelf();
                        hiloTelf3.pack = packet;
                        Thread hilo = new Thread(hiloTelf3);
                        hilo.start();

                    }

                }catch (Exception ex){
                    Config.error("Seleccione un teléfono de la lista.");
                }
            }
        });

        //BOTON CERRAR REGISTRO
        cerrarReg = new JButton("Cerrar Registro");
        cerrarReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarCierreRegistro();
            }
        });


        //BOTON ENTREGA PERITO
        entregaPerito = new JButton("  Entrega Siniestro");
        entregaPerito.setIcon(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\albaranr.png"));
        entregaPerito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (registro.getEstadoEntrega().equals("PENDIENTE")){
                    if (textFechaEPer.getDate() == null){
                        Config.error("Introduzca una fecha en el campo de Fecha Entrega Registro.");
                    }else {
                        registro.setFechaEntregaPerito(textFechaEPer.getDate());
                        registro.setEstadoEntrega("ENTREGADO");
                        SafePacket safePacket = new SafePacket(3, Config.getUser(), Config.getIpClient());
                        safePacket.setRegistro(registro);
                        safePacket.setCodVentana(getIdInterfaz());
                        Thread tr = new Thread(new SentSafePacket(safePacket));
                        tr.start();
                        validarFacturaPerito();
                        registro.getFacturaRegistro().setMesFactPerito(Integer.parseInt(textFMes.getText()));
                        registro.getFacturaRegistro().setAnoFactPerito(Integer.parseInt(textFAno.getText()));

                        SafePacket packet = new SafePacket(2, Config.getUser(), Config.getIpClient());
                        packet.setCodVentana(getIdInterfaz());
                        packet.setRegistro(registro);
                        Thread saveReg = new Thread(new SentSafePacket(packet));
                        saveReg.start();
                    }
                } else if (registro.getEstadoEntrega().equals("ENTREGADO")){
                    if (textFechaEPer.getDate() == null){
                        int opt = Config.option("¿Quiere anular la entrega del perito?");
                        if (opt == 0){
                            registro.setFechaEntregaPerito(null);
                            registro.setEstadoEntrega("PENDIENTE");
                            SafePacket safePacket = new SafePacket(3, Config.getUser(), Config.getIpClient());
                            registro.setTipoRegistro("REGISTRO");
                            safePacket.setRegistro(registro);
                            safePacket.setCodVentana(getIdInterfaz());
                            Thread tr = new Thread(new SentSafePacket(safePacket));
                            tr.start();
                        }
                    }else {
                        int opt = Config.option("Este registro ya ha sido entregado, ¿Quiere modificar su entrega?");
                        if (opt == 0){
                            registro.setFechaEntregaPerito(textFechaEPer.getDate());
                            registro.setEstadoEntrega("ENTREGADO");
                            SafePacket safePacket = new SafePacket(3, Config.getUser(), Config.getIpClient());
                            safePacket.setRegistro(registro);
                            safePacket.setCodVentana(getIdInterfaz());
                            Thread tr = new Thread(new SentSafePacket(safePacket));
                            tr.start();
                            validarFacturaPerito();
                            registro.getFacturaRegistro().setMesFactPerito(Integer.parseInt(textFMes.getText()));
                            registro.getFacturaRegistro().setAnoFactPerito(Integer.parseInt(textFAno.getText()));

                            SafePacket packet = new SafePacket(2, Config.getUser(), Config.getIpClient());
                            packet.setCodVentana(getIdInterfaz());
                            packet.setRegistro(registro);
                            Thread saveReg = new Thread(new SentSafePacket(packet));
                            saveReg.start();
                        }
                    }
                }

            }
        });
        entregaPerito.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                textGLocom.requestFocus();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        //BOTON REAPERTURAR REGISTRO
        reaperturarReg = new JButton("Reaperturar");
        reaperturarReg.setEnabled(false);
        reaperturarReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (registro.getEstadoRegistro().equals("CERRADO")){
                    if (registro.getNumeroReaperturas() > 0){
                        int opt = Config.option("Este Registro ya ha sido Reaperturado,\n" +
                                "¿Quiere Reaperturarlo nuevamente?");
                        if (opt == 0){
                            HiloNReaperturaBtn hiloReap = new HiloNReaperturaBtn();
                            hiloReap.codigoReg = registro.getCodRegistro();
                            Thread run = new Thread(hiloReap);
                            run.start();

                        }
                    } else {
                        HiloNReaperturaBtn hiloReap = new HiloNReaperturaBtn();
                        hiloReap.codigoReg = registro.getCodRegistro();
                        Thread run = new Thread(hiloReap);
                        run.start();

                    }
                }
            }
        });

        //BOTON ABRIR REAPERTURA
        abrirReapert = new JButton("Abrir Reapertura");
        abrirReapert.setBackground(Config.AUX6);
        abrirReapert.setForeground(Color.WHITE);
        abrirReapert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Registro regi = new Registro();
                            boolean isOpened = false;
                            int codReap = (int) modelReapert.getValueAt(tabReapert.getSelectedRow(),0);
                            for (InterfazRegistro i : Config.interfaces){
                                if (i.reg.getCodRegistro() == codReap){
                                    isOpened = true;
                                    break;
                                }
                            }
                            if (!isOpened){
                                for (Registro r : DataTables.getRegistros()){
                                    if (r.getCodRegistro() == codReap){
                                        regi = r;
                                        break;
                                    }
                                }
                                Reapertura temp = new Reapertura(0);
                                temp = temp.getReaperturaFromCodigo(codReap);
                                InterfazRegistro inter = new InterfazRegistro(regi, temp);
                                //Config.interfaces.add(inter);
                                InterfazRegistro.addInterface(inter);

                            } else Config.warning("Este Registro está abierto, verifique las ventanas en ejecución.");

                        }catch (Exception e){
                            Config.error("Seleccione un Registro de la tabla de Reaperturas.");
                        }
                    }
                };
                Thread t1 = new Thread(run);
                t1.start();

            }
        });

    }

    /*
     * Metodo que carga los datos del los ComboBox de la ventana
     */
    public void chargeCamposBox(){
        boxPerito.addItem("Default");
        boxCompania.addItem("Default");
        boxTipoSin.addItem("Default");
        boxRamo.addItem("Default");
        boxTipoInt.addItem("Default");
        if (registro.getEstadoRegistro().equals("CERRADO")){
            for (Perito per : DataTables.getPeritos()) boxPerito.addItem(per.getNombrePerito());
            for (Compania com : DataTables.getCompanias()) boxCompania.addItem(com.getNombreCompania());
            for(TipoSiniestro tp : DataTables.getTipoSiniestros()) boxTipoSin.addItem(tp.getDescripTipoSiniestro());
            for (TipoIntervencion ti : DataTables.getTipoInt()) boxTipoInt.addItem(ti.getTipoIntervencion());

        } else {
            for (Perito per : DataTables.getPeritos()) if(per.getEstadoPerito().equals("ACTIVO")) boxPerito.addItem(per.getNombrePerito());
            for (Compania com : DataTables.getCompanias()) if(com.getEstadoCompania().equals("ACTIVO")) boxCompania.addItem(com.getNombreCompania());
            for(TipoSiniestro tp : DataTables.getTipoSiniestros()) if(tp.getEstadoTipoSin().equals("ACTIVO")) boxTipoSin.addItem(tp.getDescripTipoSiniestro());
            for (TipoIntervencion ti : DataTables.getTipoInt()) if(ti.getEstadoIntervencion().equals("ACTIVO")) boxTipoInt.addItem(ti.getTipoIntervencion());

        }
    }


    /// PROPIEDADES, ACCIONES Y VERIFICACIONES DEL REGISTRO
    /*
     * Metodo que carga el devuelve un Registro producto de los campos del Frame
     */
    public Registro getRegistroFrame(){
        Registro regist = new Registro();
        regist.setCodRegistro(registro.getCodRegistro());
        Siniestro siniestro;
        Asegurado asegurado;
        int perito = 0, compania = 0, ramo = 0, tipoSin = 0, tipoInt = 0;

        //Obtengo Perito
        for (Perito p : DataTables.getPeritos()){
            if (boxPerito.getSelectedItem().equals(p.getNombrePerito())){
                perito = p.getCodPerito();
                break;
            }
        }
        //Obtengo Compania
        for(Compania c : DataTables.getCompanias()){
            if(boxCompania.getSelectedItem().toString().equals(c.getNombreCompania())){
                compania = c.getCodCompania();
                for (RamoCompania rc : c.getRamoCompanias()){
                    if (rc.getDescripcionRamo().equals(boxRamo.getSelectedItem().toString())){
                        ramo = rc.getCodRamo();
                        break;
                    }
                }
            }
        }
        for(TipoSiniestro ts : DataTables.getTipoSiniestros()){
            if (boxTipoSin.getSelectedItem().toString().equals(ts.getDescripTipoSiniestro())){
                tipoSin = ts.getCodTipoSiniestro();
                break;
            }
        }
        for (TipoIntervencion ti : DataTables.getTipoInt()){
            if (boxTipoInt.getSelectedItem().equals(ti.getTipoIntervencion())){
                tipoInt = ti.getCodTipoInt();
                break;
            }
        }

        if (registro.getEstadoRegistro().equals("NUEVO")){
            registro.getSiniestro().getAsegurado().setTelefonosAseg(new ArrayList<TelefonoAsegurado>());
            asegurado = new Asegurado(0, textAsegurado.getText(), textDirRies.getText(), textDirCont.getText(), textSContacto.getText());
            asegurado.setTelefonosAseg(listaTelf);
            siniestro = new Siniestro(0, asegurado, compania, ramo, tipoSin, textNPoliza.getText());
            regist.setPerito(perito);
            regist.setEstadoRegistro(registro.getEstadoRegistro());
            regist.setFechaApertura(registro.getFechaApertura());
            regist.setReferenciaCom(textRefCia.getText());
            regist.setSiniestro(siniestro);
            regist.setDescripcionReg(areaDescrip.getText());
            regist.setContactoReg(textRContacto.getText());
            regist.setTipoInt(tipoInt);
        }else {
            int numAseg = registro.getSiniestro().getAsegurado().getCodAsegurado();
            asegurado = new Asegurado(numAseg, textAsegurado.getText(), textDirRies.getText(), textDirCont.getText(), textSContacto.getText());
            asegurado.setTelefonosAseg(listaTelf);
            siniestro = new Siniestro(registro.getSiniestro().getCodSiniestro(), asegurado, compania, ramo, tipoSin, textNPoliza.getText());
            regist.setPerito(perito);
            regist.setEstadoRegistro(registro.getEstadoRegistro());
            regist.setFechaApertura(registro.getFechaApertura());
            regist.setReferenciaCom(textRefCia.getText());
            regist.setSiniestro(siniestro);
            regist.setDescripcionReg(areaDescrip.getText());
            regist.setContactoReg(textRContacto.getText());
            regist.setTipoInt(tipoInt);
            regist.getFacturaRegistro().setCodFactura(registro.getFacturaRegistro().getCodFactura());
            regist.getFacturaRegistro().gethGabinete().setCodHGabinete(registro.getFacturaRegistro().gethGabinete().getCodHGabinete());
            regist.getFacturaRegistro().gethPerito().setCodHPerito(registro.getFacturaRegistro().gethPerito().getCodHPerito());
            regist.getFacturaRegistro().setNumFactura(textNFactura.getText());
            regist.getFacturaRegistro().setEstadoFactura((String) boxEstadoPago.getSelectedItem());
            regist.getFacturaRegistro().gethGabinete().setHonorarioGab(Double.parseDouble(textGHonor.getText()));
            regist.getFacturaRegistro().gethGabinete().setLocomocionGab(Double.parseDouble(textGLocom.getText()));
            regist.getFacturaRegistro().gethGabinete().setVariosGab(Double.parseDouble(textGVarios.getText()));
            regist.getFacturaRegistro().gethGabinete().setSumaGab(Double.parseDouble(textGSuma.getText()));
            regist.getFacturaRegistro().gethGabinete().setIvaGab(Double.parseDouble(textGIva.getText()));
            regist.getFacturaRegistro().gethGabinete().setTotalGab(Double.parseDouble(textGTotal.getText()));
            regist.getFacturaRegistro().gethPerito().setHonorariosPer(Double.parseDouble(textPHonor.getText()));
            regist.getFacturaRegistro().gethPerito().setLocomocionPer(Double.parseDouble(textPLocom.getText()));
            regist.getFacturaRegistro().gethPerito().setAjustesPer(Double.parseDouble(textPAjustes.getText()));
            regist.getFacturaRegistro().gethPerito().setSumaPer(Double.parseDouble(textPSuma.getText()));
            regist.getFacturaRegistro().gethPerito().setIvaPer(Double.parseDouble(textPIva.getText()));
            regist.getFacturaRegistro().gethPerito().setIrpfPer(Double.parseDouble(textPIrpf.getText()));
            regist.getFacturaRegistro().gethPerito().setTotalPer(Double.parseDouble(textPTotal.getText()));
            regist.setEstadoEntrega(registro.getEstadoEntrega());
            if (regist.getEstadoEntrega().equals("ENTREGADO")){
                regist.getFacturaRegistro().setMesFactPerito(Integer.parseInt(textFMes.getText()));
                regist.getFacturaRegistro().setAnoFactPerito(Integer.parseInt(textFAno.getText()));
            }else {
                regist.getFacturaRegistro().setMesFactPerito(0);
                regist.getFacturaRegistro().setAnoFactPerito(0);
            }
            regist.setObservaciones(areaObserv.getText());

        }
        return regist;
    }

    /*
     * Metodo que compara las propiedades o valores de dos registros.
     * Devuelve true si los registros son iguales.
     */
    public boolean compareRegistro(Registro target){
        boolean igual = true;
        if(registro.getEstadoRegistro().equals("NUEVO")){
            if (registro.getPerito() != target.getPerito()) igual =false;
            if (registro.getSiniestro().getCompania() != target.getSiniestro().getCompania()) igual =false;
            if (registro.getSiniestro().getRamo() != target.getSiniestro().getRamo()) igual = false;
            if (registro.getSiniestro().getTipoSiniestro() != target.getSiniestro().getTipoSiniestro()) igual = false;
            if (registro.getTipoInt() != target.getTipoInt()) igual = false;
            if (!registro.getReferenciaCom().equals(target.getReferenciaCom())) igual =false;
            if (!registro.getSiniestro().getNumPoliza().equals(target.getSiniestro().getNumPoliza())) igual = false;
            if (!registro.getSiniestro().getAsegurado().getNombreAseg().equals(target.getSiniestro().getAsegurado().getNombreAseg())) igual = false;
            if (!registro.getSiniestro().getAsegurado().getDireccionContacto().equals(target.getSiniestro().getAsegurado().getDireccionContacto())) igual = false;
            if (!registro.getSiniestro().getAsegurado().getDireccionRiesgo().equals(target.getSiniestro().getAsegurado().getDireccionRiesgo())) igual = false;
            if (!registro.getSiniestro().getAsegurado().getContactoAseg().equals(target.getSiniestro().getAsegurado().getContactoAseg())) igual = false;
            if (!registro.getContactoReg().equals(target.getContactoReg())) igual = false;
            if (!registro.getDescripcionReg().equals(target.getDescripcionReg())) igual = false;

        }else{
            if (registro.getPerito() != target.getPerito()) igual =false;
            if (registro.getSiniestro().getCompania() != target.getSiniestro().getCompania()) igual =false;
            if (registro.getSiniestro().getRamo() != target.getSiniestro().getRamo()) igual = false;
            if (registro.getSiniestro().getTipoSiniestro() != target.getSiniestro().getTipoSiniestro()) igual = false;
            if (registro.getTipoInt() != target.getTipoInt()) igual = false;
            if (!registro.getReferenciaCom().equals(target.getReferenciaCom())) igual =false;
            if (!registro.getSiniestro().getNumPoliza().equals(target.getSiniestro().getNumPoliza())) igual = false;
            if (!registro.getSiniestro().getAsegurado().getNombreAseg().equals(target.getSiniestro().getAsegurado().getNombreAseg())) igual = false;
            if (!registro.getSiniestro().getAsegurado().getDireccionContacto().equals(target.getSiniestro().getAsegurado().getDireccionContacto())) igual = false;
            if (!registro.getSiniestro().getAsegurado().getDireccionRiesgo().equals(target.getSiniestro().getAsegurado().getDireccionRiesgo())) igual = false;
            if (!registro.getSiniestro().getAsegurado().getContactoAseg().equals(target.getSiniestro().getAsegurado().getContactoAseg())) igual = false;
            if (!registro.getContactoReg().equals(target.getContactoReg())) igual = false;
            if (!registro.getDescripcionReg().equals(target.getDescripcionReg())) igual = false;
            if (!registro.getFacturaRegistro().getNumFactura().equals(target.getFacturaRegistro().getNumFactura())) igual = false;
            if (!registro.getFacturaRegistro().getEstadoFactura().equals(target.getFacturaRegistro().getEstadoFactura())) igual = false;
            if (registro.getFacturaRegistro().gethGabinete().getHonorarioGab() != target.getFacturaRegistro().gethGabinete().getHonorarioGab()) igual = false;
            if (registro.getFacturaRegistro().gethGabinete().getLocomocionGab() != target.getFacturaRegistro().gethGabinete().getLocomocionGab()) igual = false;
            if (registro.getFacturaRegistro().gethGabinete().getVariosGab() != target.getFacturaRegistro().gethGabinete().getVariosGab()) igual = false;
            if (registro.getFacturaRegistro().gethGabinete().getIvaGab() != target.getFacturaRegistro().gethGabinete().getIvaGab()) igual = false;
            if (registro.getFacturaRegistro().gethPerito().getHonorariosPer() != target.getFacturaRegistro().gethPerito().getHonorariosPer()) igual = false;
            if (registro.getFacturaRegistro().gethPerito().getLocomocionPer() != target.getFacturaRegistro().gethPerito().getLocomocionPer()) igual = false;
            if (registro.getFacturaRegistro().gethPerito().getAjustesPer() != target.getFacturaRegistro().gethPerito().getAjustesPer()) igual = false;
            if (registro.getFacturaRegistro().gethPerito().getIvaPer() != target.getFacturaRegistro().gethPerito().getIvaPer()) igual = false;
            if (registro.getFacturaRegistro().gethPerito().getIrpfPer() != target.getFacturaRegistro().gethPerito().getIrpfPer()) igual = false;
           if (!registro.getObservaciones().equals(target.getObservaciones())) igual = false;
        }
        if(registro.getEstadoEntrega().equals("ENTREGADO")){
            if (registro.getFacturaRegistro().getMesFactPerito() != target.getFacturaRegistro().getMesFactPerito()) igual = false;
            if (registro.getFacturaRegistro().getAnoFactPerito() != target.getFacturaRegistro().getAnoFactPerito()) igual = false;
        }
        return igual;
    }

    /*
     * Metodo que carga en los componentes de la ventana los datos del Registro que se le pasa por parametro
     * al PRegistro (Panel de la ventana InterfazRegistro).
     */
    public void chargeOpenRegistro(){
        //Asignacion de propiedades de objeto
        codReg = registro.getCodRegistro();
        temporal = registro;
        fechaApert.setText(new SimpleDateFormat("dd/MM/yyyy").format(registro.getFechaApertura()));
        estadoReg.setText(registro.getEstadoRegistro());
        labTRegistro.setText("REGISTRO -- CÓDIGO "+codReg+"-"+anoReg);

        //Seleccion del perito
        for (Perito per : DataTables.getPeritos()){
            if (per.getCodPerito() == registro.getPerito()){
                boxPerito.setSelectedItem(per.getNombrePerito());
                break;
            }
        }

        //Label Fecha apertura
        fechaApert.setText(new SimpleDateFormat("dd/MM/yyyy").format(registro.getFechaApertura()));

        //Estado
        estadoReg.setText(registro.getEstadoRegistro());

        //Compania - Ramo
        for (Compania com : DataTables.getCompanias()){
            if (com.getCodCompania() == registro.getSiniestro().getCompania()){
                boxCompania.setSelectedItem(com.getNombreCompania());
                break;
            }
        }
        for (RamoCompania ram : DataTables.getRamos()){
            if (ram.getCodRamo() == registro.getSiniestro().getRamo()){
                boxRamo.setSelectedItem(ram.getDescripcionRamo());
                break;
            }
        }
        //Tipo Siniestro
        for(TipoSiniestro tipS : DataTables.getTipoSiniestros()){
            if(tipS.getCodTipoSiniestro() == registro.getSiniestro().getTipoSiniestro()){
                boxTipoSin.setSelectedItem(tipS.getDescripTipoSiniestro());
                break;
            }
        }

        //Tipo Intervencion
        for(TipoIntervencion tipI : DataTables.getTipoInt()){
            if(tipI.getCodTipoInt() == registro.getTipoInt()){
               boxTipoInt.setSelectedItem(tipI.getTipoIntervencion());
               break;
            }
        }

        //Siniestro
        textRefCia.setText(registro.getReferenciaCom());
        textNPoliza.setText(registro.getSiniestro().getNumPoliza());
        textRContacto.setText(registro.getContactoReg());

        //Asegurado
        textAsegurado.setText(registro.getSiniestro().getAsegurado().getNombreAseg());
        textDirCont.setText(registro.getSiniestro().getAsegurado().getDireccionContacto());
        textDirRies.setText(registro.getSiniestro().getAsegurado().getDireccionRiesgo());
        textSContacto.setText(registro.getSiniestro().getAsegurado().getContactoAseg());
        temporal.getSiniestro().getAsegurado().setTelefonosAseg(registro.getSiniestro().getAsegurado().getTelefonosAseg());
        ArrayList<TelefonoAsegurado> telfA = registro.getSiniestro().getAsegurado().getTelefonosAseg();
        listaTelf.addAll(telfA);
        TelfData tdata = new TelfData(1, listaTelf);
        TelfData tdata1 = new TelfData(2, getTelfReg());
        telfAseg = new ArrayList<TelefonoAsegurado>();
        telfAseg.addAll(tdata1.getTelfAseg());
        telfA = null;
        actTablaTelefonos();
        areaDescrip.setText(registro.getDescripcionReg());

        //Facturacion
        //Gabinete
        textNFactura.setText(registro.getFacturaRegistro().getNumFactura());
        textGHonor.setText(Double.toString(registro.getFacturaRegistro().gethGabinete().getHonorarioGab()));
        textGLocom.setText(Double.toString(registro.getFacturaRegistro().gethGabinete().getLocomocionGab()));
        textGVarios.setText(Double.toString(registro.getFacturaRegistro().gethGabinete().getVariosGab()));
        textGSuma.setText(Double.toString(registro.getFacturaRegistro().gethGabinete().getSumaGab()));
        textGIva.setText(Double.toString(registro.getFacturaRegistro().gethGabinete().getIvaGab()));
        textGTotal.setText(Double.toString(registro.getFacturaRegistro().gethGabinete().getTotalGab()));

        //Perito
        textPHonor.setText(Double.toString(registro.getFacturaRegistro().gethPerito().getHonorariosPer()));
        textPLocom.setText(Double.toString(registro.getFacturaRegistro().gethPerito().getLocomocionPer()));
        textPAjustes.setText(Double.toString(registro.getFacturaRegistro().gethPerito().getAjustesPer()));
        textPSuma.setText(Double.toString(registro.getFacturaRegistro().gethPerito().getSumaPer()));
        textPIva.setText(Double.toString(registro.getFacturaRegistro().gethPerito().getIvaPer()));
        textPIrpf.setText(Double.toString(registro.getFacturaRegistro().gethPerito().getIrpfPer()));
        textPTotal.setText(Double.toString(registro.getFacturaRegistro().gethPerito().getTotalPer()));
        areaObserv.setText(registro.getObservaciones());

        if (registro.getFacturaRegistro().getEstadoFactura().equals("IMPAGO")) boxEstadoPago.setSelectedIndex(0);
        else if (registro.getFacturaRegistro().getEstadoFactura().equals("PAGO")) boxEstadoPago.setSelectedIndex(1);

        if (registro.getEstadoRegistro().equals("NUEVO")){
            boxTipoInt.setSelectedItem("PRESENCIAL");
            entregaPerito.setEnabled(false);
            cerrarReg.setEnabled(false);
        }
        if (!registro.getEstadoRegistro().equals("NUEVO") | registro.getEstadoEntrega().equals("PENDIENTE")) entregaPerito.setBackground(Config.AUX3);

        if (registro.getEstadoRegistro().equals("ABIERTO")){
            cerrarReg.setEnabled(true);
            cerrarReg.setBackground(Config.AUX1);
            cerrarReg.setForeground(Color.WHITE);
            entregaPerito.setEnabled(true);
        }

        if (registro.getEstadoEntrega().equals("ENTREGADO")){
            entregaPerito.setBackground(Config.AUX2);
            validarFacturaPerito();
            textFechaEPer.setDate(registro.getFechaEntregaPerito());
            textFMes.setText(Integer.toString(registro.getFacturaRegistro().getMesFactPerito()));
            textFAno.setText(Integer.toString(registro.getFacturaRegistro().getAnoFactPerito()));
        }

        if (registro.getEstadoRegistro().equals("CERRADO")){
            textFCierre.setText(new SimpleDateFormat("dd/MM/yyyy").format(registro.getFechaCierre()));
            reaperturarReg.setEnabled(true);
            reaperturarReg.setBackground(Config.AUX1);
            reaperturarReg.setForeground(Color.WHITE);
            cerrarReg.setBackground(Config.COLOR4);
            for(Usuario usr : DataTables.getUsuarios()){
                if (usr.getCodUsuario() == registro.getUsuario()){
                    textUsuario.setText(usr.getNombreUsr());
                    break;
                }
            }
            blockCierreRegistro();
        }

    }

    /*
     * Metodo que de existir Reaperturas, las carga en la tabla de reaperturas asociadas a este registro.
     */
    public void chargeReaperturasReg(){
        int filas = modelReapert.getRowCount();
        for (int i = 0; i < filas; i++){
            modelReapert.removeRow(0);
        }
        ArrayList<Reapertura> reaperturas = new ArrayList<>();
        for (Reapertura r : DataTables.getReaperturas()){
            if (r.getRegistroAsociado() == registro.getCodRegistro()){
                reaperturas.add(r);
            }
        }
        for (Reapertura re : reaperturas){
            for (Registro reg : DataTables.getRegistros()){
                if (reg.getCodRegistro() == re.getReapertura()){
                    Object [] t = {reg.getCodRegistro(), new SimpleDateFormat("dd/MM/yyyy").format(reg.getFechaApertura())};
                    modelReapert.addRow(t);
                }
            }
        }
    }


    /// PROPIEDADES, ACCIONES Y VERIFICACIONES DE TELEFONOS
    /*
     * Metodo que actualiza la tabla de telefonos.
     */
    public void actTablaTelefonos(){
        int filas = modelTelf.getRowCount();
        for (int i = 0; i < filas; i++){
            modelTelf.removeRow(0);
        }
        for (TelefonoAsegurado telf : listaTelf){
            Object [] t = {telf.getTelefono()};
            modelTelf.addRow(t);
        }
        modelTelf.fireTableDataChanged();
    }

    /*
     * Metodo que devuelve la lista de telefonos asociada al regitro
     */
    public ArrayList<TelefonoAsegurado> getTelfReg(){
        return (ArrayList<TelefonoAsegurado>) registro.getSiniestro().getAsegurado().getTelefonosAseg().clone();
    }

    /*
     * Metodo que verifica una cadena que se le pasa por parametro y la filtra dando como resultado
     * un numero telefonico de 9 numeros enteros.
     */
    public boolean verificarTelf(String telf){
        boolean valid = false;
        try{
            int nuevoTelefono = Integer.parseInt(telf);
            if (nuevoTelefono > 0){
                if(telf.length() <= 9 & telf.length() >= 4){
                    valid = true;
                } else valid = false;

            }else valid = false;

        }catch (Exception ex){
            valid = false;
        }
        return valid;
    }

    /*
     * Metodo que verifica si el numero telefonico pasado por parametro esta repetido en el Registro.
     */
    public boolean duplicatedTelf(String telf){
        boolean valid = true;
        try {
            int nuevoTelefono = Integer.parseInt(telf);
            for (TelefonoAsegurado tt : listaTelf){
                if (tt.getTelefono() == nuevoTelefono){
                    valid = false;
                }
            }
        }catch (Exception e){
            valid = false;
        }
        return valid;
    }

    /*
     * Metodo que actualiza dinamicamente los campos de facturacion del Gabinete.
     */
    public void actTotalFacturaGabinete(){

        try {
            double sumaSubTotal = 0;
            double sumaTotal = 0;
            double importeIva = 0;
            sumaSubTotal = Double.parseDouble(verificarImportes(textGHonor, textGHonor.getText()));
            sumaSubTotal += Double.parseDouble(verificarImportes(textGLocom, textGLocom.getText()));
            sumaSubTotal += Double.parseDouble(verificarImportes(textGVarios, textGVarios.getText()));
            this.textGSuma.setText(Double.toString(sumaSubTotal));

            if (sumaSubTotal > 0) {
                importeIva = (porcentajeIVA * sumaSubTotal) / 100;
                sumaTotal = sumaSubTotal + Double.parseDouble(textGIva.getText());
            }
            this.textGIva.setText(Double.toString(Math.round(importeIva*100)/100d));
            this.textGTotal.setText(Double.toString(Math.round(sumaTotal*100)/100d));

        } catch (Exception e){
            Config.error("Valor inválido en los campos de facturación (Gabinete).\n" +
                    "Debe introducir números enteros o decimales. Verifique los campos porfavor.\n");
        }

    }

    /*
     * Metodo que actualiza dinamicamente los campos de facturacion del Perito.
     */
    public void actTotalFacturaPerito(){
        try {
            double sumaSubTotal = 0;
            double sumaTotal = 0;
            double importeIva = 0;

            sumaSubTotal += Double.parseDouble(verificarImportes(textPHonor ,textPHonor.getText()));
            sumaSubTotal += Double.parseDouble(verificarImportes(textPLocom, textPLocom.getText()));
            sumaSubTotal += Double.parseDouble(verificarImportes(textPAjustes, textPAjustes.getText()));
            this.textPSuma.setText(Double.toString(sumaSubTotal));
            if (sumaSubTotal > 0) {
                importeIva = (porcentajeIVA * sumaSubTotal) / 100;
                sumaTotal = sumaSubTotal;
            }
            this.textPIva.setText(Double.toString(Math.round(importeIva * 100.00) / 100.00));
            sumaTotal += Double.parseDouble(verificarImportes(textPIva, textPIva.getText()));
            sumaTotal += Double.parseDouble(verificarImportes(textPIrpf, textPIrpf.getText()));
            this.textPTotal.setText(Double.toString(Math.round(sumaTotal*100.00)/100.00));


        } catch (Exception e){
            Config.error("Valor inválido en los campos de facturación (Perito).\n" +
                    "Debe introducir números enteros o decimales. Verifique los campos porfavor.\n");
        }
    }

    /*
     * Metodo que filtra y ordena los importes de la factura.
     */
    public String verificarImportes(JTextField component, String importe){
        String importeVerf = "";
        importe.trim();
        boolean isPoint = false;
        for (int i =0; i < importe.length(); i++){
            char let = importe.charAt(i);
            if (let == ','){
                let = '.';
                isPoint = true;
            } else if(let == '.') isPoint = true;
            importeVerf = importeVerf + let;

        }
        if(!isPoint){
            importeVerf += ".0";
        }
        component.setText(importeVerf);
        return importeVerf;
    }

    /*
     * Metodo que valida la entrega des siniestro hecha por el Perito.
     */
    public void validarFacturaPerito(){
        Date fechaEntrega = new Date();
        if (registro.getEstadoEntrega().equals("ENTREGADO")){
            fechaEntrega = textFechaEPer.getDate();
            if (fechaEntrega == null) fechaEntrega = new Date();
            int mesEntrega = Integer.parseInt(new SimpleDateFormat("MM").format(fechaEntrega));
            int anoEntrega = Integer.parseInt(new SimpleDateFormat("yy").format(fechaEntrega));

            if (textFMes.getText().isEmpty() | registro.getFacturaRegistro().getMesFactPerito() == 0){
                textFMes.setText(Integer.toString(mesEntrega));
            }else {
                try{
                    int [] meses = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
                    int mesEnt = Integer.parseInt(textFMes.getText());
                    boolean validMes = false;
                    for (int num : meses){
                        if (mesEnt == num){
                            validMes = true;
                        }
                    }
                    if(!validMes){
                        textFMes.setText(Integer.toString(mesEntrega));
                    }else{
                        mesEntrega = mesEnt;
                    }

                }catch (Exception e){
                    textFMes.setText(Integer.toString(mesEntrega));
                }
            }

            if(textFAno.getText().isEmpty() | registro.getFacturaRegistro().getMesFactPerito() == 0){
                textFAno.setText(Integer.toString(anoEntrega));
            }else {
                try {
                    String ano = textFAno.getText();
                    if (ano.length() == 2) {
                        int anoEnt = Integer.parseInt(ano);
                        if (anoEnt >= anoEntrega) {
                            anoEntrega = anoEnt;
                        }
                    } else {
                        textFAno.setText(Integer.toString(anoEntrega));
                        Config.error("El formato en el campo ano de entrega del registro es incorrecto.\n" +
                                "Se actualizará automaticamente.");
                    }

                } catch (Exception ex) {
                    textFAno.setText(Integer.toString(anoEntrega));
                    Config.error("El formato en el campo ano de entrega del registro es incorrecto.\n" +
                            "Se actualizará automaticamente.");
                }
            }
        }
    }

    /*
     * Metodo que valida el cierre del Registro
     */
    public void validarCierreRegistro(){

        //Valida estado de Entrega Perito
        boolean isValidEntrega = false;
        if (registro.getEstadoEntrega().equals("ENTREGADO")) isValidEntrega = true;
        else {
            int opt = Config.option("El Registro no ha sido entregado por el Perito,\n" +
                    "¿Desea cerrarlo de todas formas?.");
            if (opt == 0) isValidEntrega = true;
        }

        //Si es valido continua el cierre
        if (isValidEntrega) {
            registro.setFechaCierre(new Date());
            registro.setUsuario(Config.getUser().getCodUsuario());
            registro.setEstadoRegistro("CERRADO");
            cerrarRegistro();
        }
        //Si no es valido no se realiza ninguna accion.
    }

    /*
     * Metodo que cierra o cambia el estado del registro (Cerrandolo)
     */
    public void cerrarRegistro(){
        SafePacket safePacket = new SafePacket(4, Config.getUser(), Config.getIpClient());
        safePacket.setRegistro(registro);
        safePacket.setCodVentana(this.getIdInterfaz());
        Thread out = new Thread(new SentSafePacket(safePacket));
        out.start();
    }

    /*
     * Metodo que bloquea algunos campos de texto si el registro se encuentra CERRADO
     */
    public void blockCierreRegistro(){
        textRefCia.setEditable(false);
        textNPoliza.setEditable(false);
        textRContacto.setEditable(false);
        textAsegurado.setEditable(false);
        textDirRies.setEditable(false);
        textDirCont.setEditable(false);
        textSContacto.setEditable(false);
        anadirTelefono.setEnabled(false);
        modifTelfono.setEnabled(false);
        elimTelefono.setEnabled(false);
        verificar.setEnabled(false);
        cerrarReg.setEnabled(false);
    }

    /*
     * Metodo que limita el campo de texto pasado por parametro a la cantidad indicada en el parametro limite.
     * Si se supera el limite, cualquier caracter introducido sera eliminado
     */
    public void limitarCaracteres(JTextField campo, int limite){
        campo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (campo.getText().length() >= limite){
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    /*
     * Metodo que selecciona el texto dentro del campo pasado por parametro cuando gana el foco
     */
    public void seleccionarTexto(JTextField campo){
        campo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                campo.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }

    /*
     * Metodo que selecciona el texto dentro del campo pasado por parametro cuando gana el foco.
     * Verifica (cuando pierde el foco el campo pasado por parametro) si los caracteres introducidos son decimales, de lo contrario arroja un error.
     * Actualiza los campos de facturacion al perder el foco el campo.
     */
    public void seleccionarTextoNumerico(JTextField campo){
        campo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                campo.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {

                try {
                    String texto = campo.getText();
                    Double.parseDouble(texto);
                    actTotalFacturaGabinete();
                    actTotalFacturaPerito();

                }catch (Exception ex){
                    campo.setText("0.0");
                    Config.error("Se ha introducido un caracter inválido en unos de los campos de Facturación.\nIntroduzca un número decimal en el campo.");
                }


            }
        });
    }

    /*
     * Metodo que pinta el panel del color indicado.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Config.AUX4);
        g.fillRect(5, 40, 2000, (Config.alturaPantalla/2) - 40);
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


    //GUETTERS Y SETTERS
    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public ArrayList<TelefonoAsegurado> getListaTelf() {
        return listaTelf;
    }

    public void setListaTelf(ArrayList<TelefonoAsegurado> listaTelf) {
        this.listaTelf = listaTelf;
    }

    public int getIdInterfaz() {
        return idInterfaz;
    }

    public void setIdInterfaz(int idInterfaz) {
        this.idInterfaz = idInterfaz;
    }
}
