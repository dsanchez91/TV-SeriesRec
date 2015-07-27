package Beans.Usuarios;
import java.io.Serializable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Diego Sánchez
 */
public class Usuario implements Serializable{
    public String identificador;
    private String identificadoraux;
    private String contraseña;
    private String contraseñanueva;
    private String contraseñaaux;
    private String imagen;
    private Vector datosusuario = new Vector();
    private DatosPersonales datos = new DatosPersonales();
    private Intereses intereses = new Intereses();
    private Formulario formulario = new Formulario(this);
    private String valido ="valido.png";
    private String ideliminar="";

    public Usuario(){}
    
    public String getIdentificador() { return identificador;}
    public void setIdentificador(String id) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("identificador", id);
        identificador = id;}
    
    public String getIdentificadoraux() {return identificadoraux;}
    public void setIdentificadoraux(String id) {this.identificadoraux = id;}
    
    public String getContraseña() {return contraseña;}
    public void setContraseña(String c) {this.contraseña = c;}
    
    public String getcontraseñaaux() {return contraseñaaux;}
    public void setcontraseñaaux(String c) {this.contraseñaaux = c;}
    
    public String getContraseñanueva() {return contraseñanueva;}
    public void setContraseñanueva(String c) {this.contraseñanueva = c;}
    
    public DatosPersonales getDatosPersonales(){return datos;}
    public void setDatosPersonales(DatosPersonales d){datos=d;}
    
    public Intereses getIntereses(){return intereses;}
    public void setIntereses(Intereses i){intereses=i;}
    
    public String getImagen() {return imagen;}
    public void setImagen(String im) {this.imagen = im;}
    
    public Formulario getFormulario(){return formulario;}
    public void setFormulario(Formulario f){formulario=f;}
    
    public void setValido(String v){this.valido=v;}
    public String getValido(){return valido;}
    
    public void setideliminar(String v){this.ideliminar=v;}
    public String getideliminar(){return ideliminar;}
    
    public String comprobaridentificador() throws SQLException, ClassNotFoundException {
        if(getIdentificador()==null){
            this.valido="nvalido.jpg";
            return "fallo";}
        else if(comprobacionIdentificador(getIdentificador())){
            this.valido="nvalido.jpg";
            return "fallo";}
        else{
            this.valido="valido.png";
            return "exito";}
    }
    
    public boolean comprobacionIdentificador(String usuario) throws SQLException, ClassNotFoundException {
        Connection conexion = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();

            ResultSet rs = instruccion.executeQuery("SELECT * FROM usuario" );
            while (rs.next()){
                if(rs.getObject("identificador").equals(usuario)){
                        rs.close();
                        instruccion.close();
                        conexion.close();
                        return true;}
            }
            rs.close();
            instruccion.close();
            conexion.close();

            return false;

        } catch(SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos");
        }
        return false;
    }
    
        public String comprobar() throws SQLException, ClassNotFoundException {
        Connection conexion = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();

            ResultSet rs = instruccion.executeQuery("SELECT * FROM usuario" );
            while (rs.next()){
                if(rs.getObject("identificador").equals(getIdentificador())){
                    if(rs.getObject("contraseña").equals(getContraseña())){
                        rs.close();
                        instruccion.close();
                        conexion.close();

                        if(getIdentificador().equals("admin")){
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("texto", "fondocolor");
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("texto", "");
                            return "admin";}
                        else
                            return "exito";
                    }
                }
            }

            rs.close();
            instruccion.close();
            conexion.close();

            return "fallo";

        } catch(SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos"+ex);}
        
        return "fallo";
    }
    
      public String registro() throws SQLException, ClassNotFoundException{
        datosusuario.removeAllElements();
        datosusuario.add(getIdentificador());
        datosusuario.add(getContraseña());
        datosusuario.add("/fotosperfil/"+getImagen());
        datosusuario.add(datos.getNombre());
        datosusuario.add(datos.getApellidos());
        datosusuario.add(datos.getSexo());
        datosusuario.add(datos.getFechanacimiento());;
        datosusuario.add(datos.getOcupacion());
        datosusuario.add(datos.getemail());  
        datosusuario.add(datos.getProvincia());
        datosusuario.add(datos.getNacionalidad());

        datosusuario.add(intereses.getActorpreferido1());
        datosusuario.add(intereses.getActorpreferido2());
        datosusuario.add(intereses.getActorpreferido3());
        datosusuario.add(intereses.getGeneropreferido1());
        datosusuario.add(intereses.getGeneropreferido2());
        datosusuario.add(intereses.getGeneropreferido3());
        datosusuario.add(intereses.getDirectorpreferido1());
        datosusuario.add(intereses.getDirectorpreferido2());
        datosusuario.add(intereses.getDirectorpreferido3());
        datosusuario.add(intereses.getCanalpreferido1());
        datosusuario.add(intereses.getCanalpreferido2());
        datosusuario.add(intereses.getCanalpreferido3());

        for(int i=0;i<datosusuario.size();i++){
            System.out.println(""+datosusuario.get(i));
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
            

            /*   INSERT INTO USUARIO IDENTIFICADOR Y CONTRASEÑA  */
            PreparedStatement instruccion1 = conexion.prepareStatement("INSERT INTO usuario VALUES (?,?,?)");

            instruccion1.setString(1,datosusuario.get(0).toString());
            instruccion1.setString(2,datosusuario.get(1).toString());
            instruccion1.setString(3,datosusuario.get(2).toString());
            instruccion1.execute();

            /*   INSERT INTO DATOSPERSONALES   */
            PreparedStatement instruccion2 = conexion.prepareStatement("INSERT INTO datospersonales VALUES (?,?,?,?,?,?,?,?,?)");

            instruccion2.setString(1,datosusuario.get(0).toString());
            instruccion2.setString(2,datosusuario.get(3).toString());
            instruccion2.setString(3,datosusuario.get(4).toString());
            instruccion2.setString(4,datosusuario.get(5).toString());
            java.sql.Date fecha = java.sql.Date.valueOf(datosusuario.get(6).toString());
            instruccion2.setDate(5,fecha);
            instruccion2.setString(6,datosusuario.get(7).toString());
            instruccion2.setString(7,datosusuario.get(8).toString());
            instruccion2.setString(8,datosusuario.get(9).toString());
            instruccion2.setString(9,datosusuario.get(10).toString());

            instruccion2.execute();

            /*   INSERT INTO INTERESES   */
            PreparedStatement instruccion3 = conexion.prepareStatement("INSERT INTO intereses VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

            instruccion3.setString(1,datosusuario.get(0).toString());
            instruccion3.setString(2,(datosusuario.get(11).toString()));
            instruccion3.setString(3,(datosusuario.get(12).toString()));
            instruccion3.setString(4,(datosusuario.get(13).toString()));
            instruccion3.setString(5,(datosusuario.get(14).toString()));
            instruccion3.setString(6,(datosusuario.get(15).toString()));
            instruccion3.setString(7,(datosusuario.get(16).toString()));
            instruccion3.setString(8,(datosusuario.get(17).toString()));
            instruccion3.setString(9,(datosusuario.get(18).toString()));
            instruccion3.setString(10,(datosusuario.get(19).toString()));
            instruccion3.setString(11,(datosusuario.get(20).toString()));
            instruccion3.setString(12,(datosusuario.get(21).toString()));
            instruccion3.setString(13,(datosusuario.get(22).toString()));

            instruccion3.execute();

            instruccion1.close();
            instruccion2.close();
            instruccion3.close();
            conexion.close();


        } catch(SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos"+ex);
        }

        return "registro";
    }
      
     public String perfil() throws ClassNotFoundException{
        Connection conexion = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            Statement instruccion = conexion.createStatement();

            ResultSet rs = instruccion.executeQuery("SELECT * FROM usuario where identificador='"+identificador+"'" );
            while (rs.next()){
                imagen=rs.getString("imagenperfil");}
            
            rs = instruccion.executeQuery("SELECT * FROM datospersonales where Usuario_id='"+identificador+"'" );
            while (rs.next()){
                datos.email=rs.getString("email");
                datos.apellidos=rs.getString("apellidos");
                datos.nombre=rs.getString("nombre");
                datos.fechanacimiento=rs.getObject("fechanacimiento").toString().substring(8,10)+"-"
                        +rs.getObject("fechanacimiento").toString().substring(5,7)+"-"
                        +rs.getObject("fechanacimiento").toString().substring(0,4);
                datos.ocupacion=rs.getString("Ocupación");
                datos.sexo=rs.getString("sexo");
                datos.provincia=rs.getString("provincia");
                datos.nacionalidad=rs.getString("nacionalidad");}
            
            rs = instruccion.executeQuery("SELECT * FROM intereses where Usuario_id='"+identificador+"'" );
            while (rs.next()){
                intereses.actorpreferido1=rs.getString("actorpreferido1");
                intereses.actorpreferido2=rs.getString("actorpreferido2");
                intereses.actorpreferido3=rs.getString("actorpreferido3");
                intereses.generopreferido1=rs.getString("generopreferido1");
                intereses.generopreferido2=rs.getString("generopreferido2");
                intereses.generopreferido3=rs.getString("generopreferido3");
                intereses.directorpreferido1=rs.getString("Directopreferido1");
                intereses.directorpreferido2=rs.getString("Directopreferido2");
                intereses.directorpreferido3=rs.getString("Directorpreferido3");
                intereses.canalpreferido1=rs.getString("canalpreferido1");
                intereses.canalpreferido2=rs.getString("canalpreferido2");
                intereses.canalpreferido3=rs.getString("canalpreferido3");}
            
                rs.close();
                instruccion.close();
                conexion.close();
        
        } catch(SQLException ex) {
            System.out.println(ex+"-> Hubo un problema al intentar conectarse con la base de datos");
        }
        
        return "perfil";
    }
     
        public String modificar() throws SQLException, ClassNotFoundException{
        datosusuario.removeAllElements();
        datosusuario.add(getIdentificador());
        datosusuario.add(getContraseña());
        datosusuario.add("/fotosperfil/"+getImagen());
        datosusuario.add(datos.getNombre());
        datosusuario.add(datos.getApellidos());
        datosusuario.add(datos.getSexo());
        datosusuario.add(datos.getFechanacimiento());
        datosusuario.add(datos.getOcupacion());
        datosusuario.add(datos.getemail()); 
        datosusuario.add(datos.getProvincia());
        datosusuario.add(datos.getNacionalidad());
        
        datosusuario.add(intereses.getActorpreferido1());
        datosusuario.add(intereses.getActorpreferido2());
        datosusuario.add(intereses.getActorpreferido3());
        datosusuario.add(intereses.getGeneropreferido1());
        datosusuario.add(intereses.getGeneropreferido2());
        datosusuario.add(intereses.getGeneropreferido3());
        datosusuario.add(intereses.getDirectorpreferido1());
        datosusuario.add(intereses.getDirectorpreferido2());
        datosusuario.add(intereses.getDirectorpreferido3());
        datosusuario.add(intereses.getCanalpreferido1());
        datosusuario.add(intereses.getCanalpreferido2());
        datosusuario.add(intereses.getCanalpreferido3());

        Connection conexion = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            /*  IDENTIFICADOR  */
            if(!datosusuario.get(0).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE usuario SET identificador='"+datosusuario.get(0).toString()+"' where identificador='"+identificador+"'");
                instruccion1.execute();
                instruccion1.close();}
            /* CONTRASEÑA */
            if(!datosusuario.get(1).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE usuario SET contraseña='"+datosusuario.get(1).toString()+"' where identificador='"+identificador+"'");
                instruccion1.execute();
                instruccion1.close();}
            /* IMAGEN PERFIL */
            if(!datosusuario.get(2).toString().equals("")){
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE usuario SET imagenperfil='"+datosusuario.get(2).toString()+"' where identificador='"+identificador+"'");
                instruccion1.execute();
                instruccion1.close();}

            /*  DATOSPERSONALES  */
            /* ID USUARIO */
            if(!datosusuario.get(0).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE datospersonales SET Usuario_id='"+datosusuario.get(0).toString()+"' where Usuario_id='"+identificador+"'");
                instruccion2.execute();
                instruccion2.close();}
            /* NOMBRE */
            if(!datosusuario.get(3).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE datospersonales SET nombre='"+datosusuario.get(3).toString()+"' where Usuario_id='"+identificador+"'");
                instruccion2.execute();
                instruccion2.close();}
            /* APELLIDOS */
            if(!datosusuario.get(4).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE datospersonales SET apellidos='"+datosusuario.get(4).toString()+"' where Usuario_id='"+identificador+"'");
                instruccion2.execute();
                instruccion2.close();}
            /* SEXO */
            if(!datosusuario.get(5).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE datospersonales SET sexo='"+datosusuario.get(5).toString()+"' where Usuario_id='"+identificador+"'");
                instruccion2.execute();
                instruccion2.close();}
            /* FECHA NACIMIENTO */
            if(!datosusuario.get(6).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE datospersonales SET fechanacimiento='"+java.sql.Date.valueOf(datosusuario.get(6).toString())+"' where Usuario_id='"+identificador+"'");
                instruccion2.execute();
                instruccion2.close();
                datos.fechanacimiento=datosusuario.get(6).toString().substring(8,10)+"-"+datosusuario.get(6).toString().substring(5,7)+"-"+datosusuario.get(6).toString().substring(0,4);
            }
            /* OCUPACION */
            if(!datosusuario.get(7).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE datospersonales SET Ocupación='"+datosusuario.get(7).toString()+"' where Usuario_id='"+identificador+"'");
                instruccion2.execute();
                instruccion2.close();}
            /* EMAIL */
            if(!datosusuario.get(8).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE datospersonales SET email='"+datosusuario.get(8).toString()+"' where Usuario_id='"+identificador+"'");
                instruccion2.execute();
                instruccion2.close();}
            /* PROVINCIA */
            if(!datosusuario.get(9).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE datospersonales SET provincia='"+datosusuario.get(9).toString()+"' where Usuario_id='"+identificador+"'");
                instruccion2.execute();
                instruccion2.close();}
            /* NACIONALIDAD */
            if(!datosusuario.get(10).toString().equals("")){
                PreparedStatement instruccion2 = conexion.prepareStatement("UPDATE datospersonales SET nacionalidad='"+datosusuario.get(10).toString()+"' where Usuario_id='"+identificador+"'");
                instruccion2.execute();
                instruccion2.close();}

            /*  INTERESES  */
            if(!datosusuario.get(0).toString().equals("")){
                PreparedStatement instruccion3 = conexion.prepareStatement("UPDATE intereses SET Usuario_id='"+datosusuario.get(0).toString()+"' where Usuario_id='"+identificador+"'");
                instruccion3.execute();
                instruccion3.close();}
            PreparedStatement instruccion3 = conexion.prepareStatement("UPDATE intereses SET actorpreferido1='"+datosusuario.get(11).toString()+"',actorpreferido2='"+datosusuario.get(12).toString()+"',actorpreferido3='"+datosusuario.get(13).toString()+"',generopreferido1='"+datosusuario.get(14).toString()+"',generopreferido2='"+datosusuario.get(15).toString()+"',generopreferido3='"+datosusuario.get(16).toString()+"',directopreferido1='"+datosusuario.get(17).toString()+"',directopreferido2='"+datosusuario.get(18).toString()+"',directorpreferido3='"+datosusuario.get(19).toString()+"',canalpreferido1='"+datosusuario.get(20).toString()+"',canalpreferido2='"+datosusuario.get(21).toString()+"',canalpreferido3='"+datosusuario.get(22).toString()+"' where Usuario_id='"+identificador+"'");
            instruccion3.execute();
            instruccion3.close();

            
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos"+ex);
        }

        return "registro";
    }
         
   public String eliminarusuario() throws SQLException, ClassNotFoundException{
        eliminar(identificador);
        return "sibaja";
    }
   
   public boolean eliminar(String usuario) throws SQLException, ClassNotFoundException {
        Connection conexion = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            PreparedStatement instruccion1 = conexion.prepareStatement("DELETE FROM usuario where identificador='"+usuario+"'" );
            instruccion1.execute();
            instruccion1.close();
            
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos"+ex);
        }
        return false;
   }
   
     public String eliminaradmin() throws SQLException, ClassNotFoundException{
        eliminar(ideliminar);
        return "eliminar";
    }
     
    public String modificaradmin() throws ClassNotFoundException{
        Connection conexion = null;
        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            
            /* CONTRASEÑA */
                PreparedStatement instruccion1 = conexion.prepareStatement("UPDATE usuario SET contraseña='"+getContraseña()+"' where identificador='"+identificador+"'");
                instruccion1.execute();
                instruccion1.close();
            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Clase Usuario: Hubo un problema al intentar conectarse con la base de datos"+ex);
        }

        return "registro";
    }
      
    public String desconectar(){
        reiniciar();
        return "desconectar";
    }
    
    
    public String reiniciar(){
        identificador="";
        contraseña="";
        imagen="";
        datos.email="";
        datos.nombre="";
        datos.apellidos="";
        datos.fechanacimiento="";
        datos.nombre="";
        datos.ocupacion="";
        datos.sexo="";
        datos.provincia="";
        intereses.actorpreferido1="";
        intereses.actorpreferido2="";
        intereses.actorpreferido3="";
        intereses.canalpreferido1="";
        intereses.canalpreferido2="";
        intereses.canalpreferido3="";
        intereses.directorpreferido1="";
        intereses.directorpreferido2="";
        intereses.directorpreferido3="";
        intereses.generopreferido1="";
        intereses.generopreferido2="";
        intereses.generopreferido3="";
              
        this.valido="valido.png";
        
        return "registro";
    }
}