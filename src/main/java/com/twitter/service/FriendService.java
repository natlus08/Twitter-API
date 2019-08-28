package com.twitter.service;

import java.util.List;

import com.twitter.model.Friend;
import com.twitter.model.User;

public interface FriendService {
	User addFriend(String userName, String friendName);
	User removeFriend(String userName, String friendName);
	List<Friend> friends(String userName);
	List<Friend> searchFriends(String query);
}
