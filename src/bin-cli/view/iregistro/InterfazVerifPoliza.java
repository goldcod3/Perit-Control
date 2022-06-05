package interfaces.iregistro;

import clases.Compania;
import clases.Perito;
import clases.Registro;
import conexion.DataTables;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InterfazVerifPoliza extends JFrame {
    public PVerifyPoliza contenedor;

    public InterfazVerifPoliza(int cod, String poliza){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PeritControl - Verificar Poliza");
        setLocation(Config.anchoPantalla/2 ,(Config.alturaPantalla/2) - 200);
        setSize(850,300);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Config.pVerifyPol = null;
            }
        });

        contenedor = new PVerifyPoliza(cod, poliza);
        setContentPane(contenedor);

        setVisible(true);

    }

    public InterfazVerifPoliza(String poliza, String referencia){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PeritControl - Verificar Poliza");
        setLocation(Config.anchoPantalla/2 ,(Config.alturaPantalla/2) - 200);
        setSize(850,300);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Config.pVerifyPol = null;
            }
        });

        contenedor = new PVerifyPoliza(poliza, referencia);
        setContentPane(contenedor);

        setVisible(true);

    }

}

