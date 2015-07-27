package CBAFuzzy;

/* -------------------------------------------------------------------------- */
/*                                                                            */
/*            APRIORI-TFP CBA (CLASSIFICATION BASED ON ASSOCIATIONS)          */
/*                                                                            */
/*                               Frans Coenen                                 */
/*                                                                            */
/*                             Friday 12 March 2004                           */
/*                     (Bug fixes and maintenance: 10/2/2005)                 */
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
				+-- AprioriTFP_CARgen
					|
					+-- AprioriTFP_CBA	*/

// Java packages
		
import java.io.*;
import java.util.Scanner;

/** Methods to produce classification rules using Wenmin Li, Jiawei Han and
Jian Pei's CBA (Classification based on Multiple associate Rules) algorithm
but founded on Apriori-TFP. Assumes that input dataset is orgnised such that
classifiers are at the end of each record. Note: number of classifiers value is
stored in the <TT>numClasses</TT> field.
@author Frans Coenen
@version 2 March 2004 */

public class AprioriTFP_CBA extends AprioriTFP_CARgen {
	
	private static final long serialVersionUID = 1L;
	/* ------ FIELDS ------ */

    // --- Data structures ---

    /** Structure to store rules that the current ruke overrrides, ie rules
    that have lower precedence than the given rule. <P>Used where the current
    rule wrongly classifies a record and the alternative lower precedence rule
    (overriden by the current rule) correctly classifies the same record.*/

    private class Overrides {
        /** A potential cRule that may Overrides an alternative, but lower
        precedence, cRule. */
        AprioriTFP_CBA.RuleNodeCBA cRule;
        /** The record TID in the training set. */
        int tid;
        /** The associated class label. */
        short classLabel;
        /** Link to next node. */
        AprioriTFP_CBA.Overrides next = null;

        /** Three argument constructor.
        @param cr the given crule the correctly classifies at least one record.
        @param tidNumber the TID number foe  arecord in the training set.
        @param cl the class label for the given record.  */

        private Overrides(AprioriTFP_CBA.RuleNodeCBA cr, int tidNumber, short cl) {
            cRule      = cr;
            tid        = tidNumber;
            classLabel = cl;
            }
        }

    /** Rule node in linked list of rules for CBA Classification rules. */

    private class RuleNodeCBA {
	    /** The Antecedent of the CAR. */
		private short[] antecedent;
		/** The Consequent of the CAR. */
		private short[] consequent;
		/** List of possible alternative rules that can be used to replace the current rules. */
		AprioriTFP_CBA.Overrides replaceList = null;
		/** The confidence value associate with the rule represented by this node. */
		private double confidenceForRule=0.0;
		/** The support value associate with the rule represented by this node. */
		private double supportForRule=0.0;
		/** The (local) dustribution array for the rule describing the number 
		of records covered/satisfied per class (taking into consideration 
		records already covered by previous rules with higher precedence). */
		private int[] classCasesCovered = new int[numClasses];
		/** A flag to indicate whether the rule is a cRule for at least one	record. */
		private boolean isAcRule = false;
		/** A flag to indicate whether the rule Is a strong cRule for at
		least one record, i.e. is has greater orecedence than the associated wRule. */
		private boolean isAstrongCrule = false;
		/** The default class associated with this rule. */
		private short defaultClass = 0;
		/** The total errors associated with this rule. */
		private int totalErrors = 0;
		/** Link to next node. */
		private AprioriTFP_CBA.RuleNodeCBA next = null;
		
		/** Four argument constructor
		@param antecedent the antecedent (LHS) of the AR.
    	@param consequent the consequent (RHS) of the AR.
		@param consvalue the associated confidence value.
    	@param suppValue the associated support value. */
		
		private RuleNodeCBA(short[] ante, short[]cons,  double confValue, double suppValue) {
		    antecedent        = ante;
		    consequent        = cons;
		    confidenceForRule = confValue;
		    supportForRule    = suppValue;
		
		    // Initialise class records array
		    for (int index=0;index<numClasses;index++) 
		    	classCasesCovered[index]=0;
		}
	}

    /** Set A structure */
    private class SetA {
        /** The TID number of a record in the training set. */
        private int tid;
        /** The class of the record with the given TID. */
        private short classLabel;
        /** The associated cRule. */
        private AprioriTFP_CBA.RuleNodeCBA cRule;
        /** The associated wRule. */
        private AprioriTFP_CBA.RuleNodeCBA wRule;
		/** Link to next node. */
		private AprioriTFP_CBA.SetA next = null;
		
	    /** Four argument constructor
		@param tidNumber the record number
	   	@param classID the record class.
		@param cr the associated cRule.
	   	@param ar the associated wRule. */
		
		private SetA(int tidNumber, short classID, AprioriTFP_CBA.RuleNodeCBA cr, AprioriTFP_CBA.RuleNodeCBA  wr) {
	            tid = tidNumber;
	            classLabel = classID;
	            cRule = cr;
	            wRule = wr;
	    }
    }
        	
    /** The reference to the start of the CBA rule list. */
    protected AprioriTFP_CBA.RuleNodeCBA startCBAruleList = null;
    /** The reference to the start of the SetA list. */
    protected AprioriTFP_CBA.SetA startSetAlist = null;

    /* ------ CONSTRUCTORS ------ */

    /** Constructor processes command line arguments.
    @param args the command line arguments (array of String instances). */

    public AprioriTFP_CBA(String[][] trainingData, int numClasses, double minSup, double minConf) {
    	super(trainingData, numClasses, minSup, minConf);
	}
	
    /* ------ METHODS ------ */

    /* START CBA CLASSIFICATION */

    /** Starts CBA classifier generation proces. <P> Proceeds as follows:<OL>	
    <LI>Generate all CARs using Apriori-TFP and place selected CARs into linked
    list of rules.
    <LI>Prune list according the cover stratgey.
    <LI>Test classification using Chi-Squared Weighting approach.</OL>
    @return The classification accuarcay (%).	*/

