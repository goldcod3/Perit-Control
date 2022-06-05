package interfaces.icontrol.pperitos.ifichaperito;

import clases.Perito;
import conexion.DataTables;
import conexion.hilos.SentPeritPacket;
import conexion.paquetes.PeritPacket;
import interfaces.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelNPerito extends JPanel {
    private Perito perito = new Perito();

    public int codPerito = 1;
    public JLabel codigoP = new JLabel("COD - 0" + codPerito);
    public JLabel datosPerito, nombre, nif, direccion,
            telefono, email, estado;
    public JTextField nombreBox, nifBox, direccionBox, telefonoBox, emailBox = new JTextField();
    public JComboBox comboEstado;
    public JButton btnGuardar;


    public PanelNPerito(Perito per) {
        setBackground(Config.COLOR2);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagLayout gbl_content = new GridBagLayout();
        gbl_content.columnWidths = new int[]{0};
        gbl_content.rowHeights = new int[]{0};
        gbl_content.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_content.rowWeights = new double[]{Double.MIN_VALUE};
        setLayout(gbl_content);

        initLabels();
        initComponents();
        chargePerito(per);

        //LAYER DATOS PERITOS
        GridBagConstraints grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 0, 0);
        grid.insets = new Insets(5, 10, 10, 0);
        grid.gridwidth = 2;
        add(datosPerito, grid);

        //LAYER CODIGO
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.NONE, 3, 0);
        grid.insets = new Insets(5, 10, 10, 0);
        add(codigoP, grid);

        //LAYER NOMBRE
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE, 0, 1);
        add(nombre, grid);

        //CAMPO NOMBRE
        grid = newPosicion(GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, 1, 1);
        grid.insets = new Insets(0, 10, 2, 0);
        grid.gridwidth = 2;
        grid.weightx = 0.09;
        add(nombreBox, grid);

        //LAYER NIF
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE, 0, 2);
        add(nif, grid);

        //CAMPO NIF
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 2);
        grid.insets = new Insets(0, 10, 2, 0);
        grid.gridwidth = 2;
        grid.weightx = 0.09;
        add(nifBox, grid);

        //LAYER DIRECCION
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE, 0, 3);
        add(direccion, grid);

        //CAMPO DIRECCION
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 3);
        grid.insets = new Insets(0, 10, 2, 0);
        grid.gridwidth = 2;
        add(direccionBox, grid);

        //LAYER TELEFONO
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE, 0, 4);
        add(telefono, grid);

        //CAMPO TELEFONO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 4);
        grid.insets = new Insets(0, 10, 2, 0);
        grid.gridwidth = 2;
        add(telefonoBox, grid);

        //LAYER EMAIL
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE, 0, 5);
        add(email, grid);

        //CAMPO EMAIL
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 5);
        grid.insets = new Insets(0, 10, 2, 0);
        grid.gridwidth = 2;
        add(emailBox, grid);

        //LAYER ESTADO
        grid = newPosicion(GridBagConstraints.CENTER,
                GridBagConstraints.NONE, 0, 6);
        add(estado, grid);

        //CAMPO ESTADO
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.NONE, 1, 6);
        grid.insets = new Insets(0, 10, 0, 0);
        add(comboEstado, grid);

        //BOTON GUARDAR
        grid = newPosicion(GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 3, 10);
        grid.insets = new Insets(10, 0, 0, 0);

        //grid.gridwidth = 3;
        add(btnGuardar, grid);
    }


    public void initLabels() {
        datosPerito = new JLabel("DATOS PERITO");
        datosPerito.setFont(Config.FUENTETITULO);
        nombre = new JLabel("Nombre:");
        nif = new JLabel("NIF:");
        direccion = new JLabel("Dirección:");
        telefono = new JLabel("Telefono:");
        email = new JLabel("Email:");
        estado = new JLabel("Estado:");

    }

    public void initComponents() {
        codigoP = new JLabel();
        nombreBox = new JTextField();
        nifBox = new JTextField();
        direccionBox = new JTextField();
        telefonoBox = new JTextField();
        emailBox = new JTextField();
        String[] estados = {"ACTIVO", "INACTIVO"};
        comboEstado = new JComboBox(estados);
        comboEstado.setPreferredSize(new Dimension(140, 25));
        comboEstado.setBackground(Color.WHITE);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(Config.COLOR4);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (perito.getCodPerito() == 0){
                    if (nombreBox.getText().isEmpty()) {
                        Config.warning("Debe introducir por lo menos el nombre del perito.");
                    } else {
                        if (verifTelefono()) {
                            boolean valid = true;
                            for (Perito p : DataTables.getPeritos()) {
                                if (p.getNombrePerito().equalsIgnoreCase(nombreBox.getText())) {
                                    valid = false;
                                }
                            }
                            if (!valid) {
                                Config.error("El Perito ya existe en la base de datos.");
                            } else {
                                Perito p = getPanelPerito();
                                if (!p.equals(new Perito())) {
                                    if (comparePerito(p)) {
                                        Config.warning("No se han detectado cambios en la ficha del Perito.");
                                    } else {
                                        PeritPacket peritPacket = new PeritPacket(1, Config.getUser(), Config.getIpClient());
                                        peritPacket.setPerito(p);
                                        Thread sent = new Thread( new SentPeritPacket(peritPacket));
                                        sent.start();

                                    }

                                } else Config.error("Alguno de los campos introducidos es inválido.");
                            }

                        } else Config.error("El formato del número telefónico es incorrecto.");
                    }

                }else {
                    if(!nombreBox.getText().isEmpty()){
                        if(!comparePerito(getPanelPerito())){
                            PeritPacket peritPacket = new PeritPacket(2, Config.getUser(), Config.getIpClient());
                            peritPacket.setPerito(getPanelPerito());
                            Thread sent = new Thread( new SentPeritPacket(peritPacket));
                            sent.start();
                        }else{
                            Config.warning("No se han detectado modificaciones en la ficha del Perito.");
                        }
                    }else {
                        Config.error("El nombre del Perito no puede estar vacío.");
                    }
                }
            }
        });
        codigoP.setFont(Config.FUENTETITULO);

    }

    public Perito getPanelPerito() {
        Perito per = new Perito();
        try {
            per.setCodPerito(perito.getCodPerito());
            per.setNombrePerito(nombreBox.getText());
            if(nifBox.getText().isEmpty()) per.setNifPerito("");
            else per.setNifPerito(nifBox.getText());
            if (direccionBox.getText().isEmpty()) per.setDirPerito("");
            else per.setDirPerito(direccionBox.getText());
            if(emailBox.getText().isEmpty()) per.setEmailPerito("");
            else per.setEmailPerito(emailBox.getText());
            per.setEstadoPerito((String) comboEstado.getSelectedItem());
            if (verifTelefono()){
                int telefonoPer = Integer.parseInt(telefonoBox.getText());
                per.setTelfPerito(telefonoPer);
            } else{
                Config.error("El teléfono es inválido");
                per.setTelfPerito(0);
            }

        } catch (Exception e) {
            Config.error("Error PanelNPerito.getPanelPerito.\n" +
                    "Contacte con el Administrador del Sistema.");
        }
        return per;
    }



    public boolean comparePerito(Perito target) {
        boolean valid = true;
        try {
            if (!perito.getNombrePerito().equals(target.getNombrePerito())) valid = false;
            if (!perito.getNifPerito().equals(target.getNifPerito())) valid = false;
            if (!perito.getDirPerito().equals(target.getDirPerito())) valid = false;
            if (!perito.getEmailPerito().equals(target.getEmailPerito())) valid = false;
            if (!perito.getEstadoPerito().equals(target.getEstadoPerito())) valid = false;
            if (perito.getTelfPerito() != target.getTelfPerito()) valid = false;
        }catch (Exception e){
            valid = false;
        }
        return valid;
    }

    public void chargePerito(Perito per) {
        Perito perit = per;
        perito = per;
        codigoP.setText("COD - 0" + perit.getCodPerito());
        nombreBox.setText(perit.getNombrePerito());
        nifBox.setText(perit.getNifPerito());
        direccionBox.setText(perit.getDirPerito());
        telefonoBox.setText(Integer.toString(perit.getTelfPerito()));
        emailBox.setText(perit.getEmailPerito());
        if (per.getEstadoPerito().equals("ACTIVO")) comboEstado.setSelectedIndex(0);
        else comboEstado.setSelectedIndex(1);

    }

    public boolean verifTelefono() {
        boolean valid = false;
        try {
            if (telefonoBox.getText().equals("0")) {
                valid = true;
            } else {
                if (telefonoBox.getText().length() > 4 && telefonoBox.getText().length() <= 9) {
                    int telefonoPer = Integer.parseInt(telefonoBox.getText());
                    valid = true;
                } else valid = false;

            }

        } catch (Exception e) {
            valid = false;
        }
        return valid;
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

    public Perito getPerito() {
        return perito;
    }

    public void setPerito(Perito perito) {
        this.perito = perito;
    }
}
