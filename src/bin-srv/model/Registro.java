package clases;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registro implements Serializable {

    private static final long serialVersionUID = 123L;

    //VALORES
    private int codRegistro;
    private int perito;
    private Date fechaApertura;
    private String referenciaCom;
    private Siniestro siniestro;
    private String descripcionReg;
    private Date fechaEntregaPerito;
    private String estadoEntrega;
    private String estadoRegistro;
    private Factura facturaRegistro;
    private Date fechaCierre;
    private String observaciones = "";
    private String contactoReg = "";
    private int usuario = 0;
    public int numeroReaperturas = 0;
    private String tipoRegistro = "REGISTRO";
    private int tipoInt = 0;

    public static SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");


    public Registro(){
        this.codRegistro = 0;
        this.perito = 0;
        this.fechaApertura = new Date();
        this.referenciaCom = "";
        this.siniestro = new Siniestro();
        this.facturaRegistro = new Factura();
        this.descripcionReg = "";
        this.estadoEntrega = "PENDIENTE";
        this.estadoRegistro = "ABIERTO";

    }

    public Registro(int codRegistro, int perito, Date fechaApertura, String referenciaCom, Siniestro siniestro,
                    String descripcionReg, Date fechaEntregaPerito, String estadoEntrega, String estadoRegistro,
                    Factura factura, Date fechaCierre, String observaciones, int usuario, int numReapert, String tipoReg) {
        this.codRegistro = codRegistro;
        this.perito = perito;
        this.fechaApertura = fechaApertura;
        this.referenciaCom = referenciaCom;
        this.siniestro = siniestro;
        this.descripcionReg = descripcionReg;
        this.fechaEntregaPerito = fechaEntregaPerito;
        this.estadoEntrega = estadoEntrega;
        this.estadoRegistro = estadoRegistro;
        this.facturaRegistro = factura;
        this.fechaCierre = fechaCierre;
        this.observaciones = observaciones;
        this.usuario = usuario;
        this.numeroReaperturas = numReapert;
        this.tipoRegistro = tipoReg;
    }

    //GUETTERS Y SETTERS

    //CODIGO REGISTRO
    public void setCodRegistro(int codRegistro) {
        this.codRegistro = codRegistro;
    }

    public int getCodRegistro() {
        return codRegistro;
    }

    //PERITO
    public int getPerito() {
        return perito;
    }

    public void setPerito(int perito) {
        this.perito = perito;
    }

    //FECHA APERTURA
    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    //REFERENCIA COMPANIA
    public String getReferenciaCom() {
        return referenciaCom;
    }

    public void setReferenciaCom(String referenciaCom) {
        this.referenciaCom = referenciaCom;
    }

    //SINIESTRO ASOCIADO (CONTIENE ASEGURADO, COMPANIA Y DATOS POLIZA)
    public Siniestro getSiniestro() {
        return siniestro;
    }

    public void setSiniestro(Siniestro siniestro) {
        this.siniestro = siniestro;
    }

    //DESCRIPCION DEL SINIESTRO (NOTAS)
    public String getDescripcionReg() {
        return descripcionReg;
    }

    public void setDescripcionReg(String descripcionReg) {
        this.descripcionReg = descripcionReg;
    }

    //FECHA DE ENTREGA DEL PERITO
    public Date getFechaEntregaPerito() {
        return fechaEntregaPerito;
    }

    public void setFechaEntregaPerito(Date fechaEntregaPerito) {
        this.fechaEntregaPerito = fechaEntregaPerito;
    }

    public Date getTextFechaEPer(String txtDate){
        Date fecha = null;
        try {

            fecha = formatDate.parse(txtDate);

        } catch (ParseException ex) {

            ex.printStackTrace();

        }
        return fecha;
    }

    //ESTADO ENTREGA REGISTRO
    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    //FACTURA ASOCIADA AL REGISTRO
    public Factura getFacturaRegistro() {
        return facturaRegistro;
    }

    public void setFacturaRegistro(Factura facturaRegistro) {
        this.facturaRegistro = facturaRegistro;
    }

    //FECHA DE CIERRE DEL REGISTRO
    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    //OBSERVACIONES DE FACTURACION
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    //USUARIO QUE LLEVA EL REGISTRO
    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    //ESTADO DEL REGISTRO (ABIERTO O CERRADO)
    public String getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(String estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public int getNumeroReaperturas() {
        return numeroReaperturas;
    }

    public void setNumeroReaperturas(int numeroReaperturas) {
        this.numeroReaperturas = numeroReaperturas;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public int getTipoInt() {
        return tipoInt;
    }

    public void setTipoInt(int tipoInt) {
        this.tipoInt = tipoInt;
    }

    public String getContactoReg() {
        return contactoReg;
    }

    public void setContactoReg(String contactoReg) {
        this.contactoReg = contactoReg;
    }

    //TO STRING
    @Override
    public String toString() {
        return "Registro{" +
                "codRegistro=" + codRegistro +
                ", perito=" + perito +
                ", fechaApertura=" + fechaApertura +
                ", referenciaCom='" + referenciaCom + '\'' +
                ", siniestro=" + siniestro +
                ", descripcionReg='" + descripcionReg + '\'' +
                ", fechaEntregaPerito=" + fechaEntregaPerito +
                ", estadoEntrega='" + estadoEntrega + '\'' +
                ", estadoRegistro='" + estadoRegistro + '\'' +
                ", facturaRegistro=" + facturaRegistro +
                ", fechaCierre=" + fechaCierre +
                ", observaciones='" + observaciones + '\'' +
                ", usuario=" + usuario +
                ", numeroReaperturas=" + numeroReaperturas +
                ", tipoRegistro='" + tipoRegistro + '\'' +
                ", tipoInt=" + tipoInt +
                '}';
    }
}
