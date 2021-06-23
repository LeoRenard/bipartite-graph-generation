/**
 * @author Arnaud Soulet
 */

package lod;

public enum Triplestore {
	WIKIDATA("https://query.wikidata.org/sparql");
	
	private String endpoint;
	private String graph;

	private Triplestore(String endpoint) {
		this.endpoint = endpoint;
		this.graph = null;
	}
	
	private Triplestore(String endpoint, String graph) {
		this.endpoint = endpoint;
		this.graph = graph;
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	
	public String getGraph() {
		if (graph == null)
			return "";
		else
			return "FROM <" + graph + "> ";
	}
	
}
