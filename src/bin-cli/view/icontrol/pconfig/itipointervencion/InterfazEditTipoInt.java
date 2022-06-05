package interfaces.icontrol.pconfig.itipointervencion;


import clases.TipoIntervencion;
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

public class InterfazEditTipoInt extends JFrame {

    public InterfazEditTipoInt(int cod, TipoIntervencion ti){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PC-Tipo Intervención");
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
                Config.pEditTInt = null;
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
        PEditTipoInt panel = new PEditTipoInt(cod, ti);
        add(panel, BorderLayout.CENTER);
        setVisible(true);

    }

}

class PEditTipoInt extends JPanel{

    public TipoIntervencion tipoIntervencion = new TipoIntervencion();
    public JLabel labTitulo, labNombreTI, labEstadoTI;
    public JTextField textNombreTI;
    public JComboBox estado;
    public JButton guardar;
    public int codigo = 0;

    public PEditTipoInt(int cod, TipoIntervencion tipoI){
        codigo = cod;
        tipoIntervencion.setCodTipoInt(tipoI.getCodTipoInt());
        tipoIntervencion.setTipoIntervencion(tipoI.getTipoIntervencion());
        tipoIntervencion.setEstadoIntervencion(tipoI.getEstadoIntervencion());
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
        add(labNombreTI, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,1,1);
        grid.insets = new Insets(0,10,10,0);
        grid.weighty = 0.05;
        add(textNombreTI, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH,1,3);
        grid.insets = new Insets(10,10,10,0);
        add(guardar, grid);

        if (cod == 8){
            grid = Config.newPosicion(GridBagConstraints.EAST,
                    GridBagConstraints.BOTH,0,2);
            grid.insets = new Insets(0,10,10,0);
            add(labEstadoTI, grid);

            grid = Config.newPosicion(GridBagConstraints.WEST,
                    GridBagConstraints.BOTH,1,2);
            grid.insets = new Insets(0,10,10,0);
            add(estado, grid);

        }
    }

    private void initComponents() {
        if (codigo == 7) labTitulo = new JLabel("Añadir Tipo Intervención:");
        else labTitulo = new JLabel("Editar Tipo Intervención:");
        labTitulo.setFont(Config.FUENTE14);

        labNombreTI = new JLabel("Nombre: ");
        labEstadoTI = new JLabel("Estado: ");

        textNombreTI = new JTextField(tipoIntervencion.getTipoIntervencion());
        textNombreTI.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textNombreTI.getText().length() >= 150){
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
        estado.setSelectedItem(tipoIntervencion.getEstadoIntervencion());

        guardar = new JButton("Guardar");
        guardar.setBackground(Config.COLOR4);
        guardar.setForeground(Color.WHITE);
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                String nombre = textNombreTI.getText().toUpperCase(Locale.ROOT);
                tipoIntervencion.setTipoIntervencion(nombre);
                for(TipoIntervencion ti : DataTables.getTipoInt()) if (ti.getTipoIntervencion().equalsIgnoreCase(nombre)) isValid = false;
                if (!tipoIntervencion.getEstadoIntervencion().equals((String) estado.getSelectedItem())) isValid = true;
                if (textNombreTI.getText().length() <= 0) isValid = false;
                if (isValid) {
                    ConfigPacket conPacket = new ConfigPacket(Config.configuracion.getIpClient(), Config.getUser());
                    conPacket.setCodPacket(7);
                    conPacket.setTipoIntervencion(tipoIntervencion);
                    if (codigo == 8){
                        conPacket.getTipoIntervencion().setEstadoIntervencion((String) estado.getSelectedItem());
                        conPacket.setCodPacket(8);
                    }
                    SendConfigPacket out = new SendConfigPacket();
                    out.packet = conPacket;
                    Thread hiloAddCom = new Thread(out);
                    hiloAddCom.start();
                } else Config.warning("Este Tipo de Intervención ya existe, intente nuevamente.");

            }
        });
    }
}
