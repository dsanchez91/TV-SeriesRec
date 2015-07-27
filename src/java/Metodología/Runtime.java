package Metodología;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.Vector;
import javax.faces.context.FacesContext;

/**
 * Esta parte de la metodología se ejecuta en tiempo de ejecución
 * sirve para encontrar las Series recomendadas para el
 * usuario que solicita la recomendación
 */

public class Runtime {

	/**************NUMERO DE ATRIBUTOS ***************/
	private static final int NUM_ATTRIBUTES = 14;
	private static final int NUM_ATTRIBUTES_SERIE = 5;

	public static final String DM_PAID = "dm_paid";
       
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

	/************RECUPERAR IDENTIFICADOR de USUARIO*************/
        private static final String USER_ID = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("identificador").toString();

        Vector<String> serie_ids;

	public Runtime(){
            String user_id;
            String accessRowData[];
            String nameAtts;
            String clusters[][], rules[][];
            double membclusters[];
            ResultSet resultClusters;
            String clustersQuery;

            user_id = getUserID();
            accessRowData = queryAccessRowData(user_id, NUM_ATTRIBUTES, getNameAttsStatic(), getNameAttsDemog());
            nameAtts = getNameAttsStatic()+", "+getNameAttsDemog();
            clusters = getClusters(nameAtts);
            rules = getRules(nameAtts);

            membclusters = new double[clusters.length];
            for(int i=0; i<clusters.length; i++){
                membclusters[i] = 0.0;
                for(int k=0; k<rules.length; k++){
                    double membRule=1.0;
                    for(int j=0; j<NUM_ATTRIBUTES; j++){
	    		boolean isFuzzy=false;
	    		double membAtt=1.0;
                        if(rules[k][j]!=null){
                            if(rules[k][j].substring(0,1).equals("[") || rules[k][j].substring(0,1).equals("]"))
	    			isFuzzy=true;

                            if(matchValue(rules[k][j], accessRowData[j], isFuzzy)){
                                if(isFuzzy)
                                    membAtt = Double.parseDouble(rules[k][j].substring(rules[k][j].indexOf(":")+1, rules[k][j].length()-1));
                                membRule = membRule*membAtt;
                            }
                        }
                    }
                    membclusters[i] = membclusters[i]+membRule;
	    	}
	    }

            //En membclusters tenemos el grado de pertenencia del usuario en los grupos:
            System.out.println("GRADO de pertenencia del USUARIO a los GRUPOS");
            for(int i=0;i<membclusters.length;i++)
                System.out.println("GRUPO: "+i+" -> pertenencia: "+membclusters[i]);

/***********************************************************************************************************/
            /*************************** OUTPUT *****************************/
            // 1 - Sacar índices de los 3 elementos con mayor valor de membsclusters
            int[] indicesmayorvalor = new int[3];
            double maximo=0;
            int indice=0;
            for(int j=0;j<3;j++){
                for(int i=0;i<membclusters.length;i++){
                    if(membclusters[i]>=maximo){
                        maximo=membclusters[i];
                        indice=i;
                    }
                }
                indicesmayorvalor[j]=indice;
                membclusters[indice]=0;
            }

            for(int i=0;i<indicesmayorvalor.length;i++)
                System.out.println("Grupos con mayor pertenencia: "+indicesmayorvalor[i]);

            serie_ids = new Vector<String>();
            
            // 2 - Consultar tabla dm_gruposseries lass ser con mayor frecuencia de acceso (4 o 6 sers)
            try {
                Connection con=null;
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                Statement stm = con.createStatement();

                for (int j=0;j<indicesmayorvalor.length;j++) {
                    clustersQuery = "SELECT idserie FROM gruposeries WHERE idgrupo="+indicesmayorvalor[j]+" ORDER BY contador DESC LIMIT 2";
                    resultClusters = stm.executeQuery(clustersQuery);
                    while(resultClusters.next())
                        serie_ids.add(resultClusters.getString("idserie"));

                    resultClusters.close();
                }

		} catch(SQLException e) {System.out.println("Error al conectar con la base de datos: "+e);}

            //Ahora en serie_ids tendremos los serie_id de las series recomendados:
            for(int i=0;i<serie_ids.size();i++)
                System.out.println("Identificadores de serie recomendados: "+serie_ids.get(i).toString());

            System.out.println("Fin de RUNTIME");
	}

