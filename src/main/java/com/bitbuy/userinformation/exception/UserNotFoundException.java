package com.bitbuy.userinformation.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("No User with username " + username + ", Please create an account");
    }
}

