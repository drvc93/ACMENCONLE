package Model;

/**
 * Created by drvc_ on 22/09/2016.
 */
public class SocioPuesto {

    private  String codSocion;
    private  String codNumeroPuesto ;
    private  String estado ;
    private  String fechaReg;
    private  String userReg;
    private  String codSeccion;




    public SocioPuesto() {
    }

    public SocioPuesto(String codSocion, String codNumeroPuesto, String estado, String fechaReg, String userReg,String CodSeccion) {


        this.codSocion = codSocion;
        this.codNumeroPuesto = codNumeroPuesto;
        this.estado = estado;
        this.fechaReg = fechaReg;
        this.userReg = userReg;
        this.codSeccion = CodSeccion;
    }


    public String getCodSocion() {
        return codSocion;
    }

    public void setCodSocion(String codSocion) {
        this.codSocion = codSocion;
    }

    public String getCodNumeroPuesto() {
        return codNumeroPuesto;
    }

    public void setCodNumeroPuesto(String codNumeroPuesto) {
        this.codNumeroPuesto = codNumeroPuesto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(String fechaReg) {
        this.fechaReg = fechaReg;
    }

    public String getUserReg() {
        return userReg;
    }

    public void setUserReg(String userReg) {
        this.userReg = userReg;
    }
    public String getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(String codSeccion) {
        this.codSeccion = codSeccion;
    }

}
