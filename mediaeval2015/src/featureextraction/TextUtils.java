package featureextraction;

import java.util.HashMap;
import me.champeau.ld.UberLanguageDetector;

/**
 * Provides the text processing procedure with the necessary functions.
 * @author Boididou Christina 
 */
public class TextUtils {

	private static TextUtils mInstance = new TextUtils();

	public static TextUtils getInstance() {
		
		
		if (mInstance == null) {
			
			mInstance = new TextUtils();
		}
		return mInstance;

	}

	/**
	 * Tokenizes the given string by the space character
	 * 
	 * @param str
	 *            String to be tokenized
	 * @return String[] the tokens found
	 */
	public String[] tokenizeText(String str) {

		//lowercase the text
		str = str.toLowerCase();
		
		// replace spanish characters with the corresponding simple ones
		str = removeSpanishAccent(str);
		
		// replace all the useless chars inside the text
		str = str.replaceAll(",", " ");
		str = str.replaceAll("$", " ");
		str = str.replaceAll("&quot;", " ");
		str = str.replaceAll("&gt;", " ");
		str = str.replaceAll("&lt;", " ");
		str = str.replaceAll("&amp", " ");
		str = str.replaceAll("http://[^ ]+ ", " ");
		str = str.replaceAll("-", " ");
		str = str.replaceAll("/", " ");
		str = str.replaceAll("=", " ");
		str = str.replaceAll("\\!", " ");
		str = str.replaceAll("\\.+", " ");
		//str = str.replaceAll("[^a-zA-Z&#@_\\d+; ]", " "); // drop
															// non-alphanumeric
		str = str.replaceAll("[^a-zA-Z&'#@\\_d+ ]", " ");
		// str = str.replaceAll("RT ",""); //clear retweet
		// str = str.replaceAll("@", ""); // Clear @'s (optional)
		// str = str.replaceAll("&#\\d+;", " "); //change &#[digits]; to space
		// str = str.replaceAll("\\?" , "\\\\?");

		str = str.trim();
		str = str.replaceAll("\\s+", " ");

		// split the result string by space
		String tokens[] = str.split(" ");

		return tokens;
	}
	
	
	
	
	/**
	 * Auxiliary function that removes the spanish accent from a spanish text
	 * @param word String the text from which the accent is removed
	 * @return String the transformed text
	 */
	public String removeSpanishAccent(String text) {
		text = text.replaceAll("à|á|â|ä", "a");
		text = text.replaceAll("ò|ó|ô|ö", "o");
		text = text.replaceAll("è|é|ê|ë", "e");
		text = text.replaceAll("ù|ú|û|ü", "u");
		text = text.replaceAll("ì|í|î|ï", "i");

		return text;
	}
	
	/**
	 * Removes the useless characters from a string (apart from the dot(.))
	 * It is used specifically for pre-processing the text before finding the urls
	 * @param str String that is processed
	 * @return String the result of processing
	 */
	public String eraseCharacters(String str){
		
		System.out.println("before " + str);
		
		str = str.replaceAll(",", " "); // Clear commas
		str = str.replaceAll("$", " "); // Clear $'s (optional)
		//str = str.replaceAll("@", " ");
		str = str.replaceAll("-", " ");
		str = str.replaceAll("!", " ");
		str = str.replaceAll("=", " ");
		//str = str.replaceAll("#"," ");
		
		//quotation marks
		str = str.replaceAll("”", " ");
		str = str.replaceAll("“", " ");
		str = str.replaceAll("»", " ");
		str = str.replaceAll("«", " ");
		str = str.replaceAll("‘", " ");
		str = str.replaceAll("’", " ");
		str = str.replaceAll("'", " ");
		str = str.replaceAll("\"", " ");
		
		//brackets
		str = str.replaceAll("\\[", " ");
		str = str.replaceAll("\\]", " ");
		
		//greater than - lower than
		str = str.replaceAll("&gt;", " ");
		str = str.replaceAll("&lt;", " ");
		
		str = str.replaceAll("\\s+", " ");
		
		//System.out.println("after " + str);
		return str;
	}

	/**
	 * Removes the useless characters from a string (including dot(.))
	 * It is used specifically for pre-processing the text before finding the urls
	 * @param str String that is processed
	 * @return String the result of processing
	 */
	public String eraseAllCharacters(String str){
		
		System.out.println("before " + str);
		
		str = str.replaceAll("\\.", " "); 
		str = str.replaceAll(",", " "); // Clear commas
		str = str.replaceAll("$", " "); // Clear $'s (optional)
		str = str.replaceAll("@", " ");
		str = str.replaceAll("-", " ");
		str = str.replaceAll("!", " ");
		str = str.replaceAll("=", " ");
		str = str.replaceAll("#"," ");
		
		//quotation marks
		str = str.replaceAll("”", " ");
		str = str.replaceAll("“", " ");
		str = str.replaceAll("»", " ");
		str = str.replaceAll("«", " ");
		str = str.replaceAll("‘", " ");
		str = str.replaceAll("’", " ");
		str = str.replaceAll("'", " ");
		str = str.replaceAll("\"", " ");
		
		//brackets
		str = str.replaceAll("\\[", " ");
		str = str.replaceAll("\\]", " ");
		
		//greater than - lower than
		str = str.replaceAll("&gt;", " ");
		str = str.replaceAll("&lt;", " ");
		
		str = str.trim();
		System.out.println("after " + str);
		return str;
	}
	
	/**
	 * Returns the language detected for the given String
	 * @param str String for which the language is detected
	 * @return String the language detected
	 */
	public String getLanguage(String str){
		String language = null;
		if (!str.trim().isEmpty()){
			UberLanguageDetector detector = UberLanguageDetector.getInstance();
			//System.out.println("string "+str);
			language = detector.detectLang(str);
			//System.out.println("language "+detector.scoreLanguages(str));			
		}
		if (language == null){
			language = "nolang";
		}
		return language;
	}
	
	public HashMap<String,Integer> calculateFrequencyInText(String[] words){
		
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		    for (String w : words) {
		        Integer n = map.get(w);
		        n = (n == null) ? 1 : ++n;
		        map.put(w, n);
		    }
		return map;
	}
	
}
