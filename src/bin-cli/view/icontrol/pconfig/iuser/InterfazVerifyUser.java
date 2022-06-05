package interfaces.icontrol.pconfig.iuser;

import clases.Usuario;
import conexion.hilos.SendVerifyPacket;
import conexion.paquetes.VerifyPacket;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class InterfazVerifyUser extends JFrame {

    public InterfazVerifyUser(){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PC - Verificar Usuario");
        setMinimumSize(new Dimension(300,250));
        setLocationRelativeTo(null);
        setResizable(false);

        //LAYOUT
        setLayout(new BorderLayout());

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                Config.pVerifyUsr = null;
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

        //AÑADO PANELES
        PVerifyUser panel = new PVerifyUser();
        add(panel, BorderLayout.CENTER);
        setVisible(true);

    }



}

class PVerifyUser extends JPanel{

    public JLabel labVerify, labTexto, labUser, labPass, imgVerificar;
    public JTextField textUser;
    public JPasswordField textPass;
    public JButton verificar;


    public PVerifyUser(){
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10,10,10,10));

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
        GridBagConstraints grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,0,0);
        grid.insets = new Insets(20,10,10,0);
        grid.gridwidth = 2;
        grid.weightx = 0.1;
        grid.weighty = 0.05;
        add(labVerify, grid);

        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE,1,0);
        grid.insets = new Insets(0,10,10,0);
        add(imgVerificar, grid);

        //LABEL TEXTO
        grid = Config.newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,0,2);
        grid.weighty = 0.05;
        grid.gridwidth = 2;
        grid.gridheight = 2;
        add(labTexto, grid);

        //LABEL USUARIO
        grid.weighty = 0.02;
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,4);
        add(labUser, grid);

        //LABEL CONTRASENA
        grid.weighty = 0.02;
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,5);
        add(labPass, grid);

        //CAMPO USUARIO
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,4);
        grid.insets = new Insets(0,10,5,20);
        grid.weightx = 0.1;
        add(textUser, grid);

        //CAMPO CONTRASENA
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,5);
        grid.insets = new Insets(0,10,5,20);
        add(textPass, grid);

        //BOTON VERIFICAR
        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,6);
        grid.gridwidth = 2;
        grid.weighty = 0.05;
        add(verificar, grid);

    }

    private void initComponents() {
        //LABEL VERIFICAR
        labVerify = new JLabel("VERIFICACIÓN");
        labVerify.setFont(Config.FUENTETITULO);

        //IMAGEN
        imgVerificar = new JLabel();
        imgVerificar.setIcon( new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\verificar.png"));

        //LABEL TEXTO
        labTexto = new JLabel("Ingrese su Usuario y Contraseña.");

        //LABEL USUARIO
        labUser = new JLabel("Usuario:");

        //LABEL CONTRASENA
        labPass = new JLabel("Contraseña:");

        //CAMPO USUARIO
        textUser = new JTextField();

        //CAMPO CONTRASENA
        textPass = new JPasswordField();
        textPass.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    String user = textUser.getText();
                    String pass = textPass.getText();

                    Usuario usr = new Usuario();
                    usr.setNombreUsr(user.toUpperCase());
                    usr.setPassUsr(pass);
                    Config.setUserVerify(usr);

                    Thread t = new Thread( new SendVerifyPacket());
                    t.start();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //BOTON VERIFICAR
        verificar = new JButton("Verificar");
        verificar.setBackground(Config.AUX4);
        verificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = textUser.getText();
                String pass = textPass.getText();

                Usuario usr = new Usuario();
                usr.setNombreUsr(user.toUpperCase());
                usr.setPassUsr(pass);
                Config.setUserVerify(usr);

                Thread t = new Thread( new SendVerifyPacket());
                t.start();


            }
        });
        verificar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    String user = textUser.getText();
                    String pass = textPass.getText();

                    Usuario usr = new Usuario();
                    usr.setNombreUsr(user.toUpperCase());
                    usr.setPassUsr(pass);
                    Config.setUserVerify(usr);

                    Thread t = new Thread( new SendVerifyPacket());
                    t.start();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


    }

    public void verifyUsr(){
        String user = textUser.getText().trim();
        String pass = textPass.getText().trim();



    }

}