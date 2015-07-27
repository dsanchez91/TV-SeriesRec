package CBAFuzzy;

/* -------------------------------------------------------------------------- */
/*                                                                            */
/*                      ASSOCIATION RULE DATA MINING                          */
/*                                                                            */
/*                            Frans Coenen                                    */
/*                                                                            */
/*                        Wednesday 9 January 2003                            */
/*   (revised 21/1/2003, 14/2/2003, 2/5/2003, 2/7/2003, 3/2/2004, 8/5/2004,   */
/*                                1/2/2005)                                   */
/*                                                                            */
/*                    Department of Computer Science                          */
/*                     The University of Liverpool                            */
/*                                                                            */ 
/* -------------------------------------------------------------------------- */

/* To compile: javaARMpackc.exe AssocRuleMining.java */

// Java packages
import java.io.*;
import java.util.*;

// Java GUI packages
import javax.swing.*;

/** Set of utillities to support various Association Rule Mining (ARM) 
algorithms included in the LUCS-KDD suite of ARM programs. 
@author Frans Coenen
@version 1 February 2005 */

public class AssocRuleMining extends JFrame {

	private static final long serialVersionUID = 1L;
    /* ------ FIELDS ------ */

    // Inner class for storing linked list of ARs or CARs as appropriate.

    public class RuleNode {
	    /** Antecedent of AR. */
    	public short[] antecedent;
		/** Consequent of AR. */
    	public short[] consequent;
		/** The confidence value associate with the rule represented by this node. */
    	public double confidenceForRule=0.0;
		/** Link to next node */
    	public RuleNode next = null;
		
		/** Three argument constructor
		@param antecedent the antecedent (LHS) of the AR.
	  	@param consequent the consequent (RHS) of the AR.
	   	@param support the associated confidence value. */
		
		protected RuleNode(short[] ante, short[]cons, double confValue) {
		    antecedent        = ante;
		    consequent        = cons;
		    confidenceForRule = confValue;
		}
	}
	
    // Data structures
	
    /** The reference to start of the rule list. */
    protected RuleNode startRulelist = null;	
    /** 2-D aray to hold input data from data file */
    protected short[][] dataArray = null;
    
    protected LinkedList supports = null;
    
    protected int addedLines = 0;

    public static LinkedList itemsTranslatorList = null;
    
    // Constants
    /** Minimum support value */
    private static final double MIN_SUPPORT = 0.0;
    /** Maximum support value */
    private static final double MAX_SUPPORT = 100.0;
    /** Maximum confidence value */
    private static final double MIN_CONFIDENCE = 0.0;
    /** Maximum confidence value */
    private static final double MAX_CONFIDENCE = 100.0;	
    
    // Command line arguments with default values and associated fields
    
    /** Command line argument for number of classes. */	
    protected int     numClasses    = 0;
    /** Command line argument for number of columns. */	
    protected int     numCols    = 0;
    /** Command line argument for number of rows. */
    protected int     numRows    = 0;
    /** Command line argument for % support (default = 20%). */
    protected double  support    = 20.0;
    /** Minimum support value in terms of number of rows. */
    protected double  minSupport = 0;
    /** Command line argument for % confidence (default = 80%). */
    protected double  confidence = 80.0;
    /** The number of one itemsets (singletons). */
    protected int numOneItemSets = 0;	
    
    // Flags
    
    /** Error flag used when checking command line arguments (default = 
    <TT>true</TT>). */
    protected boolean errorFlag  = true;
    /** Input format OK flag( default = <TT>true</TT>). */
    protected boolean inputFormatOkFlag = true;
    /** Flag to indicate whether system has data or not. */
    
    /** Flag to indicate whether input data has been sorted or not. */
    
    // Other fields  
    /** The input stream. */
    protected BufferedReader fileInput;
    /** The file path */
    protected File filePath = null;	
    
    /* ------ CONSTRUCTORS ------ */
    
    /** Processes command line arguments */
    	 
    public AssocRuleMining(String[][] trainingData, int numOfClasses, double minSup, double minConf) {
    	support = minSup;
    	confidence = minConf;
    	numClasses = numOfClasses;
	}
	
    /* ------ METHODS ------ */	
    
