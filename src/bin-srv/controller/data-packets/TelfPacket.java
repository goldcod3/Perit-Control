package conexion.paquetes;

import clases.Registro;
import clases.TelefonoAsegurado;
import clases.Usuario;

public class TelfPacket extends DataPacket{

    private Registro registro = new Registro();
    private int codVentana = 0;
    private TelefonoAsegurado telefonoAsegurado;

    public TelfPacket(int codPacket, Usuario user, String ipClient) {
        super(codPacket, user, ipClient);
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public int getCodVentana() {
        return codVentana;
    }

    public void setCodVentana(int codVentana) {
        this.codVentana = codVentana;
    }

    public TelefonoAsegurado getTelefonoAsegurado() {
        return telefonoAsegurado;
    }

    public void setTelefonoAsegurado(TelefonoAsegurado telefonoAsegurado) {
        this.telefonoAsegurado = telefonoAsegurado;
    }
}
