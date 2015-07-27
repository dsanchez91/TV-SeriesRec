package CBAFuzzy;

/* -------------------------------------------------------------------------- */
/*                                                                            */
/*                            Apriori-TFP CLASSIFIER                          */
/*                                                                            */
/*                                Frans Coenen                                */
/*                                                                            */
/*                           Monday 2 February 2004                           */
/*                            (Revised: 20/1/2005)                            */
/*                                                                            */
/*                       Department of Computer Science                       */
/*                        The University of Liverpool                         */
/*                                                                            */
/* -------------------------------------------------------------------------- */

/* Class structure

AssocRuleMining
      |
      +-- TotalSupportTree
                |
		+-- PartialSupportTree
			  |
			  +-- AprioriTFPclass		*/

/** Methods to produce classification rules using a Apriori-T appraoch. Assumes 
that input dataset is orgnised such that classifiers are at the end of each 
record. Note: number of classifiers value is stored in the <TT>numClasses</TT> 
field. 
@author Frans Coenen
@version 20 January 2005 */

public class AprioriTFPclass extends PartialSupportTree {

	private static final long serialVersionUID = 1L;	
	/* ------ FIELDS ------ */
    // Constants
    /** Maximum size of classification rule antecedent. */
    protected final int MAX_SIZE_OF_ANTECEDENT = 6;
      
    // Data structures
    
    /** 2-D array to hold the test data <P> Note that classifiaction
    involves producing a set of Classification Rules (CRs) from a training
    set and then testing the effectiveness of the CRs on a test set. */
    protected short[][] testDataArray = null;
    /** 3-data array to hold 10th sets of input data. <P> Used in
    conjunction with "10 Cross Validation" where the input data is divided
    into 10 sunsets and CRs are produced using each subset in turn and validated
    against the remaininmg 9 sets. The oveerall average accuracy is then the
    total accuracy divided by 10. */
    protected short[][][] tenthDataSets = new short[10][][];
    
    // Other fields
    /** Number of rows in input data set, not the same as the number of rows
    in the classification training set. <P> Used for temporery storage of total
    number of rows when using Ten Cross Validation (TCV) approach only. <P> The 
    <TT>numRows</TT> field inherited from the super class records is used 
    throughout the CR generation process. Set to number of rows in the training
    set using <TT>setNumRowsInInputSet</TT> method called by application 
    class. */
    protected int numRowsInInputSet;
    /** Number of rows in test set, again not the same as the number of rows
    in the classification training set. */
    protected int numRowsInTestSet;
    /** Number of rows in training set, also not the same as the number of rows
    in the classification training set. */
    protected int numRowsInTrainingSet;
    
    // Diagnostic fields
    /** Average accuracy as the result of TCV. */
    protected double averageAccuracy;
    /** Standard deviation from average accuracy. */
    protected double sdAccuracy;
    /** Average number of frequent sets as the result of TCV. */
    protected double averageNumFreqSets;
    /** Average number of updates as the result of TCV. */
    protected double averageNumUpdates; 
    /** Average accuracy number of callsification rules as the result of TCV. */
    protected double averageNumCRs;
    /** Average fp-rate as the result of TCV. */
    protected double averageFprate;
    /** Average tp-rate as the result of TCV. */
    protected double averageTprate;
    
    /* ------ CONSTRUCTORS ------ */

    /** Constructor processes command line arguments.
    @param args the command line arguments (array of String instances). */
    
    public AprioriTFPclass(String[][] trainingData, int numClasses, double minSup, double minConf) {
    	super(trainingData, numClasses, minSup, minConf);
	}
	
    /* ------ METHODS ------ */
    	
    /* ---------------------------------------------------------------- */
    /*                                                                  */
    /*                        COMMAND LINE ARGUMENTS                    */
    /*                                                                  */
    /* ---------------------------------------------------------------- */
    
    /* CREATE TRAINING AND TEST DATA SETS. */
    /** Populates test and training datasets. <P> Note: (1) assumes a 50:50 
    split, (2) training data set is stored in the dataArray structure in which 
    the input data is stored, (3) method called from application class as same 
    training and test sets may be required if using (say) "hill climbing" 
    approach to maximise accuracy, (4) method is not called from constructor
    partly for same reason as 3 but also because the input data set may (given 
    a particular application) first require ordering and possibly also pruning
    and recasting (see recastClassifiers method). */
    	
