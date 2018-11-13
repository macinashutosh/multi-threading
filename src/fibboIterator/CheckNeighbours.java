package fibboIterator;

import java.util.HashMap;

public class CheckNeighbours{
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