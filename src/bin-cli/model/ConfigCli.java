package clases;

import interfaces.Config;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class ConfigCli implements Serializable {
    @Serial
    private static final long serialVersionUID = 6529685098267757691L;

    private String ipServer = "";
    private String ipClient = "";
    private String portServer = "";
    private Date fechaDesde;
    private Date fechaHasta;
    private boolean checkAmpliacion = false;

    public static void main(String[] args) {
        ConfigCli c = new ConfigCli("192.168.10.50","192.168.10.50","3344");
        Date fechaHoy = new Date();
        Date fechaFuturo = new Date();
        Date fechaPasado = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaHoy);
        calendar.add(Calendar.YEAR, 2);
        fechaFuturo = calendar.getTime();
        calendar.setTime(fechaHoy);
        calendar.add(Calendar.YEAR, -2);
        fechaPasado = calendar.getTime();

        c.setFechaHasta(fechaFuturo);
        c.setFechaDesde(fechaPasado);
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(
                    new FileOutputStream("C:\\PeritControl_v1\\PCCli\\cliconfig.dat"));
            output.writeObject(c);
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //CONSTRUCTORES
    public ConfigCli(String ipClient, String ipServer, String portServer) {
        this.ipClient = ipClient;
        this.ipServer = ipServer;
        this.portServer = portServer;
    }

    public static void createConfigCliData(){
        File file = new File("C:\\PeritControl_v1\\PCCli\\ext\\cliconfig.dat");
        if (!file.exists()){
            ConfigCli c = new ConfigCli("192.168.10.53","192.168.10.50","3344");
            c.configFechas();
            ObjectOutputStream output = null;
            try {
                output = new ObjectOutputStream(
                        new FileOutputStream("C:\\PeritControl_v1\\PCCli\\cliconfig.dat"));
                output.writeObject(c);
                output.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void configFechas(){
        Date fechaHoy = new Date();
        Date fechaFuturo = new Date();
        Date fechaPasado = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaHoy);
        calendar.add(Calendar.YEAR, 2);
        fechaFuturo = calendar.getTime();
        calendar.setTime(fechaHoy);
        calendar.add(Calendar.YEAR, -2);
        fechaPasado = calendar.getTime();

        setFechaHasta(fechaFuturo);
        setFechaDesde(fechaPasado);
    }

    public boolean verifyDates(){
        boolean verify = false;
        ConfigCli c = ConfigCli.getConfig();
        Date hoy = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(c.getFechaHasta());
        calendar.add(Calendar.YEAR, -2);
        if (hoy.after(calendar.getTime())){
            c.configFechas();
            ConfigCli.setConfig(c);
        }else if (hoy.equals(calendar.getTime())){
            c.configFechas();
            ConfigCli.setConfig(c);
        } else if (c.getFechaHasta().equals(hoy)){
            c.configFechas();
            ConfigCli.setConfig(c);
        }else {
            verify = true;
        }
        return verify;
    }
    public static ConfigCli getConfig(){
        File file = new File("C:\\PeritControl_v1\\PCCli\\ext\\cliconfig.dat");
        if (!file.exists()){
            ConfigCli c = new ConfigCli("0","0", "0");
            c.configFechas();
            ObjectOutputStream output = null;
            try {
                output = new ObjectOutputStream(
                        new FileOutputStream("C:\\PeritControl_v1\\PCCli\\ext\\cliconfig.dat"));
                output.writeObject(c);
                output.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            ConfigCli config = new ConfigCli("","", "");
            try {
                ObjectInputStream input = new ObjectInputStream(
                        new FileInputStream("C:\\PeritControl_v1\\PCCli\\ext\\cliconfig.dat"));

                config = (ConfigCli) input.readObject();
                input.close();
            }catch (Exception e){
                System.out.println("No se encontro el archivo. Intentelo de nuevo.");
                e.printStackTrace();
            }
            return config;
        }else{

            ConfigCli config = new ConfigCli("","", "");
            try {
                ObjectInputStream input = new ObjectInputStream(
                        new FileInputStream("C:\\PeritControl_v1\\PCCli\\ext\\cliconfig.dat"));

                config = (ConfigCli) input.readObject();
                input.close();
            }catch (Exception e){
                System.out.println("No se encontro el archivo. Intentelo de nuevo.");
                e.printStackTrace();
            }
            return config;
        }
    }

    public static boolean igualConfig(ConfigCli newConf){
        boolean igual;
        ConfigCli configCon = ConfigCli.getConfig();
        if (!configCon.ipClient.equals(newConf.ipClient)) igual = false;
        else if (!configCon.ipServer.equals(newConf.ipServer)) igual = false;
        else if (!configCon.portServer.equals(newConf.portServer)) igual = false;
        else if (!configCon.fechaDesde.equals(newConf.getFechaDesde())) igual = false;
        else if (!configCon.fechaHasta.equals(newConf.getFechaHasta())) igual = false;
        else if (configCon.checkAmpliacion != Config.pControl.pConfig.check.isSelected()) igual = false;
        else igual = true;

        return igual;
    }


    public static boolean setConfig(ConfigCli newConf){

        boolean valido = ConfigCli.igualConfig(newConf);
        boolean setconfig = false;
        if (!valido){

            ObjectOutputStream output = null;
            try {
                output = new ObjectOutputStream(
                        new FileOutputStream("C:\\PeritControl_v1\\PCCli\\ext\\cliconfig.dat"));
                output.writeObject(newConf);
                setconfig = true;
                output.close();

            } catch (IOException e) {
                setconfig = false;
                e.printStackTrace();
            }

        }else {
            setconfig = false;
        }

        return setconfig;

    }

    //GUETTERS Y SETTERS
    public String getIpClient() {
        return ipClient;
    }

    public void setIpConf(String ipConf) {
        this.ipClient = ipConf;
    }

    public String getIpServer() {
        return this.ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public String getPortServer() {
        return this.portServer;
    }

    public void setPortServer(String portServer) {
        this.portServer = portServer;
    }

    public boolean isCheckAmpliacion() {
        return checkAmpliacion;
    }

    public void setCheckAmpliacion(boolean checkAmpliacion) {
        this.checkAmpliacion = checkAmpliacion;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    @Override
    public String toString() {
        return "ConfigCli{" +
                "ipServer='" + ipServer + '\'' +
                ", ipClient='" + ipClient + '\'' +
                ", portServer='" + portServer + '\'' +
                ", fechaDesde=" + fechaDesde +
                ", fechaHasta=" + fechaHasta +
                '}';
    }

}
