package clases;

import java.io.Serializable;

public class HGabinete implements Serializable {

    //VALORES
    private int codHGabinete;
    private double honorarioGab;
    private double locomocionGab;
    private double variosGab;
    private double sumaGab;
    private double ivaGab;
    private double totalGab;

    //CONTRUCTOR DEFAULT
    public HGabinete(){
        this.codHGabinete = 0;
        this.honorarioGab = 0;
        this.locomocionGab = 0;
        this.variosGab = 0;
        this.sumaGab = 0;
        this.ivaGab = 0;
        this.totalGab = 0;
    }

    //CONSTRUCTOR
    public HGabinete(int codHGabinete, double honorarioGab, double locomocionGab, double variosGab, double sumaGab, double ivaGab, double totalGab) {
        this.codHGabinete = codHGabinete;
        this.honorarioGab = honorarioGab;
        this.locomocionGab = locomocionGab;
        this.variosGab = variosGab;
        this.sumaGab = sumaGab;
        this.ivaGab = ivaGab;
        this.totalGab = totalGab;
    }

    //GUETTERS Y SETTERS

    //CODIGO HONORARIOS DEL GABINETE
    public int getCodHGabinete() {
        return codHGabinete;
    }

    public void setCodHGabinete(int codHGabinete) {
        this.codHGabinete = codHGabinete;
    }

    //HONORARIOS DEL GABINETE
    public double getHonorarioGab() {
        return honorarioGab;
    }

    public void setHonorarioGab(double honorarioGab) {
        this.honorarioGab = honorarioGab;
    }

    //LOCOMOCION DEL GABINETE
    public double getLocomocionGab() {
        return locomocionGab;
    }

    public void setLocomocionGab(double locomocionGab) {
        this.locomocionGab = locomocionGab;
    }

    //GASTOS VARIOS
    public double getVariosGab() {
        return variosGab;
    }

    public void setVariosGab(double variosGab) {
        this.variosGab = variosGab;
    }

    //SUMA DE HONORARIOS
    public double getSumaGab() {
        return sumaGab;
    }

    public void setSumaGab(double sumaGab) {
        this.sumaGab = sumaGab;
    }

    //IVA
    public double getIvaGab() {
        return ivaGab;
    }

    public void setIvaGab(double ivaGab) {
        this.ivaGab = ivaGab;
    }

    //TOTAL HONORARIOS
    public double getTotalGab() {
        return totalGab;
    }

    public void setTotalGab(double totalGab) {
        this.totalGab = totalGab;
    }

    //TO STRING

    @Override
    public String toString() {
        return "HGabinete{" +
                "codHGabinete=" + codHGabinete +
                ", honorarioGab=" + honorarioGab +
                ", locomocionGab=" + locomocionGab +
                ", variosGab=" + variosGab +
                ", sumaGab=" + sumaGab +
                ", ivaGab=" + ivaGab +
                ", totalGab=" + totalGab +
                '}';
    }
}
