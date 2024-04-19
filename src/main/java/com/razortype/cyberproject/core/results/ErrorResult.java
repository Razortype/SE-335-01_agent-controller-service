package com.razortype.cyberproject.core.results;

import org.springframework.http.HttpStatus;

public class ErrorResult extends Result {

    public ErrorResult() {
        super(false);
    }

    public ErrorResult(String message) {
        super(false, message);
    }

    public ErrorResult(HttpStatus status) {
        super(false, status);
    }

    public ErrorResult(String message, HttpStatus status) {
        super(false, message, status);
    }

}
