package agiw.pagedownloader.utils;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * Classe che pulisce dall HTML una pagina passandogli URL (ho messo che
 * restituisce il bodi ma si può impostare tutto usando jsoup aggiungete il jar)
 *
 */
public class HTMLCleaner {

	public static String getTitle(String filePath) throws IOException {
		File input = new File(filePath);
		Document doc = Jsoup.parse(input, "UTF-8", "");
		String title = doc.select("title").text();
		return title;
	}
	
	public static String getBody(String filePath) throws IOException {
		File input = new File(filePath);
		// Jsoup.connect(url).get(); quando si usa?
		Document doc = Jsoup.parse(input, "UTF-8", "");
		String body = doc.select("body").text();
		return body;
	}

	public static String[] getTitleAndBody(String filePath) throws IOException {
		File input = new File(filePath);
		Document doc = Jsoup.parse(input, "UTF-8", "");
		String title = doc.select("title").text();
		String body = doc.select("body").text();
		String[] data = {title, body};
		return data;
	}

}
