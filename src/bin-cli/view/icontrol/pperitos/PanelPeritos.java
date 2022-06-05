package interfaces.icontrol.pperitos;

import clases.Compania;
import clases.Perito;
import clases.Registro;
import conexion.DataTables;
import conexion.hilos.SentTablePacket;
import interfaces.Config;
import interfaces.icontrol.pconfig.iuser.InterfazVerifyUser;
import interfaces.icontrol.pperitos.iestadisticas.InterfazEstadisticas;
import interfaces.icontrol.pperitos.ifichaperito.InterfazPerito;
import interfaces.icontrol.pperitos.iliquidacion.InterfazLiquidacion;
import interfaces.iregistro.eventos.HiloPRegistro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;

public class PanelPeritos extends JPanel {

    private JLabel peritos, registros, imgPerito;
    private JButton estadisticas, liquidacion, cargarRegistros,
            abrirFicha, abrirRegistro, actualizar, nuevoPerito;
    public JRadioButton activos, todos;
    public ButtonGroup rGroup;

    public DefaultTableModel modelPeritos, modelRegistros;
    public JTable tabPeritos, tabRegistros;
    private JScrollPane scrollPeritos, scrollRegistros;

    private Perito perRegistros, perFicha;
    private static ArrayList<Perito> PerRegTable = new ArrayList<>();
    private static ArrayList<Perito> PerFicTable = new ArrayList<>();


