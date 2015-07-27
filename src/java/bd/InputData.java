package bd;

import java.sql.*;
import java.util.LinkedList;

public class InputData {

	private static final int DISC_TYPE = 1;
	private static final int NUM_INTERVALS = 4;
	private static final int SEED = 10;
	private static final int NUM_CLUSTERS = 3;
	private static final double MIN_SUPPORT = 1.0;
	private static final double MIN_CONFIDENCE = 70.0;
	private static final int OPT_MENU_CBAFUZZY = 6;
	//not used yet
	private static final double PERCENTAGE_SIZE_OF_TEST_SET = 50.0;

	/**************NUMERO DE ATRIBUTOS ***************/
	private static final int NUM_ATTRIBUTES = 14;
	private static final int NUM_ATTRIBUTES_SERIE = 5;
	
        /*****************ATRIBUTOS*****************/
        /** Datos Serie **/
        public static final String ATTRIBUTE_1 = "Año";
	public static final String ATTRIBUTE_2 = "País";
	public static final String ATTRIBUTE_3 = "Género_nombre";
	public static final String ATTRIBUTE_5 = "Productora_nombre";
        public static final String ATTRIBUTE_6= "Director_id";
        /** Datos personales **/
	public static final String ATTRIBUTE_7 = "fechanacimiento";
	public static final String ATTRIBUTE_8 = "Sexo";
	public static final String ATTRIBUTE_9 = "Ocupación";
	public static final String ATTRIBUTE_10 = "Provincia";
	public static final String ATTRIBUTE_11 = "Nacionalidad";
        public static final String ATTRIBUTE_12 = "Actorpreferido1";
        public static final String ATTRIBUTE_13 = "Generopreferido1";
        public static final String ATTRIBUTE_14 = "Directopreferido1";
        public static final String ATTRIBUTE_15 = "Canalpreferido1";

        public static final String ATT_NUMERIC_1 = "Año";
	public static final String ATT_NUMERIC_2 = "fechanacimiento";


	public static LinkedList users, series;
	Statement stm;

        /** Recuperar atributos de la base de datos: **/
	public static LinkedList getAttributes(){
                /** Variable para resultado de la CONSULTASQL **/
		ResultSet rst;
                /** Lista de Atributos **/
		LinkedList attributes;	  
//
//		try {
			attributes = new LinkedList();
			  
			attributes.add(ATTRIBUTE_1);
			attributes.add(ATTRIBUTE_2);
			attributes.add(ATTRIBUTE_3);
			//attributes.add(ATTRIBUTE_4);
			attributes.add(ATTRIBUTE_5);
			attributes.add(ATTRIBUTE_6);

			attributes.add(ATTRIBUTE_7);
                        attributes.add(ATTRIBUTE_8);
			attributes.add(ATTRIBUTE_9);
			attributes.add(ATTRIBUTE_10);
			attributes.add(ATTRIBUTE_11);
                        attributes.add(ATTRIBUTE_12);
                        attributes.add(ATTRIBUTE_13);
                        attributes.add(ATTRIBUTE_14);
                        attributes.add(ATTRIBUTE_15);


                        return attributes;
	}
	
