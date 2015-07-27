package CBAFuzzy;

import java.util.LinkedList;

public class IO {
	
	public static int addedLines=0;
	public static LinkedList items;
	public static LinkedList supports;
	public static LinkedList dataArrayStr[];
	public static short dataArray[][];
	public static String strColumns[];
	
	/*
	 * 
	 */
	public static LinkedList getSupports() {
		return supports;
	}

	/*
	 * 
	 */
	public static int getAddedLines() {
		return addedLines;
	}
	
	/*
	 * 
	 */
	public static boolean isFuzzyfied(String item) {
		if(item.startsWith("[") && item.contains("-") && item.contains(":"))
			return true;
		else
			return false;
	}
	
	
	public static short[][] getData() {
		return dataArray;
	}
	
	/*
	 * 
	 */
	public static char[] getBinaryValue(int intVal, int countFuzzy){
		char tempChar[], outputChar[];
		int numOfChars, j;
		
		tempChar = Integer.toBinaryString(intVal).toCharArray();
		numOfChars = tempChar.length;
		outputChar = new char[countFuzzy];

		if(countFuzzy!=numOfChars){
			for(int i=0; i<countFuzzy-numOfChars; i++)
				outputChar[i] = '0';
			j=0;
			for(int i=countFuzzy-numOfChars; i<countFuzzy; i++){
				outputChar[i] = tempChar[j];
				j++;
			}
		} else
			outputChar = tempChar;
		
		return outputChar;
	}
	
	/*
	 * 
	 */
	public static LinkedList[] addLines(LinkedList dataSet[], int countFuzzy, String firstItem[], String secondItem[], float membFAtt1[], float membFAtt2[]){
		LinkedList strReturn[];
		char binStr[];
		int numOfFinalLines, numAtts, init, indFuz;
		float currentSupport;

		numAtts = firstItem.length;
		numOfFinalLines = (int)Math.pow(2,countFuzzy);
		addedLines = addedLines + (numOfFinalLines-1);
		strReturn = dataSet;
		init = strReturn[0].size();
		for(int i=init; i<init+numOfFinalLines; i++) {
			binStr = getBinaryValue(i-init, countFuzzy);
			indFuz = 0;
			currentSupport = 1;
			for(int j=0; j<numAtts; j++) {			
				if(secondItem[j]!=null){ //is fuzzy
					if(binStr[indFuz]=='0'){
						strReturn[j].add(firstItem[j]);
						currentSupport = currentSupport*membFAtt1[j];
					} else if(binStr[indFuz]=='1') {
						strReturn[j].add(secondItem[j]);
						currentSupport = currentSupport*membFAtt2[j];
					}
					indFuz++;
				} else //is not fuzzy
					strReturn[j].add(firstItem[j]);
			}
			supports.add(String.valueOf(currentSupport));
		}

		return strReturn;
	}
	
	/*
	 * 
	 */
	public static LinkedList[] addLineNonFuzzy(LinkedList dataSet[], String firstItem[]){
		LinkedList strReturn[];
		
		strReturn = dataSet;
		for(int j=0; j<firstItem.length; j++) {
			strReturn[j].add(firstItem[j]);
			supports.add("1.0");
		}
		
		return strReturn;
	}
	
	
	/*
	 * 
	 */
	public static String[] getColumns() {
		return strColumns;
	}
	
