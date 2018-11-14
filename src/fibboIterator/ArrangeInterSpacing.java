package fibboIterator;

import java.util.ArrayList;
import java.util.Comparator;

public class ArrangeInterSpacing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static int[] arrangeArray(int[] input){
		if(input == null) {
			return new int[0];
		}
		int answer[] = new int[input.length];
		if(input.length == 0) {
			return answer;
		}
		if(input.length == 1) {
			answer[0] = input[0];
		}
		int mid = (answer.length + 1)/2;
		answer[mid] = input[0];
		return answer;
	}
}
