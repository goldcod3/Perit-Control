package interfaces.iregistro;

import clases.Compania;
import clases.Perito;
import clases.Registro;
import conexion.DataTables;
import interfaces.Config;
import interfaces.iregistro.eventos.HiloVRegistro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PVerifyPoliza extends JPanel {

    public DefaultTableModel modelSearch;
    public JTable tabSearchReg;
    private JScrollPane scrollSearch;

    private JLabel titulo;
    private JButton abrir;


    public PVerifyPoliza(int cod, String var) {
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        //LAYOUT
        GridBagLayout gbl_contenedor = new GridBagLayout();
        gbl_contenedor.columnWidths = new int[]{0};
        gbl_contenedor.rowHeights = new int[]{0};
        gbl_contenedor.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_contenedor.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_contenedor);

        //INICIALIZACION DE COMPONENTES
        initComponents();
        chargeRegistros(cod, var);

        //LAYOUT COMPONENTS
        ////////////////// REGISTRO

        //LABEL TITULO REGISTRO
        GridBagConstraints grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 0, 0);
        grid.insets = new Insets(0, 10, 5, 0);
        grid.gridwidth = 2;
        grid.weighty = 0.02;
        add(titulo, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 0, 1);
        grid.gridwidth = 4;
        grid.gridheight = 2;
        grid.weighty = 0.09;
        add(scrollSearch, grid);

        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, 2, 4);
        grid.gridwidth = 2;
        grid.weighty = 0.02;
        add(abrir, grid);
    }

    public PVerifyPoliza(String poliza, String referencia) {
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        //LAYOUT
        GridBagLayout gbl_contenedor = new GridBagLayout();
        gbl_contenedor.columnWidths = new int[]{0};
        gbl_contenedor.rowHeights = new int[]{0};
        gbl_contenedor.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_contenedor.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_contenedor);

        //INICIALIZACION DE COMPONENTES
        initComponents();
        chargeRegistros(poliza, referencia);

        //LAYOUT COMPONENTS
        ////////////////// REGISTRO

        //LABEL TITULO REGISTRO
        GridBagConstraints grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 0, 0);
        grid.insets = new Insets(0, 10, 5, 0);
        grid.gridwidth = 2;
        grid.weighty = 0.02;
        add(titulo, grid);

        grid = Config.newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 0, 1);
        grid.gridwidth = 4;
        grid.gridheight = 2;
        grid.weighty = 0.09;
        add(scrollSearch, grid);

        grid = Config.newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, 2, 4);
        grid.gridwidth = 2;
        grid.weighty = 0.02;
        add(abrir, grid);
    }

    private void initComponents() {
        titulo = new JLabel("Registros encontrados:");
        titulo.setFont(Config.FUENTETITULO);

        abrir = new JButton("Abrir Registro");
        abrir.setBackground(Config.AUX4);
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread vp = new Thread(new HiloVRegistro());
                vp.start();
            }
        });

        modelSearch = new DefaultTableModel();
        modelSearch.addColumn("Codigo");
        modelSearch.addColumn("Compañia");
        modelSearch.addColumn("Referencia Cia");
        modelSearch.addColumn("Estado");
        modelSearch.addColumn("Perito");
        modelSearch.addColumn("Póliza");
        modelSearch.addColumn("Tipo");
        tabSearchReg = new JTable(modelSearch);
        scrollSearch = new JScrollPane(tabSearchReg);
    }

    public void chargeRegistros(int cod, String var) {
        if (cod == 1) {
            //POLIZA
            for (Registro reg : DataTables.getRegistros()) {
                if (reg.getSiniestro().getNumPoliza().contains(var)) {
                    String nombreCom = "", nombrePer = "";
                    for (Compania com : DataTables.getCompanias()) {
                        if (com.getCodCompania() == reg.getSiniestro().getCompania()) {
                            nombreCom = com.getNombreCompania();
                        }
                    }
                    for (Perito per : DataTables.getPeritos()) {
                        if (per.getCodPerito() == reg.getPerito()) {
                            nombrePer = per.getNombrePerito();
                        }
                    }
                    Object[] r = {reg.getCodRegistro(), nombreCom, reg.getReferenciaCom(), reg.getEstadoRegistro(),
                            nombrePer, reg.getSiniestro().getNumPoliza(), reg.getTipoRegistro()};
                    modelSearch.addRow(r);
                }
            }

        } else if (cod == 2) {
            //REFERENCIA
            for (Registro reg : DataTables.getRegistros()) {
                if (reg.getReferenciaCom().contains(var)) {
                    String nombreCom = "", nombrePer = "";
                    for (Compania com : DataTables.getCompanias()) {
                        if (com.getCodCompania() == reg.getSiniestro().getCompania()) {
                            nombreCom = com.getNombreCompania();
                        }
                    }
                    for (Perito per : DataTables.getPeritos()) {
                        if (per.getCodPerito() == reg.getPerito()) {
                            nombrePer = per.getNombrePerito();
                        }
                    }
                    Object[] r = {reg.getCodRegistro(), nombreCom, reg.getReferenciaCom(), reg.getEstadoRegistro(),
                            nombrePer, reg.getSiniestro().getNumPoliza(), reg.getTipoRegistro()};
                    modelSearch.addRow(r);
                }
            }
        }
    }

    public void chargeRegistros(String poliza, String referencia) {

        for (Registro reg : DataTables.getRegistros()) {
            if (reg.getSiniestro().getNumPoliza().contains(poliza) || reg.getReferenciaCom().contains(referencia)) {
                String nombreCom = "", nombrePer = "";
                for (Compania com : DataTables.getCompanias()) {
                    if (com.getCodCompania() == reg.getSiniestro().getCompania()) {
                        nombreCom = com.getNombreCompania();
                    }
                }
                for (Perito per : DataTables.getPeritos()) {
                    if (per.getCodPerito() == reg.getPerito()) {
                        nombrePer = per.getNombrePerito();
                    }
                }
                Object[] r = {reg.getCodRegistro(), nombreCom, reg.getReferenciaCom(), reg.getEstadoRegistro(),
                        nombrePer, reg.getSiniestro().getNumPoliza(), reg.getTipoRegistro()};
                modelSearch.addRow(r);
            }
        }
    }


}
