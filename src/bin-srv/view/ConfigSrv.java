package interfaces;

import clases.ConfigCon;
import conexion.Service;

import java.awt.*;
import java.net.ServerSocket;

public class ConfigSrv {

    public static ServerSocket serverPort;
    public static Service service = null;
    public static InterfazServer panelControl;
    public static ConfigCon config;


    public static final Color AUX1 = new Color(164, 36, 59);
    public static final Color AUX2 = new Color(159, 196, 144);
    public static final Color COLOR1 = new Color(60, 64, 71);


    public static void main(String[] args) {
        panelControl = new InterfazServer();
        ConfigCon.setConfigConBox();
    }

    //WARNINGS

}
