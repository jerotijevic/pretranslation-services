package com.company.companymtservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when there is a missing parameter.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredParameterException extends Exception {
    public RequiredParameterException(String message) {
        super(message);
    }
}
