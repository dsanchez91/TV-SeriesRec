package kmeans;

import  java.util.*;
import  java.io.*;
import bd.UpdateData;

public class ClusterEvaluation implements Serializable {

  private static final long serialVersionUID = 1L;

 /** Resultados de los grupos de entrenamiento */
  private StringBuffer m_clusteringResults;
  /** holds the number of clusters found by the clusterer */
  private int m_numClusters;
  /** holds the assigments of instances to clusters for a particular testing dataset */
  private double [] m_clusterAssignments;
    /* holds the average log likelihood for a particular testing dataset
     if the clusterer is a DensityBasedClusterer */
  private double m_logL;
  /** will hold the mapping of classes to clusters (for class based 
      evaluation) */
  private int [] m_classToCluster = null;
  static int optionMenu=0;
  static String[][] strCentroids;
  
  /* return the results of clustering.
   * @return a string detailing the results of clustering a data set
   */
  public String clusterResultsToString() {
    return m_clusteringResults.toString();}

  /* Return the number of clusters found for the most recent call to
   * evaluateClusterer
   * @return the number of clusters found
   */
  public int getNumClusters() {
    return m_numClusters;}

  /* Return an array of cluster assignments corresponding to the most
   * recent set of instances clustered.
   * @return an array of cluster assignments
   */
  public double [] getClusterAssignments() {
    return m_clusterAssignments;}

  /* Return the array (ordered by cluster number) of minimum error class to
   * cluster mappings
   * @return an array of class to cluster mappings
   */
  public int [] getClassesToClusters() {
    return m_classToCluster;}

  /* Return the log likelihood corresponding to the most recent
   * set of instances clustered.
   *
   * @return a <code>double</code> value
   */
  public double getLogLikelihood() {
    return m_logL;}
  
  /* Evaluates a clusterer with the options given in an array of
   * strings. It takes the string indicated by "-t" as training file, the
   * string indicated by "-T" as test file.
   * If the test file is missing, a stratified ten-fold
   * cross-validation is performed (distribution clusterers only).
   * Using "-x" you can change the number of
   * folds to be used, and using "-s" the random seed.
   * If the "-p" option is present it outputs the classification for
   * each test instance. If you provide the name of an object file using
   * "-l", a clusterer will be loaded from the given file. If you provide the
   * name of an object file using "-d", the clusterer built from the
   * training data will be saved to the given file.
   *
   * @param clusterer machine learning clusterer
   * @param options the array of string containing the options
   * @exception Exception if model could not be evaluated successfully
   * @return a string describing the results 
   */
  public static String[][] evaluateClusterer (KMeans clusterer, String[][] trainingData, LinkedList attributes) throws Exception {
	/** matriz de SALIDA **/
        String outputData[][];
        
	LinkedList translator[];
        	
	clusterer.setNumClusters(bd.InputData.getNumClusters());
	clusterer.setSeed(bd.InputData.getSeed());

        /** Ordenar para cada atributo su dato de entrenamiento **/
	translator = getIndexOfAtts(trainingData);
        /** Construir los grupos **/
        
	strCentroids = clusterer.buildClusterer(trainingData, attributes, translator);
        /** Insertamos en la base de datos los nuevos grupos para los atributos especificados **/
        
        UpdateData.updateClusters(strCentroids, attributes);
        /** Supongo que aquí ordenamos un poco los grupos para devolverlos **/
        outputData = getOutputData(trainingData, clusterer, translator, attributes);

        return outputData;
  }

  static String[][] getOutputData(String[][] trainingData, KMeans clusterer, LinkedList[] translator, LinkedList attributes){
	  String output[][];
	  
	  output = new String[trainingData.length][trainingData[0].length+1];
	  	  
	  for(int i=0; i<trainingData.length; i++) {
		  for(int j=0; j<trainingData[0].length; j++)
			  output[i][j] = trainingData[i][j];
		  try {
		      output[i][output[0].length-1] = "G"+String.valueOf(clusterer.clusterInstance(trainingData[i], translator, attributes));
		  } catch (Exception e) {
		      e.printStackTrace();
		  }
	  }
	  
	  return output;
  }
  
  static LinkedList[] getIndexOfAtts(String[][] train){
	  LinkedList[] output;
	  int numAtts = train[0].length;
	  
	  output = new LinkedList[numAtts];
	  for(int j=0; j<numAtts; j++) //initialize
		  output[j] = new LinkedList();
	  
	  for(int i=0; i<train.length; i++)
		  for(int j=0; j<numAtts; j++)
			  if(!output[j].contains(train[i][j]))
				  output[j].add(train[i][j]);
	  
	  return output;
  }  
  
  
  /**
   * Classifies a given instance. Either this or distributionForInstance()
   * needs to be implemented by subclasses.
   *
   * @param instance the instance to be assigned to a cluster
   * @return the number of the assigned cluster as an integer
   * @exception Exception if instance could not be clustered
   * successfully
   */
  public int clusterInstance(String[] instance, int numClust) throws Exception {

    double [] dist = distributionForInstance(instance, numClust);

    if (dist == null)
      throw new Exception("Null distribution predicted");
    if (Utils.sum(dist) <= 0)
      throw new Exception("Unable to cluster instance");

    return Utils.maxIndex(dist);
  }

  /**
   * Predicts the cluster memberships for a given instance.  Either
   * this or clusterInstance() needs to be implemented by subclasses.
   *
   * @param instance the instance to be assigned a cluster.
   * @return an array containing the estimated membership 
   * probabilities of the test instance in each cluster (this 
   * should sum to at most 1)
   * @exception Exception if distribution could not be 
   * computed successfully 
   */
  public double[] distributionForInstance(String[] instance, int numClust) throws Exception {

    double[] d = new double[numClust];
    d[clusterInstance(instance, numClust)] = 1.0;
  
    return d;
  }
}

