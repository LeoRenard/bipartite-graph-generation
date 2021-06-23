package csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFile implements CsvFileInterface{

	public final static char SEPARATOR = ',';

	private File file;
	private List<String> lines;
	private List<String[] > data;

	private CsvFile() {}

	public CsvFile(File file) {
		this.file = file;
		init();
	}

	public CsvFile(String path) {
		this.file = CsvFileHelper.getResource(path);
		init();
	}

	public static void fileMerge(String f1, String f2, String filename) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter(filename+"_fictive_real.csv", true)))) {
			out.write("k,nbk,pk,type\n");
			for (String path : Arrays.asList(f1, f2))
				Files.lines(Paths.get(path)).skip(1).forEach(out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		lines = CsvFileHelper.readFile(file);

		data = new ArrayList<String[] >(lines.size());
		String sep = new Character(SEPARATOR).toString();
		for (String line : lines) {
			String[] oneData = line.split(sep);
			data.add(oneData);
		}
	}

	@Override
	public File getFile() {
		// TODO Auto-generated method stub
		return file;
	}

	@Override
	public List<String[]> getData() {
		// TODO Auto-generated method stub
		return data;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setData(List<String[]> data) {
		this.data = data;
	}

}
