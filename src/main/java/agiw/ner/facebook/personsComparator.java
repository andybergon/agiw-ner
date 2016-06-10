/**
 * 
 */
package agiw.ner.facebook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Simone
 *
 */
public class personsComparator {

    public static void main(String[] args) {
        System.out.println(FacebookNerCounter());
        System.out.println(FacebookOnlyCounter());
        System.out.println(NerOnlyCounter());
    }

    /* conta quanti nomi in entrambi gli insiemi 
     * restituisce una mappa <persona, contatore> (il calcolo va fatto per CIASCUNA persona, quindi memorizzo in una mappa per avere i dati di ogni persona)*/
    public static Map<String, Integer> FacebookNerCounter(){ 
        Map<String, Integer> result = new HashMap<String, Integer>();
        Map<String, List<String>> facebookMap = FacebookFriendsReader.facebookFriendsToMap();
        Map<String, List<String>> nerMap = PersonsEntityReader.perToMap();
        int count = 0;
        for(String person : nerMap.keySet()){
            for(String personEntity : nerMap.get(person)){
                if(facebookMap.get(person)!=null)
                    if(facebookMap.get(person).contains(personEntity)){
                        count++;
                    }
            }
            result.put(person, count);
        }
        return result;
    }

    /* conta quanti nomi presenti solo su Facebook */
    public static Map<String, Integer> FacebookOnlyCounter(){
        Map<String, Integer> result = new HashMap<String, Integer>();
        Map<String, List<String>> facebookMap = FacebookFriendsReader.facebookFriendsToMap();
        Map<String, Integer> facebookNer = FacebookNerCounter();
        for(String person : facebookNer.keySet()){
            int commonNamesNumber = facebookNer.get(person); //in comune
            result.put(person, facebookMap.get(person).size()-commonNamesNumber); //quelli totali su Fb - quelli in comune
        }
        return result;
    }

    /* quanti nomi in entrambi solo su Ner */
    public static Map<String, Integer> NerOnlyCounter(){ 
            Map<String, Integer> result = new HashMap<String, Integer>();
            Map<String, List<String>> nerMap = PersonsEntityReader.perToMap();
            Map<String, Integer> facebookNer = FacebookNerCounter();
            for(String person : facebookNer.keySet()){
                int commonNamesNumber = facebookNer.get(person); //in comune
                result.put(person, nerMap.get(person).size()-commonNamesNumber); //quelli totali su Fb - quelli in comune
            }
            return result;
        }
}
