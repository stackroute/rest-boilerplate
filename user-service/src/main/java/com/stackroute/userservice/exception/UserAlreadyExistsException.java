package com.stackroute.userservice.exception;

/**
 * @Author Siva
 * @Date 10/30/2021 3:01 PM
 */
public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
