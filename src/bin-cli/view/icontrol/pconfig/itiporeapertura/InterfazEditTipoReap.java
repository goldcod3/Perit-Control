package interfaces.icontrol.pconfig.itiporeapertura;


import clases.TipoReapertura;
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

public class InterfazEditTipoReap extends JFrame {

    public InterfazEditTipoReap(int cod, TipoReapertura tr){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PC - Tipo Rapertura");
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
                Config.pEditTReap = null;
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
        PEditTipoReap panel = new PEditTipoReap(cod, tr);
        add(panel, BorderLayout.CENTER);
        setVisible(true);

    }

}

class PEditTipoReap extends JPanel{

    public TipoReapertura tipoReapertura = new TipoReapertura();
    public JLabel labTitulo, labNombreTR, labEstadoTR;
    public JTextField textNombreTR;
    public JComboBox estado;
    public JButton guardar;
    public int codigo = 0;

    public PEditTipoReap(int cod, TipoReapertura tipoR){
        codigo = cod;
        tipoReapertura.setCodTipoReapertura(tipoR.getCodTipoReapertura());
        tipoReapertura.setTipoReapertura(tipoR.getTipoReapertura());
        tipoReapertura.setEstadoTipoReapertura(tipoR.getEstadoTipoReapertura());
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
        add(labNombreTR, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,1);
        grid.insets = new Insets(0,10,10,0);
        grid.weighty = 0.05;
        add(textNombreTR, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,3);
        grid.insets = new Insets(10,10,10,0);
        add(guardar, grid);

        if (cod == 10){
            grid = Config.newPosicion(GridBagConstraints.EAST,
                    GridBagConstraints.BOTH,0,2);
            grid.insets = new Insets(0,10,10,0);
            add(labEstadoTR, grid);

            grid = Config.newPosicion(GridBagConstraints.WEST,
                    GridBagConstraints.BOTH,1,2);
            grid.insets = new Insets(0,10,10,0);
            add(estado, grid);



        }
    }

    private void initComponents() {
        if (codigo == 9) labTitulo = new JLabel("Añadir Tipo Reapertura:");
        else labTitulo = new JLabel("Editar Tipo Reapertura:");
        labTitulo.setFont(Config.FUENTE14);

        labNombreTR = new JLabel("Nombre: ");
        labEstadoTR = new JLabel("Estado: ");

        textNombreTR = new JTextField(tipoReapertura.getTipoReapertura());
        textNombreTR.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textNombreTR.getText().length() >= 150){
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
        estado.setSelectedItem(tipoReapertura.getEstadoTipoReapertura());

        guardar = new JButton("Guardar");
        guardar.setBackground(Config.COLOR4);
        guardar.setForeground(Color.WHITE);
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String nombre = textNombreTR.getText().toUpperCase(Locale.ROOT);
                tipoReapertura.setTipoReapertura(nombre);
                for(TipoReapertura tr : DataTables.getTipoReaperturas()) if (tr.getTipoReapertura().equalsIgnoreCase(nombre)) isValid = false;
                if (!tipoReapertura.getEstadoTipoReapertura().equals((String) estado.getSelectedItem())) isValid = true;
                if (textNombreTR.getText().length() <= 0) isValid = false;
                if (isValid) {
                    ConfigPacket conPacket = new ConfigPacket(Config.configuracion.getIpClient(), Config.getUser());
                    conPacket.setCodPacket(9);
                    conPacket.setTipoReapertura(tipoReapertura);
                    if (codigo == 10){
                        conPacket.getTipoReapertura().setEstadoTipoReapertura((String) estado.getSelectedItem());
                        conPacket.setCodPacket(10);
                    }
                    SendConfigPacket out = new SendConfigPacket();
                    out.packet = conPacket;
                    Thread hiloAddCom = new Thread(out);
                    hiloAddCom.start();
                } else Config.warning("Este Tipo Reapertura ya existe, intente nuevamente.");

            }
        });



    }

}
