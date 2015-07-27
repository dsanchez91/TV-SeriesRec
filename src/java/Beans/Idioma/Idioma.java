
package Beans.Idioma;
import java.io.Serializable;
import javax.faces.context.FacesContext;

/**
 *
 * @author Diego Sánchez
 */
public class Idioma implements Serializable{
    
    //Index
    public String idioma="Español";
    public String idiomaelegido="Español";
    
    public static String seleccionidiomaesp="Español";
    public static String seleccionidiomaing="Inglés";
    public static String contacto="Mándanos tu sugerencia!";
    
    public static String descripcion="Catálogo y recomendación de series de televisión";
    
    public static String inicio="Inicio";
    public static String busca="Busca una serie de nuestro catálogo";
    
    public static String contraseña="Contraseña";
    public static String recordarcontraseña="¿Olvidó su contraseña?";
    public static String acceder="Acceder";
    public static String registrarse="Registrarse";
    public static String textobienvenida="TV-SeriesRec es una aplicación web de recomendación de series de televisión acorde a tus gustos. Sigue las novedades de las series que te gustan y descubre nuevas series que concuerdan con tus gustos gracias a nuestro sistema de recomendaciones personalizadas.";
    public static String textobienvenida2= "No te pierdas ninguna novedad sobre tus series preferidas: Nuevos capítulos, nuevas temporadas, fechas de lanzamiento.. Y si no tienes capítulos nuevos para ver, solicita una recomendación personalizada de una nueva serie! Este recomendación será generada en base a tus gustos, ya que nuestro sistema evalúa tus preferencias en función a tus intereses y a las Series que ya estás siguiendo. ¡Descubre nuevas series que te encantarán!";
    public static String titulobievenida="¡Descubre nuevos horizontes!";

   //Registro: cuenta
    public static String registro="Registro";    

    public static String usuario="Usuario";
    public static String Estaocupado="Identificador disponible?";
    public static String repetircontraseña="Repita su contraseña";
    public static String siguiente="Siguiente";
    public static String cancelarregistro="Cancelar Registro";
    
    //Registro: datos personales
    public static String nombre="Nombre";
    public static String apellidos="Apellidos";
    public static String fechanacimiento="Fecha de Nacimiento(dd-mm-aaaa)";
    public static String sexo="Sexo";
    public static String masculino="Hombre";
    public static String femenino="Mujer";
    public static String ocupacion="Ocupación";
    public static String estudiante="Estudiante";
    public static String amacasa="Ama de casa";
    public static String parado="Parado";
    public static String funcionario="Funcionario";
    public static String empresa="Empresario";
    public static String autonomo="Autónomo";
    public static String provincia="Provincia";
    public static String nacionalidad="Nacionalidad";
    public static String española="Española";
    public static String europea="Europeo";
    public static String asiatica="Asiático";
    public static String sudamericana="Sudamericano";
    public static String norteamericana="Norteamericano";
    public static String africana="Africano";
    public static String volver="Volver al paso anterior";
    
    //Registro: intereses
    public static String actorpreferido="Escriba sus 3 actores preferidos:";
    public static String generopreferido="Escriba sus 3 géneros preferidos:";
    public static String directorpreferido="Escriba sus 3 directores preferidos:";
    public static String canalpreferido="Escriba sus 3 canales preferidos:";
    public static String terminarregistro="Finalizar registro";
    
    //Sugerencias
    public static String sugerencias="Sugerencias";
    public static String textosugerencia="¿Tienes alguna idea o sugerencia para mejorar TV-SeriesRec? Cuentenosla para seguir haciendo cada día mejor nuestro producto!";
    public static String enviar="Enviar";
    public static String cancelar="Cancelar";
    
    //Bienvenida
    public static String accesofunc="Para poder acceder a esta funcionalidad, debe registrarse en nuestro sistema.";
    public static String identificarse="Identificarse";
    public static String seriesseguidas="Series que sigo";
    public static String miperfil="Mi perfil";
    public static String bienvenida="¡Bienvenido a TV-SeriesRec!";
    public static String textobienvenidareg="Como usuario registrado de TV-SeriesRec, usted podrá acceder a todas nuestras funcionalidades. Busque en nuestro catálogo de series sus favorites y siguelas para estar al tanto de sus novedados y reciba recomendaciones personalizadas de nuevas series en función a sus gustos cuando usted lo solicite!";
    public static String desconectarse="Desconectarse";
    public static String misseries="Mis series";
    public static String serie="serie";
    
    //Olvido contraseña
    public static String olvido="Olvido de contraseña";
    public static String recordar="Recordar contraseña";
    public static String modificardatos="Modificar datos";
    
    //Mi perfil
    public static String darbaja="Dar de baja";
    public static String actorpreferido2="Sus 3 actores preferidos:";
    public static String generopreferido2="Sus 3 géneros preferidos:";
    public static String directorpreferido2="Sus 3 directores preferidos:";
    public static String canalpreferido2="Sus 3 canales preferidos:";
    
    //Modificar cuenta
    public static String modificarcuenta="Modificación de cuenta";
    public static String contraseñanueva="Nueva contraseña";
    
    //Dar de baja
    public static String cancelarbaja="Cancelar baja";
    
    //Catalogo series
    public static String catalogo="Catálogo de series";
    public static String seleccionserie="Seleccione una serie dentro de nuestro catalogo:";
    public static String selecciongenero="También puede buscar series en función de su género";
    public static String buscar="Buscar";
    
    //Series seguidas
    public static String seguidas="Series que sigues:";
    
    //Mostrarseries
    public static String seriesbuscadas="Series que concuerdan con su búsqueda:";
    public static String comentar="Comentar";
    
    //Serie concreta
    public static String idserie;
    public static String titulo="Título:";
    public static String año="Año:";
    public static String pais="País:";
    public static String idiomaser="Idioma:";
    public static String fechainicio="Fecha inicio:";
    public static String fechafin="Fecha fin:";
    public static String genero="Género:";
    public static String productora="Productora";
    public static String director="Director";  
    public static String puntuacion="Puntuacion:";  
    public static String comentarios="Comentarios:";  
    public static String supuntuacion="Su puntuación";
    public static String consultartemp="Consultar temporada";
    
    //Motrar temporadas
    public String selecctemp="Seleccione el número de temporada que desea consultar";
    public String selecccap="Seleccione el número de capítulo que desea consultar";
    public String temporada="ª temporada";
    
    //Temporada concreta
    public static String numero="Su puntuación";
    public static String descripcionn="Su puntuación";
    public static String actores="Actores";
    public static String consultarcap="Consultar capítulo";
    
