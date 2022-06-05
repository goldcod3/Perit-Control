package interfaces.icontrol.pperitos.iliquidacion;

import clases.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import conexion.DataTables;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class PanelLiqPer extends JPanel {

    public ArrayList<Registro> liquidacionGab;
    public int numRegistros = 0;
    public boolean generate = false;

    private JLabel labTReg, labTLiq, labTLPer, labGenerar, labPerito, labMesLiq, labAnoLiq, labNRegistros,
            labTHonor, labTLocom, labTAjustes, labTIva, labTSuma, labTIrpf, labTTotal, imgCalendar;
    private JTable tabRegLiquidacion;
    public DefaultTableModel modelRegLiquidacion;
    private JScrollPane scrollRegLiquidacion;
    public JComboBox peritos;
    public JTextField textFAno, textTHonor, textTLocom, textTAjustes, textTSuma, textTIva, textTIrpf,textTTotal;
    public JButton generar, pdf;

    public JYearChooser anoLiquidacion;
    public JMonthChooser mesLiquidacion;

    public PanelLiqPer() {
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagLayout gbl_content = new GridBagLayout();
        gbl_content.columnWidths = new int[]{0};
        gbl_content.rowHeights = new int[]{0};
        gbl_content.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_content.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_content);

        initComponents();
        chargeComboPerito();

        //LABELS
        GridBagConstraints grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 0);
        grid.insets = new Insets(0, 30, 0, 0);
        add(labTLPer, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 1, 0);
        add(labTLiq, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 1, 1);
        grid.insets = new Insets(0, 30, 0, 0);
        grid.weightx = 0.2;
        add(labTHonor, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.NONE, 1, 2);
        grid.insets = new Insets(3,30, 0, 0);
        add(labTLocom, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.NONE, 1, 3);
        grid.insets = new Insets(3, 30, 0, 0);
        add(labTAjustes, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.NONE, 1, 4);
        grid.insets = new Insets(5, 30, 0, 0);
        add(labTSuma, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.NONE, 1, 5);
        grid.insets = new Insets(5, 30, 0, 0);
        add(labTIva, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.NONE, 1, 6);
        grid.insets = new Insets(3, 30, 0, 0);
        add(labTIrpf, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.NONE, 1, 7);
        grid.insets = new Insets(3, 30, 0, 0);
        add(labTTotal, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 1);
        grid.weightx = 0.3;
        add(labGenerar, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 2);
        grid.insets = new Insets(0, 10, 0, 0);
        add(labPerito, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 4);
        grid.insets = new Insets(0, 10, 0, 0);
        add(labMesLiq, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 6);
        grid.insets = new Insets(0, 10, 0, 0);
        add(labAnoLiq, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 0, 10);
        grid.weighty = 0.01;
        add(labTReg, grid);

        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE, 2, 10);
        grid.insets = new Insets(0, 0, 0, 50);
        add(labNRegistros, grid);


        //CAMPOS DE TEXTO
        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, 2, 1);
        grid.insets = new Insets(0, 0, 0, 120);
        grid.weightx = 0.3;
        add(textTHonor, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, 2, 2);
        grid.insets = new Insets(3, 0, 0, 120);
        add(textTLocom, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, 2, 3);
        grid.insets = new Insets(3, 0, 0, 120);
        add(textTAjustes, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, 2, 4);
        grid.insets = new Insets(3, 0, 0, 120);
        add(textTSuma, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, 2, 5);
        grid.insets = new Insets(3, 0, 0, 120);
        add(textTIva, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, 2, 6);
        grid.insets = new Insets(3, 0, 0, 120);
        add(textTIrpf, grid);

        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, 2, 7);
        grid.insets = new Insets(0, 0, 0, 120);
        add(textTTotal, grid);

        // DATE CHOOSERS
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 5);
        grid.insets = new Insets(0, 10, 6, 0);
        add(mesLiquidacion, grid);

        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 0, 7);
        grid.insets = new Insets(0, 10, 5, 200);
        add(anoLiquidacion, grid);

        //COMBO BOX
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 0, 3);
        grid.insets = new Insets(0, 10, 5, 75);
        add(peritos, grid);


        //TABLA REGISTROS
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.BOTH, 0, 11);
        grid.gridwidth = 3;
        grid.gridheight = 3;
        grid.weighty = 0.09;
        grid.insets = new Insets(2, 0, 0, 0);
        add(scrollRegLiquidacion, grid);

        //BOTONES
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE, 0, 8);
        grid.insets = new Insets(10, 30, 0, 0);
        add(generar, grid);

        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE, 2, 7);
        grid.insets = new Insets(0, 30, 20, 0);
        grid.gridheight = 3;
        add(pdf, grid);

        //IMAGEN
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE, 2, 1);
        grid.insets = new Insets(20, 120, 0, 0);
        grid.gridheight = 5;
        add(imgCalendar, grid);

    }

    public void initComponents() {
        // LABELS
        imgCalendar = new JLabel();
        imgCalendar.setIcon(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\calendar.png"));

        labTLPer = new JLabel("Liquidación Perito:");
        labTLPer.setFont(Config.FUENTE14);
        labTLPer.setForeground(Color.WHITE);
        labTReg = new JLabel("Registros asociados a la liquidación:");
        labTReg.setForeground(Color.WHITE);
        labTLiq = new JLabel("Liquidación:");
        labGenerar = new JLabel("Generar Liquidación:");
        labGenerar.setForeground(Color.WHITE);
        labPerito = new JLabel("Perito:");
        labPerito.setForeground(Color.WHITE);
        labMesLiq = new JLabel("Mes:");
        labMesLiq.setForeground(Color.WHITE);
        labAnoLiq = new JLabel("Año:");
        labAnoLiq.setForeground(Color.WHITE);
        labNRegistros = new JLabel("Nº Registros: "+numRegistros);
        labTHonor = new JLabel("Total Honorarios:");
        labTLocom = new JLabel("Total Locomoción:");
        labTAjustes = new JLabel("Total Ajustes:");
        labTSuma = new JLabel("Suma sin Iva:");
        labTIva = new JLabel("Total IVA:");
        labTIrpf = new JLabel("Total Irpf:");
        labTTotal = new JLabel("Liquidación Total Perito:");

        // CAMPOS DE TEXTO
        textFAno = new JTextField(new SimpleDateFormat("yyyy").format(new Date()));
        textTHonor = new JTextField("0.00");
        textTHonor.setEditable(false);
        textTLocom = new JTextField("0.00");
        textTLocom.setEditable(false);
        textTAjustes = new JTextField("0.00");
        textTAjustes.setEditable(false);
        textTSuma = new JTextField("0.00");
        textTSuma.setEditable(false);
        textTIva = new JTextField("0.00");
        textTIva.setEditable(false);
        textTIrpf = new JTextField("0.00");
        textTIrpf.setEditable(false);
        textTTotal = new JTextField("0.00");
        textTTotal.setEditable(false);

        // COMBO BOX
        peritos = new JComboBox();
        anoLiquidacion = new JYearChooser();
        mesLiquidacion = new JMonthChooser();

        // TABLA LIQUIDACION
        String [] headerStats = {"Cod", "Compañia", "Referencia Cia", "F.Entrega", "Honorarios", "locomoción","Ajustes", "Total", "Tipo"};
        modelRegLiquidacion = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        for (String column : headerStats) modelRegLiquidacion.addColumn(column);
        tabRegLiquidacion = new JTable(modelRegLiquidacion);
        tabRegLiquidacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

                    int mesLiq = mesLiquidacion.getMonth();
                    int anoLiq = anoLiquidacion.getYear();
                    ArrayList<Registro> liq = getRegistrosLiqPerito(mesLiq, anoLiq);
                    getLiquidacion(liq);
                    chargeRegTable(liq);
                    if (liq.size() > 0) generate = true;
                    Config.pControl.pControl.changeUltimaAccion("LIQUIDACIÓN DE PERITO GENERADA... ");


            }
        });

        pdf = new JButton("Generar Pdf");
        pdf.setBackground(Config.COLOR4);
        pdf.setForeground(Color.WHITE);
        pdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selPerito = (String) peritos.getSelectedItem();
                if (generate) {
                    if (!selPerito.equals("TODOS")) {
                        DataPDF data = new DataPDF();
                        data.setNombrePerito(selPerito);
                        int mesLiq = mesLiquidacion.getMonth();
                        int anoLiq = anoLiquidacion.getYear();
                        ArrayList<Registro> liq = getRegistrosLiqPerito(mesLiq, anoLiq);
                        data.setRegistros(liq);
                        data.setHonorarios(textTHonor.getText());
                        data.setLocomocion(textTLocom.getText());
                        data.setAjustes(textTAjustes.getText());
                        data.setSuma(textTSuma.getText());
                        data.setIva(textTIva.getText());
                        data.setIrpf(textTIrpf.getText());
                        data.setTotal(textTTotal.getText());
                        data.mes = mesLiq;
                        data.ano = anoLiq;
                        generarPDF(data);
                        Config.pControl.pControl.changeUltimaAccion("PDF LIQUIDACIÓN DE: \'"+selPerito+"\' GENERADA EN DIRECTORIO... ");

                    } else Config.error("Seleccione un Perito de la lista y genere la Liquidación.");
                } else Config.error("Debe generar la liquidación antes de crear el PDF.");
            }
        });

    }

    public void getLiquidacion (ArrayList<Registro> registros){
        double honor = 0;
        double locom = 0;
        double aju = 0;
        double suma = 0;
        double iva = 0;
        double irpf = 0;
        double total = 0;
        for (Registro r : registros){
            honor = honor + r.getFacturaRegistro().gethPerito().getHonorariosPer();
            locom = locom + r.getFacturaRegistro().gethPerito().getLocomocionPer();
            aju = aju + r.getFacturaRegistro().gethPerito().getAjustesPer();
            suma = suma + r.getFacturaRegistro().gethPerito().getSumaPer();
            iva = iva + r.getFacturaRegistro().gethPerito().getIvaPer();
            irpf = irpf + r.getFacturaRegistro().gethPerito().getIrpfPer();
            total = total + r.getFacturaRegistro().gethPerito().getTotalPer();
        }
        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        DecimalFormat formato1 = new DecimalFormat("#.00", s);
        textTHonor.setText(Double.toString(Double.parseDouble(formato1.format(honor))));
        textTLocom.setText(Double.toString(Double.parseDouble(formato1.format(locom))));
        textTAjustes.setText(Double.toString(Double.parseDouble(formato1.format(aju))));
        textTSuma.setText(Double.toString(Double.parseDouble(formato1.format(suma))));
        textTIva.setText(Double.toString(Double.parseDouble(formato1.format(iva))));
        textTIrpf.setText(Double.toString(Double.parseDouble(formato1.format(irpf))));
        textTTotal.setText(Double.toString(Double.parseDouble(formato1.format(total))));
    }

    public void chargeRegTable(ArrayList<Registro> registros){
        int filasRegistro = modelRegLiquidacion.getRowCount();
        for (int i = 0; i < filasRegistro; i++){
            modelRegLiquidacion.removeRow(0);
        }
        registros.sort(new Comparator<Registro>() {
            public int compare(Registro o1, Registro o2) {
                return o1.getFechaEntregaPerito().compareTo(o2.getFechaEntregaPerito());
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
            r = new Object[]{reg.getCodRegistro(), nombreCom, reg.getReferenciaCom(), new SimpleDateFormat("dd/MM/yyyy").format(reg.getFechaEntregaPerito()),
                    reg.getFacturaRegistro().gethPerito().getHonorariosPer(), reg.getFacturaRegistro().gethPerito().getLocomocionPer(),
                    reg.getFacturaRegistro().gethPerito().getAjustesPer(), reg.getFacturaRegistro().gethPerito().getTotalPer(), reg.getTipoRegistro()};
            modelRegLiquidacion.addRow(r);
        }
        numRegistros = registros.size();
        labNRegistros.setText("Nº Registros: "+numRegistros);
    }

    public ArrayList<Registro> getRegistrosLiqPerito(int mes, int ano){
        ArrayList<Registro> liquidacion = new ArrayList<>();
        int codPerito = 0;
        String selPerito = (String) peritos.getSelectedItem();
        if (!selPerito.equals("TODOS")) {
            try {
                for (Perito p : DataTables.getPeritos()) {
                    if (p.getNombrePerito().equals(selPerito)) {
                        codPerito = p.getCodPerito();
                        break;
                    }
                }
                for (Registro r : DataTables.getRegistros()) {
                    if (r.getPerito() == codPerito) {
                        if (r.getEstadoRegistro().equals("CERRADO")) {
                           
                                if (r.getFacturaRegistro().getMesFactPerito() == (mes + 1)) {
                                    String strAno = Integer.toString(ano);
                                    int anoPer = Integer.parseInt(strAno.substring(2, 4));
                                    if (r.getFacturaRegistro().getAnoFactPerito() == anoPer) liquidacion.add(r);
                                }

                        }
                    }
                }
            } catch (Exception e) {
                Config.error("Seleccione un Perito de la lista.");
            }
        } else {
            for (Registro r : DataTables.getRegistros()) {
                if (r.getEstadoRegistro().equals("CERRADO")) {
                    if (r.getEstadoEntrega().equals("ENTREGADO")) {
                        if (r.getFacturaRegistro().getMesFactPerito() == (mes + 1)) {
                            String strAno = Integer.toString(ano);
                            int anoPer = Integer.parseInt(strAno.substring(2, 4));
                            if (r.getFacturaRegistro().getAnoFactPerito() == anoPer) liquidacion.add(r);
                        }
                    }
                }
            }
        }
        return liquidacion;
    }

    public void chargeComboPerito(){
        peritos.removeAllItems();
        peritos.addItem("TODOS");
        for (Perito p : DataTables.getPeritos()){
            if (p.getEstadoPerito().equals("ACTIVO")){
                peritos.addItem(p.getNombrePerito());
            }
        }
    }


    public void generarPDF(DataPDF pdfData){
        try {

            int contador = 0;
            Document doc = new Document(PageSize.A4);
            String dir = "C:\\PeritControl_v1\\PCCli\\ext\\Liquidaciones";
            String nameArchive = "Liq_"+pdfData.getNombrePerito()+"_"+(pdfData.mes+1)+"_"+pdfData.ano;

            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(dir + "\\" + nameArchive + ".pdf"));
            doc.open();

            Font fuenteTitulo = new Font(FontFactory.getFont(FontFactory.HELVETICA, 20, Font.UNDERLINE, BaseColor.BLACK));
            Font fuenteEncabezado = new Font(FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK));
            Font fuente = new Font(FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK));
            Font fuenteSmall = new Font(FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK));

            //ENCABEZADO
            Chunk titulo = new Chunk("Liquidación Perito", fuenteTitulo);
            Paragraph compania = new Paragraph("Liquidación Gonzalo Peritaciones SL.", fuenteEncabezado);
            Paragraph fecha = new Paragraph("Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()), fuenteEncabezado);
            Paragraph perito = new Paragraph("Perito: " + pdfData.getNombrePerito(), fuenteEncabezado);
            Paragraph separador = new Paragraph("_________________________________________________________________", fuenteEncabezado);

            Rectangle rect = new Rectangle(10, 10, 20, 10);
            PdfContentByte canvas = writer.getDirectContent();
            rect.setBackgroundColor(new BaseColor(Config.COLOR6));
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(2);
            canvas.rectangle(rect);
            canvas.fillStroke();
            //LOGO
            Image foto = Image.getInstance("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoclipdf.jpg");
            foto.scaleToFit(220, 66);
            foto.setAlignment(Chunk.ALIGN_RIGHT);


            //ESTRUCTURA ENCABEZADO DOCUMENTO
            doc.add(titulo);
            doc.add(foto);
            doc.add(compania);
            doc.add(fecha);
            doc.add(perito);
            doc.add(separador);


            //TABLA LIQUIDACION
            PdfPTable tabLiquidacion = new PdfPTable(2);
            tabLiquidacion.setWidthPercentage(60);
            tabLiquidacion.setWidths(new float[]{35, 15});
            tabLiquidacion.setHorizontalAlignment(Element.ALIGN_LEFT);
            Paragraph resumen = new Paragraph("Resumen:", fuente);
            PdfPCell honor = new PdfPCell(new Paragraph("Honorarios:", fuente));
            honor.setHorizontalAlignment(Element.ALIGN_CENTER);
            honor.setBorderWidth(0);
            PdfPCell totHonor = new PdfPCell(new Paragraph(pdfData.getHonorarios(), fuente));
            PdfPCell locom = new PdfPCell(new Paragraph("Locomoción:", fuente));
            locom.setHorizontalAlignment(Element.ALIGN_CENTER);
            locom.setBorderWidth(0);
            PdfPCell totLocom = new PdfPCell(new Paragraph(pdfData.getLocomocion(), fuente));
            PdfPCell ajust = new PdfPCell(new Paragraph("Ajustes:", fuente));
            ajust.setHorizontalAlignment(Element.ALIGN_CENTER);
            ajust.setBorderWidth(0);
            PdfPCell totAjust = new PdfPCell(new Paragraph(pdfData.getAjustes(), fuente));
            PdfPCell suma = new PdfPCell(new Paragraph("Suma sin IVA:", fuente));
            suma.setHorizontalAlignment(Element.ALIGN_CENTER);
            suma.setBorderWidth(0);
            PdfPCell totSuma = new PdfPCell(new Paragraph(pdfData.getSuma(), fuente));
            PdfPCell iva = new PdfPCell(new Paragraph("IVA:", fuente));
            iva.setHorizontalAlignment(Element.ALIGN_CENTER);
            iva.setBorderWidth(0);
            PdfPCell totIva = new PdfPCell(new Paragraph(pdfData.getIva(), fuente));
            PdfPCell irpf = new PdfPCell(new Paragraph("IRPF:", fuente));
            irpf.setHorizontalAlignment(Element.ALIGN_CENTER);
            irpf.setBorderWidth(0);
            PdfPCell totIrpf = new PdfPCell(new Paragraph(pdfData.getIrpf(), fuente));
            PdfPCell total = new PdfPCell(new Paragraph("Total:", fuente));
            total.setHorizontalAlignment(Element.ALIGN_CENTER);
            total.setBorderWidth(0);
            PdfPCell totTotal = new PdfPCell(new Paragraph(pdfData.getTotal(), fuente));

            tabLiquidacion.addCell(honor);
            tabLiquidacion.addCell(totHonor);
            tabLiquidacion.addCell(locom);
            tabLiquidacion.addCell(totLocom);
            tabLiquidacion.addCell(ajust);
            tabLiquidacion.addCell(totAjust);
            tabLiquidacion.addCell(suma);
            tabLiquidacion.addCell(totSuma);
            tabLiquidacion.addCell(iva);
            tabLiquidacion.addCell(totIva);
            tabLiquidacion.addCell(irpf);
            tabLiquidacion.addCell(totIrpf);
            tabLiquidacion.addCell(total);
            tabLiquidacion.addCell(totTotal);

            doc.add(resumen);
            doc.add(tabLiquidacion);
            doc.add(separador);

            //TABLA CIERRES
            Paragraph reg = new Paragraph("Registros:", fuente);
            reg.setAlignment(Element.ALIGN_LEFT);

            PdfPTable tabCierre = new PdfPTable(2);
            tabCierre.setWidthPercentage(40);
            tabCierre.setWidths(new float[]{25, 15});
            PdfPCell cierres = new PdfPCell(new Paragraph("Nº Cierres: ", fuente));
            cierres.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cierres.setBorderWidth(0);
            PdfPCell numCierres = new PdfPCell(new Paragraph(Integer.toString(pdfData.getRegistros().size()), fuente));
            numCierres.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabCierre.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tabCierre.addCell(cierres);
            tabCierre.addCell(numCierres);

            doc.add(reg);
            doc.add(tabCierre);

            //TABLA REGISTROS
            PdfPCell[] colums = {new PdfPCell(new Paragraph("Código", fuenteSmall)),
                    new PdfPCell(new Paragraph("Compañia", fuenteSmall)),
                    new PdfPCell(new Paragraph("Referencia", fuenteSmall)),
                    new PdfPCell(new Paragraph("Entrega", fuenteSmall)),
                    new PdfPCell(new Paragraph("Honorarios", fuenteSmall)),
                    new PdfPCell(new Paragraph("Locomoción", fuenteSmall)),
                    new PdfPCell(new Paragraph("Ajustes", fuenteSmall)),
                    new PdfPCell(new Paragraph("Total", fuenteSmall))};

            PdfPTable tabRegistros = new PdfPTable(8);
            tabRegistros.setWidthPercentage(94);
            tabRegistros.setWidths(new float[]{8, 12, 18, 12, 10, 12, 10, 12});
            for (PdfPCell p : colums) {
                p.setHorizontalAlignment(Element.ALIGN_CENTER);
                p.setBorderWidth(0);
                tabRegistros.addCell(p);
            }
            chargeRegs(tabRegistros, pdfData.getRegistros());

            doc.add(Chunk.NEWLINE);
            doc.add(tabRegistros);

            doc.close();
            Config.info("Se ha generado la Liquidación del Perito \"" + pdfData.getNombrePerito() + "\" en la carpeta Liquidaciones.");


        } catch (Exception e) {
            Config.error("El archivo no se ha podido crear porque otra applicación lo esta ejecutando.");
        }
    }

    public void chargeRegs(PdfPTable tab, ArrayList<Registro> registros){
        registros.sort(new Comparator<Registro>() {
            public int compare(Registro o1, Registro o2) {
                return o1.getFechaEntregaPerito().compareTo(o2.getFechaEntregaPerito());
            }
        });
        Font font = new Font(FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK));
        for (Registro r : registros){
            PdfPCell cod = new PdfPCell(new Paragraph(String.valueOf(r.getCodRegistro()), font));
            String compania = "";
            for (Compania c : DataTables.getCompanias()) if (c.getCodCompania() == r.getSiniestro().getCompania()){
                compania = c.getNombreCompania();
                break;
            }
            PdfPCell com = new PdfPCell(new Paragraph(compania, font));
            PdfPCell ref = new PdfPCell(new Paragraph(r.getReferenciaCom(), font));
            PdfPCell ent = new PdfPCell(new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(r.getFechaEntregaPerito()), font));
            PdfPCell honor = new PdfPCell(new Paragraph(Double.toString(r.getFacturaRegistro().gethPerito().getHonorariosPer()), font));
            PdfPCell loc = new PdfPCell(new Paragraph(Double.toString(r.getFacturaRegistro().gethPerito().getLocomocionPer()), font));
            PdfPCell ajus = new PdfPCell(new Paragraph(Double.toString(r.getFacturaRegistro().gethPerito().getAjustesPer()), font));
            PdfPCell tot = new PdfPCell(new Paragraph(Double.toString(r.getFacturaRegistro().gethPerito().getTotalPer()), font));

            tab.addCell(cod);
            tab.addCell(com);
            tab.addCell(ref);
            tab.addCell(ent);
            tab.addCell(honor);
            tab.addCell(loc);
            tab.addCell(ajus);
            tab.addCell(tot);



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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Config.COLOR3);
        g.fillRect(0, 0, 280, 500);
        g.setColor(Config.COLOR1);
        g.fillRect(310, 0, 590, 295);

    }
}
