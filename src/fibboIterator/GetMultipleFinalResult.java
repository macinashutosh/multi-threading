package fibboIterator;

import java.util.ArrayList;

public class GetMultipleFinalResult  implements Runnable{
	ArrayList<ArrayList<Integer>> input;
	ArrayList<ArrayList<Integer>> result;
	ArrayList<Integer> minimumResult;
	int checkFWM;
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
	
	public GetMultipleFinalResult(ArrayList<ArrayList<Integer>> input,int checkFWM) {
		this.checkFWM = checkFWM;
		this.input = input;
	}
	public void run() {
		if(this.input!=null && this.input.size() > 0) {
			ArrayList<ArrayList<Integer>> answer = new ArrayList<>();
			int fwm = -1;
			for(int i=0;i<input.size();i++) {
				int tempFWM = FwmCalculator.getFinalFWMResult(input.get(i));
				if(tempFWM < checkFWM) {
					answer.add(input.get(i));
				}
				if(fwm==-1) {
					fwm = tempFWM;
					this.minimumResult = input.get(i);
				}
				if(tempFWM < fwm) {
					fwm = tempFWM;
					this.minimumResult = input.get(i);
				}
				if(answer.size() > 1000) {
					break;
				}
			}
			this.result = answer;
			if(this.result.size() == 0) {
				System.out.println("minimumFWM:"+fwm);
				System.out.println("checkFWM:"+this.checkFWM);
				System.out.println("minimumResult:"+this.minimumResult);
				this.result.add(this.minimumResult);
			}
			this.finalFWM = fwm;
		}
	}
	
	
}