    /* ---------------------------------------------------------------- */
    /*                                                                  */
    /*                        COMMAND LINE ARGUMENTS                    */
    /*                                                                  */
    /* ---------------------------------------------------------------- */
  
    /* CHECK SUPPORT AND CONFIDANCE */
    /** Checks support and confidence input % values, if either is out of
    bounds then <TT>errorFlag</TT> set to <TT>false</TT>. */	
    
    protected void checkSupportAndConfidence() {
		// Check Support	
		if ((support < MIN_SUPPORT) || (support > MAX_SUPPORT)) 
			System.out.println("INPUT ERROR: Support must be specified " + "as a percentage (" + MIN_SUPPORT + " - " + MAX_SUPPORT + ")");
		
		// Check confidence	
		if ((confidence < MIN_CONFIDENCE) || (confidence > MAX_CONFIDENCE)) 
			System.out.println("INPUT ERROR: Confidence must be " + "specified as a percentage (" + MIN_CONFIDENCE + " - " + MAX_CONFIDENCE + ")");
	}
	
    /* ---------------------------------------------------------------- */
    /*                                                                  */
    /*                     READ INPUT DATA FROM FILE                    */
    /*                                                                  */
    /* ---------------------------------------------------------------- */

    /* INPUT DATA SET */    
    
    /** Commences process of getting input data (GUI version also exists). */
    
    public void inputDataSet(String[][] trainingData, LinkedList attributes, LinkedList fuzzifiedAttributes) {
    	dataArray = IO.getShorts(trainingData, attributes, fuzzifiedAttributes);
    	supports =  IO.getSupports();
    	addedLines = IO.getAddedLines();
    	itemsTranslatorList = IO.getItems();
    	
    	numRows = dataArray.length;
        numCols = dataArray[0].length;
        
		// Check ordering (only if input format is OK)		
		if (inputFormatOkFlag) {
            //System.out.println("Number of records = " + (numRows-addedLines));
            countNumCols();
			//System.out.println("Number of columns = " + numCols);
			minSupport = ((numRows-addedLines)*support)/100.0;
        	//System.out.println("Min support       = " +	twoDecPlaces(minSupport) + " (records)"); 
		}
	}
	
    /* CHECK LINE */
    /** Check whether given line from input file is of appropriate format
    (space separated integers), if incorrectly formatted line found 
    <TT>inputFormatOkFlag</TT> set to <TT>false</TT>. 
    @param counter the line number in the input file.
    @param str the current line from the input file. */	
    protected void checkLine(int counter, String str) {
        for (int index=0;index <str.length();index++) {
            if (!Character.isDigit(str.charAt(index)) && !Character.isWhitespace(str.charAt(index))) {
            	JOptionPane.showMessageDialog(null,"FILE INPUT ERROR:\n" + "charcater on line " + counter + " is not a digit or white space");	        
				inputFormatOkFlag = false;
				break;
			}
	    }
	}
	
    /* COUNT NUMBER OF COLUMNS */
    /** Counts number of columns represented by input data. */    	
    protected void countNumCols() {
        int maxAttribute=0;
	
        // Loop through data array	
        for(int index=0;index<dataArray.length;index++) {
		    int lastIndex = dataArray[index].length-1;
		    if (dataArray[index][lastIndex] > maxAttribute)
		    	maxAttribute = dataArray[index][lastIndex];	    
	    }
	
		numCols        = maxAttribute;
		numOneItemSets = numCols; 	// default value only
	}
	
    /* BINARY CONVERSION. */
    
    /** Produce an item set (array of elements) from input 
    line. 
    @param dataLine row from the input data file
    @param numberOfTokens number of items in row
    @return 1-D array of short integers representing attributes in input
    row */
    
    protected short[] binConversion(StringTokenizer dataLine, int numberOfTokens) {
        short number;
        short[] newItemSet = null;
	
        // Load array
        for (int tokenCounter=0;tokenCounter < numberOfTokens;tokenCounter++) {
            number = new Short(dataLine.nextToken()).shortValue();
            newItemSet = realloc1(newItemSet,number);
	    }
	
        // Return itemSet
        return(newItemSet);
	}  
	
    /* -------------------------------------------------------------- */
    /*                                                                */
    /*        RULE LINKED LIST ORDERED ACCORDING TO CONFIDENCE        */
    /*                                                                */
    /* -------------------------------------------------------------- */

