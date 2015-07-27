package discretfuzzy;

import java.math.BigDecimal;
import java.util.LinkedList;

import bd.InputData;

public class DiscretFuzzy {
	
	public String fuzzyfiedData[][];
	
	String fileName, discretizedData[][];
	Discretizer discretizer;
	Fuzzyfier fuzzyfier;
	int numOfColumns=11;
	
	public DiscretFuzzy(String[][] inputDataArray, LinkedList attributes){
		BigDecimal bigdecIntervals[][];
	  	
	  	discretizer = new Discretizer(inputDataArray, attributes, InputData.getDiscType(), InputData.getNumIntervals()+1);
	  	bigdecIntervals = getBigdecIntervals(inputDataArray, discretizer.getIntervals(), getVariablesToDiscretize());
	  	
	  	fuzzyfier = new Fuzzyfier(inputDataArray, getVariablesToDiscretize(), discretizer.getIntervals(), bigdecIntervals, InputData.getDiscType());
	  	fuzzyfiedData = fuzzyfier.getFuzzyfiedDataToPrint();
	}
	
	public LinkedList getVariablesToDiscretize(){
		return discretizer.variablesToDiscretize;}

	public BigDecimal[][] getBigdecIntervals(String inputDataArray[][], float intervals[][], LinkedList variablesToDiscretize){
		if(variablesToDiscretize!=null)
			return getIntervalsPrintForm(intervals, inputDataArray, variablesToDiscretize);
		else
			return new BigDecimal[0][0];
	}

	public BigDecimal[][] getIntervalsPrintForm(float intervals[][], String inputValues[][], LinkedList variablesToDiscretize){
		BigDecimal bigDec;
		BigDecimal returnValues[][];
		int k, scale;
		
		returnValues = new BigDecimal[intervals.length][intervals[0].length];
		for(int i=0; i<intervals.length; i++){
			scale=0;
			for(int j=0; j<6; j++){
				k = Integer.parseInt(variablesToDiscretize.get(i).toString());
				bigDec = new BigDecimal(inputValues[i][k]);
				if(bigDec.scale()>scale)
					scale = bigDec.scale();
			}
			if(scale>3)
				scale=3;
			for(int j=0; j<intervals[i].length; j++) {
				bigDec = new BigDecimal(intervals[i][j]);
				returnValues[i][j] = bigDec.setScale(scale, BigDecimal.ROUND_HALF_UP);
			}
		}
		return returnValues;
	}

	public int getNumberOfColumns() {
		return numOfColumns;}
}
