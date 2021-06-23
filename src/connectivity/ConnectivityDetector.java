package connectivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.query.QuerySolution;

import lod.SparqlQuerier;
import lod.Triplestore;

public class ConnectivityDetector extends SparqlQuerier{
	
	public String filename = "";
	public HashMap<Integer, Integer> countConnectivity = new HashMap<Integer, Integer>();

	public ConnectivityDetector(Triplestore triplestore) {
		super(triplestore);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean fact(QuerySolution qs) throws InterruptedException {
		// TODO Auto-generated method stub
		this.countConnectivity.put(qs.get("k").asLiteral().getInt(),  qs.get("nbk").asLiteral().getInt());
		return true;
	}

	public void detect(String prop, String restrictionSubj, String restrictionObj) {

		if(restrictionSubj.length() > 0 && restrictionSubj != null) restrictionSubj+=".";
		if(restrictionObj.length() > 0 && restrictionObj != null) restrictionObj+=".";

		String queryStr = ""
				+ "SELECT ?k (COUNT(?k) as ?nbk) " + "WHERE {" +
				"SELECT ?o (count(?s) as ?k) " + "WHERE {" +
				"?s <"+prop+"> ?o."+ restrictionSubj + " " +restrictionObj+" } " +
				"GROUP BY (?o) }" + 
				"GROUP BY (?k) ";

		this.setQuery(queryStr);
		try {
			this.execute();
		} catch (InterruptedException e) {
			System.out.println("Error : "+e);
		}
	}

	public void export(String i) {
		String buff = "k,nbk,pk,type\n";
		String sep = ",";
		int tot = 0;
		
		for(Map.Entry<Integer, Integer> entry : this.countConnectivity.entrySet()) {
			tot += entry.getValue();

		}
		
		for(Map.Entry<Integer, Integer> entry : this.countConnectivity.entrySet()) {
			double pk = (double)entry.getValue()/(double)tot;
			buff += entry.getKey()+sep+entry.getValue()+sep+pk+sep+"real"+"\n";

		}
		
		this.filename = i+"_real"+".csv";
		File outputFile= new File(i+"_real"+".csv");
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
