package main.java.parser;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ClassParser {	
	public HashMap<String, String> parse(String source, Entry<String, String> packageUrl){
		Document doc = null;
		File input = new File(packageUrl.getValue());
		
		try {
			doc = Jsoup.parse(input, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String, String> classUrls = new HashMap<String, String>();
		Element  body = doc.body();
		Elements blocklists = body.select("ul.blocklist");
		for(Element blocklist:blocklists){
			Elements colFirsts = blocklist.select(".colFirst");
			for(Element colFirst:colFirsts){
				Elements hrefs = colFirst.select("a");
				for(Element href:hrefs){
					String className = packageUrl.getKey()+"."+href.ownText();
					String classPath = href.attr("href").replace("/", "\\");
					if(!classPath.contains("https:") && !classPath.contains("http:")) { // Filter out unwanted links
						classUrls.put(className, source + "\\" + classPath);
					} else {
						System.out.println(source + "\\" + classPath);
					}
				}
			}
		}
		return classUrls;
	}
}
