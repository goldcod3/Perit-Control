package conexion;

import clases.*;

import java.io.Serializable;
import java.util.ArrayList;

public class DataTables implements Serializable {

    private static final long serialVersionUID = 280321L;

    //TABLAS ESTATICAS
    public static ArrayList<Registro> registros = new ArrayList<Registro>();
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

    public static boolean addRegistroClientTable(Registro target){
        boolean isAdd = false;
        DataTables.getRegistros().add(target);
        for(Registro r : DataTables.getRegistros()){
            if (r.getCodRegistro() == target.getCodRegistro()) isAdd = true;
        }
        return isAdd;
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

    public static void addReaperturaClientTable(Reapertura target){
        DataTables.getReaperturas().add(target);
    }

    public static ArrayList<Registro> getRegistros() {
        return registros;
    }

    public static void setRegistros(ArrayList<Registro> registros) {
        DataTables.registros = registros;
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

    public static ArrayList<TipoIntervencion> getTipoInt() {
        return tipoInt;
    }

    public static void setTipoInt(ArrayList<TipoIntervencion> tipoInt) {
        DataTables.tipoInt = tipoInt;
    }

    public static ArrayList<TipoSiniestro> getTipoSiniestros() {
        return tipoSiniestros;
    }

    public static void setTipoSiniestros(ArrayList<TipoSiniestro> tipoSiniestros) {
        DataTables.tipoSiniestros = tipoSiniestros;
    }

    public static ArrayList<Reapertura> getReaperturas() {
        return DataTables.reaperturas;
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
}

