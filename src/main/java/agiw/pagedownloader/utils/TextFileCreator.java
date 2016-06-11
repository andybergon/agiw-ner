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
	public static final String MOCK_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";

	public static void createFile(String lastname, String name, String url) {
		Document retrievedDocument;
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
			retrievedDocument = Jsoup	.connect(url)
										.ignoreContentType(true)
										.userAgent(MOCK_AGENT)
										.get();

			FileOutputStream fos = new FileOutputStream(outputFilePath);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			htmlWriter = new BufferedWriter(osw);
			htmlWriter.write(retrievedDocument.toString());
			htmlWriter.close();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			System.out.println("SocketTimeoutException - Jsoup Error (?): 403 or other");
			//	e.printStackTrace();
		} catch (HttpStatusException e) {
			System.out.println("HttpStatusException - Jsoup Error (?): 403 or other");
			//	e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
