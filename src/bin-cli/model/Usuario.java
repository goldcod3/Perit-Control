package clases;

import java.io.Serializable;

public class Usuario implements Serializable {

    //VALORES
    private int codUsuario;
    private String nombreUsr;
    private String passUsr;
    private String estadoUsr;
    private String tipoUsr;

    //CONSTRUCTOR DEFAULT
    public Usuario(){
        this.codUsuario = 0;
        this.nombreUsr = "";
        this.passUsr = "-";
        this.estadoUsr = "ACTIVO";
        this.tipoUsr = "COMUN";
    }

    //CONSTRUCTOR APERTURA
    public Usuario(int codUsuario, String nombreUsuario, String passUsuario, String estadoUsuario, String tipoUsr) {
        this.codUsuario = codUsuario;
        this.nombreUsr = nombreUsuario;
        this.passUsr = passUsuario;
        this.estadoUsr = estadoUsuario;
        this.tipoUsr = tipoUsr;
    }

    //CONSTRUCTOR USUARIO SIN PASS
    public Usuario(int codUsuario, String nombreUsuario, String estadoUsuario, String tipoUsr) {
        this.codUsuario = codUsuario;
        this.nombreUsr = nombreUsuario;
        this.passUsr = "-";
        this.estadoUsr = estadoUsuario;
        this.tipoUsr = tipoUsr;
    }

    //CONSTRUCTOR QUE INICIA ESTADO A TRUE
    public Usuario(int codUsuario, String nombreUsuario, String passUsuario) {
        this.codUsuario = codUsuario;
        this.nombreUsr = nombreUsuario;
        this.passUsr = passUsuario;
        this.estadoUsr = "ACTIVO";
        this.tipoUsr = "COMUN";
    }

    //GUETTERS Y SETTERS

    //CODIGO USUARIO
    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    //NOMBRE USUARIO
    public String getNombreUsr() {
        return nombreUsr;
    }

    public void setNombreUsr(String nombreUsr) {
        this.nombreUsr = nombreUsr;
    }

    //PASS USUARIO
    public String getPassUsr() {
        return passUsr;
    }

    public void setPassUsr(String passUsr) {
        this.passUsr = passUsr;
    }

    //ESTADO USUARIO
    public String getEstadoUsr() {
        return estadoUsr;
    }

    public void setEstadoUsr(String estadoUsr) {
        this.estadoUsr = estadoUsr;
    }

    public String getTipoUsr() {
        return tipoUsr;
    }

    public void setTipoUsr(String tipoUsr) {
        this.tipoUsr = tipoUsr;
    }

    //TO STRING

    @Override
    public String toString() {
        return "Usuario{" +
                "codUsuario=" + codUsuario +
                ", nombreUsr='" + nombreUsr + '\'' +
                ", passUsr='" + passUsr + '\'' +
                ", estadoUsr='" + estadoUsr + '\'' +
                ", tipoUsr='" + tipoUsr + '\'' +
                '}';
    }
}
