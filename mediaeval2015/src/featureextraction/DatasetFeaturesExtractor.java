package featureextraction;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;


/**
 * Class that handles the feature extraction for tweets retrieved by Twitter API.
 * @author Boididou Christina
 *
 */
public class DatasetFeaturesExtractor {

	/**
	 * Creates the connection to Twitter API.
	 * @return Twitter object for the current connection
	 */
	public static Twitter connectToTwitterAPI() {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		/*please fill in your credentials*/
		cb.setDebugEnabled(true).setOAuthConsumerKey("CONSUMER_KEY")
				.setOAuthConsumerSecret("CONSUMER_SECRET")
				.setOAuthAccessToken("ACCESS_TOKEN")
				.setOAuthAccessTokenSecret("ACCESS_TOKEN_SECRET");

		cb.setJSONStoreEnabled(true);

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		return twitter;
	}

	/**
	 * Organizes the feature extraction
	 * @param tweetId
	 */
	public static void extractFeatures(String tweetId) {
		
		Twitter twitter = connectToTwitterAPI();
		
		Status status;
		try {
			status = twitter.showStatus(Long.parseLong(tweetId));
			TweetFeatureExtractor ife = new TweetFeatureExtractor();
			ife.extractTweetFeatures(status);
			
			User user = twitter.showUser(status.getUser().getId());
			UserFeatureExtractor ufe = new UserFeatureExtractor();
			ufe.extractUserFeatures(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	public static void main(String[] args) {

		String tweetId = "578401801818542080";
		extractFeatures(tweetId);
	}

}
