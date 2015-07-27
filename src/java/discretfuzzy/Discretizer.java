package discretfuzzy;

import java.util.Arrays;
import java.util.LinkedList;

import bd.InputData;

public class Discretizer {
	LinkedList numericalVariables;
	String strIntervals[][];
	public float intervals[][];
	public LinkedList variablesToDiscretize;
	
	public Discretizer(String inputArray[][], LinkedList attributes, int typeDisc, int numIntervals){
	  	if(typeDisc==0)
	  		System.out.println("Equal-width discretization process started...");
	  	else if(typeDisc==1)
	  		System.out.println("Equal-depth discretization process started...");
		numericalVariables = InputData.getNumericalVariables(inputArray, attributes);
		if(numericalVariables.size()>0){
			intervals = getDiscretizedIntervals(inputArray, attributes, numericalVariables, typeDisc, numIntervals);
			System.out.println("Discretization process: OK!");
		} else
			System.out.println("No attributes to discretize!");
	}

	public float[][] getIntervals(){
		return intervals;
	}

	public float[][] getDiscretizedIntervals(String inputDataArray[][], LinkedList attributes, LinkedList numericalVariables, int typeDisc, int numIntervals){
		float returnValues[][];
		
		variablesToDiscretize = new LinkedList();
		
		for(int i=0; i<attributes.size(); i++){
			if(numericalVariables.contains(attributes.get(i).toString()))
				variablesToDiscretize.add(String.valueOf(i));
		}
		if(typeDisc==0)
			returnValues = getIntervalsEqualWidth(inputDataArray, variablesToDiscretize, numIntervals);
		else
			returnValues = getIntervalsEqualDepth(inputDataArray, variablesToDiscretize, numIntervals);

		return returnValues;
	}

	private float[] getInitializedMin(LinkedList variablesToDiscretize, Object[][] inputDataArray){
		float outputFloat[];
		int n, k;
		
		n=variablesToDiscretize.size();
		outputFloat = new float[n];
		for(int i=0; i<n; i++){
			k = Integer.parseInt(variablesToDiscretize.get(i).toString());
			outputFloat[i] = (float)Float.parseFloat(inputDataArray[0][k].toString());
		}
		
		return outputFloat;
	}
	
	private float[] getInitializedMax(int numVars){
		float outputFloat[];
		
		outputFloat = new float[numVars];
		for(int i=0; i<numVars; i++)
			outputFloat[i] = 0;
		
		return outputFloat;
	}

	private float[][] getLengthsEWidth(float[] min, float[] max, int numIntervals){
		float outputLengths[][];
		float currentLength;

		outputLengths = new float[min.length][numIntervals];
		for(int i=0; i<min.length; i++){
			currentLength = (max[i]-min[i])/(numIntervals-1);
			outputLengths[i][0] = min[i];
			for(int j=1; j<numIntervals-1; j++){
				outputLengths[i][j] = outputLengths[i][j-1] + currentLength;
			}
			outputLengths[i][numIntervals-1] = max[i];
		}
		
		return outputLengths;
	}

	private float[][] getLengthsEDepth(float[][] floatValues, int numIntervals){
		float outputLengths[][];
		int k, recordsInInterval, intervalSize;
		
		intervalSize = (int)floatValues[0].length/(numIntervals-1);
		outputLengths = new float[floatValues.length][numIntervals];
		for(int i=0; i<outputLengths.length; i++){
			outputLengths[i][0] = floatValues[i][0];
			k=0;
			recordsInInterval=1;
			for(int j=1; j<floatValues[i].length; j++){
				if(recordsInInterval==intervalSize){
					k++;
					recordsInInterval = 0;
					outputLengths[i][k] = floatValues[i][j-1];
					if(outputLengths[i][k] != outputLengths[i][k-1]){
						outputLengths[i][k] = floatValues[i][j-1];
					} else {
						while(outputLengths[i][k] == floatValues[i][j-1]){
							j++;
							recordsInInterval++;
						}
						outputLengths[i][k] = floatValues[i][j-1];
					}
					//System.out.println(recordsInInterval+"---"+outputLengths[i][k]);
				} else
					recordsInInterval++;
			}
			outputLengths[i][numIntervals-1] = floatValues[i][floatValues[i].length-1];
		}
		
		return outputLengths;
	}

	public float[][] getIntervalsEqualWidth(String[][] values, LinkedList variablesToDiscretize, int numIntervals){
		float min[], max[], floatValues[][];
		float intervals[][];
		int k; //currentIndice
		
		min = getInitializedMin(variablesToDiscretize, values);
		max = getInitializedMax(variablesToDiscretize.size());
		floatValues = new float[values.length][variablesToDiscretize.size()];	
		
		//get min and max values
		for(int i=0; i<variablesToDiscretize.size(); i++) {
			k = Integer.parseInt(variablesToDiscretize.get(i).toString());
			for(int j=0; j<values.length; j++){
				floatValues[j][i] = Float.parseFloat(values[j][k].toString());
				if(floatValues[j][i] < min[i])
					min[i] = floatValues[j][i];
				if(floatValues[j][i] > max[i])
					max[i] = floatValues[j][i];
			}
		}
		
		//get lengths
		intervals = getLengthsEWidth(min, max, numIntervals);	
		
		return intervals;
	}	

	public float[][] getIntervalsEqualDepth(String[][] values, LinkedList variablesToDiscretize, int numIntervals){
		int k;
		float floatValues[][], intervals[][];

		floatValues = new float[variablesToDiscretize.size()][values.length];	
		
		//sort values
		for(int i=0; i<variablesToDiscretize.size(); i++) {
			k = Integer.parseInt(variablesToDiscretize.get(i).toString());
			for(int j=0; j<values.length; j++){
				floatValues[i][j] = Float.parseFloat(values[j][k].toString());
                                System.out.println(values[j][k].toString());
                        }
			Arrays.sort(floatValues[i]);
		}
		
		//discretize
		intervals = getLengthsEDepth(floatValues, numIntervals);	
		return intervals;
	}
}
