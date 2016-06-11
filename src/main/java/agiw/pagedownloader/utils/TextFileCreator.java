package agiw.pagedownloader.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import utils.PropertyFactor;

public class TextFileCreator {

	public static void createFile(String lastname, String name, String url) {
		Document doc;
		String urlEncoded, outputFile;
		BufferedWriter htmlWriter;

		PropertyFactor pf = new PropertyFactor();
		
		try {
			urlEncoded = URLEncoder.encode(url, "UTF-8");
			outputFile = pf.getStoragePath() + lastname + "_" + name + "_" + urlEncoded + ".txt";

			System.out.println("Requesting: " + url);
			doc = Jsoup.connect(url).ignoreContentType(true).get();

			htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
			htmlWriter.write(doc.toString());
			htmlWriter.close();

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			System.err.println("Jsoup Error (?): 403 or other");
			e.printStackTrace();
		}
	}

}