    //Capitulo concreto
    public static String resumen="Resumen";
    
    //Actor concreto
    public static String biografia="Biografia";
  
    //Recomendacion
    public static String recomendacion="Recomendacion";
    public static String seriesrec="Series recomendadas para usted:";
    public static String textobienvenidarecomendacion="¿Buscas una nueva serie? ¡Nostros te la sugerimos!";
    public static String textorec1="Seleccionando la opción 'Solicitar Recomendación' nuestro sistema de recomendación le proporcionará un listado de series que encajan con sus gustos, obtenidos a partir de sus interes y las series que ya sigue. Puede seleccionar estas series pa ver sus detalles y seguirlas si son de su agrado.";
    public static String textorec2="Si lo desea puede solicitar una recomendación de un género concreto";
    public static String solicitarrecomendacion="Solicitar Recomendación";
    public static String evaluarec="¡Evalúe nuestro sistema de recomendación!";
    
    //Evaluar recomendacion
    public static String opinion="¡Tu opinión nos ayuda a mejorar!";
    public static String textoopinion="TV-SeriesRec es un sistema en continua evolución. Para generar nuestras recomendaciones personalizadas, utilizamos unas novedosas metodologías basadas en sus intereses, atributos demográficos, series en las que ya está interesado y otra serie de atributos que obtenemos. Para evalúar si nuestro sistema de recomendación no es de gran utilidad información sobre el grado de satisfacción sobre nuestras recomendaciones.";
    public static String evalue="Por favor, evalúe del 1 al 5 en función de su satisfacción las siguientes cuestiones";
    public static String preg1="El sistema me ha recomendado series que coinciden con mis gustos:";
    public static String preg2="He empezado a seguir series que me haya el sistema:";
    public static String preg3="He descubierto nuevas series gracias a las recomendaciones";
    public static String evaluar="Evaluar";
    public static String gracias="Su valoración ha sido guardada en nuestro sistema. ¡Gracias por su ayuda!";
    
    //Guia
    public static String guia="Guia";
    public static String bienvguia="Bienvenido a la guía de la aplicación";
    
    public String cambiarespañol(){
        idioma="Español";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idiomaelegido", idioma);
        return "cambio";}
    public String cambiaringles(){
        idioma="Inglés";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idiomaelegido", idioma);
        return "cambio";}
    
    
    /* Getters y Setters */
    
    public  void setdescripcion(String s){descripcion=s;}
    public String getdescripcion(){
        if(idioma.equals("Español")){
            setdescripcion("Catálogo y recomendación de series de televisión");
            return descripcion;}
        else if (idioma.equals("Inglés")){
            setdescripcion("Catalog and recommendation of TV series");
            return descripcion;}
        else {
          return descripcion;  
        }
    }
    
    public  void setSeleccionidiomaesp(String i){seleccionidiomaesp=i;}
    public String getSeleccionidiomaesp(){
        if(idioma.equals("Español")){
            setSeleccionidiomaesp("Español");
            return seleccionidiomaesp;}
        else if(idioma.equals("Inglés")){
            setSeleccionidiomaesp("Spanish");
            return seleccionidiomaesp;}
        else{
            return seleccionidiomaesp;}
    }
    
    public  void setSeleccionidiomaing(String i){seleccionidiomaing=i;}
    public String getSeleccionidiomaing(){
        if(idioma.equals("Español")){
            setSeleccionidiomaing("Inglés");
            return seleccionidiomaing;}
        else if(idioma.equals("Inglés")){
            setSeleccionidiomaing("English");
            return seleccionidiomaing;}
        else{
            return seleccionidiomaing;}
    }
    
    public  void setacceder(String s){acceder=s;}
    public String getacceder(){
        if(idioma.equals("Español")){
            setacceder("Acceder");
            return acceder;}
        else if(idioma.equals("Inglés")){
            setacceder("Log in");
            return acceder;}
        else{
            return acceder;}
    }
    
    public  void setRegistrarse(String s){registrarse=s;}
    public String getRegistrarse(){
        if(idioma.equals("Español")){
            setRegistrarse("Registrarse");
            return registrarse;}
        else if(idioma.equals("Inglés")){
            setRegistrarse("Registrer");
            return registrarse;}
        else{
            return registrarse;}
    }
    
    public  void setContraseña(String s){contraseña=s;}
    public String getContraseña(){
        if(idioma.equals("Español")){
            setContraseña("Contraseña");
            return contraseña;}
        else if(idioma.equals("Inglés")){
            setContraseña("Password");
            return contraseña;}
        else{
            return contraseña;}
    }
    
    public  void setrecordarcontraseña(String s){recordarcontraseña=s;}
    public String getrecordarcontraseña(){
        if(idioma.equals("Español")){
            setrecordarcontraseña("¿Olvidó su contraseña?");
            return recordarcontraseña;}
        else if(idioma.equals("Inglés")){
            setrecordarcontraseña("Forget your password?");
            return recordarcontraseña;}
        else{
            return recordarcontraseña;}
    }
    
    public  void setTitulobievenida(String s){titulobievenida=s;}
    public String getTitulobievenida(){
        if(idioma.equals("Español")){
            setTitulobievenida("¡Descubre nuevos horizontes!");
            return titulobievenida;}
        else if(idioma.equals("Inglés")){
            setTitulobievenida("Discover new horizons!");
            return titulobievenida;}
        else{
            return titulobievenida;}
        }
    
    public  void setTextobienvenida(String s){textobienvenida=s;}
    public String getTextobienvenida(){
        if(idioma.equals("Español")){
            setTextobienvenida("TV-SeriesRec es una aplicación web de recomendación de series de televisión acorde a tus gustos. Sigue las novedades de las series que te gustan y descubre nuevas series que concuerdan con tus gustos gracias a nuestro sistema de recomendaciones personalizadas.");
            return textobienvenida;}
        else if(idioma.equals("Inglés")){
            setTextobienvenida("TV-SeriesRec is a recommendation web application of TV shows according to your tastes. Follow the news of the series that you like and discover new series that match your tastes with our custom recommendation system.");
            return textobienvenida;}
        else{
            return textobienvenida;}
        }
    
