package agiw.ner.facebook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class personsComparator {

    public static void main(String[] args) {
        Map<String, Integer> facebookNerCounter = FacebookNerCounter();
        Map<String, Integer> facebookOnlyCounter = FacebookOnlyCounter();
        Map<String, Integer> nerOnlyCounter = NerOnlyCounter();
        for (String s : facebookNerCounter.keySet()) {
            System.out.println(s +" ha in entrambi gli insiemi "+ facebookNerCounter.get(s) + " nomi.");
            System.out.println(s +" ha soltanto nel set di Facebook "+ facebookOnlyCounter.get(s) + " nomi.");
            System.out.println(s +" ha soltanto nel set di NER "+ nerOnlyCounter.get(s) + " nomi.");
        }
    }

    /* conta quanti nomi in entrambi gli insiemi 
     * restituisce una mappa <persona, contatore> (il calcolo va fatto per CIASCUNA persona, quindi memorizzo in una mappa per avere i dati di ogni persona)*/
    public static Map<String, Integer> FacebookNerCounter(){ 
        Map<String, Integer> result = new HashMap<String, Integer>();
        Map<String, List<String>> facebookMap = FacebookFriendsReader.facebookFriendsToMap();
        Map<String, List<String>> nerMap = PersonsEntityReader.perToMap();
        for(String person : nerMap.keySet()){
            int count = 0;
            for(String personEntity : nerMap.get(person)){
                if(facebookMap.get(person)!=null)
                    if(facebookMap.get(person).contains(personEntity)){
                        //System.out.println(personEntity);
                        count++;
                    }
                //System.out.println(personEntity);
                if(personEntity.split(" ").length > 1){
                    String personInverted = personEntity.split(" ", 2)[1]+" "+personEntity.split(" ", 2)[0];
                    if(facebookMap.get(person)!=null)
                        if(facebookMap.get(person).contains(personInverted)){
                            //System.out.println(personInverted);
                            count++;
                        }
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
            //System.out.println("Facebook map size"+facebookMap.get(person).size()+"  "+person.toString());
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
            //System.out.println("Ner map size"+nerMap.get(person).size()+"  "+person.toString());
            int commonNamesNumber = facebookNer.get(person); //in comune
            result.put(person, nerMap.get(person).size()-commonNamesNumber); //quelli totali su Fb - quelli in comune
        }
        return result;
    }
}
