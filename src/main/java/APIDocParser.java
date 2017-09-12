package main.java;
import java.util.HashMap;
import java.util.Map.Entry;

import main.java.config.ConfigReader;
import main.java.file.FileIO;
import main.java.parser.ClassParser;
import main.java.parser.FieldParser;
import main.java.parser.MethodParser;
import main.java.parser.PackageParser;

public class APIDocParser {
	public static void main(String[] args){	
		ConfigReader configReader = new ConfigReader();
		PackageParser packageParser = new PackageParser();
		ClassParser classParser = new ClassParser();
		MethodParser methodParser = new MethodParser();
		FieldParser fieldParser = new FieldParser();
		
		HashMap<String, String> packageUrls = new HashMap<String, String>();
		HashMap<String, String> classUrls = new HashMap<String, String>();
		HashMap<String, String> methodUrls = new HashMap<String, String>();
		HashMap<String, String> fieldUrls = new HashMap<String, String>();
		String source = new String();	
		String summaryUrl = new String();
		
		final String configPath = "D:\\Users\\user\\git\\APIDocParser\\src\\config.properties";
		source = configReader.readConfig(configPath, "source");
		summaryUrl = source + "\\overview-summary.html";		
		
		System.out.println("Start parsing package APIs");
		
		packageUrls.putAll(packageParser.parse(source, summaryUrl));
		
		for(Entry<String, String> packageUrl : packageUrls.entrySet()){
			String pSource = packageUrl.getValue().substring(0, packageUrl.getValue().lastIndexOf("\\"));
			classUrls.putAll(classParser.parse(pSource, packageUrl));
			for(Entry<String, String> classUrl : classUrls.entrySet()){
				String cSource = classUrl.getValue().substring(0, classUrl.getValue().lastIndexOf("\\"));
				methodUrls.putAll(methodParser.parse(cSource, classUrl));
				fieldUrls.putAll(fieldParser.parse(cSource, classUrl));
			}
		}
		System.out.println("Finish parsing package APIs");
		
		FileIO fileIO = new FileIO(source);
		fileIO.clearFolder("\\APIData");
		
		for(Entry<String, String> packageUrl : packageUrls.entrySet()){
			fileIO.writeString("\\APIData\\Package", packageUrl.getValue());
		}
		
		for(Entry<String, String> classUrl : classUrls.entrySet()){
			fileIO.writeString("\\APIData\\Class", classUrl.getValue());
		}
		
		for(Entry<String, String> methodUrl : methodUrls.entrySet()){
			fileIO.writeString("\\APIData\\Method", methodUrl.getValue());
		}
		
		for(Entry<String, String> fieldUrl : fieldUrls.entrySet()){
			fileIO.writeString("\\APIData\\Field", fieldUrl.getValue());
		}
		
		/*DepAPIDocParser depAPIDocParser = new DepAPIDocParser();*/

	}
}