    public  void setTextobienvenida2(String s){textobienvenida2=s;}
    public String getTextobienvenida2(){
        if(idioma.equals("Español")){
            setTextobienvenida2("No te pierdas ninguna novedad sobre tus series preferidas: Nuevos capítulos, nuevas temporadas, fechas de lanzamiento.. Y si no tienes capítulos nuevos para ver, solicita una recomendación personalizada de una nueva serie! Este recomendación será generada en base a tus gustos, ya que nuestro sistema evalúa tus preferencias en función a tus intereses y a las Series que ya estás siguiendo. ¡Descubre nuevas series que te encantarán!");
            return textobienvenida2;}
        else if(idioma.equals("Inglés")){
            setTextobienvenida2("Do not miss any news about your favorite shows: New chapters, new seasons, release dates .. And if don't you have any new chapter to see, ask for a personalized recommendation of a new TV-show! This recommendation will be generated based on your tastes, as our system evaluates your preferences according to your interests and shows you already are following.Discover new series you'll love!");
            return textobienvenida2;}
        else{
            return textobienvenida2;}
        }
    
    public  void setcontacto(String s){contacto=s;}
    public String getcontacto(){
        if(idioma.equals("Español")){
            setcontacto("Mándanos tu sugerencia!");
            return contacto;}
        else if(idioma.equals("Inglés")){
            setcontacto("Contact us");
            return contacto;}
        else{
            return contacto;}
    }
    
    public  void setInicio(String s){inicio=s;}
    public String getInicio(){
        if(idioma.equals("Español")){
            setInicio("Inicio");
            return inicio;}
        else if(idioma.equals("Inglés")){
            setInicio("Home");
            return inicio;}
        else{
            return inicio;}
    }
    
    public  void setBusca(String s){busca=s;}
    public String getBusca(){
        if(idioma.equals("Español")){
            setBusca("Series de nuestro catálogo");
            return busca;}
        else if(idioma.equals("Inglés")){
            setBusca("TV-shows in our catalog");
            return busca;}
        else{
            return busca;}
    }
    
    public  void setRegistro(String s){registro=s;}
    public String getRegistro(){
        if(idioma.equals("Español")){
            setRegistro("Registro");
            return registro;}
        else if(idioma.equals("Inglés")){
            setRegistro("Registrer");
            return registro;}
        else{
            return registro;}
    }
    
    public  void setUsuario(String s){usuario=s;}
    public String getUsuario(){
        if(idioma.equals("Español")){
            setUsuario("Usuario");
            return usuario;}
        if(idioma.equals("Inglés")){
            setUsuario("User");
            return usuario;}
        else{
            return usuario;}
    }
    
    public  void setEstaocupado(String s){Estaocupado=s;}
    public String getEstaocupado(){
      if(idioma.equals("Español")){
         setEstaocupado("¿Identificador disponible?");
            return Estaocupado;}
     if(idioma.equals("Inglés")){
         setEstaocupado("Id Available?");
         return Estaocupado;}
     else{
         return Estaocupado;}
    }
    
    public  void setRepetircontraseña(String s){repetircontraseña=s;}
    public String getRepetircontraseña(){
        if(idioma.equals("Español")){
            setRepetircontraseña("Repita su contraseña");
            return repetircontraseña;}
        if(idioma.equals("Inglés")){
            setRepetircontraseña("Repeat your password");
            return repetircontraseña;}
        else{
            return repetircontraseña;}
    }
    
    public  void setSiguiente(String s){siguiente=s;}
    public String getSiguiente(){
        if(idioma.equals("Español")){
            setSiguiente("Siguiente");
            return siguiente;}
        if(idioma.equals("Inglés")){
            setSiguiente("Next");
            return siguiente;}
        else{
            return siguiente;}
    }

    public  void setcancelarregistro(String s){cancelarregistro=s;}
    public String getcancelarregistro(){
        if(idioma.equals("Español")){
            setcancelarregistro("Cancelar Registro");
            return cancelarregistro;}
        if(idioma.equals("Inglés")){
            setcancelarregistro("Cancel registration");
            return cancelarregistro;}
        else{
            return cancelarregistro;}
    }
    
    public  void setNombre(String s){nombre=s;}
    public String getNombre(){
        if(idioma.equals("Español")){
            setNombre("Nombre");
            return nombre;}
        if(idioma.equals("Inglés")){
            setNombre("Name");
            return nombre;}
        else{
            return nombre;}
    }
    
    public  void setApellidos(String s){apellidos=s;}
    public String getApellidos(){
        if(idioma.equals("Español")){
            setApellidos("Apellidos");
            return apellidos;}
        if(idioma.equals("Inglés")){
            setApellidos("Surname");
            return apellidos;}
        else{
            return apellidos;}
    }
    
    public  void setFechanacimiento(String s){fechanacimiento=s;}
    public String getFechanacimiento(){
        if(idioma.equals("Español")){
            setFechanacimiento("Fecha de Nacimiento (dd-mm-aaaa)");
            return fechanacimiento;}
        if(idioma.equals("Inglés")){
            setFechanacimiento("Birthday (dd-mm-yyyy)");
            return fechanacimiento;}
        else{
            return fechanacimiento;}
    }
    
    public  void setSexo(String s){sexo=s;}
    public String getSexo(){
        if(idioma.equals("Español")){
            setSexo("Sexo");
            return sexo;}
        if(idioma.equals("Inglés")){
            setSexo("Sex");
            return sexo;}
        else{
            return sexo;}
    }
    
    public  void setMasculino(String s){masculino=s;}
    public String getMasculino(){
        if(idioma.equals("Español")){
            setMasculino("Hombre");
            return masculino;}
        if(idioma.equals("Inglés")){
            setMasculino("Male");
            return masculino;}
        else{
            return masculino;}
    }
    
    public  void setFemenino(String s){femenino=s;}
    public String getFemenino(){
        if(idioma.equals("Español")){
            setFemenino("Mujer");
            return femenino;}
        if(idioma.equals("Inglés")){
            setFemenino("Female");
            return femenino;}
        else{
            return femenino;}
    }
    
    public  void setOcupacion(String s){ocupacion=s;}
    public String getOcupacion(){
        if(idioma.equals("Español")){
            setOcupacion("Ocupación");
            return ocupacion;}
        if(idioma.equals("Inglés")){
            setOcupacion("Occupation");
            return ocupacion;}
        else{
            return ocupacion;}
    }
    
    public  void setEstudiante(String s){estudiante=s;}
    public String getEstudiante(){
        if(idioma.equals("Español")){
            setEstudiante("Estudiante");
            return estudiante;}
        if(idioma.equals("Inglés")){
            setEstudiante("Student");
            return estudiante;}
        else{
            return estudiante;}
    }
    
