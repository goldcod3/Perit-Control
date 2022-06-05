package clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Compania implements Serializable {

    //VALORES
    private int codCompania;
    private String nombreCompania;
    private String estadoCompania;
    private ArrayList<RamoCompania> ramoCompanias = new ArrayList<RamoCompania>();

    //CONSTRUCTOR DEFAULT
    public Compania(){
        this.codCompania = 0;
        this.nombreCompania = "Default";
        this.estadoCompania = "ACTIVO";
    }

    //CONSTRUCTOR APERTURA
    public Compania(int codCompania, String nombreCompania, String estadoCompania) {
        this.codCompania = codCompania;
        this.nombreCompania = nombreCompania;
        this.estadoCompania = estadoCompania;
        this.ramoCompanias = new ArrayList<RamoCompania>();
    }

    //CONSTRUCTOR APERTURA + RAMO
    public Compania(int codCompania, String nombreCompania, String estadoCompania, ArrayList<RamoCompania> ramos) {
        this.codCompania = codCompania;
        this.nombreCompania = nombreCompania;
        this.estadoCompania = estadoCompania;
        this.ramoCompanias = ramos;
    }

    //GUETTERS Y SETTERS

    //CODIGO COMPANIA
    public int getCodCompania() {
        return codCompania;
    }

    public void setCodCompania(int codCompania) {
        this.codCompania = codCompania;
    }

    //NOMBRE COMPANIA
    public String getNombreCompania() {
        return nombreCompania;
    }

    public void setNombreCompania(String nombreCompania) {
        this.nombreCompania = nombreCompania;
    }

    //ESTADO COMPANIA
    public String getEstadoCompania() {
        return estadoCompania;
    }

    public void setEstadoCompania(String estadoCompania) {
        this.estadoCompania = estadoCompania;
    }

    public ArrayList<RamoCompania> getRamoCompanias() {
        return ramoCompanias;
    }

    public void setRamoCompanias(ArrayList<RamoCompania> ramoCompanias) {
        this.ramoCompanias = ramoCompanias;
    }

    @Override
    public String toString() {
        return "Compania{" +
                "codCompania=" + codCompania +
                ", nombreCompania='" + nombreCompania + '\'' +
                ", estadoCompania='" + estadoCompania + '\'' +
                ", ramoCompanias=" + ramoCompanias +
                '}';
    }
}
