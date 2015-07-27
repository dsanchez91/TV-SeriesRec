package kmeans;

import  java.util.*;

/**
 * Simple k means clustering class.
 *
 * Valid options are:<p>
 *
 * -N <number of clusters> <br>
 * Specify the number of clusters to generate. <p>
 *
 * -S <seed> <br>
 * Specify random number seed. <p>
 *
 * -m <option Menu>
 * 6 = do not display menu
 *
 * @author Mark Hall (mhall@cs.waikato.ac.nz)
 * @author Eibe Frank (eibe@cs.waikato.ac.nz)
 * @version $Revision: 1.19.2.4 $
 * @see Clusterer
 * @see OptionHandler
 */
public class KMeans {

   private static final long serialVersionUID = 1L;
   protected static final double MISSING_VALUE = Double.NaN;
   
  /* number of clusters to generate */
  private int m_NumClusters = 2;
  /* holds the cluster centroids */
  public Cluster m_ClusterCentroids;

  /* Holds the standard deviations of the numeric attributes in each cluster */
  private Cluster m_ClusterStdDevs;

  
  /**
   * For each cluster, holds the frequency counts for the values of each 
   * nominal attribute
   */
  //private int [][][] m_ClusterNominalCounts;

  /**
   * The number of instances in each cluster
   */
  private int [] m_ClusterSizes;

  /**
   * random seed
   */
  private int m_Seed = 10;

  /**
   * attribute min values
   */
  private double [] m_Min;
  
  /**
   * attribute max values
   */
  private double [] m_Max;

  /**
   * Keep track of the number of iterations completed before convergence
   */
  private int m_Iterations = 0;

  private double [] m_squaredErrors;

  /**
   * Returns a string describing this clusterer
   * @return a description of the evaluator suitable for
   * displaying in the explorer/experimenter gui
   */
  public String globalInfo() {
    return "Cluster data using the k means algorithm";
  }