    public  void setAmacasa(String s){amacasa=s;}
    public String getAmacasa(){
        if(idioma.equals("Español")){
            setAmacasa("Ama de casa");
            return amacasa;}
        if(idioma.equals("Inglés")){
            setAmacasa("Homemaker");
            return amacasa;}
        else{
            return amacasa;}
    }
    
    public  void setFuncionario(String s){funcionario=s;}
    public String getFuncionario(){
        if(idioma.equals("Español")){
            setFuncionario("Funcionario");
            return funcionario;}
        if(idioma.equals("Inglés")){
            setFuncionario("Government employee");
            return funcionario;}
        else{
            return funcionario;}
    }
    
    public  void setParado(String s){parado=s;}
    public String getParado(){
        if(idioma.equals("Español")){
            setParado("Parado");
            return parado;}
        if(idioma.equals("Inglés")){
            setParado("Unemployed");
            return parado;}
        else{
            return parado;}
    }
    
    public  void setEmpresa(String s){empresa=s;}
    public String getEmpresa(){
        if(idioma.equals("Español")){
            setEmpresa("Empresario");
            return empresa;}
        if(idioma.equals("Inglés")){
            setEmpresa("Businessman");
            return empresa;}
        else{
            return empresa;}
    }
    
    public  void setAutonomo(String s){autonomo=s;}
    public String getAutonomo(){
        if(idioma.equals("Español")){
            setAutonomo("Autónomo");
            return autonomo;}
        if(idioma.equals("Inglés")){
            setAutonomo("Self-employed");
            return autonomo;}
        else{
            return autonomo;}
    }
    
    public  void setprovincia(String s){provincia=s;}
    public String getprovincia(){
        if(idioma.equals("Español")){
            setprovincia("Provincia");
            return provincia;}
        if(idioma.equals("Inglés")){
            setprovincia("Province");
            return provincia;}
        else{
            return provincia;}
    }
    
    public  void setNacionalidad(String s){nacionalidad=s;}
    public String getNacionalidad(){
        if(idioma.equals("Español")){
            setNacionalidad("Nacionalidad");
            return nacionalidad;}
        if(idioma.equals("Inglés")){
            setNacionalidad("Nationality");
            return nacionalidad;}
        else{
            return nacionalidad;}
    }
    
    public  void setEspañola(String s){española=s;}
    public String getEspañola(){
        if(idioma.equals("Español")){
            setEspañola("Española");
            return española;}
        if(idioma.equals("Inglés")){
            setEspañola("Spanish");
            return española;}
        else{
            return española;}
    }
    
    public  void setEuropea(String s){europea=s;}
    public String getEuropea(){
        if(idioma.equals("Español")){
            setEuropea("Europeo");
            return europea;}
        if(idioma.equals("Inglés")){
            setEuropea("European");
            return europea;}
        else{
            return europea;}
    }
    
    public  void setAsiatica(String s){asiatica=s;}
    public String getAsiatica(){
        if(idioma.equals("Español")){
            setAsiatica("Asiatico");
            return asiatica;}
        if(idioma.equals("Inglés")){
            setAsiatica("Asian");
            return asiatica;}
        else{
            return asiatica;}
    }
    
    public  void setAfricana(String s){africana=s;}
    public String getAfricana(){
        if(idioma.equals("Español")){
            setAfricana("Africano");
            return africana;}
        if(idioma.equals("Inglés")){
            setAfricana("African");
            return africana;}
        else{
            return africana;}
    }
    
    public  void setNorteamericana(String s){norteamericana=s;}
    public String getNorteamericana(){
        if(idioma.equals("Español")){
            setNorteamericana("Norteamericano");
            return norteamericana;}
        if(idioma.equals("Inglés")){
            setNorteamericana("North American");
            return norteamericana;}
        else{
            return norteamericana;}
    }
    
    public  void setSudamericana(String s){sudamericana=s;}
    public String getSudamericana(){
        if(idioma.equals("Español")){
            setSudamericana("Sudamericano");
            return sudamericana;}
        if(idioma.equals("Inglés")){
            setSudamericana("South American");
            return sudamericana;}
        else{;
            return sudamericana;}
    }
    
    public  void setvolver(String s){volver=s;}
    public String getvolver(){
        if(idioma.equals("Español")){
            setvolver("Volver al paso anterior");
            return volver;}
        if(idioma.equals("Inglés")){
            setvolver("Previous");
            return volver;}
        else{
            return volver;}
    }
    
    public  void setactorpreferido(String s){actorpreferido=s;}
    public String getactorpreferido(){
        if(idioma.equals("Español")){
            setactorpreferido("Escriba sus 3 actores preferidos");
            return actorpreferido;}
        if(idioma.equals("Inglés")){
            setactorpreferido("Write your 3 favourite actors:");
            return actorpreferido;}
        else{
            return actorpreferido;}
    }
    
    public  void setgeneropreferido(String s){generopreferido=s;}
    public String getgeneropreferido(){
        if(idioma.equals("Español")){
            setgeneropreferido("Escriba sus 3 generos preferidos");
            return generopreferido;}
        else if(idioma.equals("Inglés")){
            setgeneropreferido("Write your 3 favourite genres");
            return generopreferido;}
        else{
            return generopreferido;}
        }
    
    public  void setdirectorpreferido(String s){directorpreferido=s;}
    public String getdirectorpreferido(){
        if(idioma.equals("Español")){
            setdirectorpreferido("Escriba sus 3 directores preferidos");
            return directorpreferido;}
        else if(idioma.equals("Inglés")){
            setdirectorpreferido("Write your 3 favourite directors");
            return directorpreferido;}
        else{
            return directorpreferido;}
        }
    
    public  void setcanalpreferido(String s){canalpreferido=s;}
    public String getcanalpreferido(){
        if(idioma.equals("Español")){
            setcanalpreferido("Escriba sus 3 canales (productoras) preferidos");
            return directorpreferido;}
        else if(idioma.equals("Inglés")){
            setcanalpreferido("Write your 3 favourite channels");
            return directorpreferido;}
        else{
            return directorpreferido;}
        }
    
    public  void setterminarregistro(String s){terminarregistro=s;}
    public String getterminarregistro(){
        if(idioma.equals("Español")){
            setterminarregistro("Finalizar registro");
            return terminarregistro;}
        else if(idioma.equals("Inglés")){
            setterminarregistro("Finish register");
            return terminarregistro;}
        else{
            return terminarregistro;}
        }
    
    public  void setSugerencias(String s){sugerencias=s;}
    public String getSugerencias(){
        if(idioma.equals("Español")){
            setSugerencias("Sugerencias");
            return sugerencias;}
        if(idioma.equals("Inglés")){
            setSugerencias("Suggestions");
            return sugerencias;}
        else{
            return sugerencias;}
    }
    
