package conexion.paquetes;

import clases.*;
import conexion.DataTables;

import java.util.ArrayList;

public class TablePacket extends DataPacket {

    private ArrayList<Registro> paqRegistros = new ArrayList<Registro>();
    private ArrayList<Asegurado> paqAsegurados = new ArrayList<Asegurado>();
    private ArrayList<Perito> paqPeritos = new ArrayList<Perito>();
    private ArrayList<Compania> paqCompanias = new ArrayList<Compania>();
    private ArrayList<RamoCompania> paqRamos = new ArrayList<RamoCompania>();
    private ArrayList<TipoSiniestro> paqTipoSiniestros = new ArrayList<TipoSiniestro>();
    private ArrayList<TelefonoAsegurado> paqTelefonos = new ArrayList<TelefonoAsegurado>();
    private ArrayList<TipoIntervencion> paqTipoInt = new ArrayList<TipoIntervencion>();
    private ArrayList<Usuario> paqUsuarios = new ArrayList<Usuario>();
    private ArrayList<Reapertura> paqReaperturas = new ArrayList<Reapertura>();
    private ArrayList<TipoReapertura> paqTipoReaperturas = new ArrayList<TipoReapertura>();


    public TablePacket(String ip, Usuario usr){
        super(ip, usr);
        this.setCodPacket(2);
    }

    public TablePacket(int codPacket, Usuario user, String ipClient) {
        super(codPacket, user, ipClient);
    }

    public void chargeDataPacket() {
            this.setPaqPeritos(DataTables.getPeritos());
            this.setPaqCompanias(DataTables.getCompanias());
            this.setPaqRamos(DataTables.getRamos());
            this.setPaqTipoSiniestros(DataTables.getTipoSiniestros());
            this.setPaqTipoInt(DataTables.getTipoInt());
            this.setPaqUsuarios(DataTables.getUsuarios());
            this.setPaqTipoReaperturas(DataTables.getTipoReaperturas());
    }

    public void extractDataPacket() {
            DataTables.setRegistros(this.paqRegistros);
            DataTables.setPeritos(this.paqPeritos);
            DataTables.setCompanias(this.paqCompanias);
            DataTables.setRamos(this.paqRamos);
            DataTables.setTipoSiniestros(this.paqTipoSiniestros);
            DataTables.setTipoInt(this.paqTipoInt);
            DataTables.setUsuarios(this.paqUsuarios);
            DataTables.setReaperturas(new ArrayList<>(this.paqReaperturas));
            DataTables.setTipoReaperturas(this.paqTipoReaperturas);
    }

    public ArrayList<TelefonoAsegurado> getPaqTelefonos() {
        return paqTelefonos;
    }

    public void setPaqTelefonos(ArrayList<TelefonoAsegurado> paqTelefonos) {
        this.paqTelefonos = paqTelefonos;
    }

    public ArrayList<Asegurado> getPaqAsegurados() {
        return paqAsegurados;
    }

    public void setPaqAsegurados(ArrayList<Asegurado> paqAsegurados) {
        this.paqAsegurados = paqAsegurados;
    }

    public ArrayList<Registro> getPaqRegistros() {
        return paqRegistros;
    }

    public void setPaqRegistros(ArrayList<Registro> paqRegistros) {
        this.paqRegistros = paqRegistros;
    }

    public ArrayList<Perito> getPaqPeritos() {
        return paqPeritos;
    }

    public void setPaqPeritos(ArrayList<Perito> paqPeritos) {
        this.paqPeritos = paqPeritos;
    }

    public ArrayList<Compania> getPaqCompanias() {
        return paqCompanias;
    }

    public void setPaqCompanias(ArrayList<Compania> paqCompanias) {
        this.paqCompanias = paqCompanias;
    }

    public ArrayList<RamoCompania> getPaqRamos() {
        return paqRamos;
    }

    public void setPaqRamos(ArrayList<RamoCompania> paqRamos) {
        this.paqRamos = paqRamos;
    }

    public ArrayList<TipoSiniestro> getPaqTipoSiniestros() {
        return paqTipoSiniestros;
    }

    public ArrayList<TipoIntervencion> getPaqTipoInt() {
        return paqTipoInt;
    }

    public void setPaqTipoInt(ArrayList<TipoIntervencion> paqTipoInt) {
        this.paqTipoInt = paqTipoInt;
    }

    public void setPaqTipoSiniestros(ArrayList<TipoSiniestro> paqTipoSiniestros) {
        this.paqTipoSiniestros = paqTipoSiniestros;
    }

    public ArrayList<Usuario> getPaqUsuarios() {
        return paqUsuarios;
    }

    public void setPaqUsuarios(ArrayList<Usuario> paqUsuarios) {
        this.paqUsuarios = paqUsuarios;
    }

    public ArrayList<Reapertura> getPaqReaperturas() {
        return paqReaperturas;
    }

    public  void setPaqReaperturas(ArrayList<Reapertura> paqReaperturas) {
        this.paqReaperturas = paqReaperturas;
    }

    public ArrayList<TipoReapertura> getPaqTipoReaperturas() {
        return paqTipoReaperturas;
    }

    public void setPaqTipoReaperturas(ArrayList<TipoReapertura> paqTipoReaperturas) {
        this.paqTipoReaperturas = paqTipoReaperturas;
    }
}
