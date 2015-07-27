package Beans.Serie;

import Beans.Usuarios.Usuario;
import Beans.Comentarios.Comentarios;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

public class Serie implements Serializable{
    public String identificador;
    public String identificadortemp;
    public String identificadorcap;
    public String identificadoractor;
    public String identificadordirector;
    public String identificadorgenero;
    public String identificadorproductora;
    String valorselec;
    public List<DetallesSerie> lista = new ArrayList<DetallesSerie>();
    public List<DetallesSerie> listagenero = new ArrayList<DetallesSerie>();
    public List<DetallesSerie> listaseguidas;
    public List<Temporada> temporadas;
    public List<Capitulo> capitulos;
    public DetallesSerie detalles; 
    public Temporada detallestemp;
    public Actor detallesactor;
    public Director detallesdirector;
    public Genero detallesgenero;
    public Productora detallesproductora;
    public Capitulo detallescap;
    public String seriebuscar;
    public String generobuscar;
    public String seriebuscarconc;
    public String idserieseguida;
    private int idtemporadas;
    private int idactores;
    private List<Actor> actorestemp;


   
    public DetallesSerie getDetalles() {
        return detalles;
        }
    public void setDetalles(DetallesSerie d) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("detallesserie", d);
        detalles=d;
    }
    
    public Temporada getDetallestemp() {
        return detallestemp;
        }
    public void setDetallestemp(Temporada d) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("detallestemp", d);
        detallestemp=d;
    }
    
    public Capitulo getDetallescap() {
        return detallescap;
        }
    public void setDetallescap(Capitulo d) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("detallescap", d);
        detallescap=d;
    }
    
    public Actor getDetallesactor() {
        return detallesactor;
        }
    public void setDetallesactor(Actor d) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("detallesactor", d);
        detallesactor=d;
    }
    
    public Director getDetallesdirector() {
        return detallesdirector;
        }
    public void setDetallesdirector(Director d) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("detallesdirector", d);
        detallesdirector=d;
    }
    
    public Genero getDetallesgenero() {
        return detallesgenero;
        }
    public void setDetallesgenero(Genero d) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("detallesgenero", d);
        detallesgenero=d;
    }
    
    public Productora getDetallesproductora() {
        return detallesproductora;
        }
    public void setDetallesproductora(Productora d) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("detallesproductora", d);
        detallesproductora=d;
    }
    
    public void setIdentificador(String id){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("identificadorserie", id);
        identificador=id;}
    public String getIdentificador(){
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificadorserie").toString();}
    
    public void setIdentificadortemp(String id){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("identificadortemp", id);
        identificadortemp=id;}
    public String getIdentificadortemp(){
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificadortemp").toString();}
    
    public void setIdentificadorcap(String id){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("identificadorcap", id);
        identificadorcap=id;}
    public String getIdentificadorcap(){
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificadorcap").toString();}
    
    public void setIdentificadoractor(String id){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("identificadoractor", id);
        identificadoractor=id;}
    public String getIdentificadoractor(){
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificadoractor").toString();}
    
    public void setIdentificadordirector(String id){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("identificadordirector", id);
        identificadordirector=id;}
    public String getIdentificadordirector(){
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificadordirector").toString();}
    
    public void setIdentificadorgenero(String id){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("identificadorgenero", id);
        identificadorgenero=id;}
    public String getIdentificadorgenero(){
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificadorgenero").toString();}
    
    public void setIdentificadorproductora(String id){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("identificadorproductora", id);
        identificadorproductora=id;}
    public String getIdentificadorproductora(){
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificadorproductora").toString();}

    public void setValorselec(String id){
        valorselec=id;}
    public String getValorselec(){
        return valorselec;}
    
    public void setseriebuscar(String s){
        seriebuscar=s;}
    public String getseriebuscar(){
        return seriebuscar;}
    
    public void setgenerobuscar(String s){
        generobuscar=s;}
    public String getgenerobuscar(){
        return generobuscar;}
    
     public List<DetallesSerie> getLista() {
        lista.clear();
        Connection conexion = null;

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();
            
            seriebuscarconc=seriebuscar.concat("%");

            ResultSet rs;

            rs = instruccion.executeQuery("SELECT * FROM serie where Título LIKE '"+seriebuscarconc+"' ORDER BY Título asc");

            while (rs.next()){
                        DetallesSerie series = new DetallesSerie(this);
                        Director directors= new Director(this);
                        Genero generos= new Genero(this);
                        Productora productoras = new Productora (this);
                        series.setidserie(rs.getString("id"));
                        series.settitulo(rs.getString("Título"));
                        series.setaño(rs.getInt("Año"));
                        series.setpais(rs.getString("País"));
                        series.setidioma(rs.getString("Idioma"));
                        series.setfechainicio(rs.getString("Fechainicio"));
                        series.setfechafin(rs.getString("Fechafin"));
                        
                        Statement instruccion6 = conexion.createStatement();
                        ResultSet rs6 = instruccion6.executeQuery("SELECT * FROM genero where Nombre='"+rs.getString("Género_nombre")+"'");
                        
                        while(rs6.next()){
                        generos.setnombre(rs6.getString("Nombre"));
                        generos.setdescripcion(rs6.getString("Descripción"));
                        }
                        series.setgenero(generos);
                        rs6.close();
                        instruccion6.close();
                        
                        Statement instruccion7 = conexion.createStatement();
                        ResultSet rs7 = instruccion7.executeQuery("SELECT * FROM productora where Nombre='"+rs.getString("Productora_nombre")+"'");
                        
                        while(rs7.next()){
                        productoras.setnombre(rs7.getString("Nombre"));
                        productoras.setdescripcion(rs7.getString("Descripción"));
                        }
                        series.setproductora(productoras);
                        rs7.close();
                        instruccion7.close();
                        
                        
                        Statement instruccion2 = conexion.createStatement();
                        ResultSet rs2 = instruccion2.executeQuery("SELECT * FROM director where id='"+rs.getString("Director_id")+"'");
                        
                        while(rs2.next()){
                        directors.setid(rs2.getString("id"));
                        directors.setnombre(rs2.getString("Nombre"));
                        directors.setapellidos(rs2.getString("Apellidos"));
                        directors.setfechanac(rs2.getString("Fecha de nacimiento"));
                        directors.setbio(rs2.getString("Biografía"));
                        }
                        series.setdirector(directors);
                        rs2.close();
                        instruccion2.close();
                        
                        List<Comentarios> listacom = new ArrayList<Comentarios>();
                        //Recoger las puntuaciones del objeto y hayar la media:
                        int puntuacion=0;
                        int divisor=0;

                        //Recoger los comentarios
                        Statement instruccion3 = conexion.createStatement();
                        ResultSet rs3 = instruccion3.executeQuery("SELECT * FROM valoracion where Serie_id='"+series.getidserie()+"'");
                            while (rs3.next()){
                                Comentarios com = new Comentarios();
                                puntuacion=puntuacion+rs3.getInt("Puntuación");
                                divisor++;
                                com.setComentario(rs3.getString("Comentario"));
                                listacom.add(com);
                                series.setListacomentarios(listacom);
                            }
                        if(puntuacion==0)
                            series.setPuntuacion(0);
                        else
                            series.setPuntuacion(puntuacion/divisor);
                            rs3.close();
                            instruccion3.close();

                            //Recoger putuacion de usuario
                            Statement instruccion4 = conexion.createStatement();
                            ResultSet rs4 = instruccion4.executeQuery("SELECT * FROM valoracion where Serie_id='"+series.getidserie()+"' and Usuario_id='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+"'");
                            while (rs4.next()){
                                series.setPuntuacionusuario(rs4.getInt("Puntuación"));
                            }
                            rs4.close();
                            instruccion4.close();


                        lista.add(series);
            }
            rs.close();
            instruccion.close();
            conexion.close();

        } catch(SQLException ex) {System.out.println("Clase Serie: Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {System.out.println(ex);}
        
        return lista;
    }
    public void setLista(List<DetallesSerie> lista) {
        this.lista = lista;}
    
    
    
    public List<DetallesSerie> getListagenero() {
        listagenero.clear();
        Connection conexion = null;

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();
            

            ResultSet rs;

            rs = instruccion.executeQuery("SELECT * FROM serie where Género_nombre='"+generobuscar+"' ORDER BY Título asc");

            while (rs.next()){
                        DetallesSerie series = new DetallesSerie(this);
                        Director directors= new Director(this);
                        Genero generos= new Genero(this);
                        Productora productoras = new Productora (this);
                        series.setidserie(rs.getString("id"));
                        series.settitulo(rs.getString("Título"));
                        series.setaño(rs.getInt("Año"));
                        series.setpais(rs.getString("País"));
                        series.setidioma(rs.getString("Idioma"));
                        series.setfechainicio(rs.getString("Fechainicio"));
                        series.setfechafin(rs.getString("Fechafin"));
                        
                        Statement instruccion6 = conexion.createStatement();
                        ResultSet rs6 = instruccion6.executeQuery("SELECT * FROM genero where Nombre='"+rs.getString("Género_nombre")+"'");
                        
                        while(rs6.next()){
                        generos.setnombre(rs6.getString("Nombre"));
                        generos.setdescripcion(rs6.getString("Descripción"));
                        }
                        series.setgenero(generos);
                        rs6.close();
                        instruccion6.close();
                        
                        Statement instruccion7 = conexion.createStatement();
                        ResultSet rs7 = instruccion7.executeQuery("SELECT * FROM productora where Nombre='"+rs.getString("Productora_nombre")+"'");
                        
                        while(rs7.next()){
                        productoras.setnombre(rs7.getString("Nombre"));
                        productoras.setdescripcion(rs7.getString("Descripción"));
                        }
                        series.setproductora(productoras);
                        rs7.close();
                        instruccion7.close();
                        
                        
                        Statement instruccion2 = conexion.createStatement();
                        ResultSet rs2 = instruccion2.executeQuery("SELECT * FROM director where id='"+rs.getString("Director_id")+"'");
                        
                        while(rs2.next()){
                        directors.setid(rs2.getString("id"));
                        directors.setnombre(rs2.getString("Nombre"));
                        directors.setapellidos(rs2.getString("Apellidos"));
                        directors.setfechanac(rs2.getString("Fecha de nacimiento"));
                        directors.setbio(rs2.getString("Biografía"));
                        }
                        series.setdirector(directors);
                        rs2.close();
                        instruccion2.close();
                        
                        List<Comentarios> listacom = new ArrayList<Comentarios>();
                        //Recoger las puntuaciones de las series y hayar la media:
                        int puntuacion=0;
                        int divisor=0;

                        //Recoger los comentarios
                        Statement instruccion3 = conexion.createStatement();
                        ResultSet rs3 = instruccion3.executeQuery("SELECT * FROM valoracion where Serie_id='"+series.getidserie()+"'");
                            while (rs3.next()){
                                Comentarios com = new Comentarios();
                                puntuacion=puntuacion+rs3.getInt("Puntuación");
                                divisor++;
                                com.setComentario(rs3.getString("Comentario"));
                                listacom.add(com);
                                series.setListacomentarios(listacom);
                            }
                        if(puntuacion==0)
                            series.setPuntuacion(0);
                        else
                            series.setPuntuacion(puntuacion/divisor);
                            rs3.close();
                            instruccion3.close();

                            //Recoger putuacion de usuario
                            Statement instruccion4 = conexion.createStatement();
                            ResultSet rs4 = instruccion4.executeQuery("SELECT * FROM valoracion where Serie_id='"+series.getidserie()+"' and Usuario_id='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+"'");
                            while (rs4.next()){
                                series.setPuntuacionusuario(rs4.getInt("Puntuación"));
                            }
                            rs4.close();
                            instruccion4.close();


                        listagenero.add(series);
            }
            rs.close();
            instruccion.close();
            conexion.close();

        } catch(SQLException ex) {System.out.println("Clase Serie: Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {System.out.println(ex);}
        
        return listagenero;
    }
    public void setListagenero(List<DetallesSerie> lista) {
        this.listagenero = lista;}
    
    
  public List<DetallesSerie> getListaseguidas() {
        listaseguidas = new ArrayList<DetallesSerie>();
        Connection conexion = null;

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();
            

            ResultSet rs;

            rs = instruccion.executeQuery("SELECT * FROM sigue where Usuario_id='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+"'");

            while (rs.next()){
                idserieseguida=rs.getString("Serie_id");
            
        
            
            Statement instruccion2 = conexion.createStatement();
            
            ResultSet rs2;

            rs2 = instruccion2.executeQuery("SELECT * FROM serie where id='"+idserieseguida+"'ORDER BY Título asc");
            
            while (rs2.next()){
                        DetallesSerie series = new DetallesSerie(this);
                        Director directors= new Director(this);
                        Genero generos= new Genero(this);
                        Productora productoras = new Productora (this);
                        series.setidserie(rs2.getString("id"));
                        series.settitulo(rs2.getString("Título"));
                        series.setaño(rs2.getInt("Año"));
                        series.setpais(rs2.getString("País"));
                        series.setidioma(rs2.getString("Idioma"));
                        series.setfechainicio(rs2.getString("Fechainicio"));
                        series.setfechafin(rs2.getString("Fechafin"));;
                        
                        Statement instruccion6 = conexion.createStatement();
                        ResultSet rs6 = instruccion6.executeQuery("SELECT * FROM genero where Nombre='"+rs2.getString("Género_nombre")+"'");
                        
                        while(rs6.next()){
                        generos.setnombre(rs6.getString("Nombre"));
                        generos.setdescripcion(rs6.getString("Descripción"));
                        }
                        series.setgenero(generos);
                        rs6.close();
                        instruccion6.close();
                        
                        Statement instruccion7 = conexion.createStatement();
                        ResultSet rs7 = instruccion7.executeQuery("SELECT * FROM productora where Nombre='"+rs2.getString("Productora_nombre")+"'");
                        
                        while(rs7.next()){
                        productoras.setnombre(rs7.getString("Nombre"));
                        productoras.setdescripcion(rs7.getString("Descripción"));
                        }
                        series.setproductora(productoras);
                        rs7.close();
                        instruccion7.close();
                        
                        Statement instruccion3 = conexion.createStatement();
                        ResultSet rs3 = instruccion3.executeQuery("SELECT * FROM director where id='"+rs2.getString("Director_id")+"'");
                        
                        while(rs3.next()){
                        directors.setid(rs3.getString("id"));
                        directors.setnombre(rs3.getString("Nombre"));
                        directors.setapellidos(rs3.getString("Apellidos"));
                        directors.setfechanac(rs3.getString("Fecha de nacimiento"));
                        directors.setbio(rs3.getString("Biografía"));
                        }
                        series.setdirector(directors);
                        rs3.close();
                        instruccion3.close();
                        
                        List<Comentarios> listacom = new ArrayList<Comentarios>();
                        //Recoger las puntuaciones de la serie y hayar la media:
                        int puntuacion=0;
                        int divisor=0;

                        //Recoger los comentarios
                        Statement instruccion4 = conexion.createStatement();
                        ResultSet rs4 = instruccion4.executeQuery("SELECT * FROM valoracion where Serie_id='"+series.getidserie()+"'");
                            while (rs4.next()){
                                Comentarios com = new Comentarios();
                                puntuacion=puntuacion+rs4.getInt("Puntuación");
                                divisor++;
                                com.setComentario(rs4.getString("Comentario"));
                                listacom.add(com);
                                series.setListacomentarios(listacom);
                            }
                        if(puntuacion==0)
                            series.setPuntuacion(0);
                        else
                            series.setPuntuacion(puntuacion/divisor);
                            rs4.close();
                            instruccion4.close();

                            //Recoger putuacion de usuario
                            Statement instruccion5 = conexion.createStatement();
                            ResultSet rs5 = instruccion5.executeQuery("SELECT * FROM valoracion where Serie_id='"+series.getidserie()+"' and Usuario_id='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+"'");
                            while (rs5.next()){
                                series.setPuntuacionusuario(rs5.getInt("Puntuación"));
                            }
                            rs5.close();
                            instruccion5.close();


                        listaseguidas.add(series);
            }
            rs2.close();
            instruccion2.close();
            }
            rs.close();
            instruccion.close();

            conexion.close();

        } catch(SQLException ex) {System.out.println("Clase Serie: Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {System.out.println(ex);}
        
        return listaseguidas;
    }
    public void setListaseguidas(List<DetallesSerie> lista) {
        this.listaseguidas = lista;}    
    
    
   public List<Temporada> getTemporadas() {
        temporadas = new ArrayList<Temporada>();
        Connection conexion = null;

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();


            ResultSet rs = instruccion.executeQuery("SELECT DISTINCT Temporada_id FROM actuaen where Serie_id='"+identificador+"'");
            
            while (rs.next()){
                idtemporadas=rs.getInt("Temporada_id");
             
            Statement instruccion2 = conexion.createStatement();
            ResultSet rs2; 
            

            rs2= instruccion2.executeQuery("SELECT * FROM temporada where id='"+idtemporadas+"'");
           
         
            while (rs2.next()){
                Temporada temporada=new Temporada(this);
                temporada.setid(rs2.getString("id"));
                temporada.setnumero(rs2.getInt("Número"));
                temporada.setdescripcion(rs2.getString("Descripción"));
                temporada.setfechaini(rs2.getString("Fechainicio"));
                temporada.setfechafin(rs2.getString("Fechafin"));
                
                actorestemp= new ArrayList<Actor>();
                        
            Statement instruccion3 = conexion.createStatement();
            ResultSet rs3; 
            

            rs3= instruccion3.executeQuery("SELECT * FROM actuaen where Temporada_id='"+idtemporadas+"'");
             
            while (rs3.next()){
                idactores=rs3.getInt("Actor_id");
                
                Statement instruccion4 = conexion.createStatement();
                ResultSet rs4; 
                rs4= instruccion4.executeQuery("SELECT * FROM actor where id='"+idactores+"'");
                while (rs4.next()){ 
                    Actor actor = new Actor(this);
                    actor.id=rs4.getString("id");
                    actor.nombre=rs4.getString("Nombre");
                    actor.apellidos=rs4.getString("Apellidos");
                    actor.fechanac=rs4.getString("Fecha de Nacimiento");
                    actor.bio=rs4.getString("Biografía");
                    actorestemp.add(actor);
                }
                rs4.close();
                instruccion4.close();
            
            }
            temporada.setactores(actorestemp);
            temporadas.add(temporada);
            rs3.close();
            instruccion3.close();
            }
            rs2.close();
            instruccion2.close();
           
            }

            rs.close();
            instruccion.close();
            conexion.close();

        } catch(SQLException ex) {System.out.println("Clase Serie: Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {System.out.println(ex);}
        
        return temporadas;
    }
    public void setTemporadas(List<Temporada> lista) {
        this.temporadas = lista;}
    
    
    public List<Capitulo> getCapitulos() {
        capitulos = new ArrayList<Capitulo>();
        Connection conexion = null;

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();


            ResultSet rs = instruccion.executeQuery("SELECT * FROM capitulo where Temporada_id='"+identificadortemp+"'");
            
            while (rs.next()){
                Capitulo capitulo=new Capitulo(this);
                capitulo.setid(rs.getString("id"));
                capitulo.settitulo(rs.getString("Título"));
                capitulo.setnumero(rs.getInt("Número"));
                capitulo.setresumen(rs.getString("Resumen"));
            
                capitulos.add(capitulo);
            }

            rs.close();
            instruccion.close();
            conexion.close();

        } catch(SQLException ex) {System.out.println("Clase Serie: Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {System.out.println(ex);}
        
        return capitulos;
    }
    public void setCapitulos(List<Capitulo> lista) {
        this.capitulos = lista;}
    

    public void establecerSerie(DetallesSerie d){
        setIdentificador(d.getidserie());
        detalles=d;
    }
    
    public void establecerTemporada(Temporada d){
        setIdentificadortemp(d.getid());
        detallestemp=d;
    }
    
    public void establecerCapitulo(Capitulo d){
        setIdentificadorcap(d.getid());
        detallescap=d;
    }
    
    public void establecerActor(Actor d){
        setIdentificadoractor(d.getid());
        detallesactor=d;
    }
    
    public void establecerDirector(Director d){
        setIdentificadordirector(d.getid());
        detallesdirector=d;
    }
    
    public void establecerGenero(Genero d){
        setIdentificadorgenero(d.getnombre());
        detallesgenero=d;
    }
    
    public void establecerProductora(Productora d){
        setIdentificadorproductora(d.getnombre());
        detallesproductora=d;
    }










}