    /* Methods for inserting rules into a linked list of rules ordered
    according to confidence (most confident first). Each rule described in
    terms of 3 fields: 1) Antecedent (an item set), 2) a consequent (an item
    set), 3) a confidence value (double). <P> The support field is not used. */

    /* INSERT (ASSOCIATION/CLASSIFICATION) RULE INTO RULE LINKED LIST (ORDERED
    ACCORDING CONFIDENCE). */

    /** Inserts an (association/classification) rule into the linkedlist of
    rules pointed at by <TT>startRulelist</TT>. <P> The list is ordered so that
    rules with highest confidence are listed first. If two rules have the same
    confidence the new rule will be placed after the existing rule. Thus, if
    using an Apriori approach to generating rules, more general rules will
    appear first in the list with more specific rules (i.e. rules with a larger
    antecedent) appearing later as the more general rules will be generated first.
    @param antecedent the antecedent (LHS) of the rule.
    @param consequent the consequent (RHS) of the rule.
    @param supportForRule the associated support value.  */

    protected void insertRuleintoRulelist(short[] antecedent, short[] consequent, double confidenceForRule) {
		// Create new node
		RuleNode newNode = new RuleNode(antecedent, consequent, confidenceForRule);

                // Empty list situation
		if (startRulelist == null) {
		    startRulelist = newNode;
		    return;
		}
			
		// Add new node to start	
		if (confidenceForRule > startRulelist.confidenceForRule) {
		    newNode.next = startRulelist;
		    startRulelist  = newNode;
		    return;
		}
		
		// Add new node to middle
		RuleNode markerNode = startRulelist;
		RuleNode linkRuleNode = startRulelist.next;
		while (linkRuleNode != null) {
		    if (confidenceForRule > linkRuleNode.confidenceForRule) {
		        markerNode.next = newNode;
		        newNode.next    = linkRuleNode;
		        return;
			}
		    markerNode = linkRuleNode;
		    linkRuleNode = linkRuleNode.next;	
		}
		// Add new node to end
		markerNode.next = newNode;
	}
	
    /* ----------------------------------------------- */
    /*                                                 */
    /*        ITEM SET INSERT AND ADD METHODS          */
    /*                                                 */
    /* ----------------------------------------------- */   
    
    /* APPEND */
    
    /** Concatenates two itemSets --- resizes given array so that its 
    length is increased by size of second array and second array added. 
    @param itemSet1 The first item set.
    @param itemSet2 The item set to be appended. 
    @return the combined item set */
    protected short[] append(short[] itemSet1, short[] itemSet2) {
		// Test for empty sets, if found return other
		
		if (itemSet1 == null)
			return(copyItemSet(itemSet2));
		else if (itemSet2 == null)
			return(copyItemSet(itemSet1));
	        
		// Create new array
		short[] newItemSet = new short[itemSet1.length+itemSet2.length];
		
		// Loop through itemSet 1
		int index1;
		for(index1=0;index1<itemSet1.length;index1++)
		    newItemSet[index1]=itemSet1[index1];
		
		// Loop through itemSet 2
		
		for(int index2=0;index2<itemSet2.length;index2++)
		    newItemSet[index1+index2]=itemSet2[index2];
	
		// Return
		
		return(newItemSet);	
    }
	    
    /* REALLOC INSERT */
    
    /** Resizes given item set so that its length is increased by one
    and new element inserted.
    @param oldItemSet the original item set
    @param newElement the new element/attribute to be inserted
    @return the combined item set */
    
    protected short[] reallocInsert(short[] oldItemSet, short newElement) {	
       
	// No old item set
	
	if (oldItemSet == null) {
	    short[] newItemSet = {newElement};
	    return(newItemSet);
	    }
	
	// Otherwise create new item set with length one greater than old 
	// item set
	
	int oldItemSetLength = oldItemSet.length;
	short[] newItemSet = new short[oldItemSetLength+1];
	
	// Loop
	
	int index1;	
	for (index1=0;index1 < oldItemSetLength;index1++) {
	    if (newElement < oldItemSet[index1]) {
		newItemSet[index1] = newElement;	
		// Add rest	
		for(int index2 = index1+1;index2<newItemSet.length;index2++)
				newItemSet[index2] = oldItemSet[index2-1];
		return(newItemSet);
		}
	    else newItemSet[index1] = oldItemSet[index1];
	    }
	
	// Add to end
	
	newItemSet[newItemSet.length-1] = newElement; 
	
	// Return new item set
	
	return(newItemSet);
	}
	
