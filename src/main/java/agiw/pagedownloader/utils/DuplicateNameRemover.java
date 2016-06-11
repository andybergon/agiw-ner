package agiw.pagedownloader.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
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
				addedToSet = lines.add(line);
				if (!addedToSet) {
					System.out.println("Duplicate found: " + line);
				}
			}

			reader.close();
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

			for (String unique : lines) {
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