    public  void settextosugerencia(String s){textosugerencia=s;}
    public String gettextosugerencia(){
        if(idioma.equals("Español")){
            settextosugerencia("¿Tienes alguna idea o sugerencia para mejorar TV-SeriesRec? Cuentenosla para seguir haciendo cada día mejor nuestro producto!");
            return textosugerencia;}
        if(idioma.equals("Inglés")){
            settextosugerencia("Do you have any idea or suggestion to improve TV-SeriesRec? Tell it to us to continue making our product better every day!");
            return textosugerencia;}
        else{
            return textosugerencia;}
    }
    
    public  void setEnviar(String s){enviar=s;}
    public String getEnviar(){
        if(idioma.equals("Español")){
            setEnviar("Enviar");
            return enviar;}
        if(idioma.equals("Inglés")){
            setEnviar("Send");
            return enviar;}
        else{
            return enviar;}
    }
    
    public  void setCancelar(String s){cancelar=s;}
    public String getCancelar(){
        if(idioma.equals("Español")){
            setCancelar("Cancelar");
            return cancelar;}
        if(idioma.equals("Inglés")){
            setCancelar("Cancel");
            return cancelar;}
        else{
            return cancelar;}
    }
    
    public  void setaccesofunc(String s){accesofunc=s;}
    public String getaccesofunc(){
        if(idioma.equals("Español")){
            setaccesofunc("Para poder acceder a esta funcionalidad, debe registrarse en nuestro sistema.");
            return accesofunc;}
        if(idioma.equals("Inglés")){
            setaccesofunc("To access this feature, you must register on our system.");
            return accesofunc;}
        else{
            return accesofunc;}
    }
    
    public  void setIdentificarse(String s){identificarse=s;}
    public String getIdentificarse(){
        if(idioma.equals("Español")){
            setIdentificarse("Identificarse");
            return identificarse;}
        if(idioma.equals("Inglés")){
            setIdentificarse("Identify");
            return identificarse;}
        else{
            return identificarse;}
    }
    
    public  void setseriesseguidas(String s){seriesseguidas=s;}
    public String getseriesseguidas(){
        if(idioma.equals("Español")){
            setseriesseguidas("Series que sigo");
            return seriesseguidas;}
        else if(idioma.equals("Inglés")){
            setseriesseguidas("Followed shows");
            return seriesseguidas;}
        else{
            return seriesseguidas;}
        }
    
    public  void setmiperfil(String s){miperfil=s;}
    public String getmiperfil(){
        if(idioma.equals("Español")){
            setmiperfil("Mi perfil");
            return miperfil;}
        else if(idioma.equals("Inglés")){
            setmiperfil("My Profile");
            return miperfil;}
        else{
            return miperfil;}
        }
    
    public  void setDesconectarse(String s){desconectarse=s;}
    public String getDesconectarse(){
        if(idioma.equals("Español")){
            setDesconectarse("Desconectarse");
            return desconectarse;}
        else if(idioma.equals("Inglés")){
            setDesconectarse("Log out");
            return desconectarse;}
        else{
            return desconectarse;}
        }
    
    public  void setBienvenida(String s){bienvenida=s;}
    public String getBienvenida(){
        if(idioma.equals("Español")){
            setBienvenida("¡Bienvenido a TV-SeriesRec!");
            return bienvenida;}
        else if(idioma.equals("Inglés")){
            setBienvenida("Wellcome to TV-SeriesRec!");
            return bienvenida;}
        else{
            return bienvenida;}
        }
    
    public  void settextobienvenidareg(String s){textobienvenidareg=s;}
    public String gettextobienvenidareg(){
        if(idioma.equals("Español")){
            settextobienvenidareg("Como usuario registrado de TV-SeriesRec, usted podrá acceder a todas nuestras funcionalidades. Busque en nuestro catálogo de series sus favorites y siguelas para estar al tanto de sus novedados y reciba recomendaciones personalizadas de nuevas series en función a sus gustos cuando usted lo solicite!");
            return textobienvenidareg;}
        else if(idioma.equals("Inglés")){
            settextobienvenidareg("As a registered SeriesRec TV, you can access all our features. Search our catalog of his favorites series and follow them to be aware of their novedados and receive personalized recommendations for new series based on your tastes when you ask for!");
            return textobienvenidareg;}
        else{
            return textobienvenidareg;}
        }
    
    public  void setmisseries(String s){misseries=s;}
    public String getmisseries(){
        if(idioma.equals("Español")){
            setmisseries("Mis series");
            return misseries;}
        if(idioma.equals("Inglés")){
            setmisseries("My shows");
            return misseries;}
        else{
            return misseries;}
    }
    
    public  void setolvido(String s){olvido=s;}
    public String getolvido(){
        if(idioma.equals("Español")){
            setolvido("Olvido de contraseña");
            return olvido;}
        if(idioma.equals("Inglés")){
            setolvido("Forgotten password");
            return olvido;}
        else{
            return olvido;}
    }
    public  void setrecordar(String s){recordar=s;}
    public String getrecordar(){
        if(idioma.equals("Español")){
            setrecordar("Recordar contraseña");
            return recordar;}
        if(idioma.equals("Inglés")){
            setrecordar("Remember my password");
            return recordar;}
        else{
            return recordar;}
    }
    
    public  void setmodificardatos(String s){modificardatos=s;}
    public String getmodificardatos(){
        if(idioma.equals("Español")){
            setmodificardatos("Modificar Datos");
            return modificardatos;}
        if(idioma.equals("Inglés")){
            setmodificardatos("Data modification");
            return modificardatos;}
        else{
            return modificardatos;}
    }
    
    public  void setDarbaja(String s){darbaja=s;}
    public String getDarbaja(){
        if(idioma.equals("Español")){
            setDarbaja("Dar de baja");
            return darbaja;}
        if(idioma.equals("Inglés")){
            setDarbaja("Delete user");
            return darbaja;}
        else{
            return darbaja;}
    }
    
    public  void setactorpreferido2(String s){actorpreferido2=s;}
    public String getactorpreferido2(){
        if(idioma.equals("Español")){
            setactorpreferido2("Sus 3 actores preferidos");
            return actorpreferido2;}
        if(idioma.equals("Inglés")){
            setactorpreferido2("Your 3 favourite actors:");
            return actorpreferido2;}
        else{
            return actorpreferido2;}
    }
    
