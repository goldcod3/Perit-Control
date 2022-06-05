package conexion;

import clases.*;
import conexion.paquetes.*;

import java.util.ArrayList;

public class ServicePort {

    //optimizar
    public static void loginClientVerify(DataPacket packet){
        Object out;
        DataPacket userVerify = DataCharge.verifyUser(packet);
        TablePacket tablePacket;
        if (userVerify.isValidUser()){
            tablePacket = new TablePacket(userVerify.getCodPacket(), userVerify.getUser(), userVerify.getIpClient());
            tablePacket.setValidUser(true);
            tablePacket.chargeDataPacket();
            tablePacket.setPaqRegistros(DataCharge.chargeClientRegistros(userVerify.getFechaDesde(), userVerify.getFechaHasta()));
            tablePacket.setPaqReaperturas(DataCharge.chargeClientReaperturas(userVerify.getFechaDesde(), userVerify.getFechaHasta()));
            tablePacket.setCodPacket(2);
            out = tablePacket;

        } else {
            packet.setValidUser(false);
            out = packet;
        }

        Service.sendPacketClient(out, packet.getIpClient(), packet.getUser().getNombreUsr());
    }

    public static void verifyAdminUser(VerifyPacket packet){
        Object out;
        VerifyPacket result = DataCharge.verifyUserType(packet);
        out = result;
        Service.sendPacketClient(out, packet.getIpClient(), packet.getUser().getNombreUsr());
    }

    public static void sendServerTablesClient(TablePacket packet){
        Object out;
        packet.chargeDataPacket();
        packet.setCodPacket(3);
        packet.setPaqRegistros(DataCharge.chargeClientRegistros(packet.getFechaDesde(), packet.getFechaHasta()));
        packet.setPaqReaperturas(DataCharge.chargeClientReaperturas(packet.getFechaDesde(), packet.getFechaHasta()));
        ArrayList<TelefonoAsegurado> telefonos = new ArrayList<TelefonoAsegurado>();
        try{
            Registro regist = packet.getPaqRegistros().get(packet.getPaqRegistros().size() -1);
            System.out.println(regist.toString());
            int cod = regist.getSiniestro().getAsegurado().getCodAsegurado();
            for(TelefonoAsegurado tel : DataTables.getTelefonos()){
                if (tel.getAsegurado() >= cod) telefonos.add(tel);
            }
        }catch (Exception e){
            e.printStackTrace();
            for (Registro re : packet.getPaqRegistros()){
                for(TelefonoAsegurado tel : DataTables.getTelefonos()){
                    if (re.getSiniestro().getAsegurado().getCodAsegurado() == tel.getAsegurado()) telefonos.add(tel);
                }
            }
        }
        System.out.println("telfs: "+telefonos.size());
        packet.setPaqTelefonos(telefonos);
        packet.setPaqRamos(DataTables.getRamos());
        packet.setValidUser(true);
        out = packet;
        Service.sendPacketClient(out, packet.getIpClient(), packet.getUser().getNombreUsr());
    }

