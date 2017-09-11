package main.java.parser;
import java.io.IOException;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.file.APIFileIO;
import main.java.html.HtmlToString;

public class FieldParser {

	public void parse(Entry<String, String> classUrl) {
		ConfigFileParser configFileParser = new ConfigFileParser();
		APIFileIO file = new APIFileIO();
		HtmlToString htmlToString = new HtmlToString();
		
		String filePath = configFileParser.getConfig("Source")+"\\APIDoc\\Fields.txt";
		Document doc = htmlToString.toString(classUrl.getValue());
		Element  body = doc.body();
		Elements blocklists = body.select("li.blocklist");
		for(Element blocklist:blocklists){
			if(blocklist.select("table.memberSummary").size()>0 && blocklist.select("li.blocklist").size()==1){
				String apiType = new String();
				for(Element h3:blocklist.select("h3")){
					if(h3.ownText().equals("Field Summary")){
						apiType = "field";
						break;
					}
				}
				if(apiType == "field"){
					Elements colLasts = blocklist.select("td.colLast");
					for(Element colLast:colLasts){
						colLast.select("div.block").remove();
						String fieldName = colLast.text().replace("\u00a0", " ");
						fieldName = classUrl.getKey()+"."+fieldName;
						try {
							file.appendFile(filePath, fieldName);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
