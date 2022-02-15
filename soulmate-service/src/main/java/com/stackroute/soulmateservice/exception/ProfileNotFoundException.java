package com.stackroute.soulmateservice.exception;

/**
 * @Author Siva
 * @Date 10/29/2021 2:38 PM
 */
public class ProfileNotFoundException extends Exception{
    private String message;

    public ProfileNotFoundException() {
    }

    public ProfileNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
