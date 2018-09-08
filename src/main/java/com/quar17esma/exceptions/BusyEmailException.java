package com.quar17esma.exceptions;

public class BusyEmailException extends RuntimeException {
    private String userName;
    private String email;

    public BusyEmailException() {
    }

    public BusyEmailException(String message, String userName, String email) {
        super(message);
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
