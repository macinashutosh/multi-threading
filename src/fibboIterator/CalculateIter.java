package fibboIterator;

import fibboIterator.GetPermutation;
import java.util.*;


public class CalculateIter {
	public static String firstFile = "First.txt";
	public static String secondFile = "Second.txt";
	public static ArrayList<Integer> firstBU = new ArrayList<Integer>();
	public static ArrayList<Integer> secondBU = new ArrayList<Integer>();
	public static ArrayList<Integer> spacing = new ArrayList<>();
	public static ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
	public static ArrayList<Thread> threads = new ArrayList<>();
	public static ArrayList<GetPermutation> permutationBlocks = new ArrayList<>();
	public static ArrayList<Thread> threadsAfterPermutation = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> previousList = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> currentList = new ArrayList<>();
	
    public static ArrayList<ArrayList<Integer>> assignBaseUnit(int[] input) {
    	ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
    	int N = input.length+1;
    	int BaseUnit = 5;
    	boolean check = true;
    	int iterator = 0;
		int counter = 0;
    	loop1: while(check) {
    		iterator = 0;
    		counter = 0;
    		while(true) {
    			if(BaseUnit - (2 * iterator) > 0) {
        			counter = counter + (BaseUnit- 2*iterator);
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
    	System.out.println(BaseUnit);
    	while(counter <= N) {
    		int k=0;
    		int temp = counter;
    		ArrayList<Integer> tempList = new ArrayList<>();
    		while(k<BaseUnit - 2*iterator) {
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
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int input[] = {8,10,12,14,16, 15, 9,11,13};
		inputList = assignBaseUnit(input);
	    for (int i=0;i<inputList.size();i++) {
	    	System.out.println(inputList.get(i));
	    }
		solvePermutations();
		arrangeTheArrays();
        System.out.println("Program end");
        
 	}
	
	public static void solvePermutations() {
		for(int i=0;i<inputList.size();i++) {
			ArrayList<Integer>list = inputList.get(i);
			int arr [] = new int [list.size()];
			for(int j=0;j<list.size();j++) {
				arr[j] = list.get(j);
			}
			GetPermutation  m = new GetPermutation(arr);
			Thread t = new Thread(m);
			permutationBlocks.add(m);
			threads.add(t);
		}
		int startBound = 0,endBound = 100,threadSize = threads.size();
//		System.out.println("Thread Size:"+threadSize);
		while(startBound < endBound && startBound < threadSize) {
			if(endBound > threadSize) {
	        	endBound = threadSize;
	        }
			for(int i=startBound;i<endBound;i++) {
				threads.get(i).start();
			}	
	        
	        try {
	        	for(int i=startBound;i<endBound;i++) {
	    			threads.get(i).join();
	    		}
	        	
	        }catch(Exception e) {
	        	System.out.print(e.getMessage());
	        }
	        startBound = endBound;
	        endBound += endBound;
		} 
        threads.removeAll(threads);
        System.out.println("Program permuted");
	}

	public static void arrangeTheArrays() {
		for(int i=permutationBlocks.size()-1;i>=0;i--) {
        	currentList.removeAll(currentList);
        	ArrayList<ArrangeArray> arrangedArrays = new ArrayList<>();
        	ArrayList<ArrayList<Integer>> list = permutationBlocks.get(i).result;
        	for(int j=0;j<list.size();j++) {
        		ArrangeArray m = new ArrangeArray(previousList,list.get(j));
        		Thread t = new Thread(m);
        		threadsAfterPermutation.add(t);
        		arrangedArrays.add(m);
        	}
        	int startBound = 0,endBound = 100,threadSize = threadsAfterPermutation.size();
    		while(startBound < endBound && startBound < threadSize) {
    			if(endBound > threadSize) {
    	        	endBound = threadSize;
    	        }
    			for(int j=startBound;j<endBound;j++) {
    				threadsAfterPermutation.get(j).start();
    			}	
    	        
    	        try {
    	        	for(int j=startBound;j<endBound;j++) {
    	        		threadsAfterPermutation.get(j).join();
    	    		}
    	        	
    	        }catch(Exception e) {
    	        	System.out.print(e.getMessage());
    	        }
    	        startBound = endBound;
    	        endBound += endBound;
    		}
        	int size = arrangedArrays.size();
            for(int k=0;k<size;k++) {
            	ArrayList<ArrayList<Integer>> results = arrangedArrays.get(k).results;
            	if (results!=null && results.size() !=0) {
            		currentList.addAll(results);
            	}
            }
            previousList.removeAll(previousList);
            previousList.addAll(currentList);
            threadsAfterPermutation.removeAll(threadsAfterPermutation);
        }
		
		GetFinalResult r = new GetFinalResult(currentList);
		Thread s = new Thread(r);
		s.start();
		try {
			s.join();
			System.out.println("FinalFWM " +r.finalFWM);
			System.out.println("Final Answer"+r.result.toString());
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}	
}



