package main.java.parser;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MethodParser {

	public HashMap<String, String> parse(String source, Entry<String, String> classUrl) {		
		Document doc = null;
		File input = new File(classUrl.getValue());
		
		try {
			doc = Jsoup.parse(input, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String, String> methodUrls = new HashMap<String, String>();
		Element  body = doc.body();
		Elements blocklists = body.select("li.blocklist");
		for(Element blocklist:blocklists){
			if(blocklist.select("table.memberSummary").size()>0 && blocklist.select("li.blocklist").size()==1){
				String apiType = new String();
				for(Element h3:blocklist.select("h3")){
					if(h3.ownText().equals("Method Summary")){
						apiType = "method";
						break;
					}
					else if(h3.ownText().equals("Constructor Summary")){
						apiType = "constructor";
						break;
					}
				}
				if(apiType == "method"){
					Elements colLasts = blocklist.select("td.colLast");
					for(Element colLast:colLasts){
						colLast.select("div.block").remove();
						String methodName = colLast.text().replace("\u00a0", " ");
						methodName = classUrl.getKey()+"."+methodName;
						methodUrls.put(methodName, source);
					}
				}
				else if(apiType == "constructor"){
					Elements colLasts = blocklist.select("td.colOne");
					for(Element colLast:colLasts){
						colLast.select("div.block").remove();
						String methodName = colLast.text().replace("\u00a0", " ");
						methodName = classUrl.getKey()+"."+methodName;
						methodUrls.put(methodName, source);
					}
				}
			}
		}
		return methodUrls;
	}
}
