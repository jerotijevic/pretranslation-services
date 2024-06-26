package com.company.companymtservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when the maximum word limit for translation is exceeded.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WordCountLimitExceededException extends Exception{
    public WordCountLimitExceededException(String message) { super(message); }
}
