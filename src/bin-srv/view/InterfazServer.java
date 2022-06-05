package interfaces;

import clases.ConfigCon;
import conexion.ConnectionPool;
import conexion.DataCharge;
import conexion.DataTables;
import conexion.Service;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazServer extends JFrame{

    public static boolean estadoServicio = false; //ver limpiar historial
    public  JButton iniciar, detener, guardar, limpiar;
    public JLabel configLab, historialLab, ipLab, portLab, userLab, passLab;
    public JTextField ipBox, portBox, userBox;
    public JPasswordField passBox;
    public JTextArea historialPanel = new JTextArea();


    public InterfazServer(){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCSrv\\ext\\logoT.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("PeritControl - SERVER");
        setMinimumSize(new Dimension(800, 600));
        setResizable(false);

        //PANEL BASE
        JPanel contenedor = new JPanel();
        contenedor.setBorder(new EmptyBorder(10,10,10,10));
        contenedor.setBackground(Color.GRAY);
        setContentPane(contenedor);

        initComponents();

        //LAYOUT
        GridBagLayout gbl_contenedor = new GridBagLayout();
        gbl_contenedor.columnWidths = new int[]{0};
        gbl_contenedor.rowHeights = new int[]{0};
        gbl_contenedor.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_contenedor.rowWeights = new double[]{Double.MIN_VALUE};
        contenedor.setLayout(gbl_contenedor);

        //COMPONENTES //////////////////////////////////////////////////////////////////////////////////////////////////

        //BOTON INICIAR
        GridBagConstraints gridIniciar = InterfazServer.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,0,0);
        gridIniciar.insets = new Insets(0,0,0,10);
        gridIniciar.weightx = 0.2;
        contenedor.add(iniciar,gridIniciar);

        //BOTON DETENER
        GridBagConstraints gridDetener = InterfazServer.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,1,0);
        gridDetener.insets = new Insets(0,0,0,0);
        gridDetener.weightx = 0.14;
        contenedor.add(detener,gridDetener);

        //LABEL CONFIG
        GridBagConstraints gridConfLab = InterfazServer.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,0,1);
        gridConfLab.insets = new Insets(50,0,20,0);
        contenedor.add(configLab,gridConfLab);

        //LABEL IP
        GridBagConstraints gridIPLab = InterfazServer.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,0,2);
        contenedor.add(ipLab,gridIPLab);

        //LABEL PUERTO
        GridBagConstraints gridPortLab = InterfazServer.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,1,2);
        contenedor.add(portLab,gridPortLab);

        //BOX IP
        GridBagConstraints gridIPBox = InterfazServer.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,3);
        gridIPBox.insets = new Insets(5,0,0,10);
        contenedor.add(ipBox,gridIPBox);

        //BOX PORT
        GridBagConstraints gridPortBox = InterfazServer.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,3);
        gridPortBox.insets = new Insets(5,0,0,0);
        contenedor.add(portBox,gridPortBox);

        //LABEL USER
        GridBagConstraints gridUserLab = InterfazServer.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,0,4);
        gridUserLab.insets = new Insets(5,0,0,0);
        contenedor.add(userLab,gridUserLab);

        //LABEL PASS
        GridBagConstraints gridPassLab = InterfazServer.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,4);
        gridPassLab.insets = new Insets(5,0,0,0);
        contenedor.add(passLab,gridPassLab);

        //BOX USER
        GridBagConstraints gridUserBox = InterfazServer.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,5);
        gridUserBox.insets = new Insets(5,0,100,10);
        contenedor.add(userBox,gridUserBox);

        //BOX PASS
        GridBagConstraints gridPassBox = InterfazServer.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,5);
        gridPassBox.insets = new Insets(5,0,100,0);
        contenedor.add(passBox,gridPassBox);

        //BOTON GUARDAR
        GridBagConstraints gridSaveBox = InterfazServer.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,1,5);
        gridSaveBox.insets = new Insets(20,0,0,0);
        contenedor.add(guardar,gridSaveBox);

        //PANEL DE NOTIFICACIONES

        JScrollPane scroll = new JScrollPane(historialPanel);
        GridBagConstraints gridHistory = InterfazServer.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,3,0);
        gridHistory.insets = new Insets(0,20,0,0);
        gridHistory.gridwidth = 3;
        gridHistory.gridheight = 6;
        gridHistory.weightx = 0.9;
        contenedor.add(scroll,gridHistory);

        //LABEL HISTORIAL
        GridBagConstraints gridHistLab = InterfazServer.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,4,7);
        gridHistLab.insets = new Insets(10,30,40,0);
        historialLab.setFont(new Font("Calibri", Font.BOLD, 18));
        contenedor.add(historialLab,gridHistLab);

        //BOTON LIMPIAR
        GridBagConstraints gridCleanBox = InterfazServer.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,5,7);
        gridCleanBox.insets = new Insets(10,100,0,0);

        contenedor.add(limpiar,gridCleanBox);

        setVisible(true);

        ConfigSrv.config = ConfigCon.getConfig();


    }

    public void initComponents(){
        iniciar = new JButton("INICIAR SERVICIO");
        detener = new JButton("DETENER SERVICIO");
        guardar = new JButton("GUARDAR CONFIG.");
        limpiar = new JButton("LIMPIAR HISTORIAL");
        guardar.setPreferredSize(new Dimension(10,60));
        limpiar.setPreferredSize(new Dimension(100,60));

        configLab = new JLabel("CONFIGURACIÓN");
        configLab.setFont(new Font("Calibri", Font.BOLD, 18));
        historialLab = new JLabel("HISTORIAL");
        ipLab = new JLabel("Dirección IP:");
        portLab = new JLabel("Puerto Cliente:");
        userLab = new JLabel("Usuario:");
        passLab = new JLabel("Contraseña");

        ipBox = new JTextField();
        portBox = new JTextField();
        userBox = new JTextField();
        passBox = new JPasswordField();
        ipBox.setPreferredSize(new Dimension(160, 25));
        portBox.setPreferredSize(new Dimension(160, 25));
        userBox.setPreferredSize(new Dimension(180, 25));
        passBox.setPreferredSize(new Dimension(180, 25));

        historialPanel.append("Bienvenido al Servidor de PeritControl.\n" +
                "Inicie el servicio porfavor.\n");
        historialPanel.append("_______________________________________________________________\n");


        guardar.setBackground(ConfigSrv.COLOR1);
        guardar.setForeground(Color.WHITE);
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigCon.setConfig(ConfigCon.getConfigCon());
            }
        });

        iniciar.setBackground(ConfigSrv.AUX2);
        iniciar.setForeground(Color.WHITE);
        iniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historialPanel.setText("");

                    //CAMBIO EL ESTADO DEL SERVICIO
                    InterfazServer.estadoServicio = true;

                    //BLOQUEO CAMPOS DE TEXTO
                    bloqText(false);

                    //BLOQUEO BOTON INICIAR
                    iniciar.setEnabled(false);

                    //DESBLOQUEO BOTON DETENER
                    detener.setEnabled(true);

                    //INICIO DEL SERVICIO
                    Thread run = new Thread(new Runnable() {
                        @Override
                        public void run() {//INSTANCIA CONEXION CON BBDD
                            ConfigSrv.panelControl.historyMsj("Iniciando Servicio...");

                            ConfigSrv.panelControl.historyMsj("Conectando a la Base de Datos...");
                            ConnectionPool.getInstance();

                            //CARGA DE DATOS EN LAS TABLAS
                            ConfigSrv.panelControl.historyMsj("Cargando Tablas de Datos...");
                            DataCharge.chargeDataTables();
                            ConfigSrv.service = new Service();
                            ConfigSrv.service.startService();
                        }
                    });
                    run.start();

            }
        });

        detener.setEnabled(false);
        detener.setBackground(ConfigSrv.AUX1);
        detener.setForeground(Color.WHITE);
        detener.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyMsj("Deteniendo Servicio...");

                //SE BLOQUEA EL BOTON DETENER
                ConfigSrv.panelControl.detener.setEnabled(false);

                //SE CIERRA EL POOL DE CONEXIONES
                ConnectionPool.closeInstance();

                //SE DETIENE EL SERVICIO
                ConfigSrv.service.closeService();

                //LIMPIEZA DE TABLAS DEL SERVIDOR
                DataCharge.cleanDataTables();

                ConnectionPool.closeInstance();

                //MENSAJE EN HISTORIAL
                historyMsj("SE HA DETENIDO EL SERVICIO.\n" +
                        "Inicie el servicio nuevamente para que los clientes logren la conexión.");

                //SE DESBLOQUEA EL BOTON INICIAR
                ConfigSrv.panelControl.iniciar.setEnabled(true);

                //SE DESBLOQUEAN LOS CAMPOS DE TEXTO
                ConfigSrv.panelControl.bloqText(true);

                System.out.println("Se ha detenido el servicio.");
                System.out.println("___________________________");
            }
        });

        limpiar.setBackground(ConfigSrv.COLOR1);
        limpiar.setForeground(Color.WHITE);
        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historialPanel.setText("");
                if (ConfigSrv.service.isStatusService()) historyMsj("Servicio en Ejecución...");
                else historyMsj("Sin Servicio.");
            }
        });

        //INICIAR CONFIGURACION

    }

    public void actConnectionsNumber(){
        if (ConfigSrv.service != null){
            ConfigSrv.panelControl.historialPanel.setText("");
            historyMsj("Total Conexiones: "+ConfigSrv.service.totalConnections);
        }
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

    /*
    Metodo que bloquea o desbloquea los campos de texto de la interfaz.
     */
    public void bloqText(boolean accion){
        ipBox.setEnabled(accion);
        portBox.setEnabled(accion);
        userBox.setEnabled(accion);
        passBox.setEnabled(accion);
    }

    public void historyMsj(String msj){

        try {
            ConfigSrv.panelControl.historialPanel.append("---" + msj + "\n");
        }catch(Exception e){
            System.out.println("ERROR");
        }
    }

}
