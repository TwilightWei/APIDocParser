package main.java;
import java.util.HashMap;
import java.util.Map.Entry;

import main.java.parser.ClassParser;
import main.java.parser.DepAPIDocParser;
import main.java.parser.FieldParser;
import main.java.parser.MethodParser;
import main.java.parser.PackageParser;

public class APIDocParser {
	public static void main(String[] args){		
		DepAPIDocParser depAPIDocParser = new DepAPIDocParser();
		PackageParser packageParser = new PackageParser();
		ClassParser classParser = new ClassParser();
		MethodParser methodParser = new MethodParser();
		FieldParser fieldParser = new FieldParser();
		HashMap<String, String> packageUrls = new HashMap<String, String>();
		HashMap<String, String> classUrls = new HashMap<String, String>();
		
		System.out.println("Start parsing deprecated APIs");
		depAPIDocParser.parse();
		System.out.println("Finish parsing deprecated APIs");
		
		System.out.println("Start parsing package APIs");
		packageUrls = packageParser.parse();
		System.out.println("Finish parsing package APIs");
		for(Entry<String, String> packageUrl : packageUrls.entrySet()){
			classUrls = classParser.parse(packageUrl);
			for(Entry<String, String> classUrl : classUrls.entrySet()){
				methodParser.parse(classUrl);
				fieldParser.parse(classUrl);
			}
		}
		System.out.println("Finish parsing APIs");
	}
}
