package com.company.companymtservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when the Domain parameter is not available.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainNotAvailableException extends Exception{
    public DomainNotAvailableException(String message) {
        super(message);
    }
}
