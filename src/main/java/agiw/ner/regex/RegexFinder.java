package agiw.ner.regex;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFinder {
	public static final String EMAIL_REGEX = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";

	public static final String PHONE_REGEX = "([(]?[\\+](\\d){2,5}[)]?[ //.]?)?" //prefix
			+ "(((\\d){2,4}[ -/]?(\\d){5,10})|"
			+ "((\\d){8,12})|"
			+ "(((\\d){2}[/]){3,6}(\\d){2})|"
			+ "(((\\d){2}[ ]){3,6}(\\d){2})|"
			+ "(((\\d){2}[//.]){3,6}(\\d){2})|"
			+ "(((\\d){3}[ //./]){2,4}(\\d){3})|"
			+ "(((\\d){4}[ //./])((\\d){3}[ //./])((\\d){3}))|"
			+ "(((\\d){3}[ //./])((\\d){2}[ //./])((\\d){2}[ //./])((\\d){3}))|"
			+ "((\\d){3}[ //./]((\\d){2}[ //./]?){2,4}(\\d){2}))"
			+ "(([/]|[//.][/]|[/][//.])(\\d){2,3})?"; //suffix

	//	public static final String PHONE_REGEX_OLD = "([(]?[\\+(]?(\\d){2,}[)]?[ \\.]?(\\d){2,}[ \\.]?(\\d){2,}[ \\.]?(\\d){2,}[ \\.]?(\\d){2,})|"
	//			+ "([(]?[\\+(]?(\\d){2,}[)]?[ \\.]?(\\d){2,}[ \\.]?(\\d){2,}[ \\.]?(\\d){2,})|"
	//			+ "([(]?[\\+(]?(\\d){2,}[)]?[ \\.]?(\\d){2,}[ \\.]?(\\d){2,})";
	//			+ "([(]?[(]?[\\+(]?(\\d){3,4}[)]?[ -/\\.]?(\\d){3,}[ -/\\.]?(\\d){2,}[ -/\\.]?(\\d){2,}[ -/\\.]?(\\d){2,})|"
	//			+ "([(]?[\\+(]?(\\d){3,4}[)]?[ -/\\.]?(\\d){3,}[ -/\\.]?(\\d){2,}[ -/\\.]?(\\d){2,})|"
	//			+ "([(]?[\\+(]?(\\d){3,4}[)]?[ -/\\.]?(\\d){3,}[ -/\\.]?(\\d){2,})";
	//public static final String PHONE_REGEX2 = "(\\([0-9]{0,3}\\)|[0-9]{0,3}-)[0-9]{0,3}-[0-9]{0,4}";

	public static final String ADDRESS_START = "Lungo|Largo|L\\.go|Lrg\\.|Lg\\.|Piazza|P\\.zza|Piazzale|Pz\\.le|Strada|Stretto|Via|V\\.|Vicolo|Viale|V\\.le|Vl\\.";
	// P.
	public static final String ADDRESS_REGEX = "[ ^](?i)(" + ADDRESS_START
			+ ") ([A-Za-z]+[,\\.]? )+[0-9]{1,6}([/-][a-z]{0,4}[0-9]{0,6})*";
	// ([A-Za-z]+[,.]? )+[0-9]+([/-]?[a-z0-9]{0,5})" controllare se $ o ^

	public static final String JOB_START = "Arch|Avv|Dott|Comm|Don|Dott|Dott\\.sa|Dott\\.ssa|Dottor|Dr|Geom|Ill\\.mo|Ing|Mo|Mons|On\\.|"
			+ "P\\.Avv|Prof|Prof\\.sa|Prof\\.ssa|Rag|Rev|Sen|Ten|Uff";
	// "Gen|GUP|PM|SA|SE|SS"
	public static final String JOB_REGEX = "[$ \\.](?i)(" + JOB_START + ")[ \\.]";

	public static final String NAME_REGEX = "(?:^|(?<=\\s))(?=(\\S+\\s+\\S+)(?=\\s|$))";

	public static void main(String[] args) {
		String emailTest = "my email is andyb@libero.it, ajaj \n my other is adjaj@gmail.com. taas SdSDad@laskDa.org";
		String phoneTest = "l +39 22.52.255.55 mio numero è:  (+39) 43390 2024, il numero di luca è 0693 8247289. Quello di simo: +3935422985. chiara è 0689939983.";
		String addressTest = "mia Via Eschiclo, 21. luca: viale amster casav 33/34/35/c, chiara è largo adjj ee 22/b e bla. ";
		String jobTest = "P. IVA 11352961004 p.avv. jaa Ing. Paolo Merialdo, ing. paolo merialdo, dott.sa";
		String test = emailTest + phoneTest + addressTest + jobTest;

		String testBody = "Tel: +39 071 82847 - Fax: +39 071 84398  aaa 2804951 24 aaa 79/80 80/81 81/82 82/83 aaa 22.22.2222 aaa 340/068206 aaa 348-1521271"+
"aaa 0424-561108"+
"aaa +39 02.95.03.92.21" +
"aaa +39 0226 255 882"+
"aaa +39 0226 255 860/.861"+
"aaa +39 334 93 37 022"+
"aaa 0924 906902"+
"aaa 06 30891535"+
"aaa 011 4110964"+
"aaa +39.0444.545805"+
"aaa 081.5359534/523"+
"aaa 081.5359111"+
"aaa (+39) 0584 795500"+
"aaa 051/913456"+
"aaa +39 3349337022";

		RegexFinder rf = new RegexFinder();
		List<String> allMatches = rf.findRegexInString(PHONE_REGEX, testBody);
		//		List<String> allMatches = rf.findRegexInString("Dott.ssa","cjc Dottossa jdjajd");

		System.out.println(allMatches.toString());
	}

	public List<String> findRegexInString(String regex, String body) {
		List<String> allMatches = new ArrayList<String>();

		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(body);

		while (m.find()) {
			allMatches.add(m.group().trim());
			//System.out.println(m.group());
		}

		removeDuplicateFromList(allMatches);

		return allMatches;
	}

	private static void removeDuplicateFromList(List<String> list) {
		Set<String> setItems = new LinkedHashSet<String>(list);
		list.clear();
		list.addAll(setItems);
	}

	public List<String> findRegexNameInText(String body) {
		List<String> allMatches = new ArrayList<String>();

		Pattern r = Pattern.compile(NAME_REGEX);
		Matcher m = r.matcher(body);

		while (m.find()) {
			allMatches.add(m.group().trim());
			//System.out.println(m.group());
		}

		removeDuplicateFromList(allMatches);

		return allMatches;
	}

}
