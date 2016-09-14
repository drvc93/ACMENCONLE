package Model;

/**
 * Created by drvc_ on 14/09/2016.
 */
public class Seccion {

    private  String codigo ;
    private  String descripcion;
    private  String fecha ;


    public Seccion(String codigo, String descripcion, String fecha) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public Seccion() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
