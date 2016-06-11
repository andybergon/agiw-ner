package agiw.pagedownloader;

import java.io.IOException;
import java.util.List;

import agiw.pagedownloader.utils.PeopleList;
import agiw.pagedownloader.utils.TextFileCreator;
import net.billylieurance.azuresearch.AzureSearchResultSet;
import net.billylieurance.azuresearch.AzureSearchWebQuery;
import net.billylieurance.azuresearch.AzureSearchWebResult;
import utils.PropertyFactor;

public class PageDownloader {

	public static void main(String[] args) {
		PropertyFactor pf = new PropertyFactor();
		System.out.println("Starting pages download...");
		createAllFiles(pf.getPeoplePath());
	}

	public static void createAllFiles(String pathFile) {
		PropertyFactor pf = new PropertyFactor();
		List<String> peopleList;
		AzureSearchWebQuery aq;

		aq = new AzureSearchWebQuery();
		aq.setAppid(pf.getBingKey());

		try {
			peopleList = PeopleList.peopleList(pathFile);

			for (String person : peopleList) {
				String lastname = person.split(" ", 2)[0];
				String name = person.split(" ", 2)[1];

				//				aq.setMarket("it-IT");
				//				aq.setMarket("en-GB");
				aq.setMarket("en-US");
				aq.setQuery(person);

				for (int i = 1; i <= 8; i++) {
					aq.setPage(i);
					aq.doQuery();
					AzureSearchResultSet<AzureSearchWebResult> ars = aq.getQueryResult();
					for (AzureSearchWebResult anr : ars) {
						TextFileCreator.createFile(lastname, name, anr.getUrl());
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error: People List NOT present!");
			e.printStackTrace();
		}

	}

}
