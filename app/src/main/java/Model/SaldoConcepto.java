package Model;

/**
 * Created by drvc_ on 07/10/2016.
 */
public class SaldoConcepto {

    private  String codConcepto;
    private String saldoxConcepto;
    private String  pagadoxConceto;

    public SaldoConcepto() {

    }

    public String getCodConcepto() {
        return codConcepto;
    }

    public void setCodConcepto(String codConcepto) {
        this.codConcepto = codConcepto;
    }

    public String getSaldoxConcepto() {
        return saldoxConcepto;
    }

    public void setSaldoxConcepto(String saldoxConcepto) {
        this.saldoxConcepto = saldoxConcepto;
    }

    public String getPagadoxConceto() {
        return pagadoxConceto;
    }

    public void setPagadoxConceto(String pagadoxConceto) {
        this.pagadoxConceto = pagadoxConceto;
    }

    public SaldoConcepto(String codConcepto, String saldoxConcepto, String pagadoxConceto) {


        this.codConcepto = codConcepto;
        this.saldoxConcepto = saldoxConcepto;
        this.pagadoxConceto = pagadoxConceto;
    }
}
