package fibboIterator;

public class Pair {
	public int diagonal,row;
	Pair(int diagonal, int row){
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

