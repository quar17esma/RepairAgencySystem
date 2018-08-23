package com.quar17esma.exceptions;

public class LoginException extends RuntimeException {

    private String login;

    public LoginException(String message, String login) {
        super(message);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
