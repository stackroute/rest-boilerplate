package com.stackroute.soulmateservice.exception;

/**
 * @Author Siva
 * @Date 10/29/2021 2:37 PM
 */
public class ProfileAlreadyExistsException extends Exception{
    private String message;
    public ProfileAlreadyExistsException() {
    }

    public ProfileAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
