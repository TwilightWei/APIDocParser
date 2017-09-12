package main.java.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	public String readConfig(String configPath, String key) {
		String value = new String();
		try {
			File configFile = new File(configPath);
		    FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
		    props.load(reader);
		    value = props.getProperty(key);
		    reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return value;
	}
}
