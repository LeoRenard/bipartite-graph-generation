package mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ConnectivityDriver extends Configured implements Tool{

	 //private static final String OUTPUT_PATH = "intermediate_output";
	
	public int run(String[] args) throws Exception{
		if (args.length != 2) {
			System.out.printf("Usage: %s <INPUT> <OUTPUT>\n", getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.out);
			return -1;
		}
		
		//Job 1
		Configuration conf = getConf();
		FileSystem fs = FileSystem.get(conf);
		Job job = new Job(conf,"Job 1");
		job.setJarByClass(ConnectivityDriver.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path("intermediate_output"));
		
		job.setMapperClass(ConnectivityFirstMapper.class);
		job.setReducerClass(ConnectivityFirstReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.waitForCompletion(true);
		
		//Job 2
		Configuration conf2 = getConf();
		Job job2 = new Job(conf,"Job 2");
		job2.setJarByClass(ConnectivityDriver.class);
		
		FileInputFormat.addInputPath(job2, new Path("intermediate_output"));
		FileOutputFormat.setOutputPath(job2, new Path(args[1]));
		
		job2.setMapperClass(ConnectivitySecondMapper.class);
		job2.setReducerClass(ConnectivitySecondReducer.class);
		
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(IntWritable.class);
		
		if(job2.waitForCompletion(true)) {
			return job.waitForCompletion(true) ? 0 : 1;
		}
		
		return 0;
	}

}