    public  void setgeneropreferido2(String s){generopreferido2=s;}
    public String getgeneropreferido2(){
        if(idioma.equals("Español")){
            setgeneropreferido2("Sus 3 generos preferidos");
            return generopreferido2;}
        else if(idioma.equals("Inglés")){
            setgeneropreferido2("Your 3 favourite genres");
            return generopreferido2;}
        else{
            return generopreferido2;}
        }
    
    public  void setdirectorpreferido2(String s){directorpreferido2=s;}
    public String getdirectorpreferido2(){
        if(idioma.equals("Español")){
            setdirectorpreferido2("Sus 3 directores preferidos");
            return directorpreferido2;}
        else if(idioma.equals("Inglés")){
            setdirectorpreferido2("Your 3 favourite directors");
            return directorpreferido2;}
        else{
            return directorpreferido2;}
        }
    
    public  void setcanalpreferido2(String s){canalpreferido2=s;}
    public String getcanalpreferido2(){
        if(idioma.equals("Español")){
            setcanalpreferido2("Sus 3 canales (productoras) preferidos");
            return canalpreferido2;}
        else if(idioma.equals("Inglés")){
            setcanalpreferido2("Your 3 favourite channels");
            return canalpreferido2;}
        else{
            return canalpreferido2;}
        }
    
    public  void setmodificarcuenta(String s){modificarcuenta=s;}
    public String getmodificarcuenta(){
        if(idioma.equals("Español")){
            setmodificarcuenta("Modificacion de cuenta");
            return modificarcuenta;}
        if(idioma.equals("Inglés")){
            setmodificarcuenta("Account modification");
            return modificarcuenta;}
        else{
            return modificarcuenta;}
    }
    
    public  void setContraseñanueva(String s){contraseñanueva=s;}
    public String getContraseñanueva(){
        if(idioma.equals("Español")){
            setContraseñanueva("Nueva contraseña");
            return contraseñanueva;}
        else if(idioma.equals("Inglés")){
            setContraseñanueva("New password");
            return contraseñanueva;}
        else{
            return contraseñanueva;}
    }
    
    public  void setCancelarbaja(String s){cancelarbaja=s;}
    public String getCancelarbaja(){
        if(idioma.equals("Español")){
            setCancelarbaja("Cancelar baja");
            return cancelarbaja;}
        if(idioma.equals("Inglés")){
            setCancelarbaja("Cancel user deletion");
            return cancelarbaja;}
        else{
            return cancelarbaja;}
    }
    
    public  void setcatalogo(String s){catalogo=s;}
    public String getcatalogo(){
        if(idioma.equals("Español")){
            setcatalogo("Catálogo de series");
            return catalogo;}
        if(idioma.equals("Inglés")){
            setcatalogo("Shows catalog");
            return catalogo;}
        else{
            return catalogo;}
    }
    
    public  void setseleccionserie(String s){seleccionserie=s;}
    public String getseleccionserie(){
        if(idioma.equals("Español")){
            setseleccionserie("Seleccione una serie dentro de nuestro catalogo:");
            return seleccionserie;}
        if(idioma.equals("Inglés")){
            setseleccionserie("Select a show within our catalog:");
            return seleccionserie;}
        else{
            return seleccionserie;}
    }
    
    public  void setseguidas(String s){seguidas=s;}
    public String getseguidas(){
        if(idioma.equals("Español")){
            setseguidas("Series que sigues:");
            return seguidas;}
        if(idioma.equals("Inglés")){
            setseguidas("Shows you are already following:");
            return seguidas;}
        else{
            return seguidas;}
    }
    
    public  void setseriesbuscadas(String s){seriesbuscadas=s;}
    public String getseriesbuscadas(){
        if(idioma.equals("Español")){
            setseriesbuscadas("Series que concuerdan con su búsqueda:");
            return seriesbuscadas;}
        if(idioma.equals("Inglés")){
            setseriesbuscadas("Shows that match your search:");
            return seriesbuscadas;}
        else{
            return seriesbuscadas;}
    }
    
    public  void settitulo(String s){titulo=s;}
    public String gettitulo(){
        if(idioma.equals("Español")){
            settitulo("Titulo");
            return titulo;}
        if(idioma.equals("Inglés")){
            settitulo("Title");
            return titulo;}
        else{
            return titulo;}
    }
    
    public  void setaño(String s){año=s;}
    public String getaño(){
        if(idioma.equals("Español")){
            setaño("Año");
            return año;}
        if(idioma.equals("Inglés")){
            setaño("Year");
            return año;}
        else{
            return año;}
    }
    
    public  void setpais(String s){pais=s;}
    public String getpais(){
        if(idioma.equals("País:")){
            setpais("pais");
            return pais;}
        if(idioma.equals("Country:")){
            setpais("Upload");
            return pais;}
        else{
            return pais;}
    }
    
    public  void setfechainicio(String s){fechainicio=s;}
    public String getfechainicio(){
        if(idioma.equals("Español")){
            setfechainicio("Fecha inicio:");
            return fechainicio;}
        if(idioma.equals("Inglés")){
            setfechainicio("Start date:");
            return fechainicio;}
        else{
            return fechainicio;}
    }
    
    public  void setfechafin(String s){fechafin=s;}
    public String getfechafin(){
        if(idioma.equals("Español")){
            setfechafin("Fecha fin:");
            return fechafin;}
        if(idioma.equals("Inglés")){
            setfechafin("Ending date");
            return fechafin;}
        else{
            return fechafin;}
    }
        
    public  void setgenero(String s){genero=s;}
    public String getgenero(){
        if(idioma.equals("Español")){
            setgenero("Género:");
            return genero;}
        if(idioma.equals("Inglés")){
            setgenero("Genere:");
            return genero;}
        else{
            return genero;}
   
    }
    
    public  void setproductora(String s){productora=s;}
    public String getproductora(){
        if(idioma.equals("Español")){
            setproductora("Productora:");
            return productora;}
        if(idioma.equals("Inglés")){
            setproductora("Production:");
            return productora;}
        else{
            return productora;}
    }
    
     public  void setcomentarios(String s){comentarios=s;}
     public String getcomentarios(){
        if(idioma.equals("Español")){
            setcomentarios("Comentarios:");
            return comentarios;}
        if(idioma.equals("Inglés")){
            setcomentarios("Comments:");
            return comentarios;}
        else{
            return comentarios;}
    }
     