	/*
	 * first pass
	 */
	public static LinkedList[] getStrArray(String[][] trainingData, LinkedList attributes, LinkedList fuzzifiedAttributes) {
		boolean hasFuzzyfiedAtt=false;
	  	float membFAtt1[], membFAtt2[];
	  	int numCols, j, countFuzzy, indCutItemset, indCutItem;
	  	LinkedList strReturn[];
	  	String currentItem, firstItem[], secondItem[];

	  	if(fuzzifiedAttributes.size()>0)
	  		hasFuzzyfiedAtt = true;
	  	numCols = trainingData[0].length;
	  	//if(hasFuzzyfiedAtt){
		firstItem = new String[numCols];
		secondItem = new String[numCols];
		membFAtt1 = new float[numCols];
		membFAtt2 = new float[numCols];
		supports = new LinkedList();
	  	//}
		strColumns = new String[numCols];
		for(int i=0; i<attributes.size(); i++)
			strColumns[i] = attributes.get(i).toString(); 
		strColumns[numCols-1] =	"class";
		
	  	strReturn = new LinkedList[numCols];
	  	for(j=0; j<strReturn.length; j++)
	  		strReturn[j] = new LinkedList();
	  	
	  	for(int i=0; i<trainingData.length; i++){
	  		countFuzzy = 0;
	  		for(j=0; j<trainingData[0].length; j++) {			  	
		  		currentItem = trainingData[i][j];
				if(!hasFuzzyfiedAtt)
					firstItem[j] = currentItem;
				else if(isFuzzyfied(currentItem) && currentItem.contains(",")){  //fuzzy attribute with two items
					indCutItemset = currentItem.lastIndexOf(",");
					indCutItem = currentItem.indexOf(":");
					firstItem[j] = currentItem.substring(0, indCutItem);
					membFAtt1[j] = Float.parseFloat(currentItem.substring(indCutItem+1, indCutItemset));
					indCutItem = currentItem.lastIndexOf(":");
					secondItem[j] = currentItem.substring(indCutItemset+2,indCutItem);
					membFAtt2[j] = Float.parseFloat(currentItem.substring(indCutItem+1, currentItem.length()));
					countFuzzy++;
				} else if(isFuzzyfied(currentItem)){  //fuzzy attribute with just one item
					indCutItem = currentItem.lastIndexOf(":");
					firstItem[j] = currentItem.substring(0,indCutItem);
					secondItem[j] = null;
					membFAtt1[j] = 1;
					membFAtt2[j] = 0;
				} else {  //nominal attribute
					firstItem[j] = currentItem;
					secondItem[j] = null;
					membFAtt1[j] = 1;
					membFAtt2[j] = 0;
				}	  			
	  		}
		  	if(countFuzzy==0)
		  		strReturn = addLineNonFuzzy(strReturn, firstItem);
		  	else if(countFuzzy>0)
		  		strReturn = addLines(strReturn, countFuzzy, firstItem, secondItem, membFAtt1, membFAtt2);
	  	}
	  			
		return strReturn;
    }
	
	/*
	 * 
	 */
	
	
	/*
	 * second pass
	 */
	public static short[][] getShorts(String[][] trainingData, LinkedList attributes, LinkedList fuzzifiedAttributes) {
	  	short outputArray[][];
	  	int numCols, numRows;
	  	LinkedList dataArrayStr[];
	  	String currentItem, strColumns[];
	  	
	  	dataArrayStr = getStrArray(trainingData, attributes, fuzzifiedAttributes);
	  	
	  	strColumns = getColumns();
	  	numRows = dataArrayStr[0].size(); 
	  	numCols = strColumns.length;
	  	outputArray = new short[numRows][numCols];
	  	items = new LinkedList();
	  	items.add(null);
	  	for(int j=0; j<numCols; j++){
	  		for(int i=0; i<numRows; i++){
	  			currentItem = dataArrayStr[j].getFirst().toString();
	  			dataArrayStr[j].removeFirst();
				if(j!=numCols-1){ //if it is not the class
					currentItem = "{"+strColumns[j]+" = "+currentItem+"}";}
				if(!items.contains(currentItem))
					items.add(currentItem);
				if(items.size()>500){
					System.out.println("ERROR: Impossible to generate frequent itemset: too many distinct values. You might preprocess your dataset first.");
					System.exit(0);
				}
				outputArray[i][j] = (short)items.indexOf(currentItem);
	  		}
	  	}
	  	
		return outputArray;
    }

	/**
	 * to print with rules
	 * @return
	 */
	public static LinkedList getItems(){
		return items;
	}

}
