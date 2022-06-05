package interfaces.iregistro.eventos;

import clases.Reapertura;
import clases.Registro;
import conexion.DataTables;
import interfaces.Config;
import interfaces.iregistro.InterfazRegistro;

public class HiloNReaperturaBtn implements Runnable {
    public InterfazRegistro ventanaRegistro;
    public int codigoReg;
    public Reapertura reapertura = new Reapertura(0);
    public Registro reapAsociado = new Registro();
    @Override
    public void run() {

        int codigo = codigoReg;
        Registro temp = new Registro();
        Registro re = new Registro();
        for (Registro r : DataTables.getRegistros()){
            if (r.getCodRegistro() == codigo) {
                temp = reapertura.getReaperturaFromRegistro(r);
                re = r;
                break;
            }
        }
        this.reapAsociado = temp;
        this.reapAsociado.setEstadoRegistro("NUEVO");
        if (re.getTipoRegistro().equals("REGISTRO")) {
            this.reapertura.setRegistroAsociado(codigo);
        } else if (re.getTipoRegistro().equals("REAPERTURA")){
            for (Reapertura reap : DataTables.getReaperturas()){
                if (reap.getReapertura() == codigo){
                    this.reapertura.setRegistroAsociado(reap.getRegistroAsociado());
                    System.out.println(reapertura.getRegistroAsociado());
                    break;
                }
            }
        }
        this.ventanaRegistro = new InterfazRegistro(temp, reapertura);
        InterfazRegistro.addInterface(ventanaRegistro);
    }

    public Reapertura getReapertura() {
        return reapertura;
    }

    public void setReapertura(Reapertura reapertura) {
        this.reapertura = reapertura;
    }

    public Registro getReapAsociado() {
        return reapAsociado;
    }

    public void setReapAsociado(Registro reapAsociado) {
        this.reapAsociado = reapAsociado;
    }
}
