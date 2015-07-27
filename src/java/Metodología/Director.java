package Metodología;

import Beans.Serie.*;
import java.io.Serializable;
import java.text.ParseException;


public class Director implements Serializable{
        /* Tabla actor */
    public String id;
    public String nombre;
    public String apellidos;
    public String fechanac;
    public String bio;
    
    Recomendacion rec;
        
    public Director(Recomendacion a){
        rec=a;}
    
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
    
    public String paginaDirector() throws ParseException{
        rec.establecerDirector(this);
        return "director";
    }
}
