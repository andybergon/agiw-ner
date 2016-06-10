package agiw.ner.regex;

import java.util.ArrayList;
import java.util.List;

public class RegexPattern {
	private List<String> email;
	private List<String> addr;
	private List<String> qualification;
	private List<String> tel;
	
	public RegexPattern() {
		this.email = new ArrayList<String>();
		this.tel = new ArrayList<String>();
		this.addr = new ArrayList<String>();
		this.qualification = new ArrayList<String>();
	}

	public List<String> getEmail() {
		return email;
	}

	public void setEmail(List<String> email) {
		this.email = email;
	}

	public List<String> getTel() {
		return tel;
	}

	public void setTel(List<String> tel) {
		this.tel = tel;
	}

	public List<String> getAddr() {
		return addr;
	}

	public void setAddr(List<String> addr) {
		this.addr = addr;
	}

	public List<String> getQualification() {
		return qualification;
	}

	public void setQualification(List<String> qualification) {
		this.qualification = qualification;
	}

	@Override
	public String toString() {
		return "Pattern [email=" + email + ", tel=" + tel + ", addr=" + addr + ", qualification=" + qualification + "]";
	}
	
}