  /**
   * Generates a clusterer. Has to initialize all fields of the clusterer
   * that are not being set via options.
   *
   * @param data set of instances serving as training data 
   * @exception Exception if the clusterer has not been 
   * generated successfully
   */
  public String[][] buildClusterer(String[][] data, LinkedList attributes, LinkedList[] translator) throws Exception {
	String[][] strReturn;
	m_Iterations = 0;

    m_Min = new double [attributes.size()];
    m_Max = new double [attributes.size()];
    for (int i = 0; i < attributes.size(); i++)
      m_Min[i] = m_Max[i] = Double.NaN;
    
    m_ClusterCentroids = new Cluster(attributes.size()); //[m_NumClusters][attributes.length];
    int[] clusterAssignments = new int [data.length];

    for (int i = 0; i < data.length; i++)
      updateMinMax(data[i], translator, attributes);
    
    Random RandomO = new Random(m_Seed);
    int instIndex;
    HashMap initC = new HashMap();
    hashKey hk = null;
    
    for (int j = data.length - 1; j >= 0; j--) {
      instIndex = RandomO.nextInt(j+1);
      
      hk = new hashKey(data[instIndex], attributes.size(), true, translator, attributes);
      if (!initC.containsKey(hk)) {
    	  m_ClusterCentroids.addInstance(data[instIndex]);
		initC.put(hk, null);
      }
      //instances.swap(j, instIndex);
      data = swap(data, j, instIndex);
      if(m_ClusterCentroids.getNumInstances() == m_NumClusters)
    	  break;
    }

    m_NumClusters = m_ClusterCentroids.getNumInstances();
    
    int i;
    boolean converged = false;
    int emptyClusterCount;
    
    //ex. tempI[clusterInd][att][instance]
    Cluster[] tempI = new Cluster[m_NumClusters];
    //String[][][] tempI = new String[m_NumClusters];
  
    m_squaredErrors = new double [m_NumClusters];
    //m_ClusterNominalCounts = new int [m_NumClusters][attributes.length][0];
    while (!converged) {
      emptyClusterCount = 0;
      m_Iterations++;
      converged = true;
      for (i = 0; i < data.length; i++) {
			//Instance toCluster = instances.instance(i);
    	    String[] toCluster = data[i];
			int newC = clusterProcessedInstance(toCluster, true, translator, attributes);
			if (newC != clusterAssignments[i])
			  converged = false;
			clusterAssignments[i] = newC;
      }
      
      // update centroids      
      //m_ClusterCentroids = new Instances(instances, m_NumClusters);
      m_ClusterCentroids = new Cluster(attributes.size());
      for (i = 0; i < m_NumClusters; i++)
    	  tempI[i] = new Cluster(attributes.size());
      for (i = 0; i < data.length; i++)
    	  tempI[clusterAssignments[i]].addInstance(data[i]);
      
      for (i = 0; i < m_NumClusters; i++) {
		double [] vals = new double[attributes.size()];
		if (tempI[i].getNumInstances() == 0) {
		  emptyClusterCount++;
		} else {
		  for (int j = 0; j<attributes.size(); j++) {
		     vals[j] = tempI[i].meanOrMode(j, translator, attributes);
		     //m_ClusterNominalCounts[i][j] = tempI[i].attributeStats(j).nominalCounts;
		  }
		  String temp[] = new String[vals.length];
		  for(int k=0; k<temp.length; k++){
			  if(!Utils.isCategoricalAtt(attributes, k)) //numerical
				  temp[k] = String.valueOf(vals[k]);
			  else
				  temp[k] = translator[k].get((int)vals[k]).toString();
		  }
		  m_ClusterCentroids.addInstance(temp);
		}
	  }

      if (emptyClusterCount > 0) {
		m_NumClusters -= emptyClusterCount;
		//tempI = new Instances[m_NumClusters];
		tempI = new Cluster[m_NumClusters];
      }
      if (!converged) {
		m_squaredErrors = new double [m_NumClusters];
		//m_ClusterNominalCounts = new int [m_NumClusters][attributes.length][0];
      }
    }
    m_ClusterStdDevs = new Cluster(attributes.size());
    m_ClusterSizes = new int [m_NumClusters];
    for (i = 0; i < m_NumClusters; i++) {
      double [] vals2 = new double[attributes.size()];
      for (int j = 0; j < attributes.size(); j++) {
		if(!Utils.isCategoricalAtt(attributes, j))
		  vals2[j] = Math.sqrt(tempI[i].variance(attributes, j));
		else
		  vals2[j] = MISSING_VALUE;
      }
      String temp2[] = new String[vals2.length];
	  for(int k=0; k<temp2.length; k++){  
		  if(!Utils.isCategoricalAtt(attributes, k)) //numerical
			  temp2[k] = String.valueOf(vals2[k]);
		  else
			  temp2[k] = translator[k].get((int)vals2[k]).toString();
	  }
	  
      m_ClusterStdDevs.addInstance(temp2);
      m_ClusterSizes[i] = tempI[i].getNumInstances();
    }
    
    strReturn = new String[m_ClusterCentroids.getNumInstances()+1][attributes.size()];
	//i=0 atribute names
    for(int j=0; j<attributes.size(); j++)
    	strReturn[0][j] = attributes.get(j).toString();

    //write centroid data in the string matrix
    for(i=1; i<m_ClusterCentroids.getNumInstances()+1; i++) //instances
		for(int j=0; j<attributes.size(); j++) //attributes
			strReturn[i][j] = m_ClusterCentroids.instances[j].get(i-1).toString();
    return strReturn;
  }

  /*
   * 
   */
  public LinkedList[] add(LinkedList[] centroids, String[] instance){
	  for(int i=0; i<centroids.length; i++)
		  centroids[i].add(instance[i]);

	  return centroids;
  }
  
  /*
   * 
   */
  public String[][] swap(String[][] data, int ind1, int ind2){
	  String[] temp;
	  
	  temp=data[ind1];
	  data[ind1] = data[ind2];
	  data[ind2] = temp;
	  
	  return data;
  }
  
  /**
   * clusters an instance that has been through the filters
   *
   * @param instance the instance to assign a cluster to
   * @param updateErrors if true, update the within clusters sum of errors
   * @return a cluster number
   */
  private int clusterProcessedInstance(String[] instance, boolean updateErrors, LinkedList[] translator, LinkedList attributes) {
    double minDist = Integer.MAX_VALUE;
    int bestCluster = 0;
    for (int i = 0; i < m_NumClusters; i++) {
      double dist = distance(instance, getInstance(m_ClusterCentroids, i), translator, attributes);
      if (dist < minDist) {
		 minDist = dist;
		 bestCluster = i;
      }
    }
    if (updateErrors)
      m_squaredErrors[bestCluster] += minDist;
    return bestCluster;
  }

  /*
   * 
   */
  private String[] getInstance(Cluster centroids, int ind){
	  String[] output;
	  
	  output = new String[centroids.instances.length];
	  for(int i=0; i<output.length; i++)
		  output[i] = centroids.instances[i].get(ind).toString();
	  return output;
  }
  
