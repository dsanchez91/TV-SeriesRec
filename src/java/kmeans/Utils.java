/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    Utils.java
 *    Copyright (C) 1999-2004 University of Waikato
 *
 */

package kmeans;

import java.lang.Math;
import java.util.LinkedList;

/**
 * Class implementing some simple utility methods.
 *
 * @author Eibe Frank 
 * @author Yong Wang 
 * @author Len Trigg 
 * @author Julien Prados
 * @version $Revision: 1.44.2.3 $
 */
public final class Utils {

  /** The natural logarithm of 2. */
  public static double log2 = Math.log(2);

  /** The small deviation allowed in double comparisons */
  public static double SMALL = 1e-6;

  /**
   * Checks if the given array contains the flag "-Char". Stops
   * searching at the first marker "--". If the flag is found,
   * it is replaced with the empty string.
   *
   * @param flag the character indicating the flag.
   * @param strings the array of strings containing all the options.
   * @return true if the flag was found
   * @exception Exception if an illegal option was found
   */
  public static boolean getFlag(char flag, String [] options) 
    throws Exception {
       return getFlag("" + flag, options);
  }
  
  /**
   * Checks if the given array contains the flag "-String". Stops
   * searching at the first marker "--". If the flag is found,
   * it is replaced with the empty string.
   *
   * @param flag the String indicating the flag.
   * @param strings the array of strings containing all the options.
   * @return true if the flag was found
   * @exception Exception if an illegal option was found
   */
  public static boolean getFlag(String flag, String [] options) 
    throws Exception {

    if (options == null) {
      return false;
    }
    for (int i = 0; i < options.length; i++) {
      if ((options[i].length() > 1) && (options[i].charAt(0) == '-')) {
	try {
	  Double.valueOf(options[i]);
	} catch (NumberFormatException e) {
	  if (options[i].equals("-" + flag)) {
	    options[i] = "";
	    return true;
	  }
	  if (options[i].charAt(1) == '-') {
	    return false;
	  }
	}
      }
    }
    return false;
  }

  /**
   * Checks if the given array contains any non-empty options.
   *
   * @param strings an array of strings
   * @exception Exception if there are any non-empty options
   */
  public static void checkForRemainingOptions(String [] options) 
    throws Exception {
    
    int illegalOptionsFound = 0;
    StringBuffer text = new StringBuffer();

    if (options == null) {
      return;
    }
    for (int i = 0; i < options.length; i++) {
      if (options[i].length() > 0) {
		illegalOptionsFound++;
		text.append(options[i] + ' ');
      }
    }
    if (illegalOptionsFound > 0) {
      throw new Exception("Illegal options: " + text);
    }
  } 
  
  /**
   * Gets an option indicated by a flag "-Char" from the given array
   * of strings. Stops searching at the first marker "--". Replaces 
   * flag and option with empty strings.
   *
   * @param flag the character indicating the option.
   * @param options the array of strings containing all the options.
   * @return the indicated option or an empty string
   * @exception Exception if the option indicated by the flag can't be found
   */
  public static /*@non_null@*/ String getOption(char flag, String [] options) 
    throws Exception {
     return getOption("" + flag, options);
  }

  /**
   * Gets an option indicated by a flag "-String" from the given array
   * of strings. Stops searching at the first marker "--". Replaces 
   * flag and option with empty strings.
   *
   * @param flag the String indicating the option.
   * @param options the array of strings containing all the options.
   * @return the indicated option or an empty string
   * @exception Exception if the option indicated by the flag can't be found
   */
	public static /*@non_null@*/ String getOption(String flag, String [] options) throws Exception {
	
	  String newString;
	
	  if (options == null)
	    return "";
	  for (int i = 0; i < options.length; i++) {
	    if ((options[i].length() > 0) && (options[i].charAt(0) == '-')) {	
			// Check if it is a negative number
			try {
			  Double.valueOf(options[i]);
			} catch (NumberFormatException e) {
			  if (options[i].equals("-" + flag)) {
			    if (i + 1 == options.length) {
			      throw new Exception("No value given for -" + flag + " option.");
			    }
			    options[i] = "";
			    newString = new String(options[i + 1]);
			    options[i + 1] = "";
			    return newString;
			  }
			  if (options[i].charAt(1) == '-')
			    return "";
			}
	    }
	  }
	  return "";
  }
	
  /**
	* Tests if a is equal to b.
	*
	* @param a a double
	* @param b a double
   */
   public static /*@pure@*/ boolean eq(double a, double b){    
	   return (a - b < SMALL) && (b - a < SMALL); 
   }  

   /**
    * Computes the sum of the elements of an array of doubles.
    *
    * @param doubles the array of double
    * @return the sum of the elements
    */
   public static /*@pure@*/ double sum(double[] doubles) {

     double sum = 0;

     for (int i = 0; i < doubles.length; i++) {
       sum += doubles[i];
     }
     return sum;
   }

   /**
    * Computes the sum of the elements of an array of integers.
    *
    * @param ints the array of integers
    * @return the sum of the elements
    */
   public static /*@pure@*/ int sum(int[] ints) {

     int sum = 0;

     for (int i = 0; i < ints.length; i++) {
       sum += ints[i];
     }
     return sum;
   }

