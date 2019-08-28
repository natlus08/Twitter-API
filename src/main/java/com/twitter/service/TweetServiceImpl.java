package com.twitter.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.model.Tweet;
import com.twitter.repository.TweetRepository;

@Service
public class TweetServiceImpl implements TweetService {
	
	@Autowired
	private TweetRepository tweetRepository;

	@Override
	public List<Tweet> tweets(String userName) {
		return this.tweetRepository.findByOwner(userName); 
	}
	
	@Override
	public List<Tweet> friendsTweets(List<String> friends) {
		return this.tweetRepository.findByOwnerIn(friends); 
	}

	@Override
	public Tweet tweet(Tweet tweet) {
		tweet.setCreatedDateTime(new Date());
		return this.tweetRepository.save(tweet);
	}

	@Override
	public void removeTweet(Long id) {
		tweetRepository.deleteById(id);
	}

	@Override
	public Tweet getTweet(Long id) {
		return this.tweetRepository.getOne(id);
	}	
	
}
