package CBAFuzzy;

/* -------------------------------------------------------------------------- */
/*                                                                            */
/*        APRIORI-TFP CLASSIFICATION ASSOCIATION RULE (CAR) GENERATION        */
/*                                                                            */
/*                               Frans Coenen                                 */
/*                                                                            */
/*                           Tuesday 27 January 2004                          */
/*                     (Revised Thursday 5th February 2004)                   */
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
			+--AprioriTFPclass
				|
				+-- AprioriTFP_CARgen		*/

/** Methods to produce classification rules using a Apriori-TFP appraoch. 
Assumes that input dataset is orgnised such that classifiers are at the end of 
each record. CARs differ from ARs in that they have only a single consequent 
and that the number of admissable consequents is limited. Note that: (i) number 
of classifiers value is stored in the <TT>numClasses</TT> field, (ii) note that
classifiers are assumed to be listed at the end of the attribute list.    
@author Frans Coenen
@version 27 January 2004 */

public class AprioriTFP_CARgen extends AprioriTFPclass {

	private static final long serialVersionUID = 1L;
	
	/* ------ FIELDS ------ */
    
    // CONSTANTS
    /** The maximum number of CARs */
    protected final int MAX_NUM_CARS = 80000;
    
    // OTHER FIELDS
    /** The number of CARs generated so far. */
    protected int numCarsSoFar = 0;
    
    /* ------ CONSTRUCTORS ------ */

    /** Constructor processes command line arguments.
    @param args the command line arguments (array of String instances). */
    
    public AprioriTFP_CARgen(String[][] trainingData, int numClasses, double minSup, double minConf) {
    	super(trainingData, numClasses, minSup, minConf);
	}
	
    /* ------ METHODS ------ */
			
    /** Continues process of genertaing CARS using apriori TFP.	*/
    protected void startCARgeneration2(double datasetSize) {
        // Calculate minimum support threshold in terms of number of records in the training set.

		minSupport = (numRowsInTrainingSet-(addedLines*datasetSize))*support/100.0;
		// Set rule list to null. Note that startRuleList is defined in the
		// AssocRuleMining parent class and is also used to store Association
		// Rules (ARs) with respect ARM.
		startRulelist = null;
		numCarsSoFar = 0;
		
		// Create P-tree
		createPtree();
		
		// Generate T-tree and generate CARS (method contained in
		// PartialSupportTree class)
        createTotalSupportTree();
	}
	
    /* ------------------------------- */
    /*         T-TREE METHODS          */
    /* ------------------------------- */	
		
    /* CREATE T-TREE LEVEL N */

    /** Commences the process of determining the remaining levels in the T-tree
    (other than the top level), level by level in an "Apriori" manner. <P>
    Follows an add support, prune, generate loop until there are no more levels
    to generate. Overides method in TotalSupportTree class
    distinctiion between the two methods is that this version produces CARs as
    opposed to ARs. */

    protected void createTtreeLevelN() {
        int nextLevel=2;
   	
		// Loop while a further level exists
		while (nextLevelExists) {
		    // Add support
		    addSupportToTtreeLevelN(nextLevel);
		    // Prune unsupported candidate sets (method defined in 
		    // PartialSupportTree class)
		    pruneLevelN(startTtreeRef,nextLevel);
		    // Generate Classification Association Rules (CARs)
		    generateCARs(nextLevel);
		    // Check number of frequent sets generated so far
		    if (numFrequentsets>MAX_NUM_FREQUENT_SETS) {
		        System.out.println("Number of frequent sets (" + numFrequentsets + ") generted so far " + "exceeds limit of " + MAX_NUM_FREQUENT_SETS +	", generation process stopped!");
		        break;	
			}
		    // Check antecedent size, level will indicate size of any
		    // frequent sets generated so far. The antecedent size will this be
		    // the level number minus 1
		    if (nextLevel > MAX_SIZE_OF_ANTECEDENT) {
		        System.out.println("CR antecedent size (" + (nextLevel-1) + ") is at limit of " +  MAX_SIZE_OF_ANTECEDENT + ", generation process stopped!");
		        break;	
			}
		    // Attempt to generate next level (method defined in 
		    // PartialSupportTree class)
		    nextLevelExists=false;
		    generateLevelN(startTtreeRef,nextLevel,null);
		    nextLevel++;
		}
	}
	
