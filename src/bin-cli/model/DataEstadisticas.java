package clases;

public class DataEstadisticas implements Comparable<DataEstadisticas>{
    private String nombrePerito = "";
    private String estadoPerito = "ACTIVO";
    private int registrosAbiertos = 0;
    private int registrosEntregados = 0;
    private double media = 0;
    private String Rendimiento;

    public DataEstadisticas(){ }

    public String getNombrePerito() {
        return nombrePerito;
    }

    public void setNombrePerito(String nombrePerito) {
        this.nombrePerito = nombrePerito;
    }

    public String getEstadoPerito() {
        return estadoPerito;
    }

    public void setEstadoPerito(String estadoPerito) {
        this.estadoPerito = estadoPerito;
    }

    public int getRegistrosAbiertos() {
        return registrosAbiertos;
    }

    public void setRegistrosAbiertos(int registrosAbiertos) {
        this.registrosAbiertos = registrosAbiertos;
    }

    public int getRegistrosEntregados() {
        return registrosEntregados;
    }

    public void setRegistrosEntregados(int registrosEntregados) {
        this.registrosEntregados = registrosEntregados;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public String getRendimiento() {
        return Rendimiento;
    }

    public void setRendimiento(String rendimiento) {
        Rendimiento = rendimiento;
    }


    @Override
    public int compareTo(DataEstadisticas o) {
        if(o.getMedia()>getMedia()){
            return -1;
        }else if(o.getMedia()==getMedia()){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public String toString() {
        return "DataEstadisticas{" +
                "nombrePerito='" + nombrePerito + '\'' +
                ", estadoPerito='" + estadoPerito + '\'' +
                ", registrosAbiertos=" + registrosAbiertos +
                ", registrosEntregados=" + registrosEntregados +
                ", media=" + media +
                ", Rendimiento='" + Rendimiento + '\'' +
                '}';
    }
}
