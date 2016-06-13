package agiw.pagedownloader.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import utils.PropertyFactor;

public class DuplicateNameRemover {
	public static void main(String[] args) {
		System.out.println("Stripping duplicate names...");
		stripDuplicatesPeople();
		System.out.println("Duplicate names stripped.");
	}

	public static void stripDuplicatesPeople() {
		PropertyFactor pf = new PropertyFactor();
		stripDuplicatesFromFile(pf.getPeoplePath());
	}
	

	public static void stripDuplicatesFromFile(String filename) {
		try {
			boolean addedToSet;
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			Set<String> lines = new LinkedHashSet<String>(10000); // maybe bigger
			String line;

			while ((line = reader.readLine()) != null) {
				addedToSet = lines.add(line.toLowerCase());
				if (!addedToSet) {
					System.out.println("Duplicate found: " + line);
				}
			}

			reader.close();
//			Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>() {
//			    public int compare(String str1, String str2) {
//			        int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
//			        if (res == 0) {
//			            res = str1.compareTo(str2);
//			        }
//			        return res;
//			    }
//			};
//
//			Collections.sort(lines, ALPHABETICAL_ORDER);
			List<String> linesList = new ArrayList<String>(new HashSet<String>(lines));
		//	linesList=(List<String>) lines;
			Collections.sort(linesList);
			
			PropertyFactor pf = new PropertyFactor();
			//System.out.println(pf.getStoragePath());
			File dir = new File(pf.getStoragePath());
			File[] directoryListing = dir.listFiles();
			if (directoryListing != null) {
				for (File child : directoryListing) {
					//System.out.println(child.getName());
					// Do something with child
					String fileName = child.getName();
					//System.out.println(filename);
					String name = child.getName().split("_")[0];
					String surname = child.getName().split("_")[1];
					String name_surname = name+" "+surname;
					String surname_name = surname+" "+name;
					if(linesList.contains(name_surname.toLowerCase())||linesList.contains(surname_name.toLowerCase())){
						linesList.remove(name_surname.toLowerCase());
						linesList.remove(surname_name.toLowerCase());
						System.out.println(linesList.size());
						System.out.println("rimuovo: "+name_surname);
					}
					if (child.length() == 0) {
						System.out.println("empty: " + fileName);
						child.delete();
					}
				}
			} else {
				System.err.println("Storage Path not setted properly, it should be a directory!");
			}
			
			System.out.println(linesList.size());
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

			for (String unique : linesList) {
				writer.write(unique);
				writer.newLine();
			}

			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
