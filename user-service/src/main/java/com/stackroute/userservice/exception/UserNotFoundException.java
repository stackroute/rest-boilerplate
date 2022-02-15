package com.stackroute.userservice.exception;

/**
 * @Author Siva
 * @Date 10/30/2021 3:01 PM
 */
public class UserNotFoundException extends Exception{
    private String message;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
