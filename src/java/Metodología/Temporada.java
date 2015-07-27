package Metodología;

import Beans.Serie.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Vector;
import java.util.List;

public class Temporada implements Serializable{
    private String id;
    private int numero;
    private String descripcion;
    private String fechaini;
    private String fechafin;
    private List<Actor> actores;
    
    Recomendacion rec;
        
    public Temporada(Recomendacion a){
        rec=a;}
    
    public int getnumero(){return numero;}
    public void setnumero(int s){numero=s;}
    
    public String getdescripcion(){return descripcion;}
    public void setdescripcion(String s){descripcion=s;}
    
    public String getfechaini(){return fechaini;}
    public void setfechaini(String s){fechaini=s;}
    
    public String getfechafin(){return fechafin;}
    public void setfechafin(String s){fechafin=s;}
    
    public String getid(){return id;}
    public void setid(String s){id=s;}
    
    public List<Actor> getactores(){return actores;}
    public void setactores(List<Actor> s){actores=s;}
    
   public String paginaTemporada() throws ParseException{
        rec.establecerTemporada(this);
        return "serie";
    }
    
}
