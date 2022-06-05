package clases;

import java.util.ArrayList;

public class DataPDF {

    private String nombrePerito = "";
    private ArrayList<Registro> registros = new ArrayList<>();
    private String honorarios, locomocion, ajustes, suma, iva, irpf, total;
    public int mes, ano;

    public DataPDF() {
        this.honorarios = "0.00";
        this.locomocion = "0.00";
        this.ajustes = "0.00";
        this.suma = "0.00";
        this.iva = "0.00";
        this.irpf = "0.00";
        this.total = "0.00";
    }

    public String getNombrePerito() {
        return nombrePerito;
    }

    public void setNombrePerito(String nombrePerito) {
        this.nombrePerito = nombrePerito;
    }

    public ArrayList<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<Registro> registros) {
        this.registros = registros;
    }

    public String getHonorarios() {
        return honorarios;
    }

    public void setHonorarios(String honorarios) {
        this.honorarios = honorarios;
    }

    public String getLocomocion() {
        return locomocion;
    }

    public void setLocomocion(String locomocion) {
        this.locomocion = locomocion;
    }

    public String getAjustes() {
        return ajustes;
    }

    public void setAjustes(String ajustes) {
        this.ajustes = ajustes;
    }

    public String getSuma() {
        return suma;
    }

    public void setSuma(String suma) {
        this.suma = suma;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getIrpf() {
        return irpf;
    }

    public void setIrpf(String irpf) {
        this.irpf = irpf;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
