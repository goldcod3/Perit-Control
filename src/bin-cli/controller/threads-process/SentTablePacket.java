package conexion.hilos;

import clases.ConfigCli;
import conexion.paquetes.DataPacket;
import conexion.paquetes.TablePacket;
import interfaces.Config;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SentTablePacket implements Runnable{
    @Override
    public void run() {
        try {
            ConfigCli c = Config.configuracion;
            Socket pipe = new Socket(c.getIpServer(), Integer.parseInt(c.getPortServer()));
            ObjectOutputStream flushout = new ObjectOutputStream(pipe.getOutputStream());
            Config.pControl.pControl.changeUltimaAccion("ACTUALIZANDO. .");
            TablePacket tablePacket = new TablePacket(c.getIpClient(), Config.getUser());
            tablePacket.setCodPacket(3);
            tablePacket.setFechaDesde(c.getFechaDesde());
            tablePacket.setFechaHasta(c.getFechaHasta());
            flushout.writeObject(tablePacket);
            Config.pControl.pControl.changeUltimaAccion("ACTUALIZANDO . .");
            flushout.close();

        } catch (IOException e) {
            Config.error("No se ha podido conectar con el servidor.\n" +
                    "Contacte con el Administrador del Sistema.");
        }



    }
}
