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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

import analyze.PowerLawAnalyzer;
import connectivity.ConnectivityDetector;
import csv.CsvFile;
import lod.Triplestore;
import lod.WDT;

public class Test {

	static String prefix = "http://www.wikidata.org/prop/direct/";

	static double[] realDistrib;
	static double[] fictiveDistrib;

	static String buff_ds = "property,d-statistics\n";
	static String buff_alpha = "property, alpha_real, alpha_fictive\n";
	static String buff_stats = "property, kmin_pkmin, kmax_pkmin, type\n";

	static String sep = ",";

	static boolean fileExists = true;

	static int nbInit = 20;

	public static void main(String[] args) {
		compute();
		powerLawMeasurement();
		statFiles();
	}

	//This method allows to generate de bipartite graph and to create the files to generate the graphs for real and fictive data
	public static void compute() {

		String P509restrictionObj = "?o <http://www.wikidata.org/prop/direct/P31> ?oo.\n"
				+ "                 FILTER EXISTS { ?oo (<http://www.wikidata.org/prop/direct/P279>*|<http://www.wikidata.org/prop/direct/P31>) <http://www.wikidata.org/entity/Q12136>}";

		for(WDT wdt : WDT.values()) {

			ConnectivityDetector cd = new ConnectivityDetector(Triplestore.WIKIDATA);

			BiPartite b = new BiPartite(prefix+wdt.toString(), "", "");
			b.exportProcessedFile(wdt.toString()+"_"+nbInit);
			//b.exportProcessedFileSubj(wdt.toString());

			cd.detect(prefix+wdt.toString(), "", "");
			//cd.detectSubj(prefix+wdt.toString(), "", "");
			cd.export(wdt.toString()+"_"+nbInit);

			CsvFile.fileMerge(b.filename, cd.filename, wdt.toString()+"_"+nbInit);
		}


		for(WDT wdt : WDT.values()) {
			KolmogorovSmirnovTest ks = new KolmogorovSmirnovTest();

			getRealFictiveDistrib(wdt.toString()+"_"+nbInit+"_fictive_real.csv",1);
			double ds = ks.kolmogorovSmirnovStatistic(realDistrib, fictiveDistrib);

			buff_ds += wdt.toString()+sep+ds+"\n";
		}
		File outputFile= new File("d_statistics"+"_"+nbInit+".csv");
		FileWriter out;
		try {

			out = new FileWriter(outputFile);
			out.write(buff_ds);
			out.close();

		}catch(IOException e) {
			e.printStackTrace();
		}

	}

