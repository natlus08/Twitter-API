package com.twitter.service;

import com.twitter.exception.UserException;
import com.twitter.model.User;

public interface UserService {
	User saveUser(User user) throws UserException;
	User validateUser(User user);
	boolean deleteProfile(String userName);
	User getUser(String userName);	
}
