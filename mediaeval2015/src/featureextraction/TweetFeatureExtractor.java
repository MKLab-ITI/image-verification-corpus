package featureextraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import twitter4j.Status;


/**
 * Extracts the Tweet features of a tweet.
 * @author Boididou Christina
 *
 */
public class TweetFeatureExtractor {

	/**
	 * Extracts the Tweet features of the tweet.
	 * @param status Status of the tweet
	 * @throws Exception
	 */
	public void extractTweetFeatures(Status status)
			throws Exception {

		
		String text = status.getText();
		
		/** Features depending on the Tweet**/
		//id
		System.out.println("Tweet ID: "+status.getId());
		//tweet length
		System.out.println("Length of the tweet text: "+text.length());
		//num of words
		System.out.println("Number of words " + getNumWords(status.getText()));
		//contains "?"
		System.out.println("Contains question mark: "+ containsSymbol(text,"?"));
		//contains "!"
		System.out.println("Contains exclamation mark: "+containsSymbol(text,"!"));
		//num of "!"
		System.out.println("Number of exclamation mark: "+getNumSymbol(text,"!"));
		//num of "?"
		System.out.println("Number of question mark: "+getNumSymbol(text,"?"));
		//contains happy emoticon
		System.out.println("Contains happy emo: "+containsEmo(text,Vars.HAPPY_EMO_PATH));
		//contains sad emoticon
		System.out.println("Contains sad emo: "+containsEmo(text,Vars.SAD_EMO_PATH));
		//num of uppercase chars
		System.out.println("Number of uppercase characters: "+getNumUppercaseChars(text));
		//num of mentions
		System.out.println("Number of mentions: "+getNumMentions(text));
		//num of hashtags
		System.out.println("Number of hashtags: "+getNumHashtags(text));
		//num of urls
		System.out.println("Number of URLs "+getNumURLs(text));
		//num of retweets
		System.out.println("Number of retweets: "+status.getRetweetCount());
		
		
		//remove characters in order to identify the language
		String str = text.replaceAll("http+s*+://[^ ]+", "")
				.replaceAll("@[^ ]+", "").replaceAll("#[^ ]+ ", "")
				.replaceAll("RT", "").toLowerCase().trim();
		String lang = TextUtils.getInstance().getLanguage(str);
		
		/**Additional features depending on the Tweet's language (computing for english, spanish and german)**/
		Integer numPosSentiWords = null, numNegSentiWords = null;
		Boolean containsFirstPron = null, containsSecondPron = null, containsThirdPron = null;
		
		//english
		if (lang.equals("en")) {
			//num of positive sentiment words
			numPosSentiWords = getNumSentiWords(text, Vars.POS_WORDS_ENG_PATH);
			
			//num of negative sentiment words
			numNegSentiWords = getNumSentiWords(text, Vars.NEG_WORDS_ENG_PATH);
			
			//contains first,second and third order pronoun
			containsFirstPron = containsPronoun(text, Vars.FIRST_PRON_PATH);
			containsSecondPron = containsPronoun(text, Vars.SECOND_PRON_PATH);
			containsThirdPron = containsPronoun(text, Vars.THIRD_PRON_PATH);
			

		//spanish
		} else if (lang.equals("es")) {
			
			numPosSentiWords = getNumSentiWords(text, Vars.POS_WORDS_ES_PATH);
			numNegSentiWords = getNumSentiWords(text, Vars.NEG_WORDS_ES_PATH);
			
			containsFirstPron = containsPronoun(text, Vars.FIRST_PRON_ES_PATH);
			containsSecondPron = containsPronoun(text, Vars.SECOND_PRON_ES_PATH);
			containsThirdPron = containsPronoun(text, Vars.THIRD_PRON_ES_PATH);
	
		//german
		} else if (lang.equals("de")) {
			
			numPosSentiWords = getNumSentiWords(text, Vars.POS_WORDS_DE_PATH);
			numNegSentiWords = getNumSentiWords(text, Vars.NEG_WORDS_DE_PATH);
			
			containsFirstPron = containsPronoun(text, Vars.FIRST_PRON_DE_PATH);
			containsSecondPron = containsPronoun(text, Vars.SECOND_PRON_DE_PATH);
			containsThirdPron = containsPronoun(text, Vars.THIRD_PRON_DE_PATH);
		}
		
		//print results
		String result = "";
		result = numPosSentiWords!=null ? "Number of positive sentiment words: "+numPosSentiWords : "Number of positive sentiment words: cannot be defined.";
		System.out.println(result);
		result = numNegSentiWords!=null ? "Number of positive sentiment words: "+numNegSentiWords : "Number of negative sentiment words: cannot be defined.";
		System.out.println(result);
		result = containsFirstPron!=null ? "Contains first order pronoun: "+containsFirstPron : "Contains first order pronoun: cannot be defined.";
		System.out.println(result);
		result = containsFirstPron!=null ? "Contains first order pronoun: "+containsSecondPron : "Contains second order pronoun: cannot be defined.";
		System.out.println(result);
		result = containsFirstPron!=null ? "Contains first order pronoun: "+containsThirdPron : "Contains third order pronoun: cannot be defined.";
		System.out.println(result);

	}
	
	/**
	 * Calculates the number of words contained in the tweet's text
	 * @return Integer the number of words found
	 */
	public static Integer getNumWords(String text) {

		// call the tokenizer to get the words of the string
		String[] tokens = TextUtils.getInstance().tokenizeText(text);

		return tokens.length;

	}
	
	/**
	 * Checks if the text contains a specified symbol
	 * @param text String that should be checked
	 * @param symbol String 
	 * @return Boolean value whether the symbol is presented or not
	 */
	public static Boolean containsSymbol(String text, String symbol) {

		String str = text.replaceAll("http://[^ ]+", " "); // drop urls
		return str.contains(symbol);
	}
	
