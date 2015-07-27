package Beans.Usuarios;

import Beans.Idioma.Idioma;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Olvido {
    public String idusuario="";
    public int idsolicitud;
    public String aviso="";
    public String email="";
    
    private Properties props;
    private String from;
    private String to;
    private String subject;
    private String message;
    private String contraseña;
     
    public String getIdusuario(){return idusuario;}
    public void setIdusuario(String s){idusuario=s;}


    public int getIdsolititud(){return idsolicitud;}
    public void setIdsolicitud(int s){idsolicitud=s;}

    public String getAviso(){return aviso;}
    public void setAviso(String s){aviso=s;}
    
    public String getemail(){return email;}
    public void setemail(String s){email=s;}
    
    
    public String enviarcontraseña(){
        Connection conexion = null;

        try {
            try {Class.forName("com.mysql.jdbc.Driver").newInstance();}
            catch (InstantiationException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
            catch (IllegalAccessException ex) {Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
           
        
            Statement instruccion = conexion.createStatement();
            ResultSet rs=instruccion.executeQuery("SELECT * FROM usuario where identificador='"+idusuario+"'");
             
            while (rs.next()){
                contraseña=rs.getString("Contraseña"); 
             }
             rs.close();
             instruccion.close();

            Statement instruccion2 = conexion.createStatement();
            ResultSet rs2=instruccion2.executeQuery("SELECT * FROM datospersonales where Usuario_id='"+idusuario+"'");
             
            while (rs2.next()){
                to=rs2.getString("Email");
                from="tvseriesrec@gmail.com";
                subject="Recordatorio contraseña";
                message="La contraseña correspondiente al usuario "+idusuario+" es: "+contraseña;
                sendMail();
             }
             rs2.close();
             instruccion2.close();
             
             conexion.close();
        
        
        } catch(SQLException ex) {
                System.out.println("Clase:olvido. Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}
                    
            return "enviada";
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