    public  void setpuntuacion(String s){puntuacion=s;}
    public String getpuntuacion(){
        if(idioma.equals("Español")){
            setpuntuacion("Puntuacion:");
            return puntuacion;}
        if(idioma.equals("Inglés")){
            setpuntuacion("Punctuation:");
            return puntuacion;}
        else{
            return puntuacion;}
    }
    public  void setidiomaser(String s){idiomaser=s;}
    public String getidiomaser(){
        if(idioma.equals("Español")){
            setidiomaser("Idioma:");
            return idiomaser;}
        else if(idioma.equals("Inglés")){
            setidiomaser("Language:");
            return idiomaser;}
        else{
            return idiomaser;}
        }
    
    public  void setSupuntuacion(String s){supuntuacion=s;}
    public String getTupuntuacion(){
        if(idioma.equals("Español")){
            setSupuntuacion("Su puntuación");
            return supuntuacion;}
        else if(idioma.equals("Inglés")){
            setSupuntuacion("Your rate");
            return supuntuacion;}
        else{
            return supuntuacion;}
        }
    
    public  void setComentar(String s){comentar=s;}
    public String getComentar(){
        if(idioma.equals("Español")){
            setComentar("Comentar");
            return comentar;}
        else if(idioma.equals("Inglés")){
            setComentar("Comment");
            return comentar;}
        else{
            setComentar("Commentaire");
            return comentar;}
        }
    
    public  void setserie(String s){serie=s;}
    public String getserie(){
        if(idioma.equals("Español")){
            setserie("serie");
            return serie;}
        else if(idioma.equals("Inglés")){
            setserie("show");
            return serie;}
        else{
            return serie;}
        }
    
    public  void setselecctemp(String s){selecctemp=s;}
    public String getselecctemp(){
        if(idioma.equals("Español")){
            setselecctemp("Seleccione el número de temporada que desea consultar");
            return selecctemp;}
        else if(idioma.equals("Inglés")){
            setselecctemp("Select the number of season you want to see");
            return selecctemp;}
        else{
            return selecctemp;}
        }
    
    public  void setselecccap(String s){selecccap=s;}
    public String getselecccap(){
        if(idioma.equals("Español")){
            setselecccap("Seleccione el número de capítulo que desea consultar");
            return selecccap;}
        else if(idioma.equals("Inglés")){
            setselecccap("Select the number of chapter you want to see");
            return selecccap;}
        else{
            return selecccap;}
        }
    
    public  void setnumero(String s){numero=s;}
    public String getNumero(){
        if(idioma.equals("Español")){
            setnumero("Numero");
            return numero;}
        if(idioma.equals("Inglés")){
            setnumero("Number");
            return numero;}
        else{
            return numero;}
    }
    
    public  void setdescripcionn(String s){descripcionn=s;}
    public String getdescripcionn(){
        if(idioma.equals("Español")){
            setdescripcionn("Descripcion:");
            return descripcionn;}
        if(idioma.equals("Inglés")){
            setdescripcionn("Description:");
            return descripcionn;}
        else{
            return descripcionn;}
    }
    
    public  void setactores(String s){actores=s;}
    public String getactores(){
        if(idioma.equals("Español")){
            setactores("Actores");
            return actores;}
        if(idioma.equals("Inglés")){
            setactores("Actors");
            return actores;}
        else{
            setactores("L'islam");
            return actores;}
    }
    
    public  void setresumen(String s){resumen=s;}
    public String getresumen(){
        if(idioma.equals("Español")){
            setresumen("Resumen");
            return resumen;}
        if(idioma.equals("Inglés")){
            setresumen("Abstract");
            return resumen;}
        else{
            return resumen;}
    }
    
    public  void setbiografia(String s){resumen=s;}
    public String getbiografia(){
        if(idioma.equals("Español")){
            setbiografia("Biografia");
            return resumen;}
        if(idioma.equals("Inglés")){
            setbiografia("Biography");
            return resumen;}
        else{
            return resumen;}
    }
    
    public  void setRecomendacion(String s){recomendacion=s;}
    public String getRecomendacion(){
        if(idioma.equals("Español")){
            setRecomendacion("Recomendacion");
            return recomendacion;}
        else if(idioma.equals("Inglés")){
            setRecomendacion("Recomendation");
            return recomendacion;}
        else{
            return recomendacion;}
        }
    
    public  void setTextobienvenidarecomendacion(String s){textobienvenidarecomendacion=s;}
    public String getTextobienvenidarecomendacion(){
        if(idioma.equals("Español")){
            setTextobienvenidarecomendacion("¿Buscas una nueva serie? ¡Nostros te la sugerimos!");
            return textobienvenidarecomendacion;}
        if(idioma.equals("Inglés")){
            setTextobienvenidarecomendacion("Looking for a new TV-show? We suggest you");
            return textobienvenidarecomendacion;}
        else{
            return textobienvenidarecomendacion;}
    }
    
    public  void settextorec1(String s){textorec1=s;}
    public String gettextorec1(){
        if(idioma.equals("Español")){
            settextorec1("Seleccionando la opción 'Solicitar Recomendación' nuestro sistema de recomendación le proporcionará un listado de series que encajan con sus gustos, obtenidos a partir de sus interes y las series que ya sigue. Puede seleccionar estas series para ver sus detalles y seguirlas si son de su agrado.");
            return textorec1;}
        if(idioma.equals("Inglés")){
            settextorec1("Selecting the 'Request Recommendation' our recommendation system will provide a list of TV-shows that match your tastes, obtained from your interest and TV-Shows you follow. You can select these series to see its details and follow them if they are right for you.");
            return textorec1;}
        else{
            return textorec1;}
    }
    
    public  void settextorec2(String s){textorec2=s;}
    public String gettextorec2(){
        if(idioma.equals("Español")){
            settextorec2("Si lo desea puede solicitar una recomendación de un género concreto");
            return textorec2;}
        if(idioma.equals("Inglés")){
            settextorec2("You can also request a recommendation of a particular genre");
            return textorec2;}
        else{
            return textorec2;}
    }
    
    public  void setSolicitarrecomendacion(String s){solicitarrecomendacion=s;}
    public String getSolicitarrecomendacion(){
        if(idioma.equals("Español")){
            setSolicitarrecomendacion("Solicitar Recomendación");
            return solicitarrecomendacion;}
        if(idioma.equals("Inglés")){
            setSolicitarrecomendacion("Recommendation Request");
            return solicitarrecomendacion;}
        else{
            return solicitarrecomendacion;}
    }
    
     public  void setBuscar(String s){buscar=s;}
    public String getBuscar(){
        if(idioma.equals("Español")){
            setBuscar("Buscar");
            return buscar;}
        if(idioma.equals("Inglés")){
            setBuscar("Search");
            return buscar;}
        else{
            return buscar;}
    }
    
