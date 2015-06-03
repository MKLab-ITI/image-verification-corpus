package featureextraction;

import twitter4j.User;

/**
 * Extracts the User features of a user posted the tweet.
 * 
 * @author Boididou Christina
 *
 */
public class UserFeatureExtractor {

	/**
	 * Extracts the User features of a User posted the tweet.
	 * 
	 * @param user User for which the features to be extracted.
	 */
	public void extractUserFeatures(User user) {

		System.out.println("User ID: " + user.getId());
		System.out.println("Number of friends: " + user.getFriendsCount());
		System.out.println("Number of followers: " + user.getFollowersCount());
		System.out.println("Followers/friends ratio "
				+ getFollowerFriendRatio(user.getFriendsCount(),
						user.getFollowersCount()));
		System.out.println("Times listed " + user.getListedCount());
		System.out.println("Has url " + hasUrl(user.getURL()));
		System.out.println("Is verified " + user.isVerified());
		System.out.println("Number of statuses count "
				+ user.getStatusesCount());

	}

	/**
	 * Function that calculates the ratio numFollowers/numFriends of a Twitter
	 * account
	 * 
	 * @param numFr
	 *            the number of friends of a user
	 * @param numFol
	 *            the number of followers of a user
	 * @return Float number of the ratio calculated
	 */
	public static Float getFollowerFriendRatio(int numFr, int numFol) {

		Float ratio = (float) 0;
		if (numFr != 0)
			ratio = (float) numFol / numFr;

		return ratio;
	}

	public static Boolean hasUrl(String url) {
		if (url != null)
			return true;

		return false;
	}

}
