package conexion;

import clases.*;
import interfaces.Config;
import interfaces.icontrol.pcontrol.PanelControl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DataTables implements Serializable {

    private static final long serialVersionUID = 280321L;

    //TABLAS ESTATICAS
    private static ArrayList<Registro> registros = new ArrayList<Registro>();
    private static ArrayList<Registro> regActivos = new ArrayList<Registro>();
    private static ArrayList<Registro> search = new ArrayList<Registro>();
    private static ArrayList<TelefonoAsegurado> telefonos = new ArrayList<TelefonoAsegurado>();
    private static ArrayList<Perito> peritos = new ArrayList<Perito>();
    private static ArrayList<Compania> companias = new ArrayList<Compania>();
    private static ArrayList<RamoCompania> ramos = new ArrayList<RamoCompania>();
    private static ArrayList<TipoSiniestro> tipoSiniestros = new ArrayList<TipoSiniestro>();
    private static ArrayList<TipoIntervencion> tipoInt = new ArrayList<TipoIntervencion>();
    private static ArrayList<Reapertura> reaperturas = new ArrayList<Reapertura>();
    private static ArrayList<TipoReapertura> tipoReaperturas = new ArrayList<TipoReapertura>();
    private static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();



    public DataTables(){

    }

    //Carga las tablas Registros y Peritos de Arrays en DataTables
    public static void chargeTablesClient(){

        // REGISTROS ACTIVOS
        if (Config.pControl.pControl.rbtnAbiertos.isSelected()){
            DataTables.setRegActivos(new ArrayList<Registro>());
            for (Registro reg : DataTables.getRegistros()){
                if(reg.getEstadoRegistro().equals("ABIERTO")){
                    Object [] r;

                    String nombreCom  = "Default";
                    String nombrePer = "Default";

                    for (Compania com : DataTables.getCompanias()){
                        if (com.getCodCompania() == reg.getSiniestro().getCompania()){
                            nombreCom = com.getNombreCompania();
                        }
                    }
                    for (Perito per : DataTables.getPeritos()){
                        if (per.getCodPerito() == reg.getPerito()){
                            nombrePer = per.getNombrePerito();
                        }
                    }

                    String fechaApertura = new SimpleDateFormat("dd/MM/yyyy").format(reg.getFechaApertura());

                    r = new Object[]{reg.getCodRegistro(), nombreCom, reg.getReferenciaCom(),reg.getEstadoRegistro(), nombrePer,
                            fechaApertura, reg.getFacturaRegistro().getEstadoFactura(), reg.getTipoRegistro()};
                    Config.pControl.pControl.modelRegistro.addRow(r);
                }

            }
            Config.pControl.pControl.actNumeroRegistros();
            Config.pControl.pControl.actComboCompania();

        } else if(Config.pControl.pControl.rbtnTodos.isSelected()){
            DataTables.setRegActivos(new ArrayList<Registro>());
            for (Registro reg : DataTables.getRegistros()){
                Object [] r;

                String nombreCom  = "Default";
                String nombrePer = "Default";

                for (Compania com : DataTables.getCompanias()){
                    if (com.getCodCompania() == reg.getSiniestro().getCompania()){
                        nombreCom = com.getNombreCompania();
                    }
                }
                for (Perito per : DataTables.getPeritos()){
                    if (per.getCodPerito() == reg.getPerito()){
                        nombrePer = per.getNombrePerito();
                    }
                }

                String fechaApertura = new SimpleDateFormat("dd/MM/yyyy").format(reg.getFechaApertura());


                r = new Object[]{reg.getCodRegistro(), nombreCom, reg.getReferenciaCom(),reg.getEstadoRegistro(), nombrePer,
                        fechaApertura, reg.getFacturaRegistro().getEstadoFactura(), reg.getTipoRegistro()};

                Config.pControl.pControl.modelRegistro.addRow(r);
            }
            Config.pControl.pControl.actNumeroRegistros();
            Config.pControl.pControl.actComboCompania();
        }

        // PERITOS
        if (Config.pControl.pPeritos.todos.isSelected()){
            for (Perito per : DataTables.getPeritos()){
                int numRegistros = 0;
                for (Registro reg : DataTables.getRegistros()){
                    if (per.getCodPerito() == reg.getPerito()) numRegistros++;
                }
                Object [] p = {per. getCodPerito(), per.getNombrePerito(), per.getTelfPerito(), per.getEmailPerito(), numRegistros};
                Config.pControl.pPeritos.modelPeritos.addRow(p);
            }
        }else if(Config.pControl.pPeritos.activos.isSelected()){
            for (Perito per : DataTables.getPeritos()){
                if (per.getEstadoPerito().equals("ACTIVO")){
                    int numRegistros = 0;
                    for (Registro reg : DataTables.getRegistros()){
                        if (per.getCodPerito() == reg.getPerito()) numRegistros++;
                    }
                    Object [] p = {per. getCodPerito(), per.getNombrePerito(), per.getTelfPerito(), per.getEmailPerito(), numRegistros};
                    Config.pControl.pPeritos.modelPeritos.addRow(p);
                }
            }


        }

    }

    public static boolean updReaperturaClientTable(Reapertura target){
        boolean upd = false;
        for(Reapertura r : DataTables.getReaperturas()){
            if (r.getRegistroAsociado() == target.getRegistroAsociado()){
                r = new Reapertura(target.getCodReapertura(), target.getRegistroAsociado(), target.getReapertura(), target.getTipoReapertura());
                break;
            }
        }
        for(Reapertura r : DataTables.getReaperturas()){
            if (r.getReapertura() == target.getReapertura()){
                upd = true;
                break;
            }
        }
        return upd;
    }


    public static void cleanRegistros(){
        int filasRegistro = Config.pControl.pControl.modelRegistro.getRowCount();
        for (int i = 0; i < filasRegistro; i++){
            Config.pControl.pControl.modelRegistro.removeRow(0);
        }
    }

    public static void cleanBusqueda(){
        int filasRegistro = Config.pControl.pControl.modelBusqueda.getRowCount();
        for (int i = 0; i < filasRegistro; i++){
            Config.pControl.pControl.modelBusqueda.removeRow(0);
        }
    }

    public static void cleanTablesClient(){
        int filasRegistro = Config.pControl.pControl.modelRegistro.getRowCount();
        for (int i = 0; i < filasRegistro; i++){
            Config.pControl.pControl.modelRegistro.removeRow(0);
        }
        int filasPerito = Config.pControl.pPeritos.modelPeritos.getRowCount();
        for (int i = 0; i < filasPerito; i++){
            Config.pControl.pPeritos.modelPeritos.removeRow(0);
        }
    }

    public static void cleanDataTables(){
        DataTables.setRegistros(new ArrayList<Registro>());
        DataTables.setPeritos(new ArrayList<Perito>());
        DataTables.setCompanias(new ArrayList<Compania>());
        DataTables.setTipoSiniestros(new ArrayList<TipoSiniestro>());
        DataTables.setUsuarios(new ArrayList<Usuario>());
        DataTables.setTipoReaperturas(new ArrayList<TipoReapertura>());
        DataTables.setReaperturas(new ArrayList<Reapertura>());
    }

    public static void actTablasPanelControl(){
        DataTables.cleanTablesClient();
        DataTables.chargeTablesClient();
    }

    public static ArrayList<Registro> getRegistros() {
        return registros;
    }

    public static void setRegistros(ArrayList<Registro> registros) {
        DataTables.registros = registros;
    }

    public static ArrayList<Registro> getRegActivos() {
        return regActivos;
    }

    public static void setRegActivos(ArrayList<Registro> regActivos) {
        DataTables.regActivos = regActivos;
    }

    public static ArrayList<Registro> getSearch() {
        return search;
    }

    public static void setSearch(ArrayList<Registro> search) {
        DataTables.search = search;
    }

    public static ArrayList<Perito> getPeritos() {
        return peritos;
    }

    public static void setPeritos(ArrayList<Perito> peritos) {
        DataTables.peritos = peritos;
    }

    public static ArrayList<Compania> getCompanias() {
        return companias;
    }

    public static void setCompanias(ArrayList<Compania> companias) {
        DataTables.companias = companias;
    }

    public static ArrayList<RamoCompania> getRamos() {
        return ramos;
    }

    public static void setRamos(ArrayList<RamoCompania> ramos) {
        DataTables.ramos = ramos;
    }

    public static ArrayList<TipoSiniestro> getTipoSiniestros() {
        return tipoSiniestros;
    }

    public static void setTipoSiniestros(ArrayList<TipoSiniestro> tipoSiniestros) {
        DataTables.tipoSiniestros = tipoSiniestros;
    }

    public static ArrayList<TipoIntervencion> getTipoInt() {
        return tipoInt;
    }

    public static void setTipoInt(ArrayList<TipoIntervencion> tipoInt) {
        DataTables.tipoInt = tipoInt;
    }

    public static ArrayList<TelefonoAsegurado> getTelefonos() {
        return telefonos;
    }

    public static void setTelefonos(ArrayList<TelefonoAsegurado> telefonos) {
        DataTables.telefonos = telefonos;
    }

    public static ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public static void setUsuarios(ArrayList<Usuario> usuarios) {
        DataTables.usuarios = usuarios;
    }

    public static ArrayList<Reapertura> getReaperturas() {
        return reaperturas;
    }

    public static void setReaperturas(ArrayList<Reapertura> reaperturas) {
        DataTables.reaperturas = reaperturas;
    }

    public static ArrayList<TipoReapertura> getTipoReaperturas() {
        return tipoReaperturas;
    }

    public static void setTipoReaperturas(ArrayList<TipoReapertura> tipoReaperturas) {
        DataTables.tipoReaperturas = tipoReaperturas;
    }


}