    /* REALLOC 1 */
    
    /** Resizes given item set so that its length is increased by one
    and appends new element (identical to append method)
    @param oldItemSet the original item set
    @param newElement the new element/attribute to be appended
    @return the combined item set */
    
    protected short[] realloc1(short[] oldItemSet, short newElement) {
        
	// No old item set
	
	if (oldItemSet == null) {
	    short[] newItemSet = {newElement};
	    return(newItemSet);
	    }
	
	// Otherwise create new item set with length one greater than old 
	// item set
	
	int oldItemSetLength = oldItemSet.length;
	short[] newItemSet = new short[oldItemSetLength+1];
	
	// Loop
	
	int index;
	for (index=0;index < oldItemSetLength;index++)
		newItemSet[index] = oldItemSet[index];
	newItemSet[index] = newElement;
	
	// Return new item set
	
	return(newItemSet);
	}

    /* REALLOC 2 */
    
    /** Resizes given array so that its length is increased by one element
    and new element added to front
    @param oldItemSet the original item set
    @param newElement the new element/attribute to be appended
    @return the combined item set */
    
    protected short[] realloc2(short[] oldItemSet, short newElement) {
        
	// No old array
	
	if (oldItemSet == null) {
	    short[] newItemSet = {newElement};
	    return(newItemSet);
	    }
	
	// Otherwise create new array with length one greater than old array
	
	int oldItemSetLength = oldItemSet.length;
	short[] newItemSet = new short[oldItemSetLength+1];
	
	// Loop
	
	newItemSet[0] = newElement;
	for (int index=0;index < oldItemSetLength;index++)
		newItemSet[index+1] = oldItemSet[index];
	
	// Return new array
	
	return(newItemSet);
	}

    /* REALLOC 3 */
    
    /** Resizes given array so that its length is decreased by one element
    and first element removed
    @param oldItemSet the original item set
    @return the shortened item set */
    
    protected short[] realloc3(short[] oldItemSet) {
		// If old item set comprises one element return null
		if (oldItemSet.length == 1) 
			return null;

		// Create new array with length one greater than old array
		int newItemSetLength = oldItemSet.length-1;
		short[] newItemSet = new short[newItemSetLength];
		
		// Loop
		for (int index=0;index < newItemSetLength;index++)
			newItemSet[index] = oldItemSet[index+1];

		return(newItemSet);
	}	

    /* REALLOC 4 */ 
    
    /** Resize given array so that its length is decreased by size of
    second array (which is expected to be a leading subset of the first)
    and remove second array.
    @param oldItemSet The first item set.
    @param array2 The leading subset of the <TT>oldItemSet</TT>. 
    @return Revised item set with leading subset removed. */
   
    protected short[] realloc4(short[] oldItemSet, short[] array2) {
        int array2length   = array2.length;
	int newItemSetLength = oldItemSet.length-array2length;
	
	// Create new array 
	
	short[] newItemSet = new short[newItemSetLength];
	
	// Loop
	
	for (int index=0;index < newItemSetLength;index++)
		newItemSet[index] = oldItemSet[index+array2length];
	
	// Return new array
	
	return(newItemSet);
	}
	
    /* --------------------------------------------- */
    /*                                               */
    /*            ITEM SET DELETE METHODS            */
    /*                                               */
    /* --------------------------------------------- */

    /* REMOVE FIRST N ELEMENTS */
    
    /** Removes the first n elements/attributes from the given item set.
    @param oldItemSet the given item set.
    @param n the number of leading elements to be removed. 
    @return Revised item set with first n elements removed. */
    
    protected short[] removeFirstNelements(short[] oldItemSet, int n) {
        if (oldItemSet.length == n) return(null);
    	else {
	    short[] newItemSet = new short[oldItemSet.length-n];
	    for (int index=0;index<newItemSet.length;index++) {
	        newItemSet[index] = oldItemSet[index+n];
	        }
	    return(newItemSet);
	    }
	}
	
