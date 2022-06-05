package clases;

import java.io.Serializable;

public class TelefonoAsegurado implements Serializable {

    //VALORES
    private int codTelefono;
    private int telefono;
    private int asegurado;

    //CONSTRUCTOR
    public TelefonoAsegurado(int codTelefono, int telefono, int asegurado) {
        this.codTelefono = codTelefono;
        this.telefono = telefono;
        this.asegurado = asegurado;
    }

    //GUETTERS Y SETTERS
    //CODIGO TELEFONO
    public int getCodTelefono() {
        return codTelefono;
    }

    public void setCodTelefono(int codTelefono) {
        this.codTelefono = codTelefono;
    }

    //TELEFONO
    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    //CODIGO ASEGURADO
    public int getAsegurado() {
        return this.asegurado;
    }

    public void setAsegurado(int asegurado) {
        this.asegurado = asegurado;
    }

    //TO STRING
    @Override
    public String toString() {
        return "TelefonoAsegurado{" +
                "codTelefono=" + codTelefono +
                ", telefono=" + telefono +
                ", asegurado=" + asegurado +
                '}';
    }
}