    public  void setConsultartemp(String s){consultartemp=s;}
    public String getConsultartemp(){
        if(idioma.equals("Español")){
            setConsultartemp("Consultar temporadas");
            return consultartemp;}
        if(idioma.equals("Inglés")){
            setConsultartemp("See seasons");
            return consultartemp;}
        else{
            return consultartemp;}
    }
    
    public  void setConsultarcap(String s){consultarcap=s;}
    public String getConsultarcap(){
        if(idioma.equals("Español")){
            setConsultarcap("Consultar capitulos");
            return consultarcap;}
        if(idioma.equals("Inglés")){
            setConsultarcap("See chapters");
            return consultarcap;}
        else{
            return consultarcap;}
    }
    
    public  void settemporada(String s){temporada=s;}
    public String gettemporada(){
        if(idioma.equals("Español")){
            setConsultarcap("ª temporada");
            return consultarcap;}
        if(idioma.equals("Inglés")){
            settemporada(" season");
            return temporada;}
        else{
            return temporada;}
    }
    
    public  void setSeriesrec(String s){seriesrec=s;}
    public String getSeriesrec(){
        if(idioma.equals("Español")){
            setSeriesrec("Series recomendadas para usted:");
            return seriesrec;}
        else if(idioma.equals("Inglés")){
            setSeriesrec("TV-shows recommended for you:");
            return seriesrec;}
        else{
            return seriesrec;}
        }
    
    public  void setselecciongenero(String s){selecciongenero=s;}
    public String getselecciongenero(){
        if(idioma.equals("Español")){
            setselecciongenero("También puede buscar series en función de su género");
            return selecciongenero;}
        if(idioma.equals("Inglés")){
            setseleccionserie("You can also search TV-shows based on their gender");
            return selecciongenero;}
        else{
            return selecciongenero;}
    }
    
    public  void setevaluarec(String s){evaluarec=s;}
    public String getevaluarec(){
        if(idioma.equals("Español")){
            setevaluarec("¡Evalúe nuestro sistema de recomendación!");
            return evaluarec;}
        if(idioma.equals("Inglés")){
            setevaluarec("Evaluate our recommendation system!");
            return evaluarec;}
        else{
            return evaluarec;}
    }
    
    public  void setopinion(String s){opinion=s;}
    public String getopinion(){
        if(idioma.equals("Español")){
            setopinion("¡Tu opinión nos ayuda a mejorar!");
            return opinion;}
        if(idioma.equals("Inglés")){
            setopinion("Your feedback helps us to improve!");
            return opinion;}
        else{
            return opinion;}
    }

    public  void settextoopinion(String s){textoopinion=s;}
    public String gettextoopinion(){
        if(idioma.equals("Español")){
            settextoopinion("TV-SeriesRec es un sistema en continua evolución. Para generar nuestras recomendaciones personalizadas, utilizamos unas novedosas metodologías basadas en sus intereses, atributos demográficos, series en las que ya está interesado y otra serie de atributos que obtenemos. Para evalúar si nuestro sistema de recomendación no es de gran utilidad información sobre el grado de satisfacción sobre nuestras recomendaciones.");
            return textoopinion;}
        if(idioma.equals("Inglés")){
            settextoopinion("TV-SeriesRec is an evolving system. To generate our recommendations, we use a novel methodologies based on your interests, demographic attributes, and TV-shows that you are already interested and a number of attributes that we get. Toevaluate our recommendation system, we need information about your satisfaction with our recommendations.");
            return textoopinion;}
        else{
            return textoopinion;}
    }
    
    public  void setevalue(String s){evalue=s;}
    public String getevalue(){
        if(idioma.equals("Español")){
            setevalue("Por favor, evalúe del 1 al 5 en función de su satisfacción las siguientes cuestiones");
            return evalue;}
        if(idioma.equals("Inglés")){
            setevalue("Please rate from 1 to 5 according to your satisfaction the following questions!");
            return evalue;}
        else{
            return evalue;}
    }
    
    public  void setpreg1(String s){preg1=s;}
    public String getpreg1(){
        if(idioma.equals("Español")){
            setpreg1("El sistema me ha recomendado series que coinciden con mis gustos:");
            return preg1;}
        if(idioma.equals("Inglés")){
            setpreg1("The system has recommended me TV-shows that match my interest:");
            return preg1;}
        else{
            return preg1;}
    }
    
    public  void setpreg2(String s){preg2=s;}
    public String getpreg2(){
        if(idioma.equals("Español")){
            setpreg2("He empezado a seguir series que me haya recomendado el sistema:");
            return preg2;}
        if(idioma.equals("Inglés")){
            setpreg2("I started to follow TV- that the system have recommended me:");
            return preg2;}
        else{
            return preg2;}
    }
    
    public  void setpreg3(String s){preg3=s;}
    public String getpreg3(){
        if(idioma.equals("Español")){
            setpreg3("He descubierto nuevas series gracias a las recomendaciones:");
            return preg3;}
        if(idioma.equals("Inglés")){
            setpreg3("I discovered new TV-shows thanks to the recommendations:");
            return preg3;}
        else{
            return preg3;}
    }
    
    public  void setevaluar(String s){evaluar=s;}
    public String getevaluar(){
        if(idioma.equals("Español")){
            setevaluar("Evaluar");
            return evaluar;}
        if(idioma.equals("Inglés")){
            setevaluar("Evaluate");
            return evaluar;}
        else{
            return evaluar;}
    }
    
    public  void setgracias(String s){gracias=s;}
    public String getgracias(){
        if(idioma.equals("Español")){
            setgracias("Su valoración ha sido guardada en nuestro sistema. ¡Gracias por su ayuda!");
            return gracias;}
        if(idioma.equals("Inglés")){
            setgracias("Your rating has been saved in our system. Thank you for your help!");
            return gracias;}
        else{
            return gracias;}
    }
     
    public  void setguia(String s){guia=s;}
    public String getguia(){
        if(idioma.equals("Español")){
            setguia("Guia");
            return guia;}
        if(idioma.equals("Inglés")){
            setguia("Guide");
            return guia;}
        else{
            return guia;}
    }
    
    public  void setbienvguia(String s){bienvguia=s;}
    public String getbienvguia(){
        if(idioma.equals("Español")){
            setbienvguia("Bienvenido a la guía de la aplicación");
            return bienvguia;}
        if(idioma.equals("Inglés")){
            setbienvguia("Wellcome to the aplicattion guide");
            return bienvguia;}
        else{
            return bienvguia;}
    }
       
}
