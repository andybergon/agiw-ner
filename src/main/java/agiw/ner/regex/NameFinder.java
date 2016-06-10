package agiw.ner.regex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class NameFinder {
	public static void main(String[] args) {
		String text = "Andrea Bergonzo e luca massuda e lombardi Chiara";

		NameFinder nf = new NameFinder();
		List<String> foundNames = nf.findNamePairs(text);
		//		List<String> foundNames = nf.findNamesInText(text);
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

	public List<String> findNames(String text) {
		List<String> foundNames = new ArrayList<String>();
		text = text.toLowerCase();

		for (String name : this.names) {
			if (text.contains(name)) {
				foundNames.add(name);
			}
		}

		removeDuplicateFromList(foundNames);

		return foundNames;
	}

	public List<String> findNamePairs(String text) {
		List<String> foundNamePairs = new ArrayList<String>();
		List<String> names = this.names;
		text = text.toLowerCase();

		for (String name : names) {
			String nameToSearch = name + " ";
			int index = text.indexOf(nameToSearch);
			if (index != -1) {
				String textSubstring = text.substring(index + nameToSearch.length());
				for (String nameInner : names) {
					if (name != nameInner && textSubstring.startsWith(nameInner)) {
						foundNamePairs.add(name + " " + nameInner);
					}
				}
			}
		}

		removeDuplicateFromList(foundNamePairs);

		return foundNamePairs;
	}

	public List<String> findNamePairsInTextNotOpt(String text) {
		List<String> foundNamePairs = new ArrayList<String>();
		text = text.toLowerCase();
		List<String> pairs = getNamePairs(this.names);

		for (String name : pairs) {
			if (text.contains(name)) {
				foundNamePairs.add(name);
			}
		}

		removeDuplicateFromList(foundNamePairs);

		return foundNamePairs;
	}

	public static List<String> getNamePairs(List<String> names) {
		List<String> couples = new ArrayList<String>();

		for (String name1 : names) {
			for (String name2 : names) {
				if (!name1.equals(name2)) {
					couples.add(name1 + " " + name2);
					couples.add(name2 + " " + name1);
				}
			}
		}

		return couples;
	}

	private static void removeDuplicateFromList(List<String> list) {
		Set<String> setItems = new LinkedHashSet<String>(list);
		list.clear();
		list.addAll(setItems);
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

}
