package Beans.Serie;

import java.io.Serializable;
import java.text.ParseException;

/**
 *
 * @author Diego Sánchez
 */
public class Productora implements Serializable{
    public String nombre;
    public String descripcion;
    
    Serie serie;
        
    public Productora(Serie a){
        serie=a;}
    
    public String getnombre(){return nombre;}
    public void setnombre(String s){nombre=s;}

    public String getdescripcion(){return descripcion;}
    public void setdescripcion(String s){descripcion=s;}
    
    public String paginaProductora() throws ParseException{
        serie.establecerProductora(this);
        return "productora";
    }
    
}
