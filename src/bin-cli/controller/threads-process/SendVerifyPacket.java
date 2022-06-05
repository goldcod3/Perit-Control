package conexion.hilos;

import clases.ConfigCli;
import conexion.paquetes.DataPacket;
import conexion.paquetes.VerifyPacket;
import interfaces.Config;
import interfaces.ilogin.InterfazErrorCon;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendVerifyPacket implements Runnable{
    @Override
    public void run() {

        try {
            ConfigCli c = ConfigCli.getConfig();
            Socket pipe = new Socket(c.getIpServer(), Integer.parseInt(c.getPortServer()));
            ObjectOutputStream flushout = new ObjectOutputStream(pipe.getOutputStream());
            VerifyPacket dataPacket = new VerifyPacket(c.getIpClient(), Config.getUserVerify());
            flushout.writeObject(dataPacket);
            flushout.close();

        } catch (IOException e) {
            InterfazErrorCon err = new InterfazErrorCon();
            Config.error("No se ha podido conectar con el servidor.\n" +
                    "Contacte con el Administrador del Sistema.");
        }
    }
}
