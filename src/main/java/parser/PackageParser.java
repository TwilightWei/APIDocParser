package main.java.parser;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PackageParser {
	
	public HashMap<String, String> parse(String source, String summaryUrl){
		Document doc = null;
		File input = new File(summaryUrl);
		
		try {
			doc = Jsoup.parse(input, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String, String> packageUrls = new HashMap<String, String>();
		Element body = doc.body();
		Elements overviewSummarys = body.select(".overviewSummary");
		for(Element overviewSummary:overviewSummarys){
			Elements colFirsts = overviewSummary.select(".colFirst");
			for(Element colFirst:colFirsts){
				Elements hrefs = colFirst.select("a");
				for(Element href:hrefs){
					String packagePath = href.attr("href").replace("/", "\\");
					packageUrls.put(href.ownText(), source + "\\" + packagePath);
				}
			}
		}
		return packageUrls;
	}
}
