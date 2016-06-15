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
import agiw.ner.alchemyapi.exception.AlchemyException;
import agiw.ner.json.IEJsonWriter;
import agiw.ner.objects.NER;
import agiw.ner.objects.NamedEntity;
import agiw.ner.regex.NameFinder;
import agiw.ner.regex.RegexFinder;
import agiw.ner.regex.RegexPattern;
import utils.JsoupCleaner;
import utils.PropertyFactor;

public class EntityExtractor {
	public static void main(String[] args) {
		PropertyFactor pf = new PropertyFactor();
		String inputPath = pf.getJsonPath();
		String outputPath = pf.getNerPath();

		File jsonDir = new File(inputPath);
		File[] directories = jsonDir.listFiles();
		Arrays.sort(directories);

		File outputDir = new File(outputPath);

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
					List<String> emails, telephones, addresses, names, qualifications;
					File[] personFiles = personDirectory.listFiles();
					int i = 1;
					Arrays.sort(personFiles);

					for (File personJsonFile : personFiles) {

						JSONParser parser = new JSONParser();
						Object obj;

						try {
							obj = parser.parse(new FileReader(personJsonFile.getAbsolutePath()));
							org.json.simple.JSONObject jsonObject = (JSONObject) obj;
							String body = (String) jsonObject.get("html");
							String url = (String) jsonObject.get("url");

							System.out.println("json file: " + personJsonFile.getAbsolutePath());
							System.out.println("url: " + url);

							File nerJsonFile = new File(outputPersonDir, String.valueOf(i) + ".json");
							nerJsonFile.createNewFile();
							
							System.out.println("ner file:" + nerJsonFile.getAbsolutePath());

							JsoupCleaner hc = new JsoupCleaner();
							
							try {
								body = hc.cleanHtml(body);
							} catch (Exception e) {
								System.err.println("Jsoup Error, Body not cleaned. Passing dirty body to Alchemy.");
							}

							AlchemyAPIExtractor aae = new AlchemyAPIExtractor();
							List<NamedEntity> entities = new ArrayList<NamedEntity>();
							try {
								entities = aae.getEntitiesFromUrl(url);
							} catch (AlchemyException e) {
								// e.printStackTrace();
								System.err.println("No good URL, using Alchemy on saved page!");
								entities = aae.getEntitiesFromText(body);
							}
							NER ner = new NER(entities);

							// ner.print();

							RegexPattern pattern = new RegexPattern();
							RegexFinder rf = new RegexFinder();

							addresses = rf.findRegexInString(RegexFinder.ADDRESS_REGEX, body);
							pattern.setAddresses(addresses);

							emails = rf.findRegexInString(RegexFinder.EMAIL_REGEX, body);
							pattern.setEmails(emails);

							telephones = rf.findRegexInString(RegexFinder.PHONE_REGEX, body);
							pattern.setTelephones(telephones);

							qualifications = rf.findRegexInString(RegexFinder.JOB_REGEX, body);
							pattern.setQualifications(qualifications);

							names = new NameFinder().findNamesSurnamesCapital(body);
							// names = rf.findRegexNameInText(body);
							pattern.setNames(names);

							IEJsonWriter jw = new IEJsonWriter();
							jw.writeJson(nerJsonFile.getAbsolutePath(), url, ner, pattern);

							System.out.println();
							i++;

						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}

					}
				}

			}
		} else {
			System.out.println("Json Path not setted properly!");
		}
	}
}
