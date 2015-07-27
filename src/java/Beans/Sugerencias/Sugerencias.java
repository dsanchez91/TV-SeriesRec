package Beans.Sugerencias;

import Beans.Usuarios.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego Sánchez
 */
public class Sugerencias {
    public String texto;

    public String getTexto(){
        return texto;}
    public void setTexto(String t){
        texto=t;}



    public String guardartexto(){
        Connection conexion = null;
             try {
                    try {
                        Class.forName("com.mysql.jdbc.Driver").newInstance();}
                    catch (InstantiationException ex) {
                        Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}
                    catch (IllegalAccessException ex) {
                        Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

                    conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                    

                    PreparedStatement ps=conexion.prepareStatement("INSERT INTO contacto VALUES (?,?)");

                    ps.setInt(1,1);
                    ps.setString(2,texto);

                    ps.execute();
                    ps.close();
                    conexion.close();

            } catch(SQLException ex) {
                System.out.println("Clase Sugerencias: Hubo un problema al intentar conectarse con la base de datos: "+ex);
            } catch(ClassNotFoundException ex) {
                System.out.println(ex);}

        return "cancelar";}

    public String borrartexto(){
        texto="";
        return "cancelar";}
}
