package main.java.parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DepMethodParser {
	public HashMap<String, String> parse(String source, String deprecatedUrl) {
		Document doc = null;
		File input = new File(deprecatedUrl);
		
		try {
			doc = Jsoup.parse(input, "UTF-8");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String, String> depMethodUrls = new HashMap<String, String>();
		Element  body = doc.body();
		Elements tables = body.select("table.deprecatedSummary");
		for(Element table:tables) {
			table.select("div.block").remove();
			table.select("span.tabEnd").remove();
			String apiType = new String();
			for(Element caption:table.select("caption")) {
				for(Element span:caption.select("span")) {
					apiType = span.ownText();
				}
			}
			Elements hrefs = table.select("a");
			for(Element href:hrefs) {
				if(apiType.contains("Methods") || apiType.contains("Constructors")) {
					depMethodUrls.put(href.ownText(), source);
				}
			}
		}
		return depMethodUrls;
	}
}
