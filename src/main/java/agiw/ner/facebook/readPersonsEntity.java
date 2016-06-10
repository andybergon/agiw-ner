/**
 * 
 */
package agiw.ner.facebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utils.PropertyFactor;

/**
 * @author Simone
 *
 */
public class readPersonsEntity {

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
                        JSONParser parser = new JSONParser();
                        Object obj;
                        try {
                            obj = parser.parse(new FileReader(f));
                            org.json.simple.JSONObject jsonObject = (JSONObject) obj;
                            org.json.simple.JSONObject nerObject = (JSONObject) jsonObject.get("NER");
                            org.json.simple.JSONArray array = (org.json.simple.JSONArray) nerObject.get("PER");

                            for (int i = 0 ; i < array.size(); i++) {
                                String person = (String) array.get(i);
                                personsList.add(person.toLowerCase());
                            }
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
