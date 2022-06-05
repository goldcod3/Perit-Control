package conexion.paquetes;

import clases.Registro;
import clases.Usuario;

import java.util.ArrayList;

public class SearchPacket extends DataPacket{

    public String consultaSQL;
    public Registro registro;
    public ArrayList<Registro> tableRegistros;

    public SearchPacket(String ip, Usuario usr) {
        super(ip, usr);
    }

    public String getConsultaSQL() {
        return consultaSQL;
    }

    public void setConsultaSQL(String consultaSQL) {
        this.consultaSQL = consultaSQL;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public ArrayList<Registro> getTableRegistros() {
        return tableRegistros;
    }

    public void setTableRegistros(ArrayList<Registro> tableRegistros) {
        this.tableRegistros = tableRegistros;
    }
}