    /*----------------------------------------------------------------------- */
    /*                                                                        */
    /*            CLASSIFICATION ASSOCIATION RULE (CAR) GENERATION            */
    /*                                                                        */
    /*----------------------------------------------------------------------- */
    
    /* GENERATE CLASSIFICATION ASSOCIATION RULES */   
    /** Initiates process of generating Classification Association Rules (CARS),
    Loops through top level of T-tree as part of the CAR generation process.
    <P>CARs differ from ARs in that they have only a single consequent and that
    the number of admissable consequents is limited. Note that classifiers are
    assumed to be listed at the end of the attribute list. 
    @param start the identification number of the first classifier to be
    considered. */
    
    protected void generateCARs(int level) {		
    	// Loop	
    	for (int index=numOneItemSets-numClasses+1;index<=numOneItemSets;index++) {
		    // Check number of CARS generated so far
		    if (numCarsSoFar>MAX_NUM_CARS) {
		    	System.out.println("Number of CARs (" + numCarsSoFar + ") generted so far exceeds limit of " + MAX_NUM_CARS + ", generation process stopped!");
		    	return;	
	        }
		    // Else process
		    if (startTtreeRef[index]!=null && startTtreeRef[index].childRef!=null) {
		        if (startTtreeRef[index].support >= minSupport) {
		        	short[] consequent = new short[1];
		        	consequent[0] = (short) index;
		        	generateCARs(null,index,level-1,consequent,startTtreeRef[index].childRef);
			    }
		    }
	    } 
	}
    
    /* GENERATE CLASSIFICATION ASSOCIATION RULES */
    
    /** Continues process of generating classification association rules from 
    a T-tree by recursively looping through T-tree level by level. 
    @param itemSetSofar the label for a T-treenode as generated sofar.
    @param size the length/size of the current array lavel in the T-tree.
    @param level the current level in the T-tree
    @param consequent the current consequent (classifier) for the CAR.
    @param linkRef the reference to the current array lavel in the T-tree. */
    
    protected void generateCARs(short[] itemSetSofar, int size, int level,short[] consequent, TtreeNode[] linkRef) {
		// If no more nodes return	
		if (linkRef == null)
			return;
		   
		// At right level
		if (level==1) 
			generateCARsRightLevel(itemSetSofar,size,consequent,linkRef);
		else // Wrong level, Otherwise process
			generateCARsWrongLevel(itemSetSofar,size,consequent,level,linkRef);
	}	

    /* GENERATE CLASSIFICATION ASSOCIATION RULES (RIGHT LEVEL). */
    
    /** Generating classificationh association rules from a given array of 
    T-tree nodes. 
    @param itemSetSofar the label for a T-treenode as generated sofar.
    @param size the length/size of the current array lavel in the T-tree.
    @param consequent the current consequent (classifier) for the CAR.
    @param linkRef the reference to the current array lavel in the T-tree. */
    
    protected void generateCARsRightLevel(short[] itemSetSofar, int size, short[] consequent, TtreeNode[] linkRef) {
    	// Loop through T-tree array
    	for (int index=1; index < size; index++) {
		    // Check if node exists
		    if (linkRef[index] != null) {
		        // Generate Antecedent
		        short[] tempItemSet = realloc2(itemSetSofar,(short) index);
		        // Determine confidence
		        double confidenceForCAR = getConfidence(tempItemSet,linkRef[index].support);
		        // Add CAR to linked list structure if confidence greater
		        // than minimum confidence threshold.
		        if (confidenceForCAR >= confidence) {
		    	    numCarsSoFar++;
		    	    insertRuleintoRulelist(tempItemSet,consequent,confidenceForCAR); 
		        }
		    }
	    }
	}	

    /* GENERATE CLASSIFICATION ASSOCIATION RULES (WRONG LEVEL). */
    