    /* ---------------------------------------------------------------- */
    /*                                                                  */
    /*              METHODS TO RETURN SUBSETS OF ITEMSETS               */
    /*                                                                  */
    /* ---------------------------------------------------------------- */
    
    /* GET LAST ELEMENT */
    
    /** Gets the last element in the given item set, or '0' if the itemset is
    empty.
    @param itemSet the given item set. 
    @return the last element. */ 
    
    protected short getLastElement(short[] itemSet) {
        // Check for empty item set
	if (itemSet == null) return(0);
	// Otherwise return last element
        return(itemSet[itemSet.length-1]);
	}


    /* ----------------------------------------------------- */
    /*                                                       */
    /*             BOOLEAN ITEM SET METHODS ETC.             */
    /*                                                       */
    /* ----------------------------------------------------- */
    		  	
    /* CHECK ITEM SET: */ 
    
    /** Determines relationship between two item sets (same, parent, 
    before, child or after).
    @param itemSet1 the first item set.
    @param itemSet2 the second item set to be compared with first.
    @return 1 = same, 2 = itemSet2 is parent of itemSet1, 3 = itemSet2
    lexicographically before itemSet1, 4 = itemSet2 is child of itemSet1, 
    and 5 = itemSet2 lexicographically after itemSet1. */

    protected int checkItemSets(short[] itemSet1, short[] itemSet2) {
        // Check if the same
		if (isEqual(itemSet1,itemSet2)) return(1);
	
		// Check whether before or after and subset/superset. 
		if (isBefore(itemSet1,itemSet2)) {
		    if (isSubset(itemSet1,itemSet2))
		    	return(2); //equal
		    else
		    	return(3);
		}
	    
		if (isSubset(itemSet2,itemSet1)) return(4);
	       return(5); 
    }	

    /* EQUALITY CHECK */
    
    /** Checks whether two item sets are the same.
    @param itemSet1 the first item set.
    @param itemSet2 the second item set to be compared with first.
    @return true if itemSet1 is equal to itemSet2, and false otherwise. */

    protected boolean isEqual(short[] itemSet1, short[] itemSet2) {	
		// If no itemSet2 (i.e. itemSet2 is null return false)
		if (itemSet2 == null) 
			return(false);
		
		// Compare sizes, if not same length they cannot be equal.
		int length1 = itemSet1.length;
		int length2 = itemSet2.length;
		if (length1 != length2) 
			return(false);
                         
        // Same size compare elements
        for (int index=0;index < length1;index++) {
        	if (itemSet1[index] != itemSet2[index]) 
        		return(false);
	    }

        // itemSet the same. 
        return(true);
    }
    
    /* BEFORE CHECK */
    
    /** Checks whether one item set is lexicographically before a second item set.
    @param itemSet1 the first item set.
    @param itemSet2 the second item set to be compared with first.
    @return true if itemSet1 is less than or equal (before) itemSet2 and
    false otherwise. Note that before here is not numerical but lexical, 
    i.e. {1,2} is before {2} */
    public static boolean isBefore(short[] itemSet1, short[] itemSet2) {
        int length2 = itemSet2.length;

		// Compare elements
		for(int index1=0;index1<itemSet1.length;index1++) {
		    if (index1 == length2) 
		    	return(false); // itemSet2 is a proper subset of itemSet1	
	    	if (itemSet1[index1] < itemSet2[index1])
	    	   	return(true);
		    if (itemSet1[index1] > itemSet2[index1]) 
		    	return(false);
		}
		
		// Return true
		return(true);
	}
		
    /* SUBSET CHECK */
    
    /** Checks whether one item set is subset of a second item set.
    @param itemSet1 the first item set.
    @param itemSet2 the second item set to be compared with first.
    @return true if itemSet1 is a subset of itemSet2, and false otherwise.*/
    protected boolean isSubset(short[] itemSet1, short[] itemSet2) {
		// Check for empty itemsets
		if (itemSet1==null)
			return(true);
		if (itemSet2==null) 
			return(false);

		// Loop through itemSet1
		for(int index1=0;index1<itemSet1.length;index1++) {
		    if (notMemberOf(itemSet1[index1],itemSet2)) 
		    	return(false);
		}

		// itemSet1 is a subset of itemSet2
		return(true);
	}
	
