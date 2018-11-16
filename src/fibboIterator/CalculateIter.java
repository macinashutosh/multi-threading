package fibboIterator;


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
	public static ArrayList<Thread> fwmResultThreads = new ArrayList<>();
	public static ArrayList<GetFinalResult> fwmResults = new ArrayList<>();
	public static ArrayList<GetMultipleFinalResult> fwmMultipleResults = new ArrayList<>();
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
    	System.out.println("Total Length : "+N+"\n");
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
		
		HashMap<Integer,Integer> map = new HashMap<>();
		for(int i=0;i<10;i++) {
			map.put(i, -1);
		}
		map.put(10, 15);
		map.put(11, 21);
		map.put(12, 35);
		map.put(13, 46);
		map.put(14, 48);
		map.put(15, 67);
		map.put(16, 96);
		map.put(17,106);
		map.put(18, 145);
		map.put(19, 167);
		map.put(20,218);
		map.put(21,283);
		map.put(22, 229);
		map.put(23,301);
		map.put(24,341);
		map.put(25,444);
		map.put(26,417);
		map.put(28,550);
		map.put(30, 742);
		int input[] = {4,6,8,10,12,14,16,18,20,22,24, 9, 5,7,9,11,13,15,17,19,21, 8, 4,6,8,10,12,14,16, 7, 5,7,9,11,13, 6, 4};
		
		ArrayList<Integer> arr = new ArrayList<>();
	    for(int i=0;i<input.length;i++) {
	    	arr.add(input[i]);
	    }
	    System.out.println(arr);
	    System.out.println("Initial FWM:"+FwmCalculator.getFinalFWMResult(arr));
		inputList = assignBaseUnit(input);
	    for (int i=0;i<inputList.size();i++) {
	    	System.out.println(inputList.get(i));
	    }
		solvePermutations();
