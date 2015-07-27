package kmeans;

import java.util.LinkedList;

public class Cluster {
	LinkedList[] instances;
	
	public Cluster(int numAtt){
		instances = new LinkedList[numAtt];
		for(int i=0; i<numAtt; i++)
			instances[i] = new LinkedList();
	}
	
	public void addInstance(String[] inputInstance){
		for(int i=0; i<inputInstance.length; i++)
			instances[i].add(inputInstance[i]);
	}
	
	public int getNumInstances(){
		return instances[0].size();
	}
	
	/**
	   * Returns the mean (mode) for a numeric (nominal) attribute as
	   * a floating-point value. Returns 0 if the attribute is neither nominal nor 
	   * numeric. If all values are missing it returns zero.
	   *
	   * @param attIndex the attribute's index
	   * @return the mean or the mode
	   */
	  public double meanOrMode(int attIndex, LinkedList[] translator, LinkedList attributes) {
		    double result, found;
		    int [] counts;
		    
		    if(!Utils.isCategoricalAtt(attributes, attIndex)) {
		       result = found = 0;
		       for(int j = 0; j<getNumInstances(); j++) {
		    	   //if (!instance(j).isMissing(attIndex)) {
		    		   //found += instance(j).weight();
                       //result += instance(j).weight()*Double.parseDouble(instances[attIndex].get(j).toString());
		    	       found += 1.0;
		    		   result += 1.0*Double.parseDouble(instances[attIndex].get(j).toString());
		    	   //}
		       }
		       if (found <= 0) {
		    	   return 0;
		       } else {
		    	   return result / found;
		       }

		    } else if(Utils.isCategoricalAtt(attributes, attIndex)) {
		       counts = new int[translator[attIndex].size()];
		       for (int j = 0; j<getNumInstances(); j++) {
		    	   //if (!instance(j).isMissing(attIndex)) {
		    		   //counts[(int) instance(j).value(attIndex)] += instance(j).weight();
		    		   //counts[(int)translator[attIndex].indexOf(instances[attIndex].get(j).toString())] += instance(j).weight();
		    	   counts[(int)translator[attIndex].indexOf(instances[attIndex].get(j).toString())] += 1.0;
		    	   //}
		       }
		       return (double)maxIndex(counts);
		   } else
		       return 0;
	  }
	  
	  /**
	   * Computes the variance for a numeric attribute.
	   *
	   * @param attIndex the numeric attribute
	   * @return the variance if the attribute is numeric
	   * @exception IllegalArgumentException if the attribute is not numeric
	   */
	  public double variance(LinkedList attributes, int attIndex) {	  
	    double sum = 0, sumSquared = 0, sumOfWeights = 0;

	    if(Utils.isCategoricalAtt(attributes, attIndex)) {
	      throw new IllegalArgumentException("Can't compute variance because attribute is " +  "not numeric!");
	    }
	    for (int i = 0; i < getNumInstances(); i++) {
	      if (!isMissing(instances[attIndex].get(i).toString())) {
	    	  double instVal = Double.parseDouble(instances[attIndex].get(i).toString());
	    	  //sum += instance(i).weight() * instance(i).value(attIndex);
	    	  sum += 1.0 * instVal;
	    	  sumSquared += 1.0 * instVal * instVal;
	    	  sumOfWeights += 1.0;
	      }
	    }
	    if (sumOfWeights <= 1) {
	      return 0;
	    }
	    double result = (sumSquared - (sum * sum / sumOfWeights)) / (sumOfWeights - 1);

	    // We don't like negative variance
	    if (result < 0) {
	      return 0;
	    } else {
	      return result;
	    }
	  }
	  
	  /**
	   * Returns index of maximum element in a given
	   * array of integers. First maximum is returned.
	   *
	   * @param ints the array of integers
	   * @return the index of the maximum element
	   */
	  public int maxIndex(int [] ints) {

	    int maximum = 0;
	    int maxIndex = 0;

	    for (int i = 0; i < ints.length; i++) {
	      if ((i == 0) || (ints[i] > maximum)) {
			maxIndex = i;
			maximum = ints[i];
	      }
	    }

	    return maxIndex;
	  }
	  
	  /**
	   * Tests if a specific value is "missing".
	   *
	   * @param attIndex the attribute's index
	   */
	  public /*@pure@*/ boolean isMissing(String value) {

	    if(Double.isNaN(Float.parseFloat(value))) {
	      return true;
	    }
	    return false;
	  }
	  
	  /*
	   * 
	   */
	  public boolean isCategorical(int indAtt){
		  boolean output=false;

	      try {
	    	  Float.parseFloat(instances[indAtt].getFirst().toString());
		  } catch (NumberFormatException exp) {
			 output = true;
		  }	
		  
		  return output;
	  }
}