    /* NOT MEMBER OF */
    
    /** Checks whether a particular element/attribute identified by a 
    column number is not a member of the given item set.
    @param number the attribute identifier (column number).
    @param itemSet the given item set.
    @return true if first argument is not a member of itemSet, and false otherwise */
    protected boolean notMemberOf(short number, short[] itemSet) {
		// Loop through itemSet
		for(int index=0;index<itemSet.length;index++) {
		    if (number < itemSet[index]) 
		    	return(true);
		    if (number == itemSet[index]) 
		    	return(false);
		}
		
		// Got to the end of itemSet and found nothing, return true
		return(true);
	}
	
    /* CHECK FOR LEADING SUB STRING */ 
    
    /** Checks whether two itemSets share a leading substring. 
    @param itemSet1 the first item set.
    @param itemSet2 the second item set to be compared with first.
    @return the substring if a shared leading substring exists, and null 
    otherwise. */	
	   
    protected short[] checkForLeadingSubString(short[] itemSet1, 
    							short[] itemSet2) {
        //int index3=0;
	short[] itemSet3 = null;
	
	// Loop through itemSets
	
	for(int index=0;index<itemSet1.length;index++) {
	    if (index == itemSet2.length) break;
	    if (itemSet1[index] == itemSet2[index]) 
	    			itemSet3 = realloc1(itemSet3,itemSet1[index]);
	    else break;
	    }	 	
	
	// Return
	
	return(itemSet3);
	}
		
    /* ---------------------------------------------------------------- */
    /*                                                                  */
    /*                            MISCELANEOUS                          */
    /*                                                                  */
    /* ---------------------------------------------------------------- */
    
    /* COPY ITEM SET */
    
    /** Makes a copy of a given itemSet. 
    @param itemSet the given item set.
    @return copy of given item set. */
    protected short[] copyItemSet(short[] itemSet) {
		// Check whether there is a itemSet to copy
		if (itemSet == null) return(null);
		
		// Do copy and return
		short[] newItemSet = new short[itemSet.length];
		for(int index=0;index<itemSet.length;index++)
		    newItemSet[index] = itemSet[index];

		return(newItemSet);
	}

    /* COPY SET OF ITEM SETS */
    
    /** Makes a copy of a given set of itemSets. 
    @param itemSets the given set of item sets.
    @return copy of given set of item sets. */
    
    protected short[][] copyItemSet(short[][] itemSets) {
		// Check whether there is a itemSet to copy
		if (itemSets == null) 
			return(null);
		
		// Do copy and return
		short[][] newItemSets = new short[itemSets.length][];
		for(int index1=0;index1<itemSets.length;index1++) {
		    if (itemSets[index1]==null) 
		    	newItemSets[index1]=null;
		    else {
		        newItemSets[index1] = new short[itemSets[index1].length];
		        for(int index2=0;index2<itemSets[index1].length;index2++)
		            newItemSets[index1][index2] = itemSets[index1][index2];
			}
		}
		return(newItemSets);
	}

   /* ------------------------------------------------- */
    /*                                                   */
    /*                    GET METHODS                    */
    /*                                                   */
    /* ------------------------------------------------- */
	
    /* GET NUMBER OF RULES */
    /** Returns the number of generated rules .
    @return the number of rules. */

    public int getNumCRs() {
        int number = 0;
        RuleNode linkRuleNode = startRulelist;
	
		// Loop through linked list
		while (linkRuleNode != null) {
		    number++;
		    linkRuleNode = linkRuleNode.next;
	    }
		return(number);
	}
	
    /* ------------------------------------------------- */
    /*                                                   */
    /*                   OUTPUT METHODS                  */
    /*                                                   */
    /* ------------------------------------------------- */
    
    /* -------------- */
    /* OUTPUT ITEMSET */  
    /* -------------- */  
    /** Outputs a given item set. 
    @param itemSet the given item set. */
    protected void outputItemSet(short[] itemSet) {   
		// Loop through item set elements
		if (itemSet == null)
			System.out.print(" null ");
		else {
	        int counter = 0;
		    for (int index=0;index<itemSet.length;index++) {
		        if (counter == 0) {
		    	    counter++;
		    	    System.out.print(" ");
			    } else
			    	System.out.print(" AND ");
                            System.out.print(itemsTranslatorList.get(itemSet[index]));
			}
		    System.out.print(" ");
		}
	}
    
