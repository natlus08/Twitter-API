
package com.twitter.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.twitter.exception.ErrorDetail;
import com.twitter.model.Friend;
import com.twitter.model.User;
import com.twitter.service.FriendService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class FriendController {
	@Autowired
	private FriendService friendService;
	
	@GetMapping("/friend/{userName}")
	public ResponseEntity<List<Friend>> friends(@PathVariable String userName) {
		return new ResponseEntity<List<Friend>>(this.friendService.friends(userName), HttpStatus.OK);
	}
	
	@GetMapping("/friend/{userName}/befriend/{friendName}")
	public ResponseEntity<User> befriend(@PathVariable String userName, @PathVariable String friendName) {
		User modifiedUser = this.friendService.addFriend(userName, friendName);
		return new ResponseEntity<User>(modifiedUser, HttpStatus.OK);
	}
	
	@GetMapping("/friend/{userName}/unfriend/{friendName}")
	public ResponseEntity<User> unfriend(@PathVariable String userName, @PathVariable String friendName) {
		User modifiedUser = this.friendService.removeFriend(userName, friendName);
		return new ResponseEntity<User>(modifiedUser, HttpStatus.OK);
	}
	
	@GetMapping("/friend/search/{query}")
	public ResponseEntity<List<Friend>> unfriend(@PathVariable String query) {
		return new ResponseEntity<List<Friend>>(this.friendService.searchFriends(query), HttpStatus.OK);		
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetail> handleAllExceptions(Exception ex, WebRequest request) {
	  ErrorDetail errorDetails = new ErrorDetail(new Date(), ex.getMessage(), request.getDescription(false));
	  return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
