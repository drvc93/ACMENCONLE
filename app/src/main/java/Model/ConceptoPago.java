package Model;

/**
 * Created by drvc_ on 18/09/2016.
 */
public class ConceptoPago {

    String codConcepto;
    String descripcion ;
    String monto ;
    String userReg ;
    String fecharReg;

    public ConceptoPago() {

    }

    public ConceptoPago(String codConcepto, String descripcion, String monto, String userReg, String fecharReg) {

        this.codConcepto = codConcepto;
        this.descripcion = descripcion;
        this.monto = monto;
        this.userReg = userReg;
        this.fecharReg = fecharReg;
    }

    public String getCodConcepto() {
        return codConcepto;
    }

    public void setCodConcepto(String codConcepto) {
        this.codConcepto = codConcepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getUserReg() {
        return userReg;
    }

    public void setUserReg(String userReg) {
        this.userReg = userReg;
    }

    public String getFecharReg() {
        return fecharReg;
    }

    public void setFecharReg(String fecharReg) {
        this.fecharReg = fecharReg;
    }
}
