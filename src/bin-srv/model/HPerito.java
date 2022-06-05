package clases;

import java.io.Serializable;

public class HPerito implements Serializable {

    //VALORES
    private int codHPerito;
    private double honorariosPer;
    private double locomocionPer;
    private double ajustesPer;
    private double sumaPer;
    private double ivaPer;
    private double irpfPer;
    private double totalPer;

    //CONSTRUCTOR DEFAULT
    public HPerito(){
        this.codHPerito = 0;
        this.honorariosPer = 0;
        this.locomocionPer = 0;
        this.ajustesPer = 0;
        this.sumaPer = 0;
        this.ivaPer = 0;
        this.irpfPer = 0;
        this.totalPer = 0;
    }

    //CONSTRUCTOR APERTURA
    public HPerito(int codPerito, double honorariosPer, double locomocionPer, double ajustesPer, double sumaPer, double ivaPer, double irpfPer, double totalPer) {
        this.codHPerito = codPerito;
        this.honorariosPer = honorariosPer;
        this.locomocionPer = locomocionPer;
        this.ajustesPer = ajustesPer;
        this.sumaPer = sumaPer;
        this.ivaPer = ivaPer;
        this.irpfPer = irpfPer;
        this.totalPer = totalPer;
    }

    //GUETTERS Y SETTERS

    //CODIGO HONORARIOS DEL PERITO
    public int getCodHPerito() {
        return codHPerito;
    }

    public void setCodHPerito(int codHPerito) {
        this.codHPerito = codHPerito;
    }

    //HONORARIOS DEL PERITO
    public double getHonorariosPer() {
        return honorariosPer;
    }

    public void setHonorariosPer(double honorariosPer) {
        this.honorariosPer = honorariosPer;
    }

    //LOCOMOCION
    public double getLocomocionPer() {
        return locomocionPer;
    }

    public void setLocomocionPer(double locomocionPer) {
        this.locomocionPer = locomocionPer;
    }

    //AJUSTES
    public double getAjustesPer() {
        return ajustesPer;
    }

    public void setAjustesPer(double ajustesPer) {
        this.ajustesPer = ajustesPer;
    }

    //SUMA HONORARIOS
    public double getSumaPer() {
        return sumaPer;
    }

    public void setSumaPer(double sumaPer) {
        this.sumaPer = sumaPer;
    }

    //IVA
    public double getIvaPer() {
        return ivaPer;
    }

    public void setIvaPer(double ivaPer) {
        this.ivaPer = ivaPer;
    }

    //IRPF
    public double getIrpfPer() {
        return irpfPer;
    }

    public void setIrpfPer(double irpfPer) {
        this.irpfPer = irpfPer;
    }

    //TOTAL HONORARIOS DEL PERITO
    public double getTotalPer() {
        return totalPer;
    }

    public void setTotalPer(double totalPer) {
        this.totalPer = totalPer;
    }

    //TO STRING

    @Override
    public String toString() {
        return "HPerito{" +
                "codHPerito=" + codHPerito +
                ", honorariosPer=" + honorariosPer +
                ", locomocionPer=" + locomocionPer +
                ", ajustesPer=" + ajustesPer +
                ", sumaPer=" + sumaPer +
                ", ivaPer=" + ivaPer +
                ", irpfPer=" + irpfPer +
                ", totalPer=" + totalPer +
                '}';
    }
}
