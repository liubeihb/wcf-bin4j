package wcf;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class Dictionary {
	private static Map<Integer,String> DICT;
	static{
		Gson gson=new GsonBuilder().registerTypeAdapter(Integer.class, new TypeAdapter<Integer>() {
			@Override
			public void write(JsonWriter out, Integer value) throws IOException {
			}

			@Override
			public Integer read(JsonReader in) throws IOException {
				if (in.peek() == JsonToken.NULL) {
					in.nextNull();
					return null;
				}
				String value=in.nextString();
				if("".equals(value)||value==null)
					return null;
				return Integer.decode(value);
			}
		}).create();
		try {
		    String json=IOUtils.toString(Dictionary.class.getClassLoader().getResourceAsStream("dict.json"));
		    DICT = gson.fromJson(json, new TypeToken<Map<Integer,String>>(){}.getType());
		} catch (Exception e) {
		    System.err.println(e);
		}
	}
	public static String getDictionary(Integer key){
	    return DICT.get(key);
	}
}
