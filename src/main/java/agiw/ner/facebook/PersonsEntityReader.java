/**
 * 
 */
package agiw.ner.facebook;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utils.PropertyFactor;

/**
 * @author Simone
 *
 */
public class PersonsEntityReader {

    public static void main(String[] args) {
        Map<String, List<String>> perMap = perToMap();
        for (String s : perMap.keySet()){
            System.out.println(s);
            System.out.println(perMap.get(s).toString());
            System.out.println("***************************************************************");

        }
        System.out.println(perMap.size());
    }

    public static Map<String, List<String>> perToMap() {
        Map<String, List<String>> perMap = new HashMap<String, List<String>>();
        PropertyFactor pf = new PropertyFactor();
        File perDir = new File(pf.getNerPath());
        for(File dir : perDir.listFiles()){
            if(!dir.getName().equals(".DS_Store")){
                String name = dir.getName();
                List<String> personsList = new ArrayList<String>();
                for(File f : dir.listFiles()){ 
                    if(!f.getName().equals(".DS_Store")){
                        org.json.simple.parser.JSONParser parser = new JSONParser();
                        Object obj;
                        try {
                        	FileReader fileReader = new FileReader(f.getAbsolutePath());
                            obj = parser.parse(fileReader);
                            org.json.simple.JSONObject jsonObject = (JSONObject) obj;
                            org.json.simple.JSONObject nerObject = (JSONObject) jsonObject.get("NER");
                            org.json.simple.JSONArray arrayPer = (org.json.simple.JSONArray) nerObject.get("PER");

                            for (int i = 0 ; i < arrayPer.size(); i++) {
                                String person = (String) arrayPer.get(i);
                                personsList.add(person.toLowerCase());
                            }
                            
                            org.json.simple.JSONObject regexObject = (JSONObject) jsonObject.get("PATTERN");
                            org.json.simple.JSONArray arrayName = (org.json.simple.JSONArray) regexObject.get("name");
                            for (int i = 0 ; i < arrayName.size(); i++) {
                                String person = (String) arrayName.get(i);
                                personsList.add(person.toLowerCase());
                            }
                            fileReader.close();
                            
                        }
                        catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (ParseException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    perMap.put(name, personsList);
                }
            }
        }
        return perMap;
    }
}
