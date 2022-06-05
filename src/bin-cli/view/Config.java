package interfaces;

import clases.ConfigCli;
import clases.Usuario;
import conexion.ReceivePacket;
import interfaces.icontrol.InterfazPControl;
import interfaces.icontrol.pconfig.icompania.InterfazEditCompania;
import interfaces.icontrol.pconfig.iramo.InterfazEditRamo;
import interfaces.icontrol.pconfig.itipointervencion.InterfazEditTipoInt;
import interfaces.icontrol.pconfig.itiporeapertura.InterfazEditTipoReap;
import interfaces.icontrol.pconfig.itiposiniestro.InterfazEditTipoSin;
import interfaces.icontrol.pconfig.iuser.InterfazEditUsr;
import interfaces.icontrol.pconfig.iuser.InterfazVerifyUser;
import interfaces.icontrol.pcontrol.InterfazFiltroRegistro;
import interfaces.icontrol.pperitos.iestadisticas.InterfazEstadisticas;
import interfaces.icontrol.pperitos.ifichaperito.InterfazPerito;
import interfaces.icontrol.pperitos.iliquidacion.InterfazLiquidacion;
import interfaces.ilogin.InterfazLoginCli;
import interfaces.iregistro.InterfazRegistro;
import interfaces.iregistro.InterfazVerifPoliza;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Config {


    private static Usuario user = new Usuario();
    private static Usuario userVerify = new Usuario();
    public static ConfigCli configuracion;
    public static InterfazLoginCli login;
    public static InterfazPControl pControl;
    public static InterfazFiltroRegistro filtro;
    public static InterfazVerifyUser pVerifyUsr;
    public static InterfazVerifPoliza pVerifyPol;
    public static InterfazPerito pPerito;
    public static InterfazEditCompania pEditCompania;
    public static InterfazEditRamo pEditRamo;
    public static InterfazEditTipoSin pEditTSin;
    public static InterfazEditTipoInt pEditTInt;
    public static InterfazEditTipoReap pEditTReap;
    public static InterfazLiquidacion pLiquidacion;
    public static InterfazEstadisticas pEstadisticas;
    public static InterfazEditUsr pEditUsr;

    public static ArrayList<InterfazRegistro> interfaces = new ArrayList<InterfazRegistro>();
    public static int contadorInterfaces = 0;

    //PANTALLA
    public static Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
    public static int anchoPantalla = (pantalla.width/2) + 15;
    public static int alturaPantalla = pantalla.height - 34;

    //COLORES
    public static final Color COLOR1 = new Color(66, 147, 206);
    public static final Color COLOR2 = new Color(191, 201, 220);
    public static final Color COLOR3 = new Color(48, 51, 54);
    public static final Color COLOR4 = new Color(60, 64, 71);
    public static final Color COLOR5 = new Color(67, 72, 76);
    public static final Color COLOR6 = new Color(147, 147, 147);

    public static final Color AUX1 = new Color(164, 36, 59);
    public static final Color AUX2 = new Color(159, 196, 144);
    public static final Color AUX3 = new Color(216, 151, 60);
    public static final Color AUX4 = new Color(158, 183, 229);
    public static final Color AUX5 = new Color(100, 141, 229);
    public static final Color AUX6 = new Color(221, 139, 85);

    //FUENTES
    public static final String fuente = Font.SANS_SERIF;
    public static final Font FUENTETITULO = new Font(fuente, Font.PLAIN,18);
    public static final Font FUENTE14 = new Font(fuente, Font.PLAIN,14);



    public static void main(String[] args) {

        Thread receiveData = new Thread(new ReceivePacket());
        receiveData.start();
        configuracion = ConfigCli.getConfig();
        if (!configuracion.verifyDates()){
            configuracion = ConfigCli.getConfig();
            configuracion = ConfigCli.getConfig();
        } else {
            configuracion.configFechas();
            ConfigCli.setConfig(configuracion);
            configuracion = ConfigCli.getConfig();
        }
        login = new InterfazLoginCli();


    }

    public static void error(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje, "PeritControl - Error", JOptionPane.ERROR_MESSAGE );
    }

    public static void warning(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje, "PeritControl - Aviso", JOptionPane.WARNING_MESSAGE );
    }

    public static void info(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje, "PeritControl - Información", JOptionPane.PLAIN_MESSAGE );
    }

    public static int option(String mensaje){
        int opt = JOptionPane.showConfirmDialog(null, mensaje, "PeritControl - Aviso", JOptionPane.YES_NO_OPTION);
        return opt;
    }

    public static String value(String mensaje, String titulo){
        String opt = JOptionPane.showInputDialog(null,mensaje,titulo,JOptionPane.PLAIN_MESSAGE);
        return opt;
    }

    public static String getIpClient(){
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();

        }catch (UnknownHostException e){
            return "";
        }
    }

    /*
     * Metodo que posiciona el GridBagConstrain del Layout para introducir el componente en la ventana.
     */
    public static GridBagConstraints newPosicion(int anchor, int fill, int gridx, int gridy){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        return gbc;
    }

    public static boolean isWindowOpened(int codRegistro){
        boolean isOpened = false;
        for (InterfazRegistro i : Config.interfaces){
            if (i.contenedorReg.registro.getCodRegistro() == codRegistro){
                isOpened = true;
                break;
            }
        }
        return isOpened;
    }

    public static Usuario getUser() {
        return user;
    }

    public static void setUser(Usuario user) {
        Config.user = user;
    }

    public static Usuario getUserVerify() {
        return userVerify;
    }

    public static void setUserVerify(Usuario userVerify) {
        Config.userVerify = userVerify;
    }

    public static ArrayList<InterfazRegistro> getInterfaces() {
        return interfaces;
    }

    public static void setInterfaces(ArrayList<InterfazRegistro> interfaces) {
        Config.interfaces = interfaces;
    }

    public static void remove(int rem){
        for (int i = 0; i < interfaces.size(); i++){

            if (interfaces.get(i).getIdInterfaz() == rem){
                interfaces.get(i).dispose();
                interfaces.remove(i);
                break;
            }
        }
    }

}
