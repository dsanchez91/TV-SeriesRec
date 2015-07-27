package Beans.Usuarios;
import java.io.Serializable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.context.FacesContext;

/**
 *
 * @author Diego Sánchez
 */
public class Formulario implements Serializable{
    String aviso="";
    Usuario usr;
        
    Formulario(Usuario usruario){usr=usruario;}
    
    public String getAviso(){return aviso;}
    public void setAviso(String a){this.aviso=a;}
    
    public String comprobarcamposcuenta() throws SQLException{
        if(!(usr.getContraseña().equals(usr.getcontraseñaaux()))){
            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                    setAviso("Repita la Contraseña");
                else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Inglés"))
                    setAviso("Repeat password");
            } else setAviso("Repita la Contraseña");
            return "vacio";}
        if(comprobarDato(usr.getIdentificador(),"identificador")){
            usr.setValido("incorrecto.gif");
            return "repetido";}
        else if(!(usr.getIdentificador().equals("")) && !(usr.getContraseña().equals("")) && !(usr.getDatosPersonales().getemail().equals(""))){
            setAviso("");
            return "registro";}
        else{
            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                    setAviso("Debe rellenar todos los campos");
                else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Inglés"))
                    setAviso("Must fil all fields");
            } else setAviso("Debe rellenar todos los campos");
            return "vacio";}
    }
    
        public boolean comprobarDato(String usuario,String dato) throws SQLException {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
            Statement instruccion = conexion.createStatement();

            ResultSet rs = instruccion.executeQuery("SELECT * FROM usuario" );
            while (rs.next()){
                if(rs.getObject(dato).equals(usuario)){
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
            System.out.println("Clase Formulario: Hubo un problema al intentar conectarse con la base de datos"+ex);}
        return false;
    }
        
     public String volver(){
        usr.setValido("");
        setAviso("");
        String fecha = usr.getDatosPersonales().getFechanacimiento();
        if(fecha!=null)
            usr.getDatosPersonales().fechanacimiento=fecha.substring(8,10)+"-"+fecha.substring(5,7)+"-"+fecha.substring(0,4);
        return "volver";
    }
     
     public String comprobarcamposdatos(){
        
        if(!(usr.getDatosPersonales().getNombre().equals("")) && !(usr.getDatosPersonales().getApellidos().equals("")) && !(usr.getDatosPersonales().getFechanacimiento().equals("")) && !(usr.getDatosPersonales().getOcupacion().equals("")) && !(usr.getDatosPersonales().getSexo().equals(""))){
            setAviso("");
            if(!((2012-Integer.parseInt(usr.getDatosPersonales().getFechanacimiento().toString().substring(0,4)))>18
            && (2012-Integer.parseInt(usr.getDatosPersonales().getFechanacimiento().toString().substring(0,4)))<100)){
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
                    if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                        setAviso("El usuario debe ser mayor de 18 años");
                    else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Inglés"))
                        setAviso("The user must be over 18 years");
                    } else setAviso("El usuario debe ser mayor de 18 años");
                String fecha = usr.getDatosPersonales().getFechanacimiento();
                if(fecha!=null)
                    usr.getDatosPersonales().fechanacimiento=fecha.substring(8,10)+"-"+fecha.substring(5,7)+"-"+fecha.substring(0,4);
                return "vacio";}
            return "registro";}
        else{
            String fecha = usr.getDatosPersonales().getFechanacimiento();
            if(fecha!=null && fecha!="")
                usr.getDatosPersonales().fechanacimiento=fecha.substring(8,10)+"-"+fecha.substring(5,7)+"-"+fecha.substring(0,4);
            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                    setAviso("Debe rellenar todos los campos");
                else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Inglés"))
                    setAviso("Must fill all fields");
                 }else setAviso("Debe rellenar todos los campos");

            return "vacio";}
    }
     
    public String comprobarcamposmodificados() throws SQLException, ClassNotFoundException{
        /** Si cambiamos contraseña hacer coincidir la repetición */
        if(!(usr.getContraseñanueva().equals(usr.getcontraseñaaux()))){
            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                    setAviso("Repita la Nueva Contraseña");
                else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Inglés"))
                    setAviso("Repeat new password");
            } else setAviso("Repita la Nueva Contraseña");
            return "vacio";}
        //Introducir antigua contraseña
        if(!(comprobarDato(usr.getContraseña(),"contraseña"))){
            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                    setAviso("Contraseña incorrecta");
                else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Inglés"))
                    setAviso("Incorrect password");
            } else setAviso("Contraseña incorrecta");
                return "vacio";}
        /** Si cambiamos identificador comprobar disponibilidad */
        if(usr.getIdentificadoraux()!=null){
            if(!(usr.getIdentificadoraux().equals(""))){
                if(!(usr.getIdentificador().equals(usr.getIdentificadoraux()))){
                    if(comprobarDato(usr.getIdentificadoraux(),"identificador")){
                        usr.setValido("nvalido.jpg");
                        return "repetido";}
                }
            }
        }
        setAviso("");
        if(!(usr.getContraseña().equals("") && !(usr.getContraseñanueva().equals(""))))
            usr.setContraseña(usr.getContraseñanueva());
        usr.modificar();
        if(usr.getIdentificadoraux()!=null){
            if(!(usr.getIdentificadoraux().equals("")))
                usr.setIdentificador(usr.getIdentificadoraux());
            return "registro";
        }
        return "registro";
    }
    
        public String comprobarmodificacionadmin() throws SQLException, ClassNotFoundException{
        /** Si cambiamos contraseña hacer coincidir la repetida */
        if(!(usr.getContraseñanueva().equals(usr.getcontraseñaaux()))){
            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                    setAviso("Repita la Nueva Contraseña");
                else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Inglés"))
                    setAviso("Repeat new password");
            } else setAviso("Repita la Nueva Contraseña");
            return "vacio";}
        //Introducir antigua contraseña
        if(!(comprobarDato(usr.getContraseña(),"contraseña"))){
            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido")!=null){
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Español"))
                    setAviso("Contraseña incorrecta");
                else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idiomaelegido").toString().equals("Inglés"))
                    setAviso("Incorrect password");
            } else setAviso("Contraseña incorrecta");
                return "vacio";}
        setAviso("");
        if(!(usr.getContraseña().equals("") && !(usr.getContraseñanueva().equals(""))))
            usr.setContraseña(usr.getContraseñanueva());
        usr.modificaradmin();
        return "registro";
    }
    
    
}
