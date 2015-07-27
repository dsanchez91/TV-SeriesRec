package CBAFuzzy;

import java.io.*;
import java.util.LinkedList;

public class CBAFuzzy {
    public CBAFuzzy(String[][] trainingData, int numClasses, double minSup, double minConf, LinkedList attributes, LinkedList fuzzifiedAttributes) throws IOException {
    	System.out.println("Fuzzy Classification Process Started...");
        /** Algoritmo APRIORI mandamos:
         * -datos de entrenamiento
         * -numero de clases: numero de grupos
         * -minimo de soporte
         * -minimo de confianza
         **/
    	AprioriTFP_CBA newClassification = new AprioriTFP_CBA(trainingData, numClasses, minSup, minConf);
        /** **/
        newClassification.inputDataSet(trainingData, attributes, fuzzifiedAttributes);
	newClassification.setNumRowsInInputSet();

	newClassification._menuOutputOptions();
	}
	
	
    /* -------------------------------------------------------------- */
    /*                                                                */
    /*                    OUTPUT METHODS                              */
    /*                                                                */
    /* -------------------------------------------------------------- */
	
    /* TWO DECIMAL PLACES */
    /** Converts given real number to real number rounded up to two decimal 
    places. 
    @param number the given number.
    @return the number to two decimal places. */
    protected static double twoDecPlaces(double number) {
	    int numInt = (int) ((number+0.005)*100.0);
		number = ((double) numInt)/100.0;
		return(number);
	}

}