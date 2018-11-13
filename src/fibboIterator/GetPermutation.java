package fibboIterator;

import java.util.ArrayList;
import java.util.List;

public class GetPermutation  implements Runnable  {
	ArrayList<ArrayList<Integer>> result;
	int finalFWM;
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