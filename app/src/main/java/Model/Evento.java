package Model;

/**
 * Created by drvc_ on 02/02/2017.
 */
public class Evento {

     public  Evento (){}

      String codEvento ;
      String anio ;
      String mes ;
      String dia ;
      String  hora ;
      String descripCorta  ;

    public Evento(String anio, String codEvento, String descripCorta, String dia, String hora, String mes) {
        this.anio = anio;
        this.codEvento = codEvento;
        this.descripCorta = descripCorta;
        this.dia = dia;
        this.hora = hora;
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getCodEvento() {
        return codEvento;
    }

    public void setCodEvento(String codEvento) {
        this.codEvento = codEvento;
    }

    public String getDescripCorta() {
        return descripCorta;
    }

    public void setDescripCorta(String descripCorta) {
        this.descripCorta = descripCorta;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