    public static void dataRegistroBBDD(SafePacket packet){
        Object out;
        SafePacket safePacket = packet;
        System.out.println(safePacket.getCodVentana());


        switch (safePacket.getCodPacket()){
            case 1:
                //NUEVO REGISTRO
                int codNuevo = DataCharge.addRowRegistro(safePacket.getRegistro());
                if (codNuevo > 0){
                    safePacket.setSave(true);
                    safePacket.setRegistro(DataCharge.getRegistro(codNuevo));
                }
                break;
            case 2:
                //MODIFICAR REGISTRO
                Registro modificar = DataCharge.updateRowRegistro(safePacket.getRegistro());
                if (modificar.getCodRegistro()>0){
                    safePacket.setSave(true);
                    safePacket.setRegistro(modificar);
                }
                break;
            case 3:
                //ENTREGA PERITO
                if (safePacket.getRegistro().getTipoRegistro().equals("REAPERTURA")){
                    Reapertura temp = safePacket.getReapertura();
                    Registro entrega = DataCharge.updateEntregaPerito(safePacket.getRegistro());
                    safePacket.setRegistro(entrega);
                    safePacket.setReapertura(temp);
                    safePacket.setSave(true);
                } else {

                    Registro entrega = DataCharge.updateEntregaPerito(safePacket.getRegistro());
                    safePacket.setRegistro(entrega);
                    safePacket.setSave(true);
                }
                break;
            case 4:
                //CIERRE REGISTRO
                int codCierre = DataCharge.updateCierreRegistro(safePacket.getRegistro());
                Registro cierre = DataCharge.getRegistro(codCierre);
                if (cierre.getEstadoRegistro().equals("CERRADO") && codCierre > 0){
                    safePacket.setSave(true);
                    safePacket.setRegistro(cierre);
                }else safePacket.setSave(false);
                break;
            case 5:
                //NUEVA REAPERTURA
                int reg = DataCharge.addRowRegistro(safePacket.getRegistro());
                safePacket.getReapertura().setReapertura(reg);
                int reap = DataCharge.addRowReapertura(safePacket.getReapertura());
                DataTables.addReaperturaClientTable(DataCharge.getReapertura(reap));
                safePacket.getReapertura().setReapertura(reg);
                if (reap > 0){
                    safePacket.setRegistro(DataCharge.getRegistro(reg));
                    for(TelefonoAsegurado tt : DataTables.getTelefonos()){
                        if (tt.getAsegurado() == safePacket.getRegistro().getSiniestro().getAsegurado().getCodAsegurado()){
                            safePacket.getRegistro().getSiniestro().getAsegurado().getTelefonosAseg().add(tt);
                        }
                    }
                    safePacket.setReapertura(DataCharge.getReapertura(reap));
                    safePacket.setSave(true);
                    safePacket.setCodPacket(5);
                } else safePacket.setSave(false);
                break;
            case 6:

                //MODIFICAR REAPERTURA
                Registro modif = DataCharge.updateRowRegistro(safePacket.getRegistro());

                System.out.println("modif: "+modif.getCodRegistro());
                if (modif.getCodRegistro()>0){

                    Reapertura updReap = DataCharge.updateRowReapertura(safePacket.getReapertura());
                    if (updReap.getCodReapertura()>0){
                        boolean tt = DataTables.updReaperturaClientTable(updReap);
                        safePacket.setRegistro(modif);
                        safePacket.setReapertura(DataCharge.getReapertura(updReap.getCodReapertura()));
                        safePacket.setSave(true);
                    } else safePacket.setSave(false);
                }else safePacket.setSave(false);
                safePacket.setCodPacket(6);
                break;

        }
        System.out.println(safePacket.getCodVentana());
        System.out.println(safePacket.isSave());


        out = safePacket;
        Service.sendPacketClient(out, packet.getIpClient(), packet.getUser().getNombreUsr());

    }

    public static void dataTelfBBDD(TelfPacket packet){
        Object out;
        TelfPacket telfPacket = packet;
        switch (telfPacket.getCodPacket()){
            case 1:
                int codTelefono = DataCharge.addRowTelefono(telfPacket.getTelefonoAsegurado());
                if (codTelefono > 0) telfPacket.getTelefonoAsegurado().setCodTelefono(codTelefono);
                else telfPacket.getTelefonoAsegurado().setCodTelefono(0);
                break;
            case 2:
                int updTelefono = DataCharge.updateRowTelefono(telfPacket.getTelefonoAsegurado());
                if (updTelefono == 0) telfPacket.getTelefonoAsegurado().setCodTelefono(0);
                else telfPacket.getTelefonoAsegurado().setCodTelefono(updTelefono);
                break;
            case 3:
                int delTelefono = DataCharge.deleteRowTelefono(telfPacket.getTelefonoAsegurado());
                if (delTelefono > 0){
                    for (TelefonoAsegurado tt : DataTables.getTelefonos()){
                        if (tt.getCodTelefono() == telfPacket.getTelefonoAsegurado().getCodTelefono()){
                            DataTables.getTelefonos().remove(tt);
                            break;
                        }
                    }
                }else telfPacket.getTelefonoAsegurado().setCodTelefono(0);
                break;
        }
        out = telfPacket;
        Service.sendPacketClient(out, packet.getIpClient(), packet.getUser().getNombreUsr());


    }

