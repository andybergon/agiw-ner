package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyFactor {
	public static void main(String[] args) {
		PropertyFactor pf = new PropertyFactor();
		System.out.println(pf	.getProperties()
								.keySet());
		System.out.println(pf	.getProperties()
								.values());
	}

	private Map<String, String> properties;

	public PropertyFactor() {
		this.properties = new HashMap<String, String>();
		this.initialize();
	}

	public void initialize() {
		Properties properties = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			properties.load(input);
			for (final String name : properties.stringPropertyNames())
				this.properties.put(name, properties.getProperty(name));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getAlchemyApiKey() {
		return this.properties.get("alchemy-api-key");
	}
	
	public String getJsonPathAmerica() {
		return this.properties.get("json-path-america");
	}

	public String getJsonPath() {
		return this.properties.get("json-path");
	}

	public String getNerPath() {
		return this.properties.get("ner-path");
	}

	public String getBingKey() {
		return this.properties.get("bingKey");
	}

	public String getStoragePath() {
		return this.properties.get("storagePath");
	}

	public String getPeoplePath() {
		return this.properties.get("peoplePath");
	}
	
	public String getStructurePath() {
		return this.properties.get("structurePath");
	}

	public String getFriendsPath() {
		return this.properties.get("friends-path");
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
