package interfaces.icontrol.pperitos.iliquidacion;

import interfaces.Config;
import interfaces.icontrol.pconfig.PanelConfig;
import interfaces.icontrol.pcontrol.PanelControl;
import interfaces.icontrol.pperitos.PanelPeritos;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class InterfazLiquidacion extends JFrame {

    private static JButton btnGabinete, btnPerito;
    private static JPanel panelBotones, contenedor;
    public PanelLiqGab pGabinete;
    public PanelLiqPer pPerito;

    public InterfazLiquidacion(){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PeritControl - Liquidación");
        setBounds((Config.anchoPantalla/4), (Config.alturaPantalla/4),800,640); setResizable(true);
        setResizable(false);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                Config.pLiquidacion = null;
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
        contenedor.add(pGabinete);

        //AÑADO PANELES
        add(contenedor, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initPanelBtn(){

        //INICIALIZO PANEL BOTONES
        FlowLayout fLayout = new FlowLayout(FlowLayout.CENTER,30,10);
        panelBotones = new JPanel();
        panelBotones.setBackground(Config.COLOR4);
        panelBotones.setLayout(fLayout);

        //INICIALIZO BOTONES
        btnGabinete = new JButton("GABINETE");
        btnGabinete.setBackground(Config.COLOR6);
        btnGabinete.setForeground(Color.WHITE);
        btnPerito = new JButton("PERITO");
        btnPerito.setBackground(Config.COLOR6);
        btnPerito.setForeground(Color.WHITE);
        btnGabinete.setPreferredSize(new Dimension(100,40));
        btnPerito.setPreferredSize(new Dimension(100,40));

        //EVENTOS DE LOS BOTONES
        btnGabinete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pGabinete.setVisible(true);
                pPerito.setVisible(false);
                contenedor.add(pGabinete);
                contenedor.validate();
            }
        });
        btnPerito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pGabinete.setVisible(false);
                pPerito.setVisible(true);
                contenedor.add(pPerito);
                contenedor.validate();
            }
        });


        //AÑADO BOTONES AL PANEL
        panelBotones.add(btnGabinete);
        panelBotones.add(btnPerito);

    }

    /*
     * METODO QUE INICIALIZA EL PANEL CENTRAL Y LOS PANELES PanelControl, PanelPeritos y PanelConfiguracion.
     */
    private void initPaneles(){
        contenedor = new JPanel();
        contenedor.setLayout(new BorderLayout());
        contenedor.setVisible(true);

        pGabinete = new PanelLiqGab();
        pPerito = new PanelLiqPer();

        pGabinete.setVisible(true);
        pPerito.setVisible(false);

    }
}

