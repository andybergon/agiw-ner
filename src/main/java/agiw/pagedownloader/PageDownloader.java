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
	public final static int MAX_BING_PAGES = 8;

	public static void main(String[] args) {
		PropertyFactor pf = new PropertyFactor();
		System.out.println("Starting pages download...");
		createAllFiles(pf.getPeoplePath());
	}

	public static void createAllFiles(String pathFile) {
		PropertyFactor pf = new PropertyFactor();
		List<String> peopleList;
		AzureSearchWebQuery aq;
		int personCounter, personTotal;
		int pageResultCounter;
		String lastname, name;

		personCounter = 0;

		aq = new AzureSearchWebQuery();
		aq.setAppid(pf.getBingKey());

		try {
			peopleList = PeopleList.peopleList(pathFile);
			personTotal = peopleList.size();

			for (String person : peopleList) {
				personCounter++;

				lastname = person.split(" ", 2)[0];
				name = person.split(" ", 2)[1];

				System.out.println("Querying for person #" + personCounter + "/" + personTotal);
				System.out.println(name + "_" + lastname);
				
				//				aq.setMarket("it-IT");
				//				aq.setMarket("en-GB");
				aq.setMarket("en-US");
				aq.setQuery(person);

				for (int i = 1; i <= MAX_BING_PAGES; i++) {
					System.out.println("Querying person #" + personCounter + " page #" + i + "/" + MAX_BING_PAGES);

					aq.setPage(i);
					aq.doQuery();

					AzureSearchResultSet<AzureSearchWebResult> ars = aq.getQueryResult();
					
					pageResultCounter = 0;

					for (AzureSearchWebResult anr : ars) {
						pageResultCounter++;
						
						StringBuilder sb = new StringBuilder();
						sb.append("#" + personCounter + "/" + personTotal);
						sb.append(" - ");
						sb.append("##" + i + "/" + MAX_BING_PAGES);
						sb.append(" - ");
						sb.append("###" + pageResultCounter);
						System.out.println(sb);
						
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
