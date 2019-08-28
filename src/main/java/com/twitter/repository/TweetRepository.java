package com.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.model.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {	
	List<Tweet> findByOwner(String userName);
	List<Tweet> findByOwnerIn(List<String> friends);
	boolean deleteByOwner(String owner);
}