    public void createTrainingAndTestDataSets(boolean allDataSet) {
    	final double PERCENTAGE_SIZE_OF_TEST_SET; 
    	//Determine size of training and test sets.
    	
    	if(allDataSet)
    		PERCENTAGE_SIZE_OF_TEST_SET = 10.0;
    	else
    		PERCENTAGE_SIZE_OF_TEST_SET = 50.0;

        numRowsInTestSet     = (int) ((double) (numRows)*PERCENTAGE_SIZE_OF_TEST_SET/100.0);
        numRowsInTrainingSet = numRows-numRowsInTestSet;
        numRows              = numRowsInTrainingSet;
        
        // Dimension and populate training set. 
		short[][] trainingSet = new short[numRowsInTrainingSet][];
		int index1=0;
		for (;index1<numRowsInTrainingSet;index1++) 
			trainingSet[index1] = dataArray[index1];
		
		// Dimension and populate test set
		testDataArray = new short[numRowsInTestSet][];
		for (int index2=0;index1<dataArray.length;index1++,index2++) 
			testDataArray[index2] = dataArray[index1];

		// Assign training set label to input data set label.
		dataArray = trainingSet;
	}

    /** Populates test and training datasets. <P> Note: (1) works on a 9:1
    split with nine of the tenths data sets forming the training set and
    the remaining one tenth the test set, (2) training data set is stored in 
    the same dataArray structure in which the initial input data is stored,
    (3) this method is not called from the constructor as the input data set may
    (given a particular application) first require ordering and possibly also pruning. 
    @param testSetIndex the index of the tenths data sets to be used as the test set. */
    public void createTrainingAndTestDataSets(int testSetIndex) {
        // Dimension and populate test set.
        numRowsInTestSet = tenthDataSets[testSetIndex].length;
		testDataArray    = tenthDataSets[testSetIndex];
	        // Dimension of and populate training set.
		numRowsInTrainingSet = numRowsInInputSet-numRowsInTestSet;
		numRows              = numRowsInTrainingSet;

		short[][] trainingSet = new short[numRows][];
		int trainingSetIndex=0;	
		// Before test set
		for(int index=0;index<testSetIndex;index++) {
		    for (int tenthsIndex=0;tenthsIndex<tenthDataSets[index].length; tenthsIndex++,trainingSetIndex++)
		        trainingSet[trainingSetIndex] =	tenthDataSets[index][tenthsIndex];
		    }    	
		// After test set
		for(int index=testSetIndex+1;index<tenthDataSets.length;index++) {
		    for (int tenthsIndex=0;tenthsIndex<tenthDataSets[index].length;
						tenthsIndex++,trainingSetIndex++) {
		        trainingSet[trainingSetIndex] = 
						tenthDataSets[index][tenthsIndex];
		        }
		    }
		
		// Assign training set label to input data set label.
		dataArray = trainingSet;   
	}
	
    /* CREATE TENTHS DATA SETS. */

    /** Populates ten tenths data sets for use when doing Ten Cross Validation
    (TCV) --- test and training datasets. <P> Note: this method is not called 
    from the constructor as the input data set may (given a particular 
    application) first require ordering (and possibly also pruning!). */
    public void createTenthsDataSets() {
		// If number of rows is less than 10 cannot create appropriate data sets
		if (numRows<10) {
		    System.out.println("ERROR: only " + numRows + ", therefore cannot create tenths data sets!");
		    System.exit(1);
		}
				
		// Determine size of first nine tenths data sets.
		int tenthSize = numRows/10;
		
		// Dimension first nine tenths data sets.
		int index=0;
		for( ;index<tenthDataSets.length-1;index++)
			tenthDataSets[index] = new short[tenthSize][];
		// Dimension last tenths data set
		tenthDataSets[index] = new short[numRows-(tenthSize*9)][];	
		
		// Populate tenth data sets
		int inputDataIndex=0;
		for(index=0;index<tenthDataSets.length;index++)
		    for(int tenthIndex=0;tenthIndex<tenthDataSets[index].length;tenthIndex++,inputDataIndex++)
		        tenthDataSets[index][tenthIndex] = dataArray[inputDataIndex];
	}
			
    /* ------------------------------------------------------------- */
    /*                                                               */
    /*                         CLASSIFIER                            */
    /*                                                               */
    /* ------------------------------------------------------------- */
	
