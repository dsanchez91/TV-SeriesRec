/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodología;

import Beans.Serie.*;
import java.io.Serializable;
import java.text.ParseException;

/**
 *
 * @author Diego Sánchez
 */
public class Genero implements Serializable{
    public String nombre;
    public String descripcion;
    
    Recomendacion rec;
        
    public Genero(Recomendacion a){
        rec=a;}
    
    public String getnombre(){return nombre;}
    public void setnombre(String s){nombre=s;}

    public String getdescripcion(){return descripcion;}
    public void setdescripcion(String s){descripcion=s;}
    
    public String paginaGenero() throws ParseException{
        rec.establecerGenero(this);
        return "genero";
    }
    
}
