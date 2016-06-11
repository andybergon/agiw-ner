package agiw.pagedownloader.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class PeopleList {

	public static List<String> peopleList(String pathFile) throws IOException {
		List<String> nameList = FileUtils.readLines(new File(pathFile), "utf-8");

		return nameList;
	}

	public static List<String> peopleListWOcommons(String pathFile) throws IOException {
		Scanner s = new Scanner(new File(pathFile));
		ArrayList<String> list = new ArrayList<String>();

		while (s.hasNextLine()) {
			list.add(s.nextLine());
		}

		s.close();

		return list;
	}
}
