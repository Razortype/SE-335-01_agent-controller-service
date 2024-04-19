package com.razortype.cyberproject.core.results;

import org.springframework.http.HttpStatus;

public class SuccessResult extends Result {

    public SuccessResult() {
        super(true);
    }

    public SuccessResult(String message) {
        super(true, message);
    }

    public SuccessResult(HttpStatus httpStatus) {
        super(true, httpStatus);
    }

    public SuccessResult(String message, HttpStatus httpStatus) {
        super(true, message, httpStatus);
    }

}
