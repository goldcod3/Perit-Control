package clases;

import java.io.Serializable;

public class TipoSiniestro implements Serializable {

    //VALORES
    private int codTipoSiniestro;
    private String descripTipoSiniestro;
    private String estadoTipoSin = "ACTIVO";

    //CONSTRUCTOR DEFAULT
    public TipoSiniestro(){
        this.codTipoSiniestro = 1;
        this.descripTipoSiniestro = "";
    }

    //CONSTRUCTOR APERTURA
    public TipoSiniestro(int tipoSiniestro, String descripTipoSiniestro, String estado) {
        this.codTipoSiniestro = tipoSiniestro;
        this.descripTipoSiniestro = descripTipoSiniestro;
        this.estadoTipoSin = estado;
    }

    //CONSTRUCTOR APERTURA
    public TipoSiniestro(int tipoSiniestro, String descripTipoSiniestro) {
        this.codTipoSiniestro = tipoSiniestro;
        this.descripTipoSiniestro = descripTipoSiniestro;
    }

    //GUETTERS Y SETTERS

    //CODIGO TIPO SINIESTRO
    public int getCodTipoSiniestro() {
        return codTipoSiniestro;
    }

    public void setCodTipoSiniestro(int codTipoSiniestro) {
        this.codTipoSiniestro = codTipoSiniestro;
    }

    //DESCRIPCION SINIESTRO
    public String getDescripTipoSiniestro() {
        return descripTipoSiniestro;
    }

    public void setDescripTipoSiniestro(String descripTipoSiniestro) {
        this.descripTipoSiniestro = descripTipoSiniestro;
    }

    public String getEstadoTipoSin() {
        return estadoTipoSin;
    }

    public void setEstadoTipoSin(String estadoTipoSin) {
        this.estadoTipoSin = estadoTipoSin;
    }

    //TO STRING
    @Override
    public String toString() {
        return "TipoSiniestro{" +
                "codTipoSiniestro=" + codTipoSiniestro +
                ", descripTipoSiniestro='" + descripTipoSiniestro + '\'' +
                '}';
    }
}
