package fibboIterator;

import java.util.ArrayList;

public class ArrangeArray implements Runnable{
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