	public static String[][] queryGetInputData(LinkedList attributes){
                /** Matriz donde almacenamos el promedio de duración de cada usuario respecto a cada atributo **/
		String[][] output;
                /** Variables String para almacenar las CONSULTASSQL **/
		String queryAcesses, querySerieData, queryDateOfBirth, queryDemographicData, queryInterestData;
                /** Variables para resultados de las CONSULTASSQL **/
		ResultSet resultAcesses, resultSerie, resultDateOfBirth, resultDemographicData, resultInterestData;
                /** Variables auxiliares **/
		int j, numRows;
		
		try {
		   Connection conexion=null;
  		    conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                    
                        Statement instruccion = conexion.createStatement();

                        queryAcesses = "SELECT Usuario_id, Serie_id FROM acceso ORDER BY Fecha DESC limit 177"; //limit 1000
                        resultAcesses = instruccion.executeQuery(queryAcesses);

                        /** Lista de usuarios **/
                        users = new LinkedList();
                        /** Lista de series **/
                        series = new LinkedList();
                        /** Almacenamos en las listas los usuarios y los puntos de interes **/
                        while(resultAcesses.next()) {
                           users.add(resultAcesses.getString(1));
                               series.add(resultAcesses.getString(2));
                        }
                        resultAcesses.close();

                        numRows = users.size();
                        /** Establecer tamaño de la matriz de SALIDA a partir de los usuarios obtenidos y
                         el nº de atributos estudiados **/
                        output = new String[numRows][NUM_ATTRIBUTES];
                        /** String con todos los atributos para hacer la consulta de la base de datos **/
                        String SerieAtts = attributes.get(0).toString();
                        for(j=1; j<NUM_ATTRIBUTES_SERIE; j++)
                               SerieAtts = SerieAtts+", "+attributes.get(j).toString();

                        /** Estudiamos los atributos de Serie para cada uno de los --USUARIOS-- **/
                        for(int i=0; i<numRows; i++){
                               j=0;
                               /***********************************************************************************/
                               /** CONSULTASQL para obtener todos los atributos de un punto de interes especifico **/
                               querySerieData = "SELECT "+SerieAtts+" FROM serie WHERE id='"+series.get(i).toString()+"'";
                               resultSerie = instruccion.executeQuery(querySerieData);
                               resultSerie.next();
                               /** Almacenar en la SALIDA el promedio de duración (que es el atributo 1 del String
                                * SerieAtts) del usuario -> pasado a minutos y el resto de atributos en el bucle for **/
                               output[i][j] = resultSerie.getString(1); //promedio de duración
                               for(j=1; j<NUM_ATTRIBUTES_SERIE; j++){
                                       output[i][j] = resultSerie.getString(j+1);}
                               //Deberia cerrar aquí resultSerie???
                               
                               /***********************************************************************************/
                               /** CONSULTASQL para obtener la fecha de nacimiento de un usuario determinado **/
                               queryDateOfBirth = "SELECT Fechanacimiento FROM datospersonales WHERE Usuario_id='"+users.get(i).toString()+"'";
                               resultDateOfBirth = instruccion.executeQuery(queryDateOfBirth);
                               resultDateOfBirth.next();
                               /** Almacenar en la SALIDA la fecha de nacimiento de cada usuario **/
                               output[i][j] = resultDateOfBirth.getString(1).substring(0,4); //fecha de nacimiento
                               resultDateOfBirth.close();
                               
                               /***********************************************************************************/
                               /** CONSULTASQL para obtener nombre y valor de los atributos .... **/
                               queryDemographicData = "SELECT * FROM datospersonales WHERE datospersonales.Usuario_id='"+users.get(i).toString()+"'";
                               resultDemographicData = instruccion.executeQuery(queryDemographicData);
                               while(resultDemographicData.next()) {
                                   /** sexo **/
                                   String attName = "Sexo";
                                   String value = resultDemographicData.getString("Sexo");
                                   if(attributes.contains(attName)){
                                       j++;
                                       output[i][j] = value;}
                                   
                                   
                                   /** ocupacion **/
                                   attName = "Ocupación";
                                   value = resultDemographicData.getString("Ocupación");
                                   if(attributes.contains(attName)){
                                       j++;
                                       output[i][j] = value;}
                                   
                                   /** provincia **/
                                   attName = "Provincia";
                                   value = resultDemographicData.getString("Provincia");
                                   if(attributes.contains(attName)){
                                       j++;
                                       output[i][j] = value;}
                                   

                                   /** Nacionalidad **/
                                   attName = "Nacionalidad";
                                   value = resultDemographicData.getString("Nacionalidad");
                                   if(attributes.contains(attName)){
                                       j++;
                                       output[i][j] = value;}

                               }
                               resultDemographicData.close();
                               /***********************************************************************************/
                               /** CONSULTASQL para obtener nombre y valor de los atributos intereses.... **/
                               queryInterestData = "SELECT * FROM intereses WHERE intereses.Usuario_id='"+users.get(i).toString()+"'";
                               resultInterestData = instruccion.executeQuery(queryInterestData);
                               while(resultInterestData.next()) {
                                   /** sexo **/
                                   String attName = "Actorpreferido1";
                                   String value = resultInterestData.getString("Actorpreferido1");
                                   if(attributes.contains(attName)){
                                       j++;
                                       output[i][j] = value;}
                                   
                                   
                                   /** ocupacion **/
                                   attName = "Generopreferido1";
                                   value = resultInterestData.getString("Generopreferido1");
                                   if(attributes.contains(attName)){
                                       j++;
                                       output[i][j] = value;}
                                   
                                   /** provincia **/
                                   attName = "Directopreferido1";
                                   value = resultInterestData.getString("Directopreferido1");
                                   if(attributes.contains(attName)){
                                       j++;
                                       output[i][j] = value;}
                                   

                                   /** Nacionalidad **/
                                   attName = "Canalpreferido1";
                                   value = resultInterestData.getString("Canalpreferido1");
                                   if(attributes.contains(attName)){
                                       j++;
                                       output[i][j] = value;}

                               }
                               resultInterestData.close();
                       }
                        


                       conexion.close();
                       instruccion.close();
                       
                       /** retornamos la SALIDA con todos los valores de los atributos para cada usuario **/
                       return output;
		} catch(SQLException e) {
                    e.printStackTrace();
                    return null;}
	}
	
	/* promedio duración	 */
	public static String getMinutes(String time){
		int minutes, hours, days;
		
		days = Integer.parseInt(time.substring(0,1));
		hours = Integer.parseInt(time.substring(3,4));
		minutes = Integer.parseInt(time.substring(6,7));
		
		minutes += days*1440 + hours*60;
		
		return String.valueOf(minutes);
	}
	
