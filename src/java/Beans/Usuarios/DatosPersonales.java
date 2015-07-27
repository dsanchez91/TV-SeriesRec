package Beans.Usuarios;
import java.io.Serializable;


/**
 *
 * @author Diego Sánchez
 */
public class DatosPersonales implements Serializable{
    String nombre;
    String apellidos;
    String sexo;
    String fechanacimiento;
    String ocupacion;
    String email;
    String provincia;
    String nacionalidad;

    public void setNombre(String n){this.nombre = n;}
    public String getNombre(){return this.nombre;}

    public void setApellidos(String a){this.apellidos=a;}
    public String getApellidos(){return this.apellidos;}

    public void setOcupacion(String o){this.ocupacion = o;}
    public String getOcupacion(){return this.ocupacion;}
    
    public String getemail() {return email;}
    public void setemail(String s) {this.email = s;}

    public void setFechanacimiento(String f){
        if(!f.isEmpty()){
            String formato = f.substring(6,10)+"-"+f.substring(3,5)+"-"+f.substring(0,2);
            this.fechanacimiento = formato;}
    }
    public String getFechanacimiento(){return this.fechanacimiento;}

    public void setSexo(String e){this.sexo = e;}
    public String getSexo(){return this.sexo;}


    public void setProvincia(String e){this.provincia = e;}
    public String getProvincia(){return this.provincia;}

    public void setNacionalidad(String e){this.nacionalidad = e;}
    public String getNacionalidad(){return this.nacionalidad;}
}

