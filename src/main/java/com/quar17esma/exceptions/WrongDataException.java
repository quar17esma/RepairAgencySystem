package com.quar17esma.exceptions;

public class WrongDataException extends RuntimeException {
    public WrongDataException() {
        super();
    }

    public WrongDataException(String message) {
        super(message);
    }
}
