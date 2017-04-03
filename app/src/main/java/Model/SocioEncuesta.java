package Model;

/**
 * Created by PC on 01/04/2017.
 */

public class SocioEncuesta {

     private  String  codEncuesta ;
      private String descrip ;
    private String fecha ;

    public String getCodEncuesta() {
        return codEncuesta;
    }

    public void setCodEncuesta(String codEncuesta) {
        this.codEncuesta = codEncuesta;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public SocioEncuesta(String codEncuesta, String descrip, String fecha) {
        this.codEncuesta = codEncuesta;
        this.descrip = descrip;
        this.fecha = fecha;
    }

    public SocioEncuesta() {
    }
}
