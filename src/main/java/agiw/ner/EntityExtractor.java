package agiw.ner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import agiw.ner.alchemyapi.AlchemyAPIExtractor;
import agiw.ner.json.IEJsonWriter;
import agiw.ner.objects.NER;
import agiw.ner.objects.NamedEntity;
import agiw.ner.regex.RegexFinder;
import agiw.ner.regex.RegexPattern;
import utils.JsoupCleaner;
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
					Arrays.sort(personFiles);

					for (File personFile : personFiles) {

						JSONParser parser = new JSONParser();
						Object obj;
						try {
							obj = parser.parse(new FileReader(personFile.getAbsolutePath()));
							org.json.simple.JSONObject jsonObject = (JSONObject) obj;
							String body = (String) jsonObject.get("html");
							String url = (String) jsonObject.get("url");

							System.out.println("old file: " + personFile.getAbsolutePath());
							System.out.println("url: " + url);

							File json = new File(outputPersonDir, String.valueOf(i) + ".json");
							json.createNewFile();

							System.out.println("new file:" + json.getAbsolutePath());
							System.out.println();

							AlchemyAPIExtractor aae = new AlchemyAPIExtractor();
							//						List<NamedEntity> entities = aae.getEntitiesFromUrl(url);
							List<NamedEntity> entities = new ArrayList<NamedEntity>();
							NER ner = new NER(entities);

							//ner.print();

							JsoupCleaner hc = new JsoupCleaner();
							body = hc.cleanHtml(body);

							if (i == 12)
								System.out.println(body + "\n\n\n\n\n\n");

							RegexPattern pattern = new RegexPattern();
							RegexFinder rf = new RegexFinder();

							pattern.setAddr(rf.findRegexInString(RegexFinder.ADDRESS_REGEX, body));
							pattern.setEmail(rf.findRegexInString(RegexFinder.EMAIL_REGEX, body));
							pattern.setTel(rf.findRegexInString(RegexFinder.PHONE_REGEX, body));
							pattern.setQualification(rf.findRegexInString(RegexFinder.JOB_REGEX, body));

							IEJsonWriter jw = new IEJsonWriter();
							jw.writeJson(json.getAbsolutePath(), url, ner, pattern);

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
