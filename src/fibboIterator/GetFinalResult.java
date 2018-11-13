package fibboIterator;

import java.util.ArrayList;
import java.util.HashMap;

public class GetFinalResult implements Runnable{
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
	    int numerator = factorial(number);
	    int denominator = factorial(2)*factorial(number - 2);
	    return (numerator/denominator);
	}
	
	public GetFinalResult(ArrayList<ArrayList<Integer>> input) {
		this.input = input;
	}
	public void run() {
		if(this.input!=null && this.input.size() > 0) {
			ArrayList<Integer> answer = new ArrayList<Integer>();
			int fwm = -1;
			for(int i=0;i<input.size();i++) {
				int tempFWM = FwmCalculator.getFinalFWMResult(input.get(i));
				if(answer.isEmpty()) {
					answer = input.get(i);
					fwm = tempFWM;
				}else {
					if(tempFWM < fwm) {
						fwm = tempFWM;
						answer = input.get(i);
//						System.out.println(fwm);
					}
				}
				
			}
			this.result = answer;
			this.finalFWM = fwm;
		}
	}
	
	
}

