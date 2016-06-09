package agiw.ner;

import java.util.ArrayList;
import java.util.List;

public class NER {
	private List<String> per;
	private List<String> org;
	private List<String> loc;

	private static final List<String> locations = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("Country");
			add("CityTown");
			add("Island");
			add("IslandGroup");
			add("Kingdom");
			add("Location");
			add("Region");
			add("USCounty");
			add("USState");
		}
	};

	public NER(List<NamedEntity> entities) {
		this();
		
		for (NamedEntity ne : entities) {
			String type = ne.getType();

			if (type.equals("Person")) {
				this.per.add(ne.getName());
			} else if (type.equals("Company")) {
				this.org.add(ne.getName());
			} else if (locations.contains(type)) {
				this.loc.add(ne.getName());
			}
		}

	}

	public NER(List<String> per, List<String> org, List<String> loc) {
		this();
		this.per = per;
		this.org = org;
		this.loc = loc;
	}
	
	public NER() {
		this.per = new ArrayList<String>();
		this.org = new ArrayList<String>();
		this.loc = new ArrayList<String>();
	}

	//	public void addNamedEntity(NamedEntity ne) {
	//		neType = ne.getType()
	//		
	//		
	//		
	//	}

	//	public void initializeLocationList() {
	//		this.locations.add("Country");
	//	}

	public List<String> getPer() {
		return per;
	}

	public void setPer(List<String> per) {
		this.per = per;
	}

	public List<String> getOrg() {
		return org;
	}

	public void setOrg(List<String> org) {
		this.org = org;
	}

	public List<String> getLoc() {
		return loc;
	}

	public void setLoc(List<String> loc) {
		this.loc = loc;
	}

	@Override
	public String toString() {
		return "NER [per=" + per + ", org=" + org + ", loc=" + loc + "]";
	}

	public void print() {
		System.out.println("### PER ###");
		printList(this.per);
		System.out.println("### ORG ###");
		printList(this.org);
		System.out.println("### LOC ###");
		printList(this.loc);
	}
	
	private void printList(List<String> list) {
		for (String el : list) {
			System.out.println(el);
		}
	}
}
