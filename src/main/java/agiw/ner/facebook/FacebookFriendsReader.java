/**
 * 
 */
package agiw.ner.facebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.PropertyFactor;

/**
 * @author Simone
 *
 */
public class FacebookFriendsReader {

    public static void main(String[] args) {
        Map<String, List<String>> friendsMap = facebookFriendsToMap();
        for (String s : friendsMap.keySet()){
            System.out.println(s);
            System.out.println(friendsMap.get(s).toString());
            System.out.println("***************************************************************");

        }
        System.out.println(friendsMap.size());
    }


    public static Map<String, List<String>> facebookFriendsToMap() {
        Map<String, List<String>> facebookFriendsMap = new HashMap<String, List<String>>();

        //leggo dalla cartella friends e metto nella mappa
        PropertyFactor pf = new PropertyFactor();
        File friendsDir = new File(pf.getFriendsPath());
        for(File f : friendsDir.listFiles()){
            String name = f.getName().replace(".txt", "");
            List<String> friendsList = new ArrayList<String>();
            FileReader fileReader;
            try {
                fileReader = new FileReader(f);
                BufferedReader bufferReader = new BufferedReader(fileReader);
                bufferReader.readLine();
                bufferReader.readLine();    //zompo le prime due            
                String thisLine;
                while ((thisLine = bufferReader.readLine()) != null) {
                    friendsList.add(thisLine.toLowerCase());
                }  
                facebookFriendsMap.put(name, friendsList);
                bufferReader.close();
                fileReader.close();
            }
            catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }   
        return facebookFriendsMap;
    }
}