   /**
    * Rounds a double and converts it into String.
    *
    * @param value the double value
    * @param afterDecimalPoint the (maximum) number of digits permitted
    * after the decimal point
    * @return the double as a formatted string
    */
   public static /*@pure@*/ String doubleToString(double value, int afterDecimalPoint) {
     
     StringBuffer stringBuffer;
     double temp;
     int dotPosition;
     long precisionValue;
     
     temp = value * Math.pow(10.0, afterDecimalPoint);
     if (Math.abs(temp) < Long.MAX_VALUE) {
       precisionValue = (temp > 0) ? (long)(temp + 0.5): -(long)(Math.abs(temp) + 0.5);
       if (precisionValue == 0) {
    	   stringBuffer = new StringBuffer(String.valueOf(0));
       } else {
    	   stringBuffer = new StringBuffer(String.valueOf(precisionValue));
       }
       if (afterDecimalPoint == 0) {
    	   return stringBuffer.toString();
       }
       dotPosition = stringBuffer.length() - afterDecimalPoint;
       while (((precisionValue < 0) && (dotPosition < 1)) || (dotPosition < 0)) {
    	   if (precisionValue < 0) {
    		   stringBuffer.insert(1, '0');
    	   } else {
    		   stringBuffer.insert(0, '0');
    	   }
    	   dotPosition++;
       }
       stringBuffer.insert(dotPosition, '.');
       if ((precisionValue < 0) && (stringBuffer.charAt(1) == '.')) {
    	   stringBuffer.insert(1, '0');
       } else if (stringBuffer.charAt(0) == '.') {
    	   stringBuffer.insert(0, '0');
       }
       int currentPos = stringBuffer.length() - 1;
       while ((currentPos > dotPosition) && (stringBuffer.charAt(currentPos) == '0')) {
    	   stringBuffer.setCharAt(currentPos--, ' ');
       }
       if (stringBuffer.charAt(currentPos) == '.') {
    	   stringBuffer.setCharAt(currentPos, ' ');
       }
       
       return stringBuffer.toString().trim();
     }
     return new String("" + value);
   }

   /**
    * Rounds a double and converts it into a formatted decimal-justified String.
    * Trailing 0's are replaced with spaces.
    *
    * @param value the double value
    * @param width the width of the string
    * @param afterDecimalPoint the number of digits after the decimal point
    * @return the double as a formatted string
    */
   public static /*@pure@*/ String doubleToString(double value, int width,
 				      int afterDecimalPoint) {
     
     String tempString = doubleToString(value, afterDecimalPoint);
     char[] result;
     int dotPosition;

     if ((afterDecimalPoint >= width) 
         || (tempString.indexOf('E') != -1)) { // Protects sci notation
       return tempString;
     }

     // Initialize result
     result = new char[width];
     for (int i = 0; i < result.length; i++) {
       result[i] = ' ';
     }

     if (afterDecimalPoint > 0) {
       // Get position of decimal point and insert decimal point
       dotPosition = tempString.indexOf('.');
       if (dotPosition == -1) {
 	dotPosition = tempString.length();
       } else {
 	result[width - afterDecimalPoint - 1] = '.';
       }
     } else {
       dotPosition = tempString.length();
     }
     

     int offset = width - afterDecimalPoint - dotPosition;
     if (afterDecimalPoint > 0) {
       offset--;
     }

     // Not enough room to decimal align within the supplied width
     if (offset < 0) {
       return tempString;
     }

     // Copy characters before decimal point
     for (int i = 0; i < dotPosition; i++) {
       result[offset + i] = tempString.charAt(i);
     }

     // Copy characters after decimal point
     for (int i = dotPosition + 1; i < tempString.length(); i++) {
       result[offset + i] = tempString.charAt(i);
     }

     return new String(result);
   }
   
   /**
    * Returns index of maximum element in a given
    * array of doubles. First maximum is returned.
    *
    * @param doubles the array of doubles
    * @return the index of the maximum element
    */
   public static /*@pure@*/ int maxIndex(double [] doubles) {

     double maximum = 0;
     int maxIndex = 0;

     for (int i = 0; i < doubles.length; i++) {
       if ((i == 0) || (doubles[i] > maximum)) {
	 	maxIndex = i;
	 	maximum = doubles[i];
       }
     }

     return maxIndex;
   }

   /**
    * Returns index of maximum element in a given
    * array of integers. First maximum is returned.
    *
    * @param ints the array of integers
    * @return the index of the maximum element
    */
   public static /*@pure@*/ int maxIndex(int [] ints) {

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
   * 
   */
   public static boolean isCategoricalAtt(LinkedList attributes, int j){
	   boolean output;
	   
	  if(attributes.indexOf(bd.InputData.ATT_NUMERIC_1)==j)
 		 output = false;
	  else if(attributes.indexOf(bd.InputData.ATT_NUMERIC_2)==j)
		 output = false;
      else
    	 output = true; 

	  return output;
   }
}
  
