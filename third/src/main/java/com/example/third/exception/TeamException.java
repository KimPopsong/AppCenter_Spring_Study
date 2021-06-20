package com.example.third.exception;

public class TeamException extends RuntimeException{

    public TeamException() {
        super();
    }

    public TeamException(String message) {
        super(message);
    }

    public TeamException(Throwable cause) {
        super(cause);
    }
}
