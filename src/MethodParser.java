import java.io.IOException;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.file.APIFileIO;

public class MethodParser {

	public void parse(Entry<String, String> classUrl) {
		ConfigFileParser configFileParser = new ConfigFileParser();
		APIFileIO file = new APIFileIO();
		HtmlToString htmlToString = new HtmlToString();
		
		String filePath = configFileParser.getConfig("Source")+"\\APIDoc\\Methods.txt";
		Document doc = htmlToString.toString(classUrl.getValue());
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
						try {
							file.appendFile(filePath, methodName);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else if(apiType == "constructor"){
					Elements colLasts = blocklist.select("td.colOne");
					for(Element colLast:colLasts){
						colLast.select("div.block").remove();
						String methodName = colLast.text().replace("\u00a0", " ");
						methodName = classUrl.getKey()+"."+methodName;
						try {
							file.appendFile(filePath, methodName);
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
