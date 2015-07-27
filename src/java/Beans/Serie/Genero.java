/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans.Serie;

import java.io.Serializable;
import java.text.ParseException;

/**
 *
 * @author Diego Sánchez
 */
public class Genero implements Serializable{
    public String nombre;
    public String descripcion;
    
    Serie serie;
        
    public Genero(Serie a){
        serie=a;}
    
    public String getnombre(){return nombre;}
    public void setnombre(String s){nombre=s;}

    public String getdescripcion(){return descripcion;}
    public void setdescripcion(String s){descripcion=s;}
    
    public String paginaGenero() throws ParseException{
        serie.establecerGenero(this);
        return "genero";
    }
    
}