    public static void dataPeritoBBDD(PeritPacket packet){
        Object out;
        PeritPacket peritPacket = packet;
        switch (peritPacket.getCodPacket()){
            case 1:
                //NUEVO PERITO
                Perito perito = DataCharge.addRowPerito(peritPacket.getPerito());
                if (perito.getCodPerito() != 0){
                    peritPacket.setPerito(perito);
                    peritPacket.setSave(true);
                }
                break;
            case 2:
                //MODIFICAR PERITO
                boolean modifPerito = DataCharge.updateRowPerito(peritPacket.getPerito());
                if (modifPerito) peritPacket.setSave(true);
                break;
        }
        out = peritPacket;
        Service.sendPacketClient(out, packet.getIpClient(), packet.getUser().getNombreUsr());

    }

    public static void dataConfigBBDD(ConfigPacket packet){
        Object out;
        ConfigPacket configPacket = packet;
        switch (configPacket.getCodPacket()){
            case 1:
                //ADD COMPANIA
                int codigoAddCom = DataCharge.addRowCompania(configPacket.getCompania());
                if (codigoAddCom>0){
                    configPacket.getCompania().setCodCompania(codigoAddCom);
                    configPacket.setSave(true);
                    DataTables.getCompanias().add(configPacket.getCompania());
                }
                break;
            case 2:
                //EDIT COMPANIA
                int codigoEditCom = DataCharge.updateRowCompania(configPacket.getCompania());
                if (codigoEditCom>0){
                    for (Compania c : DataTables.getCompanias()){
                        if (c.getCodCompania() == configPacket.getCompania().getCodCompania()){
                            c.setNombreCompania(configPacket.getCompania().getNombreCompania());
                            c.setEstadoCompania(configPacket.getCompania().getEstadoCompania());
                            break;
                        }
                    }
                    configPacket.setSave(true);
                }
                break;
            case 3:
                //ADD RAMO
                int codigoAddRam = DataCharge.addRamoCompania(configPacket.getRamo());
                if (codigoAddRam>0){
                    for (Compania c : DataTables.getCompanias()){
                        if (c.getCodCompania() == configPacket.getRamo().getCompania()){
                            configPacket.getRamo().setCodRamo(codigoAddRam);
                            c.getRamoCompanias().add(configPacket.getRamo());
                            break;
                        }
                    }
                    configPacket.setSave(true);
                }
                break;
            case 4:
                //EDIT RAMO
                int codigoEditRam = DataCharge.updateRamoCompania(configPacket.getRamo());
                if (codigoEditRam>0){
                    for (Compania c : DataTables.getCompanias()){
                        if (c.getCodCompania() == configPacket.getRamo().getCompania()){
                            for (RamoCompania r : c.getRamoCompanias()){
                                r.setDescripcionRamo(configPacket.getRamo().getDescripcionRamo());
                                r.setEstadoRam(configPacket.getRamo().getEstadoRam());
                                break;
                            }
                            break;
                        }
                    }
                    configPacket.setSave(true);
                }
                break;
            case 5:
                //ADD RAMO
                int codigoAddTipoS = DataCharge.addTipoSiniestro(configPacket.getTipoSiniestro());
                if (codigoAddTipoS>0){
                    configPacket.getTipoSiniestro().setCodTipoSiniestro(codigoAddTipoS);
                    DataTables.getTipoSiniestros().add(configPacket.getTipoSiniestro());
                    configPacket.setSave(true);
                }
                break;
            case 6:
                //EDIT RAMO
                int codigoEditTipoS = DataCharge.updateTipoSiniestro(configPacket.getTipoSiniestro());
                if (codigoEditTipoS>0){
                    for (TipoSiniestro ts : DataTables.getTipoSiniestros()){
                        if (ts.getCodTipoSiniestro() == configPacket.getTipoSiniestro().getCodTipoSiniestro()){
                            ts.setDescripTipoSiniestro(configPacket.getTipoSiniestro().getDescripTipoSiniestro());
                            ts.setEstadoTipoSin(configPacket.getTipoSiniestro().getEstadoTipoSin());
                            break;
                        }
                    }
                    configPacket.setSave(true);
                }
                break;
            case 7:
                //ADD INTERVENCION
                int codigoAddTipoI = DataCharge.addTipoIntervencion(configPacket.getTipoIntervencion());
                if (codigoAddTipoI>0){
                    configPacket.getTipoIntervencion().setCodTipoInt(codigoAddTipoI);
                    DataTables.getTipoInt().add(configPacket.getTipoIntervencion());
                    configPacket.setSave(true);
                }
                break;
            case 8:
                //EDIT INTERVENCION
                int codigoEditTipoI = DataCharge.updateTipoIntervencion(configPacket.getTipoIntervencion());
                if (codigoEditTipoI>0){
                    for(TipoIntervencion ti : DataTables.getTipoInt()){
                        if (ti.getCodTipoInt() == configPacket.getTipoIntervencion().getCodTipoInt()){
                            ti.setTipoIntervencion(configPacket.getTipoIntervencion().getTipoIntervencion());
                            ti.setEstadoIntervencion(configPacket.getTipoIntervencion().getEstadoIntervencion());
                            break;
                        }
                    }
                    configPacket.setSave(true);
                }
                break;
            case 9:
                //ADD REAPERTURA
                int codigoAddTipoR = DataCharge.addTipoReapertura(configPacket.getTipoReapertura());
                if (codigoAddTipoR>0){
                    configPacket.getTipoReapertura().setCodTipoReapertura(codigoAddTipoR);
                    DataTables.getTipoReaperturas().add(configPacket.getTipoReapertura());
                    configPacket.setSave(true);
                }
                break;
            case 10:
                //EDIT REAPERTURA
                int codigoEditTipoR = DataCharge.updateTipoReapertura(configPacket.getTipoReapertura());
                if (codigoEditTipoR>0){
                    for (TipoReapertura tr : DataTables.getTipoReaperturas()){
                        if (tr.getCodTipoReapertura() == configPacket.getTipoReapertura().getCodTipoReapertura()){
                            tr.setTipoReapertura(configPacket.getTipoReapertura().getTipoReapertura());
                            tr.setEstadoTipoReapertura(configPacket.getTipoReapertura().getEstadoTipoReapertura());
                            break;
                        }
                    }
                    configPacket.setSave(true);
                }
                break;
            case 11:
                //ADD USUARIO
                int codigoAddUsr = DataCharge.addUsuario(configPacket.getUsuario());
                if (codigoAddUsr>0){
                    configPacket.getUsuario().setCodUsuario(codigoAddUsr);
                    configPacket.getUsuario().setPassUsr("-");
                    DataTables.getUsuarios().add(configPacket.getUsuario());
                    configPacket.setSave(true);
                }
                break;
            case 12:
                //EDIT USUARIO
                int codigoEditUsr = DataCharge.updateUsuario(configPacket.getUsuario());
                if (codigoEditUsr>0){
                    for (Usuario u : DataTables.getUsuarios()){
                        if (u.getCodUsuario() == configPacket.getUsuario().getCodUsuario()){
                            u.setNombreUsr(configPacket.getUsuario().getNombreUsr());
                            u.setPassUsr(configPacket.getUsuario().getPassUsr());
                            u.setEstadoUsr(configPacket.getUsuario().getEstadoUsr());
                            u.setTipoUsr(configPacket.getUsuario().getTipoUsr());
                        }
                    }
                    configPacket.setSave(true);
                }
                break;
        }
        out = configPacket;
        Service.sendPacketClient(out, packet.getIpClient(), packet.getUser().getNombreUsr());

    }

}
