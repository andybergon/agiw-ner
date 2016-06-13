package agiw.ner.regex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameFinder {
	public static void main(String[] args) {
		String text = "Bergonzo Bacco e Andrea Bergonzo e massuda luca, Lombardi Chiara anche detta chiara Lombardi.";

		NameFinder nf = new NameFinder();
		List<String> foundNames = nf.findNamesSurnamesCapital(text);
		//List<String> foundNames = nf.findNamesSurnames(text);
		//List<String> foundNames = nf.findNamePairs(text);
		//List<String> foundNames = nf.findNamesInText(text);
		System.out.println(foundNames.toString());

	}

	private static final String NAMES_PATH = "italian_names.txt";
	private static final String SURNAMES_PATH = "italian_surnames.txt";

	private List<String> names; // 1.5k
	private List<String> surnames; // 12.8k

	public NameFinder() {
		this.names = new ArrayList<String>();
		this.surnames = new ArrayList<String>();
		this.initializeNames();
		this.initializeSurnames();
	}

	private void initializeNames() {
		try {
			File namesFile = new File(getClass().getClassLoader()
												.getResource(NAMES_PATH)
												.getFile());

			Scanner s = new Scanner(namesFile);

			// s.nextLine() s.next()
			// s.hasNextLine() s.next()
			while (s.hasNextLine()) {
				this.names.add(s.nextLine()
								.toLowerCase());
			}

			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initializeSurnames() {
		try {
			File surnamesFile = new File(getClass()	.getClassLoader()
													.getResource(SURNAMES_PATH)
													.getFile());

			Scanner s = new Scanner(surnamesFile);

			// s.nextLine() s.next()
			// s.hasNextLine() s.next()
			while (s.hasNextLine()) {
				this.surnames.add(s	.nextLine()
									.toLowerCase());
			}

			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/* METHODS */
	public List<String> findNamesSurnamesCapital(String text) {
		List<String> foundNamesSurnames = new ArrayList<String>();
		List<String> namesSurnames = new ArrayList<String>();
		List<String> surnamesNames = new ArrayList<String>();
		List<String> names = firstLetterToUppercase(this.names);
		List<String> surnames = firstLetterToUppercase(this.surnames);

		namesSurnames = findCapitalInText(text, names, surnames);
		surnamesNames = findCapitalInText(text, surnames, names);
		
		foundNamesSurnames.addAll(namesSurnames);
		foundNamesSurnames.addAll(surnamesNames);

		return foundNamesSurnames;
	}
	
	private List<String> findCapitalInText(String text, List<String> l1, List<String> l2) {
		int index, nextWordEndIndex;
		String nextWord;
		List<String> foundNamesSurnames = new ArrayList<String>();

		
		for (String antecedent : l1) {
			index = text.indexOf(antecedent);
			if (index != -1) {
				// la parola seguente deve finire con uno spazio, potrebbe essere virgola o punto
				nextWordEndIndex = text.indexOf(" ", index + antecedent.length() + 1);
				if (nextWordEndIndex != -1) {
					nextWord = text.substring(index + antecedent.length(), nextWordEndIndex);
					nextWord = nextWord.trim();
					if (!nextWord.equals("")) {
						for (String consequent : l2) {
							if (nextWord.equals(consequent)) {
								foundNamesSurnames.add(antecedent + " " + consequent);
								System.out.println(antecedent + " " + consequent);
								nextWord = "";
							}
						}
					}
				}
			}
		}
		
		removeDuplicateFromList(foundNamesSurnames);

		return foundNamesSurnames;
		
	}

	//		
	//		for (String name : names) {
	//			index = text.indexOf(name);
	//			if (index != -1) {
	//				//				System.out.println(surname);
	//				for (String surname : surnames) {
	//					nextWordStartIndex = text.indexOf(" ", index + name.length());
	//					remainingText = text.substring(nextWordStartIndex);
	//					if (remainingText.startsWith(" ")) {
	//						remainingText = remainingText.substring(1);
	//					}
	//					if (remainingText.startsWith(surname)) {
	//						foundNamesSurnames.add(name + " " + surname);
	//					}
	//				}
	//			}
	//		}
	
	private List<String> firstLetterToUppercase(List<String> list) {
		List<String> firstUppercaseList = new ArrayList<String>();

		for (String s : list) {
			firstUppercaseList.add(s.substring(0, 1)
									.toUpperCase()
					+ s.substring(1));
		}

		return firstUppercaseList;
	}

	public List<String> findNamesSurnames(String text) {
		List<String> foundNamesSurnames = new ArrayList<String>();
		List<String> regexFound;
		List<String> names = this.names;
		List<String> surnames = this.surnames;
		text = text.toLowerCase();

		for (String surname : surnames) {
			if (containsWord(text, surname)) {
				System.out.println(surname);
				for (String name : names) {
					regexFound = findNameSurnameWithRegex(text, name, surname);
					if (!regexFound.isEmpty()) {
						foundNamesSurnames.addAll(regexFound);
					}
				}
			}
		}

		removeDuplicateFromList(foundNamesSurnames);

		return foundNamesSurnames;
	}

	private boolean containsWord(String text, String word) {
		List<String> found = new ArrayList<String>();
		Pattern r;
		Matcher m;
		String regexString;
		String foundMatch;

		regexString = "\\b" + word + "\\b";
		r = Pattern.compile(regexString);
		m = r.matcher(text);

		while (m.find()) {
			foundMatch = m.group();
			foundMatch = foundMatch.trim();

			found.add(foundMatch);
		}

		return !(found.isEmpty());
	}

	public List<String> findSurnamesNames(String text) {
		List<String> foundNamesSurnames = new ArrayList<String>();
		List<String> regexFound;
		List<String> names = this.names;
		List<String> surnames = this.surnames;
		text = text.toLowerCase();

		for (String name : names) {
			if (text.contains(name)) {
				System.out.println(name);
				for (String surname : surnames) {
					regexFound = findNameSurnameWithRegex(text, name, surname);
					if (!regexFound.isEmpty()) {
						foundNamesSurnames.addAll(regexFound);
					}
				}
			}
		}

		removeDuplicateFromList(foundNamesSurnames);

		return foundNamesSurnames;
	}

	private List<String> findNameSurnameWithRegex(String text, String name, String surname) {
		List<String> found = new ArrayList<String>();
		Pattern r;
		Matcher m;
		String regexString;
		String foundMatch;

		// regexString = "[^]((" + name + " " + surname + ")|(" + surname + " " + name + "))[ ,$\\.]";
		regexString = "((\\b" + name + "\\b \\b" + surname + "\\b)|(\\b" + surname + "\\b \\b" + name + "\\b))";
		r = Pattern.compile(regexString);
		m = r.matcher(text);

		while (m.find()) {
			foundMatch = m.group();
			foundMatch = foundMatch.trim();
			foundMatch = foundMatch.replace(",", "");
			foundMatch = foundMatch.replace(".", "");

			found.add(foundMatch);
			//System.out.println(m.group());
		}

		removeDuplicateFromList(found);

		return found;
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