	public static LinkedList getNumericalVariables(String inputData[][], LinkedList attributes){
		LinkedList values[], output, numerical_att_ind;
	
		numerical_att_ind = new LinkedList();

		numerical_att_ind.add(String.valueOf(attributes.indexOf(ATT_NUMERIC_1)));
		numerical_att_ind.add(String.valueOf(attributes.indexOf(ATT_NUMERIC_2)));
			
		values = new LinkedList[numerical_att_ind.size()];
		output = new LinkedList();
		
		for(int j=0; j<numerical_att_ind.size(); j++){
			int ind_current_att = Integer.parseInt(numerical_att_ind.get(j).toString());
			values[j] = new LinkedList();
			for(int i=0; i<inputData.length; i++){
				String currentValue = inputData[i][ind_current_att];
				if(!values[j].contains(currentValue))
                                    System.out.println("Entro if value");
					values[j].add(currentValue);
				if(values[j].size()>NUM_INTERVALS){
                                        System.out.println("Entro if añadir");
					output.add(attributes.get(ind_current_att));
                                        
					break;
				}
			}
		}
		return output;
	}
	
	public static int getDiscType(){
		return DISC_TYPE;}

	public static int getNumIntervals(){
		return NUM_INTERVALS;}

	public static double getSizeTestset(){
		return PERCENTAGE_SIZE_OF_TEST_SET;}	
	
	public static double getMinSupport(){
		return MIN_SUPPORT;}
	
	public static int getOptMenuCBAFuzzy(){
		return OPT_MENU_CBAFUZZY;}
	
	public static double getMinConfidence(){
		return MIN_CONFIDENCE;}
	
	public static int getNumClusters(){
		return NUM_CLUSTERS;}

	public static int getSeed(){
		return SEED;}
	  
	public static String[][] getTempString(LinkedList attributes){
		  String values[][];
	    
	      values = new String[13][7];
		  values[0][0] = "85";
		  values[1][0] = "90";
		  values[2][0] = "86";
		  values[3][0] = "96";
		  values[4][0] = "80";
		  values[5][0] = "70";
		  values[6][0] = "65";
		  values[7][0] = "95";
		  values[8][0] = "70";
		  values[9][0] = "80";
		  values[10][0] = "70";
		  values[11][0] = "90";
		  values[12][0] = "75";
		  
		  values[0][1] = "85";
		  values[1][1] = "80";
		  values[2][1] = "83";
		  values[3][1] = "70";
		  values[4][1] = "68";
		  values[5][1] = "65";
		  values[6][1] = "64";
		  values[7][1] = "72";
		  values[8][1] = "69";
		  values[9][1] = "75";
		  values[10][1] = "75";
		  values[11][1] = "72";
		  values[12][1] = "81";

		  values[0][2] = "sunny";
		  values[1][2] = "sunny";
		  values[2][2] = "overcast";
		  values[3][2] = "rainy";
		  values[4][2] = "rainy";
		  values[5][2] = "rainy";
		  values[6][2] = "overcast";
		  values[7][2] = "sunny";
		  values[8][2] = "sunny";
		  values[9][2] = "rainy";
		  values[10][2] = "sunny";
		  values[11][2] = "overcast";
		  values[12][2] = "overcast";
		  
		  values[0][3] = "FALSE";
		  values[1][3] = "TRUE";
		  values[2][3] = "FALSE";
		  values[3][3] = "FALSE";
		  values[4][3] = "FALSE";
		  values[5][3] = "TRUE";
		  values[6][3] = "TRUE";
		  values[7][3] = "FALSE";
		  values[8][3] = "FALSE";
		  values[9][3] = "FALSE";
		  values[10][3] = "TRUE";
		  values[11][3] = "TRUE";
		  values[12][3] = "FALSE";
		  
		  values[0][4] = "no";
		  values[1][4] = "no";
		  values[2][4] = "yes";
		  values[3][4] = "yes";
		  values[4][4] = "yes";
		  values[5][4] = "no";
		  values[6][4] = "yes";
		  values[7][4] = "no";
		  values[8][4] = "yes";
		  values[9][4] = "yes";
		  values[10][4] = "yes";
		  values[11][4] = "yes";
		  values[12][4] = "yes";
		  
		  values[0][5] = "FSE";
		  values[1][5] = "TE";
		  values[2][5] = "FSE";
		  values[3][5] = "FSE";
		  values[4][5] = "FSE";
		  values[5][5] = "TE";
		  values[6][5] = "TE";
		  values[7][5] = "FSE";
		  values[8][5] = "FSE";
		  values[9][5] = "FSE";
		  values[10][5] = "UE";
		  values[11][5] = "UE";
		  values[12][5] = "LSE";
		  
		  values[0][6] = "85";
		  values[1][6] = "90";
		  values[2][6] = "86";
		  values[3][6] = "96";
		  values[4][6] = "80";
		  values[5][6] = "70";
		  values[6][6] = "65";
		  values[7][6] = "95";
		  values[8][6] = "70";
		  values[9][6] = "80";
		  values[10][6] = "70";
		  values[11][6] = "90";
		  values[12][6] = "75";
		  
		  return values;
	  }
	
	 public static LinkedList getUsers(){
		  return users;}
	 
	 public static LinkedList getSeries(){
		  return series;}
}