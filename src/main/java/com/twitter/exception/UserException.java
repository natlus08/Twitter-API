package com.twitter.exception;

@SuppressWarnings("serial")
public class UserException extends Exception {
	
	public UserException(String errorMessage) {
        super(errorMessage);
    }

}