    public double[] startCBAclassification(double datasetSize) {
        /*System.out.println("START APRIORI-TFP CBA\n" + "--------------------------");	
		System.out.println("Max number of CARS   = " + MAX_NUM_CARS);
		System.out.println("Max size antecedent  = " + MAX_SIZE_OF_ANTECEDENT);*/
		// Proceed
		return(startCBAclassification2(datasetSize));
	}
	
    /** Continues process of starting the CMAR classifier generation proces. 
    @return The classification accuarcay (%).	*/
    
    protected double[] startCBAclassification2(double datasetSize) {
		// Set data structure references to null
		startSetAlist = null;
		startCBAruleList = null;
		    				
		// Generate all CARs using Apriori-TFP and place selected CARs into linked list of rules.
        startCARgeneration2(datasetSize);
		//outputCBArules();	
                //System.out.println("----------");
        // Prune linked list of rules using CBA "cover" principal
        if(bd.InputData.getOptMenuCBAFuzzy()==0)
        	outputNumCBArules();
		pruneUsingCBAapproach(copyItemSet(dataArray));
		if(bd.InputData.getOptMenuCBAFuzzy()==0)
			outputNumRules();
		
		// Test classification using the test set.
		double testClassif[];
		testClassif = new double[3];
		testClassif = testClassification();
		if(bd.InputData.getOptMenuCBAFuzzy()==0){
			System.out.println("Accuracy = " +  twoDecPlaces(testClassif[0]));
			System.out.println("FP-Rate = " +  twoDecPlaces(testClassif[1]));
			//System.out.println("TP-Rate = " +  twoDecPlaces(testClassif[2]));
		}
		return(testClassif);
    }
        		
    /*----------------------------------------------------------------------- */
    /*                                                                        */
    /*             APRIORI-TFP CBA WITH TEN CROSS VALIDATION (TCV)            */
    /*                                                                        */
    /*----------------------------------------------------------------------- */

    /* COMMEMCE TEN CROSS VALIDATION WITH OUTPUT*/

    /** Start Ten Cross Validation (TCV) process with output of individual
    accuracies. */

    public void commenceTCVwithOutput() {
        double[][] parameters = new double[10][6];

		System.out.println("START TCV APRIORI-TFP CBA CLASSIFICATION\n" + "------------------------------------");
		System.out.println("Max number of CARS   = " + MAX_NUM_CARS);
		System.out.println("Max size antecedent  = " + MAX_SIZE_OF_ANTECEDENT);
		
		// Loop through tenths data sets
		for (int index=0;index<10;index++) {
		    System.out.println("[--- " + index + " ---]");
		    // Create training and test sets
		    createTrainingAndTestDataSets(index);
		    // Mine data, produce T-tree and generate CRs
		    double testClassif[];
		    testClassif = new double[2];
		    testClassif = startCBAclassification2(0.9);
		    parameters[index][0] = testClassif[0]; //accuracy
		    parameters[index][1] = countNumFreqSets();
		    parameters[index][2] = numUpdates;
		    // Final rule set contained in the standrad list of rules not the
		    // CBA rule list defined in this class
		    parameters[index][3] = getNumCRs();
		    parameters[index][4] = testClassif[1]; //fprate
		    parameters[index][5] = testClassif[2]; //tprate
		}
	
		// Output
		tcvOutput(parameters);
	}
	
    /*----------------------------------------------------------------------- */
    /*                                                                        */
    /*            CLASSIFICATION ASSOCIATION RULE (CAR) GENERATION            */
    /*                                                                        */
    /*----------------------------------------------------------------------- */	

    /* GENERATE CLASSIFICATION ASSOCIATION RULES (RIGHT LEVEL). */
    
    /** Generating classificationh association rules from a given array of 
    T-tree nodes. <P> For each rule generated add to rule list if: (i) 
    Chi-Squared value is above a specified critical threshold (5% by default), 
    and (ii) the CR tree does not contain a more general rule with a higher 
    ordering. Rule added to rule list according to CBA ranking (ordering). 
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
				double confidenceForCAR = getConfidence(tempItemSet, linkRef[index].support);
				// Add CAR to linked list structure if confidence greater
				// than minimum confidence threshold.
				if (confidenceForCAR >= confidence) {
				    numCarsSoFar++;
				    //double suppForConcequent = (double) getSupportForItemSetInTtree(consequent);
				    insertRinRlistCBAranking(tempItemSet,consequent,confidenceForCAR,linkRef[index].support);
		        }
			}
	    }
	}	
	
    /* ---------------------------------------------------------------- */
    /*                                                                  */
    /*        RULE LINKED LIST ORDERED ACCORDING TO CBA RANKING         */
    /*                                                                  */
    /* ---------------------------------------------------------------- */

    /* Methods for inserting rules into a linked list of rules ordered
    according to CBA ranking. Each rule described in terms of 4 fields: 1)
    Antecedent (an item set), 2) a consequent (an item set), 3) a total support
    value and 4) a confidence value (double). */

    /* INSERT ASSOCIATION CLASSIFICATION) RULE INTO RULE LINKED LIST (ORDERED
    ACCORDING TO CBA RANKING). */

    /** Inserts an (association/classification) rule into the linkedlist of
    rules pointed at by <TT>startRulelist</TT>. <P> List is ordered according
    to "CBA" ranking.
    @param antecedent the antecedent (LHS) of the rule.
    @param consequent the consequent (RHS) of the rule.
    @param confidenceForRule the associated confidence value.
    @param supportForRule the associated support value. */

