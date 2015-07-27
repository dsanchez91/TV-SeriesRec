package Beans.Administrador;

import Beans.Serie.Actor;
import Beans.Serie.Serie;
import java.util.Vector;
import Beans.Usuarios.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class admin {
    
    private Vector fechas = new Vector();
    /* Tabla Serie */
    private int idserie;
    private String titulo;
    private int año;
    private String país;
    private String idioma;
    private String fechainicio;
    private String fechafin;
    private String genero;
    private String productora;
    private String director;  
    private String imagen; 
    
    private String directornombre;  
    
    /* Tabla productora */
    private String nombreproductora;
    private String descripcionproductora;
    
    /* Tabla género */
    private String nombregenero;
    private String descripciongenero;

    /* Tabla director */
    private String iddirector;
    private String nombredirector;
    private String apellidosdirector;
    private String fechanacdirector;
    private String biodirector;
    
    /* Tabla capitulo */
    private String idcapitulo;
    private String titulocapitulo;
    private int numerocapitulo;
    private String resumencapitulo;
    
    /* Tabla temporada */
    private int idtemporada;
    private int numerotemporada;
    private String descripciontemporada;
    private String fechainitemp;
    private String fechafintemp;
    
    /* Tabla actor */
    private String idactor;
    private String nombreactor;
    private String apellidosactor;
    private String fechanaciactor;
    private String bioactor;

    /*Serie eliminar*/
    private String serieeliminar;
    private String seriemodificar;
    private int idseriemodificar;
    private String tempmod;
    private Vector seriemod = new Vector();
    
    private String aviso;
    
    private List<Actor> identificadornombre;
    private List<String> actores;
    private Vector directores = new Vector();
    private Vector productoras = new Vector();
    
    private Vector idtempmod = new Vector();
    private Vector numerotemp = new Vector();
    private int tempeliminar;
    private int tempmodificar;
    private String capeliminar;
    private String idcapeliminar;
    private String capmodificar;
    private String idcapmodificar;
    private int num;
    private Vector capitulostemp = new Vector();
    private Vector capmod = new Vector();
    private Vector temporadamod = new Vector();
    Serie serie;
    
    //Correo
    private Properties props;
    private String from;
    private String to;
    private String subject;
    private String message;
    private String usuarionot;
   
    //Getters and setters
    public int getidserie(){return idserie;}
    public void setidserie(int s){idserie=s;}

    public String gettitulo(){return titulo;}
    public void settitulo(String s){titulo=s;}

    public String getproductora(){return productora;}
    public void setproductora(String s){productora=s;}

    public int getaño(){return año;}
    public void setaño(int s){año=s;}

    public String getpaís(){return país;}
    public void setpaís(String s){país=s;}

    public String getidioma(){return idioma;}
    public void setidioma(String s){idioma=s;}

    public String getfechainicio(){return fechainicio;}
    public void setfechainicio(String s){fechainicio=s;}

    public String getfechafin(){return fechafin;}
    public void setfechafin(String s){fechafin=s;}

    public String getgenero(){return genero;}
    public void setgenero(String s){genero=s;}

    public String getdirector(){return director;}
    public void setdirector(String s){director=s;}
    
    public String getImagen(){return imagen;}
    public void setImagen(String s){imagen=s;}
    
    public String getnombredirector(){return nombredirector;}
    public void setnombredirector(String s){nombredirector=s;}

    public String getapellidosdirector(){return apellidosdirector;}
    public void setapellidosdirector(String s){apellidosdirector=s;}

    public String getfechanacdirector(){return fechanacdirector;}
    public void setfechanacdirector(String s){fechanacdirector=s;}
    
    public String getbiodirector(){return biodirector;}
    public void setbiodirector(String s){biodirector=s;}
    
    public String getnombreproductora(){return nombreproductora;}
    public void setnombreproductora(String s){nombreproductora=s;}

    public String getdescripcionproductora(){return descripcionproductora;}
    public void setdescripcionproductora(String s){descripcionproductora=s;}
    
    public String getidactor(){return idactor;}
    public void setidactor(String s){idactor=s;}
    
    public String getnombreactor(){return nombreactor;}
    public void setnombreactor(String s){nombreactor=s;}

    public String getapellidosactor(){return apellidosactor;}
    public void setapellidosactor(String s){apellidosactor=s;}

    public String getfechanaciactor(){return fechanaciactor;}
    public void setfechanaciactor(String s){fechanaciactor=s;}

    public String getbioactor(){return bioactor;}
    public void setbioactor(String s){bioactor=s;}
    
    public String getserieeliminar(){return serieeliminar;}
    public void setserieeliminar(String s){serieeliminar=s;}
    
    public String getseriemodificar(){return seriemodificar;}
    public void setseriemodificar(String s){seriemodificar=s;}
    
    public String getAviso(){return aviso;}
    public void setAviso(String a){this.aviso=a;}

    public int getnumerotemporada(){return numerotemporada;}
    public void setnumerotemporada(int s){numerotemporada=s;}
    
    public String getdescripciontemporada(){return descripciontemporada;}
    public void setdescripciontemporada(String s){descripciontemporada=s;}
    
    public String getfechainitemp(){return fechainitemp;}
    public void setfechainitemp(String s){fechainitemp=s;}
    
    public String getfechafintemp(){return fechafintemp;}
    public void setfechafintemp(String s){fechafintemp=s;}
    
    public String gettempmod(){return tempmod;}
    public void settempmod(String s){tempmod=s;}
    
    public Vector getidtempmod(){return idtempmod;}
    public void setidtempmod(Vector s){idtempmod=s;}
    
    public Vector getnumerotemp(){return numerotemp;}
    public void setnumerotemp(Vector s){numerotemp=s;}
    
    public List<String> getactores(){   return actores;}   
    public void setactores(List<String> s){actores=s;}
    
    public int gettempeliminar(){return tempeliminar;}
    public void settempeliminar(int s){tempeliminar=s;}
    
    public int gettempmodificar(){return tempmodificar;}
    public void settempmodificar(int s){tempmodificar=s;}
    
    public String gettitulocapitulo(){return titulocapitulo;}
    public void settitulocapitulo(String s){titulocapitulo=s;}
    
    public int getnumerocapitulo(){return numerocapitulo;}
    public void setnumerocapitulo(int s){numerocapitulo=s;}
    
    public String getresumencapitulo(){return resumencapitulo;}
    public void setresumencapitulo(String s){resumencapitulo=s;}
    
    public String getcapeliminar(){return capeliminar;}
    public void setcapeliminar(String s){capeliminar=s;}
    
    public String getcapmodificar(){return capmodificar;}
    public void setcapmodificar(String s){capmodificar=s;}
    
    public String getdirectornombre(){return directornombre;}
    public void setdirectornombre(String s){directornombre=s;}
    
    
    public List<Actor> getIdentificadornombre(){         
        identificadornombre = new ArrayList<Actor>();
        Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();

            ResultSet rs = instruccion.executeQuery("SELECT * FROM Actor");
            while (rs.next()){
                Actor actor = new Actor(serie);
                actor.setnombre("Id: "+rs.getString("id")+". Nombre: "+rs.getString("nombre")+". Apellidos: "+rs.getString("apellidos"));
                identificadornombre.add(actor);
            }
            rs.close();
            instruccion.close();
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {
            System.out.println(ex);}
        
        return identificadornombre;}
    public void setIdentificadornombre(List<Actor> s){identificadornombre=s;}
    
    public Vector getIdentificadortemp(){         
        Connection conexion = null;
        numerotemp.removeAllElements();
        idtempmod.removeAllElements();

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();

            ResultSet rs = instruccion.executeQuery("SELECT * FROM Serie where Título='"+seriemodificar+"'");
            
            while (rs.next()){
              idserie=rs.getInt("id");
            }
            
            ResultSet rs2 = instruccion.executeQuery("SELECT DISTINCT Temporada_id FROM actuaen where Serie_id='"+idserie+"'");
            
            while (rs2.next()){
                System.out.println(rs2.getInt("Temporada_id"));
                idtempmod.add(rs2.getInt("Temporada_id"));
             }
            
            ResultSet rs3; 
            int a=0;
            for (Object i: idtempmod){
                rs3= instruccion.executeQuery("SELECT * FROM temporada where id='"+idtempmod.get(a)+"'");
                while (rs3.next()){
                System.out.println(rs3.getInt("Número"));
                numerotemp.add(rs3.getInt("Número"));
                a++;
                }
            }
            rs.close();
            rs2.close();
            
            instruccion.close();
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {
            System.out.println(ex);}
        
        return numerotemp;}
    public void setIdentificadortemp(Vector s){numerotemp=s;}
    
    public Vector getIdentificadorcap(){         
        Connection conexion = null;
        capitulostemp.removeAllElements();

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();

            ResultSet rs = instruccion.executeQuery("SELECT * FROM capitulo where Temporada_id='"+idtempmod.get(num)+"'");
            while (rs.next()){
                capitulostemp.add("Id: "+rs.getString("id")+". Título: "+rs.getString("Título")+". Número: "+rs.getString("Número"));
            }
            rs.close();
            instruccion.close();
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Clase Admin:. Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {
            System.out.println(ex);}
        
        return capitulostemp;
    }
    public void setIdentificadorcap(Vector s){directores=s;}
    
    public Vector getIdentificadordir(){         
        Connection conexion = null;
        directores.removeAllElements();

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();

            ResultSet rs = instruccion.executeQuery("SELECT * FROM director");
            while (rs.next()){
                directores.add(rs.getString("id")+". "+rs.getString("Nombre")+" "+rs.getString("Apellidos"));
            }
            rs.close();
            instruccion.close();
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {
            System.out.println(ex);}
        
        return directores;
    }
    public void setIdentificadordir(Vector s){directores=s;}
    
    public Vector getIdentificadorprod(){         
        Connection conexion = null;
        productoras.removeAllElements();

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();

            ResultSet rs = instruccion.executeQuery("SELECT * FROM productora");
            while (rs.next()){
                productoras.add(rs.getString("Nombre"));
            }
            rs.close();
            instruccion.close();
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
        } catch(ClassNotFoundException ex) {
            System.out.println(ex);}
        
        return productoras;
    }
    public void setIdentificadorprod(Vector s){productoras=s;}
    
    
    public String nuevaserie(){

        Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();

            ResultSet rs = instruccion.executeQuery("SELECT * FROM Serie where Título='"+seriemodificar+"'");
            
            while (rs.next()){
              idserie=rs.getInt("id");
            }

                    director=directornombre.substring(0,directornombre.indexOf("."));
                    System.out.println(director);
                    PreparedStatement ps=conexion.prepareStatement("INSERT INTO serie VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1,0);
                    ps.setString(2,titulo);
                    ps.setInt(3,año);
                    ps.setString(4,país);
                    ps.setString(5,idioma);
                    ps.setString(6,fechainicio);
                    ps.setString(7,fechafin);
                    ps.setString(8,genero);
                    ps.setString(9,productora);
                    ps.setString(10,director);
                    ps.setString(11,"NULL");

                    ps.execute();
                    ps.close();
                    conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}
        return "añadida";
    }
    
    public String nuevodirector(){

        Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");

            
                    PreparedStatement ps=conexion.prepareStatement("INSERT INTO director VALUES (?,?,?,?,?)");
                    ps.setInt(1,0);
                    ps.setString(2,nombredirector);
                    ps.setString(3,apellidosdirector);;
                    ps.setString(4,fechanacdirector);
                    ps.setString(5,biodirector);

                    ps.execute();
                    ps.close();
                    conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}
        return "añadida";
    }
    
    public String nuevaproductora(){

        Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");

            
                    PreparedStatement ps=conexion.prepareStatement("INSERT INTO productora VALUES (?,?)");
                    ps.setString(1,nombreproductora);
                    ps.setString(2,descripcionproductora);;
                    
                    ps.execute();
                    ps.close();
                    conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}
        return "añadida";
    }
    
    public String nuevoactor(){

        Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");

            
                    PreparedStatement ps=conexion.prepareStatement("INSERT INTO actor VALUES (?,?,?,?,?)");
                    ps.setInt(1,0);
                    ps.setString(2,nombreactor);
                    ps.setString(3,apellidosactor);;
                    ps.setString(4,fechanaciactor);
                    ps.setString(5,bioactor);

                    ps.execute();
                    ps.close();
                    conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}
        return "añadida";
    }
        
    public String nuevatemporada(){

        Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");

            
                    PreparedStatement ps=conexion.prepareStatement("INSERT INTO temporada VALUES (?,?,?,?,?)");
                    ps.setInt(1,0);
                    ps.setInt(2,numerotemporada);
                    ps.setString(3,descripciontemporada);
                    ps.setString(4,fechainitemp);
                    ps.setString(5,fechafintemp);
                    
                    ps.execute();
                    ps.close();
                    
                    Statement instruccion = conexion.createStatement();

                    ResultSet rs = instruccion.executeQuery("SELECT LAST_INSERT_ID()" );
                    
                    while (rs.next()){
                        idtemporada=rs.getInt(1);
                    }
                    Statement instruccion2 = conexion.createStatement();
                    
                    ResultSet rs2 = instruccion2.executeQuery("SELECT * FROM serie where Título='"+seriemodificar+"'" );
                    
                    while (rs2.next()){
                        idserie=rs2.getInt("id");
                    }
                    
                    int i=0;
                    
                    PreparedStatement ps2=conexion.prepareStatement("INSERT INTO actuaen VALUES (?,?,?)");
                    
                    for( String a: actores){
                            idactor=actores.get(i).substring(4,actores.get(i).indexOf("."));
                            ps2.setInt(1,idserie);
                            ps2.setInt(2,idtemporada);
                            ps2.setInt(3,Integer.parseInt(idactor));
                            i++;
                            ps2.execute();
                    }
                    
                    ps2.close();
                   
                    conexion.close();
                    
                    actores.clear();

            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}
       
        return "añadida";
    }
        
    public String eliminarserie(){
       Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                    PreparedStatement ps=conexion.prepareStatement("DELETE FROM serie where Título='"+serieeliminar+"' ");
                    ps.execute();
                    ps.close();
                    conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}

       return "volver";
   }

     public String buscarseriemod(){
       setAviso("");  
       Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();
            
            ResultSet rs = instruccion.executeQuery("SELECT * FROM serie where Título='"+seriemodificar+"'" );

            while(rs.next()) {
               titulo=rs.getString("Título");
               año=rs.getInt("Año");
               país=rs.getString("País");
               idioma=rs.getString("Idioma");
               fechainicio=rs.getString("Fechainicio");
               fechafin=rs.getString("Fechafin");
               genero=rs.getString("Género_nombre");
               productora=rs.getString("Productora_nombre");
               director=rs.getString("Director_id");
            }
            
                rs.close();
                instruccion.close();
                conexion.close();
                
                if (año==0) {
                setAviso("Serie no encontrada");
                return "fallo";
                }

            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}

       return "modificar";
   }
     
   public String modificarserie() throws SQLException, ClassNotFoundException{
        director=directornombre.substring(0,directornombre.indexOf("."));
        seriemod.removeAllElements();
        seriemod.add(gettitulo());
        seriemod.add(getaño());
        seriemod.add(getpaís());
        seriemod.add(getidioma());
        seriemod.add(getfechainicio());
        seriemod.add(getfechafin());
        seriemod.add(getgenero());
        seriemod.add(getproductora());
        seriemod.add(getdirector());

        for(int i=0;i<seriemod.size();i++){
            System.out.println(""+seriemod.get(i));
        }
        
        Connection conexion = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            

            if(!seriemod.get(0).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE serie SET Título='"+seriemod.get(0).toString()+"' where Título='"+seriemodificar+"'");
                instruccion1.execute();
                instruccion1.close();}

            if(!seriemod.get(1).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE serie SET Año='"+Integer.parseInt(seriemod.get(1).toString())+"' where Título='"+seriemodificar+"'");
                instruccion1.execute();
                instruccion1.close();}

            if(!seriemod.get(2).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE serie SET País='"+seriemod.get(2).toString()+"' where Título='"+seriemodificar+"'");
                instruccion1.execute();
                instruccion1.close();}

            if(!seriemod.get(3).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE serie SET Idioma='"+seriemod.get(3).toString()+"' where Título='"+seriemodificar+"'");
                instruccion2.execute();
                instruccion2.close();}

            if(!seriemod.get(4).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE serie SET Fechainicio='"+seriemod.get(4).toString()+"' where Título='"+seriemodificar+"'");
                instruccion2.execute();
                instruccion2.close();}

            if(!seriemod.get(5).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE serie SET Fechafin='"+seriemod.get(5).toString()+"' where Título='"+seriemodificar+"'");
                instruccion2.execute();
                instruccion2.close();}

            if(!seriemod.get(6).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE serie SET Género_nombre='"+seriemod.get(6).toString()+"' where Título='"+seriemodificar+"'");
                instruccion2.execute();
                instruccion2.close();}

            if(!seriemod.get(7).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE serie SET Productora_nombre='"+seriemod.get(7).toString()+"' where Título='"+seriemodificar+"'");
                instruccion2.execute();
                instruccion2.close();}
 
            if(!seriemod.get(8).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE serie SET Director_id='"+seriemod.get(8).toString()+"' where Título='"+seriemodificar+"'");
                instruccion2.execute();
                instruccion2.close();}
            
          
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos"+ex);
        }

        return "modificada";
    }
   
       public String eliminartemp() throws SQLException, ClassNotFoundException{
           
      Connection conexion = null;

      try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();
            ResultSet rs3; 
            int a=0;
            for (Object i: idtempmod){
                rs3= instruccion.executeQuery("SELECT * FROM temporada where id='"+idtempmod.get(a)+"'");
                while (rs3.next()){
                System.out.println(rs3.getInt("Número"));
                if((rs3.getInt("Número"))==tempeliminar) {
                    PreparedStatement ps=conexion.prepareStatement("DELETE FROM actuaen where Temporada_id='"+idtempmod.get(a)+"' ");
                    ps.execute();
                    ps.close(); 
                    PreparedStatement ps2=conexion.prepareStatement("DELETE FROM Temporada where id='"+idtempmod.get(a)+"' ");
                    ps2.execute();
                    ps2.close(); 
                }
                a++;
                }
            }

            conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}
    
       return "eliminada";
       
       }
       
       public String buscartempmod(){ 
       Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();
            ResultSet rs; 
            ResultSet rs2; 
            
            int a=0;
            for (Object i: idtempmod){
                rs= instruccion.executeQuery("SELECT * FROM temporada where id='"+idtempmod.get(a)+"'");
                while (rs.next()){
                System.out.println(rs.getInt("Número"));
                if((rs.getInt("Número"))==tempmodificar) {
                    rs.close();
                    rs2= instruccion.executeQuery("SELECT * FROM temporada where id='"+idtempmod.get(a)+"'");
                    num=a;
                    while(rs2.next()){
                    idtemporada=Integer.parseInt(idtempmod.get(a).toString());
                    numerotemporada=rs2.getInt("Número");
                    descripciontemporada=rs2.getString("Descripción");
                    fechainitemp=rs2.getString("Fechainicio");
                    fechafintemp=rs2.getString("Fechafin");
                    }      
                }
                a++;
                }
            }
            
                instruccion.close();
                conexion.close();
                

            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}

       return "modificar";
   }
       
     public String modificartemp() throws SQLException, ClassNotFoundException{
        temporadamod.removeAllElements();
        temporadamod.add(getnumerotemporada());
        temporadamod.add(getdescripciontemporada());
        temporadamod.add(getfechainitemp());
        temporadamod.add(getfechafintemp());
        
        Connection conexion = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            

            if(!temporadamod.get(0).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE temporada SET Número='"+Integer.parseInt(temporadamod.get(0).toString())+"' where id='"+idtemporada+"'");
                instruccion1.execute();
                instruccion1.close();}

            if(!temporadamod.get(1).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE temporada SET Descripción='"+temporadamod.get(1).toString()+"' where id='"+idtemporada+"'");
                instruccion1.execute();
                instruccion1.close();}

            if(!temporadamod.get(2).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE temporada SET Fechainicio='"+temporadamod.get(2).toString()+"' where id='"+idtemporada+"'");
                instruccion1.execute();
                instruccion1.close();}
             if(!temporadamod.get(3).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE temporada SET Fechafin='"+temporadamod.get(3).toString()+"' where id='"+idtemporada+"'");
                instruccion1.execute();
                instruccion1.close();}

            PreparedStatement ps = conexion.prepareStatement("DELETE FROM actuaen where Temporada_id='"+idtemporada+"' ");
            ps.execute();
            ps.close(); 

            PreparedStatement ps2=conexion.prepareStatement("INSERT INTO actuaen VALUES (?,?,?)");
                    
              int i=0;      
              
              for( String a: actores){
                   idactor=actores.get(i).substring(4,actores.get(i).indexOf("."));
                   ps2.setInt(1,idserie);
                   ps2.setInt(2,idtemporada);
                   ps2.setInt(3,Integer.parseInt(idactor));
                   i++;
                   ps2.execute();
              }
                    
                    ps2.close();
             
             
             
             conexion.close();
             
             actores.clear();

        } catch(SQLException ex) {
            System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos"+ex);
        }

        return "modificada";
    }
       
    public String nuevocapitulo(){

        Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");

            
                    PreparedStatement ps=conexion.prepareStatement("INSERT INTO capitulo VALUES (?,?,?,?,?)");
                    ps.setInt(1,0);
                    ps.setString(2,titulocapitulo);
                    ps.setInt(3,numerocapitulo);
                    ps.setString(4,resumencapitulo);
                    ps.setInt(5,Integer.parseInt(idtempmod.get(num).toString()));

                    ps.execute();
                    ps.close();
                    
                    Statement instruccion = conexion.createStatement();
                    ResultSet rs=instruccion.executeQuery("SELECT * FROM serie where Título='"+seriemodificar+"'");
                       
                    while (rs.next()){
                       idseriemodificar=rs.getInt("id"); 
                    }
                    rs.close();
                    instruccion.close();
                    
                    Statement instruccion2 = conexion.createStatement();
                    ResultSet rs2=instruccion2.executeQuery("SELECT * FROM sigue where Serie_id='"+idseriemodificar+"'");
            
                     while (rs2.next()){
                        usuarionot=rs2.getString("Usuario_id");
                        Statement instruccion3 = conexion.createStatement();
                        ResultSet rs3=instruccion3.executeQuery("SELECT * FROM datospersonales where Usuario_id='"+usuarionot+"'");
                        while (rs3.next()){
                           to=rs3.getString("Email");
                           from="tvseriesrec@gmail.com";
                           subject="Nuevo capítulo de "+seriemodificar;
                           message="El capítulo numero "+numerocapitulo+" titulado "+titulocapitulo+" acaba de ser añadido a nuestra base de datos";
                           sendMail();
                        }
                        rs3.close();
                        instruccion3.close();
                        
                     }
                     rs2.close();
                     instruccion2.close();
                     
                     conexion.close();
            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}
        return "añadido";
    }
    
       public String eliminarcap(){
       Connection conexion = null;
       
        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            idcapeliminar=capeliminar.substring(4,capeliminar.indexOf("."));
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                    PreparedStatement ps=conexion.prepareStatement("DELETE FROM capitulo where id='"+idcapeliminar+"' ");
                    ps.execute();
                    ps.close();
                    conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}

       return "volver";
   }
       
   public String buscarcapmod(){
       setAviso("");  
       Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            
            idcapmodificar=capmodificar.substring(4,capmodificar.indexOf("."));

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();
            
            ResultSet rs = instruccion.executeQuery("SELECT * FROM capitulo where id='"+idcapmodificar+"'" );

            while(rs.next()) {
               titulocapitulo=rs.getString("Título");
               numerocapitulo=rs.getInt("Número");
               resumencapitulo=rs.getString("Resumen");
            }
            
                rs.close();
                instruccion.close();
                conexion.close();


            } catch(SQLException ex) {
                System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}

       return "modificar";
   }
       
     public String modificarcapitulo() throws SQLException, ClassNotFoundException{
        capmod.removeAllElements();
        capmod.add(gettitulocapitulo());
        capmod.add(getnumerocapitulo());
        capmod.add(getresumencapitulo());
        
        Connection conexion = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            

            if(!capmod.get(0).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE capitulo SET Título='"+capmod.get(0).toString()+"' where id='"+idcapmodificar+"'");
                instruccion1.execute();
                instruccion1.close();}

            if(!capmod.get(1).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE capitulo SET Número='"+Integer.parseInt(capmod.get(1).toString())+"' where id='"+idcapmodificar+"'");
                instruccion1.execute();
                instruccion1.close();}

            if(!capmod.get(2).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE capitulo SET Resumen='"+capmod.get(2).toString()+"' where id='"+idcapmodificar+"'");
                instruccion1.execute();
                instruccion1.close();}

          
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Clase Admin: Hubo un problema al intentar conectarse con la base de datos"+ex);
        }

        return "modificado";
    }
    
    public String reinicio(){
    reiniciarserie();
    idcapitulo="";
    titulocapitulo="";
    numerocapitulo=0;
    resumencapitulo="";
    idtemporada=0;
    numerotemporada=0;
    descripciontemporada="";
    fechainitemp="";
    fechafintemp="";
    tempeliminar=0;
    tempmodificar=0;
    capeliminar="";
    idcapeliminar="";
    capmodificar="";
    idcapmodificar="";
    serieeliminar="";
    seriemodificar="";
     
    return"modificarserie";
    }
    
    public String reiniciarserie(){
    idserie=0;
    titulo="";
    año=0;
    país="";
    idioma="";
    fechainicio="";
    fechafin="";
    genero="";
    productora="";
    director="";  
    imagen="";  
    return "nuevoserie";
    }
    
    public String reiniciargenero(){
    nombregenero="";
    descripciongenero="";    
    return "nuevogenero";
    }
    
    public String reiniciardirector(){
    iddirector="";
    nombredirector="";
    apellidosdirector="";
    fechanacdirector="";
    biodirector="";
    return "nuevodirector";
    }
    
    public String reiniciarproductora(){
    nombreproductora="";
    descripcionproductora="";   
    return "nuevoproductora";
    }
    
    public String reiniciaractor(){
    idactor="";
    nombreactor="";
    apellidosactor="";
    fechanaciactor="";
    bioactor="";   
    return "nuevoactor";
    }
   
    
    public void sendMail() {
        
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        
        System.out.println("Entro aqui");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("tvseriesrec", "holaquetal");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(this.to));
            message.setSubject(this.subject);
            message.setText(this.message);

            Transport.send(message);

            System.out.println("Correo enviado correctamente!");


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }







}
