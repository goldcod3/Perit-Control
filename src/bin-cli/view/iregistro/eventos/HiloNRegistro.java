package interfaces.iregistro.eventos;

import clases.Asegurado;
import clases.Registro;
import clases.TelefonoAsegurado;
import interfaces.Config;
import interfaces.iregistro.InterfazRegistro;

import java.util.Date;

public class HiloNRegistro implements Runnable{
    public InterfazRegistro ventanaRegistro;
    public Registro registro;
    @Override
    public void run() {
        registro = new Registro();
        registro.setEstadoRegistro("NUEVO");
        registro.getSiniestro().setNumPoliza("");

        ventanaRegistro = new InterfazRegistro(registro);
        InterfazRegistro.addInterface(ventanaRegistro);
    }
}
