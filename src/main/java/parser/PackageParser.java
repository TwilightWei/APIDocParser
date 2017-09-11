package main.java.parser;
import java.io.IOException;
import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.file.APIFileIO;
import main.java.html.HtmlToString;

public class PackageParser {
	public HashMap<String, String> parse(){
		HashMap<String, String> packageUrls = new HashMap<String, String>();
		ConfigFileParser configFileParser = new ConfigFileParser();
		APIFileIO file = new APIFileIO();
		HtmlToString htmlToString = new HtmlToString();
		
		String filePath = configFileParser.getConfig("Source")+"\\APIDoc\\Packages.txt";
		Document doc = htmlToString.toString(configFileParser.getConfig("APIUrl"));
		Element  body = doc.body();
		Elements overviewSummarys = body.select(".overviewSummary");
		for(Element overviewSummary:overviewSummarys){
			Elements colFirsts = overviewSummary.select(".colFirst");
			for(Element colFirst:colFirsts){
				Elements hrefs = colFirst.select("a");
				for(Element href:hrefs){
					try{
						// Save into files
						file.appendFile(filePath, href.ownText());
						packageUrls.put(href.ownText(), href.absUrl("href"));
					} catch(NullPointerException e){
						System.out.println(e);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return packageUrls;
	}
}
