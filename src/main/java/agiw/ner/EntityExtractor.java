package agiw.ner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import agiw.ner.alchemyapi.AlchemyAPIExtractor;
import agiw.ner.regex.Pattern;
import agiw.ner.regex.RegexFinder;
import utils.PropertyFactor;

public class EntityExtractor {
	public static void main(String[] args) {
		PropertyFactor pf = new PropertyFactor();

		File jsonDir = new File(pf.getJsonPath());
		File[] directories = jsonDir.listFiles();
		Arrays.sort(directories);

		File outputDir = new File(pf.getNerPath());

		if (!outputDir.exists()) {
			outputDir.mkdir();
		}

		if (directories != null) {
			for (File personDirectory : directories) {

				File outputPersonDir = new File(outputDir, personDirectory.getName());
				if (!outputPersonDir.exists()) {
					outputPersonDir.mkdir();
				}

				if (personDirectory.isDirectory()) {
					File[] personFiles = personDirectory.listFiles();
					int i = 1;
					for (File personFile : personFiles) {
						String lastName, firstName;
						String jsonFileName = personFile.getName();

						int httpIndex = jsonFileName.indexOf("http");
						String url = jsonFileName.substring(httpIndex).replace(".json", "");
						String urlDecoded = "";
						try {
							urlDecoded = URLDecoder.decode(url, "UTF-8");
							System.out.println(urlDecoded);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

//						AlchemyAPIExtractor aae = new AlchemyAPIExtractor();
//						List<NamedEntity> entities = aae.getEntitiesFromUrl(urlDecoded);
//						NER ner = new NER(entities);
//
//						ner.print();
						
						JSONParser parser = new JSONParser();
						Object obj;
						try {
							obj = parser.parse(new FileReader(personFile.getAbsolutePath()));
							org.json.simple.JSONObject jsonObject = (JSONObject) obj;
							String body = (String) jsonObject.get("html");
							
							Pattern pattern = new Pattern();
							RegexFinder rf = new RegexFinder();
							
							pattern.setAddr(rf.findRegexInString(RegexFinder.ADDRESS_REGEX, body));
							pattern.setEmail(rf.findRegexInString(RegexFinder.EMAIL_REGEX, body));
							pattern.setTel(rf.findRegexInString(RegexFinder.PHONE_REGEX, body));
							
							System.out.println(pattern.toString());

							File json = new File(outputPersonDir, String.valueOf(i));
							json.createNewFile();

							i++;
							
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}

			}
		} else {
			System.out.println("Storage Path not setted properly, it should be a directory!");
		}

	}
}
