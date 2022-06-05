package interfaces.icontrol.pperitos.ifichaperito;

import clases.Perito;
import interfaces.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class InterfazPerito extends JFrame {


    public PanelNPerito contenedor;
    public InterfazPerito(Perito per){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PeritControl - Ficha Perito");
        setBounds((Config.anchoPantalla/4),
               (Config.alturaPantalla/4),500,300); setResizable(true);

        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Config.pPerito = null;
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

        contenedor = new PanelNPerito(per);
        add(contenedor, BorderLayout.CENTER);
        setResizable(false);
        setVisible(true);
    }

}

