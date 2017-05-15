package Model;

/**
 * Created by PC on 15/05/2017.
 */

public class SocioPregunta {

    private  String  codEcnuetsa;
    private  String orden ;
    private  String codSocio ;
    private  String valor ;
    private  String fechaReg;
    private  String tipo ;
    private  String pregunta;


    public SocioPregunta() {


    }

    public SocioPregunta(String codEcnuetsa, String orden, String codSocio, String valor, String fechaReg, String tipo, String pregunta) {

        this.codEcnuetsa = codEcnuetsa;
        this.orden = orden;
        this.codSocio = codSocio;
        this.valor = valor;
        this.fechaReg = fechaReg;
        this.tipo = tipo;
        this.pregunta = pregunta;
    }

    public String getCodEcnuetsa() {
        return codEcnuetsa;
    }

    public void setCodEcnuetsa(String codEcnuetsa) {
        this.codEcnuetsa = codEcnuetsa;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getCodSocio() {
        return codSocio;
    }

    public void setCodSocio(String codSocio) {
        this.codSocio = codSocio;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(String fechaReg) {
        this.fechaReg = fechaReg;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
}
