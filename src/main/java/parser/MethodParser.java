package main.java.parser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		//System.out.println("---Class---");
		for(Element blocklist:blocklists){
			blocklist.select("ul.blocklist").remove();
			if(blocklist.select("table.memberSummary").size()>0 && blocklist.select("li.blocklist").size()==1){
				String apiType = new String();
				for(Element h3:blocklist.select("h3")){
					if(h3.ownText().equals("Method Summary")){
						apiType = "method";
						//System.out.println(apiType);
						//System.out.println(blocklist.select("li.blocklist").size());
						break;
					}
					else if(h3.ownText().equals("Constructor Summary")){
						apiType = "constructor";
						//System.out.println(apiType);
						//System.out.println(blocklist.select("li.blocklist").size());
						break;
					}
				}
				if(apiType == "method"){
					Elements colLasts = blocklist.select("td.colLast");
					for(Element colLast:colLasts){
						colLast.select("div.block").remove();
						String methodName = colLast.text().replace("\u00a0", " ");
						if(!methodName.contains("()")) {
							methodName = removeParaName(methodName);
						} else {
							methodName = methodName.substring(0, methodName.indexOf("("))+"()";
						}
						methodName = classUrl.getKey()+"."+methodName;
						methodUrls.put(methodName, source);
					}
					apiType = null;
				}
				else if(apiType == "constructor"){
					Elements colOnes = blocklist.select("td.colOne");
					for(Element colOne:colOnes){
						colOne.select("div.block").remove();
						String methodName = colOne.text().replace("\u00a0", " ");
						if(!methodName.contains("()")) {
							methodName = removeParaName(methodName);
						} else {
							methodName = methodName.substring(0, methodName.indexOf("("))+"()";
						}
						methodName = classUrl.getKey()+"."+methodName;
						methodUrls.put(methodName, source);
					}
					apiType = null;
				}
			}
		}
		return methodUrls;
	}
	
	public String removeParaName(String methodName) {
		String newMethodName = null;
		//System.out.println(methodName);
		String[] parameters = methodName.substring(methodName.indexOf("(")+1, methodName.indexOf(")")).split(", ");
		for (int i=0; i<parameters.length; i++) {
			parameters[i] = parameters[i].substring(0, parameters[i].lastIndexOf(" "));
		}
		newMethodName = methodName.substring(0, methodName.indexOf("(")) + "(" + String.join(", ", parameters) + ")";
		//System.out.println(newMethodName+"\n");
		return newMethodName;
	}
}
