package conexion;

import clases.*;
import conexion.paquetes.*;
import interfaces.ConfigSrv;
import interfaces.InterfazServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;

public class Service {

    public static final int MAX_CONNECTION_REFRESH = 100;
    public static final int MAX_ERRORS_ALLOWED = 5;

    private ConnectionPool connBBDD;
    private boolean statusService = false;
    private boolean statusPort = false;
    public int errorService = 0;
    public int totalConnections = 0;
    public Thread serverService , verifyService;
    private ArrayList<String[]> connHistory = new ArrayList<>();

    public Service(){ }

    public void startService(){
        //APERTURA DE PUERTO

        try {
            //INSTANCIA DE PUERTO A APERTURAR
            ConfigSrv.panelControl.historyMsj("Abriendo puerto "+ConfigSrv.panelControl.portBox.getText()+" a los Clientes...");
            ConfigSrv.serverPort = new ServerSocket(3344);
            ConfigSrv.service.setStatusPort(true);
            Thread.sleep(1000);

        } catch (Exception e){
            ConfigSrv.service.setStatusPort(false);
        }


        if (ConfigSrv.service.statusPort) {

            //APERTURA DE PUERTO
            ConfigSrv.panelControl.historyMsj("Configurando Puerto de Comunicación.");
            ConfigSrv.service.openServicePort();

            ConfigSrv.panelControl.historyMsj("Servicio en Ejecución.");
            ConfigSrv.panelControl.historyMsj("_________________________________________________________---");

            System.out.println("___________________________");
            System.out.println("Servicio en Ejecución.");
            System.out.println("___________________________");

            ConfigSrv.service.setStatusService(true);

        } else ConfigSrv.panelControl.historyMsj("Error de Puerto: Reinicie el Servicio.\n    Si persiste el error contacte con el Administrador del sistema");
    }

