package interfaces.iregistro;

import clases.Reapertura;
import clases.Registro;
import conexion.DataTables;
import interfaces.Config;
import interfaces.iregistro.eventos.HiloOAmpliacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PanelAmpliacion extends JPanel {

    public int cod = 0;
    public JButton cerrar , abrir;
    public JTextArea descripcion;
    public JTable regTable;
    public DefaultTableModel modelReg;
    public JScrollPane scrollDescrip, scrollTableReg;

    public PanelAmpliacion(int codInterfaz, Registro r, String desc){
        setBorder(new EmptyBorder(10,10,10,10));
        setBackground(Config.COLOR2);

        this.cod = codInterfaz;
        initComponents();
        descripcion.setText(desc);
        chargeReaperturasReg(r);

        //LAYOUT
        GridBagLayout gbl_contenedor = new GridBagLayout();
        gbl_contenedor.columnWidths = new int[]{0};
        gbl_contenedor.rowHeights = new int[]{0};
        gbl_contenedor.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_contenedor.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_contenedor);

        GridBagConstraints grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,0,0);
        grid.gridwidth = 2;
        grid.gridheight = 2;
        grid.weightx = 0.3;
        grid.weighty = 0.5;
        add(scrollDescrip, grid);

        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,2,0);
        grid.gridwidth = 1;
        grid.gridheight = 2;
        add(scrollTableReg, grid);

        grid = newPosicion(GridBagConstraints.SOUTHWEST,
                GridBagConstraints.HORIZONTAL,1,2);
        add(cerrar, grid);

        grid = newPosicion(GridBagConstraints.SOUTHWEST,
                GridBagConstraints.HORIZONTAL,2,2);
        add(abrir, grid);



    }

    public void initComponents(){
        cerrar = new JButton("Cerrar");
        cerrar.setBackground(Config.AUX1);
        cerrar.setForeground(Color.WHITE);
        cerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (InterfazRegistro i : Config.interfaces) {
                    if (i.getIdInterfaz() == cod) {
                        if (i.reg.getTipoRegistro().equals("REGISTRO")) {
                            i.contenedorReg.areaDescrip.setText(descripcion.getText());
                            i.contenedorReg.ampliacion.dispose();
                            i.contenedorReg.ampliacion = null;
                            i.contenedorReg.textAsegurado.requestFocus();

                        } else {
                            i.contenedorReap.areaDescrip.setText(descripcion.getText());
                            i.contenedorReap.ampliacion.dispose();
                            i.contenedorReap.ampliacion = null;
                            i.contenedorReap.textAsegurado.requestFocus();

                        }
                        break;
                    }
                }
            }
        });
        abrir = new JButton("Abrir Registro");
        abrir.setBackground(Config.AUX5);
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HiloOAmpliacion amp = new HiloOAmpliacion();
                amp.codigo = (int) modelReg.getValueAt(regTable.getSelectedRow(), 0);
                Thread h = new Thread(amp);
                h.start();
            }
        });

        descripcion = new JTextArea();
        scrollDescrip = new JScrollPane(descripcion);

        modelReg = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        modelReg.addColumn("Código");
        modelReg.addColumn("Fecha");
        regTable = new JTable(modelReg);
        regTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        regTable.isCellEditable(0,0);
        scrollTableReg = new JScrollPane(regTable);

    }

    public void chargeReaperturasReg(Registro reg) {
        if (reg.getTipoRegistro().equals("REGISTRO")) {
            int filas = modelReg.getRowCount();
            for (int i = 0; i < filas; i++) {
                modelReg.removeRow(0);
            }
            ArrayList<Reapertura> reaperturas = new ArrayList<>();
            for (Reapertura r : DataTables.getReaperturas()) {
                if (r.getRegistroAsociado() == reg.getCodRegistro()) {
                    reaperturas.add(r);
                }
            }
            if (reaperturas.size() > 0) {
                for (Reapertura re : reaperturas) {
                    for (Registro regi : DataTables.getRegistros()) {
                        if (regi.getCodRegistro() == re.getReapertura()) {
                            Object[] t = {regi.getCodRegistro(), new SimpleDateFormat("dd/MM/yyyy").format(reg.getFechaApertura())};
                            modelReg.addRow(t);
                        }
                    }
                }
            }
        } else {
            int filas = modelReg.getRowCount();
            int regAsoc = 0;
            for (int i = 0; i < filas; i++){
                modelReg.removeRow(0);
            }
            ArrayList<Reapertura> reaperturas = new ArrayList<>();
            for (Reapertura r : DataTables.getReaperturas()){
                if (r.getReapertura() == reg.getCodRegistro()){
                    regAsoc = r.getRegistroAsociado();
                    if (r.getReapertura() == regAsoc){
                        reaperturas.add(r);
                    }
                }
            }
            System.out.println(regAsoc);
            for (Registro regi : DataTables.getRegistros()){
                if (regi.getCodRegistro() == regAsoc){
                    Object [] tb = {regAsoc, new SimpleDateFormat("dd/MM/yyyy").format(regi.getFechaApertura())+" (Base)"};
                    modelReg.addRow(tb);
                    break;
                }
            }
            for (Reapertura re : reaperturas){
                for (Registro registro : DataTables.getRegistros()){
                    if (re.getRegistroAsociado() == re.getReapertura()){
                        Object [] t = {registro.getCodRegistro(), new SimpleDateFormat("dd/MM/yyyy").format(registro.getFechaApertura())};
                        modelReg.addRow(t);
                    }
                }
            }
        }
    }

    public void cerrar(){
        for (InterfazRegistro i : Config.interfaces) {
            if (i.getIdInterfaz() == cod) {
                if (i.reg.getTipoRegistro().equals("REGISTRO")) {
                    i.contenedorReg.areaDescrip.setText(descripcion.getText());
                    i.contenedorReg.ampliacion.dispose();
                    i.contenedorReg.ampliacion = null;
                } else {
                    i.contenedorReap.areaDescrip.setText(descripcion.getText());
                    i.contenedorReap.ampliacion.dispose();
                    i.contenedorReap.ampliacion = null;
                }
                break;
            }
        }
    }
    public static GridBagConstraints newPosicion(int anchor, int fill, int gridx, int gridy){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        return gbc;
    }
}
