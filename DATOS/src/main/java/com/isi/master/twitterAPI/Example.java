package com.isi.master.twitterAPI;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Example {

	public static void main(String[] args) {
		try {
			Twitter twitter = TwitterFactory.getSingleton();
			twitter.setOAuthConsumer("z4mU2gFl8tACEun7mzkwKaMI8", "s2srh0iCA1HoifFldrQNYutAKFKBy7xRfmpnb5gUwPEyCy6ACB");
			twitter.setOAuthAccessToken(new AccessToken("375340426-qTNlY4mtFZZ6NdhY5x67UQAkQGOeTYs51Rwu7RqM", "9p5qKgtaKSoALFu7ZCfNZ2joaYaecI96UTMu1ZqACr3LM"));

			ResponseList<Status> statuses = twitter.getHomeTimeline();
			System.out.println("Showing home timeline.");
			for (Status status : statuses) {
				System.out.println(status.getUser().getName() + ":" +
						status.getText());
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
