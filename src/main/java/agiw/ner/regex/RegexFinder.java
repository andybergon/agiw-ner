package agiw.ner.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFinder {
	public static final String EMAIL_REGEX = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
	public static final String PHONE_REGEX = "([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})";
	public static final String ADDRESS_START = "Lungo|Largo|L.go|Lrg.|Lg.|Piazza|P.zza|P.|Piazzale|Pz.le|Strada|Stretto|Via|V.|Vicolo|Viale|V.le|Vl.";
	public static final String ADDRESS_REGEX = "(?i)(" + ADDRESS_START + ") ([A-Za-z]+[,.]? )+[0-9]+([/-]?[a-z0-9]*)*";

	
	public static void main(String[] args) {
		String emailTest = "my email is andyb@libero.it, ajaj \n my other is adjaj@gmail.com. taas SdSDad@laskDa.org";
		String phoneTest = "l mio numero è:  (+39) 433902024, il numero di luca è 0693 8247289. Quello di simo: +3935422985. chiara è 0689939983.";
		String addressTest = "mia Via Eschiclo, 21. luca: viale amster casav 3/33/333, chiara è largo adjj ee 22/b e bla.";
		String jobTest = "Ing. Paolo Merialdo, ing. paolo merialdo, dott.sa";
		String test = emailTest+phoneTest+addressTest+jobTest;
		
		String emailRegex = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
		String er0 = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}";

		String phoneRegex = "([(]?[\\+(]?(\\d){2,}[)]?[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- /\\.]?(\\d){2,}[- /\\.]?(\\d){2,})";
		String pr0 = ".*[^0-9-( )] ([0-9-( )]+)$";
		String pr1 = "(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]‌​)\\s*)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)([2-9]1[02-9]‌​|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})\\s*(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+)\\s*)?";

		String addressStart = "Lungo|Largo|L.go|Lrg.|Lg.|Piazza|P.zza|P.|Piazzale|Pz.le|Strada|Stretto|Via|V.|Vicolo|Viale|V.le|Vl.";
		String addressRegex = "(?i)(" + addressStart + ") ([A-Za-z]+[,.]? )+[0-9]+([/-]?[a-z0-9]*)*";

		String jobStart = "Arch.|Avv.|Dott.|Comm.|Dott.|Dott.sa|Dott.ssa|Dr.|Gen.|Geom.|Ill.mo|Ing.|Mo.|Mons.|On.|"
				+ "Prof.|Prof.sa|Prof.ssa|Rag.|Rev.|Sen.|Ten.|Uff.";
		String jobStartDoubt = "|SA|SE|SS";
		String jobRegex = "(?i)(" + jobStart + ")"; // to finish

		RegexFinder rf = new RegexFinder();
		List<String> allMatches = rf.findRegexInString(phoneRegex, test);

		System.out.println(allMatches.toString());

	}

	public List<String> findRegexInString(String regex, String body) {
		List<String> allMatches = new ArrayList<String>();

		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(body);

		while (m.find()) {
			allMatches.add(m.group());
			System.out.println(m.group());
		}

		return allMatches;
	}
}