    public void openServicePort(){
        serverService = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        if (ConfigSrv.service != null) {
                            Socket socketIn = ConfigSrv.serverPort.accept();
                            //RECIBO PAQUETE
                            ObjectInputStream streamIn = new ObjectInputStream(socketIn.getInputStream());
                            Object packet = streamIn.readObject();

                            //VERIFICACION DE LOGIN USUARIO
                            if (packet.getClass() == DataPacket.class) {
                                ServicePort.loginClientVerify((DataPacket) packet);

                                //VERIFICACION DE USUARIO ADMINISTRADOR
                            } else if (packet.getClass() == VerifyPacket.class) {
                                ServicePort.verifyAdminUser((VerifyPacket) packet);

                                //ENVIO DE TABLAS A CLIENTE
                            } else if (packet.getClass() == TablePacket.class) {
                                ServicePort.sendServerTablesClient((TablePacket) packet);
                                System.gc();

                                //INSERCION Y MODIFICACION DE REGISTROS Y REAPERTURAS
                            } else if (packet.getClass() == SafePacket.class) {
                                ServicePort.dataRegistroBBDD((SafePacket) packet);

                                //INSERCION, MODIFICACION Y ELIMINACION DE TELEFONOS
                            } else if (packet.getClass() == TelfPacket.class) {
                                ServicePort.dataTelfBBDD((TelfPacket) packet);

                                //INSERCION, MODIFICACION DE PERITOS
                            } else if (packet.getClass() == PeritPacket.class) {
                                ServicePort.dataPeritoBBDD((PeritPacket) packet);

                                //INSERCION, MODIFICACION DE DATOS MAESTROS (COMPANIAS, USUARIOS, TIPOS DE SINIESTROS, ETC)
                            } else if (packet.getClass() == ConfigPacket.class) {
                                ServicePort.dataConfigBBDD((ConfigPacket) packet);

                            }

                            streamIn.close();
                            socketIn.close();

                            ConfigSrv.service.totalConnections++;
                            ConnectionPool.totalBDConnections++;
                            if (ConnectionPool.totalBDConnections == ConnectionPool.MAX_CONNECTIONS_BD-1) break;
                            ConfigSrv.service.verifyService();
                            ConfigSrv.panelControl.actConnectionsNumber();                        }
                    }
                    Service.resetService();
                    serverService.interrupt();
                } catch (Exception f) {
                    resetService();
                    serverService.interrupt();
                }
            }
        });
        serverService.start();

    }

    public static void sendPacketClient(Object packet, String ipClient, String user){
        Thread sendPacket = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    if (ConfigSrv.service != null) {
                        Socket socketOut = new Socket(ipClient, Integer.parseInt(ConfigSrv.panelControl.portBox.getText()));
                        ObjectOutputStream streamOut = null;
                        streamOut = new ObjectOutputStream(socketOut.getOutputStream());
                        streamOut.writeObject(packet);
                        streamOut.close();
                        socketOut.close();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    ConfigSrv.panelControl.historyMsj("Error de Respuesta: No se ha enviado el paquete. Usr-"+user+"  Ip-"+ipClient);
                    ConfigSrv.service.errorService++;
                }

            }
        });
        sendPacket.start();
        do{
            if (!sendPacket.isAlive()){
                sendPacket = null;
                break;
            }
        } while (sendPacket.isAlive());
    }

    public void closeService() {
        if (ConfigSrv.service != null){
            try {
                ConfigSrv.serverPort.close();
                ConfigSrv.serverPort = null;
                ConfigSrv.service.serverService.interrupt();
                ConfigSrv.service.serverService = new Thread(Thread.currentThread());
                ConfigSrv.service = null;
                System.gc();
            } catch (Exception e){
                ConfigSrv.panelControl.historyMsj("Error al detener el servicio.\n" +
                        "     Inicie el servicio nuevamente, Si el problema persiste reinicie la aplicación.");
            }
        }
    }

    public void verifyService(){
        if (ConfigSrv.service != null){
            System.out.println("Conexiones: "+ConfigSrv.service.totalConnections);
            System.out.println("Errores: "+ConfigSrv.service.errorService);
            System.out.println("___________________________");

            boolean conections;
            boolean errors = false;
            conections = Service.verifyConnectionsService();
            if (!conections){
                errors = Service.verifyErrorsService();
            }
            if (conections || errors){
                ConfigSrv.panelControl.historialPanel.setText("");
                ConfigSrv.panelControl.historyMsj("ACT: Se ha actualizado el servidor.");
                System.gc();
            }
        }
    }

    public static boolean verifyConnectionsService() {
        if (ConfigSrv.service != null){
            if (ConfigSrv.service.totalConnections >= Service.MAX_CONNECTION_REFRESH){
                ConfigSrv.service.closeService();
                ConfigSrv.service = new Service();
                ConfigSrv.service.startService();
                System.out.println("Se ha actualizado el servicio.");
                System.out.println("___________________________");
                return true;
            }
        }
        return false;
    }

    public static boolean verifyErrorsService(){
        if (ConfigSrv.service != null){
            if (ConfigSrv.service.errorService >= Service.MAX_ERRORS_ALLOWED){
                ConfigSrv.service.closeService();
                ConfigSrv.service = new Service();
                ConfigSrv.service.startService();
                System.out.println("Se ha actualizado el servicio.");
                System.out.println("___________________________");
                return true;
            }
        }
        return false;
    }

    public static void resetService(){
        try {
            //SE DETIENE EL SERVICIO
            ConfigSrv.serverPort.close();
            ConfigSrv.serverPort = null;
            ConfigSrv.service.closeService();
            ConfigSrv.service.serverService = new Thread(Thread.currentThread());
            ConfigSrv.service = null;
            ConnectionPool.closeInstance();
            DataCharge.cleanDataTables();
            ConnectionPool.totalBDConnections = 0;


            System.gc();

            // INICIA EL SERVIDOR
            ConnectionPool.getInstance();
            DataCharge.chargeDataTables();
            ConfigSrv.service = new Service();
            ConfigSrv.service.startService();

        } catch (Exception e) {
            ConfigSrv.panelControl.historyMsj("Error al reiniciar el servicio.\n" +
                    "     Inicie el servicio nuevamente, Si el problema persiste reinicie la aplicación.");

        }

    }

    public boolean isStatusService() {
        return statusService;
    }

    public void setStatusService(boolean statusService) {
        this.statusService = statusService;
    }

    public boolean isStatusPort() {
        return statusPort;
    }

    public void setStatusPort(boolean statusPort) {
        this.statusPort = statusPort;
    }

    public int getErrorService() {
        return errorService;
    }

    public void setErrorService(int errorService) {
        this.errorService = errorService;
    }

    public ArrayList<String[]> getConnHistory() {
        return connHistory;
    }

    public void setConnHistory(ArrayList<String[]> connHistory) {
        this.connHistory = connHistory;
    }
}
