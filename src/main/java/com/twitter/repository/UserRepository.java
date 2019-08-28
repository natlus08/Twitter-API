package com.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.model.Friend;
import com.twitter.model.User;

public interface UserRepository extends JpaRepository<User, Long> {	
	User findByUserName(String userName);

	Long deleteByUserName(String userName);
	
	List<Friend> findByUserNameIn(List<String> friends);
	
	List<Friend> findByNameStartsWithIgnoreCase(String query);
	
}
