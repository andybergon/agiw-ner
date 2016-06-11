package agiw.pagedownloader.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import utils.PropertyFactor;

public class TextFileCreator {

	public static void createFile(String lastname, String name, String url) {
		Document doc;
		String urlEncoded, outputFolderPath, outputFilePath;
		BufferedWriter htmlWriter;

		PropertyFactor pf = new PropertyFactor();

		try {
			outputFolderPath = pf.getStoragePath();
			File outputFolder = new File(outputFolderPath);
			
			if (!outputFolder.exists()) {
				outputFolder.mkdirs();
			}
			
			urlEncoded = URLEncoder.encode(url, "UTF-8");
			outputFilePath = outputFolderPath + lastname + "_" + name + "_" + urlEncoded + ".txt";

			System.out.println("Requesting: " + url);
			doc = Jsoup.connect(url).ignoreContentType(true).get();

			FileOutputStream fos = new FileOutputStream(outputFilePath);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			htmlWriter = new BufferedWriter(osw);
			htmlWriter.write(doc.toString());
			htmlWriter.close();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			System.err.println("SocketTimeoutException - Jsoup Error (?): 403 or other");
			//	e.printStackTrace();
		} catch (HttpStatusException e) {
			System.err.println("HttpStatusException - Jsoup Error (?): 403 or other");
			//	e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
