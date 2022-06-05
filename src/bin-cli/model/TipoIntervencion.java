package clases;

import java.io.Serializable;

public class TipoIntervencion implements Serializable {

    private int codTipoInt = 0;
    private String tipoIntervencion = "";
    private String estadoIntervencion = "ACTIVO";

    public TipoIntervencion(int codTipoInt, String tipoIntervencion, String estadoIntervencion) {
        this.codTipoInt = codTipoInt;
        this.tipoIntervencion = tipoIntervencion;
        this.estadoIntervencion = estadoIntervencion;
    }

    public TipoIntervencion() {

    }

    public int getCodTipoInt() {
        return codTipoInt;
    }

    public void setCodTipoInt(int codTipoInt) {
        this.codTipoInt = codTipoInt;
    }

    public String getTipoIntervencion() {
        return tipoIntervencion;
    }

    public void setTipoIntervencion(String tipoIntervencion) {
        this.tipoIntervencion = tipoIntervencion;
    }

    public String getEstadoIntervencion() {
        return estadoIntervencion;
    }

    public void setEstadoIntervencion(String estadoIntervencion) {
        this.estadoIntervencion = estadoIntervencion;
    }
}
