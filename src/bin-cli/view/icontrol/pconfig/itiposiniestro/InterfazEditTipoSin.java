package interfaces.icontrol.pconfig.itiposiniestro;


import clases.Compania;
import clases.TipoSiniestro;
import conexion.DataTables;
import conexion.hilos.SendConfigPacket;
import conexion.paquetes.ConfigPacket;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class InterfazEditTipoSin extends JFrame {

    public InterfazEditTipoSin(int cod, TipoSiniestro ts){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PC - Tipo Siniestro");
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
                Config.pEditTSin = null;
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
        PEditTipoSin panel = new PEditTipoSin(cod, ts);
        add(panel, BorderLayout.CENTER);
        setVisible(true);

    }

}

class PEditTipoSin extends JPanel{

    public TipoSiniestro tipoSiniestro = new TipoSiniestro();
    public JLabel labTitulo, labNombreTS, labEstadoTS;
    public JTextField textNombreTS;
    public JComboBox estado;
    public JButton guardar;
    public int codigo = 0;

    public PEditTipoSin(int cod, TipoSiniestro tipoS){
        codigo = cod;
        tipoSiniestro.setCodTipoSiniestro(tipoS.getCodTipoSiniestro());
        tipoSiniestro.setDescripTipoSiniestro(tipoS.getDescripTipoSiniestro());
        tipoSiniestro.setEstadoTipoSin(tipoS.getEstadoTipoSin());
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
        add(labNombreTS, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,1);
        grid.insets = new Insets(0,10,10,0);
        grid.weighty = 0.05;
        add(textNombreTS, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,3);
        grid.insets = new Insets(10,10,10,0);
        add(guardar, grid);

        if (cod == 6){
            grid = Config.newPosicion(GridBagConstraints.EAST,
                    GridBagConstraints.BOTH,0,2);
            grid.insets = new Insets(0,10,10,0);
            add(labEstadoTS, grid);

            grid = Config.newPosicion(GridBagConstraints.WEST,
                    GridBagConstraints.BOTH,1,2);
            grid.insets = new Insets(0,10,10,0);
            add(estado, grid);



        }
    }

    private void initComponents() {
        if (codigo == 5) labTitulo = new JLabel("Añadir Tipo Siniestro:");
        else labTitulo = new JLabel("Editar Tipo Siniestro:");
        labTitulo.setFont(Config.FUENTE14);

        labNombreTS = new JLabel("Nombre: ");
        labEstadoTS = new JLabel("Estado: ");

        textNombreTS = new JTextField(tipoSiniestro.getDescripTipoSiniestro());
        textNombreTS.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textNombreTS.getText().length() >= 150){
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
        estado.setSelectedItem(tipoSiniestro.getEstadoTipoSin());

        guardar = new JButton("Guardar");
        guardar.setBackground(Config.COLOR4);
        guardar.setForeground(Color.WHITE);
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String nombre = textNombreTS.getText().toUpperCase(Locale.ROOT);
                tipoSiniestro.setDescripTipoSiniestro(nombre);
                for(TipoSiniestro ts : DataTables.getTipoSiniestros()) if (ts.getDescripTipoSiniestro().equalsIgnoreCase(nombre)) isValid = false;
                if (!tipoSiniestro.getEstadoTipoSin().equals((String) estado.getSelectedItem())) isValid = true;
                if (textNombreTS.getText().length() <= 0) isValid = false;
                if (isValid) {
                    ConfigPacket conPacket = new ConfigPacket(Config.configuracion.getIpClient(), Config.getUser());
                    conPacket.setCodPacket(5);
                    conPacket.setTipoSiniestro(tipoSiniestro);
                    if (codigo == 6){
                        conPacket.getTipoSiniestro().setEstadoTipoSin((String) estado.getSelectedItem());
                        conPacket.setCodPacket(6);
                    }
                    SendConfigPacket out = new SendConfigPacket();
                    out.packet = conPacket;
                    Thread hiloAddCom = new Thread(out);
                    hiloAddCom.start();
                } else Config.warning("Este Tipo Siniestro ya existe, intente nuevamente.");

            }
        });



    }

}
