package clases;

import java.io.Serializable;

public class Perito implements Serializable {

    //VALORES
    private int codPerito;
    private String nombrePerito;
    private String nifPerito;
    private String dirPerito;
    private int telfPerito;
    private String emailPerito;
    private String estadoPerito;


    //CONSTRUCTOR CREAR PERITO
    public Perito(){
        this.codPerito = 0;
        this.nombrePerito = "Default";
        this.nifPerito = "Default";
        this.dirPerito = "Default";
        this.telfPerito = 0;
        this.emailPerito = "Default";
        this.estadoPerito = "ACTIVO";
    }

    //CONSTRUCTOR ABRIR PERITO
    public Perito(int codPerito, String nombrePerito, String nifPerito, String dirPerito, int telfPerito, String emailPerito, String estadoPerito) {
        this.codPerito = codPerito;
        this.nombrePerito = nombrePerito;
        this.nifPerito = nifPerito;
        this.dirPerito = dirPerito;
        this.telfPerito = telfPerito;
        this.emailPerito = emailPerito;
        this.estadoPerito = estadoPerito;
    }

    //GUETTERS Y SETTERS

    //CODIGO PERITO
    public int getCodPerito() {
        return codPerito;
    }

    public void setCodPerito(int codPerito) {
        this.codPerito = codPerito;
    }

    //NOMBRE PERITO
    public String getNombrePerito() {
        return nombrePerito;
    }

    public void setNombrePerito(String nombrePerito) {
        this.nombrePerito = nombrePerito;
    }

    //NIF PERITO
    public String getNifPerito() {
        return nifPerito;
    }

    public void setNifPerito(String nifPerito) {
        this.nifPerito = nifPerito;
    }

    //DIRECCION PERITO
    public String getDirPerito() {
        return dirPerito;
    }

    public void setDirPerito(String dirPerito) {
        this.dirPerito = dirPerito;
    }

    //TELEFONO PERITO
    public int getTelfPerito() {
        return telfPerito;
    }

    public void setTelfPerito(int telfPerito) {
        this.telfPerito = telfPerito;
    }

    //EMAIL PERITO
    public String getEmailPerito() {
        return emailPerito;
    }

    public void setEmailPerito(String emailPerito) {
        this.emailPerito = emailPerito;
    }

    //ESTADO PERITO
    public String getEstadoPerito() {
        return this.estadoPerito;
    }

    public void setEstadoPerito(String estadoPerito) {
        this.estadoPerito = estadoPerito;
    }

    //TO STRING

    @Override
    public String toString() {
        return "Perito{" +
                "codPerito=" + codPerito +
                ", nombrePerito='" + nombrePerito + '\'' +
                ", nifPerito='" + nifPerito + '\'' +
                ", dirPerito='" + dirPerito + '\'' +
                ", telfPerito=" + telfPerito +
                ", emailPerito='" + emailPerito + '\'' +
                ", estadoPerito=" + estadoPerito +
                '}';
    }
}
