package clases;

import java.io.Serializable;

public class Siniestro implements Serializable {

    //VALORES
    private int codSiniestro;
    private Asegurado asegurado;
    private int compania;
    private int ramo;
    private String numPoliza;
    private int tipoSiniestro;

    //CONSTRUCTOR DEFAULT
    public Siniestro(){
        this.codSiniestro = 0;
        this.asegurado = new Asegurado();
        this.compania = 0;
        this.ramo = 0;
        this.numPoliza = "0";
        this.tipoSiniestro = 0;
    }

    //CONTRUCTOR APERTURA
    public Siniestro(int codSiniestro, Asegurado asegurado, int compania, int ramo, int tipoSin, String numPoliza) {
        this.codSiniestro = codSiniestro;
        this.asegurado = asegurado;
        this.compania = compania;
        this.ramo = ramo;
        this.numPoliza = numPoliza;
        this.tipoSiniestro = tipoSin;
    }

    //GUETTERS Y SETTERS

    //CODIGO SINIESTRO
    public int getCodSiniestro() {
        return codSiniestro;
    }

    public void setCodSiniestro(int codSiniestro) {
        this.codSiniestro = codSiniestro;
    }

    //CODIGO ASEGURADO
    public Asegurado getAsegurado() {
        return asegurado;
    }

    public void setAsegurado(Asegurado asegurado) {
        this.asegurado = asegurado;
    }

    //CODIGO COMPANIA
    public int getCompania() {
        return compania;
    }

    public void setCompania(int compania) {
        this.compania = compania;
    }

    //RAMO ASOCIADO AL SINIESTRO, COMPANIA Y REGISTRO
    public int getRamo() {
        return ramo;
    }

    public void setRamo(int ramo) {
        this.ramo = ramo;
    }

    //NUMERO DE POLIZA
    public String getNumPoliza() {
        return numPoliza;
    }

    public void setNumPoliza(String numPoliza) {
        this.numPoliza = numPoliza;
    }

    //TIPO SINIESTRO
    public int getTipoSiniestro() {
        return tipoSiniestro;
    }

    public void setTipoSiniestro(int tipoSiniestro) {
        this.tipoSiniestro = tipoSiniestro;
    }


    //TO STRING

    @Override
    public String toString() {
        return "Siniestro{" +
                "codSiniestro=" + codSiniestro +
                ", asegurado=" + asegurado +
                ", compania=" + compania +
                ", ramo=" + ramo +
                ", numPoliza='" + numPoliza + '\'' +
                ", tipoSiniestro=" + tipoSiniestro +
                '}';
    }
}
