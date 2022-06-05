package interfaces.iregistro.eventos;

import clases.Reapertura;
import clases.Registro;
import conexion.DataTables;
import interfaces.Config;
import interfaces.iregistro.InterfazRegistro;

public class  HiloVRegistro implements Runnable{
    public InterfazRegistro ventanaRegistro;
    public Registro registro = new Registro();
    @Override
    public void run() {
        try {
            boolean isOpened = false;
            int codigo = (int) Config.pVerifyPol.contenedor.modelSearch.getValueAt(Config.pVerifyPol.contenedor.tabSearchReg.getSelectedRow(), 0);
            for (Registro r : DataTables.getRegistros()) {
                if (r.getCodRegistro() == codigo) {
                    registro = r;
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

             */
            if (!InterfazRegistro.existsInterfaceReg(registro.getCodRegistro())) {
                Config.pVerifyPol.dispose();
                Config.pVerifyPol = null;

                if(registro.getTipoRegistro().equals("REGISTRO")){
                    ventanaRegistro = new InterfazRegistro(registro);
                    InterfazRegistro.addInterface(ventanaRegistro);

                } else if (registro.getTipoRegistro().equals("REAPERTURA")){
                    Reapertura temp = new Reapertura(0);
                    temp = temp.getReaperturaFromCodigo(codigo);
                    System.out.println(temp.toString());
                    ventanaRegistro = new InterfazRegistro(registro, temp);
                    InterfazRegistro.addInterface(ventanaRegistro);

                }

            } else Config.warning("Este Registro está abierto, verifique las ventanas en ejecución.");

        }catch (Exception e){
            Config.error("Seleccione un Registro de la tabla de verificación.");
            e.printStackTrace();
        }
    }
}