    public PanelPeritos(){
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10,10,10,10));

        initLabels();
        initButtons();
        initTables();

        //LAYOUT
        GridBagLayout gbl_content = new GridBagLayout();
        gbl_content.columnWidths = new int[]{0};
        gbl_content.rowHeights = new int[]{0};
        gbl_content.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_content.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_content);

        //LAYER PERITOS
        GridBagConstraints grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE,0,0);
        grid.insets = new Insets(20,10,0,0);
        grid.weightx = 0.09;
        add(peritos, grid);

        //IMAGEN PERITO
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,0,0);
        grid.gridheight = 2;
        add(imgPerito, grid);

        //BOTON NUEVO PERITO
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.VERTICAL,1,0);
        grid.insets = new Insets(0,0,0,10);
        grid.gridheight = 2;
        add(nuevoPerito, grid);

        //RBUTTON ACTIVOS
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,4,0);
        grid.insets = new Insets(0,20,5,0);
        add(activos, grid);

        //RBUTTON TODOS
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE,4,1);
        grid.insets = new Insets(0,16,5,0);
        add(todos, grid);

        //BOTON ESTADISTICAS
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.VERTICAL,2,0);
        grid.gridheight = 2;
        add(estadisticas, grid);

        //BOTON LIQUIDACION
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.VERTICAL,3,0);
        grid.insets = new Insets(0,0,0,10);
        grid.gridheight = 2;
        add(liquidacion, grid);

        //BOTON ACTUALIZAR
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.VERTICAL,5,0);
        grid.gridheight = 2;
        add(actualizar, grid);

        //TABLA PERITOS
        grid = newPosicion(GridBagConstraints.NORTH,
                GridBagConstraints.BOTH,0,2);
        grid.insets = new Insets(10,0,8,0);
        grid.gridwidth = 6;
        grid.gridheight = 2;
        grid.weightx = 0.09;
        grid.weighty = 0.09;
        add(scrollPeritos, grid);

        //BOTON CARGAR REGISTROS
        grid = newPosicion(GridBagConstraints.NORTHEAST,
                GridBagConstraints.NONE,3,5);
        add(cargarRegistros, grid);

        //BOTON ABRIR FICHA
        grid = newPosicion(GridBagConstraints.NORTHEAST,
                GridBagConstraints.NONE,5,5);
        add(abrirFicha, grid);

        //LAYER REGISTROS
        grid = newPosicion(GridBagConstraints.NORTHWEST,
                GridBagConstraints.NONE,0,5);
        grid.insets = new Insets(30,10,0,0);
        grid.gridwidth = 2;
        add(registros, grid);

        //TABLA REGISTROS
        grid = newPosicion(GridBagConstraints.NORTH,
                GridBagConstraints.BOTH,0,6);
        grid.insets = new Insets(10,0,10,0);
        grid.gridwidth = 6;
        grid.gridheight = 2;
        grid.weightx = 0.09;
        grid.weighty = 0.09;
        add(scrollRegistros, grid);

        //BOTON ABRIR REGISTRO
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE,5,9);
        add(abrirRegistro, grid);





    }

    public void initLabels(){
        peritos = new JLabel("PERITOS");
        peritos.setFont(Config.FUENTETITULO);
        Font fontp= peritos.getFont();
        Map attributesp = fontp.getAttributes();
        attributesp.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        peritos.setFont(fontp.deriveFont(attributesp));

        registros = new JLabel("REGISTROS ASOCIADOS AL PERITO");
        registros.setFont(Config.FUENTETITULO);
        Font fontr= registros.getFont();
        Map attributesr = fontr.getAttributes();
        attributesr.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        registros.setFont(fontr.deriveFont(attributesr));

        imgPerito = new JLabel();
        imgPerito.setIcon( new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\perito.png"));
    }

    public void initButtons(){
        estadisticas = new JButton("Estadísticas");
        estadisticas.setPreferredSize(new Dimension(160,50));
        estadisticas.setBackground(Config.AUX5);
        estadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Config.pEstadisticas == null){
                    Config.pEstadisticas = new InterfazEstadisticas();
                } else Config.warning("Existe una ventana de Estadísticas Abierta.");
            }
        });

        liquidacion = new JButton("Liquidación ");
        liquidacion.setPreferredSize(new Dimension(160,50));
        liquidacion.setBackground(Config.AUX3);
        liquidacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Config.pLiquidacion == null){
                    Config.pLiquidacion = new InterfazLiquidacion();
                } else Config.warning("Existe una ventana de Liquidación Abierta.");
            }
        });


        cargarRegistros = new JButton("Cargar Registros");
        cargarRegistros.setPreferredSize(new Dimension(160,50));
        cargarRegistros.setBackground(Config.COLOR5);
        cargarRegistros.setForeground(Color.WHITE);
        cargarRegistros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabPeritos.getSelectedRow() != -1) {
                    cleanTablaPerReg();
                    int codigo = (int) modelPeritos.getValueAt(tabPeritos.getSelectedRow(), 0);
                    for (Registro reg : DataTables.getRegistros()){
                        if (reg.getPerito() == codigo){
                            String compania = "";
                            for(Compania com : DataTables.getCompanias()){
                                if (com.getCodCompania() == reg.getSiniestro().getCompania()){
                                    compania = com.getNombreCompania();
                                }
                            }


                            Object [] registro = {reg.getCodRegistro(), compania, reg.getReferenciaCom(), reg.getEstadoRegistro(),
                                    reg.getFechaApertura(), reg.getFacturaRegistro().getEstadoFactura(), reg.getTipoRegistro()};

                            modelRegistros.addRow(registro);


                        }
                    }
                } else {
                    Config.error("Seleccione un Perito.");
                }

                int a = tabPeritos.getSelectedRow();
            }
        });

        abrirFicha = new JButton("Abrir Ficha");
        abrirFicha.setPreferredSize(new Dimension(160,50));
        abrirFicha.setBackground(Config.AUX4);
        abrirFicha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(Config.pPerito == null){
                        int codigo = (int) modelPeritos.getValueAt(tabPeritos.getSelectedRow(), 0);
                        for (Perito per : DataTables.getPeritos()) {
                            if (per.getCodPerito() == codigo) {
                                Config.pPerito = new InterfazPerito(per);
                            }
                        }

                    } else {
                        Config.error("Cierre la ventana de \"Ficha Perito\" para abrir una nueva.");
                    }

                }catch (Exception ex){
                    Config.error("Seleccione un Perito.");
                }
            }
        });


        abrirRegistro = new JButton("Abrir Registro");
        abrirRegistro.setPreferredSize(new Dimension(160,50));
        abrirRegistro.setBackground(Config.AUX4);
        abrirRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread pr = new Thread(new HiloPRegistro());
                pr.start();
            }
        });

        actualizar = new JButton();
        actualizar.setBackground(Config.AUX5);
        actualizar.setPreferredSize(new Dimension(160,50));
        actualizar.setIcon(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\act.png"));
        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataTables.cleanTablesClient();
                DataTables.cleanRegistros();
                Thread sendPacket = new Thread(new SentTablePacket());
                sendPacket.start();
                DataTables.actTablasPanelControl();
                System.gc();
            }
        });

        nuevoPerito = new JButton("Nuevo Perito");
        nuevoPerito.setPreferredSize(new Dimension(160,50));
        nuevoPerito.setBackground(Config.AUX2);
        nuevoPerito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Config.pPerito == null){
                    Config.pPerito = new InterfazPerito(new Perito(0, "",
                            "", "", 0, "", "ACTIVO"));

                } else {
                    Config.error("Cierre la ventana de \"Ficha Perito\" para abrir una nueva.");
                }
            }
        });


        activos = new JRadioButton("Activos");
        activos.setBackground(Config.COLOR2);
        todos = new JRadioButton("Todos");
        todos.setBackground(Config.COLOR2);

        rGroup = new ButtonGroup();
        rGroup.add(activos);
        rGroup.add(todos);
        activos.setSelected(true);


    }

    public void initTables(){

        //TABLA PERITOS
        String [] headerPeritos = {"Codigo", "Nombre", "Telefono", "Email", "Nº Registros"};
        modelPeritos = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        for (String c : headerPeritos) modelPeritos.addColumn(c);
        tabPeritos = new JTable(modelPeritos);
        tabPeritos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tabPeritos.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabPeritos.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabPeritos.getColumnModel().getColumn(3).setPreferredWidth(180);
        tabPeritos.getColumnModel().getColumn(4).setPreferredWidth(30);
        tabPeritos.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tabPeritos.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tabPeritos.getColumnModel().getColumn(3).setCellRenderer(tcr);
        tabPeritos.getColumnModel().getColumn(4).setCellRenderer(tcr);
        scrollPeritos = new JScrollPane(tabPeritos);

        //TABLA REGISTROS
        String [] headerRegistro = {"Codigo", "Compañia", "Referencia Cia", "Estado", "Fecha", "Pago", "Tipo"};
        modelRegistros = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        for (String c : headerRegistro) modelRegistros.addColumn(c);

        tabRegistros = new JTable(modelRegistros);
        tabRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabRegistros.getColumnModel().getColumn(0).setPreferredWidth(10);
        scrollRegistros = new JScrollPane(tabRegistros);
    }

    public static void cleanTablaPeritos() {
        int filasRegistro = Config.pControl.pPeritos.modelPeritos.getRowCount();
        for (int i = 0; i < filasRegistro; i++){
            Config.pControl.pPeritos.modelPeritos.removeRow(0);
        }
    }

    public static void cleanTablaPerReg() {
        int filasRegistro = Config.pControl.pPeritos.modelRegistros.getRowCount();
        for (int i = 0; i < filasRegistro; i++){
            Config.pControl.pPeritos.modelRegistros.removeRow(0);
        }
    }

    /*
     * Metodo que posiciona el GridBagConstrain del Layout para introducir el componente en la ventana.
     */
    public static GridBagConstraints newPosicion(int anchor, int fill, int gridx, int gridy){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        return gbc;
    }

    public Perito getPerRegistros() {
        return perRegistros;
    }

    public void setPerRegistros(Perito perRegistros) {
        this.perRegistros = perRegistros;
    }

    public Perito getPerFicha() {
        return perFicha;
    }

    public void setPerFicha(Perito perFicha) {
        this.perFicha = perFicha;
    }

    public static ArrayList<Perito> getPerRegTable() {
        return PerRegTable;
    }

    public static void setPerRegTable(ArrayList<Perito> perRegTable) {
        PerRegTable = perRegTable;
    }

    public static ArrayList<Perito> getPerFicTable() {
        return PerFicTable;
    }

    public static void setPerFicTable(ArrayList<Perito> perFicTable) {
        PerFicTable = perFicTable;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Config.COLOR6);
        g.fillRect(Config.anchoPantalla -100, 0, 120, Config.alturaPantalla);

    }
}
