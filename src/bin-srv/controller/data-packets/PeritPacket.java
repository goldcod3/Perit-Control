package conexion.paquetes;

import clases.Perito;
import clases.Usuario;

import java.io.Serializable;

public class PeritPacket extends DataPacket{

    private Perito perito = new Perito();
    private int codVentana;
    private boolean isSave = false;

    public PeritPacket(String ip, Usuario usr) {
        super(ip, usr);
    }

    public PeritPacket(int codPacket, Usuario user, String ipClient) {
        super(codPacket, user, ipClient);
    }

    public Perito getPerito() {
        return perito;
    }

    public void setPerito(Perito perito) {
        this.perito = perito;
    }

    public int getCodVentana() {
        return codVentana;
    }

    public void setCodVentana(int codVentana) {
        this.codVentana = codVentana;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }
}
