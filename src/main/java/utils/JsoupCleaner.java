package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupCleaner {

	public String cleanHtml(String html) {
		Document doc = Jsoup.parse(html);
		String parsedHtml = doc	.body()
								.text();

		return parsedHtml;
	}
}
