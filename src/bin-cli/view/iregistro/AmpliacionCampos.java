package interfaces.iregistro;

import clases.Registro;
import interfaces.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AmpliacionCampos extends JFrame{

    public PanelAmpliacion panel;

    public AmpliacionCampos(int interfaz, Registro r) {
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PeritControl - Ampliación campos");
        setBounds((Config.anchoPantalla / 4), (Config.alturaPantalla / 4), 800, 640);
        setResizable(false);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                for (InterfazRegistro i : Config.interfaces) {
                    if (i.getIdInterfaz() == interfaz) {
                        if (i.reg.getTipoRegistro().equals("REGISTRO")) {
                            i.contenedorReg.areaDescrip.setText(panel.descripcion.getText());
                            i.contenedorReg.ampliacion.dispose();
                            i.contenedorReg.ampliacion = null;
                            i.contenedorReg.textAsegurado.requestFocus();
                        } else {
                            i.contenedorReap.areaDescrip.setText(panel.descripcion.getText());
                            i.contenedorReap.ampliacion.dispose();
                            i.contenedorReap.ampliacion = null;
                            i.contenedorReap.textAsegurado.requestFocus();
                        }

                    }
                    break;
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
        String d = "";
        for (InterfazRegistro i : Config.interfaces) {
            if (i.getIdInterfaz() == interfaz) {
                if (i.reg.getTipoRegistro().equals("REGISTRO")) {
                    d = i.contenedorReg.areaDescrip.getText();
                } else {
                    d = i.contenedorReap.areaDescrip.getText();
                }
                break;
            }
        }
        //LAYOUT
        setLayout(new BorderLayout());

        //AÑADO PANELES
        panel = new PanelAmpliacion(interfaz, r, d);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}