    /* ----------- */
    /* OUTPUT MENU */
    /* ----------- */
    /** Outputs menu for command line arguments. */
    
    protected void outputMenu() {
    	// STUB
	}

    protected void menuOutputOptions() {
    	// STUB	
    }
    
    /* --------------- */
    /* OUTPUT SETTINGS */
    /* --------------- */
    /** Outputs command line values provided by user. */
    
    protected void outputSettings() {
        System.out.println("SETTINGS\n--------");
		System.out.println("Support (default 20%)    = " + support); 
		System.out.println("Confidence (default 80%) = " + confidence);
		System.out.println("Num. classes (Optional)  = " + numClasses);	
		System.out.println();
    }
	
    /* ------------------------ */
    /* OUTPUT RULE LINKED LISTS */
    /* ------------------------ */	
    /** Outputs contents of rule linked list (if any) */
    /* OUTPUT RULE LINKED LIST WITH DEFAULT */
    /** Outputs contents of rule linked list (if any), with reconversion, such
    that last rule is the default rule. */
    public void outputRulesWithDefault() {
        int number = 1;
        RuleNode linkRuleNode = startRulelist;
	
		while (linkRuleNode != null) {
			//if(linkRuleNode.antecedent.length>1) print rule
			
			// Output rule number
		    System.out.print("R" + number + ": ");
		    // Output antecedent
		    if (linkRuleNode.next==null) 
		    	System.out.print(" Default -->");
		    else {
		        outputItemSet(linkRuleNode.antecedent);
		        System.out.print("-->");
			}
		    // Output consequent
	        outputItemSet(linkRuleNode.consequent);
	        System.out.println("   Conf=" + twoDecPlaces(linkRuleNode.confidenceForRule) + "%");
		    // Increment parameters
		    number++;
		    linkRuleNode = linkRuleNode.next;
		}
	} 

    public RuleNode getRuleList(){
    	return startRulelist;
    }
    
    /* OUTPUT NUMBER OF RULES */
    /** Outputs number of generated rules (ARs or CARS). */
    public void outputNumRules() {
        System.out.println("Number of rules         = " + getNumCRs());
	}
			
    /* --------------------------------- */
    /*                                   */
    /*        DIAGNOSTIC OUTPUT          */
    /*                                   */
    /* --------------------------------- */
        
    /* OUTPUT DURATION */    
    /** Outputs difference between two given times.
    @param time1 the first time.
    @param time2 the second time. 
    @return duration. */
    public void outputDuration(double time1, double time2) {
        double duration = (time2-time1)/1000;
        System.out.println("\nGeneration time = " + twoDecPlaces(duration) + " seconds (" + twoDecPlaces(duration/60) + " mins)");
        
        // Return
        //return(duration);
	}
    
    /* GET DURATION */
    /** Returns the difference between two given times as a string.
    @param time1 the first time.
    @param time2 the second time. 
    @return the difference between the given times as a string. */
    
    protected String getDuration(double time1, double time2) {
        double duration = (time2-time1)/1000;
    	return("Generation time = " + twoDecPlaces(duration) + " seconds (" + twoDecPlaces(duration/60) + " mins)");
	}
	
    /* -------------------------------- */
    /*                                  */
    /*        SIMILARITY UTILITIES      */
    /*                                  */
    /* -------------------------------- */

    /* SIMILAR 2 DFECIMAL PLACES */

    /* Compares two real numbers and returns true if the two numbers are
    the same within two decimal places.
    @param the first given real number.
    @param the second given number.
    @return true if similar within two decimal places ad false otherwise. */

    protected boolean similar2dec(double number1, double number2) {
        // Convert to integers
        int numInt1 = (int) ((number1+0.005)*100.0);
        int numInt2 = (int) ((number2+0.005)*100.0);

        // Compare and return
        if (numInt1 == numInt2) return(true);
        else return(false);
        }
	
    /* ------------------------------------------------------ */
    /*                                                        */
    /*                   FILE OUTPUT METHODS                  */
    /*                                                        */
    /* ------------------------------------------------------ */
    
