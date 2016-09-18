package Model;

/**
 * Created by drvc_ on 18/09/2016.
 */
public class Banco {

    String codBanco ;
    String  nombreLargo;
    String  nombreCorto;
    String nroCuenta ;

    public Banco() {

    }

    public Banco(String codBanco, String nombreLargo, String nombreCorto, String nroCuenta) {

        this.codBanco = codBanco;
        this.nombreLargo = nombreLargo;
        this.nombreCorto = nombreCorto;
        this.nroCuenta = nroCuenta;
    }


    public String getCodBanco() {
        return codBanco;
    }

    public void setCodBanco(String codBanco) {
        this.codBanco = codBanco;
    }

    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }
}
