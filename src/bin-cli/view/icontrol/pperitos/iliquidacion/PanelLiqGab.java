package interfaces.icontrol.pperitos.iliquidacion;

import clases.Compania;
import clases.Perito;
import clases.Registro;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import conexion.DataTables;
import interfaces.Config;
import interfaces.icontrol.pcontrol.PanelControl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class PanelLiqGab extends JPanel {

    public ArrayList<Registro> liquidacionGab;
    public int numRegistros = 0;

    private JLabel labTReg, labTLiq, labTLGab, labGenerar, labMesLiq, labAnoLiq, labNRegistros,
            labTHonor, labTLocom, labTVarios, labTSuma, labTIva, labTTotal, imgCalendar, labCompania;
    private JTable tabRegLiquidacion;
    public DefaultTableModel modelRegLiquidacion;
    private JScrollPane scrollRegLiquidacion;
    public JTextField textFAno, textTHonor, textTLocom, textTVarios, textTSuma, textTIva,textTTotal;
    public JButton generar;

    public JComboBox companias;
    public JYearChooser anoLiquidacion;
    public JMonthChooser mesLiquidacion;

    public PanelLiqGab() {
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagLayout gbl_content = new GridBagLayout();
        gbl_content.columnWidths = new int[]{0};
        gbl_content.rowHeights = new int[]{0};
        gbl_content.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_content.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_content);

        initComponents();

        //LABELS
        GridBagConstraints grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 0);
        grid.insets = new Insets(0, 30, 0, 0);
        add(labTLGab, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 1, 0);
        add(labTLiq, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 1, 1);
        grid.weightx = 0.2;
        grid.insets = new Insets(0, 30, 0, 0);
        add(labTHonor, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 1, 2);
        grid.insets = new Insets(0, 30, 0, 0);
        add(labTLocom, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 1, 3);
        grid.insets = new Insets(0, 30, 3, 0);
        add(labTVarios, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 1, 4);
        grid.insets = new Insets(3, 30, 0, 0);
        add(labTSuma, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 1, 5);
        grid.insets = new Insets(3, 30, 0, 0);
        add(labTIva, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 1, 6);
        grid.insets = new Insets(0, 30, 0, 0);
        add(labTTotal, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 1);
        grid.weightx = 0.3;
        add(labGenerar, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 2);
        grid.insets = new Insets(0, 10, 0, 0);
        add(labCompania, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 4);
        grid.insets = new Insets(0, 10, 0, 0);
        add(labMesLiq, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 6);
        grid.insets = new Insets(0, 10, 0, 0);
        add(labAnoLiq, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 0, 9);
        grid.insets = new Insets(10, 0, 10, 0);
        add(labTReg, grid);

        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE, 2, 7);
        add(labNRegistros, grid);


        //CAMPOS DE TEXTO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 2, 1);
        grid.insets = new Insets(3, 0, 0, 120);
        grid.weightx = 0.3;
        add(textTHonor, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 2, 2);
        grid.insets = new Insets(3, 0, 0, 120);
        add(textTLocom, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 2, 3);
        grid.insets = new Insets(0, 0, 3, 120);
        add(textTVarios, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 2, 4);
        grid.insets = new Insets(3, 0, 0, 120);
        add(textTSuma, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 2, 5);
        grid.insets = new Insets(3, 0, 2, 120);
        add(textTIva, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 2, 6);
        grid.insets = new Insets(3, 0, 5, 120);
        add(textTTotal, grid);

        // COMBO BOXES
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 3);
        grid.insets = new Insets(0, 10, 0, 0);
        add(companias, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 5);
        grid.insets = new Insets(0, 10, 3, 0);
        add(mesLiquidacion, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 0, 7);
        grid.insets = new Insets(0, 10, 3, 200);
        add(anoLiquidacion, grid);

        //TABLA ESTADISTICAS
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 0, 10);
        grid.gridwidth = 3;
        grid.gridheight = 3;
        grid.weighty = 0.09;
        grid.insets = new Insets(2, 0, 0, 0);
        add(scrollRegLiquidacion, grid);



        //BOTON
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 8);
        grid.insets = new Insets(10, 30, 0, 0);
        add(generar, grid);

        //IMAGEN
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE, 2, 1);
        grid.insets = new Insets(20, 80, 0, 0);
        grid.gridheight = 5;
        add(imgCalendar, grid);

    }

    public void initComponents() {
        // LABELS
        imgCalendar = new JLabel();
        imgCalendar.setIcon(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\calendar.png"));

        labTLGab = new JLabel("Liquidación Gabinete:");
        labTLGab.setFont(Config.FUENTE14);
        labTLGab.setForeground(Color.WHITE);
        labTReg = new JLabel("Registros asociados a la liquidación:");
        labTReg.setForeground(Color.WHITE);
        labTLiq = new JLabel("Liquidación:");
        labGenerar = new JLabel("Generar Liquidación:");
        labGenerar.setForeground(Color.WHITE);
        labMesLiq = new JLabel("Mes:");
        labMesLiq.setForeground(Color.WHITE);
        labAnoLiq = new JLabel("Año:");
        labAnoLiq.setForeground(Color.WHITE);
        labCompania = new JLabel("Compañia:");
        labCompania.setForeground(Color.WHITE);
        labNRegistros = new JLabel("Nº Registros: "+numRegistros);
        labTHonor = new JLabel("Total Honorarios:");
        labTLocom = new JLabel("Total Locomoción:");
        labTVarios = new JLabel("Total Varios:");
        labTSuma = new JLabel("Suma sin IVA:");
        labTIva = new JLabel("Total IVA:");
        labTTotal = new JLabel("Liquidación Total Gabinete:");

        // CAMPOS DE TEXTO
        textFAno = new JTextField(new SimpleDateFormat("yyyy").format(new Date()));
        textTHonor = new JTextField("0.00");
        textTHonor.setEditable(false);
        textTLocom = new JTextField("0.00");
        textTLocom.setEditable(false);
        textTVarios = new JTextField("0.00");
        textTVarios.setEditable(false);
        textTSuma = new JTextField("0.00");
        textTSuma.setEditable(false);
        textTIva = new JTextField("0.00");
        textTIva.setEditable(false);
        textTTotal = new JTextField("0.00");
        textTTotal.setEditable(false);

        // COMBO BOX
        anoLiquidacion = new JYearChooser();
        mesLiquidacion = new JMonthChooser();
        companias = new JComboBox();
        chargeComboCompania();

        // TABLA LIQUIDACION
        String [] headerStats = {"Cod", "Compañia", "Referencia Cia", "F.Cierre", "Honorarios", "locomoción","Varios", "Total", "Tipo"};
        modelRegLiquidacion = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        for (String column : headerStats) modelRegLiquidacion.addColumn(column);
        tabRegLiquidacion = new JTable(modelRegLiquidacion);
        tabRegLiquidacion.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabRegLiquidacion.getColumnModel().getColumn(1).setPreferredWidth(60);
        tabRegLiquidacion.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabRegLiquidacion.getColumnModel().getColumn(3).setPreferredWidth(60);
        scrollRegLiquidacion = new JScrollPane(tabRegLiquidacion);

        //BOTONES
        generar = new JButton("Generar Liquidación");
        generar.setBackground(Config.AUX1);
        generar.setForeground(Color.WHITE);
        generar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int mesLiq = mesLiquidacion.getMonth();
                    int anoLiq = anoLiquidacion.getYear();
                   // mesLiq++;
                    System.out.println(mesLiq);
                    ArrayList<Registro> liq = getRegistrosLiqGabinete(mesLiq, anoLiq);
                    getLiquidacion(liq);
                    chargeRegTable(liq);
                    Config.pControl.pControl.changeUltimaAccion("LIQUIDACIÓN DE GABINETE GENERADA... ");

                }catch (Exception ex){
                    ex.printStackTrace();

                }
            }
        });


    }
    public void chargeComboCompania(){
        companias.removeAllItems();
        companias.addItem("TODAS");
        for (Compania c : DataTables.getCompanias()){
            if (c.getEstadoCompania().equals("ACTIVO")){
                companias.addItem(c.getNombreCompania());
            }
        }
    }

    public void getLiquidacion (ArrayList<Registro> registros){
        double honor = 0;
        double locom = 0;
        double var = 0;
        double suma = 0;
        double iva = 0;
        double total = 0;
        for (Registro r : registros){
            honor = honor + r.getFacturaRegistro().gethGabinete().getHonorarioGab();
            locom = locom + r.getFacturaRegistro().gethGabinete().getLocomocionGab();
            var = var + r.getFacturaRegistro().gethGabinete().getVariosGab();
            suma = suma + r.getFacturaRegistro().gethGabinete().getSumaGab();
            iva = iva + r.getFacturaRegistro().gethGabinete().getIvaGab();
            total = total + r.getFacturaRegistro().gethGabinete().getTotalGab();
        }
        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        DecimalFormat formato1 = new DecimalFormat("#.00", s);
        textTHonor.setText(Double.toString(Double.parseDouble(formato1.format(honor))));
        textTLocom.setText(Double.toString(Double.parseDouble(formato1.format(locom))));
        textTVarios.setText(Double.toString(Double.parseDouble(formato1.format(var))));
        textTSuma.setText(Double.toString(Double.parseDouble(formato1.format(suma))));
        textTIva.setText(Double.toString(Double.parseDouble(formato1.format(iva))));
        textTTotal.setText(Double.toString(Double.parseDouble(formato1.format(total))));
    }

    public void chargeRegTable(ArrayList<Registro> registros){
        int filasRegistro = modelRegLiquidacion.getRowCount();
        for (int i = 0; i < filasRegistro; i++){
            modelRegLiquidacion.removeRow(0);
        }
        Collections.sort(registros, new Comparator<Registro>() {
            public int compare(Registro o1, Registro o2) {
                return o1.getFechaCierre().compareTo(o2.getFechaCierre());
            }
        });
        Object r [];
        for (Registro reg : registros){
            String nombreCom = "";
            for (Compania com : DataTables.getCompanias()){
                if (com.getCodCompania() == reg.getSiniestro().getCompania()){
                    nombreCom = com.getNombreCompania();
                }
            }
            r = new Object[]{reg.getCodRegistro(), nombreCom, reg.getReferenciaCom(), new SimpleDateFormat("dd/MM/yyyy").format(reg.getFechaCierre()),
                    reg.getFacturaRegistro().gethGabinete().getHonorarioGab(), reg.getFacturaRegistro().gethGabinete().getLocomocionGab(),
                    reg.getFacturaRegistro().gethGabinete().getVariosGab(), reg.getFacturaRegistro().gethGabinete().getTotalGab(), reg.getTipoRegistro()};
            modelRegLiquidacion.addRow(r);
        }
        numRegistros = registros.size();
        labNRegistros.setText("Nº Registros: "+numRegistros);
    }

    public ArrayList<Registro> getRegistrosLiqGabinete(int mes, int ano){
        ArrayList<Registro> liquidacion = new ArrayList<>();
        int codCompania = 0;
        for (Compania c : DataTables.getCompanias()) {
            if (c.getNombreCompania().equals(companias.getSelectedItem())) {
                codCompania = c.getCodCompania();
                break;
            }
        }
        System.out.println("cod com: "+codCompania);
        if (codCompania > 0) {
            //UNA COMPANIA
            for (Registro r : DataTables.getRegistros()) {
                if (r.getEstadoRegistro().equals("CERRADO")) {
                    if (r.getSiniestro().getCompania() == codCompania) {

                            String mesFCierre = new SimpleDateFormat("MM").format(r.getFechaCierre());
                            int numMes = Integer.parseInt(mesFCierre);
                            String anoFCierre = new SimpleDateFormat("yyyy").format(r.getFechaCierre());
                            int numAno = Integer.parseInt(anoFCierre);
                            if (numMes == (mes + 1)) {
                                if (numAno == ano) liquidacion.add(r);
                            }

                    }
                }
            }
        } else {
            //TODAS LAS COMPANIAS
            for (Registro r : DataTables.getRegistros()) {
                if (r.getEstadoRegistro().equals("CERRADO")) {

                        String mesFCierre = new SimpleDateFormat("MM").format(r.getFechaCierre());
                        int numMes = Integer.parseInt(mesFCierre);
                        String anoFCierre = new SimpleDateFormat("yyyy").format(r.getFechaCierre());
                        int numAno = Integer.parseInt(anoFCierre);
                        if (numMes == (mes + 1)) {
                            if (numAno == ano) liquidacion.add(r);
                        }

                }
            }
            companias.setSelectedItem("TODAS");
        }

        return liquidacion;
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Config.COLOR3);
        g.fillRect(0, 0, 230, 500);
        g.setColor(Config.AUX3);
        g.fillRect(260, 0, 550, 250);

    }
}
