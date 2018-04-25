package com.reactive.project.exception;

public class UserNotFoundException extends Exception {

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserNotFoundException() {
        this.message = message;
    }
}
