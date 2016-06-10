package agiw.ner.regex;

import java.util.ArrayList;
import java.util.List;

public class RegexPattern {
	private List<String> emails;
	private List<String> addresses;
	private List<String> qualifications;
	private List<String> telephones;
	private List<String> names;

	public RegexPattern() {
		this.emails = new ArrayList<String>();
		this.telephones = new ArrayList<String>();
		this.addresses = new ArrayList<String>();
		this.qualifications = new ArrayList<String>();
		this.names = new ArrayList<String>();
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}

	public List<String> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<String> qualifications) {
		this.qualifications = qualifications;
	}

	public List<String> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<String> telephones) {
		this.telephones = telephones;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	@Override
	public String toString() {
		return "RegexPattern [emails=" + emails + ", " + "addresses=" + addresses + ", " + "qualifications="
				+ qualifications + ", telephones=" + telephones + ", names=" + names + "]";
	}

}
