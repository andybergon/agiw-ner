package agiw.ner.regex;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFinder {
	public static final String EMAIL_REGEX = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";

	public static final String PHONE_REGEX = "([(]?[\\+(]?(\\d){2,}[)]?[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,})";
	//public static final String PHONE_REGEX2 = "(\\([0-9]{0,3}\\)|[0-9]{0,3}-)[0-9]{0,3}-[0-9]{0,4}";
	public static final String ADDRESS_START = "Lungo|Largo|L\\.go|Lrg\\.|Lg\\.|Piazza|P\\.zza|Piazzale|Pz\\.le|Strada|Stretto|Via|V\\.|Vicolo|Viale|V\\.le|Vl\\.";
	// P.
	public static final String ADDRESS_REGEX = "[ ^](?i)(" + ADDRESS_START + ") ([A-Za-z]+[,\\.]? )+[0-9]+([/-]?[a-z0-9]*)*";
	// ([A-Za-z]+[,.]? )+[0-9]+([/-]?[a-z0-9]{0,5})" controllare se $ o ^
	
	public static final String JOB_START = "Arch|Avv|Dott|Comm|Don|Dott|Dott\\.sa|Dott\\.ssa|Dottor|Dr|Geom|Ill\\.mo|Ing|Mo|Mons|On\\.|"
			+ "P\\.Avv|Prof|Prof\\.sa|Prof\\.ssa|Rag|Rev|Sen|Ten|Uff";
	// "Gen|GUP|PM|SA|SE|SS"
	public static final String JOB_REGEX = "[$ \\.](?i)(" + JOB_START + ")[ \\.]";

	public static void main(String[] args) {
		String emailTest = "my email is andyb@libero.it, ajaj \n my other is adjaj@gmail.com. taas SdSDad@laskDa.org";
		String phoneTest = "l +39 22.52.255.55 mio numero è:  (+39) 43390 2024, il numero di luca è 0693 8247289. Quello di simo: +3935422985. chiara è 0689939983.";
		String addressTest = "mia Via Eschiclo, 21. luca: viale amster casav 3/33/333, chiara è largo adjj ee 22/b e bla. ";
		String jobTest = "P. IVA 11352961004 p.avv. jaa Ing. Paolo Merialdo, ing. paolo merialdo, dott.sa";
		String test = emailTest + phoneTest + addressTest + jobTest;

		String testBody = "22/22/2222 22 22 22 22 NomeIndirizzoLocalità General Map Street Adele Nicoletti Home AVVOCATI COMMERCIALISTI NOTAI CONSULENTI Chi Siamo Contattaci Professionisti Napoli > Avvocati > Napoli > Adele Nicoletti Adele Nicoletti Avvocati Via A.mancini 15 80127 Napoli, NA TEL: Adele Nicoletti website: E' lei Adele Nicoletti? Ci contatti per modificare o aggiornare le informazioni di questa pagina cliccando qui Richieda Rimozione Profilo CONTATTACI professionistinapoli.it è il sito per trovare i professionisti. Questa è la pagina dedicata a Adele Nicoletti. Per trovare informazioni su Nicoletti Adele, Avvocato Professore Universita puoi visitare questa pagina oppure puoi trovare lo studio all'indirizzo Napoli all'indirizzo Via A.mancini 15. Coordinate Ufficio Adele Nicoletti Latitudine: 40.842499 Longitudine: 14.234955 Ufficio Via A.mancini 15 Napoli (NA) Benvenuto nella pagina di Nicoletti Adele, Avvocato Professore Universita , lo studio di Adele Nicoletti si trova a Napoli all'indirizzo Via A.mancini 15. Se stavi cercando il numero di telefono di Adele Nicoletti purtoppo noi di professionistinapoli.it non possiamo aiutarti. Il numero ancora ce lo devono comunicare. Se ci dovessero essere dei problemi puoi segnalarcelo con il form presente su professionistinapoli.it. TAGS Adele Nicoletti Napoli Altri in zona Avvocato Professore Universita Adele Nicoletti Via A.mancini 15 - Napoli Vai alla scheda Avvocato Nicola Amato Via P.s.mancini 13 - Napoli Vai alla scheda Avvocato Carlo Gisolfi Via P.s.mancini 13 - Napoli Vai alla scheda Avvocato Paola Pinto Via Mancini 19 - Napoli Vai alla scheda Avvocato Daniela Tognon Via Antonio Mancini 20/bis - Napoli Vai alla scheda Avvocato Nicola Terzi Via A.mancini 20 - Napoli Vai alla scheda Avvocato Adriana Mangogna Via A.mancini 20/bis - Napoli Vai alla scheda Avvocato Mariano Qualiano Via G.donizetti 1/e - Napoli Vai alla scheda Avvocato Alfredo Musto Via G.donizetti, 2 - Napoli Vai alla scheda Avvocato Rosa Magnotti Via Cimarosa, 9 - Napoli Vai alla scheda Avvocato Gaia Murolo Via Domenico Cimarosa, 23 - Napoli Vai alla scheda Avvocato Anna Maria Siniscalco Via Donizetti 5 - Napoli Vai alla scheda Avvocato Fiorella Saviotti Via Donizetti 5 - Napoli Vai alla scheda Avvocato Luca Ruffino Via F. P. Michetti 10 - Napoli Vai alla scheda Avvocato Vincenzo Benassai Via Renato Lordi 3 - Napoli Vai alla scheda Avvocato Giuliana Daniele Via R.lordi 6 - Napoli Vai alla scheda Avvocato Professore Universita Lucio De Giovanni Via Michetti 5 - Napoli Vai alla scheda Avvocato Anna Maria Galasso Via F.p. Michetti 6 - Napoli Vai alla scheda Avvocato Marina De Luca Via F.p.michetti - Napoli Vai alla scheda Avvocato Renato Russo Via F.p.michetti 10 - Napoli Vai alla scheda Servizi per i professionisti Contatti Nome Adele Nicoletti Citta' Napoli (NA)";

		RegexFinder rf = new RegexFinder();
		List<String> allMatches = rf.findRegexInString(PHONE_REGEX, test);
		//		List<String> allMatches = rf.findRegexInString("Dott.ssa","cjc Dottossa jdjajd");

		System.out.println(allMatches.toString());
	}

	public List<String> findRegexInString(String regex, String body) {
		List<String> allMatches = new ArrayList<String>();

		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(body);

		while (m.find()) {
			allMatches.add(m.group().trim());
			//			System.out.println(m.group());
		}
		
		removeDuplicateFromList(allMatches);

		return allMatches;
	}
	
	private static void removeDuplicateFromList(List<String> list) {
		Set<String> setItems = new LinkedHashSet<String>(list);
		list.clear();
		list.addAll(setItems);
	}
	
	public List<String> findRegexNameInText(String regex, String body) {
		List<String> allMatches = new ArrayList<String>();

		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(body);

		while (m.find()) {
			allMatches.add(m.group().trim());
			//			System.out.println(m.group());
		}
		
		removeDuplicateFromList(allMatches);

		return allMatches;
	}
	
}
