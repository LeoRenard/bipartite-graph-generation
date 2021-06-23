import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BiPartite {

	public HashMap<Integer, Node> subjNodes = new HashMap<Integer, Node>();
	public HashMap<Integer, Node> objNodes = new HashMap<Integer, Node>();

	public int index = 0;

	public int pS = 0;
	public int pO = 0;
	
	public String filename = "";

	public BiPartite() {}

	public BiPartite(int fact, int subjects, int objects, int initFact) {
		index = subjects+1;

		int lim = subjects+objects+1;

		double pS_d = (double) subjects / (double) fact;
		double pO_d = (double) objects / (double) fact;

		pS = (int) (pS_d*100);
		pO = (int) (pO_d*100);

		ArrayList<Integer> as = new ArrayList<Integer>();
		ArrayList<Integer> ao = new ArrayList<Integer>();

		for(int i = 0; i<subjects;i++) {
			as.add(i);
		}
		for(int i = subjects; i< (objects+subjects); i++) {
			ao.add(i);
		}

		//Initialize the number of fact already present in the bipartite graph
		for(int i = 0; i < initFact ; i++) {

			int is = (int) (Math.random() * (as.size()));
			int s = as.get(is); 

			int io = (int) (Math.random() * (ao.size()));
			int o = ao.get(io);

			//Case if the bipartite graph already contains the subject and object
			if(subjNodes.containsKey(s) && objNodes.containsKey(o)) {
				is = (int) (Math.random() * (as.size()));
				s = as.get(is);

				io = (int) (Math.random() * (ao.size()));
				o = ao.get(io);

				Iterator itr = as.iterator();
				while (itr.hasNext())
				{
					int x = (Integer)itr.next();
					if (x == s)
						itr.remove();
				}

				Iterator itr2 = ao.iterator();
				while (itr2.hasNext())
				{
					int x = (Integer)itr2.next();
					if (x == o)
						itr2.remove();
				}

			}
			//Case if the bipartite graph already contains the subject
			else if(subjNodes.containsKey(s) && !objNodes.containsKey(o)) {
				is = (int) (Math.random() * (as.size()));
				s = as.get(is);

				Iterator itr = as.iterator();
				while (itr.hasNext())
				{
					int x = (Integer)itr.next();
					if (x == s)
						itr.remove();
				}
			}
			//Case if the bipartite graph already contains the object
			else if(!subjNodes.containsKey(s) && objNodes.containsKey(o)) {
				io = (int) (Math.random() * (ao.size()));
				o = ao.get(io);

				Iterator itr2 = ao.iterator();
				while (itr2.hasNext())
				{
					int x = (Integer)itr2.next();
					if (x == o)
						itr2.remove();
				}
			}
			this.addSubjNode(s);
			this.addObjNode(o);
			this.addEdge(s, o);

		}

		//Algo body
		int s = -1;
		int  o = -1;

		for(int j = initFact; j < fact; j++) {

			int pForNewSubj = (int)(Math.random()*101);
			int pForNewObj = (int)(Math.random()*101);
			System.out.println(pForNewSubj);
			System.out.println(pForNewObj);

			//Check if we add a new subject
			if(pForNewSubj <= pS) {
				System.out.println("new subject");

				if(as.size() != 0) {
					int is = (int) (Math.random() * (as.size()));
					s = as.get(is);

					Iterator itr = as.iterator();
					while (itr.hasNext())
					{
						int x = (Integer)itr.next();
						if (x == s)
							itr.remove();
					}
				}
				else {
					s = lim;
					lim++;
				}
				this.addSubjNode(s);
			}
			else {
				System.out.println("old subject");
				double subjTotC = this.getSubjTotConnectivity();

				HashMap<Integer,Double> subjConnectivity = new HashMap<Integer,Double>();

				//Recovering the probabilities that each subject is chosen
				for(Map.Entry mapentry : this.subjNodes.entrySet()) {

					Node node = (Node) mapentry.getValue();
					double connectivity = node.succ.size();
					double proba_d = (connectivity/subjTotC)*100;
					subjConnectivity.put(node.id, proba_d);
				}
				subjConnectivity = this.triAvecValeur(subjConnectivity);
				double subj = (int) (Math.random()*101);
				double deb = 0;
				boolean stop = false;

				Iterator<Map.Entry<Integer, Double>> it = subjConnectivity.entrySet().iterator();
				while (it.hasNext() && stop == false) {
					Map.Entry<Integer, Double> pair = it.next();
					double proba = (double) pair.getValue();
					proba+=deb;

					if(deb<=subj && subj<proba) {
						s = (int) pair.getKey();
						System.out.println("Subject : "+s);
						stop = true;
					}
					else {
						deb = proba;
					}
				}

			}

			//Check if we add a new object
			if(pForNewObj <= pO) {
				System.out.println("new object");

				if(ao.size() != 0) {
					int io = (int) (Math.random() * (ao.size()));
					o = ao.get(io);

					Iterator itr2 = ao.iterator();
					while (itr2.hasNext())
					{
						int x = (Integer)itr2.next();
						if (x == o)
							itr2.remove();
					}
				}
				else {
					o = lim;
					lim++;
				}
				this.addObjNode(o);
			}
			else {
				System.out.println("old object");
				double objTotC = this.getObjTotConnectivity();

				HashMap<Integer,Double> objConnectivity = new HashMap<Integer,Double>();

				//Recovering the probabilities that each object is chosen
				for(Map.Entry mapentry : this.objNodes.entrySet()) {

					Node node = (Node) mapentry.getValue();
					double connectivity = node.succ.size();
					double proba_d = (connectivity/objTotC)*100;
					objConnectivity.put(node.id, proba_d);
				}
				objConnectivity = this.triAvecValeur(objConnectivity);
				double obj = (double) (Math.random()*101);
				double deb = 0;
				boolean stop = false;
				Iterator<Map.Entry<Integer, Double>> it = objConnectivity.entrySet().iterator();
				while (it.hasNext() && stop == false) {
					Map.Entry<Integer, Double> pair = it.next();
					double proba = (double) pair.getValue();
					proba+=deb;

					if(deb<=obj && obj<proba) {
						o = (int) pair.getKey();
						System.out.println("Object : "+o);
						stop = true;
					}
					else {
						deb = proba;
					}
				}

			}

			this.addEdge(s, o);
		}

	}

	public void addSubjNode(int n) {
		Node node = new Node(n);

		subjNodes.put(n, node);
	}

	public void addObjNode(int n) {
		Node node = new Node(n);

		objNodes.put(n, node);
	}


	public void addEdge(int nS, int nF) {
		Node n = new Node(nS);
		Node n1 = new Node(nF);

		if(subjNodes.containsKey(nS) && objNodes.containsKey(nF)) {
			this.getSubjNodes(nS).succ.add(new Edge(n,n1));
			this.getObjNodes(nF).succ.add(new Edge(n1,n));
		}
	}

	public boolean hasEdge(int s) {
		boolean res = false;

		return res;
	}

	public boolean objectAlreadyLinked(int o) {
		boolean res = false;

		for(Map.Entry mapentry : this.subjNodes.entrySet()) {
			Node n = (Node) mapentry.getValue();
			if(n.hasEdge(o)) res = true;
		}

		return res;
	}

	public Node getSubjNodes(int n) {
		Node res = subjNodes.get(n);
		return res;
	}

	public Node getObjNodes(int n) {
		Node res = objNodes.get(n);
		return res;
	}

	public int getSubjTotConnectivity() {
		int connectivity = 0;
		for(Map.Entry mapentry : this.subjNodes.entrySet()) {
			Node node = (Node) mapentry.getValue();
			connectivity+=node.succ.size();
		}
		return connectivity;
	}

	public int getObjTotConnectivity() {
		int connectivity = 0;
		for(Map.Entry mapentry : this.objNodes.entrySet()) {
			Node node = (Node) mapentry.getValue();
			connectivity+=node.succ.size();
		}
		return connectivity;
	}

	public void export(int i) {
		String buff = "Source,Target\n";
		String sep = ",";

		for(Map.Entry<Integer, Node> entry : this.subjNodes.entrySet()) {

			for(Edge edge : entry.getValue().succ) {
				buff += edge.sourceNode.id+sep+edge.finalNode.id+"\n";
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

	public void export(String i) {
		String buff = "Source,Target\n";
		String sep = ",";

		for(Map.Entry<Integer, Node> entry : this.subjNodes.entrySet()) {

			for(Edge edge : entry.getValue().succ) {
				buff += edge.sourceNode.id+sep+edge.finalNode.id+"\n";
			}
		}
		
		File outputFile= new File(this.getClass()+"_"+i+".csv");
		FileWriter out;
		try {

			out = new FileWriter(outputFile);
			out.write(buff);
			out.close();

		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void exportProcessedFile(String i) {
		String buff = "k,nbk,pk,type\n";
		String sep = ",";
		int tot = 0;

		HashMap<Integer, Integer> alConn = new HashMap<Integer, Integer>();
		
		for(Map.Entry<Integer, Node> entry : this.objNodes.entrySet()) {
			int conn = entry.getValue().succ.size();
			
			if(!alConn.containsKey(conn)) alConn.put(conn, 1);
			else {
				int nbWithThisConn = alConn.get(conn);
				nbWithThisConn ++;
				alConn.put(conn, nbWithThisConn);
			}
		}
		
		for(Map.Entry<Integer, Integer> mapentry : alConn.entrySet()) {
			tot += mapentry.getValue();
		}
		
		for(Map.Entry<Integer, Integer> mapentry : alConn.entrySet()) {
			double pk = (double)mapentry.getValue()/(double) tot;
			buff += mapentry.getKey()+sep+mapentry.getValue()+sep+pk+sep+"fictive"+"\n";
		}

		this.filename = i+"_fictive"+".csv";
		File outputFile= new File(i+"_fictive"+".csv");
		FileWriter out;
		try {

			out = new FileWriter(outputFile);
			out.write(buff);
			out.close();

		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<Integer, Double> triAvecValeur( HashMap<Integer, Double> map ){
		List<Map.Entry<Integer, Double>> list = new LinkedList<Map.Entry<Integer, Double>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<Integer, Double>>(){
			public int compare( Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2 ){
				return (o1.getValue()).compareTo( o2.getValue());
			}
		});

		HashMap<Integer, Double> map_apres = new LinkedHashMap<Integer, Double>();
		for(Map.Entry<Integer, Double> entry : list)
			map_apres.put( entry.getKey(), entry.getValue() );
		return map_apres;
	}

}
