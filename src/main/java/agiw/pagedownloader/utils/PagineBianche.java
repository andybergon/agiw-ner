package agiw.pagedownloader.utils;
import java.net.*;
import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;

public class PagineBianche {
	public static void main(String[] args) throws Exception {
		String URL1= "http://www.paginebianche.it/A";
		String htm=".htm";
		for(char alphabet = 'A'; alphabet <= 'Z';alphabet++) {
			if(alphabet=='J' || alphabet=='W' || alphabet=='X' || alphabet=='Y')
				alphabet++;
			String URL=URL1+alphabet+htm;
			URLConnection connection = new URL(URL).openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			BufferedReader in  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				
				if(inputLine.contains("<b>(")){
					//System.out.println(inputLine);
					String[] arrayLine=inputLine.split("-");
					String nome1=arrayLine[0];
					String nome2=arrayLine[1];
					//System.out.println(nome1+nome2);
				
						nome1 = nome1.substring(nome1.indexOf("(") + 1);
						nome1 = nome1.substring(0, nome1.length()-1);
						
						//nome2 = nome2.substring(nome2.indexOf(" ") + 1);
						nome2 = nome2.substring(1, nome2.indexOf(")"));
						System.out.println(nome1);
						System.out.println(nome2);
				
				}
			in.close();
		}
	}
}