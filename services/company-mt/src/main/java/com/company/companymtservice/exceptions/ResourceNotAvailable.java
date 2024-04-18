package com.company.companymtservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotAvailable extends ResourceAccessException {
    public ResourceNotAvailable(String message) {
        super(message);
    }
}
