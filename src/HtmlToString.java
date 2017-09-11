import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class HtmlToString {
	public Document toString(String url){
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		return doc;
	}
}
