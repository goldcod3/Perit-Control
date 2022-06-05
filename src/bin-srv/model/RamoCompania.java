package clases;

import java.io.Serializable;

public class RamoCompania implements Serializable {

    //VALORES
    private int codRamo;
    private String descripcionRamo;
    private int compania;
    private String estadoRam = "ACTIVO";

    //CONSTRUCTOR DEFAULT
    public RamoCompania(){
        this.codRamo = 0;
        this.descripcionRamo = "Default";
        this.compania = 0;
    }

    //CONSTRUCTOR APERTURA
    public RamoCompania(int codRamo, String descripcionRamo, int compania) {
        this.codRamo = codRamo;
        this.descripcionRamo = descripcionRamo;
        this.compania = compania;
    }

    //CONSTRUCTOR APERTURA
    public RamoCompania(int codRamo, String descripcionRamo, int compania, String estadoRam) {
        this.codRamo = codRamo;
        this.descripcionRamo = descripcionRamo;
        this.compania = compania;
        this.estadoRam = estadoRam;
    }
    //GUETTERS Y SETTERS

    //CODIGO RAMO
    public int getCodRamo() {
        return codRamo;
    }

    public void setCodRamo(int codRamo) {
        this.codRamo = codRamo;
    }

    //DESCRIPCION RAMO
    public String getDescripcionRamo() {
        return descripcionRamo;
    }

    public void setDescripcionRamo(String descripcionRamo) {
        this.descripcionRamo = descripcionRamo;
    }

    //COMPANIA
    public int getCompania() {
        return compania;
    }

    public void setCompania(int compania) {
        this.compania = compania;
    }

    public String getEstadoRam() {
        return estadoRam;
    }

    public void setEstadoRam(String estadoRam) {
        this.estadoRam = estadoRam;
    }

    //TO STRING
    @Override
    public String toString() {
        return "RamoCompania{" +
                "codRamo=" + codRamo +
                ", descripcionRamo='" + descripcionRamo + '\'' +
                ", compania=" + compania +
                '}';
    }
}
