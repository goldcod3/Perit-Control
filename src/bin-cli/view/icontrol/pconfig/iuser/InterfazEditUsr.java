package interfaces.icontrol.pconfig.iuser;


import clases.TipoSiniestro;
import clases.Usuario;
import conexion.DataTables;
import conexion.hilos.SendConfigPacket;
import conexion.paquetes.ConfigPacket;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class InterfazEditUsr extends JFrame {

    public InterfazEditUsr(int cod, Usuario u){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PC - Usuario");
        setMinimumSize(new Dimension(300,300));
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
                Config.pEditUsr = null;
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
        PEditUsr panel = new PEditUsr(cod, u);
        add(panel, BorderLayout.CENTER);
        setVisible(true);

    }

}

class PEditUsr extends JPanel{

    public Usuario usuario = new Usuario();
    public JLabel labTitulo, labNombreU, labPassUsr, labEstadoU, labTipoUsr;
    public JTextField textNombreU, textPassUsr;
    public JComboBox estado, tipo;
    public JButton guardar;
    public int codigo = 0;

    public PEditUsr(int cod, Usuario usr){
        codigo = cod;
        usuario.setCodUsuario(usr.getCodUsuario());
        usuario.setNombreUsr(usr.getNombreUsr());
        usuario.setPassUsr(usr.getPassUsr());
        usuario.setEstadoUsr(usr.getEstadoUsr());
        usuario.setTipoUsr(usr.getTipoUsr());
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
        GridBagConstraints grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,0,0);
        grid.insets = new Insets(20,10,10,0);
        grid.gridwidth = 2;
        grid.weightx = 0.1;

        add(labTitulo, grid);

        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.BOTH,0,1);
        grid.insets = new Insets(0,10,10,0);
        add(labNombreU, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,1);
        grid.insets = new Insets(0,10,10,0);
        grid.weighty = 0.05;
        add(textNombreU, grid);

        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.BOTH,0,2);
        grid.insets = new Insets(0,10,10,0);
        add(labPassUsr, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,2);
        grid.insets = new Insets(0,10,10,0);
        grid.weighty = 0.05;
        add(textPassUsr, grid);

        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.BOTH,0,4);
        grid.insets = new Insets(0,10,10,0);
        add(labTipoUsr, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,4);
        grid.insets = new Insets(0,10,10,0);
        add(tipo, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,5);
        grid.insets = new Insets(10,10,10,0);
        add(guardar, grid);

        if (cod == 12){
            grid = Config.newPosicion(GridBagConstraints.EAST,
                    GridBagConstraints.BOTH,0,3);
            grid.insets = new Insets(0,10,10,0);
            add(labEstadoU, grid);

            grid = Config.newPosicion(GridBagConstraints.WEST,
                    GridBagConstraints.BOTH,1,3);
            grid.insets = new Insets(0,10,10,0);
            add(estado, grid);

        }
    }

    private void initComponents() {
        if (codigo == 5) labTitulo = new JLabel("Añadir Usuario:");
        else labTitulo = new JLabel("Editar Usuario:");
        labTitulo.setFont(Config.FUENTE14);

        labNombreU = new JLabel("Nombre: ");
        labPassUsr = new JLabel("Contraseña:");
        labEstadoU = new JLabel("Estado: ");
        labTipoUsr = new JLabel("Tipo:");

        textNombreU = new JTextField(usuario.getNombreUsr());
        textNombreU.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textNombreU.getText().length() >= 100){
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
        textPassUsr = new JTextField(usuario.getPassUsr());
        textPassUsr.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textPassUsr.getText().length() >= 100){
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

        String [] est = {"ACTIVO", "INACTIVO"};
        estado = new JComboBox(est);
        estado.setSelectedItem(usuario.getEstadoUsr());

        String [] tip = {"COMUN", "ADMIN"};
        tipo = new JComboBox(tip);
        tipo.setSelectedItem(usuario.getTipoUsr());

        guardar = new JButton("Guardar");
        guardar.setBackground(Config.COLOR4);
        guardar.setForeground(Color.WHITE);
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String nombre = textNombreU.getText().toUpperCase(Locale.ROOT);
                String pass = textPassUsr.getText();
                Usuario temp = new Usuario();
                for(Usuario u : DataTables.getUsuarios()) if (u.getNombreUsr().equalsIgnoreCase(nombre)) isValid = false;
                if (!usuario.getEstadoUsr().equals((String) estado.getSelectedItem())) isValid = true;
                if (!usuario.getTipoUsr().equals((String) tipo.getSelectedItem())) isValid = true;
                if (textNombreU.getText().length() <= 0) isValid = false;
                if (textPassUsr.getText().length() <= 0) isValid = false;
                if (!textPassUsr.getText().equals("-")) isValid = true;
                usuario.setNombreUsr(nombre.toUpperCase(Locale.ROOT));
                usuario.setPassUsr(pass);
                if (isValid) {
                    ConfigPacket conPacket = new ConfigPacket(Config.configuracion.getIpClient(), Config.getUser());
                    conPacket.setCodPacket(11);
                    usuario.setTipoUsr((String) tipo.getSelectedItem());
                    conPacket.setUsuario(usuario);
                    if (codigo == 12){
                        conPacket.getUsuario().setEstadoUsr((String) estado.getSelectedItem());
                        conPacket.setCodPacket(12);
                    }
                    SendConfigPacket out = new SendConfigPacket();
                    out.packet = conPacket;
                    Thread hiloAddCom = new Thread(out);
                    hiloAddCom.start();
                } else Config.warning("Este Usuario ya existe, intente nuevamente.");

            }
        });



    }

}
