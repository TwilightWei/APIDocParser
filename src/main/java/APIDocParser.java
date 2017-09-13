package main.java;
import java.util.HashMap;
import java.util.Map.Entry;

import main.java.config.ConfigReader;
import main.java.file.FileIO;
import main.java.parser.ClassParser;
import main.java.parser.DepClassParser;
import main.java.parser.DepFieldParser;
import main.java.parser.DepMethodParser;
import main.java.parser.FieldParser;
import main.java.parser.MethodParser;
import main.java.parser.PackageParser;

public class APIDocParser {
	public static void main(String[] args) {
		ConfigReader configReader = new ConfigReader();
		PackageParser packageParser = new PackageParser();
		ClassParser classParser = new ClassParser();
		MethodParser methodParser = new MethodParser();
		FieldParser fieldParser = new FieldParser();
		DepClassParser depClassParser = new DepClassParser();
		DepMethodParser depMethodParser = new DepMethodParser();
		DepFieldParser depFieldParser = new DepFieldParser();
		
		HashMap<String, String> packageUrls = new HashMap<String, String>();
		HashMap<String, String> classUrls = new HashMap<String, String>();
		HashMap<String, String> methodUrls = new HashMap<String, String>();
		HashMap<String, String> fieldUrls = new HashMap<String, String>();
		HashMap<String, String> depClassUrls = new HashMap<String, String>();
		HashMap<String, String> depMethodUrls = new HashMap<String, String>();
		HashMap<String, String> depFieldUrls = new HashMap<String, String>();
		String source = new String();	
		String deprecatedUrl = new String();
		String summaryUrl = new String();
		
		final String configPath = "D:\\Users\\user\\git\\APIDocParser\\src\\config.properties";
		source = configReader.readConfig(configPath, "source");
		summaryUrl = source + "\\overview-summary.html";
		deprecatedUrl = source + "\\deprecated-list.html";
		
		FileIO fileIO = new FileIO(source);
		fileIO.clearFolder("\\APIData");
		
		System.out.println("Start parsing deprecated APIs");
		depClassUrls.putAll(depClassParser.parse(source, deprecatedUrl));
		depMethodUrls.putAll(depMethodParser.parse(source, deprecatedUrl));
		depFieldUrls.putAll(depFieldParser.parse(source, deprecatedUrl));
		System.out.println("Finish parsing deprecated APIs");
		
		System.out.println("Start writing deprecated API files");		
		for(Entry<String, String> depClassUrl : depClassUrls.entrySet()){
			fileIO.writeString("\\APIData\\DeprecatedClass", depClassUrl.getKey());
		}
		
		for(Entry<String, String> depMethodUrl : depMethodUrls.entrySet()){
			fileIO.writeString("\\APIData\\DeprecatedMethod", depMethodUrl.getKey());
		}
		
		for(Entry<String, String> depFieldUrl : depFieldUrls.entrySet()){
			fileIO.writeString("\\APIData\\DeprecatedField", depFieldUrl.getKey());
		}
		System.out.println("Finish writing deprecated API files");
		
		System.out.println("Start parsing APIs");
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
		System.out.println("Finish parsing APIs");
		
		System.out.println("Start writing API files");
		for(Entry<String, String> packageUrl : packageUrls.entrySet()){
			fileIO.writeString("\\APIData\\Package", packageUrl.getKey());
		}
		
		for(Entry<String, String> classUrl : classUrls.entrySet()){
			fileIO.writeString("\\APIData\\Class", classUrl.getKey());
		}
		
		for(Entry<String, String> methodUrl : methodUrls.entrySet()){
			fileIO.writeString("\\APIData\\Method", methodUrl.getKey());
		}
		
		for(Entry<String, String> fieldUrl : fieldUrls.entrySet()){
			fileIO.writeString("\\APIData\\Field", fieldUrl.getKey());
		}
		System.out.println("Finish writing API files");
	}
}
