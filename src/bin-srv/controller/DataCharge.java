package conexion;

import clases.*;
import conexion.paquetes.DataPacket;
import conexion.paquetes.VerifyPacket;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DataCharge implements Serializable {

    private static final long serialVersionUID = 290322L;

    //TABLAS
    public DataPacket data;
    public static ArrayList<Usuario> users = new ArrayList<Usuario>();
    public ArrayList<Registro> paqRegistros = new ArrayList<Registro>();
    public ArrayList<Perito> paqPeritos = new ArrayList<Perito>();
    public ArrayList<Compania> paqCompanias = new ArrayList<Compania>();
    public ArrayList<RamoCompania> paqRamoCompanias = new ArrayList<RamoCompania>();
    public ArrayList<TipoSiniestro> paqTipoSiniestros = new ArrayList<TipoSiniestro>();

    public DataCharge(){

    }

    //CARGA DE TABLAS
    public static void chargeDataTables(){
        DataCharge.chargeServerTableTelefonos();
        DataCharge.chargeServerTablePeritos();
        DataCharge.chargeServerTableCompania();
        DataCharge.chargeServerTableRamos();
        DataCharge.chargeServerTableTipoSin();
        DataCharge.chargeServerTableTipoInt();
        DataCharge.chargeServerTableUsuario();
        DataCharge.chargeServerTableReaperturas();
        DataCharge.chargeServerTableTipoReap();

    }

    public static void cleanDataTables(){
        DataTables.setTelefonos(new ArrayList<TelefonoAsegurado>());
        DataTables.setPeritos(new ArrayList<Perito>());
        DataTables.setCompanias(new ArrayList<Compania>());
        DataTables.setRamos(new ArrayList<RamoCompania>());
        DataTables.setTipoSiniestros(new ArrayList<TipoSiniestro>());
        DataTables.setTipoInt(new ArrayList<TipoIntervencion>());
        DataTables.setUsuarios(new ArrayList<Usuario>());
        DataTables.setTipoReaperturas(new ArrayList<TipoReapertura>());
        DataTables.setReaperturas(new ArrayList<Reapertura>());
    }

    //  TABLA REGISTROS
    public static Registro getRegistro(int codigoRegistro) {
        ArrayList<TelefonoAsegurado> listaTelefonos = DataCharge.getTableTelefonos();
        Registro out = new Registro();
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT registro.codRegistro, registro.codPerito, registro.fechaApertura, registro.referenciaCia, registro.codAsegCom, registro.descripcionReg, registro.fechaEntregaPer, registro.estadoEntrega, registro.estadoReg," +
                    " registro.codFactura, registro.fechaCierre, registro.observaciones, registro.codUsuario, registro.numeroReaperturas, registro.tipoRegistro, registro.tipoInt, registro.contactoReg, siniestro.codAsegurado, asegurado.nombreAseg," +
                    " asegurado.direccionRiesgo, asegurado.direccionContacto, asegurado.contactoAseg, siniestro.codCompania, siniestro.codRamo, siniestro.codTipoSin, siniestro.numPoliza, factura.numFactura," +
                    " factura.codHGabinete, factura.codHPerito, factura.estadoPagoFac, factura.mesFacPerito, factura.anoFacPerito, hgabinete.honorarioGab, hgabinete.locomocionGab, hgabinete.variosGab, hgabinete.sumaGab," +
                    " hgabinete.ivaGab, hgabinete.totalGab, hperito.honorarioPer, hperito.locomocionPer, hperito.ajustesPer, hperito.sumaPer, hperito.ivaPer, hperito.irpfPer, hperito.totalPer" +
                    " FROM registro, siniestro , asegurado, factura, hgabinete, hperito" +
                    " WHERE registro.codAsegCom=siniestro.codSiniestro AND siniestro.codAsegurado=asegurado.codAsegurado AND factura.codHGabinete=hgabinete.codHGabinete AND factura.codHPerito=hperito.codHPerito" +
                    " AND siniestro.codSiniestro=registro.codAsegCom AND factura.codFactura=registro.codFactura AND registro.codRegistro=?");
            stmt.setInt(1, codigoRegistro);
            rs = stmt.executeQuery();

            while(rs.next()){
                int codRegistro = rs.getInt(1);
                int codPerito = rs.getInt(2);
                Date fechaApertura = rs.getDate(3);
                String refCia = rs.getString(4);
                int codSiniestro = rs.getInt(5);
                String descripcion = rs.getString(6);
                Date fechaEntregaPer = rs.getDate(7);
                String estadoEntrega = rs.getString(8);
                String estadoReg = rs.getString(9);
                int codFactura = rs.getInt(10);
                Date fechaCierre = rs.getDate(11);
                String observaciones = rs.getString(12);
                int codUsuario = rs.getInt(13);
                int numReaperturas = rs.getInt(14);
                String tipoRegistro = rs.getString(15);
                int tipoIntervencion = rs.getInt(16);
                String contactoReg = rs.getString(17);

                //ASEGURADO
                int codAseg = rs.getInt(18);
                String nombre = rs.getString(19);
                String dirRiesgo = rs.getString(20);
                String dirContacto = rs.getString(21);
                String contactoAseg = rs.getString(22);

                Asegurado asegurado = new Asegurado(codAseg, nombre, dirRiesgo, dirContacto, contactoAseg);

                ArrayList<TelefonoAsegurado> telfRegistro = new ArrayList<TelefonoAsegurado>();
                for (TelefonoAsegurado ta : listaTelefonos){
                    if(ta.getAsegurado() == codAseg){
                        telfRegistro.add(ta);
                    }
                }
                asegurado.setTelefonosAseg(telfRegistro);

                //SINIESTRO

                int codCompania = rs.getInt(23);
                int codRamo = rs.getInt(24);
                int codTipoSin = rs.getInt(25);
                String numPoliza = rs.getString(26);
                Siniestro siniestro = new Siniestro(codSiniestro, asegurado, codCompania,codRamo, codTipoSin, numPoliza);


                //FACTURA
                String numFac = rs.getString(27);
                int codHGab = rs.getInt(28);
                int codHPer = rs.getInt(29);
                String estado = rs.getString(30);
                int mesPerito = rs.getInt(31);
                int anoPerito = rs.getInt(32);
                double hGab = rs.getDouble(33);
                double locGab = rs.getDouble(34);
                double variosGab = rs.getDouble(35);
                double sumaGab = rs.getDouble(36);
                double ivaGab = rs.getDouble(37);
                double totalGab = rs.getDouble(38);
                double hPer = rs.getDouble(39);
                double locPer = rs.getDouble(40);
                double ajustesPer = rs.getDouble(41);
                double sumaPer = rs.getDouble(42);
                double ivaPer = rs.getDouble(43);
                double irpfPer = rs.getDouble(44);
                double totalPer = rs.getDouble(45);

                Factura factura = new Factura(codFactura, numFac, estado, mesPerito, anoPerito);
                factura.sethGabinete(new HGabinete(codHGab, hGab, locGab, variosGab, sumaGab, ivaGab, totalGab));
                factura.sethPerito(new HPerito(codHPer, hPer, locPer, ajustesPer, sumaPer, ivaPer, irpfPer, totalPer));


                Registro registro = new Registro(codRegistro, codPerito, fechaApertura, refCia, siniestro, descripcion,
                        fechaEntregaPer, estadoEntrega, estadoReg, factura, fechaCierre, observaciones, codUsuario, numReaperturas, tipoRegistro);
                registro.setTipoInt(tipoIntervencion);
                registro.setContactoReg(contactoReg);

                out = registro;

            }

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception troubles) {
            System.out.println("ERROR");
            troubles.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    public static int addRowRegistro(Registro newRegistro){
        int nuevoRegistro = 0;
        java.sql.Date date = new java.sql.Date(newRegistro.getFechaApertura().getTime());
        Connection c = null;
        CallableStatement stmt;
            try {
                c = ConnectionPool.getConnection();
                stmt = c.prepareCall("CALL addRegistro(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                stmt.setString(1, newRegistro.getSiniestro().getAsegurado().getNombreAseg());
                stmt.setString(2, newRegistro.getSiniestro().getAsegurado().getDireccionRiesgo());
                stmt.setString(3, newRegistro.getSiniestro().getAsegurado().getDireccionContacto());
                stmt.setString(4, newRegistro.getSiniestro().getAsegurado().getContactoAseg());
                stmt.setInt(5, newRegistro.getSiniestro().getCompania());
                stmt.setInt(6, newRegistro.getSiniestro().getRamo());
                stmt.setInt(7, newRegistro.getSiniestro().getTipoSiniestro());
                stmt.setString(8, newRegistro.getSiniestro().getNumPoliza());
                stmt.setString(9, newRegistro.getFacturaRegistro().getNumFactura());
                stmt.setInt(10, newRegistro.getFacturaRegistro().getMesFactPerito());
                stmt.setInt(11, newRegistro.getFacturaRegistro().getAnoFactPerito());
                stmt.setInt(12, newRegistro.getPerito());
                stmt.setDate(13, date);
                stmt.setString(14, newRegistro.getReferenciaCom());
                stmt.setString(15, newRegistro.getDescripcionReg());
                stmt.setString(16, newRegistro.getObservaciones());
                stmt.setInt(17, newRegistro.getUsuario());
                stmt.setInt(18, newRegistro.getTipoInt());
                stmt.setString(19,newRegistro.getContactoReg());
                stmt.setString(20,newRegistro.getTipoRegistro());
                stmt.registerOutParameter(21,Types.INTEGER);
                stmt.execute();

                nuevoRegistro = stmt.getInt(21);

                stmt.close();
                c.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    if (null != c) c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return nuevoRegistro;
        }

    public static Registro updateRowRegistro(Registro target){
        Registro out = new Registro();
        Connection c = null;
        CallableStatement stmt;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL updateRowRegistro(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, target.getCodRegistro());
            stmt.setInt(2, target.getPerito());
            stmt.setInt(3, target.getSiniestro().getCompania());
            stmt.setInt(4, target.getSiniestro().getRamo());
            stmt.setString(5,target.getReferenciaCom());
            stmt.setString(6,target.getContactoReg());
            stmt.setInt(7,target.getSiniestro().getCodSiniestro());
            stmt.setString(8,target.getSiniestro().getNumPoliza());
            stmt.setInt(9,target.getSiniestro().getTipoSiniestro());
            stmt.setInt(10,target.getTipoInt());
            stmt.setInt(11,target.getSiniestro().getAsegurado().getCodAsegurado());
            stmt.setString(12,target.getSiniestro().getAsegurado().getNombreAseg());
            stmt.setString(13,target.getSiniestro().getAsegurado().getDireccionContacto());
            stmt.setString(14,target.getSiniestro().getAsegurado().getDireccionRiesgo());
            stmt.setString(15,target.getSiniestro().getAsegurado().getContactoAseg());
            stmt.setString(16,target.getDescripcionReg());
            stmt.setInt(17,target.getFacturaRegistro().getCodFactura());
            stmt.setString(18,target.getFacturaRegistro().getNumFactura());
            stmt.setString(19,target.getFacturaRegistro().getEstadoFactura());
            stmt.setDouble(20, target.getFacturaRegistro().gethGabinete().getHonorarioGab());
            stmt.setDouble(21, target.getFacturaRegistro().gethGabinete().getLocomocionGab());
            stmt.setDouble(22, target.getFacturaRegistro().gethGabinete().getVariosGab());
            stmt.setDouble(23, target.getFacturaRegistro().gethGabinete().getSumaGab());
            stmt.setDouble(24, target.getFacturaRegistro().gethGabinete().getIvaGab());
            stmt.setDouble(25, target.getFacturaRegistro().gethGabinete().getTotalGab());
            stmt.setDouble(26, target.getFacturaRegistro().gethPerito().getHonorariosPer());
            stmt.setDouble(27, target.getFacturaRegistro().gethPerito().getLocomocionPer());
            stmt.setDouble(28, target.getFacturaRegistro().gethPerito().getAjustesPer());
            stmt.setDouble(29, target.getFacturaRegistro().gethPerito().getSumaPer());
            stmt.setDouble(30, target.getFacturaRegistro().gethPerito().getIvaPer());
            stmt.setDouble(31, target.getFacturaRegistro().gethPerito().getIrpfPer());
            stmt.setDouble(32, target.getFacturaRegistro().gethPerito().getTotalPer());
            stmt.setString(33,target.getObservaciones());
            stmt.setInt(34,target.getFacturaRegistro().gethGabinete().getCodHGabinete());
            stmt.setInt(35,target.getFacturaRegistro().gethPerito().getCodHPerito());
            stmt.setInt(36,target.getFacturaRegistro().getMesFactPerito());
            stmt.setInt(37,target.getFacturaRegistro().getAnoFactPerito());
            stmt.registerOutParameter(38,Types.INTEGER);
            stmt.execute();

            int result = stmt.getInt(38);
            if (result == 1){
                out = DataCharge.getRegistro(target.getCodRegistro());
            }else out = new Registro();

            stmt.close();
            c.close();
        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return out;
    }

    public static Registro updateEntregaPerito(Registro in){
        int cod = 0;
        Registro out = new Registro();
        Connection c = null;
        CallableStatement stmt;

        try {
            java.sql.Date date;
            c = ConnectionPool.getConnection();
            if (in.getFechaEntregaPerito() != null) {
                date = new java.sql.Date(in.getFechaEntregaPerito().getTime());
            } else date = new java.sql.Date(new Date().getTime());
            if (in.getEstadoEntrega().equals("ENTREGADO")){
                stmt = c.prepareCall("CALL updateEntregaPerito(?,?,?,?,?,?,?)");
                stmt.setInt(1, in.getCodRegistro());
                stmt.setInt(2, in.getFacturaRegistro().getCodFactura());
                stmt.setDate(3, date);
                stmt.setInt(4, in.getFacturaRegistro().getMesFactPerito());
                stmt.setInt(5, in.getFacturaRegistro().getAnoFactPerito());
                stmt.setString(6, in.getEstadoEntrega());
                stmt.registerOutParameter(7, Types.INTEGER);
                stmt.execute();
                cod = stmt.getInt(7);
                stmt.close();

            } else if (in.getEstadoEntrega().equals("PENDIENTE")){
                stmt = c.prepareCall("UPDATE registro SET estadoEntrega=?, fechaEntregaPer=? WHERE codRegistro=?");
                stmt.setString(1, in.getEstadoEntrega());
                stmt.setDate(2, date);
                stmt.setInt(3, in.getCodRegistro());
                stmt.execute();
                cod = 1;
                stmt.close();
            }

            if (cod == 1) {
                out = in;

            }
            c.close();
        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return out;
    }

    public static int updateCierreRegistro(Registro in){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        java.sql.Date date = new java.sql.Date(in.getFechaCierre().getTime());
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL updateCierreRegistro(?,?,?,?)");
            stmt.setInt(1, in.getCodRegistro());
            stmt.setDate(2, date);
            stmt.setInt(3, in.getUsuario());
            stmt.registerOutParameter(4,Types.INTEGER);
            stmt.execute();

            cod = stmt.getInt(4);


            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }


    //  TABLA REAPERTURA
    public static Reapertura getReapertura(int reapertura) {
        Reapertura re = new Reapertura(0);
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("SELECT codReapertura, codRegistroAsociado, codReaperturaReg, codTipoReapertura FROM reapertura WHERE codReapertura=?");
            stmt.setInt(1, reapertura);
            rs = stmt.executeQuery();

            while(rs.next()){
                int codReap = rs.getInt(1);
                int regAsoc = rs.getInt(2);
                int codRp = rs.getInt(3);
                int tipoReap = rs.getInt(4);

                re = new Reapertura(codReap,regAsoc,codRp,tipoReap);
            }

            rs.close();
            stmt.close();
            c.close();
        }catch (Exception troubles) {
            System.out.println("ERROR");
            troubles.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return re;
    }

    public static int addRowReapertura(Reapertura reap){
        int nuevaReapertura = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL addReapertura(?,?,?,?)");
            stmt.setInt(1, reap.getRegistroAsociado());
            stmt.setInt(2, reap.getReapertura());
            stmt.setInt(3, reap.getTipoReapertura());
            stmt.registerOutParameter(4,Types.INTEGER);
            stmt.execute();

            nuevaReapertura = stmt.getInt(4);
            stmt.close();
            c.close();
        }catch (Exception e){
              e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return nuevaReapertura;
    }

    public static Reapertura updateRowReapertura (Reapertura target){
        Reapertura out = new Reapertura(0);

        Connection c = null;
        CallableStatement stmt;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("UPDATE reapertura SET codTipoReapertura=? WHERE codReapertura=?");
            stmt.setInt(1, target.getTipoReapertura());
            stmt.setInt(2, target.getCodReapertura());
            stmt.execute();

            stmt.close();
            c.close();
        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }

        out = target;
        return out;
    }


    //  TABLA TELEFONOS
    public static ArrayList<TelefonoAsegurado> getTableTelefonos(){
        ArrayList<TelefonoAsegurado> telfs = new ArrayList<>();
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codTelefono, telefonoAseg, codAsegurado FROM telefono");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int codT = rs.getInt(1);
                int tel = rs.getInt(2);
                int codA = rs.getInt(3);
                TelefonoAsegurado t = new TelefonoAsegurado(codT, tel, codA);
                telfs.add(t);
            }

            rs.close();
            stmt.close();
            c.close();
        }catch (SQLException troubles) {
            System.out.println("ERROR");
            troubles.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return telfs;
    }

    public static int addRowTelefono(TelefonoAsegurado addTelf){
        int codTelefono = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL addTelefonoAsegurado(?,?,?)");
            stmt.setInt(1, addTelf.getTelefono());
            stmt.setInt(2, addTelf.getAsegurado());
            stmt.registerOutParameter(3,Types.INTEGER);
            stmt.execute();

            codTelefono = stmt.getInt(3);

            if (codTelefono > 0){
                addTelf.setCodTelefono(codTelefono);
                DataTables.getTelefonos().add(addTelf);
            }

            stmt.close();
            c.close();
        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return codTelefono;
    }

    public static int updateRowTelefono(TelefonoAsegurado updateTelf){
        int codTelefono = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL updateTelefonoAsegurado(?,?,?)");
            stmt.setInt(1, updateTelf.getCodTelefono());
            stmt.setInt(2, updateTelf.getTelefono());
            stmt.registerOutParameter(3,Types.INTEGER);
            stmt.execute();

            codTelefono = stmt.getInt(3);

            stmt.close();
            c.close();
        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return codTelefono;
    }

    public static int deleteRowTelefono(TelefonoAsegurado deleteTelf){
        int codTelefono = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL deleteTelefonoAsegurado(?,?)");
            stmt.setInt(1, deleteTelf.getCodTelefono());
            stmt.registerOutParameter(2,Types.INTEGER);
            stmt.execute();

            codTelefono = stmt.getInt(2);


            stmt.close();
            c.close();
        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return codTelefono;
    }


    //  TABLA PERITOS
    public static Perito addRowPerito(Perito addPerito){
        Perito perito = new Perito();
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL addPerito(?,?,?,?,?,?)");
            stmt.setString(1, addPerito.getNombrePerito());
            stmt.setString(2, addPerito.getNifPerito());
            stmt.setString(3, addPerito.getDirPerito());
            stmt.setInt(4, addPerito.getTelfPerito());
            stmt.setString(5, addPerito.getEmailPerito());
            stmt.registerOutParameter(6,Types.INTEGER);
            stmt.execute();

            int codPerito = stmt.getInt(6);

            if (codPerito > 0){
                addPerito.setCodPerito(codPerito);
                DataTables.getPeritos().add(addPerito);
                perito = addPerito;
            }

            stmt.close();
            c.close();
        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return perito;
    }

    public static boolean updateRowPerito(Perito updPerito){
        boolean modif = false;
        int valid = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL updatePerito(?,?,?,?,?,?,?,?)");
            stmt.setInt(1, updPerito.getCodPerito());
            stmt.setString(2, updPerito.getNombrePerito());
            stmt.setString(3, updPerito.getNifPerito());
            stmt.setString(4, updPerito.getDirPerito());
            stmt.setInt(5, updPerito.getTelfPerito());
            stmt.setString(6, updPerito.getEmailPerito());
            stmt.setString(7, updPerito.getEstadoPerito());
            stmt.registerOutParameter(8,Types.INTEGER);
            stmt.execute();

            valid = stmt.getInt(8);

            stmt.close();
            c.close();
        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (valid > 0){
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
            modif = true;
        }
        return modif;
    }


    //ANADIR DATOS CONFIG
    public static int addRowCompania(Compania compania){
        int codCompania = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL addCompania(?,?)");
            stmt.setString(1, compania.getNombreCompania());
            stmt.registerOutParameter(2,Types.INTEGER);
            stmt.execute();

            codCompania = stmt.getInt(2);


            stmt.close();
            c.close();
        }catch (Exception e){
            codCompania = 0;
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return codCompania;
    }

    public static int updateRowCompania(Compania compania){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL updateCompania(?,?,?,?)");
            stmt.setInt(1, compania.getCodCompania());
            stmt.setString(2, compania.getNombreCompania());
            stmt.setString(3, compania.getEstadoCompania());
            stmt.registerOutParameter(4,Types.INTEGER);
            stmt.execute();

            cod= stmt.getInt(4);


            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }

    public static int addRamoCompania(RamoCompania ramo){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL addRamoCompania(?,?,?)");
            stmt.setString(1, ramo.getDescripcionRamo());
            stmt.setInt(2, ramo.getCompania());
            stmt.registerOutParameter(3,Types.INTEGER);
            stmt.execute();

            cod = stmt.getInt(3);


            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }

    public static int updateRamoCompania(RamoCompania ramo){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL updateRamoCompania(?,?,?,?)");
            stmt.setInt(1, ramo.getCodRamo());
            stmt.setString(2, ramo.getDescripcionRamo());
            stmt.setString(3, ramo.getEstadoRam());
            stmt.registerOutParameter(4,Types.INTEGER);
            stmt.execute();

            cod= stmt.getInt(4);


            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }

    public static int addTipoSiniestro(TipoSiniestro tipoSiniestro){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL addTipoSiniestro(?,?)");
            stmt.setString(1, tipoSiniestro.getDescripTipoSiniestro());
            stmt.registerOutParameter(2,Types.INTEGER);
            stmt.execute();

            cod = stmt.getInt(2);

            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }

    public static int updateTipoSiniestro (TipoSiniestro tipoSiniestro){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL updateTipoSiniestro(?,?,?,?)");
            stmt.setInt(1, tipoSiniestro.getCodTipoSiniestro());
            stmt.setString(2, tipoSiniestro.getDescripTipoSiniestro());
            stmt.setString(3, tipoSiniestro.getEstadoTipoSin());
            stmt.registerOutParameter(4,Types.INTEGER);
            stmt.execute();

            cod= stmt.getInt(4);


            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }

    public static int addTipoIntervencion(TipoIntervencion tipoIntervencion){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL addTipoIntervencion(?,?)");
            stmt.setString(1, tipoIntervencion.getTipoIntervencion());
            stmt.registerOutParameter(2,Types.INTEGER);
            stmt.execute();

            cod = stmt.getInt(2);


            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }

    public static int updateTipoIntervencion (TipoIntervencion tipoIntervencion){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL updateTipoIntervencion(?,?,?,?)");
            stmt.setInt(1, tipoIntervencion.getCodTipoInt());
            stmt.setString(2, tipoIntervencion.getTipoIntervencion());
            stmt.setString(3, tipoIntervencion.getEstadoIntervencion());
            stmt.registerOutParameter(4,Types.INTEGER);
            stmt.execute();

            cod= stmt.getInt(4);


            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }

    public static int addTipoReapertura(TipoReapertura tipoReapertura){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL addTipoReapertura(?,?)");
            stmt.setString(1, tipoReapertura.getTipoReapertura());
            stmt.registerOutParameter(2,Types.INTEGER);
            stmt.execute();

            cod = stmt.getInt(2);


            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }

    public static int updateTipoReapertura (TipoReapertura tipoReapertura){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL updateTipoReapertura(?,?,?,?)");
            stmt.setInt(1, tipoReapertura.getCodTipoReapertura());
            stmt.setString(2, tipoReapertura.getTipoReapertura());
            stmt.setString(3, tipoReapertura.getEstadoTipoReapertura());
            stmt.registerOutParameter(4,Types.INTEGER);
            stmt.execute();

            cod= stmt.getInt(4);

            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }

    public static int addUsuario(Usuario usuario){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL addUsuario(?,?,?,?)");
            stmt.setString(1, usuario.getNombreUsr());
            stmt.setString(2, usuario.getPassUsr());
            stmt.setString(3, usuario.getTipoUsr());
            stmt.registerOutParameter(4,Types.INTEGER);
            stmt.execute();

            cod = stmt.getInt(4);


            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }

    public static int updateUsuario (Usuario usuario){
        int cod = 0;
        Connection c = null;
        CallableStatement stmt;
        String pass = "";
        if (usuario.getPassUsr().equals("-")){
            for (Usuario u : DataTables.getUsuarios()){
                if (u.getCodUsuario() == usuario.getCodUsuario()){
                    pass = u.getPassUsr();
                    break;
                }
            }
        } else pass = usuario.getPassUsr();
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareCall("CALL updateUsuario(?,?,?,?,?,?)");
            stmt.setInt(1, usuario.getCodUsuario());
            stmt.setString(2, usuario.getNombreUsr());
            stmt.setString(3, pass);
            stmt.setString(4, usuario.getEstadoUsr());
            stmt.setString(5, usuario.getTipoUsr());
            stmt.registerOutParameter(6,Types.INTEGER);
            stmt.execute();

            cod= stmt.getInt(6);

            stmt.close();
            c.close();
        }catch (Exception e){
            cod = 0;
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cod;
    }

    public static VerifyPacket verifyUserType(VerifyPacket packet) {
        Usuario usrClient = packet.getUser();
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codUsuario, nombreUsr, passUsr, estadoUsr, tipoUsr FROM usuario");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int codUsr = rs.getInt(1);
                String nombreUsr = rs.getString(2);
                String passUsr = rs.getString(3);
                String estadoUsr = rs.getString(4);
                String tipoUsr = rs.getString(5);

                Usuario usr = new Usuario(codUsr, nombreUsr, passUsr, estadoUsr, tipoUsr);
                DataCharge.users.add(usr);
            }
            rs.close();
            stmt.close();
            c.close();
            for (Usuario usuario : DataCharge.users) {
                if (usrClient.getNombreUsr().equals(usuario.getNombreUsr())) {
                    if (usrClient.getPassUsr().equals(usuario.getPassUsr())) {
                        if(usuario.getTipoUsr().equals("ADMIN")){
                            packet.setValidUser(true);
                        }
                    }
                }
            }
        } catch (SQLException troubles) {
            System.out.println("ERROR");
            troubles.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return packet;
    }

    public static DataPacket verifyUser(DataPacket packet) {
        Usuario usrClient = packet.getUser();
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codUsuario, nombreUsr, passUsr, estadoUsr, tipoUsr FROM usuario");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int codUsr = rs.getInt(1);
                String nombreUsr = rs.getString(2);
                String passUsr = rs.getString(3);
                String estadoUsr = rs.getString(4);
                String tipoUsr = rs.getString(5);

                Usuario usr = new Usuario(codUsr, nombreUsr, passUsr, estadoUsr, tipoUsr);
                DataCharge.users.add(usr);
            }
            rs.close();
            stmt.close();
            c.close();
            for (Usuario usuario : DataCharge.users) {
                if (usrClient.getNombreUsr().equals(usuario.getNombreUsr())) {
                    if (usrClient.getPassUsr().equals(usuario.getPassUsr())) {
                        packet.setUser(usuario);
                        packet.setValidUser(true);
                    }
                }
            }
        } catch (SQLException troubles) {
            System.out.println("ERROR");
            troubles.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return packet;
    }


    ///////// CARGA DE TABLAS
    public static void chargeServerTableUsuario(){
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codUsuario, nombreUsr, estadoUsr, tipoUsr FROM usuario");
            rs = stmt.executeQuery();

            while(rs.next()) {
                int codUsr = rs.getInt(1);
                String nombre = rs.getString(2);
                String estado = rs.getString(3);
                String tipoUsr = rs.getString(4);

                Usuario user = new Usuario(codUsr, nombre, estado, tipoUsr);
                DataTables.getUsuarios().add(user);

            }
            rs.close();
            stmt.close();
            c.close();
            }catch (SQLException troubles){
                System.out.println("ERROR");
                troubles.printStackTrace();
            }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void chargeServerTableCompania(){
        Connection c = null;
        PreparedStatement stmt, stmtr;
        ResultSet rs, rsr;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codCompania, nombreCom, estadoCom FROM compania");
            rs = stmt.executeQuery();

            while(rs.next()){
                ArrayList<RamoCompania> ramoCom = new ArrayList<RamoCompania>();
                int codCompania = rs.getInt(1);
                String nombreCom = rs.getString(2);
                String estado = rs.getString(3);

                Compania compania = new Compania(codCompania, nombreCom, estado);

                stmtr = c.prepareStatement("SELECT codRamo, descripcionRam, codCompania, estadoRam FROM ramo WHERE codCompania=?");
                stmtr.setInt(1, codCompania);
                rsr = stmtr.executeQuery();

                while(rsr.next()){

                    int codRamo = rsr.getInt(1);
                    String descripcion = rsr.getString(2);
                    int codCom = rsr.getInt(3);
                    String estadoR = rsr.getString(4);

                    RamoCompania ramo = new RamoCompania(codRamo, descripcion, codCom, estadoR);
                    ramoCom.add(ramo);

                }
                compania.setRamoCompanias(ramoCom);
                DataTables.getCompanias().add(compania);

                rsr.close();
                stmtr.close();
            }

            rs.close();
            stmt.close();
            c.close();
        }catch (SQLException troubles){
            System.out.println("ERROR");
            troubles.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public static void chargeServerTableRamos(){
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codRamo, descripcionRam, codCompania, estadoRam FROM ramo");
            rs = stmt.executeQuery();

            while (rs.next()) {

                int codRamo = rs.getInt(1);
                String descripcion = rs.getString(2);
                int codCom = rs.getInt(3);
                String estadoR = rs.getString(4);

                RamoCompania ramo = new RamoCompania(codRamo, descripcion, codCom, estadoR);
                DataTables.getRamos().add(ramo);

            }

            rs.close();
            stmt.close();
            c.close();

        }catch (SQLException troubles){
            System.out.println("ERROR");
            troubles.printStackTrace();

        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void chargeServerTablePeritos(){
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codPerito, nombrePer, nifPer, direccionPer, telefonoPer, emailPer, estadoPer FROM perito");
            rs = stmt.executeQuery();

            while(rs.next()){
                int codPer = rs.getInt(1);
                String nombre = rs.getString(2);
                String nif = rs.getString(3);
                String direccion = rs.getString(4);
                int telefono = rs.getInt(5);
                String email = rs.getString(6);
                String estado = rs.getString(7);

                Perito perito = new Perito(codPer, nombre, nif, direccion, telefono, email, estado);
                DataTables.getPeritos().add(perito);

            }

            rs.close();
            stmt.close();
            c.close();
        }catch (SQLException troubles){
            System.out.println("ERROR");
            troubles.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public static void chargeServerTableTelefonos(){
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codTelefono, telefonoAseg, codAsegurado FROM telefono");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int codTelf = rs.getInt(1);
                int telefono = rs.getInt(2);
                int codAseg = rs.getInt(3);
                TelefonoAsegurado nuevoTelf = new TelefonoAsegurado(codTelf, telefono, codAseg);
                DataTables.getTelefonos().add(nuevoTelf);
            }

            rs.close();
            stmt.close();
            c.close();

        }catch (SQLException troubles) {
            System.out.println("ERROR");
            troubles.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void chargeServerTableTipoInt(){
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codTipoInt, descripTipoInt, estadoTipoInt FROM tipointervencion");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int codTipoInt = rs.getInt(1);
                String descripTipoInt = rs.getString(2);
                String estadoTipoInt = rs.getString(3);

                TipoIntervencion ti = new TipoIntervencion(codTipoInt, descripTipoInt, estadoTipoInt);
                DataTables.getTipoInt().add(ti);

            }
            rs.close();
            stmt.close();
            c.close();

        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void chargeServerTableTipoSin(){
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codTipoSin, descripTSiniestro, estadoTSin FROM tiposiniestro");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int codTipoS = rs.getInt(1);
                String descripTipoS = rs.getString(2);
                String estadoTipoS = rs.getString(3);

                TipoSiniestro ts = new TipoSiniestro(codTipoS, descripTipoS, estadoTipoS);
                DataTables.getTipoSiniestros().add(ts);

            }
            rs.close();
            stmt.close();
            c.close();

        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void chargeServerTableTipoReap(){
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codTipoReapertura, tipoReapertura, estadoTipoReap FROM tiporeapertura");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int codTipoR = rs.getInt(1);
                String descripTipoR = rs.getString(2);
                String estadoTipoR = rs.getString(3);

                TipoReapertura tr = new TipoReapertura(codTipoR, descripTipoR, estadoTipoR);
                DataTables.getTipoReaperturas().add(tr);

            }
            rs.close();
            stmt.close();
            c.close();

        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void chargeServerTableReaperturas(){
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT codReapertura, codRegistroAsociado, codReaperturaReg, codTipoReapertura FROM reapertura");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int codReapertura = rs.getInt(1);
                int registroBase = rs.getInt(2);
                int reapertura = rs.getInt(3);
                int tipoReapertura = rs.getInt(4);

                Reapertura reap = new Reapertura(codReapertura, registroBase, reapertura, tipoReapertura);
                DataTables.getReaperturas().add(reap);

            }
            rs.close();
            stmt.close();
            c.close();

        }catch (Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Registro> chargeServerAllRegistros(){

        ArrayList<Registro> registros = new ArrayList<>();
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT registro.codRegistro, registro.codPerito, registro.fechaApertura, registro.referenciaCia, registro.codAsegCom, registro.descripcionReg, registro.fechaEntregaPer, registro.estadoEntrega, registro.estadoReg," +
                    " registro.codFactura, registro.fechaCierre, registro.observaciones, registro.codUsuario, registro.numeroReaperturas, registro.tipoRegistro, registro.tipoInt, registro.contactoReg, siniestro.codAsegurado, asegurado.nombreAseg," +
                    " asegurado.direccionRiesgo, asegurado.direccionContacto, asegurado.contactoAseg, siniestro.codCompania, siniestro.codRamo, siniestro.codTipoSin, siniestro.numPoliza, factura.numFactura," +
                    " factura.codHGabinete, factura.codHPerito, factura.estadoPagoFac, factura.mesFacPerito, factura.anoFacPerito, hgabinete.honorarioGab, hgabinete.locomocionGab, hgabinete.variosGab, hgabinete.sumaGab," +
                    " hgabinete.ivaGab, hgabinete.totalGab, hperito.honorarioPer, hperito.locomocionPer, hperito.ajustesPer, hperito.sumaPer, hperito.ivaPer, hperito.irpfPer, hperito.totalPer" +
                    " FROM registro, siniestro , asegurado, factura, hgabinete, hperito" +
                    " WHERE registro.codAsegCom=siniestro.codSiniestro AND siniestro.codAsegurado=asegurado.codAsegurado AND factura.codHGabinete=hgabinete.codHGabinete AND factura.codHPerito=hperito.codHPerito" +
                    " AND siniestro.codSiniestro=registro.codAsegCom AND factura.codFactura=registro.codFactura; ");
            rs = stmt.executeQuery();

            while(rs.next()){
                int codRegistro = rs.getInt(1);
                int codPerito = rs.getInt(2);
                Date fechaApertura = rs.getDate(3);
                String refCia = rs.getString(4);
                int codSiniestro = rs.getInt(5);
                String descripcion = rs.getString(6);
                Date fechaEntregaPer = rs.getDate(7);
                String estadoEntrega = rs.getString(8);
                String estadoReg = rs.getString(9);
                int codFactura = rs.getInt(10);
                Date fechaCierre = rs.getDate(11);
                String observaciones = rs.getString(12);
                int codUsuario = rs.getInt(13);
                int numReaperturas = rs.getInt(14);
                String tipoRegistro = rs.getString(15);
                int tipoIntervencion = rs.getInt(16);
                String contactoReg = rs.getString(17);

                //ASEGURADO
                int codAseg = rs.getInt(18);
                String nombre = rs.getString(19);
                String dirRiesgo = rs.getString(20);
                String dirContacto = rs.getString(21);
                String contactoAseg = rs.getString(22);

                Asegurado asegurado = new Asegurado(codAseg, nombre, dirRiesgo, dirContacto, contactoAseg);

                ArrayList<TelefonoAsegurado> telfs = new ArrayList<TelefonoAsegurado>();
                for (TelefonoAsegurado ta : DataTables.getTelefonos()){
                    if(ta.getAsegurado() == codAseg){
                        telfs.add(ta);
                    }
                }
                asegurado.setTelefonosAseg(telfs);

                //SINIESTRO

                int codCompania = rs.getInt(23);
                int codRamo = rs.getInt(24);
                int codTipoSin = rs.getInt(25);
                String numPoliza = rs.getString(26);
                Siniestro siniestro = new Siniestro(codSiniestro, asegurado, codCompania,codRamo, codTipoSin, numPoliza);


                //FACTURA
                String numFac = rs.getString(27);
                int codHGab = rs.getInt(28);
                int codHPer = rs.getInt(29);
                String estado = rs.getString(30);
                int mesPerito = rs.getInt(31);
                int anoPerito = rs.getInt(32);
                double hGab = rs.getDouble(33);
                double locGab = rs.getDouble(34);
                double variosGab = rs.getDouble(35);
                double sumaGab = rs.getDouble(36);
                double ivaGab = rs.getDouble(37);
                double totalGab = rs.getDouble(38);
                double hPer = rs.getDouble(39);
                double locPer = rs.getDouble(40);
                double ajustesPer = rs.getDouble(41);
                double sumaPer = rs.getDouble(42);
                double ivaPer = rs.getDouble(43);
                double irpfPer = rs.getDouble(44);
                double totalPer = rs.getDouble(45);

                Factura factura = new Factura(codFactura, numFac, estado, mesPerito, anoPerito);
                factura.sethGabinete(new HGabinete(codHGab, hGab, locGab, variosGab, sumaGab, ivaGab, totalGab));
                factura.sethPerito(new HPerito(codHPer, hPer, locPer, ajustesPer, sumaPer, ivaPer, irpfPer, totalPer));


                Registro registro = new Registro(codRegistro, codPerito, fechaApertura, refCia, siniestro, descripcion,
                        fechaEntregaPer, estadoEntrega, estadoReg, factura, fechaCierre, observaciones, codUsuario, numReaperturas, tipoRegistro);
                registro.setTipoInt(tipoIntervencion);
                registro.setContactoReg(contactoReg);
                registros.add(registro);
            }

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception troubles) {
            System.out.println("ERROR");
            troubles.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return registros;
    }

    public static ArrayList<Registro> chargeClientRegistros(Date desde, Date hasta){

        ArrayList<TelefonoAsegurado> listaTelefonos = DataCharge.getTableTelefonos();
        ArrayList<Registro> registros = new ArrayList<>();
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            java.sql.Date dateDesde = new java.sql.Date(desde.getTime());
            java.sql.Date dateHasta = new java.sql.Date(hasta.getTime());
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT registro.codRegistro, registro.codPerito, registro.fechaApertura, registro.referenciaCia, registro.codAsegCom, registro.descripcionReg, registro.fechaEntregaPer, registro.estadoEntrega, registro.estadoReg," +
                    " registro.codFactura, registro.fechaCierre, registro.observaciones, registro.codUsuario, registro.numeroReaperturas, registro.tipoRegistro, registro.tipoInt, registro.contactoReg, siniestro.codAsegurado, asegurado.nombreAseg," +
                    " asegurado.direccionRiesgo, asegurado.direccionContacto, asegurado.contactoAseg, siniestro.codCompania, siniestro.codRamo, siniestro.codTipoSin, siniestro.numPoliza, factura.codFactura, factura.numFactura," +
                    " factura.codHGabinete, factura.codHPerito, factura.estadoPagoFac, factura.mesFacPerito, factura.anoFacPerito, hgabinete.honorarioGab, hgabinete.locomocionGab, hgabinete.variosGab, hgabinete.sumaGab," +
                    " hgabinete.ivaGab, hgabinete.totalGab, hperito.honorarioPer, hperito.locomocionPer, hperito.ajustesPer, hperito.sumaPer, hperito.ivaPer, hperito.irpfPer, hperito.totalPer" +
                    " FROM registro, siniestro , asegurado, factura, hgabinete, hperito" +
                    " WHERE registro.codAsegCom=siniestro.codSiniestro AND siniestro.codAsegurado=asegurado.codAsegurado AND factura.codHGabinete=hgabinete.codHGabinete AND factura.codHPerito=hperito.codHPerito" +
                    " AND siniestro.codSiniestro=registro.codAsegCom AND factura.codFactura=registro.codFactura AND registro.fechaApertura BETWEEN ? AND ? ORDER BY codRegistro DESC");
            stmt.setDate(1,dateDesde);
            stmt.setDate(2,dateHasta);
            rs = stmt.executeQuery();

            while(rs.next()){
                int codRegistro = rs.getInt(1);
                int codPerito = rs.getInt(2);
                Date fechaApertura = rs.getDate(3);
                String refCia = rs.getString(4);
                int codSiniestro = rs.getInt(5);
                String descripcion = rs.getString(6);
                Date fechaEntregaPer = rs.getDate(7);
                String estadoEntrega = rs.getString(8);
                String estadoReg = rs.getString(9);
                int codFactura = rs.getInt(10);
                Date fechaCierre;
                try {
                    fechaCierre = rs.getDate(11);
                }catch (Exception e){
                    fechaCierre = new Date();
                }
                String observaciones = rs.getString(12);
                int codUsuario = rs.getInt(13);
                int numReaperturas = rs.getInt(14);
                String tipoRegistro = rs.getString(15);
                int tipoIntervencion = rs.getInt(16);
                String contactoReg = rs.getString(17);

                //ASEGURADO
                int codAseg = rs.getInt(18);
                String nombre = rs.getString(19);
                String dirRiesgo = rs.getString(20);
                String dirContacto = rs.getString(21);
                String contactoAseg = rs.getString(22);

                Asegurado asegurado = new Asegurado(codAseg, nombre, dirRiesgo, dirContacto, contactoAseg);

                //TELEFONOS
                ArrayList<TelefonoAsegurado> telfs = new ArrayList<TelefonoAsegurado>();
                for (TelefonoAsegurado ta : listaTelefonos){
                    if(ta.getAsegurado() == codAseg){
                        telfs.add(ta);
                    }
                }
                asegurado.setTelefonosAseg(telfs);

                //SINIESTRO
                int codCompania = rs.getInt(23);
                int codRamo = rs.getInt(24);
                int codTipoSin = rs.getInt(25);
                String numPoliza = rs.getString(26);
                Siniestro siniestro = new Siniestro(codSiniestro, asegurado, codCompania,codRamo, codTipoSin, numPoliza);

                //FACTURA
                int codFac = rs.getInt(27);
                String numFac = rs.getString(28);
                int codHGab = rs.getInt(29);
                int codHPer = rs.getInt(30);
                String estado = rs.getString(31);
                int mesPerito = rs.getInt(32);
                int anoPerito = rs.getInt(33);
                double hGab = rs.getDouble(34);
                double locGab = rs.getDouble(35);
                double variosGab = rs.getDouble(36);
                double sumaGab = rs.getDouble(37);
                double ivaGab = rs.getDouble(38);
                double totalGab = rs.getDouble(39);
                double hPer = rs.getDouble(40);
                double locPer = rs.getDouble(41);
                double ajustesPer = rs.getDouble(42);
                double sumaPer = rs.getDouble(43);
                double ivaPer = rs.getDouble(44);
                double irpfPer = rs.getDouble(45);
                double totalPer = rs.getDouble(46);

                Factura factura = new Factura(codFac, numFac, estado, mesPerito, anoPerito);
                factura.sethGabinete(new HGabinete(codHGab, hGab, locGab, variosGab, sumaGab, ivaGab, totalGab));
                factura.sethPerito(new HPerito(codHPer, hPer, locPer, ajustesPer, sumaPer, ivaPer, irpfPer, totalPer));

                Registro registro = new Registro(codRegistro, codPerito, fechaApertura, refCia, siniestro, descripcion,
                        fechaEntregaPer, estadoEntrega, estadoReg, factura, fechaCierre, observaciones, codUsuario, numReaperturas, tipoRegistro);
                registro.setTipoInt(tipoIntervencion);
                registro.setContactoReg(contactoReg);
                registros.add(registro);
            }

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception troubles) {
            System.out.println("ERROR");
            troubles.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return registros;
    }

    public static ArrayList<Reapertura> chargeClientReaperturas(Date desde, Date hasta){
        ArrayList<Reapertura> reap = new ArrayList<>();
        Connection c = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            java.sql.Date dateDesde = new java.sql.Date(desde.getTime());
            java.sql.Date dateHasta = new java.sql.Date(hasta.getTime());
            c = ConnectionPool.getConnection();
            stmt = c.prepareStatement("SELECT reapertura.codReapertura, reapertura.codRegistroAsociado, reapertura.codReaperturaReg, reapertura.codTipoReapertura FROM reapertura, registro \n" +
                    "WHERE reapertura.codReaperturaReg=registro.codRegistro AND registro.fechaApertura BETWEEN ? AND ?");
            stmt.setDate(1,dateDesde);
            stmt.setDate(2,dateHasta);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int codReapertura = rs.getInt(1);
                int registroBase = rs.getInt(2);
                int reapertura = rs.getInt(3);
                int tipoReapertura = rs.getInt(4);

                Reapertura r = new Reapertura(codReapertura, registroBase, reapertura, tipoReapertura);
                reap.add(r);

            }
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception troubles) {
            System.out.println("ERROR");
            troubles.printStackTrace();
        }finally {
            try {
                if (null != c) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reap;
    }

    public DataPacket getData() {
        return data;
    }

    public void setData(DataPacket data) {
        this.data = data;
    }


}
