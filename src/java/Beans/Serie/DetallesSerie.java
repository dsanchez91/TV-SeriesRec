package Beans.Serie;

import Beans.Comentarios.Comentarios;
import Beans.Usuarios.Usuario;
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

public class DetallesSerie implements Serializable{
    private String idserie;
    private String titulo;
    private int año;
    private String pais;
    private String idioma;
    private String fechainicio;
    private String fechafin;
    Genero genero;
    Productora productora;
    Director director;  
    private String imagen;  
    
    private int puntuacion=0;
    private int puntuacionusuario=0;
    private String nuevocomentario;
    private String puntuado="Puntuar";
    private String seguida=comprobarseguida(); 
    private String imagenseguida="seguir";
    int valor=0;
    
    private List<Comentarios> listacomentarios;
    
    Serie serie;
        
    public DetallesSerie(Serie a){
        serie=a;}
    
    public String getidserie(){return idserie;}
    public void setidserie(String s){idserie=s;}

    public String gettitulo(){return titulo;}
    public void settitulo(String s){titulo=s;}

    public Productora getproductora(){return productora;}
    public void setproductora(Productora s){productora=s;}

    public int getaño(){return año;}
    public void setaño(int s){año=s;}

    public String getpais(){return pais;}
    public void setpais(String s){pais=s;}

    public String getidioma(){return idioma;}
    public void setidioma(String s){idioma=s;}

    public String getfechainicio(){return fechainicio;}
    public void setfechainicio(String s){fechainicio=s;}

    public String getfechafin(){return fechafin;}
    public void setfechafin(String s){fechafin=s;}

    public Genero  getgenero(){return genero;}
    public void setgenero(Genero  s){genero=s;}

    public Director getdirector(){return director;}
    public void setdirector(Director s){director=s;}
    
    public String getImagen(){return imagen;}
    public void setImagen(String s){imagen=s;}
    
    public List<Comentarios> getListacomentarios() {
        return listacomentarios;}
    public void setListacomentarios(List<Comentarios> list) {
        this.listacomentarios = list;}
    
    public void setPuntuacion(int p){
        puntuacion=p;}
    public int getPuntuacion(){
        return puntuacion;}

    public void setPuntuacionusuario(int id){
        puntuacionusuario=id;}
    public int getPuntuacionusuario(){
        return puntuacionusuario;}
    
    public void setseguida(String img){
        seguida=img;}
    public String getseguida(){
        seguida=comprobarseguida();
        return seguida;}
    
    public void setValor(int v){
        valor=v;}
    public int getValor(){
        return valor;}
    
    public void setPuntuado(String p){
        puntuado=p;}
    public String getPuntuado(){
        return puntuado;}
    
    public void setNuevocomentario(String c){
        nuevocomentario=c;}
    public String getNuevocomentario(){
        return nuevocomentario;}
    
    public void setimagenseguida(String c){
        imagenseguida=c;}
    public String getimagenseguida(){
        return imagenseguida;}
    
    
    public void reiniciar(){
        idserie="";
        titulo="";
        año=0;
        pais="";
        idioma="";
        fechainicio="";
        fechafin="";
        //genero="";
        //productora="";
        //director.clear();
        imagen="";  
    }
    
    public String paginaserie() throws ParseException{
        serie.establecerSerie(this);
        almacenarvisita(serie.getIdentificador());
        comprobarseguida();
        return "serie";
    }
    
