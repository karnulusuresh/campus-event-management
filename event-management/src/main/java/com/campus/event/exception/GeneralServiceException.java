package com.campus.event.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GeneralServiceException extends RuntimeException {
    public GeneralServiceException(String message) {
        super(message);
    }
    public GeneralServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
