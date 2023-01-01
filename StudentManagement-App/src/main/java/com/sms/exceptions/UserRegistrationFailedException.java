package com.sms.exceptions;

public class UserRegistrationFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserRegistrationFailedException(String message) {
        super(message);
    }
}
