package Model;

/**
 * Created by drvc_ on 13/10/2016.
 */
public class DetalleSaldo {

     private  String codConcepto  ;
     private  String mes ;
    private  String anio ;
    private  String estado;
    private  String monto ;

    public DetalleSaldo() {
    }

    public DetalleSaldo(String codConcepto, String mes, String anio, String estado, String monto) {
        this.codConcepto = codConcepto;
        this.mes = mes;
        this.anio = anio;
        this.estado = estado;
        this.monto = monto;
    }


    public String getCodConcepto() {
        return codConcepto;
    }

    public void setCodConcepto(String codConcepto) {
        this.codConcepto = codConcepto;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
