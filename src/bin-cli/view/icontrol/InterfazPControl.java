package interfaces.icontrol;

import clases.Registro;
import conexion.DataTables;
import interfaces.Config;
import interfaces.icontrol.pconfig.PanelConfig;
import interfaces.icontrol.pcontrol.PanelControl;
import interfaces.icontrol.pperitos.PanelPeritos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class InterfazPControl extends JFrame{

    private static JButton btnControl, btnPeritos, btnConfig;
    private static JPanel panelBotones, contenedor;
    public PanelControl pControl;
    public PanelPeritos pPeritos;
    public PanelConfig pConfig;

    public InterfazPControl(){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("PeritControl");
        setMinimumSize(new Dimension(Config.anchoPantalla, Config.alturaPantalla));
        setLocation(-6,0);
        setResizable(true);
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                int opt = Config.option("¿Quiere cerrar el programa? No se guardarán los cambios en los Registros Abiertos.");
                if(opt == 0){
                    System.exit(0);
                }
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

        //LAYOUT
        setLayout(new BorderLayout());

        //INICIALIZO COMPONENTES
        initPanelBtn();
        initPaneles();
        contenedor.add(pControl);

        //AÑADO PANELES
        add(contenedor, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    /*
    * METODO QUE INICIALIZA EL PANEL DE BOTONES
    */
    private void initPanelBtn(){

        //INICIALIZO PANEL BOTONES
        FlowLayout fLayout = new FlowLayout(FlowLayout.CENTER,30,10);
        panelBotones = new JPanel();
        panelBotones.setBackground(Config.COLOR4);
        panelBotones.setLayout(fLayout);

        //INICIALIZO BOTONES
        btnControl = new JButton("PANEL CONTROL");
        btnControl.setBackground(Config.COLOR6);
        btnControl.setForeground(Color.WHITE);
        btnPeritos = new JButton("PERITOS");
        btnPeritos.setBackground(Config.COLOR6);
        btnPeritos.setForeground(Color.WHITE);
        btnConfig = new JButton("CONFIGURACIÓN");
        btnConfig.setBackground(Config.COLOR6);
        btnConfig.setForeground(Color.WHITE);
        btnControl.setPreferredSize(new Dimension(150,60));
        btnPeritos.setPreferredSize(new Dimension(150,60));
        btnConfig.setPreferredSize(new Dimension(150,60));

        //EVENTOS DE LOS BOTONES
        btnControl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pControl.setVisible(true);
                pPeritos.setVisible(false);
                pConfig.setVisible(false);
                Config.pControl.pConfig.estadoBotonesConfig(false);
                contenedor.add(pControl);
                contenedor.validate();
            }
        });
        btnPeritos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pControl.setVisible(false);
                pPeritos.setVisible(true);
                pConfig.setVisible(false);
                Config.pControl.pConfig.estadoBotonesConfig(false);
                contenedor.add(pPeritos);
                contenedor.validate();
            }
        });
        btnConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pControl.setVisible(false);
                pPeritos.setVisible(false);
                pConfig.setVisible(true);

                contenedor.add(pConfig);
                contenedor.validate();
            }
        });

        //AÑADO BOTONES AL PANEL
        panelBotones.add(btnControl);
        panelBotones.add(btnPeritos);
        panelBotones.add(btnConfig);

    }

    /*
    * METODO QUE INICIALIZA EL PANEL CENTRAL Y LOS PANELES PanelControl, PanelPeritos y PanelConfiguracion.
    */
    private void initPaneles(){
        contenedor = new JPanel();
        contenedor.setLayout(new BorderLayout());
        contenedor.setVisible(true);

        pControl = new PanelControl();
        pPeritos = new PanelPeritos();
        pConfig = new PanelConfig();

        pControl.setVisible(true);
        pPeritos.setVisible(false);
        pConfig.setVisible(false);

    }


}