    /** Generating classificationh association rules from a given array of 
    T-tree nodes. 
    @param itemSetSofar the label for a T-treenode as generated sofar.
    @param size the length/size of the current array lavel in the T-tree.
    @param consequent the current consequent (classifier) for the CAR.
    @param level the current level in the T-tree.
    @param linkRef the reference to the current array lavel in the T-tree. */    
    protected void generateCARsWrongLevel(short[] itemSetSofar, int size,short[] consequent, int level, TtreeNode[] linkRef) {
        // Loop through T-tree array
    	for (int index=1; index < size; index++) {
		    // Check if node exists
		    if (linkRef[index] != null && linkRef[index].childRef!=null) {
		        short[] tempItemSet = realloc2(itemSetSofar,(short) index);
		        // Proceed down child branch
		        generateCARs(tempItemSet,index,level-1,consequent,linkRef[index].childRef); 
			}
	    }
	}
    
    /* ----------------------------- */
    /*                               */
    /*        OUTPUT METHODS         */
    /*                               */
    /* ------------------------------ */
	
    /* TCV OUTPUT */
    
    /** Output values from TCV excercise. 
    @param parameter the 2D array contaning details of results. */
    
    protected void tcvOutput(double[][] parameters) {
		double totalAccu        = 0;
		double totalNumFreqSets = 0;
		double totalNumUpdates  = 0;
		double totalNumCRs      = 0;
		double totalFprate      = 0;
		double totalTprate      = 0;
		
		// Determine totals
		for (int index=0;index<parameters.length;index++) {
		    System.out.println("(" + (index+1) + ") Accuracy = " + twoDecPlaces(parameters[index][0]) + ", Num. Freq. Sets = " + twoDecPlaces(parameters[index][1]) + ", Num Updates = " + twoDecPlaces(parameters[index][2]) + ", Num CRs = " + twoDecPlaces(parameters[index][3]));	
		    // Totals
		    totalAccu        = totalAccu+parameters[index][0];
		    totalNumFreqSets = totalNumFreqSets+parameters[index][1];
		    totalNumUpdates  = totalNumUpdates+parameters[index][2];
		    totalNumCRs      = totalNumCRs+parameters[index][3];
		    totalFprate      = totalFprate+parameters[index][4];
		    totalTprate      = totalTprate+parameters[index][5];
	    }
		    
		// Calculate averages
		averageAccuracy    = totalAccu/10;
        averageNumFreqSets = totalNumFreqSets/10;
    	averageNumUpdates  = totalNumUpdates/10;
    	averageNumCRs      = totalNumCRs/10;
    	averageFprate      = totalFprate/10;
    	averageTprate      = totalTprate/10;
		
		// Calculate standard deviation for accuracy
		double residuals = 0.0;
		for (int index=0;index<parameters.length;index++) {
		    double residual = parameters[index][0]-averageAccuracy;
		    residuals = residuals + Math.pow(residual,2.0);
	    }
		sdAccuracy = Math.sqrt(residuals/9.0); 
		        
		// Output averages 
		System.out.println("---------------------------------------");
		//System.out.println("Ave. # Freq. Sets   = " + twoDecPlaces(averageNumFreqSets) + "\nAvergae Num Updates = " + twoDecPlaces(averageNumUpdates)  + "\nAverage Num CRs     = " +  twoDecPlaces(averageNumCRs) + "\nAverage Accuracy    = " + twoDecPlaces(averageAccuracy)    + "\nsdAccuracy          = " + twoDecPlaces(sdAccuracy)+ "\nAverage FP-Rate     = " + twoDecPlaces(averageFprate)+ "\nAverage TP-Rate     = " + twoDecPlaces(averageTprate));
		System.out.println("Ave. # Freq. Sets   = " + twoDecPlaces(averageNumFreqSets) + "\nAvergae Num Updates = " + twoDecPlaces(averageNumUpdates)  + "\nAverage Num CRs     = " +  twoDecPlaces(averageNumCRs) + "\nAverage Accuracy    = " + twoDecPlaces(averageAccuracy)    + "\nsdAccuracy          = " + twoDecPlaces(sdAccuracy)+ "\nAverage FP-Rate     = " + twoDecPlaces(averageFprate));
	}
 }