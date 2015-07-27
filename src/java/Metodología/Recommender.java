package Metodología;

import java.util.LinkedList;
import kmeans.ClusterEvaluation;
import kmeans.KMeans;
import CBAFuzzy.CBAFuzzy;
import discretfuzzy.*;
import bd.InputData;

/**
 * Esta parte de la metodología se realiza off-line, se generan los
 * grupos de transacciones y las reglas de clasificación:
 */

public class Recommender {

    /** Datos de entrenamiento - Datos de enborronado **/
    String[][] trainningData, fuzzyfiedData;

    public Recommender(){
        /** Grupo de Datos - **/
        String[][] clusteredData, inputCBA;
        /** Atributos **/
        LinkedList attributes;

        try {

/****************************** GENERAR GRUPOS DE TRANSACCIONES **********************************/
/*************************************************************************************************/
            /** Recuperamos que atributos queremos estudiar de la base de datos **/
            attributes = InputData.getAttributes();
            
            /*_________________CONJUNTO DE ENTRENAMIENTO_________________________*/
            /** Recuperamos los datos de la base de datos **/
            /** En trainningData tenemos la matriz de SALIDA que se ha ido generando en la
             * función con los valores de los atributos del GRUPO para cada usuario **/
            trainningData = InputData.queryGetInputData(attributes);

            System.out.println("*********************  Recupero CONJUNTO DE ENTRENAMIENTO *****************");

            /*___________________ALGORITMOS K-MEDIAS_________________________*/
            /*_________________GRUPOS DE TRANSACCIONES_______________________*/
            /** Grupo de datos evaluados mandamos:
             * -algoritmo K-Medias
             * -conjunto de entrenamiento
             * -atributos que queremos estudiar */
            /** En clusteredData tenemos la matriz de SALIDA que se ha ido generando en la
             * función con los valores medios o frecuentes de cada atributo de cada grupo **/
                    
//                    for(int i=0;i<trainningData.length;i++)
//                        for(int j=0;j<trainningData[i].length;j++)
//                            System.out.println(trainningData[i][j].toString());

            clusteredData = ClusterEvaluation.evaluateClusterer(new KMeans(), trainningData, attributes);
            System.out.println("********************** Creamos GRUPOS de usuarios **********************");

            /*_________________CONJUNTO DE ITEMS FRECUENTES__________________*/
            /** Subimos a la base de datos la lista de items frecuentes mandamos:
             * -grupo de datos
             * -usuarios obtenidos de la base de datos
             * -puntos de interes obtenidos de la base de datos
             **/
            bd.UpdateData.updateListOfFreqItems(clusteredData, bd.InputData.getUsers(), bd.InputData.getSeries());
            System.out.println("******************************* Clustering process: OK! **************************");
                        
/*************************************************************************************************/
/****************************** GENERAR REGLAS DE CLASIFICACIÓN **********************************/
/*************************************************************************************************/
            /** Discretización y Emborronamiento de los datos mandamos:
             * -Datos de entrenamiento
             * -Atributos que queremos estudiar
             **/
            //System.out.println(attributes);
            DiscretFuzzy df = new DiscretFuzzy(trainningData, attributes);
            fuzzyfiedData = df.fuzzyfiedData;

          /*_______________NUEVO CONJUNTO DE ENTRENAMIENTO_________________________*/
            /** Cogemos del grupo de discretización y emborramiento las variablaes que se hayan
             * discretizado y las conjugamos con las variables de grupo que no se han discretizado,
             * para mandar esas variables al algoritmo CBA, mandamos:
             * -Grupo de datos evaluados
             * -Datos discretizados y enborronados
             * -Variables de los datos discretizados y emborronados que se han discretizado
            **/
            inputCBA = getInputToCBA(clusteredData, fuzzyfiedData, df.getVariablesToDiscretize());

           /*_________________ALGORITMO CBA-Fuzzy_________________________*/
            /** Llamada al ALGORITMO CBAFuzzy mandamos:
             * -Datos procesados para CBA
             * -Numero de grupos -> recuperado de la base de datos
             * -Minimo soporte -> recuperado de la base de datos
             * -Minima confianza -> recuperado de la base de datos
             * -Atributos que queremos estudiar
             * -Variables de los datos discretizados y emborronados para discretizar
             **/
            new CBAFuzzy(inputCBA, bd.InputData.getNumClusters(), bd.InputData.getMinSupport(), bd.InputData.getMinConfidence(), attributes, df.getVariablesToDiscretize());
            System.out.println("Fuzzy Classification process: OK!");
            System.out.println("");
            System.out.println("Recommendation Model built sucessfully!");

/*************************************************************************************************/
/*************************************************************************************************/
	    
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();}
    }

    String[][] getInputToCBA(String[][] clusteredData, String[][] fuzzyfiedData, LinkedList variablesToDiscretize){
        String[][] output;
		
        output = new String[clusteredData.length][clusteredData[0].length];
        for(int i=0; i<output.length; i++)
            for(int j=0; j<output[0].length; j++){
                if(variablesToDiscretize.contains(String.valueOf(j)))
                    output[i][j] = fuzzyfiedData[i][j];
                else
                    output[i][j] = clusteredData[i][j];
            }
        return output;
    }

    public static void main(String[] argv) {
        new Recommender();
    }
}