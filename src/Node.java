import java.util.ArrayList;

public class Node {

	public int id;
	public ArrayList<Edge> succ = new ArrayList<Edge>();
	
	public double connectivity = 0;
	
	public Node(int id) {
		this.id = id;
	}
	
	public int degree() {
		return this.succ.size();
	}
	
	public boolean hasEdge(int id) {
		boolean res = false; 
		
		for(Edge e : succ) {
			if(e.finalNode.id == id) {
				res = true;
			}
		}
		
		return res;
	}
}
