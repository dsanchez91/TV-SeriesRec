package kmeans;

import java.io.*;
import java.util.LinkedList;

/**
   * Class providing keys to the hash table
   */
  public class hashKey implements Serializable {
    
    /***/
    private static final long serialVersionUID = 1L;
	  
    /** Array of attribute values for an instance */
    private double [] attributes;
    
    /** True for an index if the corresponding attribute value is missing. */
    private boolean [] missing;

    /** The key */
    private int key;

    /**
     * Constructor for a hashKey
     *
     * @param t an instance from which to generate a key
     * @param numAtts the number of attributes
     * @param ignoreClass if true treat the class as a normal attribute
     */
    public hashKey(String[] t, int numAtts, boolean ignoreClass, LinkedList[] translator, LinkedList nomAttributes) throws Exception {
      int i;
      //int cindex = t.classIndex();
      int cindex = -1;

      key = -999;
      attributes = new double [numAtts];
      missing = new boolean [numAtts];
      for(i=0;i<numAtts;i++) {
		if(i == cindex && !ignoreClass) {
		   missing[i] = true;
		} else {
		  if ((missing[i] = t[i].isEmpty()) == false) {
			  if(Utils.isCategoricalAtt(nomAttributes, i)){
				  attributes[i] = (double)translator[i].indexOf(t[i]);}
			  else{
				  attributes[i] = Double.parseDouble(t[i]);}
		  }
		}
      }
    }

    /**
     * Constructor for a hashKey
     *
     * @param t an array of feature values
     */
    public hashKey(double [] t) {

      int i;
      int l = t.length;

      key = -999;
      attributes = new double [l];
      missing = new boolean [l];
      for (i=0;i<l;i++) {
	if (t[i] == Double.MAX_VALUE) {
	  missing[i] = true;
	} else {
	  missing[i] = false;
	  attributes[i] = t[i];
	}
      }
    }
    
    /**
     * Calculates a hash code
     *
     * @return the hash code as an integer
     */
    public int hashCode() {

      int hv = 0;
      
      if (key != -999)
	return key;
      for (int i=0;i<attributes.length;i++) {
	if (missing[i]) {
	  hv += (i*13);
	} else {
	  hv += (i * 5 * (attributes[i]+1));
	}
      }
      if (key == -999) {
	key = hv;
      }
      return hv;
    }

    /**
     * Tests if two instances are equal
     *
     * @param b a key to compare with
     */
    public boolean equals(Object b) {
      
      if ((b == null) || !(b.getClass().equals(this.getClass()))) {
        return false;
      }
      boolean ok = true;
      boolean l;
      if (b instanceof hashKey) {
	hashKey n = (hashKey)b;
	for (int i=0;i<attributes.length;i++) {
	  l = n.missing[i];
	  if (missing[i] || l) {
	    if ((missing[i] && !l) || (!missing[i] && l)) {
	      ok = false;
	      break;
	    }
	  } else {
	    if (attributes[i] != n.attributes[i]) {
	      ok = false;
	      break;
	    }
	  }
	}
      } else {
	return false;
      }
      return ok;
    }
    
    /**
     * Prints the hash code
     */
    public void print_hash_code() {
      
      System.out.println("Hash val: "+hashCode());
    }
  }