//		System.out.println(permutationBlocks.size());
		arrangeTheArrays(map);  
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

	public static void arrangeTheArrays(HashMap<Integer,Integer> map) {
		

		previousList = permutationBlocks.get(permutationBlocks.size()-1).result;
		for(int i=permutationBlocks.size()-2;i>=0;i--) {
				if(currentList != null && currentList.size() >= 10 && currentList.get(0).size() >= 7)
				{	
					ArrayList<ArrayList<Integer>> results = permutationBlocks.get(i).result;
//					System.out.println(results.get(0).size() + 1);
//					System.out.println(map.get(results.get(0).size() + 1));
					GetMultipleFinalResult r = new GetMultipleFinalResult(results,map.get( results.get(0).size() + 1));
					Thread t = new Thread(r);
					fwmResultThreads.add(t);
					fwmMultipleResults.add(r);
				}else {
					ArrayList<ArrayList<Integer>> s = permutationBlocks.get(i).result;
//					System.out.println(currentList.size()+" : current");
					currentList = new ArrayList<>();
					for(int j=0;j<previousList.size();j++) {
						for(int k=0;k<s.size();k++) {
							ArrayList<Integer> temp = new ArrayList<>();
							temp.addAll(s.get(k));
							temp.addAll(previousList.get(j));
							currentList.add(temp);
						}
					}
//					System.out.println(currentList.get(0));
					previousList.removeAll(previousList);
					previousList.addAll(currentList);
					if(currentList != null && currentList.size() >= 10 && currentList.get(0).size() >= 7) {
						ArrayList<ArrayList<Integer>> results = new ArrayList<>();
						results.addAll(currentList);
						GetMultipleFinalResult r = new GetMultipleFinalResult(results,map.get( results.get(0).size() + 1));
						Thread t = new Thread(r);
						fwmResultThreads.add(t);
						fwmMultipleResults.add(r);
					}
				}
		}
		System.out.println("current List:"+currentList.size());
		System.out.println("fwmResult Thread Size : "+fwmResultThreads.size());
		for(int i=0;i<fwmResultThreads.size();i++) {
			fwmResultThreads.get(i).start();	
		}
		
		previousList = new ArrayList<>();
		currentList = new ArrayList<>();
		ArrayList<Thread> finalResultThreadList = new ArrayList<>();
		ArrayList<GetFinalResult> finalResultList = new ArrayList<>();
		System.out.println(fwmResultThreads.size()+" : thread");
		System.out.println(fwmMultipleResults.size());
		for(int i=0;i<fwmResultThreads.size();i++) {
			try {
				fwmResultThreads.get(i).join();
				if(i == 0) {
					ArrayList<ArrayList<Integer>> temp = fwmMultipleResults.get(i).result;
					previousList.addAll(temp);
					if(i == fwmResultThreads.size() -1) {
						GetFinalResult m = new GetFinalResult(currentList);
						Thread t = new Thread(m);
						finalResultList.add(m);
						finalResultThreadList.add(t);
					}
					
				}else {
					currentList = new ArrayList<>();
					ArrayList<ArrayList<Integer>> s = fwmMultipleResults.get(i).result;
					System.out.println("previous result:"+previousList.size());
					System.out.println(s.get(0).size()+" : "+s.size());
					
					if(s.size() > 1000 && s.get(0).size() >= 10) {
						ArrayList<ArrayList<Integer>> t = new ArrayList<>();
						for(int itr=0;itr<1000;itr++) {
							t.add(s.get(itr));
						}
						s = t;
						System.out.println(s.get(0).size()+" : "+s.size());
					}
					for(int j=0;j<previousList.size();j++) {
						if(i == fwmResultThreads.size() - 1) {
							ArrayList<ArrayList<Integer>> list = new ArrayList<>();
							for(int k=0;k<s.size();k++) {
								ArrayList<Integer> temp = new ArrayList<>();
								temp.addAll(s.get(k));
								temp.addAll(previousList.get(j));
								list.add(temp);
								currentList.add(temp);
							}
							
							GetFinalResult m = new GetFinalResult(list);
							Thread t = new Thread(m);
							finalResultList.add(m);
							finalResultThreadList.add(t);
						}else {
							for(int k=0;k<s.size();k++) {
								ArrayList<Integer> temp = new ArrayList<>();
								temp.addAll(s.get(k));
								temp.addAll(previousList.get(j));
								currentList.add(temp);
							}
						}
						
					}
					previousList = new ArrayList<>();
					previousList.addAll(currentList);
					System.out.println(currentList.get(0));
					System.out.println(currentList.size()+" : current");
				}
				
				
			}catch(Exception e) {
				
			}
		}
		System.out.println("Final List Length:"+ currentList.size());
		System.out.println("Calulating Final results:");
		int minFwm = -1;
		String finalResult = "";
		int startBound = 0,endBound = 200,threadSize = finalResultThreadList.size();
		while(startBound < endBound && startBound < threadSize) {
			if(endBound > threadSize) {
	        	endBound = threadSize;
	        }
			for(int j=startBound;j<endBound;j++) {
				finalResultThreadList.get(j).start();
			}	
	        
	        try {
	        	for(int j=startBound;j<endBound;j++) {
	        		finalResultThreadList.get(j).join();
	        		GetFinalResult r = finalResultList.get(j);
					if(minFwm == -1) {
						minFwm = r.finalFWM;
						finalResult = r.result.toString();
					}
					if(minFwm > r.finalFWM) {
						minFwm = r.finalFWM;
						finalResult = r.result.toString();
					}
//					System.out.println("FinalFWM " +r.finalFWM);
//					System.out.println("Final Answer"+r.result.toString());
	    		}
	        	
	        }catch(Exception e) {
	        	System.out.print(e.getMessage());
	        }
	        startBound = endBound;
	        endBound += endBound;
		}
		System.out.println("FinalFWM " +minFwm);
		System.out.println("Final Answer"+finalResult);
	}	
}



