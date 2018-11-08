package fibboIterator;

import java.util.*;

class GetPermutation  implements Runnable  {
	ArrayList<ArrayList<Integer>> result;
	int finalFWM;
	int input [];
	
	

	public GetPermutation(int []arr) {
		this.input = arr;
	}
	public void run() {
		this.result = permute(this.input);
//		ArrayList<Integer> answer = new ArrayList<Integer>();
//		int fwm = -1;
//		for(int i=0;i<temp.size();i++) {
//			ArrayList<Integer> list = temp.get(i);
//			int tempFWM = checkFeasibility(returnDifferenceArr(list));
//			if(answer.isEmpty()) {
//				answer = list;
//				fwm = tempFWM;
//			}else {
//				if(tempFWM < fwm || fwm == -1) {
//					fwm = tempFWM;
//					answer = list;
//				}
//			}
//		}
//		this.result = answer;
//		this.finalFWM = fwm;
	}
	public  ArrayList<ArrayList<Integer>> permute(int[] arr) {
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();
		permuteHelper(list, new ArrayList<>(), arr);
		return list;
	}
	
	private  void permuteHelper(ArrayList<ArrayList<Integer>> list, List<Integer> resultList, int [] arr){
 
		// Base case
		if(resultList.size() == arr.length){
			list.add(new ArrayList<>(resultList));
		} 
		else{
			for(int i = 0; i < arr.length; i++){ 
 
				if(resultList.contains(arr[i])) 
				{
					// If element already exists in the list then skip
					continue; 
				}
				// Chosen element
				resultList.add(arr[i]);
				// Explore
				permuteHelper(list, resultList, arr);
				// Not chosen element
				resultList.remove(resultList.size() - 1);
			}
		}
	}
	
}


class ArrangeArray implements Runnable{
	ArrayList<ArrayList<Integer>> precedingResults;
	ArrayList<Integer> input;
	ArrayList<ArrayList<Integer>> results;
	public ArrangeArray(ArrayList<ArrayList<Integer>> p,ArrayList<Integer> input) {
		this.precedingResults = p;
		this.input = input;
	}
	public void run() {
		this.results = new ArrayList<ArrayList<Integer>> ();
		if(this.precedingResults.size() == 0) {
			this.results.add(input);
		}else {
			for (int i=0;i<precedingResults.size();i++) {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.addAll(input);
				temp.addAll(precedingResults.get(i));
				this.results.add(temp);
			}
			
		}
	}
}

class GetFinalResult implements Runnable{
	ArrayList<ArrayList<Integer>> input;
	ArrayList<Integer> result;
	int finalFWM;
	public int factorial(int number){
	    if(number <= 1){
	      return 1;
	    }
	    return number * factorial(number - 1);
	}

	public int getCombination(int number){
	    var numerator = factorial(number);
	    var denominator = factorial(2)*factorial(number - 2);
	    if(denominator <= 0) {
	    	denominator = 1;
	    }
	    return numerator/denominator;
	}
	
	public GetFinalResult(ArrayList<ArrayList<Integer>> input) {
		this.input = input;
	}
	public void run() {
		if(this.input!=null && this.input.size() > 0) {
			ArrayList<Integer> answer = new ArrayList<Integer>();
			int fwm = -1;
			for(int i=0;i<input.size();i++) {
				ArrayList<Integer> list = input.get(i);
				list.add(0,0);
				System.out.println(list);
				System.out.println(returnDifferenceArr(list));
				int tempFWM = checkFeasibility(returnDifferenceArr(list));
				if(answer.isEmpty()) {
					answer = list;
					fwm = tempFWM;
				}else {
					if(tempFWM < fwm || fwm == -1) {
						fwm = tempFWM;
						answer = list;
					}
				}
			}
			this.result = answer;
			this.finalFWM = fwm;
		}
	}
	
