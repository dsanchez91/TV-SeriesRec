package Metodología;
import Beans.Comentarios.Comentarios;

import Beans.Usuarios.Usuario;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

public class Recomendacion implements Serializable{
    public String identificador;
    public String identificadortemp;
    public String identificadorcap;
    public String identificadoractor;
    public String identificadordirector;
    public String identificadorgenero;
    public String identificadorproductora;
    public Temporada detallestemp;
    public Actor detallesactor;
    public Director detallesdirector;
    public Genero detallesgenero;
    public Productora detallesproductora;
    public Capitulo detallescap;
    public String generorec;
    public List<DetallesRecomendacion> lista;
    private DetallesRecomendacion detalles;
    private Vector<String> seriesrecomendadas;
    public List<Temporada> temporadas;
    public List<Capitulo> capitulos;
    private int idtemporadas;
    private int idactores;
    private List<Actor> actorestemp;

     
     public DetallesRecomendacion getDetalles() {
        return detalles;}
     public void setDetalles(DetallesRecomendacion d) {
        this.detalles = d;}
     
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
     
     public List<DetallesRecomendacion> getLista() {
        lista = new ArrayList<DetallesRecomendacion>();
        lista.clear();
        if(seriesrecomendadas==null)seriesrecomendadas= new Vector<String>();
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

            //Recorrer la variables listadopuntosinteresrecomendados
            for(int i=0;i<seriesrecomendadas.size();i++){

                /** Para especificar recomendacion solo de algún tipo de punto de interes: **/
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString().equals("Todos")){
                    rs = instruccion.executeQuery("SELECT * FROM serie where id='"+seriesrecomendadas.get(i).toString()+"'");
                }
                else {
                    //Comprobar el tipo antes de hacer la consulta:
                    if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString().equals("Acción")){
                        //Series de acción
                        rs = instruccion.executeQuery("SELECT * FROM serie where Género_nombre='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString()+"' and id='"+seriesrecomendadas.get(i).toString()+"'");}
                    else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString().equals("Ciencia ficcion")){
                        //Series de ciencia ficcion
                        rs = instruccion.executeQuery("SELECT * FROM serie where Género_nombre='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString()+"' and id='"+seriesrecomendadas.get(i).toString()+"'");}
                    else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString().equals("Comedia")){
                        //Series de Comedia
                        rs = instruccion.executeQuery("SELECT * FROM serie where Género_nombre='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString()+"' and id='"+seriesrecomendadas.get(i).toString()+"'");}
                    else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString().equals("Drama")){
                        //Series de Drama
                        rs = instruccion.executeQuery("SELECT * FROM serie where Género_nombre='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString()+"' and id='"+seriesrecomendadas.get(i).toString()+"'");}
                    else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString().equals("Infantil")){
                        //Series infantiles
                        rs = instruccion.executeQuery("SELECT * FROM serie where Género_nombre='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString()+"' and id='"+seriesrecomendadas.get(i).toString()+"'");}
                    else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString().equals("Intriga")){
                        //Series de intriga
                        rs = instruccion.executeQuery("SELECT * FROM serie where Género_nombre='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString()+"' and id='"+seriesrecomendadas.get(i).toString()+"'");}
                    else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString().equals("Histórica")){
                        //Series históricas
                        rs = instruccion.executeQuery("SELECT * FROM serie where Género_nombre='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString()+"' and id='"+seriesrecomendadas.get(i).toString()+"'");}
                    else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString().equals("Histórica")){
                        //Series históricas
                        rs = instruccion.executeQuery("SELECT * FROM serie where Género_nombre='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString()+"' and id='"+seriesrecomendadas.get(i).toString()+"'");}
                    else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString().equals("Romance")){
                        //Series históricas
                        rs = instruccion.executeQuery("SELECT * FROM serie where Género_nombre='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString()+"' and id='"+seriesrecomendadas.get(i).toString()+"'");}
                     else {
                        //Series terror
                        rs = instruccion.executeQuery("SELECT * FROM serie where Género_nombre='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiporecomendacion").toString()+"' and id='"+seriesrecomendadas.get(i).toString()+"'");}
                    }
                    while (rs.next()){
                    DetallesRecomendacion recomendado = new DetallesRecomendacion(this);
                    
                    Director directors= new Director(this);
                    Genero generos= new Genero(this);
                    Productora productoras = new Productora (this);
                    recomendado.setidserie(rs.getString("id"));
                    //Valores de los DETALLES:
                    recomendado.settitulo(rs.getString("Título"));
                    recomendado.setaño(rs.getInt("Año"));
                    recomendado.setpais(rs.getString("País"));
                    recomendado.setidioma(rs.getString("Idioma"));
                    recomendado.setfechainicio(rs.getString("Fechainicio"));
                    recomendado.setfechafin(rs.getString("Fechafin"));
                        
                        Statement instruccion6 = conexion.createStatement();
                        ResultSet rs6 = instruccion6.executeQuery("SELECT * FROM genero where Nombre='"+rs.getString("Género_nombre")+"'");
                        
                        while(rs6.next()){
                        generos.setnombre(rs6.getString("Nombre"));
                        generos.setdescripcion(rs6.getString("Descripción"));
                        }
                        recomendado.setgenero(generos);
                        rs6.close();
                        instruccion6.close();
                        
                        Statement instruccion7 = conexion.createStatement();
                        ResultSet rs7 = instruccion7.executeQuery("SELECT * FROM productora where Nombre='"+rs.getString("Productora_nombre")+"'");
                        
                        while(rs7.next()){
                        productoras.setnombre(rs7.getString("Nombre"));
                        productoras.setdescripcion(rs7.getString("Descripción"));
                        }
                        recomendado.setproductora(productoras);
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
                        recomendado.setdirector(directors);
                        rs2.close();
                        instruccion2.close();

                    /**__________________ VALORACION __________________________**/
                    List<Comentarios> listacom = new ArrayList<Comentarios>();
                    //Recoger las puntuaciones del objeto y hayar la media:
                    int puntuacion=0;
                    int divisor=0;
                    //Recoger los comentarios
                    Statement instruccion3 = conexion.createStatement();
                    ResultSet rs3 = instruccion3.executeQuery("SELECT * FROM valoracion where Serie_id='"+recomendado.getidserie()+"'");
                    while (rs3.next()){
                        Comentarios com = new Comentarios();
                        com.setComentario(rs3.getString("Comentario"));
                        listacom.add(com);
                        recomendado.setListacomentarios(listacom);
                        puntuacion=puntuacion+rs3.getInt("Puntuación");
                        divisor++;
                    }
                    if(divisor!=0)
                        recomendado.setPuntuacion(puntuacion/divisor);
                    rs3.close();
                    instruccion3.close();

                   

                        lista.add(recomendado);
                }
                rs.close();
            

            }
            instruccion.close();
            conexion.close();
        } catch(SQLException ex) {System.out.println("Clase:Recomendacionnn. Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {System.out.println(ex);}
        
        return lista;
        
     }
    public void setLista(List<DetallesRecomendacion> list) {
        this.lista = list;}
    
    public void setIdentificador(String id){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("identificadorrecomendacion", id);
        identificador=id;}
    public  String getIdentificador(){
        if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador")!=null)
            identificador=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString();
        return identificador;}
    
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

        } catch(SQLException ex) {System.out.println("Clase:Serie.Hubo un problema al intentar conectarse con la base de datos: "+ex);
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

        } catch(SQLException ex) {System.out.println("Clase:Serie.Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {System.out.println(ex);}
        
        return capitulos;
    }
    public void setCapitulos(List<Capitulo> lista) {
        this.capitulos = lista;}
    
   public  void setGenerorec(String id){
        generorec=id;}
    public String getGenerorec(){
        return generorec;}
    
    public Vector<String> getseriesrecomendadas() {
        return seriesrecomendadas;}
    public void setseriesrecomendadas(Vector<String> l) {
        this.seriesrecomendadas = l;}
    
    
    public void establecerRecomendacion(DetallesRecomendacion d) {
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
    
    public String recomendar(){
        /** Ejecutamos la clase que realiza la recomendación
         * obteniendo los puntos de interes recomendados **/
        seriesrecomendadas= new Vector<String>();

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tiporecomendacion", "Todos");

        Runtime r = new Runtime();
        for(int i=0;i<r.getSeriesrecomendadas().size();i++){
            seriesrecomendadas.add(r.getSeriesrecomendadas().get(i).toString());
        System.out.println(r.getSeriesrecomendadas().get(i).toString());
        }

        return "recomendacion";
    }
    
    public String recomendargen(){

        seriesrecomendadas= new Vector<String>();

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tiporecomendacion", generorec);

        Runtime r = new Runtime();
        for(int i=0;i<r.getSeriesrecomendadas().size();i++){
            seriesrecomendadas.add(r.getSeriesrecomendadas().get(i).toString());
        System.out.println(r.getSeriesrecomendadas().get(i).toString());
        }

        return "recomendacion";
    }
    
     public String algoritmo(){
        /** Ejecutamos la clase que realiza el algoritmo de recomendación off-line **/
        Recommender r = new Recommender();

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fondocolor", "fondobotonrojo");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("texto","El algoritmo se ha ejecutado correctamente, puede volver a ejecutarlo manualmente de nuevo cuando quiera.");

        return "algoritmo";
    }
    
    
    
}
