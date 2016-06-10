package agiw.ner.regex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NameFinder {
	public static void main(String[] args) {
		String text = "Andrea Bergonzo e luca massuda e lombardi Chiara";
		
		NameFinder nf = new NameFinder();
		List<String> foundNames = nf.findNamesInText(text);
		System.out.println(foundNames.toString());
		
	}
	
	private static final String NAMES_PATH = "italian_names.txt";

	private List<String> names;

	public NameFinder() {
		this.names = new ArrayList<String>();
		this.initialize();
	}

	private void initialize() {
		try {
			File namesFile = new File(getClass().getClassLoader().getResource(NAMES_PATH).getFile());
			Scanner s = new Scanner(namesFile);
			while (s.hasNext()) { // s.nextLine()
				this.names.add(s.next().toLowerCase()); // s.hasNextLine()
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> findNamesInText(String text) {
		List<String> foundNames = new ArrayList<String>();
		
		
		return foundNames;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}
	
}
