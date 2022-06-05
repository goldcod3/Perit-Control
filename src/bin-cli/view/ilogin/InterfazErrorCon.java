package interfaces.ilogin;

import clases.ConfigCli;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazErrorCon extends JFrame{

    public InterfazErrorCon(){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("PeritControl - Verificar Usuario");
        setMinimumSize(new Dimension(300,250));
        setLocationRelativeTo(null);
        setResizable(false);

        //LAYOUT
        setLayout(new BorderLayout());

        //AÑADO PANELES
        PErrorCom panel = new PErrorCom();
        add(panel, BorderLayout.CENTER);
        setVisible(true);

    }



}

class PErrorCom extends JPanel{

    public JLabel labConexion, labIpClient, labIpSrv, labPort;
    public JTextField textIpCli, textIpSrv, textPort;
    public JButton guardar;


    public PErrorCom(){
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10,10,10,10));

        initComponents();
        getConfig();

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
        GridBagConstraints grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,0,0);
        grid.insets = new Insets(20,10,10,0);
        grid.gridwidth = 2;
        grid.weightx = 0.1;
        grid.weighty = 0.05;
        add(labConexion, grid);

        //LABEL IPCLI
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,0,3);
        grid.weighty = 0.02;
        add(labIpClient, grid);

        //LABEL IPSRV
        grid.weighty = 0.02;
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,4);
        add(labIpSrv, grid);

        //LABEL PUERTO
        grid.weighty = 0.02;
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,5);
        add(labPort, grid);

        //CAMPO IPCLI
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,3);
        grid.insets = new Insets(0,10,5,20);
        add(textIpCli, grid);

        //CAMPO IPSRV
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,4);
        grid.insets = new Insets(0,10,5,20);
        grid.weightx = 0.1;
        add(textIpSrv, grid);

        //CAMPO CONTRASENA
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,5);
        grid.insets = new Insets(0,10,5,20);
        add(textPort, grid);

        //BOTON VERIFICAR
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,6);
        grid.gridwidth = 2;
        grid.weighty = 0.05;
        add(guardar, grid);

    }

    private void initComponents() {
        //LABEL VERIFICAR
        labConexion = new JLabel("Configuración Srv.");
        labConexion.setFont(Config.FUENTETITULO);

        //LABEL TEXTO
        labIpClient = new JLabel("IP Cliente:");

        //LABEL USUARIO
        labIpSrv = new JLabel("IP Servidor:");

        //LABEL CONTRASENA
        labPort = new JLabel("Puerto:");

        //CAMPO USUARIO
        textIpCli = new JTextField();
        textIpSrv = new JTextField();

        //CAMPO CONTRASENA
        textPort = new JTextField();

        //BOTON VERIFICAR
        guardar = new JButton("Guardar");
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipCli, ipSrv, port;
                ipCli = textIpCli.getText();
                ipSrv = textIpSrv.getText();
                port = textPort.getText();
                ConfigCli c = new ConfigCli(ipCli, ipSrv, port);
                c.configFechas();
                boolean change = ConfigCli.setConfig(c);
                if (change){
                    Config.warning("Se ha cambiado la configuración del Servidor.\n" +
                            "Inicie nuevamente la aplicación.");
                    System.exit(0);
                } else Config.warning("No se ha realizado ningun cambio.");

            }
        });


    }

    public void getConfig(){
        ConfigCli c = ConfigCli.getConfig();
        textIpCli.setText(c.getIpClient());
        textIpSrv.setText(c.getIpServer());
        textPort.setText(c.getPortServer());
    }

}



