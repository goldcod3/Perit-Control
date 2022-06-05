package clases;

import java.util.ArrayList;

public class TelfData {
    public int cod =0;
    public ArrayList<TelefonoAsegurado> telfAseg = new ArrayList<>();

    public TelfData(int cod, ArrayList<TelefonoAsegurado> telfAseg) {
        this.cod = cod;
        this.telfAseg = telfAseg;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public ArrayList<TelefonoAsegurado> getTelfAseg() {
        return telfAseg;
    }

    public void setTelfAseg(ArrayList<TelefonoAsegurado> telfAseg) {
        this.telfAseg = telfAseg;
    }
}
