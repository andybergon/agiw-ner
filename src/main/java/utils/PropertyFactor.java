package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyFactor {
	private Map<String, String> properties;

	public PropertyFactor() {
		this.properties = new HashMap<String, String>();
		this.initialize();
	}


	public static void main(String[] args) {
		PropertyFactor pf = new PropertyFactor();
		System.out.println(pf.getProperties().keySet());
		System.out.println(pf.getProperties().values());
	}
	
	public void initialize() {
		Properties properties = new Properties();

		try {
			InputStream input = new FileInputStream("config.properties");
			properties.load(input);
			for (final String name: properties.stringPropertyNames())
				this.properties.put(name, properties.getProperty(name));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getAlchemyApiKey() {
		return this.properties.get("alchemy-api-key");
	}
	
	public String getJsonPath() {
		return this.properties.get("json-path");
	}
	
	public String getNerPath() {
		return this.properties.get("ner-path");
	}
	
	public String getBingKey(){
		return this.properties.get("bingKey");
	}
	
	public String getStoragePath(){
		return this.properties.get("storagePath");
	}
	
	public String getPeoplePath(){
		return this.properties.get("peoplePath");
	}
	
	public String getJsonPagesPath(){
	    return this.properties.get("json-pages-path");
	}
	
	public String getFriendsPath(){
		return this.properties.get("friends-path");
	}
	
	public static String getJsonPathA() {
		String path = null;
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			// load a properties file
			prop.load(input);
			path = prop.getProperty("jsonPath");
			return path;
		} catch (IOException ex) {
			ex.printStackTrace();
			return path;
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
	
	public Map<String, String> getProperties() {
		return properties;
	}



	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
