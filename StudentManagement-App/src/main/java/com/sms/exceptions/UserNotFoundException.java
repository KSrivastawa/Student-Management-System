package com.sms.exceptions;

public class UserNotFoundException extends Exception {
    
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String user_not_found) {
        super(user_not_found);
    }
}
