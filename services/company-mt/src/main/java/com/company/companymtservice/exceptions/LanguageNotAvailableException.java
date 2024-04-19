package com.company.companymtservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when the Language parameter is not available.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LanguageNotAvailableException extends Exception{
    public LanguageNotAvailableException(String message) { super(message); }
}
