/*
 * This Test class allows to generate bipartite graph corresponding to each property present in the WDT enumeration and export in a csvl file the useful information. 
 * Then it retrieves the real data, from each property, by querying, with its endpoint SPARQL, the Wikidata KB. (cf : classes of packages 'connectivity' and 'lod').
 * After that it merges these two csv files to obtain a single csv file that can be quickly used in R (scripts in the GitHub) to generate the object distribution graph.
 * 
 * The second loop calculates the d-statistic between the real and fictive data for each property. (Thanks to the csv file generated above).
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

import connectivity.ConnectivityDetector;
import csv.CsvFile;
import lod.Triplestore;
import lod.WDT;

public class Test {

	static String prefix = "http://www.wikidata.org/prop/direct/";

	static double[] realDistrib;
	static double[] fictiveDistrib;
	
	static String buff_ds = "property,d-statistics\n";
	static String sep = ",";

	public static void main(String[] args) {

		for(WDT wdt : WDT.values()) {

			ConnectivityDetector cd = new ConnectivityDetector(Triplestore.WIKIDATA);

			int nbFacts = wdt.getFacts();
			int nbSubjects = wdt.getSubjects();
			int nbObjects = wdt.getObjects();

			BiPartite b = new BiPartite(nbFacts, nbSubjects, nbObjects, 1);
			b.exportProcessedFile(wdt.toString());

			cd.detect(prefix+wdt.toString(), "", "");
			cd.export(wdt.toString());

			CsvFile.fileMerge(b.filename, cd.filename, wdt.toString());
		}

		for(WDT wdt : WDT.values()) {
			KolmogorovSmirnovTest ks = new KolmogorovSmirnovTest();

			getRealFictiveDistrib(wdt.toString()+"_fictive_real.csv");
			double ds = ks.kolmogorovSmirnovStatistic(realDistrib, fictiveDistrib);
			
			buff_ds += wdt.toString()+sep+ds+"\n";
		}
		File outputFile= new File("d_statistics.csv");
		FileWriter out;
		try {

			out = new FileWriter(outputFile);
			out.write(buff_ds);
			out.close();

		}catch(IOException e) {
			e.printStackTrace();
		}


	}

	public static void getRealFictiveDistrib(String path) {
		List<Double> resFictive = new ArrayList<Double>();
		List<Double> resReal = new ArrayList<Double>();

		CsvFile csvFile = new CsvFile(path);
		List<String[]> data = csvFile.getData();
		data.remove(0);
		for(String[] row : data) {
			//Change the second argument to select the right column
			Double v = convertToDouble(row,1);
			String type = row[3];
			if(type.equals("fictive")) resFictive.add(v);
			else if(type.equals("real")) resReal.add(v);

		}

		realDistrib = arrayToTab((ArrayList<Double>) resReal);
		fictiveDistrib = arrayToTab((ArrayList<Double>) resFictive);
	}

	public static List<Double> findDistribution(String path) {
		List<Double> res = new ArrayList<Double>();

		CsvFile csvFile = new CsvFile(path);
		List<String[]> data = csvFile.getData();
		data.remove(0);
		for(String[] row : data) {
			//Change the second argument to select the right column
			Double v = convertToDouble(row,1);
			String type = row[3];
			if(type.equals("fictive")) res.add(v);

		}

		return res;
	}

	private static Double convertToDouble(String[] tab, int i) {

		Double d = Double.parseDouble(tab[i]);
		return d;
	}

	public static double[] arrayToTab(ArrayList<Double> al) {
		double[] d = new double[al.size()];

		for(int i = 0; i < al.size(); i++) {
			d[i] = al.get(i);
		}

		return d;
	}

}