	public int checkFeasibility(ArrayList<ArrayList<Integer>> differenceArr) {
				HashMap<Integer,ArrayList<Double>> map = new HashMap<>();
				for(int j=0;j<differenceArr.size();j++){
				    int tempDia = 1;
				    ArrayList<Integer> singleList = differenceArr.get(j);		
				    for(int k=0;k<singleList.size();k++){
				      if(singleList.get(k) == Integer.MIN_VALUE){
				    	 
				      }else{
				        if (map.containsKey(singleList.get(k))) {
				          Double a = new Double(tempDia,j+1);
				          ArrayList<Double> array = map.get(singleList.get(k));
				          array.add(a);
				          map.put(singleList.get(k), array);
				        }else{
				          Double a = new Double(tempDia,j+1);
				          ArrayList<Double> array = new ArrayList<>();
				          array.add(a);
				          map.put(singleList.get(k), array);
				        }
				        tempDia++;
				      }
		
				    }
				}
				
			  int Nb = 0;
			  int Nc = 0;
			  
			  int lengthOfTheArray = 0;
			  for (int key:map.keySet()) {
				  
				      if(map.containsKey(key)) {
				    	  
				    	  ArrayList<Double> arr = map.get(key);
				        if(arr.size() > 1){
				          lengthOfTheArray = lengthOfTheArray + getCombination(arr.size());
				          HashMap<Integer,ArrayList<Integer>> temp = new HashMap<>();
				          boolean isItCheckedAlready [] = new boolean[arr.size()];
				          for (int i=0;i<arr.size();i++){
				            int [] info = arr.get(i).getInfo();
				            int diagonal = info[0];
				            int row  = info[1];
				            if (temp.containsKey(row)){
				              ArrayList<Integer> tempArr = temp.get(row);
				              tempArr.add(i);
				              temp.put(row, tempArr);
				            }else{
				              ArrayList<Integer> tempArr = new ArrayList<>();
				              tempArr.add(i);
				              temp.put(row,tempArr);
				            }
			
				            isItCheckedAlready[i] = false;
				            // console.log(temp)
				          }
			
				          CheckNeighbours neighbour = new CheckNeighbours();
				          for (int i = 0;i<arr.size();i++){
				            int []info = arr.get(i).getInfo();
				            int diagonal = info[0];
				            int row  = info[1];
				            int difference = row - diagonal;
				            int sum = row + diagonal;
					        if (difference > 0 && temp.containsKey(difference)){
					              isItCheckedAlready[i] = true;
					              ArrayList<Integer> toCheckTrue = temp.get(difference);
					              for(int j=0;j<toCheckTrue.size();j++){
					                isItCheckedAlready[toCheckTrue.get(j)] = true;
					                neighbour.addNeighbour(i,toCheckTrue.get(j),difference);
					              }
					            if (sum >0 && temp.containsKey(sum)){
					              toCheckTrue = temp.get(sum);
					              for(int j=0;j<toCheckTrue.size();j++){
					                isItCheckedAlready[toCheckTrue.get(j)] = true;
					                neighbour.addNeighbour(i,toCheckTrue.get(j),sum);
					              }
					            }
				           }
				          Nb = Nb +neighbour.returnNeighbour().keySet().size();
				        }
				      }
				  }
			  }
		//	  int answer[] = new int[2];
		//	  answer[0] = Nb;
		//	  answer[1] = lengthOfTheArray-Nb;
			  Nc = lengthOfTheArray - Nb;
			  int answer = 3*Nb+2*Nc;
			  System.out.println(Nc);
			  return answer;
		}

	public ArrayList<ArrayList<Integer>> returnDifferenceArr(ArrayList<Integer> answer){
		int prevN = 0;
		System.out.println(answer);
		ArrayList<ArrayList<Integer>> differenceArr = new ArrayList<>();
		for(int i=0;i<answer.size();i++){
		    ArrayList<Integer> temp1 = new ArrayList<>();
		    for(int m=0;m<prevN+1;m++){
		      temp1.add(Integer.MIN_VALUE);
		    }
		    for(int j=prevN+1;j<answer.size();j++){
		      temp1.add((answer.get(i)-answer.get(prevN)));
		    }
		    differenceArr.add(temp1);
		    prevN++;
		  }
		return differenceArr;
	}
}



public class CalculateFibbo {
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		ArrayList<Integer> zero = new ArrayList<>();
		firstBU.add(1);
		firstBU.add(2);
		firstBU.add(3);
		firstBU.add(4);
		firstBU.add(5);
		spacing.add(6);
		secondBU.add(7);
		secondBU.add(8);
		secondBU.add(9);
		inputList.add(firstBU);
		inputList.add(spacing);
		inputList.add(secondBU);
	    for (int i=0;i<inputList.size();i++) {
	    	System.out.println(inputList.get(i));
	    }
		int arr2 [] = new int [secondBU.size()];
		for(int i=0;i<secondBU.size();i++) {
			arr2[i] = secondBU.get(i);
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
		int startBound = 0,endBound = 10,threadSize = threads.size();
		System.out.println("Thread Size:"+threadSize);
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
	        
	        System.out.println("While loop is still on");
		}
		    
				
        
        
        for(int i=0;i<permutationBlocks.size();i++) {
        	System.out.println(permutationBlocks.get(i).result.size());
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
        	int startBound = 0,endBound = 10,threadSize = threadsAfterPermutation.size();
    		System.out.println("Thread Size:"+threadSize);
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
    	        
    	        System.out.println("While loop is still on");
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
		ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
		temp.add(currentList.get(0));
		GetFinalResult r = new GetFinalResult(temp);
		Thread s = new Thread(r);
		s.start();
		try {
			s.join();
			System.out.println(r.finalFWM);
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
        System.out.println(currentList.size());
        System.out.println("Arrays Arranged");
	}

	
}


class Double {
	public int diagonal,row;
	Double(int diagonal, int row){
		this.diagonal = diagonal;
		this.row = row;
	}
	
	public int[] getInfo(){
		int [] a = new int[2];
		a[0] = this.diagonal;
		a[1] = this.row;
		return a;
	}
}

class CheckNeighbours{
	HashMap<String,Integer> neighbour ;
	public CheckNeighbours() {
		// TODO Auto-generated constructor stub
		this.neighbour = new HashMap<>();
	}
	
	public boolean addNeighbour(int a,int b, int difference) {
		if (difference > 0) {
			String key = Math.min(a, b)+","+Math.max(a, b);
			if(this.neighbour.containsKey(key)) {
				return false;
			}else {
				this.neighbour.put(key, difference);
				return true;
			}
		}else {
			return false;
		}
	}
	public HashMap<String,Integer> returnNeighbour(){
		return this.neighbour;
	}
}