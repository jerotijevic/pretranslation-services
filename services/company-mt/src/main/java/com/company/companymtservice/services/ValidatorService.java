package com.company.companymtservice.services;

import com.company.companymtservice.dtos.TranslationRequestDTO;
import com.company.companymtservice.exceptions.DomainNotAvailableException;
import com.company.companymtservice.exceptions.RequiredParameterException;
import com.company.companymtservice.exceptions.LanguageNotAvailableException;
import com.company.companymtservice.exceptions.WordCountLimitExceededException;

public interface ValidatorService {
    void validate(TranslationRequestDTO translationRequestDTO)
            throws WordCountLimitExceededException, LanguageNotAvailableException,
            DomainNotAvailableException, RequiredParameterException;
}
