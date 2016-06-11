package agiw.ner.json;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import agiw.ner.objects.NER;
import agiw.ner.regex.RegexPattern;

public class IEJsonWriter {

	public IEJsonWriter() {

	}

	public void writeJson(String path, String url, NER ner, RegexPattern pattern) {
		JSONObject obj = new JSONObject();
		obj.put("url", url);

		JSONObject nerObj = new JSONObject();

		nerObj.put("PER", ner.getPer());
		nerObj.put("ORG", ner.getOrg());
		nerObj.put("LOC", ner.getLoc());

		obj.put("NER", nerObj);

		JSONObject patternObj = new JSONObject();

		patternObj.put("email", pattern.getEmails());
		patternObj.put("tel", pattern.getTelephones());
		patternObj.put("addr", pattern.getAddresses());
		patternObj.put("name", pattern.getNames()); // TODO: check
		patternObj.put("qualification", pattern.getQualifications());

		obj.put("PATTERN", patternObj);

		try {
			FileWriter file = new FileWriter(path);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(obj);

			file.write(prettyJson);
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
