import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.file.APIFileIO;

// TOFIX: better naming mechanism
public class DepAPIDocParser {
	public void parse(){
		ConfigFileParser configFileParser = new ConfigFileParser();
		HtmlToString htmlToString = new HtmlToString();
		Document doc = htmlToString.toString(configFileParser.getConfig("DreAPIUrl"));
		Element  body = doc.body();
		Elements tables = body.select("table.deprecatedSummary");
		for (Element table:tables){
			APIFileIO file = new APIFileIO();
			String filePath = new String();
			table.select("div.block").remove();
			table.select("span.tabEnd").remove();
			for (Element caption:table.select("caption")){
				// Use caption as file name
				for (Element fileName:caption.select("span")){
					filePath = configFileParser.getConfig("Source")+"\\APIDoc\\"+fileName.ownText()+".txt";
					file.createFile(filePath);
				}
			}
			Elements hrefs = table.select("a");
			for (Element href:hrefs){
				try{
					// Save into files
					file.appendFile(filePath, href.ownText());
				} catch(NullPointerException e){
					System.out.println(e);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}
