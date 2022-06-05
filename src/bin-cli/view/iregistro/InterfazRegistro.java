package interfaces.iregistro;

import clases.Reapertura;
import clases.Registro;
import clases.TipoReapertura;
import conexion.DataTables;
import conexion.hilos.SentSafePacket;
import conexion.paquetes.SafePacket;
import interfaces.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class InterfazRegistro extends JFrame {
    //GESTOR DE INTERFACES

    //Arrat de interfaces Registro
    public static ArrayList<InterfazRegistro> interfaces = new ArrayList<>();

    //Contador de asignacion de id's
    public static int contadorInterfaz;

    //Propiedades
    private int idInterfaz = 0;
    public PRegistro contenedorReg;
    public PReapertura contenedorReap;
    public Registro reg = new Registro();

    public InterfazRegistro(Registro registro){
        //Asignacion de id a la interfaz
        this.setIdInterfaz(++contadorInterfaz);
        this.reg = registro;
        int num = this.getIdInterfaz();

        //Propiedades de la ventana
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(Config.anchoPantalla, Config.alturaPantalla));
        setLocation(Config.anchoPantalla - 22, 0);
        setResizable(true);
        contenedorReg = new PRegistro(registro, this.getIdInterfaz());

        //Titulo de la ventana
        if(registro.getCodRegistro() == 0){
            setTitle("PeritControl - Nuevo Registro");
        }else setTitle("PeritControl - Registro "+registro.getCodRegistro());

        //Evento cierre de ventana
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Registro t = contenedorReg.getRegistroFrame();
                if (t.getEstadoEntrega().equals("ENTREGADO")) {
                    contenedorReg.validarFacturaPerito();
                    t.getFacturaRegistro().setMesFactPerito(Integer.parseInt(contenedorReg.textFMes.getText()));
                    t.getFacturaRegistro().setAnoFactPerito(Integer.parseInt(contenedorReg.textFAno.getText()));
                }

                if (contenedorReg.compareRegistro(t)){
                    InterfazRegistro.deleteInterface(num);

                }else{
                    int i = Config.option("No ha guardado los cambios, se cerrará la ventana.\n" +
                            "¿Quiere guardar y cerrar el Registro?");
                    if ( i == 1){
                       InterfazRegistro.deleteInterface(num);

                    } else {
                        if (!contenedorReg.boxPerito.getSelectedItem().equals("Default")) {
                            if (contenedorReg.textNPoliza.getText().length() > 1) {
                                if (contenedorReg.textRefCia.getText().length() > 1) {
                                    if (!contenedorReg.boxCompania.getSelectedItem().equals("Default")) {
                                        if (!contenedorReg.boxRamo.getSelectedItem().equals("Default")) {
                                            if (!contenedorReg.boxTipoSin.getSelectedItem().equals("Default")) {
                                                if (!contenedorReg.boxTipoInt.getSelectedItem().equals("Default")) {

                                                    if (contenedorReg.registro.getEstadoRegistro().equals("NUEVO")) {
                                                        SafePacket packet = new SafePacket(1, Config.getUser(), Config.getIpClient());
                                                        packet.setCodVentana(contenedorReg.getIdInterfaz());
                                                        packet.setRegistro(t);
                                                        packet.setSafeAndClose(true);
                                                        Thread saveReg = new Thread(new SentSafePacket(packet));
                                                        saveReg.start();
                                                    } else {
                                                        SafePacket packet = new SafePacket(2, Config.getUser(), Config.getIpClient());
                                                        packet.setCodVentana(contenedorReg.getIdInterfaz());
                                                        packet.setRegistro(t);
                                                        packet.setSafeAndClose(true);
                                                        Thread saveReg = new Thread(new SentSafePacket(packet));
                                                        saveReg.start();
                                                    }

                                                } else Config.warning("Debe seleccionar una opción para el campo Tipo Intervención.");
                                            } else Config.warning("Debe seleccionar una opción para el campo Tipo Siniestro.");
                                        } else Config.warning("Debe seleccionar una opción para el campo Ramo Compañia.");
                                    } else Config.warning("Debe seleccionar una opción para el campo Compañia.");
                                } else Config.warning("Debe introducir un Número de Referencia.");
                            } else Config.warning("Debe introducir un Número de Póliza.");
                        } else Config.warning("Debe seleccionar una opción para el campo Perito.");
                    }
                }

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

        //Asignacion de panel base
        setContentPane(contenedorReg);
        setVisible(true);
    }

    public InterfazRegistro(Registro asociado, Reapertura reapertura){
        //Asignacion de id a la interfaz
        this.setIdInterfaz(++contadorInterfaz);
        this.reg = asociado;
        int num = this.idInterfaz;

        //Propiedades de la ventana
        setIconImage(new ImageIcon("C:\\PeritControl_v1\\PCCli\\ext\\img\\logoT.png").getImage());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(Config.anchoPantalla, Config.alturaPantalla));
        setLocation(Config.anchoPantalla - 22, 0);
        setResizable(true);
        contenedorReap = new PReapertura(asociado, reapertura, this.getIdInterfaz());

        //Titulo de la ventana
        if(asociado.getCodRegistro() == 0){
            setTitle("PeritControl - Nueva Reapertura");
        }else setTitle("PeritControl - Reapertura "+asociado.getCodRegistro());

        //Evento cierre de ventana
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Registro t = contenedorReap.getRegistroFrame();
                if (t.getEstadoEntrega().equals("ENTREGADO")) {
                    contenedorReap.validarFacturaPerito();
                    t.getFacturaRegistro().setMesFactPerito(Integer.parseInt(contenedorReap.textFMes.getText()));
                    t.getFacturaRegistro().setAnoFactPerito(Integer.parseInt(contenedorReap.textFAno.getText()));
                }
                Reapertura rt = contenedorReap.getReaperturaFrame();

                if (contenedorReap.compareRegistro(t) && contenedorReap.compareReapertura(rt)){
                    InterfazRegistro.deleteInterface(num);

                }else {
                    int i = Config.option("No ha guardado los cambios, se cerrará la ventana.\n" +
                            "¿Quiere guardar y cerrar la Reapertura?");
                    if ( i == 1){
                        InterfazRegistro.deleteInterface(num);
                    } else {
                        if (!contenedorReap.boxTipoReap.getSelectedItem().equals("Default")) {
                            if (!contenedorReap.boxPerito.getSelectedItem().equals("Default")) {
                                if (contenedorReap.textNPoliza.getText().length() > 1) {
                                    if (contenedorReap.textRefCia.getText().length() > 1) {
                                        if (!contenedorReap.boxCompania.getSelectedItem().equals("Default")) {
                                            if (!contenedorReap.boxRamo.getSelectedItem().equals("Default")) {
                                                if (!contenedorReap.boxTipoSin.getSelectedItem().equals("Default")) {
                                                    if (!contenedorReap.boxTipoInt.getSelectedItem().equals("Default")) {

                                                        if (contenedorReap.registro.getEstadoRegistro().equals("NUEVO")) {

                                                            for (TipoReapertura rtt : DataTables.getTipoReaperturas()) {
                                                                if (rtt.getTipoReapertura().equals(contenedorReap.boxTipoReap.getSelectedItem())) {
                                                                    rt.setTipoReapertura(rtt.getCodTipoReapertura());
                                                                    break;
                                                                }
                                                            }
                                                            t.setTipoRegistro("REAPERTURA");
                                                            SafePacket packet = new SafePacket(5, Config.getUser(), Config.getIpClient());
                                                            packet.setCodVentana(contenedorReap.getIdInterfaz());
                                                            packet.setRegistro(t);
                                                            packet.setSafeAndClose(true);
                                                            contenedorReap.rp.setTipoReapertura(rt.getTipoReapertura());
                                                            packet.setReapertura(contenedorReap.rp);
                                                            Thread saveRegN = new Thread(new SentSafePacket(packet));
                                                            saveRegN.start();

                                                        } else {
                                                            for (TipoReapertura rtt : DataTables.getTipoReaperturas()) {
                                                                if (rtt.getTipoReapertura().equals(contenedorReap.boxTipoReap.getSelectedItem())) {
                                                                    contenedorReap.rp.setTipoReapertura(rtt.getCodTipoReapertura());
                                                                    break;
                                                                }
                                                            }
                                                            SafePacket packet = new SafePacket(6, Config.getUser(), Config.getIpClient());
                                                            packet.setCodVentana(contenedorReap.getIdInterfaz());
                                                            packet.setRegistro(t);
                                                            packet.setReapertura(rt);
                                                            packet.setSafeAndClose(true);
                                                            Thread saveReg = new Thread(new SentSafePacket(packet));
                                                            saveReg.start();
                                                        }

                                                    } else
                                                        Config.warning("Debe seleccionar una opción para el campo Tipo Intervención.");
                                                } else
                                                    Config.warning("Debe seleccionar una opción para el campo Tipo Siniestro.");
                                            } else
                                                Config.warning("Debe seleccionar una opción para el campo Ramo Compañia.");
                                        } else Config.warning("Debe seleccionar una opción para el campo Compañia.");
                                    } else Config.warning("Debe introducir un Número de Referencia.");
                                } else Config.warning("Debe introducir un Número de Póliza.");
                            } else Config.warning("Debe seleccionar una opción para el campo Perito.");
                        } else Config.warning("Debe seleccionar una opción para el campo Tipo Reapertura.");
                    }
                }




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

        //Asignacion de panel base
        setContentPane(contenedorReap);
        setVisible(true);
    }

    public static void addInterface(InterfazRegistro target){
        ArrayList<InterfazRegistro> temp = interfaces;
        temp.add(target);
        interfaces = null;
        interfaces = temp;

    }

    public static void deleteInterface(int target){
        for (InterfazRegistro i : interfaces){
            if (i.getIdInterfaz() == target){
                i.dispose();
                interfaces.remove(i);
                break;
            }
        }
    }

    public static boolean existsInterfaceReg(int codRegistro){
        boolean exists = false;
        for (InterfazRegistro i : interfaces){
            if (i.reg.getCodRegistro() == codRegistro){
                exists = true;
                break;
            }
        }

        return exists;
    }

    public static ArrayList<InterfazRegistro> getinterfaces() {
        return interfaces;
    }

    public int getIdInterfaz() {
        return idInterfaz;
    }

    public void setIdInterfaz(int idInterfaz) {
        this.idInterfaz = idInterfaz;
    }

    public static int validIdInt(int num){
        int codigo = num;
        boolean valid = false;
        while (!valid) {
            for (InterfazRegistro i : Config.interfaces) {
                if (i.getIdInterfaz() == codigo) {
                    codigo++;
                    valid = false;
                    break;
                }else valid = true;
            }
        }
        return codigo;
    }

    @Override
    public String toString() {
        return "InterfazRegistro{" +
                "contenedorReg=" + contenedorReg +
                ", contenedorReap=" + contenedorReap +
                ", reg=" + reg +
                ", idInterfaz=" + idInterfaz +
                '}';
    }
}

