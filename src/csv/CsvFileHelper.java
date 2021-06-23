package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHelper {

	public static String getResourcePath(String fileName) {
		final File f = new File("");
		final String dossierPath = f.getAbsolutePath() + File.separator + fileName;
		return dossierPath;
	}

	public static File getResource(String fileName) {
		final String completeFileName = getResourcePath(fileName);
		File file = new File(completeFileName);
		return file;
	}

	public static List<String> readFile(File file) {

		List<String> result = new ArrayList<String>();

		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				result.add(line);
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
