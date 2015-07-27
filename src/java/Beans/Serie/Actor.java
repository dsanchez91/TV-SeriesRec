package Beans.Serie;

import java.io.Serializable;
import java.text.ParseException;


public class Actor implements Serializable{
        /* Tabla actor */
    public String id;
    public String nombre;
    public String apellidos;
    public String fechanac;
    public String bio;
    
    Serie serie;
        
    public Actor(Serie a){
        serie=a;}
    
    public String getid(){return id;}
    public void setid(String s){id=s;}
    
    public String getnombre(){return nombre;}
    public void setnombre(String s){nombre=s;}

    public String getapellidos(){return apellidos;}
    public void setapellidos(String s){apellidos=s;}

    public String getfechanac(){return fechanac;}
    public void setfechanac(String s){fechanac=s;}

    public String getbio(){return bio;}
    public void setbio(String s){bio=s;}
    
    public String paginaActor() throws ParseException{
        serie.establecerActor(this);
        return "actor";
    }
}
