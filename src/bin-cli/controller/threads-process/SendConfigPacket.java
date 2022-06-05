package conexion.hilos;

import clases.ConfigCli;
import conexion.paquetes.ConfigPacket;
import conexion.paquetes.VerifyPacket;
import interfaces.Config;
import interfaces.ilogin.InterfazErrorCon;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendConfigPacket implements Runnable{

    public ConfigPacket packet;

    @Override
    public void run() {

        try {
            ConfigCli c = ConfigCli.getConfig();
            Socket pipe = new Socket(c.getIpServer(), Integer.parseInt(c.getPortServer()));
            ObjectOutputStream flushout = new ObjectOutputStream(pipe.getOutputStream());
            ConfigPacket confPacket = this.packet;
            flushout.writeObject(confPacket);
            flushout.close();

        } catch (IOException e) {
            Config.error("No se ha podido conectar con el servidor.\n" +
                    "Contacte con el Administrador del Sistema.");
            InterfazErrorCon err = new InterfazErrorCon();
        }
    }
}