        /**
         * Recuperar los identificadores de las series recomendados
         */
        public Vector getSeriesrecomendadas(){
            return serie_ids;
        }

        public static boolean matchValue(String rules,String access, boolean isFuzzy){
            if(rules.equals(access))
                return true;
            return false;
        }

        /**
         * Recuperar el nombre de los atributos estáticos
         */
	public static String getNameAttsStatic(){
		String output;

		output = ATTRIBUTE_1;
		output=output+", "+ATTRIBUTE_2;
		output=output+", "+ATTRIBUTE_3;
		output=output+", "+ATTRIBUTE_5;
		output=output+", "+ATTRIBUTE_6;
		output=output+", "+ATTRIBUTE_7;
		return output;
	}

        /**
         * Recuperar el nombre de los atributos demográficos
         */
	public static String getNameAttsDemog(){
		String output;

		output = ATTRIBUTE_8;
		output=output+", "+ATTRIBUTE_9;
		output=output+", "+ATTRIBUTE_10;
		output=output+", "+ATTRIBUTE_11;
                output=output+", "+ATTRIBUTE_12;
                output=output+", "+ATTRIBUTE_13;
                output=output+", "+ATTRIBUTE_14;
                output=output+", "+ATTRIBUTE_15;

		return output;
	}

        /**
         * Recuperar grupos
         */
	public static String[][] getClusters(String nameAtts){
	    ResultSet resultClusters;
            String clusters[][];
            String clustersQuery;
            int i;

		try {
  		    Connection con=null;
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                    Statement stm = con.createStatement();

                    clustersQuery = "SELECT "+nameAtts+" FROM grupos";
		    resultClusters = stm.executeQuery(clustersQuery);
                    int numRows=0;
                    while(resultClusters.next())
                            numRows++;

		    resultClusters = stm.executeQuery(clustersQuery);
		    clusters = new String[numRows][NUM_ATTRIBUTES];
		    i=0;
		    while(resultClusters.next()) {
                        for(int j=0; j<NUM_ATTRIBUTES; j++)
                            clusters[i][j] = resultClusters.getString(j+1);
                        i++;
                    }
		    resultClusters.close();
                    return clusters;
                        
		} catch(SQLException e) {e.printStackTrace();return null;}
	}

        /**
         * Recuperar reglas
         */
	public static String[][] getRules(String nameAtts){
		ResultSet resultRules;
		String rules[][];
		String rulesQuery;
		int i;

		try {
  		    Connection con=null;
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                    Statement stm = con.createStatement();

                    rulesQuery = "SELECT "+nameAtts+", class FROM reglas";
		    resultRules = stm.executeQuery(rulesQuery);
		    int numRows = 200;
		    rules = new String[numRows][NUM_ATTRIBUTES];
		    i=0;
		    while(resultRules.next()) {
		    	for(int j=0; j<NUM_ATTRIBUTES; j++)
		    		rules[i][j] = resultRules.getString(j+1);
                        i++;
                    }
		    resultRules.close();
                    con.close();
                    stm.close();
                    return rules;
                        
		} catch(SQLException e) {e.printStackTrace();return null;}
	}

