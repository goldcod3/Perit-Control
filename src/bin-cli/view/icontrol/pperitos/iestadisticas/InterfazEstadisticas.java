package interfaces.icontrol.pperitos.iestadisticas;

import clases.DataEstadisticas;
import clases.Perito;
import clases.Registro;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import conexion.DataTables;
import interfaces.Config;
import interfaces.icontrol.pperitos.iliquidacion.InterfazLiquidacion;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;

public class InterfazEstadisticas extends JFrame {

    public InterfazEstadisticas(){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PeritControl - Ficha Perito");
        setBounds((Config.anchoPantalla/4), (Config.alturaPantalla/4),800,500); setResizable(true);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                Config.pEstadisticas = null;
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

        PanelEstadisticas cont = new PanelEstadisticas();
        add(cont, BorderLayout.CENTER);
        setResizable(false);
        setVisible(true);
    }

}

