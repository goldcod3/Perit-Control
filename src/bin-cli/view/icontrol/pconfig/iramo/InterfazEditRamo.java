package interfaces.icontrol.pconfig.iramo;


import clases.Compania;
import clases.RamoCompania;
import conexion.DataTables;
import conexion.hilos.SendConfigPacket;
import conexion.paquetes.ConfigPacket;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class InterfazEditRamo extends JFrame {

    public InterfazEditRamo(int cod, RamoCompania ram){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PC - Ramo Compañia");
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
                Config.pEditRamo = null;
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
        PEditRamo panel = new PEditRamo(cod, ram);
        add(panel, BorderLayout.CENTER);
        setVisible(true);

    }

}

class PEditRamo extends JPanel{

    public RamoCompania ramo = new RamoCompania();
    public JLabel labTitulo, labNombreRam, labEstadoRam;
    public JTextField textNombreRam;
    public JComboBox estado;
    public JButton guardar;
    public int codigo = 0;

    public PEditRamo(int cod, RamoCompania ram){
        codigo = cod;
        ramo.setCodRamo(ram.getCodRamo());
        ramo.setDescripcionRamo(ram.getDescripcionRamo());
        ramo.setCompania(ram.getCompania());
        ramo.setEstadoRam(ram.getEstadoRam());
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
        add(labNombreRam, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,1);
        grid.insets = new Insets(0,10,10,0);
        grid.weighty = 0.05;
        add(textNombreRam, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,3);
        grid.insets = new Insets(10,10,10,0);
        add(guardar, grid);

        if (cod == 4){
            grid = Config.newPosicion(GridBagConstraints.EAST,
                    GridBagConstraints.BOTH,0,2);
            grid.insets = new Insets(0,10,10,0);
            add(labEstadoRam, grid);

            grid = Config.newPosicion(GridBagConstraints.WEST,
                    GridBagConstraints.BOTH,1,2);
            grid.insets = new Insets(0,10,10,0);
            add(estado, grid);


            textNombreRam.setText(ramo.getDescripcionRamo());
            estado.setSelectedItem(ramo.getEstadoRam());
        }
    }

    private void initComponents() {
        if (codigo == 3) labTitulo = new JLabel("Añadir Ramo:");
        else labTitulo = new JLabel("Editar Ramo:");
        labTitulo.setFont(Config.FUENTE14);

        labNombreRam = new JLabel("Nombre: ");
        labEstadoRam = new JLabel("Estado: ");

        textNombreRam = new JTextField();
        textNombreRam.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textNombreRam.getText().length() >= 150){
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
                String nombre = textNombreRam.getText().toUpperCase(Locale.ROOT);
                ramo.setDescripcionRamo(nombre.toUpperCase(Locale.ROOT));
                for (Compania c : DataTables.getCompanias()) if (c.getNombreCompania().equalsIgnoreCase(nombre)) isValid = false;
                if (!ramo.getEstadoRam().equals((String) estado.getSelectedItem())) isValid = true;
                if (textNombreRam.getText().length() <= 0) isValid = false;
                if (isValid) {
                    ConfigPacket conPacket = new ConfigPacket(Config.configuracion.getIpClient(), Config.getUser());
                    conPacket.setCodPacket(3);
                    conPacket.setRamo(ramo);
                    if (codigo == 4){
                        conPacket.getRamo().setEstadoRam((String) estado.getSelectedItem());
                        System.out.println(estado.getSelectedItem());
                        conPacket.setCodPacket(4);
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
