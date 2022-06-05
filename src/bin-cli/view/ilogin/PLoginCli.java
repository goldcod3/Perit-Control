package interfaces.ilogin;

import clases.Usuario;
import conexion.DataTables;
import conexion.hilos.SentPacket;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PLoginCli extends JPanel {

    public JLabel labLogin, labUser, labPass, logoC, logoPC;
    public JTextField textUser;
    public JPasswordField textPass;
    public JButton login, close;
    public Thread loginPing = new Thread(new SentPacket());

    public PLoginCli() {
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        initComponents();

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
        GridBagConstraints grid = Config.newPosicion(GridBagConstraints.SOUTH,
                GridBagConstraints.HORIZONTAL, 0, 0);
        grid.insets = new Insets(20, 10, 10, 0);
        //grid.gridwidth = 2;
        grid.weightx = 0.1;
        grid.weighty = 0.05;
        add(labLogin, grid);

        //LABEL LOGO COMPANIA
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE, 0, 0);
        add(logoPC, grid);

        //LABEL LOGO COMPANIA
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 1, 0);
        grid.insets = new Insets(0, 30, 0, 0);
        grid.gridwidth = 2;
        add(logoC, grid);

        //LABEL USUARIO
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, 0, 4);
        grid.insets = new Insets(0, 20, 0, 0);
        grid.weighty = 0.03;
        add(labUser, grid);

        //LABEL CONTRASENA
        grid.weighty = 0.03;
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, 0, 5);
        grid.insets = new Insets(0, 20, 15, 0);
        add(labPass, grid);

        //CAMPO USUARIO
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 4);
        grid.insets = new Insets(0, 10, 0, 20);
        grid.weightx = 0.1;
        add(textUser, grid);

        //CAMPO CONTRASENA
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 5);
        grid.insets = new Insets(0, 10, 15, 20);
        add(textPass, grid);

        //BOTON VERIFICAR
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 0, 6);
        grid.gridwidth = 2;
        grid.weighty = 0.05;
        add(login, grid);

        //BOTON CERRAR
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 0, 7);
        grid.gridwidth = 2;
        grid.weighty = 0.05;
        add(close, grid);


    }

    private void initComponents() {
        //LABEL VERIFICAR
        labLogin = new JLabel("PeritControl");
        labLogin.setFont(Config.FUENTETITULO);

        //LABEL USUARIO
        labUser = new JLabel("Usuario:");

        //LABEL CONTRASENA
        labPass = new JLabel("Contraseña:");

        //LABEL LOGO PERIT CONTROL
        logoPC = new JLabel();
        logoPC.setIcon(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png"));

        //LABEL LOGO COMPANIA
        logoC = new JLabel();
        logoC.setIcon(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logocli.jpg"));
        logoC.setPreferredSize(new Dimension(200, 130));

        //CAMPO USUARIO
        textUser = new JTextField();
        setFocusTraversalKeysEnabled(true);
        textUser.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String usuario = textUser.getText().toUpperCase();
                    String pass = textPass.getText();

                    Usuario usr = new Usuario();
                    usr.setNombreUsr(usuario);
                    usr.setPassUsr(pass);
                    Config.setUser(usr);

                    loginPing = new Thread(new SentPacket());
                    loginPing.start();
                    DataTables.actTablasPanelControl();
                    DataTables.cleanBusqueda();

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //CAMPO CONTRASENA
        textPass = new JPasswordField();
        textPass.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String usuario = textUser.getText().toUpperCase();
                    String pass = textPass.getText();

                    Usuario usr = new Usuario();
                    usr.setNombreUsr(usuario);
                    usr.setPassUsr(pass);
                    Config.setUser(usr);

                    loginPing = new Thread(new SentPacket());
                    loginPing.start();
                    login.setBackground(Config.AUX2);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //BOTON LOGIN
        login = new JButton("Login");
        login.setBackground(Config.AUX5);
        login.setForeground(Color.WHITE);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textUser.getText().toUpperCase();
                String pass = textPass.getText();

                Usuario usr = new Usuario();
                usr.setNombreUsr(usuario);
                usr.setPassUsr(pass);
                Config.setUser(usr);

                loginPing = new Thread(new SentPacket());
                loginPing.start();
                login.setBackground(Config.AUX2);

            }
        });
        login.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String usuario = textUser.getText().toUpperCase();
                    String pass = textPass.getText();

                    Usuario usr = new Usuario();
                    usr.setNombreUsr(usuario);
                    usr.setPassUsr(pass);
                    Config.setUser(usr);

                    loginPing = new Thread(new SentPacket());
                    loginPing.start();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //BOTON CERRAR
        close = new JButton("Cerrar");
        close.setBackground(Config.AUX1);
        close.setForeground(Color.WHITE);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


    }

}
