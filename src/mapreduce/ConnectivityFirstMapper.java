package mapreduce;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONArray;
import org.json.JSONObject;

public class ConnectivityFirstMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	@Override
	public void map(LongWritable key, Text value, Mapper.Context context)
			throws IOException, InterruptedException {

		String s = value.toString();
		if(!s.equals("[") && !s.equals("]")) {
			String[] tuple = s.split("\\n");


			for(int i = 0; i<tuple.length; i++) {

				//With real pattern

				JSONObject jsonobj = new JSONObject(tuple[i]);

				if(jsonobj.has("claims")) {
					JSONObject jo = jsonobj.getJSONObject("claims");
					Iterator<String> keys = jo.keys();

					while(keys.hasNext()) {
						String k = keys.next();
						JSONArray ja = (JSONArray) jo.get(k);

						for(int j = 0; j < ja.length(); j++) {
							JSONObject jo_bis = ja.getJSONObject(j);

							if(jo_bis.has("mainsnak")) {
								JSONObject jo2 = jo_bis.getJSONObject("mainsnak");

								if(jo2.getString("snaktype").equals("value")) {

									if(jo2.has("datavalue")) {
										JSONObject jo3 = jo2.getJSONObject("datavalue");

										if(jo3.optJSONObject("value") != null) {

											//if(jo3.has("value")) {
												JSONObject jo4 = jo3.getJSONObject("value");

												if(jo4.has("numeric-id")) {
													int objId_str = (int) jo4.get("numeric-id");
													String objId = Integer.toString(objId_str);
													k = k+"/"+objId;

													context.write(new Text(k), new IntWritable(1));
												//}

											}

										}

									}

								}

							}

						}

					}

				}

			}



		}
	}

}



