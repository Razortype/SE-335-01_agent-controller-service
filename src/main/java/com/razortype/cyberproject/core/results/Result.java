package com.razortype.cyberproject.core.results;

import jakarta.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Result {

    private boolean success;
    private String message;
    private HttpStatus httpStatus;

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String message) {
        this(success);
        this.message = message;
    }

    public Result(boolean success, HttpStatus httpStatus) {
        this.success = success;
        this.httpStatus = httpStatus;
    }

    public Result(boolean success, String message, HttpStatus httpStatus) {
        this(success, httpStatus);
        this.message = message;
    }

    public void determineHttpStatus() {
        if (httpStatus == null) {
            httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        }
    }

}
