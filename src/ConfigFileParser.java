import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileParser {
	private static String config = "D:\\Users\\user\\workspace\\APIDocParser\\src\\config.properties";
	
	protected String getConfig(String key){
		File configFile = new File(config);
		String value = new String();
		try {
		    FileReader reader = new FileReader(configFile);
		    Properties props = new Properties();
		    props.load(reader);
		    value = props.getProperty(key);
		    reader.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		return value;
	}
}