    /* CLASSIFY RECORD (HIGHEST CONFIDENCE AND DEFAULT RULE) */
    /** Searches through rule data looking for a rule antecedent which is a
    subset of the input set or the default rule (last rule).
    @param itemset the record to be classified.
    @return the classification. */
    protected short classifyRecordDefault(short[] itemSet) {	
		RuleNode linkRuleNode = startRulelist;
		
		while(true) {
		    // Default (last) rule, next reference is empty
		    if (linkRuleNode.next==null) 
		    	return(linkRuleNode.consequent[0]);
		    // Compare antecedents
		    if (isSubset(linkRuleNode.antecedent,itemSet))
		    	return(linkRuleNode.consequent[0]);
		    else  // Increment parameters
		    	linkRuleNode = linkRuleNode.next;
		}
	}
    
    /* CLASSIFY RECORD (HIGHEST CONFIDENCE AND WITHOUT DEFAULT RULE) USED FOR FP-RATE */
    /** Searches through rule data looking for a rule antecedent which is a
    subset of the input set.
    @param itemset the record to be classified.
    @return the classification. */
    protected short classifyRecord(short[] itemSet) {	
		RuleNode linkRuleNode = startRulelist;
		
		while(true) {
			//not classified
			if (linkRuleNode.next==null) 
		    	return(0);
			// Compare antecedents (just considering rules with more than one antecedent)
		    if (isSubset(linkRuleNode.antecedent,itemSet) && linkRuleNode.antecedent.length>1)
		    	return(linkRuleNode.consequent[0]);
		    else  // Increment parameters
		    	linkRuleNode = linkRuleNode.next;
		}
	}

    /*------------------------------------------------------------------- */
    /*                                                                    */
    /*                             SET METHODS                            */
    /*                                                                    */
    /*------------------------------------------------------------------- */
    
    /* SET NUM ROWS IN INPUT SET */
    /** Assigns value to the <TT>numRowsInInputSet</TT> field. <P> used in
    conjunction with TCV to "remember" the overall number of rows in the 
    input data set. <P> Usually called from application classes. */
    
    public void setNumRowsInInputSet() {
        numRowsInInputSet = numRows;
	}
	
    /* ---------------------------------------------------------------- */
    /*                                                                  */
    /*                             GET METHODS                          */
    /*                                                                  */
    /* ---------------------------------------------------------------- */
    
    /* GET AVERAGE ACCURACY */
    /** Gets value for average accuracy field.
    @return average accuracy. */
   
    public double getAverageAccuracy() {
        return(averageAccuracy);
	}

    /* GET STANDARD DEVIATION */
    /** Gets value for average accuracy field.
    @return average accuracy. */
   
    public double getSDaccuracy() {
        return(sdAccuracy);
	}
    
    /* GET AVERAGE NUMBER OF FREQUENT SETS */
    /** Gets value for average umber of frequent sets field.
    @return averagenumber of frequent sets. */
   
    public double getAverageNumFreqSets() {
        return(averageNumFreqSets);
	}
    
    /* GET AVERAGE NUMBER OF CLASSIFICATION RULES */
    /** Gets value for average number of generated classification rules field.
    @return average number of classification rules. */
   
    public double getAverageNumCRs() {
        return(averageNumCRs);
	}
	
    /* ---------------------------------------------------------------- */
    /*                                                                  */
    /*                               OUTPUT                             */
    /*                                                                  */
    /* ---------------------------------------------------------------- */
        
    /* OUTPUT MENU */
    
    /** Outputs menu for command line arguments. (Overides higher level method)
    */
    
    protected void outputMenu() {
        System.out.println();
		System.out.println("-A  = Number of attribute");
		System.out.println("-C  = Confidence (default 80%)");
		System.out.println("-F  = File name");	
		System.out.println("-R  = Number of records");
		System.out.println("-S  = Support (default 20%)"); 
		System.out.println("-N  = Number of classes"); 
		System.out.println();
	
		// Exit
		System.exit(1);
	}
    
    /* OUTPUT SETTINGS */
    
    /** Outputs command line values provided by user. (Overides higher level 
    method.) */
    
    protected void outputSettings() {
        System.out.println("SETTINGS\n--------");	
		System.out.println("Support (default 20%)         = " + support); 
		System.out.println("Confidence (default 80%)      = " + confidence);
		System.out.println("Number of classes             = " + numClasses);
		System.out.println();
    }

}
