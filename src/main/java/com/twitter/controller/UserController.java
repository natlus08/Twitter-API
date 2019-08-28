package com.twitter.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.twitter.exception.ErrorDetail;
import com.twitter.exception.UserException;
import com.twitter.model.User;
import com.twitter.service.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/user")
	public ResponseEntity<User> register(@RequestBody User user) throws UserException {
		User newUser = this.userService.saveUser(user);		
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}
	
	@PostMapping("/user/{userName}")
	public ResponseEntity<User> addAvatar(@RequestPart("image") MultipartFile image,
			@PathVariable String userName) throws UserException, IOException {
		User user = this.userService.getUser(userName);
		user.setAvatar(image.getBytes());	    
		User newUser = this.userService.saveUser(user);	
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<User> login(@RequestBody User user) throws UserException {
		User validatedUser = this.userService.validateUser(user);
		if (null == validatedUser) {
			throw new UserException("Invalid username/password.");
		}
		return new ResponseEntity<User>(validatedUser, HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{userName}")
	public ResponseEntity<String> deleteProfile(@PathVariable String userName) {
		this.userService.deleteProfile(userName);
		return new ResponseEntity<String>(HttpStatus.OK);	
	}
	
	@ExceptionHandler(UserException.class)
	public final ResponseEntity<ErrorDetail> handleUserException(Exception ex, WebRequest request) {
	  ErrorDetail errorDetails = new ErrorDetail(new Date(), ex.getMessage(), request.getDescription(false));
	  return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetail> handleAllExceptions(Exception ex, WebRequest request) {
	  ErrorDetail errorDetails = new ErrorDetail(new Date(), ex.getMessage(), request.getDescription(false));
	  return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