  /**
   * Classifies a given instance.
   *
   * @param instance the instance to be assigned to a cluster
   * @return the number of the assigned cluster as an interger
   * if the class is enumerated, otherwise the predicted value
   * @exception Exception if instance could not be classified
   * successfully
   */
  public int clusterInstance(String[] instance, LinkedList[] translator, LinkedList attributes) throws Exception {
    //m_ReplaceMissingFilter.input(instance);
    //m_ReplaceMissingFilter.batchFinished();
    //Instance inst = m_ReplaceMissingFilter.output();

    return clusterProcessedInstance(instance, false, translator, attributes);
  }

  /**
   * Calculates the distance between two instances
   *
   * @param test the first instance
   * @param train the second instance
   * @return the distance between the two given instances, between 0 and 1
   */          
  private double distance(String first[], String[] second, LinkedList[] translator, LinkedList attributes) {  

    double distance = 0;
    int firstI, secondI;

    for(int p1 = 0,p2 = 0; p1 < first.length || p2 < second.length;) {
      if(p1 >= first.length)
    	  firstI = m_ClusterCentroids.getNumInstances();
      else
    	  //firstI = first.index(p1); 
    	  firstI = p1;
      if(p2 >= second.length)
    	  secondI = m_ClusterCentroids.getNumInstances();
      else
    	  //secondI = second.index(p2);
    	  secondI = p2;
      /*if (firstI == m_ClusterCentroids.classIndex())
		p1++; continue;
      if (secondI == m_ClusterCentroids.classIndex())
		p2++; continue;*/
      double diff;
      double val1, val2;
      if(Utils.isCategoricalAtt(attributes, p1))
		  val1 = (double)translator[p1].indexOf(first[p1]);
	  else
		  val1 = Double.parseDouble(first[p1]);
      if(Utils.isCategoricalAtt(attributes, p2))
		  val2 = (double)translator[p2].indexOf(second[p2]);
	  else
		  val2 = Double.parseDouble(second[p2]);
      
      if (firstI == secondI) {
    	  //diff = difference(firstI,first.valueSparse(p1),second.valueSparse(p2));
    	  diff = difference(firstI,attributes,val1,val2);
    	  p1++;
    	  p2++;
      } else if (firstI > secondI) {
    	  diff = difference(secondI,attributes,0,val2);
    	  p2++;
      } else {
    	  diff = difference(firstI,attributes,val1,0);
    	  p1++;
      }
      distance += diff * diff;
    }
    
    //return Math.sqrt(distance / m_ClusterCentroids.numAttributes());
    return distance;
  }

  /**
   * Tests if the given value codes "missing".
   *
   * @param val the value to be tested
   * @return true if val codes "missing"
   */
  public static boolean isMissingValue(double val) {

    return Double.isNaN(val);
  }
  
  /**
   * Computes the difference between two given attribute
   * values.
   */
  private double difference(int index, LinkedList attributes, double val1, double val2) {
    //switch (m_ClusterCentroids.attribute(index).type()) {
	if(Utils.isCategoricalAtt(attributes, index)) {
	      // If attribute is nominal
	      if (isMissingValue(val1) || isMissingValue(val2) || ((int)val1 != (int)val2))
	    	  return 1;
	      else
	    	  return 0;
	} else {
        // If attribute is numeric
        if (isMissingValue(val1) || isMissingValue(val2)) {
        	if (isMissingValue(val1) && isMissingValue(val2)) {
        		return 1;
        	} else {
        		double diff;
        		if (isMissingValue(val2)) {
        			diff = norm(val1, index);
        		} else {
        			diff = norm(val2, index);
        		}
        		if (diff < 0.5) {
        			diff = 1.0 - diff;
        		}
        		return diff;
        	}
        } else {
    	    return norm(val1, index) - norm(val2, index);
        }
    }
  }

  /**
   * Normalizes a given value of a numeric attribute.
   *
   * @param x the value to be normalized
   * @param i the attribute's index
   */
  private double norm(double x, int i) {

    if (Double.isNaN(m_Min[i]) || Utils.eq(m_Max[i],m_Min[i])) {
      return 0;
    } else {
      return (x - m_Min[i]) / (m_Max[i] - m_Min[i]);
    }
  }

  /**
   * Updates the minimum and maximum values for all the attributes
   * based on a new instance.
   *
   * @param instance the new instance
   */
  private void updateMinMax(String instance[], LinkedList[] translator, LinkedList attributes) {  
    double numericVal;
    for (int j = 0;j < instance.length; j++) {
      if(Utils.isCategoricalAtt(attributes, j))
         numericVal = (double)translator[j].indexOf(instance[j]);
      else
    	 numericVal = Float.parseFloat(instance[j]);
      
      if (!instance[j].isEmpty()) {
		if (Double.isNaN(m_Min[j])) {
		    m_Min[j] = numericVal;
		    m_Max[j] = numericVal;
		} else {
		    if (numericVal < m_Min[j])
		      m_Min[j] = numericVal;
		    else {
		      if (numericVal > m_Max[j])
		        m_Max[j] = numericVal;
		    }
		}
      }
    }
  }
  
