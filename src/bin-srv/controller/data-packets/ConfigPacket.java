package conexion.paquetes;

import clases.*;

public class ConfigPacket extends DataPacket{

    private Compania compania = new Compania();
    private RamoCompania ramo = new RamoCompania();
    private TipoSiniestro tipoSiniestro = new TipoSiniestro();
    private TipoIntervencion tipoIntervencion = new TipoIntervencion();
    private Usuario usuario = new Usuario();
    private TipoReapertura tipoReapertura = new TipoReapertura();
    boolean save = false;

    public ConfigPacket(String ip, Usuario usr) {
        super(ip, usr);
    }

    public ConfigPacket(int codPacket, Usuario user, String ipClient) {
        super(codPacket, user, ipClient);
    }

    public Compania getCompania() {
        return compania;
    }

    public void setCompania(Compania compania) {
        this.compania = compania;
    }

    public RamoCompania getRamo() {
        return ramo;
    }

    public void setRamo(RamoCompania ramo) {
        this.ramo = ramo;
    }

    public TipoSiniestro getTipoSiniestro() {
        return tipoSiniestro;
    }

    public void setTipoSiniestro(TipoSiniestro tipoSiniestro) {
        this.tipoSiniestro = tipoSiniestro;
    }

    public TipoIntervencion getTipoIntervencion() {
        return tipoIntervencion;
    }

    public void setTipoIntervencion(TipoIntervencion tipoIntervencion) {
        this.tipoIntervencion = tipoIntervencion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoReapertura getTipoReapertura() {
        return tipoReapertura;
    }

    public void setTipoReapertura(TipoReapertura tipoReapertura) {
        this.tipoReapertura = tipoReapertura;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }
}
