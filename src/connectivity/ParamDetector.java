package connectivity;

import org.apache.jena.query.QuerySolution;

import lod.SparqlQuerier;
import lod.Triplestore;

public class ParamDetector extends SparqlQuerier{

	private int fact = 0;
	private int subjects = 0;
	private int objects = 0;

	public ParamDetector(Triplestore triplestore) {
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
		this.fact = qs.get("nbFact").asLiteral().getInt();
		this.subjects = qs.get("nbSubjects").asLiteral().getInt();
		this.objects = qs.get("nbObjects").asLiteral().getInt();
		return true;
	}

	public void detect(String prop, String restrictionSubj, String restrictionObj) {

		if(restrictionSubj.length() > 0 && restrictionSubj != null) restrictionSubj+=".";
		if(restrictionObj.length() > 0 && restrictionObj != null) restrictionObj+=".";

		String queryStr = ""
				+ "SELECT (COUNT(*) as ?nbFact) (COUNT(DISTINCT ?s) as ?nbSubjects) (COUNT(DISTINCT ?o) as ?nbObjects)"
				+ "WHERE {"
				+"?s <"+prop+"> ?o."+ restrictionSubj + " "+restrictionObj+" } ";

		this.setQuery(queryStr);
		try {
			this.execute();
		} catch (InterruptedException e) {
			System.out.println("Error : "+e);
		}
	}

	public int getFact() {
		return fact;
	}

	public int getSubjects() {
		return subjects;
	}

	public int getObjects() {
		return objects;
	}

}
