package clases;

import conexion.DataTables;

import java.io.Serializable;
import java.util.ArrayList;

public class Reapertura implements Serializable {

    //VALORES
    private int codReapertura;
    private int registroAsociado;
    private int reapertura;
    private int tipoReapertura;

    public Reapertura(int registroAsoc){
        this.codReapertura = 0;
        this.registroAsociado = registroAsoc;
        this.reapertura = 0;
        this.tipoReapertura = 0;
    }

    public Reapertura(int codReapertura, int registroAsoc, int reapertura, int tipoReapertura){
        this.codReapertura = codReapertura;
        this.registroAsociado = registroAsoc;
        this.reapertura = reapertura;
        this.tipoReapertura = tipoReapertura;
    }

    //DEVUELVE Reapertura de tipo Registro con datos del registro pasado por parametro
    public Registro getReaperturaFromRegistro(Registro base){
        Registro reap = new Registro();
        reap.setCodRegistro(0);
        reap.setPerito(base.getPerito());
        reap.getSiniestro().setCompania(base.getSiniestro().getCompania());
        reap.getSiniestro().setRamo(base.getSiniestro().getRamo());
        reap.getSiniestro().setTipoSiniestro(base.getSiniestro().getTipoSiniestro());
        reap.setReferenciaCom(base.getReferenciaCom());
        reap.getSiniestro().setNumPoliza(base.getSiniestro().getNumPoliza());
        reap.setContactoReg(base.getContactoReg());
        reap.setTipoInt(base.getTipoInt());
        reap.getSiniestro().setAsegurado(base.getSiniestro().getAsegurado());
        reap.getSiniestro().getAsegurado().setTelefonosAseg(base.getSiniestro().getAsegurado().getTelefonosAseg());
        reap.setTipoRegistro("REAPERTURA");
        return reap;
    }

    public Reapertura getReaperturaFromCodigo(int codigo){
        Reapertura temp = new Reapertura(0);
        for (Reapertura rp : DataTables.getReaperturas()) {
            if(rp.getReapertura() == codigo){
                temp = rp;
                break;
            }
        }
        return temp;
    }

    //GUETTERS Y SETTERS

    //CODIGO REAPERTURA
    public int getCodReapertura() {
        return codReapertura;
    }

    public void setCodReapertura(int codReapertura) {
        this.codReapertura = codReapertura;
    }

    //REGISTRO ASOCIADO
    public int getRegistroAsociado() {
        return this.registroAsociado;
    }

    public void setRegistroAsociado(int registroAsociado) {
        this.registroAsociado = registroAsociado;
    }

    //REGISTRO REAPERTURA
    public int getReapertura() {
        return this.reapertura;
    }

    public void setReapertura(int reapertura) {
        this.reapertura = reapertura;
    }

    //TIPO DE REAPERTURA
    public int getTipoReapertura() {
        return this.tipoReapertura;
    }

    public void setTipoReapertura(int tipoReapertura) {
        this.tipoReapertura = tipoReapertura;
    }

    //TO STRING
    @Override
    public String toString() {
        return "Reapertura{" +
                "codReapertura=" + codReapertura +
                ", registroAsociado=" + registroAsociado +
                ", reapertura=" + reapertura +
                ", tipoReapertura=" + tipoReapertura +
                '}';
    }
}
