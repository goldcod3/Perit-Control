package interfaces.icontrol.pconfig.icompania;


import clases.Compania;
import conexion.DataTables;
import conexion.hilos.SendConfigPacket;
import conexion.paquetes.ConfigPacket;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class InterfazEditCompania extends JFrame {

    public InterfazEditCompania(int cod, Compania com){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PC - Compañia");
        setMinimumSize(new Dimension(300,230));
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
                Config.pEditCompania = null;
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
        PEditCompania panel = new PEditCompania(cod, com);
        add(panel, BorderLayout.CENTER);
        setVisible(true);

    }

}

class PEditCompania extends JPanel{

    public Compania compania = new Compania();
    public JLabel labTitulo, labNombreCom, labEstadoCom;
    public JTextField textNombreCom;
    public JComboBox estado;
    public JButton guardar;
    public int codigo = 0;

    public PEditCompania(int cod, Compania com){
        codigo = cod;
        compania.setCodCompania(com.getCodCompania());
        compania.setNombreCompania(com.getNombreCompania());
        compania.setEstadoCompania(com.getEstadoCompania());
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
        add(labNombreCom, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,1);
        grid.insets = new Insets(0,10,10,0);
        grid.weighty = 0.05;
        add(textNombreCom, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,3);
        grid.insets = new Insets(10,10,10,0);
        add(guardar, grid);

        if (cod == 2){
            grid = Config.newPosicion(GridBagConstraints.EAST,
                    GridBagConstraints.BOTH,0,2);
            grid.insets = new Insets(0,10,10,0);
            add(labEstadoCom, grid);

            grid = Config.newPosicion(GridBagConstraints.WEST,
                    GridBagConstraints.BOTH,1,2);
            grid.insets = new Insets(0,10,10,0);
            add(estado, grid);


            textNombreCom.setText(compania.getNombreCompania());
            estado.setSelectedItem(compania.getEstadoCompania());
        }
    }

    private void initComponents() {
        if (codigo == 1) labTitulo = new JLabel("Añadir Compañia:");
        else labTitulo = new JLabel("Editar Compañia:");
        labTitulo.setFont(Config.FUENTE14);

        labNombreCom = new JLabel("Nombre: ");
        labEstadoCom = new JLabel("Estado: ");

        textNombreCom = new JTextField();
        textNombreCom.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textNombreCom.getText().length() >= 150){
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

        guardar = new JButton("Guardar");
        guardar.setBackground(Config.COLOR4);
        guardar.setForeground(Color.WHITE);
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String nombre = textNombreCom.getText().toUpperCase(Locale.ROOT);
                compania.setNombreCompania(nombre);
                for (Compania c : DataTables.getCompanias()) if (c.getNombreCompania().equalsIgnoreCase(nombre)) isValid = false;
                if (!compania.getEstadoCompania().equals((String) estado.getSelectedItem())) isValid = true;
                if (textNombreCom.getText().length() <= 0) isValid = false;
                if (isValid) {
                    ConfigPacket conPacket = new ConfigPacket(Config.configuracion.getIpClient(), Config.getUser());
                    conPacket.setCodPacket(1);
                    conPacket.setCompania(compania);
                    if (codigo == 2){
                        conPacket.getCompania().setEstadoCompania((String) estado.getSelectedItem());
                        conPacket.setCodPacket(2);
                    }
                    SendConfigPacket out = new SendConfigPacket();
                    out.packet = conPacket;
                    Thread hiloAddCom = new Thread(out);
                    hiloAddCom.start();
                } else Config.warning("Esta compañia ya existe, intente nuevamente.");
            }
        });
    }

}
