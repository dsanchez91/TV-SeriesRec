package discretfuzzy;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Fuzzyfier {	
	String fuzzyfiedData[][];

	public Fuzzyfier(String inputDataArray[][], LinkedList variablesToDiscretize, float intervals[][], BigDecimal bigdecIntervals[][], int typeDisc){
		System.out.println("Fuzzyfication process started...");
		if(variablesToDiscretize!=null){
			if(typeDisc==0) //Equal-Width
				fuzzyfiedData = fuzzyfieEW(inputDataArray, variablesToDiscretize, intervals, bigdecIntervals);
			else if(typeDisc==1) //Equal-Depth
				fuzzyfiedData = fuzzyfieED(inputDataArray, variablesToDiscretize, intervals, bigdecIntervals);
			System.out.println("Fuzzyfication process: OK!");
		} else {
			fuzzyfiedData = inputDataArray;
			System.out.println("No attributes to fuzzyfie!");
		}
	}

	public String[][] getFuzzyfiedDataToPrint(){
		return fuzzyfiedData;
	}

	public float[] getIntervalLowestLength(float intervals[][]){
		float lowest[];
		
		lowest = new float[intervals.length];
		for(int i=0; i<intervals.length; i++){
			lowest[i] = intervals[i][1] - intervals[i][0];
			for(int j=1; j<intervals[i].length; j++){
				if(lowest[i] > (intervals[i][j] - intervals[i][j-1]))
					lowest[i] = intervals[i][j] - intervals[i][j-1];
			}
		}
		
		return lowest;
	}
	
	/**
	 * for Equal-Width discretization
	 */
	public float[] getIntervalsLength(float intervals[][]){
		float returnValues[];
		
		returnValues = new float[intervals.length];
		for(int i=0; i<intervals.length; i++)
			returnValues[i] = intervals[i][1] - intervals[i][0];
		
		return returnValues;
	}

	public BigDecimal getMembershipTrap(float x, float term1, float term2, float factorLowest){
		BigDecimal bigDec;
		float a, b, c, d, memb=0;
		
		a = term1 - factorLowest;
		b = term1 + factorLowest;
		c = term2 - factorLowest;
		d = c + factorLowest*2;
		
		if(b==c){ //triangle
			if(x<=a)
				memb = 0;
			else if(x>a && x<=b)
				memb = (x-a)/(b-a);
			else if(x>b && x<d)
				memb = (d-x)/(d-b);
			else if(x>=d)
				memb = 0;

		} else {		
			if(x<=a)
				memb = 0;
			else if(x>a && x<=b)
				memb = (x-a)/(b-a);
			else if(x>b && x<=c)
				memb = 1;
			else if(x>c && x<=d)
				memb = (d-x)/(d-c);
			else if(x>d)
				memb = 0;
		}
		
		bigDec = new BigDecimal(memb);
		return bigDec.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getMembershipTriang(float x, float a, float b, float c){
		BigDecimal bigDec;
		float memb=0;
		
		if(x<=a)
			memb = 0;
		else if(x>a && x<=b)
			memb = (x-a)/(b-a);
		else if(x>b && x<c)
			memb = (c-x)/(c-b);
		else if(x>=c)
			memb = 0;
		
		bigDec = new BigDecimal(memb);
		return bigDec.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getMembershipTriangFirst(float x, float b, float c){
		BigDecimal bigDec;
		float memb=0;
		
		if(x<=b)
			memb = 1;
		else if(x>b && x<c)
			memb = (c-x)/(c-b);
		else if(x>=c)
			memb = 0;
		
		bigDec = new BigDecimal(memb);
		return bigDec.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getMembershipTriangLast(float x, float a, float b){
		BigDecimal bigDec;
		float memb=0;
		
		if(x<=a)
			memb = 0;
		else if(x>a && x<b)
			memb = (x-a)/(b-a);
		else if(x>=b)
			memb = 1;
		
		bigDec = new BigDecimal(memb);
		return bigDec.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public float[] getABC(float localIntervals[], float intervalLength){
		float intervalInit, ABC[];
		
		
		intervalInit = localIntervals[0];
		
		ABC = new float[3];
		ABC[0] = intervalInit;
		ABC[1] = intervalInit + intervalLength;
		ABC[2] = intervalInit + intervalLength*2;
		
		return ABC;
	}

	public int getInitIntervalInd(float x, float localIntervals[]){
		int returnValue=0;
		
		for(int i=1; i<localIntervals.length; i++)
			if((x >= localIntervals[i-1]) && (x <= localIntervals[i])){
				returnValue = i-1;
				break;
			}
		
		return returnValue;
	}

	public String getStrReturn(BigDecimal firstValue, BigDecimal secondValue, BigDecimal thirdValue, BigDecimal firstMembership, BigDecimal secondMembership) {
		String strReturn=null;
		
		if(firstMembership.floatValue()!=0 && secondMembership.floatValue()!=0)
			strReturn = "["+firstValue.toString()+"-"+secondValue.toString()+"[:"+firstMembership.toString()+", ["+secondValue.toString()+"-"+thirdValue.toString()+"[:"+secondMembership.toString();
		else if(secondMembership.floatValue()==0)
			strReturn = "["+firstValue.toString()+"-"+secondValue.toString()+"[:"+firstMembership.toString();
		else if(firstMembership.floatValue()==0)
			strReturn = "["+secondValue.toString()+"-"+thirdValue.toString()+"[:"+secondMembership.toString();

		return strReturn;
	}

	public String[][] fuzzyfieEW(String inputData[][], LinkedList variablesToDiscretize, float intervals[][], BigDecimal bigdecIntervals[][]){
		int k, ind;
		float x, initInterval, factor, intervalsLength[];
		BigDecimal  memb1, memb2;
		String strReturn[][];
		
		strReturn = inputData;
		intervalsLength = getIntervalsLength(intervals);
		for(int i=0; i<variablesToDiscretize.size(); i++){
			k = Integer.parseInt(variablesToDiscretize.get(i).toString());
			factor = intervalsLength[i]/2;
			for(int j=0; j<inputData.length; j++){
				x = Float.parseFloat(inputData[j][k].toString());
				ind = getInitIntervalInd(x, intervals[i]);
				initInterval = intervals[i][ind];
				if(ind==0) { //fisrt interval
					memb1 = getMembershipTriangFirst(x, initInterval+factor, initInterval+factor+intervalsLength[i]);
					memb2 = getMembershipTriang(x, initInterval+factor, initInterval+intervalsLength[i]+factor, initInterval+factor+(intervalsLength[i]*2));
					strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], bigdecIntervals[i][ind+2], memb1, memb2);
				
				} else if(ind == 1) { //second interval
					if(x < initInterval + factor) {				
						memb1 = getMembershipTriangFirst(x, initInterval-factor, initInterval+factor);
						memb2 = getMembershipTriang(x, initInterval-factor, initInterval+factor, initInterval+intervalsLength[i]+factor);
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind-1], bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], memb1, memb2);
					} else if(x > initInterval + factor){
						memb1 = getMembershipTriang(x, initInterval-factor, initInterval+factor, initInterval+intervalsLength[i]+factor);
						memb2 = getMembershipTriang(x, initInterval+factor, initInterval+intervalsLength[i]+factor, initInterval+(intervalsLength[i]*2));
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], bigdecIntervals[i][ind+2], memb1, memb2);
					} else if(x == initInterval + factor)
						strReturn[j][k] = "["+bigdecIntervals[i][ind].toString()+"-"+bigdecIntervals[i][ind+1].toString()+"[: 1.00";
				
				} else if(ind == intervals[i].length-3) { //before last interval
					if(x < initInterval + factor) {
						memb1 = getMembershipTriang(x, initInterval-intervalsLength[i]-factor, initInterval-factor, initInterval+factor);
						memb2 = getMembershipTriang(x, initInterval-factor, initInterval+factor, initInterval+intervalsLength[i]+factor);
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind-1], bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], memb1, memb2);
					} else if(x > initInterval + factor){
						memb1 = getMembershipTriang(x, initInterval-factor, initInterval+factor, initInterval+intervalsLength[i]+factor);
						memb2 = getMembershipTriangLast(x, initInterval+factor, initInterval+factor+intervalsLength[i]);
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], bigdecIntervals[i][ind+2], memb1, memb2);
					} else if(x == initInterval + factor)
						strReturn[j][k] = "["+bigdecIntervals[i][ind].toString()+"-"+bigdecIntervals[i][ind+1].toString()+"[: 1.00";
			
				} else if(ind == intervals[i].length-2) { //last interval
					memb1 = getMembershipTriang(x, initInterval-factor-intervalsLength[i], initInterval-factor, initInterval+factor);
					memb2 = getMembershipTriangLast(x, initInterval-factor, initInterval+factor);
					strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind-1], bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], memb1, memb2);

				} else { //rest of intervals
					if(x < initInterval + factor) {
						memb1 = getMembershipTriang(x, initInterval-intervalsLength[i]-factor, initInterval-factor, initInterval+factor);
						memb2 = getMembershipTriang(x, initInterval-factor, initInterval+factor, initInterval+intervalsLength[i]+factor);
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind-1], bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], memb1, memb2);
						
					} else if(x > initInterval + factor){
						memb1 = getMembershipTriang(x, initInterval-factor, initInterval+factor, initInterval+intervalsLength[i]+factor);
						memb2 = getMembershipTriang(x, initInterval+factor, initInterval+intervalsLength[i]+factor, initInterval+(intervalsLength[i]*2));
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], bigdecIntervals[i][ind+2], memb1, memb2);
					} else if(x == initInterval + factor)
						strReturn[j][k] = "["+bigdecIntervals[i][ind].toString()+"-"+bigdecIntervals[i][ind+1].toString()+"[: 1.00";	
				}
			}
		}

		return strReturn; 
	}

	public String[][] fuzzyfieED(String inputData[][], LinkedList variablesToDiscretize, float intervals[][], BigDecimal bigdecIntervals[][]){
		int k, ind;
		float x, initInterval, factorLowest, intervalBackward=0, intervalForward, intervalForward2=0, intervalLowestLength[];
		BigDecimal  memb1, memb2;
		String strReturn[][];
		
		strReturn = inputData;
		intervalLowestLength = getIntervalLowestLength(intervals);
		for(int i=0; i<variablesToDiscretize.size(); i++){
			k = Integer.parseInt(variablesToDiscretize.get(i).toString()); 
			factorLowest = intervalLowestLength[i]/2; 
			for(int j=0; j<inputData.length; j++){
				x = Float.parseFloat(inputData[j][k].toString());
				ind = getInitIntervalInd(x, intervals[i]);
				initInterval = intervals[i][ind];
				if(ind>0)
					intervalBackward = intervals[i][ind-1];
				intervalForward = intervals[i][ind+1];
				if(ind<intervals[i].length-2)
					intervalForward2 = intervals[i][ind+2];
				if(ind==0) { //first interval
					memb1 = getMembershipTriangFirst(x, intervalForward-factorLowest, intervalForward+factorLowest);
					memb2 = getMembershipTrap(x, intervalForward, intervalForward2, factorLowest);
					strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], bigdecIntervals[i][ind+2], memb1, memb2);
				
				} else if(ind == 1) { //second interval
					if(x < initInterval + factorLowest) {				
						memb1 = getMembershipTriangFirst(x, initInterval-factorLowest, initInterval+factorLowest);
						memb2 = getMembershipTrap(x, initInterval, intervalForward, factorLowest);
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind-1], bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], memb1, memb2);
					} else if(x > initInterval + factorLowest){
						memb1 = getMembershipTrap(x, initInterval, intervalForward, factorLowest);
						memb2 = getMembershipTrap(x, intervalForward, intervalForward2, factorLowest);
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], bigdecIntervals[i][ind+2], memb1, memb2);
					} else if(x == initInterval + factorLowest)						
						strReturn[j][k] = "["+bigdecIntervals[i][ind].toString()+"-"+bigdecIntervals[i][ind+1].toString()+"[: 1.00";
				
				} else if(ind == intervals[i].length-3) { //before last interval
					if(x < initInterval + factorLowest) {
						memb1 = getMembershipTrap(x, intervalBackward, initInterval, factorLowest);						
						memb2 = getMembershipTrap(x, initInterval, intervalForward, factorLowest);
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind-1], bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], memb1, memb2);
					} else if(x > initInterval + factorLowest){
						memb1 = getMembershipTrap(x, initInterval, intervalForward, factorLowest);
						memb2 = getMembershipTriangLast(x, intervalForward-factorLowest, intervalForward+factorLowest);
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], bigdecIntervals[i][ind+2], memb1, memb2);
					} else if(x == initInterval + factorLowest)
						strReturn[j][k] = "["+bigdecIntervals[i][ind].toString()+"-"+bigdecIntervals[i][ind+1].toString()+"[: 1.00";
			
				} else if(ind == intervals[i].length-2) { //last interval
					memb1 = getMembershipTrap(x, intervalBackward, initInterval, factorLowest);
					memb2 = getMembershipTriangLast(x, initInterval-factorLowest, initInterval+factorLowest);
					strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind-1], bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], memb1, memb2);

				} else { //rest of intervals
					if(x < initInterval + factorLowest) {
						memb1 = getMembershipTrap(x, intervalBackward, initInterval, factorLowest);						
						memb2 = getMembershipTrap(x, initInterval, intervalForward, factorLowest);
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind-1], bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], memb1, memb2);
					} else if(x > initInterval + factorLowest){
						memb1 = getMembershipTrap(x, initInterval, intervalForward, factorLowest);
						memb2 = getMembershipTrap(x, intervalForward, intervalForward2, factorLowest);						
						strReturn[j][k] = getStrReturn(bigdecIntervals[i][ind], bigdecIntervals[i][ind+1], bigdecIntervals[i][ind+2], memb1, memb2);
					} else if(x == initInterval + factorLowest)
						strReturn[j][k] = "["+bigdecIntervals[i][ind].toString()+"-"+bigdecIntervals[i][ind+1].toString()+"[: 1.00";	
				}
			}
		}	
		return strReturn; 
	}

}
