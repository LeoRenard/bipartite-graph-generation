/*
 * This enumeration is deprecated. Used in the Test class to loop the processes on the properties present in this enumeration
 * It was useful for the previous versions.  
 * 
 * The new version of bipartite graph generation retrieves parameters int real time by querying the SPARQL endpoint of the KB (cf : ParamDetector class)
 * 
 * July 2021
 * */
package lod;

public enum WDT {
	
	//Properties with high Gini index
	P110("illustrator",11910,10809,5827),
	P65("site of astronomical discovery",53718,53679,548),
	P509("cause of death",109242,107417,1863),
	P149("architectural style",116275,109887,880),
	P2079("fabrication method",118389,98163,964),
	P103("native language",180905,179691,1159),
	P6379("has works in the collection",243665,107308,5267),
	P2094("competition class",339319,334873,913),
	P140("religion",380881,374437,1453),
	P102("member of political party",445421,405777,9815),
	P170("creator",864759,845694,192974),
	
	//Properties with low Gini index
	P161("cast member",1340394,210372,210372),
	P1547("depends on software",17951,7024,4131),
	P181("taxon range map image",15643,15479,15119),
	P3602("candidacy in election",115259,67074,6169),
	P451("partner",11682,9719,9862);
	//P407("language of work or name",13989747,-1,-1);
	
	
	private String name; 
	private int nbFacts;
	private int nbDistinctSubjects;
	private int nbDistinctObjects;
	
	private WDT(String name, int nbf, int nds, int ndo) {
		this.name = name;
		this.nbFacts = nbf;
		this.nbDistinctSubjects = nds;
		this.nbDistinctObjects = ndo;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getFacts() {
		return this.nbFacts;
	}
	
	public int getSubjects() {
		return this.nbDistinctSubjects;
	}
	
	public int getObjects() {
		return this.nbDistinctObjects;
	}

}
