package fibboIterator;

import java.util.ArrayList;

public class CalculateTriangle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int input[] = {4,6,8,10,12,14,16,18,20,22, 9, 5,7,9,11,13,15,17,19,21, 8, 4,6,8,10,12,14,16, 7, 5,7,9,11, 6};
		//starting base unit is 5
		ArrayList<ArrayList<Integer>> inputList = assignTriangleBaseUnit(5,input);
	    for (int i=0;i<inputList.size();i++) {
	    	System.out.println(inputList.get(i));
	    }
		CalculateFinalAnswer.getSolution(inputList, input);
	}
	
	public static int ithTriangularTerm(int ele) {
		int result = 0;
		 	int i, j = 1, k = 1; 
	        // For each iteration increase j by 1 
	        // and add it into k 
	        for (i = 1; i <= ele; i++) { 
//	            System.out.printf("%d ", k); 
	            result = k;
	            j = j + 1; // Increasing j by 1 
	            k = k + j; // Add value of j into k and update k 
	        } 
	        return result;       
	}
	
	public static ArrayList<ArrayList<Integer>>  assignTriangleBaseUnit(int starting,int[] input) {
    	ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
    	int N = input.length+1;
    	int BaseUnit = starting;
    	boolean check = true;
    	int iterator = 0;
		int counter = 0;
    	loop1: while(check) {
    		iterator = 0;
    		counter = 0;
    		while(true) {
    			if(BaseUnit - ithTriangularTerm(iterator) > 0) {
        			counter = counter + (BaseUnit- ithTriangularTerm(iterator));
        			counter = counter + 1;
        			if(counter > N-1) {
        				check = false;
        				break;
        			}
        		}else {
        			if(counter > N-2) {
        				counter++;
        			}else {
        				BaseUnit += 1;
        				continue loop1;
        			}
        		}
        		iterator = iterator + 1;
        		if(counter > N -1) {
        			check = false;
        			break;
        		}
    		}
    		
    	}
    	counter = 1;
    	iterator = 0;
    	int previousBaseUnit = BaseUnit;
    	boolean checkBase = true;
    	System.out.println("Total Length : "+N+"\n");
    	while(counter <= N) {
    		int k=0;
    		int temp = counter;
    		ArrayList<Integer> tempList = new ArrayList<>();
    		while(k<BaseUnit - ithTriangularTerm(iterator)) {
    			k++;
    			temp++;
    			if(temp > N) {
    				checkBase = false;
    				break;
    			}else {
    				tempList.add(input[temp - 2]);
    			}
    		}
    		if(checkBase) {
				
				counter = counter + k;
				previousBaseUnit = previousBaseUnit-2;
				inputList.add(tempList);
				
			}
			if(counter >= N) {
				break;
			}
			ArrayList<Integer> tempFreq = new ArrayList<>();
			temp  = counter;
			if(checkBase == false) {
				
				tempFreq.add(input[temp-1]);
				inputList.add(tempFreq);
			}else {
				
				tempFreq.add(input[temp-1]);
				inputList.add(tempFreq);
			}
			
			counter++;
			if(counter > N-1) {
				break;
			}
			iterator ++;
    	}
    	
    	
    	return inputList;
    }

}
