package com.twitter.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.twitter.exception.ErrorDetail;
import com.twitter.model.Tweet;
import com.twitter.model.User;
import com.twitter.service.TweetService;
import com.twitter.service.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TweetController {
	@Autowired
	private TweetService tweetService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/tweet")
	public ResponseEntity<Tweet> tweet(@RequestPart(name = "image", required = false) MultipartFile image, 
			@RequestPart("owner") String owner,
			@RequestPart(name = "text", required = false) String text) throws IOException {
		Tweet tweet = new Tweet();
		tweet.setOwner(owner);
		if (image != null) {
			tweet.setImage(image.getBytes());
		}
		if (text != null) {
			tweet.setText(text);
		}
		Tweet tweetResponse = this.tweetService.tweet(tweet);
		return new ResponseEntity<Tweet>(tweetResponse, HttpStatus.OK);
	}	
	
	@DeleteMapping("/tweet/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		this.tweetService.removeTweet(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping("/tweet/{userName}")
	public ResponseEntity<List<Tweet>> tweets(@PathVariable String userName) {
		List<Tweet> tweets = this.tweetService.tweets(userName);
		return new ResponseEntity<List<Tweet>>(tweets, HttpStatus.OK);
	}
	
	@GetMapping("/tweet/friends/{userName}")
	public ResponseEntity<List<Tweet>> firendsTweets(@PathVariable String userName) {
		User user = this.userService.getUser(userName);
		if (user != null && user.getFriends() != null && !user.getFriends().isEmpty()) {
			List<Tweet> tweets = this.tweetService.friendsTweets(user.getFriends());
			return new ResponseEntity<List<Tweet>>(tweets, HttpStatus.OK);
		}
		return new ResponseEntity<List<Tweet>>(new ArrayList<Tweet>(), HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetail> handleAllExceptions(Exception ex, WebRequest request) {
	  ErrorDetail errorDetails = new ErrorDetail(new Date(), ex.getMessage(), request.getDescription(false));
	  return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
