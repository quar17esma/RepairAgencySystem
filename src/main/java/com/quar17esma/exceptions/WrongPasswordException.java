package com.quar17esma.exceptions;

public class WrongPasswordException extends RuntimeException {

    private String email;

    public WrongPasswordException(String message, String email) {
        super(message);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}