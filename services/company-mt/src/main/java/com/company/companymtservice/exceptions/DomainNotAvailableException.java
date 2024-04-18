package com.company.companymtservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainNotAvailableException extends Exception{
    public DomainNotAvailableException(String message) {
        super(message);
    }
}
