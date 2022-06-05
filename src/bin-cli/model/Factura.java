package clases;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Factura implements Serializable {

    //VALORES
    private int codFactura;
    private String numFactura;
    private HGabinete hGabinete;
    private HPerito hPerito;
    private int mesFactPerito;
    private int anoFactPerito;
    private String estadoFactura;

    //CONSTRUCTOR DEFAULT
    public Factura(){
        this.codFactura = 0;
        this.numFactura = "";
        this.hGabinete = new HGabinete();
        this.hPerito = new HPerito();
        this.mesFactPerito = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        this.anoFactPerito = Integer.parseInt(new SimpleDateFormat("yy").format(new Date()));
        this.estadoFactura = "IMPAGO";
    }

    //COSTRUCTOR APERTURA
    public Factura(int codFactura, String numFactura, HGabinete hGabinete, HPerito hPerito,
                   String estado, int mesFactPerito, int anoFactPerito) {
        this.codFactura = codFactura;
        this.numFactura = numFactura;
        this.hGabinete = hGabinete;
        this.hPerito = hPerito;
        this.mesFactPerito = mesFactPerito;
        this.anoFactPerito = anoFactPerito;
        this.estadoFactura = estado;
    }

    //COSTRUCTOR APERTURA SIN HGabinete y HPerito
    public Factura(int codFactura, String numFactura, String estado, int mesFactPerito, int anoFactPerito) {
        this.codFactura = codFactura;
        this.numFactura = numFactura;
        this.mesFactPerito = mesFactPerito;
        this.anoFactPerito = anoFactPerito;
        this.estadoFactura = estado;
    }

    //GUETTERS Y SETTERS

    //CODIGO FACTURA
    public int getCodFactura() {
        return codFactura;
    }

    public void setCodFactura(int codFactura) {
        this.codFactura = codFactura;
    }

    //NUM FACTURA
    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    //HONORARIOS DEL GABINETE
    public HGabinete gethGabinete() {
        return hGabinete;
    }

    public void sethGabinete(HGabinete hGabinete) {
        this.hGabinete = hGabinete;
    }

    //HONORARIOS DEL PERITO
    public HPerito gethPerito() {
        return hPerito;
    }

    public void sethPerito(HPerito hPerito) {
        this.hPerito = hPerito;
    }

    //MES DE FACTURACION DEL PERITO
    public int getMesFactPerito() {
        return mesFactPerito;
    }

    public void setMesFactPerito(int mesFactPerito) {
        this.mesFactPerito = mesFactPerito;
    }

    //ANO DE FACTURACION DEL PERITO
    public int getAnoFactPerito() {
        return anoFactPerito;
    }

    public void setAnoFactPerito(int anoFactPerito) {
        this.anoFactPerito = anoFactPerito;
    }

    //ESTADO DE FACTURA (PAGA = FALSE, IMPAGO = TRUE)
    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    //TO STRING

    @Override
    public String toString() {
        return "Factura{" +
                "codFactura=" + codFactura +
                ", numFactura=" + numFactura +
                ", hGabinete=" + hGabinete +
                ", hPerito=" + hPerito +
                ", mesFactPerito=" + mesFactPerito +
                ", anoFactPerito=" + anoFactPerito +
                ", estadoFactura=" + estadoFactura +
                '}';
    }
}
