package fibboIterator;
import java.util.*;
public class FwmCalculator {
	public static void main(String args[]) {
		ArrayList<Integer> input = new ArrayList<>();
		input.add(8);
		input.add(10);
		input.add(12);
		input.add(14);
		input.add(16);
		input.add(15);
		input.add(9);
		input.add(11);
		input.add(13);
		int fwm = getFinalFWMResult(input);
//		System.out.println("FWM:"+fwm);
	}
	public static int factorial(int number){
	    if(number <= 1){
	      return 1;
	    }
	    return number * factorial(number - 1);
	}

	public static int getCombination(int number){
	    int numerator = number * (number-1);
	    int denominator = 2;
	    return (numerator/denominator);
	}
	
	public static int getFinalFWMResult(ArrayList<Integer> input) {
		int answer = -1;
		
		int sum = 0;
		ArrayList<Integer> temp = new ArrayList<>();
		temp.add(0,0);
		for(int i=0;i<input.size();i++) {
			sum = sum+input.get(i);
			temp.add(sum);
		}
//		System.out.println(input);
		int [][] differenceArr = returnDifferenceArr(temp);
		answer = checkFeasibility(differenceArr);
		return answer;
	}
	
	public static int [][] returnDifferenceArr(ArrayList<Integer> answer){
		int prevN = 0;
		int [][] differenceArr = new int[answer.size()][answer.size()];
		for(int i=0;i<answer.size();i++){
		    for(int m=0;m<prevN+1;m++){
		      differenceArr[i][m] = Integer.MIN_VALUE;
		    }
		    for(int j=prevN+1;j<answer.size();j++){
		    	differenceArr[i][j] = (answer.get(j)-answer.get(prevN));
		    }
		    prevN++;
		  }
		return differenceArr;
	}
	public static int checkFeasibility(int differenceArr[][]) {
		int answer = 0;
		HashMap<Integer, ArrayList<Pair>> map = new HashMap<>();
		for(int i=0;i<differenceArr.length;i++){
			int tempDia = 1;
			for(int j=0;j<differenceArr[0].length;j++){
				if(differenceArr[i][j] == Integer.MIN_VALUE){

				}else{
					if (map.containsKey(differenceArr[i][j])) {
						Pair a = new Pair(tempDia,i+1);
						ArrayList<Pair>array = map.get(differenceArr[i][j]);
						array.add(a);
						map.put(differenceArr[i][j], array);
					}else{
						Pair a = new Pair(tempDia,i+1);
						ArrayList<Pair>array = new ArrayList<>();
						array.add(a);
						map.put(differenceArr[i][j],array);
					}
					tempDia++;
				}

			}
		}
		int Nb = 0;
		int Nc = 0;
		int lengthOfTheArray = 0;
		for (int key:map.keySet()) {
		    if (map.containsKey(key)) {
		    	if(map.get(key).size() > 1){
		    		ArrayList<Pair> arr = map.get(key);
		    		lengthOfTheArray = lengthOfTheArray + getCombination(arr.size());
		    		HashMap<Integer,ArrayList<Integer>>temp = new HashMap<>();
//		    		var isItCheckedAlready = []
		    		for (int i =0;i<arr.size();i++){
		    			int []info = arr.get(i).getInfo();
		    			int diagonal = info[0];
		    			int row  = info[1];
//		    			System.out.println(key+"-->"+row+","+diagonal);
		    			if (temp.containsKey(row)){
		    				ArrayList<Integer>tempArr = temp.get(row);
		    				tempArr.add(i);
		    				temp.put(row,tempArr);
		    			}else{
		    				ArrayList<Integer>tempArr = new ArrayList<>();
		    				tempArr.add(i);
		    				temp.put(row,tempArr);
		    			}
//		    			isItCheckedAlready.push(false)
		    		}
		    		
		    		CheckNeighbours neighbour = new CheckNeighbours();
		    		for (int i = 0;i<arr.size();i++){
		    			int [] info = arr.get(i).getInfo();
		    			int diagonal = info[0];
		    			int row  = info[1];
		    			int difference = row - diagonal;
		    			int sum = row + diagonal;
		    			if (difference > 0 && temp.containsKey(difference)){
//		    				isItCheckedAlready[i] = true
		    				ArrayList<Integer> toCheckTrue = temp.get(difference);
		    				for(int j=0;j<toCheckTrue.size();j++){
//		    					isItCheckedAlready[toCheckTrue[j]] = true
		    					neighbour.addNeighbour(i,toCheckTrue.get(j),difference);
		    				}
		    			}
		    			if (sum >0 && temp.containsKey(sum)){
		    				ArrayList<Integer> toCheckTrue = temp.get(sum);
		    				for(int j=0;j<toCheckTrue.size();j++){
//		    					isItCheckedAlready[toCheckTrue[j]] = true;
		    					 neighbour.addNeighbour(i,toCheckTrue.get(j),sum);
		    				}
		    			}

		    		}
		    		HashMap<String,Integer> neighbourHash = neighbour.returnNeighbour();
		    		Nb = Nb + neighbourHash.keySet().size();
		    	}else{
		    		// not required variables
		    	}

		    }
		}
		Nc = lengthOfTheArray - Nb;
		answer = 3*Nb+2*Nc;
		return answer;
	}
	
}
