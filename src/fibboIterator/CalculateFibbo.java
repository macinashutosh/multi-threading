package fibboIterator;

import java.util.*;

class GetPermutation  implements Runnable  {
	ArrayList<ArrayList<Integer>> result;
	int input [];
	public GetPermutation(int []arr) {
		this.input = arr;
	}
	public void run() {
		this.result = permute(this.input);
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
public class CalculateFibbo {
	public static String firstFile = "First.txt";
	public static String secondFile = "Second.txt";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> firstBU = new ArrayList<Integer>();
		ArrayList<Integer> secondBU = new ArrayList<Integer>();
		ArrayList<Integer> spacing = new ArrayList<>();
		ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
		ArrayList<Thread> threads = new ArrayList<>();
		ArrayList<GetPermutation> permutationBlocks = new ArrayList<>();
		
		spacing.add(10);
		for(int i=0;i<8;i++) {
			firstBU.add(i);
		}
		for(int i=0;i<6;i++) {
			secondBU.add(i);
		}
		inputList.add(firstBU);
		inputList.add(spacing);
		inputList.add(secondBU);
		inputList.add(spacing);
	    for (int i=0;i<inputList.size();i++) {
	    	System.out.println(inputList.get(i));
	    }
		int arr2 [] = new int [secondBU.size()];
		for(int i=0;i<secondBU.size();i++) {
			arr2[i] = secondBU.get(i);
		}
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
		    
		for(int i=0;i<threads.size();i++) {
			threads.get(i).start();
		}	
        
        try {
        	for(int i=0;i<threads.size();i++) {
    			threads.get(i).join();
    		}
        	
        }catch(Exception e) {
        	System.out.print(e.getMessage());
        }
        
        
        for(int i=0;i<permutationBlocks.size();i++) {
        	System.out.println(permutationBlocks.get(i).result.size());
        }
        threads.removeAll(threads);
         
        System.out.println("Program permuted");
        
        ArrayList<Thread> threadsAfterPermutation = new ArrayList<>();
        ArrayList<ArrayList<Integer>> previousList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> currentList = new ArrayList<>();
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
        	for(int k=0;k<threadsAfterPermutation.size();k++) {
        		threadsAfterPermutation.get(k).start();
    		}	
            
            try {
            	for(int k=0;k<threadsAfterPermutation.size();k++) {
            		threadsAfterPermutation.get(k).join();
        		}
            	
            }catch(Exception e) {
            	System.out.print(e.getMessage());
            }
            for(int k=0;k<arrangedArrays.size();k++) {
            	ArrayList<ArrayList<Integer>> results = arrangedArrays.get(k).results;
            	if (results!=null && results.size() !=0) {
            		currentList.addAll(results);
            	}
            }
            previousList.removeAll(previousList);
            previousList.addAll(currentList);
            
            threadsAfterPermutation.removeAll(threadsAfterPermutation);
        	
        }
        
        System.out.println(currentList.size());
        System.out.println("Program end");
        
 	}
	
	
	
	

}