	public static void powerLawMeasurement() {
		for(WDT wdt : WDT.values()) {
			
			fileExists = true;
			getRealFictiveDistrib(wdt.toString()+"_"+nbInit+"_fictive_real.csv",0);
			if(fileExists == true) {
				PowerLawAnalyzer pla = new PowerLawAnalyzer(fictiveDistrib);
				PowerLawAnalyzer pla2 = new PowerLawAnalyzer(realDistrib);
				pla.analyze();
				pla2.analyze();
				System.out.println(pla.getMinX());
				System.out.println(pla2.getMinX());
				System.out.println();
				buff_alpha += wdt.getName()+sep+pla2.getAlpha()+sep+pla.getAlpha()+"\n";

				File outputFile= new File("powerLaw"+"_"+nbInit+".csv");
				FileWriter out;
				try {

					out = new FileWriter(outputFile);
					out.write(buff_alpha);
					out.close();	

				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void statFiles() {

		for(WDT wdt : WDT.values()) {
			Map<Double, Double> resFictive = new TreeMap<Double,Double>();
			Map<Double,Double> resReal = new TreeMap<Double,Double>();

			CsvFile csvFile = new CsvFile(wdt.toString()+"_"+nbInit+"_fictive_real.csv");

			if(csvFile.exists()) {

				List<String[]> data = csvFile.getData();
				data.remove(0);
				for(String[] row : data) {
					//Change the second argument to select the right column
					Double k = convertToDouble(row,0);
					Double pk = convertToDouble(row,2);
					String type = row[3];
					if(type.equals("fictive")) resFictive.put(k,pk);
					else if(type.equals("real")) resReal.put(k,pk);

				}
				
				Map<Double, ArrayList<Double>> pk_lines_fictive = new TreeMap<Double, ArrayList<Double>>();
				Map<Double, ArrayList<Double>> pk_lines_real = new TreeMap<Double, ArrayList<Double>>();
				
				Double minKey_fictive = 10000.0;
				Double minKey_real = 10000.0;
				
				//Fictive data
				for(Entry<Double, Double> mapentry : resFictive.entrySet()) {
					
					if(pk_lines_fictive.containsKey(mapentry.getValue())) {
						ArrayList<Double> al = new ArrayList<Double>();
						al = pk_lines_fictive.get(mapentry.getValue());
						al.add(mapentry.getKey());
					}
					else {
						ArrayList<Double> al = new ArrayList<Double>();
						al.add(mapentry.getKey());
						pk_lines_fictive.put(mapentry.getValue(),al);
						
						if(mapentry.getValue() <= minKey_fictive) minKey_fictive = mapentry.getValue();
						
					}
					
				}
				
				//Real data
				for(Entry<Double, Double> mapentry : resReal.entrySet()) {
					
					if(pk_lines_real.containsKey(mapentry.getValue())) {
						ArrayList<Double> al = new ArrayList<Double>();
						al = pk_lines_real.get(mapentry.getValue());
						al.add(mapentry.getKey());
					}
					else {
						ArrayList<Double> al = new ArrayList<Double>();
						al.add(mapentry.getKey());
						pk_lines_real.put(mapentry.getValue(),al);
						
						if(mapentry.getValue() <= minKey_real) minKey_real = mapentry.getValue();
					}
					
				}
				
				List<Double> k_pkmin_fictive = new ArrayList<Double>();
				List<Double> k_pkmin_real = new ArrayList<Double>();
				
				k_pkmin_fictive = pk_lines_fictive.get(minKey_fictive);
				k_pkmin_real = pk_lines_real.get(minKey_real);
				
				Collections.sort(k_pkmin_fictive);
				Collections.sort(k_pkmin_real);
				
				Double kmin_pkmin_fictive = k_pkmin_fictive.get(0);
				Double kmax_pkmin_fictive = k_pkmin_fictive.get(k_pkmin_fictive.size()-1);
				
				Double kmin_pkmin_real = k_pkmin_real.get(0);
				Double kmax_pkmin_real = k_pkmin_real.get(k_pkmin_real.size()-1);
				

				buff_stats += wdt.getName()+sep+kmin_pkmin_fictive+sep+kmax_pkmin_fictive+sep+"fictive\n";
				buff_stats += wdt.getName()+sep+kmin_pkmin_real+sep+kmax_pkmin_real+sep+"real\n";

				File outputFile= new File("stats"+"_"+nbInit+".csv");
				FileWriter out;
				try {

					out = new FileWriter(outputFile);
					out.write(buff_stats);
					out.close();	

				}catch(IOException e) {
					e.printStackTrace();
				}

			}
			
		}
	}

	public static void getRealFictiveDistrib(String path, int col) {
		List<Double> resFictive = new ArrayList<Double>();
		List<Double> resReal = new ArrayList<Double>();

		CsvFile csvFile = new CsvFile(path);

		if(csvFile.exists()) {

			List<String[]> data = csvFile.getData();
			data.remove(0);
			for(String[] row : data) {
				//Change the second argument to select the right column
				Double v = convertToDouble(row,col);
				String type = row[3];
				if(type.equals("fictive")) resFictive.add(v);
				else if(type.equals("real")) resReal.add(v);

			}

			realDistrib = arrayToTab((ArrayList<Double>) resReal);
			fictiveDistrib = arrayToTab((ArrayList<Double>) resFictive);

		}
		else fileExists = false;

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