        /**
         * Recuperar valores de los atributos de la base de datos
         */
	public static String[] queryAccessRowData(String user_id, int numAtts, String nameAttsStatic, String nameAttsDemog){
		String output[];
		String nameAttsDemogSplit[];
		String queryLastAccess, queryDemographicData;
		ResultSet resultLastAccess, resultDemographicData;
		int j;

		try {
  		    Connection con=null;
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                    Statement stm = con.createStatement();

                    output = new String[numAtts];
                    queryLastAccess = "SELECT " +nameAttsStatic+ " FROM acceso, serie, datospersonales"+
					" WHERE serie.id=acceso.Serie_id "+
					" AND datospersonales.Usuario_id=acceso.Usuario_id"+
					" AND acceso.Usuario_id='"+user_id+"' ORDER BY acceso.Fecha DESC";

                        System.out.println("Sentencia SQL: "+queryLastAccess);
			resultLastAccess = stm.executeQuery(queryLastAccess);
			resultLastAccess.first();
			j=0;
			output[j] = resultLastAccess.getString(j+1); 
			j++;
			for(j=1; j<NUM_ATTRIBUTES_SERIE; j++){
                            output[j] = resultLastAccess.getString(j+1);}
			output[j] = resultLastAccess.getString(j+1).substring(0,4); //date of birth
			resultLastAccess.close();

			queryDemographicData = "SELECT * FROM datospersonales, intereses WHERE datospersonales.Usuario_id='"+user_id+"' AND intereses.Usuario_id='"+user_id+"'";
                           resultDemographicData = stm.executeQuery(queryDemographicData);
                           nameAttsDemogSplit = nameAttsDemog.split(", ");
                           j++;

		   while(resultDemographicData.next()) {
                       /** sexo **/
                       String attName = "Sexo";
                       String value = resultDemographicData.getString("Sexo");
                       for(int m=0; m<nameAttsDemogSplit.length; m++)
                           if(nameAttsDemogSplit[m].equals(attName)){
                               output[j+m] = value;
                               m=nameAttsDemogSplit.length;
                           }
                       
                       /** ocupacion **/
                       attName = "Ocupación";
                       value = resultDemographicData.getString("Ocupación");
                       for(int m=0; m<nameAttsDemogSplit.length; m++)
                           if(nameAttsDemogSplit[m].equals(attName)){
                               output[j+m] = value;
                               m=nameAttsDemogSplit.length;
                           }
                       
                       /** provincia **/
                       attName = "Provincia";
                       value = resultDemographicData.getString("Provincia");
                       for(int m=0; m<nameAttsDemogSplit.length; m++)
                           if(nameAttsDemogSplit[m].equals(attName)){
                               output[j+m] = value;
                               m=nameAttsDemogSplit.length;
                           }
                       
                       /** nacionalidad **/
                       attName = "Nacionalidad";
                       value = resultDemographicData.getString("Nacionalidad");
                       for(int m=0; m<nameAttsDemogSplit.length; m++)
                           if(nameAttsDemogSplit[m].equals(attName)){
                               output[j+m] = value;
                               m=nameAttsDemogSplit.length;}
                                              /** sexo **/
                       
                       attName = "Actorpreferido1";
                       value = resultDemographicData.getString("Actorpreferido1");
                       for(int m=0; m<nameAttsDemogSplit.length; m++)
                           if(nameAttsDemogSplit[m].equals(attName)){
                               output[j+m] = value;
                               m=nameAttsDemogSplit.length;
                           }
                       
                       /** ocupacion **/
                       attName = "Generopreferido1";
                       value = resultDemographicData.getString("Generopreferido1");
                       for(int m=0; m<nameAttsDemogSplit.length; m++)
                           if(nameAttsDemogSplit[m].equals(attName)){
                               output[j+m] = value;
                               m=nameAttsDemogSplit.length;
                           }
                       
                       /** provincia **/
                       attName = "Directopreferido1";
                       value = resultDemographicData.getString("Directopreferido1");
                       for(int m=0; m<nameAttsDemogSplit.length; m++)
                           if(nameAttsDemogSplit[m].equals(attName)){
                               output[j+m] = value;
                               m=nameAttsDemogSplit.length;
                           }
                       
                       /** nacionalidad **/
                       attName = "Canalpreferido1";
                       value = resultDemographicData.getString("Canalpreferido1");
                       for(int m=0; m<nameAttsDemogSplit.length; m++)
                           if(nameAttsDemogSplit[m].equals(attName)){
                               output[j+m] = value;
                               m=nameAttsDemogSplit.length;
                           }

		   }
		   con.close();
		   stm.close();
		   resultDemographicData.close();
//
//                   System.out.println("Valor de Output: ");
//                   for(int i=0;i<output.length;i++)
//                       System.out.println(output[i]);
		   return output;

		} catch(SQLException e) {e.printStackTrace();return null;}
	}

	/* promedio duración */
	public static String getMinutes(String time){
		int minutes, hours, days;

		days = Integer.parseInt(time.substring(0,1));
		hours = Integer.parseInt(time.substring(3,4));
		minutes = Integer.parseInt(time.substring(6,7));

		minutes += days*1440 + hours*60;

		return String.valueOf(minutes);
	}

        /**
         * Recuperar valor del identificador de usuario que solicita la recomendación
         */
	public static String getUserID(){return USER_ID;}

	public static void main(String[] argv) {new Runtime();}

}
