package interfaces.ilogin;

import clases.Usuario;
import conexion.DataTables;
import conexion.hilos.SentPacket;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class InterfazLoginCli extends JFrame{
    public PLoginCli pLogin = new PLoginCli();

    public InterfazLoginCli(){
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400,300));
        setLocationRelativeTo(null);
        setResizable(false);

        //LAYOUT
        setLayout(new BorderLayout());

        //AÑADO PANELES
        add(pLogin, BorderLayout.CENTER);
        setUndecorated(true);
        setVisible(true);
    }


}

