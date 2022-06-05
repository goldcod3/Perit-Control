package clases;

import interfaces.*;

import javax.swing.*;
import java.io.*;

public class ConfigCon implements Serializable {
    @Serial
    private static final long serialVersionUID = 6529685098267757690L;

    private String ipConf = "";
    private String portConf = "";
    private String userConf = "";
    private String passConf = "";

    //INICIALIZAR conconfig.dat
    /*
    public static void main(String[] args) {
        ConfigCon c = new ConfigCon("g","", "", "");
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(
                    new FileOutputStream("C:\\PeritControl_v1\\PCSrv\\ext\\conconfig.dat"));
            output.writeObject(c);
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //*


    //VERIFICAR VALORES conconfig.dat
    /*
    public static void main(String[] args) {
        ConfigCon c = ConfigCon.getConfig();

    }
    //*/


    //CONSTRUCTORES
    public ConfigCon(String ipConf, String portConf, String userConf, String passConf) {
        this.ipConf = ipConf;
        this.portConf = portConf;
        this.userConf = userConf;
        this.passConf = passConf;
    }

    //METODOS
    /*
    Metodo que devuelve un objeto ConfigCon ubicado en el archivo conconfig.dat.
     */
    public static ConfigCon getConfig(){
        ConfigCon config = new ConfigCon("","", "", "");
        try {
            ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream("C:\\PeritControl_v1\\PCSrv\\ext\\conconfig.dat"));

            config = (ConfigCon) input.readObject();
            input.close();
        }catch (Exception e){
            System.out.println("No se encontro el archivo. Intentelo de nuevo.");
            e.printStackTrace();
        }
        return config;
    }

    public static ConfigCon getConfigCon(){
        ConfigCon configuracion = new ConfigCon("","","","");
        String ip, port, user, pass;

        ip = ConfigSrv.panelControl.ipBox.getText();
        port = ConfigSrv.panelControl.portBox.getText();
        user = ConfigSrv.panelControl.userBox.getText();
        pass = ConfigSrv.panelControl.passBox.getText();

        configuracion.setIpConf(ip);
        configuracion.setPortConf(port);
        configuracion.setUserConf(user);
        configuracion.setPassConf(pass);

        return configuracion;

    }

    /*
    Metodo que devuelve true si la configuracion introducida en la ventana es igual a la del archivo
    configcon.dat
     */
    public static boolean igualConfig(ConfigCon newConf){
        boolean igual;
        ConfigCon configCon = ConfigCon.getConfig();
        if (!configCon.ipConf.equals(newConf.ipConf)) igual = false;
        else if (!configCon.portConf.equals(newConf.portConf)) igual = false;
        else if (!configCon.userConf.equals(newConf.userConf)) igual = false;
        else if (!configCon.passConf.equals(newConf.passConf)) igual = false;
        else igual = true;

        return igual;
    }


    /*
    Metodo que modifica el archivo conconfig.dat si la configuracion introducida es
    diferente.
     */
    public static void setConfig(ConfigCon newConf){

            boolean valido = ConfigCon.igualConfig(newConf);
            if (!valido){

                ObjectOutputStream output = null;
                try {
                    output = new ObjectOutputStream(
                            new FileOutputStream("C:\\PeritControl_v1\\PCSrv\\ext\\conconfig.dat"));
                output.writeObject(newConf);
                    JOptionPane.showMessageDialog(ConfigSrv.panelControl, "Se ha modificado la Configuración del Servidor." +
                            "\nCambie la configuración en los clientes para lograr la conexión.");

                output.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else JOptionPane.showMessageDialog(ConfigSrv.panelControl, "La Configuración no ha cambiado.");

    }

    /*
    Metodo que obtiene la configuracion del archivo conconfig.dat y lo imprime en los
    campos de texto de la ventana InterfazServer
     */
    public static void setConfigConBox(){

        ConfigCon configuracion = ConfigCon.getConfig();
        String ip, port, user, pass;
        ip = configuracion.getIpConf();
        port = configuracion.getPortConf();
        user = configuracion.getUserConf();
        pass = configuracion.getPassConf();

        ConfigSrv.panelControl.ipBox.setText(ip);
        ConfigSrv.panelControl.portBox.setText(port);
        ConfigSrv.panelControl.userBox.setText(user);
        ConfigSrv.panelControl.passBox.setText(pass);

    }


    //GUETTERS Y SETTERS
    public String getIpConf() {
        return ipConf;
    }

    public void setIpConf(String ipConf) {
        this.ipConf = ipConf;
    }

    public String getPortConf() {
        return portConf;
    }

    public void setPortConf(String portConf) {
        this.portConf = portConf;
    }

    public String getUserConf() {
        return userConf;
    }

    public void setUserConf(String userConf) {
        this.userConf = userConf;
    }

    public String getPassConf() {
        return passConf;
    }

    public void setPassConf(String passConf) {
        this.passConf = passConf;
    }

    @Override
    public String toString() {
        return "ConfigCon{" +
                "ipConf='" + ipConf + '\'' +
                ", portConf='" + portConf + '\'' +
                ", userConf='" + userConf + '\'' +
                ", passConf='" + passConf + '\'' +
                '}';
    }
}
