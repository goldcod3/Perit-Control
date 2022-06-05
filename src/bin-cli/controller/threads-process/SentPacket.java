package conexion.hilos;

import clases.ConfigCli;
import conexion.paquetes.DataPacket;
import interfaces.Config;
import interfaces.ilogin.InterfazErrorCon;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SentPacket implements Runnable{
    @Override
    public void run() {

        try {
            ConfigCli c = Config.configuracion;
            Socket pipe = new Socket(c.getIpServer(), Integer.parseInt(c.getPortServer()));
            ObjectOutputStream flushout = new ObjectOutputStream(pipe.getOutputStream());
            DataPacket dataPacket = new DataPacket(c.getIpClient(), Config.getUser());
            dataPacket.setFechaDesde(c.getFechaDesde());
            dataPacket.setFechaHasta(c.getFechaHasta());
            flushout.writeObject(dataPacket);
            flushout.close();

        } catch (IOException e) {
            InterfazErrorCon err = new InterfazErrorCon();
            Config.login.pLogin.login.setBackground(Config.AUX5);
            Config.error("No se ha podido conectar con el servidor.\n" +
                    "Contacte con el Administrador del Sistema.");
        }


    }
}
