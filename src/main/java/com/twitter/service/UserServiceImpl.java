package com.twitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.twitter.exception.UserException;
import com.twitter.model.User;
import com.twitter.repository.TweetRepository;
import com.twitter.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TweetRepository tweetRepository;
	

	@Override
	public User saveUser(User user) throws UserException {
		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException exception) {
			exception.printStackTrace();
			throw new UserException("username already exists.");
		}
	}

	@Override
	public User validateUser(User user) {
		User userByName = userRepository.findByUserName(user.getUserName());		
		if (userByName != null && user.getPassword().equalsIgnoreCase(userByName.getPassword())) {
			return userByName;
		}
		return null;
	}

	@Override
	public boolean deleteProfile(String userName) {
		if (userRepository.deleteByUserName(userName) == 1) {
			this.tweetRepository.deleteByOwner(userName);
			return true;
		} 
		return false;
	}
	
	@Override
	public User getUser(String userName) {
		return userRepository.findByUserName(userName);	
	}

}
