package com.quar17esma.exceptions;

public class NoSuchUserException extends RuntimeException {

    private String email;

    public NoSuchUserException() {
    }

    public NoSuchUserException(String message, String email) {
        super(message);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