     public void almacenarvisita(String id) throws ParseException{
            //Almacenar en la base de datos la visita del usuario

            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString().length()>0){
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

                int idacceso=0;
                ResultSet rs = instruccion.executeQuery("SELECT * FROM acceso ORDER BY id desc limit 1");
                while (rs.next()){
                    idacceso=rs.getInt("id")+1;
                }

                rs.close();

                PreparedStatement ps=conexion.prepareStatement("INSERT INTO acceso VALUES (?,?,?,?)");
                ps.setInt(1,idacceso);
                ps.setString(2,id);
                ps.setString(3,FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString());
                Calendar c = new GregorianCalendar();
                String dia = Integer.toString(c.get(Calendar.DATE));
                if(dia.length()==1)
                    dia="0"+dia;
                String mes = Integer.toString(c.get(Calendar.MONTH)+1);
                if(mes.length()==1)
                    mes="0"+mes;
                String annio = Integer.toString(c.get(Calendar.YEAR));

                //Trabajar con DATE.
                java.sql.Date fecha = java.sql.Date.valueOf(annio+"-"+mes+"-"+dia);
                ps.setDate(4,fecha);

                ps.execute();
                ps.close();

            instruccion.close();
            conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase DetallesSerie: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}
             }
        }
     
       public String comprobarseguida(){
        Connection conexion = null;

            /**Lo añadimos a la base de datos **/
             try {
                    try {
                        Class.forName("com.mysql.jdbc.Driver").newInstance();}
                    catch (InstantiationException ex) {
                        Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
                    catch (IllegalAccessException ex) {
                        Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

                    conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                    
                    Statement instruccion = conexion.createStatement();
                    ResultSet rs = instruccion.executeQuery("SELECT * FROM sigue where Usuario_id='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+"' and Serie_id='"+idserie+"'");
                    while (rs.next()){
                    imagenseguida="dejardeseguir";
                            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
                            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                                return "Dejar de seguir";
                            else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Inglés"))
                                return "Unfollow";}
                        else return "Dejar de seguir";
                        }

                rs.close();
                conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase:Detalles serie.Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}

        imagenseguida="seguir";
        if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
           if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                return "Seguir";
            else 
                return "Follow";}
        else return "Seguir";
    }
       
       public String seguir(){

        //Seguir
        if(seguida.equals("Seguir") || seguida.equals("Follow")){
            imagenseguida="dejardeseguir";
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                    seguida="Dejar de seguir";
                else 
                   seguida="Unfollow";
                }
                else seguida="Dejar de seguir";

            Connection conexion = null;
            
            /**Lo añadimos a la base de datos **/
             try {
                    try {
                        Class.forName("com.mysql.jdbc.Driver").newInstance();}
                    catch (InstantiationException ex) {
                        Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
                    catch (IllegalAccessException ex) {
                        Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

                    conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                    
                    PreparedStatement ps=conexion.prepareStatement("INSERT INTO sigue VALUES (?,?,?)");
                    //Vamos a poner como idsigue: idusuario-idobjeto
                    ps.setString(1,FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+""+idserie);
                    ps.setString(3,idserie);
                    ps.setString(2,FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString());

                    ps.execute();
                    ps.close();
                    conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase:DetallesSerie.Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}
        }
        /**_________ELIMINAR_________**/
        else{
            imagenseguida="seguir";
               if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
                 if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                    seguida="Seguir";
                else 
                    seguida="Follow";
                }
            else seguida="Seguir";
            Connection conexion = null;

            /**Lo eliminamos a la base de datos **/
             try {
                    try {
                        Class.forName("com.mysql.jdbc.Driver").newInstance();}
                    catch (InstantiationException ex) {
                        Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
                    catch (IllegalAccessException ex) {
                        Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

                    conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                    
                    PreparedStatement ps=conexion.prepareStatement("DELETE FROM sigue where Usuario_id='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+"' and Serie_id='"+idserie+"'");
                    ps.execute();
                    ps.close();
                    conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase DetallesSerie: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}
        }

        return "modificacion";
    }
       
       public String puntuar(){
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

                int auxiliar=1;
                ResultSet rs = instruccion.executeQuery("SELECT * FROM valoracion where Usuario_id='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+"' and Serie_id='"+idserie+"'");
                while (rs.next()){
                    auxiliar=0;}

                rs.close();

                if(auxiliar==1){
                    PreparedStatement ps=conexion.prepareStatement("INSERT INTO valoracion VALUES (?,?,?,?)");
                    ps.setString(1,idserie);
                    ps.setString(2,FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString());
                    ps.setInt(3,valor);
                    ps.setString(4,"");

                    ps.execute();
                    ps.close();
                }
                else{
                    PreparedStatement ps = conexion.prepareStatement("UPDATE valoracion SET Puntuación="+valor+" WHERE Serie_id='"+idserie+"' and Usuario_id='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+"' ");
                    ps.execute();
                    ps.close();
                }

                /**__________________ VALORACION __________________________-**/
                //Recoger las puntuaciones del objeto y hayar la media:
                int punt=0;
                int divisor=0;

                Statement instruccion3 = conexion.createStatement();
                ResultSet rs3 = instruccion3.executeQuery("SELECT * FROM valoracion where Serie_id='"+idserie+"'");
                while (rs3.next()){
                    punt=punt+rs3.getInt("Puntuación");
                    divisor++;
                }
                if(divisor!=0)
                    puntuacion=(punt/divisor);
                rs3.close();
                instruccion3.close();

            instruccion.close();
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Clase DetallesSerie: Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {
            System.out.println(ex);}

            puntuacionusuario=valor;
            return "serie";
        }
       
         public String guardarcomentario(){
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

                int auxiliar=1;
                ResultSet rs = instruccion.executeQuery("SELECT * FROM valoracion where Usuario_id='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+"' and Serie_id='"+idserie+"'");
                while (rs.next()){
                    auxiliar=0;}

                rs.close();

                if(auxiliar==1){
                    PreparedStatement ps=conexion.prepareStatement("INSERT INTO valoracion VALUES (?,?,?,?)");
                    ps.setString(1,idserie);
                    ps.setString(2,FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString());
                    ps.setInt(3,valor);
                    ps.setString(4,FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+":  " + nuevocomentario );

                    ps.execute();
                    ps.close();
                }
                else{
                    PreparedStatement ps = conexion.prepareStatement("UPDATE valoracion SET Comentario='"+nuevocomentario+"' WHERE Serie_id='"+idserie+"' and Usuario_id='"+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString()+"' ");
                    ps.execute();
                    ps.close();
                }

            instruccion.close();
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Clase DetallesSerie: Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {
            System.out.println(ex);}

            return "serie";
        }
    
}