    /* OUTPUT DATA ARRAY TO FILE */
    
    /* Outputs contents of data array to file. <P> WARNING will overwrite 
    existing data if stored in the same directory as the application 
    exacutable, data files are better stored in a separate "DataFiles" 
    directory. */
    
    /* public void outputDataArrayToFile() throws IOException{
        //Determin file name
	int    fileNameIndex = fileName.lastIndexOf('/');
	String shortFileName = fileName.substring(fileNameIndex+1,
							fileName.length());  
	
	// Open file for writing
	PrintWriter outputFile = new PrintWriter(new FileWriter(shortFileName)); 
	
	// Write contents of Data array to file
	for (int rowIndex = 0;rowIndex<numRows;rowIndex++) {
	    if (dataArray[rowIndex] != null) {
	        boolean firstAtt = true;
	        // Loop through row
	        for (int colIndex=0;colIndex<dataArray[rowIndex].length;
	    							colIndex++) {
	            if (firstAtt) firstAtt=false;
	            else outputFile.print(" ");
	            outputFile.print(dataArray[rowIndex][colIndex]);
	            }
	        outputFile.println();
	        }
	    }
	
	// Close file
	outputFile.close();
	}
	
    /* OUTPUT SEGMENT TO FILE */
    
    /** Outputs a segment of the input dataset to file. 
    @param fileName the name of the output file
    @param startRecord the record marking the start of the segment
    @param endRecord the record marking the end of the segement. */
    
    /* private void ouputSegmentToFile(String outputFileName, int startRecord,
    					int endRecord) throws IOException {
        // Open file for writing
	PrintWriter outputFile = new 
				PrintWriter(new FileWriter(outputFileName));
	
	// Step through data array
	for (int rowIndex = startRecord;rowIndex<endRecord;rowIndex++) {
	    // Read line
	    String line = fileInput.readLine();
	    // Output line
	    outputFile.println(line);
	    }
	outputFile.close();	
	}
	
    /* OUTPUT PARTION TO FILE */
    
    /** Outputs a one column partition of the data array to file.
    @param fileName the name of the output file
    @param colNumber the column number indicating the partition. */
                 
    /* private void outputPartitionToFile(String fName, short colNumber) 
    				throws IOException {

        // Open file for writing
	PrintWriter outputFile = new PrintWriter(new FileWriter(fName + 
						"." + colNumber));
	
	// Step through data array
	for (int rowIndex = 0;rowIndex<dataArray.length;rowIndex++) {
	    if (memberOf(colNumber,dataArray[rowIndex])) {
	        boolean firstAtt = true;
	        for (int colIndex=0;colIndex<dataArray[rowIndex].length;
								colIndex++) {
		    if (firstAtt) firstAtt=false;
	            else outputFile.print(" ");
		    outputFile.print(dataArray[rowIndex][colIndex]);
		    if (dataArray[rowIndex][colIndex]==colNumber) break;
		    }
		outputFile.println();
		}
	    else outputFile.println();
	    }
	    
	// End
	outputFile.close();	
	}

    /* OUTPUT ITEMSET TO FILE */
    /** Outputs a given item set.
    @param itemSet the given item set.
    @param outputFile the name ofb the desirted output file. */

    /* protected void outputItemSetToFile(short[] itemSet,
    				PrintWriter outputFile) throws IOException {
	
	// Loop through item set elements
	
	if (itemSet == null) outputFile.print(" null ");
	else {
            int counter = 0;
	    for (int index=0;index<itemSet.length;index++) {
	        if (counter == 0) counter++;
		else outputFile.print(" ");
	        outputFile.print(Short.toString(itemSet[index]));
		}
	     outputFile.println();
	    } 
	} */
	
    /* -------------------------------- */
    /*                                  */
    /*        OUTPUT UTILITIES          */
    /*                                  */
    /* -------------------------------- */
    
    /* TWO DECIMAL PLACES */
    
    /** Converts given real number to real number rounded up to two decimal 
    places. 
    @param number the given number.
    @return the number to two decimal places. */ 
    
    protected double twoDecPlaces(double number) {
    	int numInt = (int) ((number+0.005)*100.0);
	number = ((double) numInt)/100.0;
	return(number);
	}    
    }

