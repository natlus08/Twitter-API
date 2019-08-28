package com.twitter.service;

import java.util.List;

import com.twitter.model.Tweet;

public interface TweetService {
	Tweet tweet(Tweet tweet);
	void removeTweet(Long id);
	List<Tweet> tweets(String userName);
	Tweet getTweet(Long id);
	List<Tweet> friendsTweets(List<String> friends);
}
