package clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Asegurado implements Serializable {

    //VALORES
    private int codAsegurado;
    private String nombreAseg;
    private String direccionRiesgo;
    private String direccionContacto;
    private String contactoAseg;
    private ArrayList<TelefonoAsegurado> telefonosAseg;

    //CONSTRUCTOR DEFAULT
    public Asegurado(){
        this.codAsegurado = 0;
        this.nombreAseg = "";
        this.direccionRiesgo = "";
        this.direccionContacto = "";
        this.contactoAseg = "";
        this.telefonosAseg = new ArrayList<TelefonoAsegurado>();
    }

    //CONSTRUCTOR ASEGURADO
    public Asegurado(int codAsegurado, String nombreAseg, ArrayList<TelefonoAsegurado> telefonos, String direccionRiesgo,
                     String direccionContacto, String contactoAseg) {
        this.codAsegurado = codAsegurado;
        this.nombreAseg = nombreAseg;
        this.direccionRiesgo = direccionRiesgo;
        this.direccionContacto = direccionContacto;
        this.contactoAseg = contactoAseg;
        this.telefonosAseg = telefonos;
    }

    public Asegurado(int codAsegurado, String nombreAseg, String direccionRiesgo, String direccionContacto, String contactoAseg) {
        this.codAsegurado = codAsegurado;
        this.nombreAseg = nombreAseg;
        this.direccionRiesgo = direccionRiesgo;
        this.direccionContacto = direccionContacto;
        this.contactoAseg = contactoAseg;
        this.telefonosAseg = new ArrayList<TelefonoAsegurado>();
    }


    //GUETTERS Y SETTERS


    //CODIGO ASEGURADO
    public int getCodAsegurado() {
        return codAsegurado;
    }

    public void setCodAsegurado(int codAsegurado) {
        this.codAsegurado = codAsegurado;
    }

    //NOMBRE ASEGURADO
    public String getNombreAseg() {
        return nombreAseg;
    }

    public void setNombreAseg(String nombreAseg) {
        this.nombreAseg = nombreAseg;
    }

    //TELEFONOS ASEGURADO
    public ArrayList<TelefonoAsegurado> getTelefonosAseg() {
        return telefonosAseg;
    }

    public void setTelefonosAseg(ArrayList<TelefonoAsegurado> telefonosAseg) {
        this.telefonosAseg = telefonosAseg;
    }

    //DIRECCION DE RIESGO
    public String getDireccionRiesgo() {
        return direccionRiesgo;
    }

    public void setDireccionRiesgo(String direccionRiesgo) {
        this.direccionRiesgo = direccionRiesgo;
    }

    //DIRECCION DE CONTACTO
    public String getDireccionContacto() {
        return direccionContacto;
    }

    public void setDireccionContacto(String direccionContacto) {
        this.direccionContacto = direccionContacto;
    }

    //CONTACTO ASEGURADO
    public String getContactoAseg() {
        return contactoAseg;
    }

    public void setContactoAseg(String contactoAseg) {
        this.contactoAseg = contactoAseg;
    }


    @Override
    public String toString() {
        return "Asegurado{" +
                "codAsegurado=" + codAsegurado +
                ", nombreAseg='" + nombreAseg + '\'' +
                ", direccionRiesgo='" + direccionRiesgo + '\'' +
                ", direccionContacto='" + direccionContacto + '\'' +
                ", contactoAseg='" + contactoAseg + '\'' +
                ", telefonosAseg=" + telefonosAseg +
                '}';
    }
}
