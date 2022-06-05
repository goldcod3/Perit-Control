package conexion.hilos;

import clases.ConfigCli;
import conexion.paquetes.PeritPacket;
import interfaces.Config;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SentPeritPacket implements Runnable{
    public PeritPacket packet;

    public SentPeritPacket(PeritPacket pp){
        this.packet = pp;
    }

    @Override
    public void run() {

        try {
            ConfigCli c = ConfigCli.getConfig();
            Socket pipe = new Socket(c.getIpServer(), Integer.parseInt(c.getPortServer()));
            ObjectOutputStream flushout = new ObjectOutputStream(pipe.getOutputStream());
            PeritPacket packet = this.packet;
            flushout.writeObject(packet);
            flushout.close();

        } catch (IOException e) {
            Config.error("No se ha podido conectar con el servidor.\n" +
                    "Contacte con el Administrador del Sistema.");
        }

    }
}
