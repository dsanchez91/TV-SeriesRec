package Metodología;

import Beans.Serie.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Vector;
import java.util.List;

public class Capitulo implements Serializable{
    private String id;
    private String titulo;
    private int numero;
    private String resumen;

    
    Recomendacion rec;
        
    public Capitulo(Recomendacion a){
        rec=a;}
    
    public int getnumero(){return numero;}
    public void setnumero(int s){numero=s;}
    
    public String getresumen(){return resumen;}
    public void setresumen(String s){resumen=s;}
    
    public String gettitulo(){return titulo;}
    public void settitulo(String s){titulo=s;}

    public String getid(){return id;}
    public void setid(String s){id=s;}

    
   public String paginaCapitulo() throws ParseException{
        rec.establecerCapitulo(this);
        return "serie";
    }
    
}
