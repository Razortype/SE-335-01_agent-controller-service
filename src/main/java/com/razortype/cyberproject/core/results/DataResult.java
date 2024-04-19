package com.razortype.cyberproject.core.results;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataResult<T> extends Result {

    private T data;

    public DataResult(T data, boolean success) {
        super(success);
        this.data = data;
    }

    public DataResult(T data, boolean success, String message) {
        super(success, message);
        this.data = data;
    }

    public DataResult(T data, boolean success, HttpStatus httpStatus) {
        super(success, httpStatus);
        this.data = data;
    }

    public DataResult(T data, boolean success, String message, HttpStatus httpStatus) {
        super(success, message, httpStatus);
        this.data = data;
    }

    @Override
    public void determineHttpStatus() {
        if (this.getHttpStatus() == null) {
            HttpStatus status = isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            super.setHttpStatus(status);
        }
    }

}
