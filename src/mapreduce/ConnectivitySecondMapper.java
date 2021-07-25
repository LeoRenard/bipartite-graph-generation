package mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ConnectivitySecondMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	@Override
	public void map(LongWritable key, Text value, Mapper.Context context)
			throws IOException, InterruptedException {
		//Pattern of value prop/object	degree_of_this_object
		
		String s = value.toString();
		String[] val = s.split("	");
		
		String key_str = val[0];
		String[] key_bis = key_str.split("/");
		
		//Retrieve the prop
		key_str = key_bis[0];
		
		//We add degree of this object
		//Pattern of new key : prop/k
		key_str = key_str+"/"+val[1];
		
		
		context.write(new Text(key_str), new IntWritable(1));
	
	}



}
