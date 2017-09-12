package main.java.parser;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FieldParser {

	public HashMap<String, String> parse(String source, Entry<String, String> classUrl) {
		Document doc = null;
		File input = new File(classUrl.getValue());
		
		try {
			doc = Jsoup.parse(input, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String, String> fieldUrls = new HashMap<String, String>();
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
						fieldUrls.put(fieldName, source);
					}
				}
			}
		}
		return fieldUrls;
	}
}
