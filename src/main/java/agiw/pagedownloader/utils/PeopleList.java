package agiw.pagedownloader.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author chiara
 *
 */
public class PeopleList {
	final static int PEOPLE_NUMBER = 600;
	
	public static List<String> peopleList(String pathFile) throws IOException{
		List<String> nameList = new ArrayList<String>();
		FileReader fileReader = new FileReader(pathFile);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		for( int i=0; i<PEOPLE_NUMBER; i++){
			String line = bufferReader.readLine();
			nameList.add(line);			
		}
		return nameList;
	}
}
