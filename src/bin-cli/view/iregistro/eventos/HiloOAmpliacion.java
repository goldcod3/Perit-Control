package interfaces.iregistro.eventos;

import clases.Reapertura;
import clases.Registro;
import conexion.DataTables;
import interfaces.Config;
import interfaces.iregistro.InterfazRegistro;

public class HiloOAmpliacion implements Runnable{
    public InterfazRegistro ventanaRegistro;
    public Registro registro = new Registro();
    public int codigo = 0;

    @Override
    public void run() {
        try {
            boolean isOpened = false;
            int codigo = this.codigo;
            for (Registro r : DataTables.getRegistros()){
                if (r.getCodRegistro() == codigo){
                    registro = r;
                    break;
                }
            }
            /*
            if(registro.getTipoRegistro().equals("REGISTRO")){
                for (InterfazRegistro i : Config.interfaces){
                    if (i.reg.getCodRegistro() == registro.getCodRegistro()){
                        isOpened = true;
                    }
                }

            }else if (registro.getTipoRegistro().equals("REAPERTURA")){
                for (InterfazRegistro i : Config.interfaces){
                    if (i.reg.getCodRegistro() == registro.getCodRegistro()){
                        isOpened = true;
                    }
                }
            }
            if (!isOpened){
                if(registro.getTipoRegistro().equals("REGISTRO")){
                    ventanaRegistro = new InterfazRegistro(registro);
                    //Config.interfaces.add(ventanaRegistro);
                    boolean add = InterfazRegistro.addInterface(ventanaRegistro);
                    System.out.println("Add -- "+add);

                } else if (registro.getTipoRegistro().equals("REAPERTURA")){
                    Reapertura temp = new Reapertura(0);
                    temp = temp.getReaperturaFromCodigo(codigo);
                    ventanaRegistro = new InterfazRegistro(registro, temp);
                    //Config.interfaces.add(ventanaRegistro);
                    boolean add = InterfazRegistro.addInterface(ventanaRegistro);
                    System.out.println("Add -- "+add);
                }
            } else Config.warning("Este Registro está abierto, verifique las ventanas en ejecución.");
            */
            if (!InterfazRegistro.existsInterfaceReg(registro.getCodRegistro())){
                if(registro.getTipoRegistro().equals("REGISTRO")){
                    ventanaRegistro = new InterfazRegistro(registro);
                    InterfazRegistro.addInterface(ventanaRegistro);

                } else if (registro.getTipoRegistro().equals("REAPERTURA")){
                    Reapertura temp = new Reapertura(0);
                    temp = temp.getReaperturaFromCodigo(codigo);
                    ventanaRegistro = new InterfazRegistro(registro, temp);
                    InterfazRegistro.addInterface(ventanaRegistro);

                }
            } else Config.warning("Este Registro está abierto, verifique las ventanas en ejecución.");
        }catch (Exception e){
            e.printStackTrace();
            Config.error("Seleccione un Registro de la tabla");
        }
    }
}
