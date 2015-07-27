package Metodología;

import Beans.Usuarios.Usuario;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

public class Evaluacion implements Serializable{
    public int preg1;
    public int preg2;
    public int preg3;
    public String identificador;
    
    
    public void setIdentificador(String id){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("identificadorrecomendacion", id);
        identificador=id;}
    public  String getIdentificador(){
        if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador")!=null)
            identificador=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString();
        return identificador;}
    
    public int getpreg1(){return preg1;}
    public void setpreg1(int c){preg1=c;}
    
    public int getpreg2(){return preg2;}
    public void setpreg2(int c){preg2=c;}
    
    public int getpreg3(){return preg3;}
    public void setpreg3(int c){preg3=c;}
    
   
    public String evaluar()throws SQLException, ClassNotFoundException{
      Connection conexion = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);}

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            

            /*   INSERT INTO EVALUACION  */
            PreparedStatement instruccion = conexion.prepareStatement("INSERT INTO evaluacion VALUES (?,?,?,?,?)");

            instruccion.setInt(1,0);
            instruccion.setString(2,FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString());
            instruccion.setInt(3,preg1);
            instruccion.setInt(4,preg2);
            instruccion.setInt(5,preg2);
            instruccion.execute();

            instruccion.close();

            conexion.close();

        } catch(SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos"+ex);
        }

        return "evaluada";
    }
    
    
    
}
