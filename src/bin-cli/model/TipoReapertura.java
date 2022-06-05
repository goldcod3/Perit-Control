package clases;

import java.io.Serializable;

public class TipoReapertura implements Serializable {

    //VALORES
    private int codTipoReapertura = 0;
    private String tipoReapertura = "";
    private String estadoTipoReapertura = "ACTIVO";

    //CONSTRUCTOR
    public TipoReapertura(int codTipoReapertura, String tipoReapertura, String estadoReapertura) {
        this.codTipoReapertura = codTipoReapertura;
        this.tipoReapertura = tipoReapertura;
        this.estadoTipoReapertura = estadoReapertura;
    }

    public TipoReapertura() {

    }

    //GUEETTERS Y SETTERS

    //CODIGO TIPO REAPERTURA
    public int getCodTipoReapertura() {
        return codTipoReapertura;
    }

    public void setCodTipoReapertura(int codTipoReapertura) {
        this.codTipoReapertura = codTipoReapertura;
    }

    //DESCRIPCION TIPO REAPERTURA
    public String getTipoReapertura() {
        return tipoReapertura;
    }

    public void setTipoReapertura(String tipoReapertura) {
        this.tipoReapertura = tipoReapertura;
    }

    // ESTADO
    public String getEstadoTipoReapertura() {
        return estadoTipoReapertura;
    }

    public void setEstadoTipoReapertura(String estadoTipoReapertura) {
        this.estadoTipoReapertura = estadoTipoReapertura;
    }

    //TO STRING
    @Override
    public String toString() {
        return "TipoReapertura{" +
                "codTipoReapertura=" + codTipoReapertura +
                ", tipoReapertura='" + tipoReapertura + '\'' +
                '}';
    }
}
