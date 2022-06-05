package conexion.paquetes;

import clases.Reapertura;
import clases.Registro;
import clases.Usuario;

public class SafePacket extends DataPacket{

    /*
    CODIGOS DE PAQUETE:
    1 -- ANADIR NUEVO REGISTRO / REAPERTURA
    2 -- GUARDAR CAMBIOS EN EL REGISTRO / REAPERTURA
    3 -- VALIDAR ENTREGA PERITO
    4 -- CIERRE DE REGISTRO
     */

    private Registro registro;
    private Reapertura reapertura = new Reapertura(0);
    private int codVentana;
    private boolean isSave = false;
    private boolean safeAndClose = false;

    public SafePacket(String ip, Usuario usr) {
        super(ip, usr);
    }

    public SafePacket(int codPacket, Usuario user, String ipClient) {
        super(codPacket, user, ipClient);
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public Reapertura getReapertura() {
        return reapertura;
    }

    public void setReapertura(Reapertura reapertura) {
        this.reapertura = reapertura;
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

    public boolean isSafeAndClose() {
        return safeAndClose;
    }

    public void setSafeAndClose(boolean safeAndClose) {
        this.safeAndClose = safeAndClose;
    }

    @Override
    public String toString() {
        return "SafePacket{" +
                "registro=" + registro +
                ", codVentana=" + codVentana +
                ", isSave=" + isSave +
                '}';
    }
}
