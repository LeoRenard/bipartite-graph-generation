package lod;

public enum WDT {
	
	P65("site of astronomical discovery",53718,53679,548),
	P149("architectural style",116275,109887,880),
	P2079("fabrication method",118389,98163,964),
	P103("native language",180905,179691,1159),
	P6379("has works in the collection",243665,107308,5267),
	P2094("competition class",339319,334873,913),
	P140("religion",380881,374437,1453),
	P102("member of political party",445421,405777,9815);
	//P170("creator",864759,845694,192974);
	
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