  /**
   * Returns the number of clusters.
   *
   * @return the number of clusters generated for a training dataset.
   * @exception Exception if number of clusters could not be returned
   * successfully
   */
  public int numberOfClusters() {
    return m_NumClusters;
  }

  /**
   * Returns the tip text for this property
   * @return tip text for this property suitable for
   * displaying in the explorer/experimenter gui
   */
  public String numClustersTipText() {
    return "set number of clusters";
  }

  /**
   * set the number of clusters to generate
   *
   * @param n the number of clusters to generate
   */
  public void setNumClusters(int n) throws Exception {
    if (n <= 0) {
      throw new Exception("Number of clusters must be > 0");
    }
    m_NumClusters = n;
  }

  /**
   * gets the number of clusters to generate
   *
   * @return the number of clusters to generate
   */
  public int getNumClusters() {
    return m_NumClusters;
  }
    
  /**
   * Returns the tip text for this property
   * @return tip text for this property suitable for
   * displaying in the explorer/experimenter gui
   */
  public String seedTipText() {
    return "random number seed";
  }


  /**
   * Set the random number seed
   *
   * @param s the seed
   */
  public void setSeed (int s) {
    m_Seed = s;
  }


  /**
   * Get the random number seed
   *
   * @return the seed
   */
  public int getSeed () {
    return  m_Seed;
  }

  /**
   * return a string describing this clusterer
   *
   * @return a description of the clusterer as a string
   */
  public String toString(LinkedList attributes) {
	int maxWidth = 0;
    for (int i=0; i<m_NumClusters; i++) {
      for (int j=0; j<m_ClusterCentroids.instances.length; j++) {
    	  if (!Utils.isCategoricalAtt(attributes, j)) {
    		  double width = Math.log(Math.abs(Float.parseFloat(m_ClusterCentroids.instances[j].get(i).toString()))) / Math.log(10.0);
    		  width += 1.0;
    		  if ((int)width > maxWidth)
    			  maxWidth = (int)width;
    	  }
      }
    }
    StringBuffer temp = new StringBuffer();
    String naString = "N/A";
    for (int i = 0; i < maxWidth+2; i++)
      naString += " ";
    temp.append("\nkMeans\n======\n");
    temp.append("\nNumber of iterations: " + m_Iterations+"\n");
    temp.append("Within cluster sum of squared errors: " + Utils.sum(m_squaredErrors));

    temp.append("\n\nCluster centroids:\n");
    //print clusters
    for (int i = 0; i < m_NumClusters; i++) {
      temp.append("\nCluster "+i+"\n\t");
      temp.append("Mean/Mode: ");
      for (int j = 0; j < m_ClusterCentroids.instances.length; j++) {
    	if(m_ClusterCentroids.isCategorical(j))	{
		   //temp.append(" "+m_ClusterCentroids.attribute(j).value((int)m_ClusterCentroids.instance(i).value(j)));
		   temp.append(" "+m_ClusterCentroids.instances[j].get(i).toString());
		  // System.out.println(j+"----"+m_ClusterCentroids.instances[j].get(i).toString());
    	} else
		   //temp.append(" "+Utils.doubleToString(m_ClusterCentroids.instance(i).value(j), maxWidth+5, 4));
			temp.append(" "+Utils.doubleToString(Double.parseDouble(m_ClusterCentroids.instances[j].get(i).toString()), maxWidth+5, 4));
      }
      temp.append("\n\tStd Devs:  ");
      for (int j = 0; j<m_ClusterStdDevs.instances.length; j++) {
		if (!m_ClusterStdDevs.isCategorical(j))
		   temp.append(" "+Utils.doubleToString(Double.parseDouble(m_ClusterStdDevs.instances[j].get(i).toString()), maxWidth+5, 4));
		else
		   temp.append(" "+naString);
      }
    }
    temp.append("\n\n");
    return temp.toString();
  }
  
  public Cluster getClusterCentroids() {
    return m_ClusterCentroids;
  }

  public Cluster getClusterStandardDevs() {
    return m_ClusterStdDevs;
  }

  public double getSquaredError() {
    return Utils.sum(m_squaredErrors);
  }

  public int [] getClusterSizes() {
    return m_ClusterSizes;
  }
}
