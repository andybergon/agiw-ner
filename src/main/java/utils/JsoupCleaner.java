package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class JsoupCleaner {

	public String cleanHtml(String html) {
		Document doc = Jsoup.parse(html);
		Element body = doc.body();
		String cleanedHtml = body.text();

		return cleanedHtml;
	}
}
