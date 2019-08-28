package com.twitter.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.model.Friend;
import com.twitter.model.User;
import com.twitter.repository.UserRepository;

@Service
public class FriendServiceImpl implements FriendService {
	
	@Autowired
	private UserRepository userRepository;	

	@Override
	public User addFriend(String userName, String friendName) {
		User friendByName = userRepository.findByUserName(friendName);
		if (friendByName != null) {
			User userByName = userRepository.findByUserName(userName);
			if (userByName!= null && userByName.getFriends() != null) {
				userByName.getFriends().add(friendName);
			} else {
				List<String> friends = Arrays.asList(friendName);
				userByName.setFriends(friends);
			}
			return this.userRepository.save(userByName);
		}
		return null;
	}

	@Override
	public User removeFriend(String userName, String friendName) {
		User friendByName = userRepository.findByUserName(friendName);
		if (friendByName != null) {
			User userByName = userRepository.findByUserName(userName);
			if (userByName!= null && userByName.getFriends() != null) {
				userByName.getFriends().remove(friendName);
			}
			return this.userRepository.save(userByName);
		}
		return null;
	}

	@Override
	public List<Friend> friends(String userName) {
		User user = userRepository.findByUserName(userName);
		if (user != null && user.getFriends() != null && user.getFriends().size() > 0) {
			List<String> friends = user.getFriends();
			return this.userRepository.findByUserNameIn(friends);
		}
		return null;
	}
	
	@Override
	public List<Friend> searchFriends(String query) {
		return userRepository.findByNameStartsWithIgnoreCase(query);		
	}

}
