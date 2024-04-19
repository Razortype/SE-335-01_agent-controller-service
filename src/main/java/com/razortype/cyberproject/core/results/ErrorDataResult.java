package com.razortype.cyberproject.core.results;

import org.springframework.http.HttpStatus;

public class ErrorDataResult<T> extends DataResult<T> {

    public ErrorDataResult(T data) {
        super(data, false);
    }

    public ErrorDataResult(T data, String message) {
        super(data, false, message);
    }

    public ErrorDataResult() {
        super(null, false);
    }

    public ErrorDataResult(String message) {
        super(null, false, message);
    }

    public ErrorDataResult(T data, HttpStatus httpStatus) {
        super(data, false, httpStatus);
    }

    public ErrorDataResult(T data, String message, HttpStatus httpStatus) {
        super(data, false, message, httpStatus);
    }

    public ErrorDataResult(HttpStatus httpStatus) {
        super(null, false, httpStatus);
    }

    public ErrorDataResult(String message, HttpStatus httpStatus) {
        super(null, false, message, httpStatus);
    }

}
