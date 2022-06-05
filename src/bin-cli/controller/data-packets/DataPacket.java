package conexion.paquetes;

import clases.*;
import conexion.DataTables;
import interfaces.Config;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/*
 * Clase DataPacket que se envia al servidor esperando respuesta de validacion de usuario.
 * - Si la respuesta del servidor es positiva, se cargaran las DataTables del cliente.
 *
 */
public class DataPacket implements Serializable {

    @Serial
    private static final long serialVersionUID = 290321L;

    //VALORES
    private int codPacket;
    private Usuario user;
    private String ipClient;
    private Date fechaDesde;
    private Date fechaHasta;
    private boolean isValidUser = false;


    public DataPacket(String ip, Usuario usr){
        this.codPacket = 1;
        this.user = usr;
        this.ipClient = ip;

    }

    //CONSTRUCTOR
    public DataPacket(int codPacket, Usuario user, String ipClient) {
        this.codPacket = codPacket;
        this.user = user;
        this.ipClient = ipClient;
    }

    //GUETTERS Y SETTERS
    public int getCodPacket() {
        return codPacket;
    }

    public void setCodPacket(int codPacket) {
        this.codPacket = codPacket;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getIpClient() {
        return ipClient;
    }

    public void setIpClient(String ipClient) {
        this.ipClient = ipClient;
    }

    public boolean isValidUser() {
        return isValidUser;
    }

    public void setValidUser(boolean validUser) {
        isValidUser = validUser;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
}