	/**
	 * Calculates the number that a symbol appears in some text
	 * @param text String the text that should be checked
	 * @param symbol String 
	 * @return integer number of the times the symbol appears
	 */
	public static Integer getNumSymbol(String text,String symbol) {
		
		Integer numSymbols = 0;

		// check every single character of text for the given symbol
		for (int i = 0; i < text.length(); i++) {
			Character ch = text.charAt(i);
			if (ch.toString().equals(symbol)) {
				numSymbols++;
			}
		}
		return numSymbols;
	}
	
	/**
	 * Checks if the text contains an emoticon
	 * @param text String that should be checked
	 * @param filePath String the path of the file
	 * @return Boolean valus is the emoticon is contained or not
	 */
	public static Boolean containsEmo(String text, String filePath) {
		
		Boolean containsEmo = false;
		BufferedReader br = null;
		// hashset that contains the emoticons from the txt file
		HashSet<String> emoticons = new HashSet<String>();

		try {
			File fileEmoticons = new File(filePath);
			if (!fileEmoticons.exists()) {
				fileEmoticons.createNewFile();
			}
			String currentLine;
			// create the file reader
			br = new BufferedReader(new FileReader(fileEmoticons));
			// read the txt file and add each line to the hash set
			while ((currentLine = br.readLine()) != null) {
				emoticons.add(currentLine);
			}

			// use the iterator to get elements from the hashset
			// check if text contains each of the elements
			Iterator<String> iterator = emoticons.iterator();
			while (iterator.hasNext()) {
				String emo = iterator.next().toString();
				if (text.contains(emo)) {
					containsEmo = true;
				}
			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return containsEmo;

	}
	
	/**
	 * Calculated the number of uppercase characters the text contains
	 * @param text String the text for which the uppercase chars are calculated
	 * @return Integer the number of the uppercase chars
	 */
	public static Integer getNumUppercaseChars(String text) {
		Integer numUppercaseChars = 0;
		Character ch = null;

		// drop all URLs, hashtags and mentions ("http://", "#anyhashtag",
		// "@anymention", "@anymentionwithspace")- no need to count the
		// uppercase
		// chars on them

		String str = text.replaceAll("http://[^ ]+", "")
				.replaceAll("@ [^ ]+ ", "").replaceAll("@[^ ]+", "")
				.replaceAll("#[^ ]+", "");

		// count the uppercase chars
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				numUppercaseChars++;
			}
		}
		if (text.contains("RT ") && numUppercaseChars > 1) {
			numUppercaseChars = numUppercaseChars - 2;
		}
		
		return numUppercaseChars;
	}
	
	/**
	 * Calculates the number of mentions the text contains
	 * @param text String that should be checked
	 * @return Integer the number of mentions found
	 */
	public static Integer getNumMentions(String text) {
		Integer numMentions = 0;

		String[] tokens = TextUtils.getInstance().tokenizeText(text);
		// check if any token is a mention, so if it starts with @
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].startsWith("@")) {
				numMentions++;
			}
		}
		
		return numMentions;
	}

	/**
	 * Calculates the number of hashtags the text contains
	 * @param text String that should be checked
	 * @return Integer the number of hashtags found
	 */
	public static Integer getNumHashtags(String text) {
		Integer numHashtags = 0;
		String[] tokens = TextUtils.getInstance().tokenizeText(text);
		// check if any token is a hashtag, so if it starts with #
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].startsWith("#")) {
				numHashtags++;
			}
		}
		
		return numHashtags;
	}

	/**
	 * Calculates the number of urls the text contains
	 * @param text String that should be checked
	 * @return Integer number of urls found
	 */
	public static Integer getNumURLs(String text) {
		Integer numURLs = 0;

		// count the urls by checking if text contains "http" string
		if (text.contains("http://")) {
			numURLs++;
		}
		
		return numURLs;
	}
	
	public static Integer getNumSentiWords(String text,String filePath) {
		Integer numSentiWords = 0;
		BufferedReader br = null;
				
		String[] tokens = TextUtils.getInstance().tokenizeText(text);
		
		// use hashset to save the words from the txt file
		HashSet<String> sentiwords = new HashSet<String>();
		try {
			File sentiWords = new File(filePath);
			if (!sentiWords.exists()) {
				sentiWords.createNewFile();
			}
			String currentLine;
			br = new BufferedReader(new FileReader(sentiWords));

			while ((currentLine = br.readLine()) != null) {
				sentiwords.add(currentLine);
			}

			for (int i = 0; i < tokens.length; i++) {
				if (sentiwords.contains(tokens[i].replaceAll("[^A-Za-z0-9 ]", "").toLowerCase())) {
					numSentiWords++;
				}
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return numSentiWords;
	}
	
	public static Boolean containsPronoun(String text, String filePath) {
		Boolean containsPron = false;
		BufferedReader br = null;
		
		String[] tokens = TextUtils.getInstance().tokenizeText(text);
		
		// hash set that contains the words from the txt file
		HashSet<String> pronounWords = new HashSet<String>();

		try {
			File Prons = new File(filePath);
			if (!Prons.exists()) {
				Prons.createNewFile();
			}
			String currentLine;
			br = new BufferedReader(new FileReader(Prons));

			// save to hashset every line of the txt file
			while ((currentLine = br.readLine()) != null) {
				pronounWords.add(currentLine);
			}

			for (int j = 0; j < tokens.length; j++) {
				if (pronounWords.contains(tokens[j].replaceAll("[^A-Za-z0-9 ]", "").toLowerCase())) {
					containsPron = true;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return containsPron;
	}
}
