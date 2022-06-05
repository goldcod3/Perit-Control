package conexion.paquetes;

import clases.Usuario;

import java.io.Serial;
import java.io.Serializable;

public class VerifyPacket extends DataPacket {

    @Serial
    private static final long serialVersionUID = 290321L;

    //VALORES
    private int codPacket;
    private Usuario user;
    private String ipClient;
    private boolean isValidUser = false;


    public VerifyPacket(String ip, Usuario usr){
        super(ip,usr);
        this.codPacket = 1;
        this.user = usr;
        this.ipClient = ip;
    }

    //CONSTRUCTOR
    public VerifyPacket(int codPacket, Usuario user, String ipClient) {
        super(codPacket, user, ipClient);
        this.codPacket = codPacket;
        this.user = user;
        this.ipClient = ipClient;
    }

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
}
