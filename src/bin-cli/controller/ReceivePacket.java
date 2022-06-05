package conexion;

import clases.*;
import conexion.hilos.SentTablePacket;
import conexion.paquetes.*;
import interfaces.Config;
import interfaces.icontrol.InterfazPControl;
import interfaces.icontrol.pperitos.ifichaperito.InterfazPerito;
import interfaces.iregistro.InterfazRegistro;

import java.awt.*;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/*
 * Hilo que pone a la escucha el puerto seleccionado para la comunicacion con el servidor.
 */
public class ReceivePacket implements Runnable {

    @Override
    public void run() {
        //falta puerto configcli

        try {
            ServerSocket misrv = new ServerSocket(4433);
            while (true) {
                Socket socket = misrv.accept();
                ObjectInputStream fluIn = new ObjectInputStream(socket.getInputStream());
                Object packet = fluIn.readObject();
                if (packet.getClass() == DataPacket.class){
                    if(!((DataPacket) packet).isValidUser()){
                        Config.login.pLogin.login.setBackground(Config.AUX1);
                        Config.error("El Usuario es invalido, intentelo de nuevo.");
                    }

                }else if (packet.getClass() == TablePacket.class){

                    TablePacket tabPacket = (TablePacket) packet;

                    if(tabPacket.getCodPacket() == 2){
                        //el usuario ya ha sido verificado y se cargan las tablas adjuntas al paquete
                        Config.login.pLogin.login.setBackground(Config.AUX3);
                        tabPacket.extractDataPacket();
                        Config.setUser(tabPacket.getUser());
                        Config.getUser().setPassUsr("-");
                        Config.login.dispose();
                        if (Config.pControl == null) Config.pControl = new InterfazPControl();
                        DataTables.chargeTablesClient();
                        DataTables.cleanBusqueda();

                    }else if(tabPacket.getCodPacket() == 3){
                        Config.pControl.pControl.changeUltimaAccion("ACTUALIZANDO. .");
                        DataTables.cleanDataTables();
                        tabPacket.extractDataPacket();
                        DataTables.actTablasPanelControl();
                        Config.pControl.pControl.actualizar.setBackground(Config.AUX5);
                        Config.pControl.pControl.actComboCompania();
                        Config.pControl.pControl.actNumeroRegistros();
                        Config.pControl.pControl.changeUltimaAccion("TABLAS DE DATOS ACTUALIZADAS");
                        System.gc();
                    }

                }else if(packet.getClass() == VerifyPacket.class){
                    VerifyPacket verifyPacket = (VerifyPacket) packet;
                    if(verifyPacket.isValidUser()){
                        Config.pVerifyUsr.dispose();
                        Config.pVerifyUsr = null;
                        Config.pControl.pConfig.chargeTablesConfig();
                        Config.pControl.pConfig.estadoBotonesConfig(true);
                        Config.setUserVerify(new Usuario());
                    }else {
                        Config.error("Debe ser Usuario Administrador para modificar estos campos.\n" +
                                "Contacte con el Administrador del sistema.");
                    }


                }else if (packet.getClass() == SafePacket.class){

                    SafePacket safePacket = (SafePacket) packet;
                    if (safePacket.isSave()){
                        /*
                        CODIGOS DE PAQUETE:
                        1 -- ANADIR NUEVO REGISTRO
                        2 -- GUARDAR CAMBIOS EN EL REGISTRO
                        3 -- VALIDAR ENTREGA PERITO
                        4 -- CIERRE DE REGISTRO
                        5 -- NUEVA REAPERTURA
                        6 -- MODIFICAR REAPERTURA
                        7 -- GUARDAR Y CERRAR
                         */
                        for (InterfazRegistro i : InterfazRegistro.getinterfaces()){

                            if (i.getIdInterfaz() == safePacket.getCodVentana()){
                                switch (safePacket.getCodPacket()){
                                    case 1:
                                        if (safePacket.isSafeAndClose()){
                                            i.dispose();
                                            Config.interfaces.remove(i);
                                        }else {
                                            i.contenedorReg.setRegistro(safePacket.getRegistro());
                                            if (safePacket.getRegistro().getEstadoEntrega().equals("ENTREGADO")) {
                                                i.contenedorReg.textFMes.setText(Integer.toString(safePacket.getRegistro().getFacturaRegistro().getMesFactPerito()));
                                                i.contenedorReg.textFAno.setText(Integer.toString(safePacket.getRegistro().getFacturaRegistro().getAnoFactPerito()));
                                            }
                                            i.contenedorReg.chargeOpenRegistro();
                                            Config.info("Se han guardado los cambios en el Registro: "+i.contenedorReg.registro.getCodRegistro()+"\nPulse el botón de actualizar en el Panel de Control");

                                        }
                                        Config.pControl.pControl.changeUltimaAccion("Registro:" + safePacket.getRegistro().getCodRegistro() + " Ref:" + safePacket.getRegistro().getReferenciaCom() + " NUEVO REGISTRO.");
                                        DataTables.getRegistros().add(safePacket.getRegistro());
                                        DataTables.actTablasPanelControl();
                                        Config.pControl.pControl.actualizar.setBackground(Config.AUX3);
                                        break;
                                    case 2:
                                        if (safePacket.isSafeAndClose()) {
                                            i.dispose();
                                            Config.interfaces.remove(i);
                                        } else {
                                            i.contenedorReg.setRegistro(safePacket.getRegistro());
                                            if (safePacket.getRegistro().getEstadoEntrega().equals("ENTREGADO")) {
                                                i.contenedorReg.textFMes.setText(Integer.toString(safePacket.getRegistro().getFacturaRegistro().getMesFactPerito()));
                                                i.contenedorReg.textFAno.setText(Integer.toString(safePacket.getRegistro().getFacturaRegistro().getAnoFactPerito()));
                                            }
                                            Config.info("Se han guardado los cambios en el Registro: " + i.contenedorReg.registro.getCodRegistro());
                                        }
                                        Config.pControl.pControl.changeUltimaAccion("Registro:" + safePacket.getRegistro().getCodRegistro() + " Ref:" + safePacket.getRegistro().getReferenciaCom() + " MODIFICADO CON ÉXITO.");

                                        for (Registro r : DataTables.getRegistros()) {
                                            if (r.getCodRegistro() == safePacket.getRegistro().getCodRegistro()) {
                                                r.setPerito(safePacket.getRegistro().getPerito());
                                                r.setReferenciaCom(safePacket.getRegistro().getReferenciaCom());
                                                r.setDescripcionReg(safePacket.getRegistro().getDescripcionReg());
                                                r.setSiniestro(safePacket.getRegistro().getSiniestro());
                                                r.setFacturaRegistro(safePacket.getRegistro().getFacturaRegistro());
                                                r.setObservaciones(safePacket.getRegistro().getObservaciones());
                                                r.getFacturaRegistro().setMesFactPerito(safePacket.getRegistro().getFacturaRegistro().getMesFactPerito());
                                                r.getFacturaRegistro().setAnoFactPerito(safePacket.getRegistro().getFacturaRegistro().getAnoFactPerito());
                                                r.setContactoReg(safePacket.getRegistro().getContactoReg());
                                                r.setTipoInt(safePacket.getRegistro().getTipoInt());
                                                break;
                                            }
                                        }
                                        Config.pControl.pControl.actualizar.setBackground(Config.AUX3);
                                        break;
                                    case 3:
                                        if (safePacket.getRegistro().getTipoRegistro().equals("REGISTRO")) {
                                            if (safePacket.getRegistro().getEstadoEntrega().equals("PENDIENTE")) {
                                                i.contenedorReg.entregaPerito.setBackground(Config.AUX3);
                                                Config.pControl.pControl.changeUltimaAccion("Registro:" + safePacket.getRegistro().getCodRegistro() + " Ref:" + safePacket.getRegistro().getReferenciaCom() + " ENTREGA PERITO ANULADA.");
                                                for (Registro r : DataTables.getRegistros()) {
                                                    if (r.getCodRegistro() == safePacket.getRegistro().getCodRegistro()) {
                                                        r.setEstadoEntrega("PENDIENTE");
                                                        break;
                                                    }
                                                }
                                            } else if (safePacket.getRegistro().getEstadoEntrega().equals("ENTREGADO")) {
                                                i.contenedorReg.entregaPerito.setBackground(Config.AUX2);
                                                i.contenedorReg.entregaPerito.setBackground(Config.AUX2);
                                                Config.pControl.pControl.changeUltimaAccion("Registro:" + safePacket.getRegistro().getCodRegistro() + " Ref:" + safePacket.getRegistro().getReferenciaCom() + " ENTREGA PERITO REGISTRADA.");
                                                 for (Registro r : DataTables.getRegistros()) {
                                                    if (r.getCodRegistro() == safePacket.getRegistro().getCodRegistro()) {
                                                        r.setEstadoEntrega("ENTREGADO");
                                                        r.getFacturaRegistro().setMesFactPerito(safePacket.getRegistro().getFacturaRegistro().getMesFactPerito());
                                                        r.getFacturaRegistro().setAnoFactPerito(safePacket.getRegistro().getFacturaRegistro().getAnoFactPerito());
                                                        break;
                                                    }
                                                }
                                            }
                                        } else if (safePacket.getRegistro().getTipoRegistro().equals("REAPERTURA")){
                                            i.contenedorReap.setRegistro(safePacket.getRegistro());
                                            i.contenedorReap.setReapertura(safePacket.getReapertura());
                                            if (safePacket.getRegistro().getEstadoEntrega().equals("PENDIENTE")){
                                                i.contenedorReap.registro.setEstadoEntrega("PENDIENTE");
                                                i.contenedorReap.entregaPerito.setBackground(Config.AUX3);
                                                Config.pControl.pControl.changeUltimaAccion("Reapertura:" + safePacket.getRegistro().getCodRegistro() + " Ref:" + safePacket.getRegistro().getReferenciaCom() + " ENTREGA PERITO ANULADA.");

                                                for (Registro r : DataTables.getRegistros()){
                                                    if (r.getCodRegistro() == safePacket.getRegistro().getCodRegistro()){
                                                        r.setEstadoEntrega("PENDIENTE");
                                                        break;
                                                    }
                                                }
                                            }else if (safePacket.getRegistro().getEstadoEntrega().equals("ENTREGADO")) {
                                                i.contenedorReap.registro.setEstadoEntrega("ENTREGADO");
                                                i.contenedorReap.registro.getFacturaRegistro().setMesFactPerito(safePacket.getRegistro().getFacturaRegistro().getMesFactPerito());
                                                i.contenedorReap.registro.getFacturaRegistro().setAnoFactPerito(safePacket.getRegistro().getFacturaRegistro().getAnoFactPerito());
                                                i.contenedorReap.textFMes.setText(Integer.toString(safePacket.getRegistro().getFacturaRegistro().getMesFactPerito()));
                                                i.contenedorReap.textFAno.setText(Integer.toString(safePacket.getRegistro().getFacturaRegistro().getAnoFactPerito()));
                                                i.contenedorReap.entregaPerito.setBackground(Config.AUX2);
                                                i.contenedorReap.entregaPerito.setBackground(Config.AUX2);
                                                Config.pControl.pControl.changeUltimaAccion("Reapertura:" + safePacket.getRegistro().getCodRegistro() + " Ref:" + safePacket.getRegistro().getReferenciaCom() + " ENTREGA PERITO REGISTRADA.");

                                                for (Registro r : DataTables.getRegistros()){
                                                    if (r.getCodRegistro() == safePacket.getRegistro().getCodRegistro()){
                                                        r.setEstadoEntrega("ENTREGADO");
                                                        r.getFacturaRegistro().setMesFactPerito(safePacket.getRegistro().getFacturaRegistro().getMesFactPerito());
                                                        r.getFacturaRegistro().setAnoFactPerito(safePacket.getRegistro().getFacturaRegistro().getAnoFactPerito());
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    case 4:
                                        if (safePacket.getRegistro().getTipoRegistro().equals("REGISTRO")) {
                                            i.contenedorReg.setRegistro(safePacket.getRegistro());
                                            i.contenedorReg.setRegistro(safePacket.getRegistro());
                                            i.contenedorReg.reaperturarReg.setEnabled(true);
                                            i.contenedorReg.reaperturarReg.setBackground(Config.AUX1);
                                            i.contenedorReg.reaperturarReg.setForeground(Color.WHITE);
                                            i.contenedorReg.cerrarReg.setBackground(Config.COLOR4);
                                            i.contenedorReg.estadoReg.setText(safePacket.getRegistro().getEstadoRegistro());
                                            i.contenedorReg.textFCierre.setText(new SimpleDateFormat("dd/MM/yyyy").format(safePacket.getRegistro().getFechaCierre()));
                                            String usr = "";
                                            for (Usuario u : DataTables.getUsuarios()) {
                                                if (u.getCodUsuario() == safePacket.getRegistro().getUsuario()){
                                                    usr = u.getNombreUsr();
                                                    break;
                                                }
                                            }
                                            i.contenedorReg.textUsuario.setText(usr);
                                            i.contenedorReg.blockCierreRegistro();
                                            DataTables.actTablasPanelControl();
                                            Config.info("Se ha cerrado el Registro "+safePacket.getRegistro().getCodRegistro()+" correctamente.");
                                            Config.pControl.pControl.changeUltimaAccion("Registro:"+safePacket.getRegistro().getCodRegistro()+" Ref:"+safePacket.getRegistro().getReferenciaCom()+" REGISTRO CERRADO");

                                        } else if (safePacket.getRegistro().getTipoRegistro().equals("REAPERTURA")){
                                            i.contenedorReap.setRegistro(safePacket.getRegistro());
                                            i.contenedorReap.setRegistro(safePacket.getRegistro());
                                            i.contenedorReap.reaperturarReg.setEnabled(true);
                                            i.contenedorReap.reaperturarReg.setBackground(Config.AUX1);
                                            i.contenedorReap.reaperturarReg.setForeground(Color.WHITE);
                                            i.contenedorReap.cerrarReg.setBackground(Config.COLOR4);
                                            i.contenedorReap.estadoReg.setText(safePacket.getRegistro().getEstadoRegistro());
                                            i.contenedorReap.textFCierre.setText(new SimpleDateFormat("dd/MM/yyyy").format(safePacket.getRegistro().getFechaCierre()));
                                            String usr = "";
                                            for (Usuario u : DataTables.getUsuarios()){
                                                if (u.getCodUsuario() == safePacket.getRegistro().getUsuario()){
                                                    usr = u.getNombreUsr();
                                                    break;
                                                }
                                            }
                                            i.contenedorReap.textUsuario.setText(usr);
                                            i.contenedorReap.blockCierreRegistro();
                                            DataTables.actTablasPanelControl();
                                            Config.info("Se ha cerrado la Reapertura "+safePacket.getRegistro().getCodRegistro()+" correctamente.");
                                            Config.pControl.pControl.changeUltimaAccion("Reapertura:"+safePacket.getRegistro().getCodRegistro()+" Ref:"+safePacket.getRegistro().getReferenciaCom()+" REPERTURA CERRADA");

                                        }
                                        for (Registro r : DataTables.getRegistros()) {
                                            if (r.getCodRegistro() == safePacket.getRegistro().getCodRegistro()) {
                                                r.setEstadoRegistro(safePacket.getRegistro().getEstadoRegistro());
                                                r.setFechaCierre(safePacket.getRegistro().getFechaCierre());
                                                r.setUsuario(safePacket.getRegistro().getUsuario());
                                                break;
                                            }
                                        }
                                        Config.pControl.pControl.actualizar.setBackground(Config.AUX3);
                                        break;
                                    case 5:
                                        if (safePacket.isSafeAndClose()){
                                            Config.remove(i.getIdInterfaz());
                                        }else{
                                            i.contenedorReap.setRegistro(safePacket.getRegistro());
                                            i.contenedorReap.setReapertura(safePacket.getReapertura());
                                            i.contenedorReap.chargeOpenRegistro();
                                        }
                                        DataTables.getRegistros().add(safePacket.getRegistro());
                                        DataTables.getReaperturas().add(safePacket.getReapertura());
                                        DataTables.actTablasPanelControl();
                                        Config.pControl.pControl.changeUltimaAccion("Reapertura:"+safePacket.getRegistro().getCodRegistro()+" Ref:"+safePacket.getRegistro().getReferenciaCom()+" NUEVA REAPERTURA");
                                        Config.pControl.pControl.actualizar.setBackground(Config.AUX3);
                                        Config.info("Se han guardado los cambios en la Reapertura: " + i.contenedorReap.registro.getCodRegistro() + "\nPulse el botón de actualizar en el Panel de Control");
                                        break;
                                    case 6:
                                        Config.info("Se han guardado los cambios en la Reapertura: "+i.contenedorReap.registro.getCodRegistro());
                                        if (safePacket.isSafeAndClose()){
                                            Config.remove(i.getIdInterfaz());
                                        }else {
                                            i.contenedorReap.setRegistro(safePacket.getRegistro());
                                            i.contenedorReap.setReapertura(safePacket.getReapertura());
                                            if (i.contenedorReap.registro.getEstadoEntrega().equals("ENTREGADO")) {
                                                i.contenedorReap.registro.getFacturaRegistro().setMesFactPerito(safePacket.getRegistro().getFacturaRegistro().getMesFactPerito());
                                                i.contenedorReap.registro.getFacturaRegistro().setAnoFactPerito(safePacket.getRegistro().getFacturaRegistro().getAnoFactPerito());
                                            }
                                            Config.pControl.pControl.changeUltimaAccion("Reapertura:"+safePacket.getRegistro().getCodRegistro()+" Ref:"+safePacket.getRegistro().getReferenciaCom()+" REAPERTURA MODIFICADA");
                                        }
                                        for (Registro r : DataTables.getRegistros()){
                                            if (r.getCodRegistro() == safePacket.getRegistro().getCodRegistro()) {
                                                r.setPerito(safePacket.getRegistro().getPerito());
                                                r.setReferenciaCom(safePacket.getRegistro().getReferenciaCom());
                                                r.setDescripcionReg(safePacket.getRegistro().getDescripcionReg());
                                                r.setSiniestro(safePacket.getRegistro().getSiniestro());
                                                r.setFacturaRegistro(safePacket.getRegistro().getFacturaRegistro());
                                                r.setObservaciones(safePacket.getRegistro().getObservaciones());
                                                r.setEstadoEntrega(safePacket.getRegistro().getEstadoEntrega());
                                                r.getFacturaRegistro().setMesFactPerito(safePacket.getRegistro().getFacturaRegistro().getMesFactPerito());
                                                r.getFacturaRegistro().setAnoFactPerito(safePacket.getRegistro().getFacturaRegistro().getAnoFactPerito());
                                                r.setContactoReg(safePacket.getRegistro().getContactoReg());
                                                r.setTipoInt(safePacket.getRegistro().getTipoInt());
                                                break;
                                            }
                                        }
                                        for (Reapertura re : DataTables.getReaperturas()){
                                            if (re.getCodReapertura() == safePacket.getReapertura().getCodReapertura()) re.setTipoReapertura(safePacket.getReapertura().getTipoReapertura());
                                            break;
                                        }
                                        DataTables.actTablasPanelControl();

                                        Config.pControl.pControl.actualizar.setBackground(Config.AUX3);
                                        break;
                                }
                            }



                        }
                    }else Config.error("Error al guardar los datos, intentelo de nuevo.");


                }else if (packet.getClass() == TelfPacket.class){

                    TelfPacket telfPacket = (TelfPacket) packet;
                    Registro temp = telfPacket.getRegistro();
                        switch (telfPacket.getCodPacket()){
                            //ANADIR TELEFONO
                            case 1:
                                if (temp.getTipoRegistro().equals("REGISTRO")) {
                                    if (telfPacket.getTelefonoAsegurado().getCodTelefono() > 0){
                                        for (InterfazRegistro i : InterfazRegistro.getinterfaces()){
                                            if (i.getIdInterfaz() == telfPacket.getCodVentana()){
                                                i.contenedorReg.getListaTelf().add(telfPacket.getTelefonoAsegurado());
                                                i.contenedorReg.actTablaTelefonos();
                                            }
                                        }
                                        for (Registro r : DataTables.getRegistros()){
                                            if (r.getCodRegistro() == telfPacket.getRegistro().getCodRegistro()){
                                                r.getSiniestro().getAsegurado().getTelefonosAseg().add(telfPacket.getTelefonoAsegurado());
                                            }
                                        }
                                    }else{
                                        Config.error("Ha ocurrido un error al añadir el teléfono.\n" +
                                                "Intentelo nuevamente.");
                                    }
                                } else if (temp.getTipoRegistro().equals("REAPERTURA")){
                                    int codRegBase = 0;
                                    if (telfPacket.getTelefonoAsegurado().getCodTelefono() > 0){
                                        for (InterfazRegistro i : InterfazRegistro.getinterfaces()){
                                            if (i.getIdInterfaz() == telfPacket.getCodVentana()){
                                                i.contenedorReap.registro.getSiniestro().getAsegurado().getTelefonosAseg().add(telfPacket.getTelefonoAsegurado());
                                                i.contenedorReap.getListaTelf().add(telfPacket.getTelefonoAsegurado());
                                                i.contenedorReap.actTablaTelefonos();
                                                codRegBase = i.contenedorReap.rp.getRegistroAsociado();
                                                break;
                                            }
                                        }
                                        for (Registro r : DataTables.getRegistros()){
                                            if (r.getCodRegistro() == codRegBase){
                                                r.getSiniestro().getAsegurado().getTelefonosAseg().add(telfPacket.getTelefonoAsegurado());
                                                break;
                                            }
                                        }
                                    }else{
                                        Config.error("Ha ocurrido un error al añadir el teléfono.\n" +
                                                "Intentelo nuevamente.");
                                    }
                                }
                                for (TelefonoAsegurado te : DataTables.getTelefonos()){
                                    if (te.getCodTelefono() == telfPacket.getTelefonoAsegurado().getCodTelefono()){
                                        DataTables.getTelefonos().add(telfPacket.getTelefonoAsegurado());
                                        break;
                                    }
                                }
                                break;
                            //MODIFICAR TELEFONO
                            case 2:
                                if (temp.getTipoRegistro().equals("REGISTRO")) {
                                    if (telfPacket.getTelefonoAsegurado().getCodTelefono() > 0) {
                                        for (InterfazRegistro i : InterfazRegistro.getinterfaces()) {
                                            if (i.getIdInterfaz() == telfPacket.getCodVentana()) {
                                                for (TelefonoAsegurado tr : i.contenedorReg.registro.getSiniestro().getAsegurado().getTelefonosAseg()) {
                                                    if (tr.getCodTelefono() == telfPacket.getTelefonoAsegurado().getCodTelefono()) {
                                                        tr.setTelefono(telfPacket.getTelefonoAsegurado().getTelefono());
                                                        break;
                                                    }
                                                }
                                                for (TelefonoAsegurado tt : i.contenedorReg.getListaTelf()) {
                                                    if (tt.getCodTelefono() == telfPacket.getTelefonoAsegurado().getCodTelefono()) {
                                                        tt.setTelefono(telfPacket.getTelefonoAsegurado().getTelefono());
                                                        break;
                                                    }
                                                }
                                                i.contenedorReg.actTablaTelefonos();
                                            }
                                        }
                                    } else {
                                        Config.error("Ha ocurrido un error al modificar el teléfono.\n" +
                                                "Intentelo nuevamente.");
                                    }
                                } else if (temp.getTipoRegistro().equals("REAPERTURA")){
                                    if (telfPacket.getTelefonoAsegurado().getCodTelefono() > 0) {
                                        for (InterfazRegistro i : InterfazRegistro.getinterfaces()) {
                                            if (i.getIdInterfaz() == telfPacket.getCodVentana()) {
                                                ArrayList<TelefonoAsegurado> tempo = i.contenedorReap.chargeTelefonosBase(i.contenedorReap.rp.getRegistroAsociado());
                                                for (TelefonoAsegurado tr : tempo) {
                                                    if (tr.getCodTelefono() == telfPacket.getTelefonoAsegurado().getCodTelefono()) {
                                                        tr.setTelefono(telfPacket.getTelefonoAsegurado().getTelefono());
                                                        break;
                                                    }
                                                }
                                                i.contenedorReap.setListaTelf(tempo);
                                                i.contenedorReap.actTablaTelefonos();
                                                Thread act = new Thread(new SentTablePacket());
                                                act.start();
                                            }
                                        }
                                    } else {
                                        Config.error("Ha ocurrido un error al modificar el teléfono.\n" +
                                                "Intentelo nuevamente.");
                                    }
                                }
                                for (TelefonoAsegurado te : DataTables.getTelefonos()){
                                    if (te.getCodTelefono() == telfPacket.getTelefonoAsegurado().getCodTelefono()){
                                        te.setTelefono(telfPacket.getTelefonoAsegurado().getTelefono());
                                        break;
                                    }
                                }
                                break;
                            // ELIMINAR TELEFONO
                            case 3:
                                if (temp.getTipoRegistro().equals("REGISTRO")) {
                                    if (telfPacket.getTelefonoAsegurado().getCodTelefono() > 0){
                                        for (InterfazRegistro i : InterfazRegistro.getinterfaces()){
                                            if (i.getIdInterfaz() == telfPacket.getCodVentana()){
                                                for(TelefonoAsegurado tt : i.contenedorReg.getListaTelf()){
                                                    if (tt.getCodTelefono() == telfPacket.getTelefonoAsegurado().getCodTelefono()){
                                                       i.contenedorReg.getListaTelf().remove(tt);
                                                       break;
                                                    }
                                                }
                                                i.contenedorReg.actTablaTelefonos();
                                                for (Registro r : DataTables.getRegistros()){
                                                    if (r.getCodRegistro() == telfPacket.getRegistro().getCodRegistro()){
                                                        for (TelefonoAsegurado tel : r.getSiniestro().getAsegurado().getTelefonosAseg()){
                                                            if (tel.getTelefono() == telfPacket.getTelefonoAsegurado().getTelefono()){
                                                                r.getSiniestro().getAsegurado().getTelefonosAseg().remove(tel);
                                                                break;
                                                            }
                                                        }
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        for (TelefonoAsegurado te : DataTables.getTelefonos()){
                                            if (te.getCodTelefono() == telfPacket.getTelefonoAsegurado().getCodTelefono()){
                                                DataTables.getTelefonos().remove(te);
                                                break;
                                            }
                                        }
                                    }else{
                                        Config.error("Ha ocurrido un error al eliminar el teléfono.\n" +
                                                "Intentelo nuevamente.");
                                    }
                                } else if (temp.getTipoRegistro().equals("REAPERTURA")){
                                    if (telfPacket.getTelefonoAsegurado().getCodTelefono() > 0){
                                        for (InterfazRegistro i : InterfazRegistro.getinterfaces()){
                                            if (i.getIdInterfaz() == telfPacket.getCodVentana()){
                                                for(TelefonoAsegurado tt : i.contenedorReap.getListaTelf()){
                                                    if (tt.getCodTelefono() == telfPacket.getTelefonoAsegurado().getCodTelefono()){
                                                        i.contenedorReap.getListaTelf().remove(tt);
                                                        break;
                                                    }
                                                }
                                                i.contenedorReap.actTablaTelefonos();
                                                Thread act = new Thread(new SentTablePacket());
                                                act.start();
                                            }
                                        }
                                    }else{
                                        Config.error("Ha ocurrido un error al eliminar el teléfono.\n" +
                                                "Intentelo nuevamente.");
                                    }
                                }
                                break;
                        }





                } else if (packet.getClass() == PeritPacket.class){

                    PeritPacket peritPacket = (PeritPacket) packet;
                    if (peritPacket.isSave()){
                        switch (peritPacket.getCodPacket()){
                            case 1:
                                DataTables.getPeritos().add(peritPacket.getPerito());
                                Config.pPerito.dispose();
                                Config.pPerito = new InterfazPerito(peritPacket.getPerito());
                                Config.info("Se ha añadido el perito a la base de datos.\n" +
                                        "Recuerde pulsar el boton actualizar.");
                                break;
                            case 2:
                                Perito updPerito = peritPacket.getPerito();
                                for (Perito p : DataTables.getPeritos()){
                                    if (p.getCodPerito() == updPerito.getCodPerito()){
                                        p.setNombrePerito(updPerito.getNombrePerito());
                                        p.setNifPerito(updPerito.getNifPerito());
                                        p.setDirPerito(updPerito.getDirPerito());
                                        p.setTelfPerito(updPerito.getTelfPerito());
                                        p.setEmailPerito(updPerito.getEmailPerito());
                                        p.setEstadoPerito(updPerito.getEstadoPerito());
                                    break;
                                    }
                                }
                                Config.pPerito.dispose();
                                Config.pPerito = new InterfazPerito(peritPacket.getPerito());
                                Config.info("Se ha guardado los cambios.\n" +
                                        "Recuerde pulsar el boton actualizar.");
                                break;
                        }

                    }else Config.error("Ha ocurrido un error al guardar los datos.\n Intentelo de nuevo.");


                } else if (packet.getClass() == ConfigPacket.class) {
                    ConfigPacket configPacket = (ConfigPacket) packet;
                    if (configPacket.isSave()){
                        switch (configPacket.getCodPacket()){
                            //ADD COMPANIA
                            case 1:
                                if (configPacket.getCompania().getCodCompania()>0) {
                                    Config.pEditCompania.dispose();
                                    Config.pEditCompania = null;
                                    DataTables.getCompanias().add(configPacket.getCompania());
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha añadido la Compañia "+configPacket.getCompania().getCodCompania()+" a la base de datos.");
                                } else Config.error("Error al guardar la Compañia, intentelo nuevamente");
                                break;
                            //EDIT COMPANIA
                            case 2:
                                if (configPacket.getCompania().getCodCompania()>0) {
                                    Config.pEditCompania.dispose();
                                    Config.pEditCompania = null;
                                    for (Compania c : DataTables.getCompanias()){
                                        if (c.getCodCompania() == configPacket.getCompania().getCodCompania()){
                                            c.setNombreCompania(configPacket.getCompania().getNombreCompania());
                                            c.setEstadoCompania(configPacket.getCompania().getEstadoCompania());
                                            break;
                                        }
                                    }
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha modificado la Compañia "+ configPacket.getCompania().getNombreCompania() +" en la base de datos.");
                                } else Config.error("Error al guardar la Compañia, intentelo nuevamente");
                                break;
                            //ADD RAMO
                            case 3:
                                if (configPacket.getRamo().getCodRamo()>0) {
                                    Config.pEditRamo.dispose();
                                    Config.pEditRamo = null;
                                    for (Compania c : DataTables.getCompanias()){
                                        if (c.getCodCompania() == configPacket.getRamo().getCompania()){
                                            c.getRamoCompanias().add(configPacket.getRamo());
                                            break;
                                        }
                                    }
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha añadido el Ramo "+configPacket.getRamo().getCodRamo()+" a la base de datos.");
                                } else Config.error("Error al guardar el Ramo, intentelo nuevamente");
                                break;
                            //EDIT RAMO
                            case 4:
                                if (configPacket.getRamo().getCodRamo()>0) {
                                    Config.pEditRamo.dispose();
                                    Config.pEditRamo = null;
                                    for (Compania c : DataTables.getCompanias()){
                                        if (c.getCodCompania() == configPacket.getRamo().getCompania()){
                                            for (RamoCompania r : c.getRamoCompanias()){
                                                if (r.getCodRamo() == configPacket.getRamo().getCodRamo()){
                                                    r.setDescripcionRamo(configPacket.getRamo().getDescripcionRamo());
                                                    r.setEstadoRam(configPacket.getRamo().getEstadoRam());
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha modificado el Ramo "+ configPacket.getRamo().getDescripcionRamo() +" en la base de datos.");
                                } else Config.error("Error al guardar la Compañia, intentelo nuevamente");
                                break;
                            // ADD TIPO SINIESTRO
                            case 5:
                                if (configPacket.getTipoSiniestro().getCodTipoSiniestro()>0) {
                                    Config.pEditTSin.dispose();
                                    Config.pEditTSin = null;
                                    DataTables.getTipoSiniestros().add(configPacket.getTipoSiniestro());
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha añadido el Tipo Siniestro "+configPacket.getTipoSiniestro().getCodTipoSiniestro()+" a la base de datos.");
                                } else Config.error("Error al guardar el Tipo Siniestro, intentelo nuevamente");
                                break;
                            // EDIT TIPO SINIESTRO
                            case 6:
                                if (configPacket.getTipoSiniestro().getCodTipoSiniestro()>0) {
                                    Config.pEditTSin.dispose();
                                    Config.pEditTSin = null;
                                    for (TipoSiniestro ts : DataTables.getTipoSiniestros()){
                                        if (ts.getCodTipoSiniestro() == configPacket.getTipoSiniestro().getCodTipoSiniestro()){
                                            ts.setDescripTipoSiniestro(configPacket.getTipoSiniestro().getDescripTipoSiniestro());
                                            ts.setEstadoTipoSin(configPacket.getTipoSiniestro().getEstadoTipoSin());
                                            break;
                                        }
                                    }
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha modificado el Tipo Siniestro "+configPacket.getTipoSiniestro().getDescripTipoSiniestro()+" en la base de datos.");
                                } else Config.error("Error al guardar el Tipo Siniestro, intentelo nuevamente");
                                break;
                            // ADD TIPO INTERVENCION
                            case 7:
                                if (configPacket.getTipoIntervencion().getCodTipoInt()>0) {
                                    Config.pEditTInt.dispose();
                                    Config.pEditTInt = null;
                                    DataTables.getTipoInt().add(configPacket.getTipoIntervencion());
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha añadido el Tipo Intervención "+configPacket.getTipoIntervencion().getCodTipoInt()+" a la base de datos.");
                                } else Config.error("Error al guardar el Tipo Intervención, intentelo nuevamente");
                                break;
                            // EDIT TIPO INTERVENCION
                            case 8:
                                if (configPacket.getTipoIntervencion().getCodTipoInt()>0) {
                                    Config.pEditTInt.dispose();
                                    Config.pEditTInt = null;
                                    for (TipoIntervencion ti : DataTables.getTipoInt()){
                                        if (ti.getCodTipoInt() == configPacket.getTipoIntervencion().getCodTipoInt()){
                                            ti.setTipoIntervencion(configPacket.getTipoIntervencion().getTipoIntervencion());
                                            ti.setEstadoIntervencion(configPacket.getTipoIntervencion().getEstadoIntervencion());
                                            break;
                                        }
                                    }
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha modificado el Tipo Intervención "+configPacket.getTipoIntervencion().getTipoIntervencion()+" en la base de datos.");
                                } else Config.error("Error al guardar el Tipo Intervención, intentelo nuevamente");
                                break;
                            // ADD TIPO REAPERTURA
                            case 9:
                                if (configPacket.getTipoReapertura().getCodTipoReapertura()>0) {
                                    Config.pEditTReap.dispose();
                                    Config.pEditTReap = null;
                                    DataTables.getTipoReaperturas().add(configPacket.getTipoReapertura());
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha añadido el Tipo Reapertura "+configPacket.getTipoReapertura().getCodTipoReapertura()+" a la base de datos.");
                                } else Config.error("Error al guardar el Tipo Reapertura, intentelo nuevamente");
                                break;
                            // EDIT TIPO REAPERTURA
                            case 10:
                                if (configPacket.getTipoReapertura().getCodTipoReapertura()>0) {
                                    Config.pEditTReap.dispose();
                                    Config.pEditTReap = null;
                                    for (TipoReapertura tr : DataTables.getTipoReaperturas()){
                                        if (tr.getCodTipoReapertura() == configPacket.getTipoReapertura().getCodTipoReapertura()){
                                            tr.setTipoReapertura(configPacket.getTipoReapertura().getTipoReapertura());
                                            tr.setEstadoTipoReapertura(configPacket.getTipoReapertura().getEstadoTipoReapertura());
                                            break;
                                        }
                                    }
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha modificado el Tipo Reapertura "+configPacket.getTipoReapertura().getTipoReapertura()+" en la base de datos.");
                                } else Config.error("Error al guardar el Tipo Reapertura, intentelo nuevamente");
                                break;
                            // ADD TIPO REAPERTURA
                            case 11:
                                if (configPacket.getUsuario().getCodUsuario()>0) {
                                    Config.pEditUsr.dispose();
                                    Config.pEditUsr = null;
                                    DataTables.getUsuarios().add(configPacket.getUsuario());
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha añadido el Usuario "+configPacket.getUsuario().getNombreUsr()+" a la base de datos.");
                                } else Config.error("Error al guardar el Usuario, intentelo nuevamente");
                                break;
                            // EDIT TIPO REAPERTURA
                            case 12:
                                if (configPacket.getUsuario().getCodUsuario()>0) {
                                    Config.pEditUsr.dispose();
                                    Config.pEditUsr = null;
                                    for(Usuario u : DataTables.getUsuarios()){
                                        if (u.getCodUsuario() == configPacket.getUsuario().getCodUsuario()){
                                            u.setNombreUsr(configPacket.getUsuario().getNombreUsr());
                                            u.setEstadoUsr(configPacket.getUsuario().getEstadoUsr());
                                            u.setTipoUsr(configPacket.getUsuario().getTipoUsr());
                                            break;
                                        }
                                    }
                                    Config.pControl.pConfig.chargeTablesConfig();
                                    Config.info("Se ha modificado el Usuario "+configPacket.getUsuario().getNombreUsr()+" en la base de datos.");
                                } else Config.error("Error al guardar el Tipo Reapertura, intentelo nuevamente");
                                break;
                        }

                    } else Config.error("Ha ocurrido un error al guardar los datos.\n Intentelo de nuevo.");
                }

                fluIn.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Config.error("Error en comunicación.\n Revise la conexión y el estado del Servidor.");
        }
    }
}
