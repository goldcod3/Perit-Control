package interfaces.icontrol.pperitos.iestadisticas;

import clases.DataEstadisticas;
import clases.Perito;
import clases.Registro;
import com.toedter.calendar.JDateChooser;
import conexion.DataTables;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class PanelEstadisticas extends JPanel {

    private JLabel estatsLab, fDesde, fHasta;
    private JTable tabStats;
    public DefaultTableModel modelStats;
    private JScrollPane scrollStats;
    public JDateChooser dateFechaDesde, dateFechaHasta;
    public JButton actualizar;


    public PanelEstadisticas() {
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagLayout gbl_content = new GridBagLayout();
        gbl_content.columnWidths = new int[]{0};
        gbl_content.rowHeights = new int[]{0};
        gbl_content.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_content.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_content);

        initComponents();

        //LAYER DATOS PERITOS
        GridBagConstraints grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 0);
        add(estatsLab, grid);

        //TABLA ESTADISTICAS
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 0, 1);
        grid.gridwidth = 5;
        grid.gridheight = 3;
        grid.weightx = 0.09;
        grid.weighty = 0.09;
        grid.insets = new Insets(10, 0, 10, 0);
        add(scrollStats, grid);

        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE, 0, 4);
        add(fDesde, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 4);
        grid.insets = new Insets(0, 10, 0, 10);
        grid.weightx = 0.1;
        add(dateFechaDesde, grid);

        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE, 2, 4);
        add(fHasta, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 3, 4);
        grid.insets = new Insets(0, 10, 0, 10);
        grid.weightx = 0.1;
        add(dateFechaHasta, grid);

        //BOTON ACTUALIZAR
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE, 4, 4);
        add(actualizar, grid);

    }

    public void initComponents() {

        //LABEL ESTADISTICAS
        estatsLab = new JLabel("ESTADÍSTICAS");
        estatsLab.setFont(Config.FUENTETITULO);

        //TABLA ESTADISTICAS
        String[] headerStats = {"Nombre", "Estado", "Registros Abiertos", "Entregados", "Media Tiempo(Dias)"};
        modelStats = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        for (String column : headerStats) modelStats.addColumn(column);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tabStats = new JTable(modelStats);
        tabStats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabStats.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabStats.getColumnModel().getColumn(1).setPreferredWidth(30);
        tabStats.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabStats.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabStats.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabStats.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tabStats.getColumnModel().getColumn(3).setCellRenderer(tcr);
        tabStats.getColumnModel().getColumn(4).setCellRenderer(tcr);
        scrollStats = new JScrollPane(tabStats);

        fDesde = new JLabel("Desde:");
        fHasta = new JLabel("Hasta:");

        dateFechaDesde = new JDateChooser(Config.configuracion.getFechaDesde());
        dateFechaHasta = new JDateChooser(Config.configuracion.getFechaHasta());

        actualizar = new JButton("Generar");
        actualizar.setPreferredSize(new Dimension(140, 25));
        actualizar.setBackground(Config.COLOR5);
        actualizar.setForeground(Color.WHITE);
        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filasRegistro =  modelStats.getRowCount();
                for (int i = 0; i < filasRegistro; i++){
                    modelStats.removeRow(0);
                }
                cargaDatosPeritos();
            }
        });

    }

    public void cargaDatosPeritos() {
        ArrayList<DataEstadisticas> estats = new ArrayList<>();
        double mejorMedia = 0;
        double mediaMasAlta = 0;
        int peritoMejorMedia = 0;
        Calendar calendar = Calendar.getInstance();
        Date fechad;
        Date fechah;
        if (fDesde == null || fHasta == null) {
            fechad = Config.configuracion.getFechaDesde();
            fechah = Config.configuracion.getFechaHasta();

        } else {
            fechad = dateFechaDesde.getDate();
            fechah = dateFechaHasta.getDate();
        }
        calendar.setTime(fechad);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        fechad = calendar.getTime();
        calendar.setTime(fechah);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        fechah = calendar.getTime();
        for (Perito p : DataTables.getPeritos()) {

            double media = 0;
            double mejorMediaPerito = 0;
            int numRegAbiertos = 0;
            double sumaDias = 0;
            int contador = 0;

            int milisecondsByDay = 86400000;

            for (Registro r : DataTables.getRegistros()) {
                if (p.getCodPerito() == r.getPerito()) {
                    if (r.getEstadoRegistro().equals("ABIERTO")) numRegAbiertos++;
                    if (r.getEstadoEntrega().equals("ENTREGADO")) {
                        if (r.getFechaEntregaPerito().after(fechad) & r.getFechaEntregaPerito().before(fechah)) {
                            double dias = (double) ((r.getFechaEntregaPerito().getTime() - r.getFechaApertura().getTime()) / milisecondsByDay);
                            sumaDias += dias;
                            contador++;
                        }
                    }
                }
            }

            media = sumaDias / contador;
            if (mejorMediaPerito == 0) mejorMediaPerito = media;
            else if (mejorMediaPerito > media) mejorMediaPerito = media;
            if (mediaMasAlta == 0) mediaMasAlta = media;
            else if (mediaMasAlta < media) mediaMasAlta = media;

            DataEstadisticas data = new DataEstadisticas();
            data.setNombrePerito(p.getNombrePerito());
            data.setEstadoPerito(p.getEstadoPerito());
            data.setRegistrosAbiertos(numRegAbiertos);
            data.setRegistrosEntregados(contador);
            data.setMedia(media);
            estats.add(data);

            numRegAbiertos = 0;
            sumaDias = 0;
            contador = 0;
        }

        for (DataEstadisticas d : estats) {

            double rendimiento = (100 * d.getMedia()) / mediaMasAlta;
            d.setMedia(Math.round(d.getMedia() * 100.0) / 100.0);
            if (d.getRegistrosEntregados() == 0) {
                d.setMedia(0);
            } else if (d.getMedia() < 0) {
                d.setMedia(0);
            }

        }
        Collections.sort(estats);
        for (DataEstadisticas es : estats) {
            modelStats.addRow(new Object[]{es.getNombrePerito(), es.getEstadoPerito(), es.getRegistrosAbiertos(), es.getRegistrosEntregados(), es.getMedia()});
        }
    }


    /*
     * Metodo que posiciona el GridBagConstrain del Layout para introducir el componente en la ventana.
     */
    public static GridBagConstraints newPosicion(int anchor, int fill, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        return gbc;
    }

}