    protected void insertRinRlistCBAranking(short[] antecedent, short[] consequent, double confidenceForRule, double supportForRule) {
    	// Create new node
    	AprioriTFP_CBA.RuleNodeCBA newNode = new AprioriTFP_CBA.RuleNodeCBA(antecedent,consequent,confidenceForRule,supportForRule);
					
        // Empty list situation
		if (startCBAruleList == null) {
		    startCBAruleList = newNode;
		    return;
	    }
		
		// Add new node to start	
		if (ruleIsCBAgreater(newNode,startCBAruleList)) {
		    newNode.next = startCBAruleList;
		    startCBAruleList  = newNode;
		    return;
	    }
	
		// Add new node to middle
		AprioriTFP_CBA.RuleNodeCBA markerNode = startCBAruleList;
		AprioriTFP_CBA.RuleNodeCBA linkRuleNode = startCBAruleList.next;
		while (linkRuleNode != null) {
		    if (ruleIsCBAgreater(newNode,linkRuleNode)) {
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
	
    /* RULE IS CBA GREATER */

    /** Compares two rules and returns true if the first is "CBA greater" (has
    a higher ranking) than the second. <P> CBA ordering (same as CMAR) is as
    follows:
    <OL>
    <LI>Confidence, a rule <TT>r1</TT> has priority over a rule <TT>r2</TT> if
    <TT>confidence(r1) &gt; confidence(r2)</TT>.
    <LI>Support, a rule <TT>r1</TT> has priority over a rule <TT>r2</TT> if
    <TT>confidence(r1)==confidence(r2) &amp;&amp; support(r1)&gt;support(r2)
    </TT>.
    <LI>Size of antecedent, a rule <TT>r1</TT> has priority over a rule
    <TT>r2</TT> if <TT>confidence(r1)==confidence(r2) &amp;&amp;
    support(r1)==spoort(r2) &amp;&amp;|A<SUB>r1</SUB>|&lt;|A<SUB>r2</SUB>|
    </TT>.
    </OL>
    @param rule1 the given rule to be compared to the second.
    @param rule2 the rule which the given rule1 is to be compared to.
    @return true id rule1 is greater then rule2, and false otherwise. */

    private boolean ruleIsCBAgreater(AprioriTFP_CBA.RuleNodeCBA rule1, AprioriTFP_CBA.RuleNodeCBA rule2) {
		// Compare confidences
		if (rule1.confidenceForRule > rule2.confidenceForRule)
			return(true);
		
		// If confidences are the same compare support values
        if (similar2dec(rule1.confidenceForRule,rule2.confidenceForRule)) {
		   if (rule1.supportForRule > rule2.supportForRule) 
			   return(true);
		   // If confidences and supports are the same compare antecedents
		   if (similar2dec(rule1.supportForRule,rule2.supportForRule)) {
		       if (rule1.antecedent.length < rule2.antecedent.length)
		   			return(true);
		       }
		   }
		
		// Otherwise return false
		return(false);
	} 	
	
    /* ---------------------------------------------------------------- */
    /*                                                                  */
    /*                         PRUNE CBA RULES                          */
    /*                                                                  */
    /* ---------------------------------------------------------------- */	
	
    /* PRUNE CBA RULES */
    /** Commences process of generating local distribution arrays and producing
    rthe final classifier. <P>Done in four stages:</P>
    <OL>
    <LI>C AND W RULE IDENTIFICATION: Identify all cRules and wRules in the rule
    list. Record in the appropriate distribution arrays the number of records 
    covered by strong cRules. Where ever the wRule has higher precedence than 
    the corresponding cRule store the effected training set records in a linked
    list of SetA structures, <TT>A</TT>.
    <LI>CONSIDER WRONGLY CLASSIFIED RECORDS: Process the set <TT>A</TT> (the 
    list of records that are wrongly classified). If the "offending" wRule 
    can safely be removed from <TT>R</TT> identify other rules that 
    <I>override</I> (have higher precedence) the cRule corresponding to the 
    wRule. If the offending wRule cannot be removed (because it is a CRule with
    respect to some other record) then adjust local distribution array for the 
    wRule accordingly.
    <LI>DETERMINE DEFAULT CLASSES AND TOTAL ERROR COUNTS: Adjust local 
    distribution arrays with respect to overridden rules identified in Stage 2.
    Then for each "strong" cRule determine the deafault class and the total 
    error count.
    <LI>GENERATE CLASSIFIER Create classifier.
    </OL> */

    protected void pruneUsingCBAapproach(short[][] trainingSet) {
        // Check for empty rule list
		if (startCBAruleList==null) {
	 	    System.out.println("NO RULES GENERATED");
		    return;
		}
		
	    // (1) Find C and W rules
		findCandWrules(trainingSet);
	
	    // (2) Process wrongly classified records
	    procesSetAlinkedList(trainingSet);
		
		// (3) Process rule list
		processRuleList(trainingSet);
	
	    // (4) Generate classifier
	    generateClassifier();
    }

    /* -------------------------------------------------------- */
    /*           STAGE 1: C AND W RULE IDENTIFICATION           */
    /* -------------------------------------------------------- */

    /* FIND C AND W RULES */
    /** Processes training set and identifies appropriate cRules and wRules. 
    <P> Procceds as follows, for each record in the training set: <OL>
    <LI>Identify the first rule (in the ordered list of CBA rules) that 
    correctly classifies the record and the first rule that wrongly classifies 
    the record (these are the corresponding cRule and wRule for the record). 
    <LI>Compare the identified cRule and Wrule:<OL>
    	<LI>No cRule, do nothing
    	<LI>cRule exits but no wRule, mark cRule as a "Strong" cRule
    	<LI>cRule and wRule exist, and cRule has greater precedence than wRule, 
    	mark as a "Strong" cRule.
    	<LI>cRule and wRule exist, and wRule has greater precedence than cRule, 
    	create a SetA structure and add to linked list of SetA structtures.</OL>
    </OL>
    On completion: (i) all records which are wrongly classified (but for which 
    there is a corresponding cRule) will be collated into a linked list of SetA 
    structres ready for further consideration and (ii) all rules which are
    "strong" cRules with respect to at least one record will be identified.
    @param trainingSet the array of arrays of training set records. */

    private void findCandWrules(short[][] trainingSet) {
		//int numCrulesFound = 0;
		//int numMisses = 0;
		for(int index=0;index<trainingSet.length;index++) {
		    // Get class
		    int lastIndex = trainingSet[index].length-1;
		    short classLabel = trainingSet[index][lastIndex];
		    // Find cRule
		    AprioriTFP_CBA.RuleNodeCBA cRule = getCrule(trainingSet[index],classLabel);
		    // Find wRule
		    AprioriTFP_CBA.RuleNodeCBA wRule = getWrule(trainingSet[index],classLabel);
		    // Compare cRule and wRule. 
		    if (cRule != null) {
		    	//numCrulesFound++;
		    	if (wRule == null) cRule.isAstrongCrule = true;	// Option 2
		    	else {
		    	    if (ruleIsCBAgreater(cRule,wRule))
	    	    		cRule.isAstrongCrule = true;	// Option 3
		    	    else
		    	    	insertIntoSetAlist(index,classLabel,cRule,wRule);	// Option 4
		    	}
		    }
		    //else numMisses++;		
		}
		//System.out.println("numCrulesFound = " + numCrulesFound +
		//", numMisses = " + numMisses + ", total = " +
		//(numCrulesFound+numMisses));
	}
	
    /* GET C RULE */

    /** Returns first rule in CBA list (i.e. rule with highest precedence) that
    correctly classifies the given record and also increments the appropriate
    element in the identified rule's distribution array (an array of integers 
    indicating, for each record that the rule satisfies, the class to which 
    the record belongs to).
    @param record the given record.
    @param classLabel the class for the given record.
    @param the first rule that correctly classifies the record. */

    private AprioriTFP_CBA.RuleNodeCBA getCrule(short[] record, short classLabel) {
        AprioriTFP_CBA.RuleNodeCBA linkRef = startCBAruleList;
	
        while (linkRef != null) {
        	if (isSubset(linkRef.antecedent,record) &&	linkRef.consequent[0]==classLabel) {
        		linkRef.isAcRule=true;
        		linkRef.classCasesCovered[classLabel-numOneItemSets+numClasses-1]++;
        		return(linkRef);
        	}
        	linkRef=linkRef.next;
	    }
	
		// Default rerturn
		return(null);
	}

    /* GET W RULE */

    /** Returns first rule in CBA list (i.e. rule with highest precedence) that
    wrongly classifies the given record.
    @param record the given record.
    @param classLabel the class for the given record.
    @param the first rule that wrongly classifies the record. */

    private AprioriTFP_CBA.RuleNodeCBA getWrule(short[] record, short classLabel) {
        AprioriTFP_CBA.RuleNodeCBA linkRef = startCBAruleList;
	
		while (linkRef != null) {
		    if (isSubset(linkRef.antecedent,record) &&	linkRef.consequent[0]!=classLabel) 
		    	return(linkRef);
		    linkRef=linkRef.next;
		}
	
		// Default rerturn
		return(null);
	}	

    /* INSERT INTO SET A LIST */
    /** Insets details concerning a record where the wRule has stronger 
    precedence than the cRule (or there is no cRule) into a Set A record and 
    places into a linked list of such records.
    @param tidNumber the record number
    @param classID the record class.
    @param cr the associated cRule.
    @param ar the associated wRule.   */

    private void insertIntoSetAlist(int tidNumber, short classID,AprioriTFP_CBA.RuleNodeCBA cr, AprioriTFP_CBA.RuleNodeCBA  wr) {
        AprioriTFP_CBA.SetA tempSetA = new AprioriTFP_CBA.SetA(tidNumber,classID,cr,wr);
        tempSetA.next  = startSetAlist;
        startSetAlist = tempSetA;
    }

    /* ---------------------------------------------------------------- */
    /*           STAGE 2: CONSIDER WRONGLY CLASSIFIED RECORDS           */
    /* ---------------------------------------------------------------- */

    /* PROCESS SET A LINKED LIST */
    /** Processes the Set A linked list, the list of records that have been
    wrongly clasified. <P> Proceeds as follows:</P><OL>
    <LI>If wRule associated with a record is marked as a "cRule", i.e. 
    it correctly classifies at least one other record, adjust classCasesCovered 
    array for wRule and corresponding cRule.
    <LI>If wRule is not marked, i.e. it is not a "cRule" for any record,
    determine all the rules after the wRule, that wrongly classify the 
    record upto when the corresponding cRule is reached and place these in a 
    linked list of <TT>Overrides</TT> structures (these are then all the rules
    which we would like to remove from the rule list, if possible, so that
    the record in question will be correctly classified.</OL>
    @param trainingSet the array of arrays of training set records. */
    private void procesSetAlinkedList(short[][] trainingSet) {
		//System.out.println("In procesSetAlinkedList: # trainingSet records = " +
		//trainingSet.length);
        AprioriTFP_CBA.SetA linkRef = startSetAlist;
        // Loop through the list of records that have been wrongly classified
        while (linkRef != null) {
			//System.out.print("\ntid = " + linkRef.tid + ", linkRef.wRule = ");
			//outputRule(linkRef.wRule);
            // Get class index
            int index = linkRef.classLabel-numOneItemSets+numClasses-1;
            //System.out.println("Class index = " + index);  
            // If wRule is a strong cRule for some other record, we cannot get rid of it, therefore adjust distribution array accordingly.
            if (linkRef.wRule.isAcRule) {
            	//System.out.println("wRule is a strong cRule");
                linkRef.cRule.classCasesCovered[index]--;
                linkRef.wRule.classCasesCovered[index]++;
            } else {
	            // Else wRule is not a strong cRule for some other record,
	            // therefore identify all rules that overide the corresponding cRule for the record.
            	//System.out.println("wRule is NOT a strong cRule");
            	allCoverRules(trainingSet[linkRef.tid],linkRef.tid,linkRef.classLabel,linkRef.cRule,linkRef.wRule);
            }
            linkRef = linkRef.next;
        }
    }
	
    /* ALL COVER RULES */
    /** Finds all rules with higher precedence than the given cRule that
    wrongly classify the record, commencing with the given wRule, before
    the c rule is reached. <P>Note that we only have to consider rules marked
    as cRules (with respect to some other record(s)), other rules can safely 
    be removed from the data set. Proceeds as follows: For each rule between
    the wRule and the cRule that are CRules with respect to some other 
    record:</P><OL>
    <LI>Add the cRule to the rules overrides list.
    <LI>Increment the classCasesCovered for the rule. 
    </OL>
    @param record the current record in the training sert.
    @param tid the TID number for the current record in the training set.
    @param classLabel the label for the record.
    @param the given cRule up to which the rule list will be processed.
    @param the given cWule from where the rule list will be processed.   */

    private void allCoverRules(short[] record, int tid, short classLabel, AprioriTFP_CBA.RuleNodeCBA cRule, AprioriTFP_CBA.RuleNodeCBA wRule) {
        // Determine class index
        int classIndex = classLabel-numOneItemSets+numClasses-1;
		    	
        // Process wRule list
        AprioriTFP_CBA.RuleNodeCBA linkRef = wRule.next;
		//System.out.println("***IN*** allCoverRules");
		//System.out.print("wRule = ");
		//outputRule(wRule);
		//System.out.print("wRule.next = linkRef = ");
		//outputRule(wRule.next);
		//System.out.print("cRule = ");
		//outputRule(cRule);
		//System.out.println();
       while (linkRef != null) {
            // If reached the given cRule jump out of the loop
            if (linkRef == cRule)
            	break;
            // Check if current rule is also a cRule (i.e. satisfies at least
            // one record in the training set), otherwise ignore
            if (linkRef.isAcRule) {
            	// Check if identified cRule satisfies current record and if so
            	// add to overides linked list.
                if (isSubset(linkRef.antecedent,record)) {
				    // Add to replace list for rule identified by linkRef
				    AprioriTFP_CBA.Overrides newOverrides = new AprioriTFP_CBA.Overrides(cRule,tid,classLabel);
					//if (newOverrides != null) System.out.println("****************************" +
					//"********* OVERIDES *********" +
					//"****************************");
					//System.out.print("Intervening rule = ");
					//outputRule(linkRef);
					//System.out.print("\nOveriders struct = cRule = ");
					//outputRule(cRule);
					//System.out.println(", tid = " + tid + ", classLabel = " + classLabel +
					//", classIndex = " + classIndex);
				    newOverrides.next   = linkRef.replaceList;
				    linkRef.replaceList = newOverrides;
				    // Increment counter
				    linkRef.classCasesCovered[classIndex]++;
				    // Set to true
				    linkRef.isAstrongCrule = true;
                }
            }
            linkRef = linkRef.next;
        }
    }
	
    /* --------------------------------------------------------------- */
    /*    STAGE 3: PROCESS RULE LIST   */
    /* --------------------------------------------------------------- */

    /* PROCESS RULE LIST */
    /** Creates the final list of CBA rules by processesing the rule list to 
    identify, for each "strong"  cRule the default class and the total error 
    count. <P> Commences with the generation of a global class distribution 
    array which contains the number of training cases for each class. Next the 
    "overriden" rule list for each "strong" cRule is considered, if such a 
    list exists, it is processed. Next the default class is identified and the 
    total error count calculated.
    @param trainingSet the array of arrays of training set records. */

    private void processRuleList(short[][] trainingSet) {
        // Local fields
        int ruleErrors=0;
        // Class distribution array
        int[] classDistrib = genClassclassCasesCovered(trainingSet);
        //System.out.println("processRuleList");
        //for (int index=0;index<classDistrib.length;index++) 
        //System.out.println("Records for class " + index + " = " + classDistrib[index]);

        // Process rule list
        AprioriTFP_CBA.RuleNodeCBA linkRef = startCBAruleList;
        while(linkRef!=null) {
		    // Consider "strong" cRules only
		    if (linkRef.isAstrongCrule) {
		        // Get index in class records array for consequent of rule
		    	int classIndex = linkRef.consequent[0]-numOneItemSets+numClasses-1;
		    	// Check that rule correctly satisfies at least one record 
		    	// (otherwise ignore).
		    	if (linkRef.classCasesCovered[classIndex] != 0) {
		    		//outputRule(linkRef);
		    		// Process overrides linked list for current "strong" cRule
		    		AprioriTFP_CBA.Overrides orLinkRef = linkRef.replaceList;
		    		while (orLinkRef != null) {
		    			int cIndex = orLinkRef.classLabel-numOneItemSets+numClasses-1;
		    			//System.out.println("Process overrides linked list, tid = " + orLinkRef.tid);
		    			// The TID referenced by the current overriding rule
		    			// Is already satisfied by a previous rule decrement 
		    			// Current rule distribution array, otherwise
		    			// decrement overriding rule's distribution array
		    			if (coveredByPreviousRule(orLinkRef.tid,linkRef,trainingSet)) {
		    				//System.out.println("Is covered by previous rule");	 				
		    				linkRef.classCasesCovered[cIndex]--;
		    			} else { 
		    				orLinkRef.cRule.classCasesCovered[cIndex]--;
		    				//System.out.println("Is NOT covered by previous rule");
		    			}
		    			orLinkRef = orLinkRef.next;
		    		}
		    		// Determine number of accumulated miss-classifications 
		    		// that will result if the given rule were the last rule in 
		    		// the classifier (i.e. the default rule)
		    		for (int index=0;index<linkRef.classCasesCovered.length;index++)
			    	if (classIndex!=index) 
			    		ruleErrors=ruleErrors+linkRef.classCasesCovered[index];
		    		// Adjust global distribution array
		    		adjustclassCasesCovered(linkRef.classCasesCovered,classDistrib);
		    		// Select default class and return default error
		    		int defaultErrors = selectDefaultRule(linkRef,classDistrib);
		    		// Total errors
		            linkRef.totalErrors = ruleErrors+defaultErrors;
		            //outputRule(linkRef);
		            //for (int index=0;index<classDistrib.length;index++) 
		            //System.out.println("Records for class " + index + " = " + classDistrib[index]);
			    } else // Redefine class as not a strong class
			    	linkRef.isAstrongCrule=false;
			}
		    linkRef = linkRef.next;
	    }
	}

    /* GENERATE CLASS DISTRIBUTION ARRAY */
    /** Generates a Class distribution array --- i.e. an array indicating
    the number of records in the training set that correspond to individual
    classess. 	
    @param trainingSet the array of arrays of training set records. */
    private int[] genClassclassCasesCovered(short[][] trainingSet) {
		//System.out.println("In genClassclassCasesCovered: # trainingSet records = " +
		//trainingSet.length + ", numClasses = " + numClasses + ", numOneItemSets = " +
		//numOneItemSets);

        // Create and initailise distribution array
        int [] classDistrib = new int [numClasses];
        for (int index=0;index<classDistrib.length;index++)
        	classDistrib[index]=0;

        // Process training set
        for (int index=0;index<trainingSet.length;index++) {
            int lastIndex = trainingSet[index].length-1;
            int classIndex = trainingSet[index][lastIndex]-numOneItemSets+numClasses-1;
			//System.out.print("trainingSet = ");
			//outputItemSet(trainingSet[index]);
			//System.out.println("\nlastIndex = " + lastIndex + ", classIndex = " + 
			//classIndex);
            classDistrib[classIndex]++;
        }
		//for (int index=0;index<classDistrib.length;index++)
		//System.out.print("[" + index + "] " + index + ", ");
		//System.out.println();

        // Returm
        return(classDistrib);
    }

    /* ADJUST DISTRIBUTION ARRAY */
    /** Adjusts class distribution array to take into consideration latest rule.
    @param ruleDistrib the number records of each class satisfied by the rule.
    @param classDistribution the current distribution of classes, amoungst the
    remaining records in the training set, to be adjusted according to the
    rule distribution. */	
    private void adjustclassCasesCovered(int [] ruleDistrib, int [] classDistrib) {
        for (int index=0;index<ruleDistrib.length;index++) {
            classDistrib[index]=classDistrib[index]-ruleDistrib[index];
            }
    }

    /* SELECT A DEFAULT RULE */
    /** Selects a default rule according to the current class distribution
    within the remaining records in the training set and determines and returns
    the number of errors that would result.
    @param cRule the current "string" cRule.
    @param classDistribution the current distribution of classes, amoungst the
    remaining records in the training set.
    @return the number of errors that would result from application of the
    suggested default rule at this stage. */	
    private int selectDefaultRule(AprioriTFP_CBA.RuleNodeCBA cRule, int [] classDistrib) {
        int defaultIndex=0;

        // Loop through class disatribution array
        for (int index=1;index<classDistrib.length;index++)
            if (classDistrib[index]>classDistrib[defaultIndex])
    			defaultIndex=index;

        // Add to defualt field for cRule
        cRule.defaultClass = (short) (defaultIndex+numOneItemSets-numClasses+1);

        // Calculate errors
        int errors=0;
        for (int index=0;index<classDistrib.length;index++)
            if (index!=defaultIndex)
            	errors=errors+classDistrib[index];

        // Return
        return(errors);
    }

    /** Determines wherther the given record in the training set has been
    covered (i.e. wrongly classified) by a strong c rule with higher precedence. 
    @param tid the record number in the training set for the given record.
    @param currentRuleRef the reference to the current cRule.
    @param trainingSet the array of arrays of training set records.    
    @return true if record is covered by a previous rule and false otherwise. */
    private boolean coveredByPreviousRule(int tid, AprioriTFP_CBA.RuleNodeCBA currentRuleRef,short[][] trainingSet) {
        boolean isCoveredByPreviousRule = true;
		// Process CBA rule list until current cRule is reached.
		AprioriTFP_CBA.RuleNodeCBA linkRef = startCBAruleList;
		while (linkRef!=null) {
		    // Reached current rule
		    if (linkRef == currentRuleRef)
		    	break;
			// Check if rule is a strong c rule and if so if it satsfies record
			if (linkRef.isAstrongCrule && (isSubset(linkRef.antecedent,trainingSet[tid]))) {
			   	isCoveredByPreviousRule = false;
			   	break;
			}
			linkRef = linkRef.next;
		}
		// Return
		return(isCoveredByPreviousRule);    
	}
	
    /* ------------------------------------------------ */
    /*           STAGE 4: GENERATE CLASSIFIER           */
    /* ------------------------------------------------ */

    /*  GENERATE CLASSIFIER */
    
    /** Generates classifier by finding first strong c rule (that satisfies at
    least one record) with the lowest total error in the CBA rule list. <P> The 
    final classifier comprises all the rules upto and including the identified
    rule pluss a default rule that produces the default class associated with
    the identified rukle. */
        
    private void generateClassifier() {
        AprioriTFP_CBA.RuleNodeCBA linkRef = startCBAruleList;
        RuleNode markerRef  = null;
        //System.out.println("Generate classifier");	
        // Find lowest total error
        int error = findLowestTotalError();
        //System.out.println("Lowest total error = " + error);
        // Process rule list
        while(linkRef!=null) {
        	//System.out.println("\t");outputRule(linkRef);
        	if (linkRef.isAstrongCrule) {
        		int classIndex = linkRef.consequent[0]-numOneItemSets+numClasses-1;
        		if (linkRef.classCasesCovered[classIndex] != 0) {		
        			RuleNode newNode = new RuleNode(linkRef.antecedent,linkRef.consequent,linkRef.confidenceForRule);
        			if (startRulelist==null){
        				startRulelist=newNode;}
        			else
        				markerRef.next=newNode;
        			markerRef=newNode;
	            }
        	}
        	if (linkRef.totalErrors==error) 
        		break;
        	linkRef = linkRef.next;
	    }
	
        //System.out.println("\nAdd default rule");
        // Add default rule
        short[] consequent = new short[1];
        consequent[0] = linkRef.defaultClass;
        RuleNode newNode = new RuleNode(null,consequent,0);
        if (startRulelist==null)
        	startRulelist=newNode;
        else
        	markerRef.next=newNode;
	}
	
    /* FIND LOWEST TOTAL ERROR */
    /** Finds the first strong c rule (that satisfies at least one record) in
    the CBA rule list with the lowest total error. 
    @return the minimum total error. */
    private int findLowestTotalError() {
        int error=numRows;	// Maximum error, all records miss classified

        // Process rule list
        AprioriTFP_CBA.RuleNodeCBA linkRef = startCBAruleList;
        while(linkRef!=null) {
            if (linkRef.isAstrongCrule) { 
		        int classIndex = linkRef.consequent[0]-numOneItemSets+numClasses-1;
		    	if (linkRef.classCasesCovered[classIndex] != 0) {
	               if (linkRef.totalErrors<error) 
	            	   error=linkRef.totalErrors;
			    }
            }
            linkRef = linkRef.next;
	    }	
		// Return
		return(error);
	}  
	
    /* ---------------------------------------------------------------- */
    /*                                                                  */
    /*                        TEST CLASSIFICATION                       */
    /*                                                                  */
    /* ---------------------------------------------------------------- */

    /* TEST CLASSIFICATION */
    /** Tests the generated classification rules using test sets and return
    percentage accuracy.
    @param the perecentage accuarcy. */
    protected double[] testClassification() {	
		int correctClassCounter[];
		int wrongClassCounter[];
		int countInstancesClass[];
		int unclassifiedCounter = 0;
		int totalCorrectClassCounter = 0;
	
		wrongClassCounter = new int[numClasses];
		countInstancesClass = new int[numClasses];
		correctClassCounter = new int[numClasses];
		
		// Check if test data exists, if not return' 0'
		if (testDataArray==null) {
		    System.out.println("ERROR: No test data");
		    return(null);
	    }
		
		// Check if any classification rules have been generated, if not
		// return'0'.
		if (startRulelist==null) {
		    System.out.println("No classification rules generated!");
		    return(null);
	    }

		// Loop through test set
		int index=0;
		
	    for(;index<testDataArray.length;index++) {
	    	// Note: classifyRecord methods are contained in the
	    	// AssocRuleMining class. To calssify without default use
	    	short classActual = getLastElement(testDataArray[index]);
			countInstancesClass[numCols-classActual]++;
	    	
			// classifyRecord, with defualt using classifyRecordDefault.
			short classResult = classifyRecordDefault(testDataArray[index]);		
		    if (classResult == classActual)
		    	totalCorrectClassCounter++;
			
			// classifyRecord, without defualt using classifyRecord.
			classResult = classifyRecord(testDataArray[index]);		
	    	if (classResult==0)
		    	unclassifiedCounter++;
		    else {
		    	if (classResult == classActual)
		    		correctClassCounter[numCols-classResult]++;
		    	else if (classResult != classActual && classResult!=0)
			    	wrongClassCounter[numCols-classResult]++;
			}
		}
	    //System.out.println(unclassifiedCounter+"***C0W="+wrongClassCounter[0]+"------C1W="+wrongClassCounter[1]+"-----C0="+countInstancesClass[0]+"-----C1="+countInstancesClass[1]);

	    //calculates false positives rate
	    double fp_rate=0;
	    double negatives;
	    for(int i=0; i<numClasses; i++){
	    	negatives = testDataArray.length-countInstancesClass[i];
	    	fp_rate += wrongClassCounter[i]/negatives;
	    	//System.out.println("++++++"+wrongClassCounter[i]/negatives+"  NNNNNNNN  "+negatives+"  ------  "+countInstancesClass[i]);
	    }
	    fp_rate = fp_rate/numClasses;
	    fp_rate = fp_rate*100;

	    //calculates true positives rate
	    double tp_rate=0.0;
	    double positives;
	    for(int i=0; i<numClasses; i++){
	    	positives = countInstancesClass[i];
	    	tp_rate += correctClassCounter[i]/positives;
	    }
	    tp_rate = tp_rate/numClasses;
	    tp_rate = tp_rate*100;
	    
	    
	    //Calculate and return classification accuracy
		double accuracy = ((double) totalCorrectClassCounter*100.0/(double) (countInstancesClass[0] + countInstancesClass[1]));
		//System.out.println("correctClassCounter = " + correctClassCounter);
		//System.out.println("unclassifiedCounter = " + unclassifiedCounter);
		//System.out.println("wrongClassCounter   = " + wrongClassCounter);
		//System.out.println("Num case            = " + index);
		//System.out.println("accuracy            = " + accuracy);
		// Return
		double returnVals[];
		returnVals = new double[3];
		returnVals[0] = accuracy;
		returnVals[1] = fp_rate;
		returnVals[2] = tp_rate;
		return(returnVals);				
	}

    /* ----------------------------------- */
    /*                                     */
    /*              GET METHODS            */
    /*                                     */
    /* ----------------------------------- */

    /* GET NUMBER OF CBA CLASSIFICATION RULES */
    /**  Returns the number of generated CBA classification rules.
    @return the number of CRs. */
    public int getNumCBA_CRs() {
        int number = 0;
        AprioriTFP_CBA.RuleNodeCBA linkRuleNode = startCBAruleList;
	
		// Loop through linked list
		while (linkRuleNode != null) {
		    number++;
		    linkRuleNode = linkRuleNode.next;
		}
		// Return
		return(number);
	}
	
    /* ------------------------------ */
    /*                                */
    /*              OUTPUT            */
    /*                                */
    /* ------------------------------ */

    /* OUTPUT RULE LINKED LIST */
	
    /* OUTPUT CBA RULE LINKED LIST */
    /** Outputs contents of CBA rule linked list (if any) */

    public void outputCBArules() {
        outputRules(startCBAruleList);
	}
	
    /** Outputs given CBA rule list.
    @param ruleList the given rule list. */
    public void outputRules(AprioriTFP_CBA.RuleNodeCBA ruleList) {
		// Check for empty rule list
		if (ruleList==null) {
		    System.out.println("No rules generated!");
		    return;
		}
		
		// Loop through rule list
		int number = 1;
		while (ruleList != null) {
		    System.out.print("(" + number + ") ");
		    outputRule(ruleList);
		    number++;
		    ruleList = ruleList.next;
		}
	} 
	
    /** Outputs a CBA rule.
    @param rule the rule to be output. */
    private void outputRule(AprioriTFP_CBA.RuleNodeCBA rule) {
        if (rule==null)
        	System.out.println("Null");
		else {
	        outputItemSet(rule.antecedent);
		    System.out.print(" -> ");
	        outputItemSet(rule.consequent);
	        System.out.print(" (" + twoDecPlaces(rule.confidenceForRule) + "%, " + rule.supportForRule + ")");
		    // Output if cRule
		    if (rule.isAcRule) {
		        if (rule.isAstrongCrule)
		        	System.out.print(" * STRONG cRule *");
		        else
		        	System.out.print(" * cRule *");
		    }
		    // Output replaceList
		    outputOverridesLinkedList(rule.replaceList);
		    // Output class cases covered array
		    System.out.print(" [" + rule.classCasesCovered[0]);
		    for(int index=1;index<rule.classCasesCovered.length;index++)
		        System.out.print("," + rule.classCasesCovered[index]);
		    System.out.print("]");
		    // Output default class and total errors unless default is 0
		    if (rule.defaultClass!=0)
		    	System.out.println(", D-Class = " + rule.defaultClass + ", T-errors = " + rule.totalErrors);
		    else
		    	System.out.println();
		}
	}

    /* OUTPUT OVERRIDES LINKED LIST */
    
    /** Outputs the given overrides linked list associated with a particular CBA rule. 
    @param listRef the reference to the overrides linked list. */
    private void outputOverridesLinkedList(AprioriTFP_CBA.Overrides listRef) {
        AprioriTFP_CBA.Overrides linkRef = listRef;
	
		// Check for empty list
		if (linkRef== null) {
		    System.out.println(" Overrides linked list: null");
		    return;
	    } else
	    	System.out.println(" Overrides linked list:");  
		  
		// Loop through list
		while (linkRef != null) {
		    System.out.print("\t");
		    outputItemSet(linkRef.cRule.antecedent);
		    System.out.print(" -> ");
	        outputItemSet(linkRef.cRule.consequent);
		    System.out.println(", TID = " + linkRef.tid + ", classLabel = " + linkRef.classLabel);
		    linkRef = linkRef.next;
		}
	}
	
    /* OUTPUT NUMBER OF CBA RULES */
    /** Outputs number of generated rules (ARs or CARS). */
    public void outputNumCBArules() {
        System.out.println("\nNumber of CBA rules     = " + getNumCBA_CRs());
	}

    /* MENU OF OUTPUT OPTIONS*/
    public void _menuOutputOptions() throws IOException{
        int opt=bd.InputData.getOptMenuCBAFuzzy();

			if(opt!=6) { //TEST
				System.out.println("\n---------- OPTIONS MENU -----------");
				System.out.println("   1: Test with 50:50");
				System.out.println("   2: Test with TCV");
				System.out.println("   3: Generate Classification Rules (90% training set)");
				System.out.println("   4: Default (50:50 + CRules)");
				System.out.println("   5: Exit ");
				System.out.print("Type your option: ");

				while(opt==0){
				  try {opt=System.in.read();} catch (IOException ioException ) {}
				  if(opt<49 || opt>53){
					  System.out.print("Invalid option! Please type your option again: ");
					  // reset input
					  try {opt=System.in.read();} catch (IOException ioException ) {}
					  try {opt=System.in.read();} catch (IOException ioException ) {}
					  opt=0;
				  }
                                  else {
					  // Start timer
					  double time1 = (double) System.currentTimeMillis();
					  switch (opt) {
					  	case 49:
					  		createTrainingAndTestDataSets(false);
					    	// Mine data, produce T-tree and generate CRs
					  		startCBAclassification(0.5);
					  		outputNumCBArules();
							outputRulesWithDefault();
					  		break;
					  	case 50:
							// Create tenths data sets (method in ClassificationAprioriT class)
					  		createTenthsDataSets();
							// Mine data, produce T-tree and generate CRs
							commenceTCVwithOutput();
					  		break;
					  	case 51:
					  		createTrainingAndTestDataSets(true);
					  		startCBAclassification2(0.9);
							outputRulesWithDefault();
					  		break;
					  	case 52:
					        createTrainingAndTestDataSets(false);
					    	// Mine data, produce T-tree and generate CRs
					    	startCBAclassification(0.5);
					  		outputNumRules();
					  		outputRulesWithDefault();
					  		break;
					  	case 53:
					  		System.exit(0);
					  		break; 
					  }
					  outputDuration(time1, (double) System.currentTimeMillis());
				  }
				}
			}
                        else {
                            //RUN WITHOUT OUTPUT
                            createTrainingAndTestDataSets(true);
                            // Mine data, produce T-tree and generate CRs
                            startCBAclassification(0.9);

//                            outputNumRules();
//                            outputRulesWithDefault();
                            bd.UpdateData.updateRules(getRuleList(), getNumCRs()-1, itemsTranslatorList);
                       }
		}

}
