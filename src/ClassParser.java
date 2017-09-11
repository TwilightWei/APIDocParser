import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.file.APIFileIO;

public class ClassParser {
	public HashMap<String, String> parse(Entry<String, String> packageUrl){
		HashMap<String, String> classUrls = new HashMap<String, String>();
		ConfigFileParser configFileParser = new ConfigFileParser();
		APIFileIO file = new APIFileIO();
		HtmlToString htmlToString = new HtmlToString();
		
		String filePath = configFileParser.getConfig("Source")+"\\APIDoc\\Classes.txt";
		Document doc = htmlToString.toString(packageUrl.getValue());
		Element  body = doc.body();
		Elements blocklists = body.select("ul.blocklist");
		for(Element blocklist:blocklists){
			Elements colFirsts = blocklist.select(".colFirst");
			for(Element colFirst:colFirsts){
				Elements hrefs = colFirst.select("a");
				for(Element href:hrefs){
					try{
						// Save into files
						String className = packageUrl.getKey()+"."+href.ownText();
						file.appendFile(filePath, className);
						classUrls.put(className, href.absUrl("href"));
					} catch(NullPointerException e){
						System.out.println(e);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return classUrls;
	}
}
