import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Facts {

	//public HashMap<Node,HashMap<String,ArrayList<Node>>> setFacts = new HashMap<Node,HashMap<String,ArrayList<Node>>>();

	public HashMap<Node,HashMap<String,Node>> setFacts = new HashMap<Node,HashMap<String,Node>>();

	public Facts() {}

	//Randomly generated set of facts. 
	//ns -> number of subjets 
	//no -> random number between 0 and no of objets for each subject
	public Facts(int ns, int no) {

		int index = ns+1;

		String prop = "Property name test";

		HashMap<Node,HashMap<String,Node>> setFactsTmp = new HashMap<Node,HashMap<String,Node>>();


		for(int i = 0 ; i<no ; i++) {
			int subj = (int) (Math.random()*ns)+1;
			HashMap<String,Node> setPropObj = new HashMap<String,Node>();

			setPropObj.put(prop, new Node(index));
			index++;

			setFactsTmp.put(new Node(subj), setPropObj);
			index++;

		}

		this.setFacts = setFactsTmp;

	}

	//TO DO if it is useful when we switch to real data
	public void addPropObj(int ns, int no, String prop) {}

	/*public String toString() {
		String res = "";

		for(Map.Entry mapentry : this.setFacts.entrySet()) {
			HashMap<String,ArrayList<Node>> setPropObj = (HashMap<String, ArrayList<Node>>) mapentry.getValue();
			Node subjectNode = (Node) mapentry.getKey();
			res += "Subject : "+subjectNode.id+" : "+"\n";
			for(Map.Entry mapentryTwo : setPropObj.entrySet()) {
				ArrayList<Node> setObj = (ArrayList<Node>) mapentryTwo.getValue();
				for(Node n : setObj) {
					res += " Prop : "+mapentryTwo.getKey()+" Object : "+n.id+"\n";
				}
			}
		}

		return res;
	}*/

	public String toString() {
		String res = "";

		for(Map.Entry mapentry : this.setFacts.entrySet()) {
			HashMap<String,Node> setPropObj = (HashMap<String,Node>) mapentry.getValue();
			Node subjectNode = (Node) mapentry.getKey();
			res += "Subject : "+subjectNode.id+" : ";
			for(Map.Entry mapentryTwo : setPropObj.entrySet()) {
				Node obj = (Node) mapentryTwo.getValue();
				res += " Prop : "+mapentryTwo.getKey()+" Object : "+obj.id+"\n";
			}
		}

		return res;
	}

	public void export(int i) {
		String buff = "Source,Target\n";
		String sep = ",";

		for(Map.Entry entry : this.setFacts.entrySet()) {
			Node n = (Node) entry.getKey();
			HashMap<String,Node> hmap = (HashMap<String, Node>) entry.getValue();
			for(Map.Entry entry2 : hmap.entrySet()) {

				Node n1 = (Node) entry2.getValue();
				buff +=n.id+sep+n1.id+"\n";

			}
		}

		File outputFile= new File(this.getClass()+String.valueOf(i)+".csv");
		FileWriter out;
		try {

			out = new FileWriter(outputFile);
			out.write(buff);
			out.close();

		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
