package conexion;

import interfaces.*;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    public static final int MAX_CONNECTIONS_BD = 500;
    public static int totalBDConnections = 0;
    
    private final String DB = "peritcontrol";
    private final String HOST = ConfigSrv.panelControl.ipBox.getText();
    private final String URL = "jdbc:mysql://localhost:3306/"+DB+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String USER = ConfigSrv.panelControl.userBox.getText();
    private final String PASS = ConfigSrv.panelControl.passBox.getText();
    
    private static ConnectionPool dataSource = null;
    private BasicDataSource basicDataSource;

    //CONSTRUCTOR DEL POOL DE CONEXIONES
    private ConnectionPool(){
     
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(USER);
        basicDataSource.setPassword(PASS);
        basicDataSource.setUrl(URL);
        basicDataSource.setInitialSize(10);
        basicDataSource.setMinIdle(1);
        basicDataSource.setMaxIdle(ConnectionPool.MAX_CONNECTIONS_BD/2);
        basicDataSource.setMaxTotal(ConnectionPool.MAX_CONNECTIONS_BD);
        basicDataSource.setMaxWaitMillis(2);
        
    }

    /*
    METODO QUE INICIALIZA EL POOL DE CONEXIONES
    VERIFICA QUE NO EXISTA UN POOL DE CONEXIONES ANTERIORMENTE.
    */
    public static ConnectionPool getInstance() {
        if (dataSource == null) {
            dataSource = new ConnectionPool();
        }
        return dataSource;
    }
    /*
    METODO QUE CIERRA EL POOL DE CONEXIONES
    VERIFICA QUE EXISTE UN POOL DE CONEXIONES ANTES DEL CIERRE
     */
    public static void closeInstance(){
        if (dataSource != null) {
            dataSource = null;
        }
    }


    public static ConnectionPool getConnect(){
        return ConnectionPool.dataSource;
    }


    /*
    METODO QUE CREA UNA CONEXION A LA BASE DE DATOS
    */
    public static Connection getConnection() throws SQLException{
      return dataSource.basicDataSource.getConnection();
    }

    /*
    METODO QUE CIERRA UNA CONEXION A LA BASE DE DATOS
    */
    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    /*
    DEVUELVE CONEXIONES ACTIVAS AL POOL DE CONEXIONES
     */
    public static int obtenerConActivas(){
        int conexiones = 0;
        if (dataSource != null) {
            conexiones = dataSource.basicDataSource.getNumActive();
        }
        return conexiones;
    }

    /*
    DEVUELVE CONEXIONES DISPONIBLES AL POOL DE CONEXIONES
     */
    public static int obtenerConDisponibles(){
        int conexiones = 0;
        if (dataSource != null) {
            conexiones = dataSource.basicDataSource.getNumIdle();
        }
        return conexiones;
    }
}


