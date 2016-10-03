package Model;

/**
 * Created by drvc_ on 03/10/2016.
 */
public class CPagos {


    private  String nombres;
    private  String fechaPago;
    private  String seccion ;
    private  String monto ;
    private  String banco;
    private  String nroPuesto ;
    private  String estado ;

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNroPuesto() {
        return nroPuesto;
    }

    public void setNroPuesto(String nroPuesto) {
        this.nroPuesto = nroPuesto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CPagos() {
    }

    public CPagos(String nombres, String fechaPago, String seccion, String monto, String banco, String nroPuesto, String estado) {
        this.nombres = nombres;
        this.fechaPago = fechaPago;
        this.seccion = seccion;
        this.monto = monto;
        this.banco = banco;
        this.nroPuesto = nroPuesto;
        this.estado = estado;
    }
}
