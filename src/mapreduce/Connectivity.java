package mapreduce;

import org.apache.hadoop.util.ToolRunner;

public class Connectivity {

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new ConnectivityDriver(), args);
		System.exit(exitCode);
	}

}
