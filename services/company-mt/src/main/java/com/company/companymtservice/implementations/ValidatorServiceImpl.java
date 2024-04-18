package com.company.companymtservice.implementations;

import com.company.companymtservice.constants.Constants;
import com.company.companymtservice.dtos.TranslationRequestDTO;
import com.company.companymtservice.entities.Domain;
import com.company.companymtservice.entities.Language;
import com.company.companymtservice.exceptions.DomainNotAvailableException;
import com.company.companymtservice.exceptions.RequiredParameterException;
import com.company.companymtservice.exceptions.LanguageNotAvailableException;
import com.company.companymtservice.exceptions.WordCountLimitExceededException;
import com.company.companymtservice.services.ValidatorService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class ValidatorServiceImpl implements ValidatorService{

    @Autowired
    LanguageServiceImpl languageService;

    @Autowired
    DomainServiceImpl domainService;

    @Override
    public void validate(TranslationRequestDTO translationRequestDTO)
            throws WordCountLimitExceededException, LanguageNotAvailableException,
            DomainNotAvailableException, RequiredParameterException {
        validateContent(translationRequestDTO.getContent());
        validateLanguages(translationRequestDTO.getSource_language(), translationRequestDTO.getTarget_language());
        validateDomain(translationRequestDTO.getDomain());
    }

    private void validateContent(String content) throws WordCountLimitExceededException, RequiredParameterException {
        isParamEmpty("Content", content);
        if(getWordCount(content) > Constants.MAX_CONTENT_WORD_SIZE) {
            throw new WordCountLimitExceededException("Maximum number of words exceeded.");
        }
    }

    private int getWordCount(String content) {
        String[] contentWords = content.trim().split("\\s+");
        return contentWords.length;
    }

    private void validateLanguages(Language sourceLanguage, Language targetLanguage)
            throws RequiredParameterException, LanguageNotAvailableException {
        isParamEmpty("Source language", sourceLanguage);
        isParamEmpty("Target language", targetLanguage);
        List<Language> languageCodeList = languageService.getMtLanguageCodes();
        if(!languageCodeList.contains(sourceLanguage.getLanguageCode())) {
            throw new LanguageNotAvailableException("Source language is not supported at the moment.");
        }
        if(!languageCodeList.contains(targetLanguage.getLanguageCode())) {
            throw new LanguageNotAvailableException("Target language is not supported at the moment.");
        }
    }

    private void validateDomain(Domain domain) throws DomainNotAvailableException, RequiredParameterException {
        isParamEmpty("Domain", domain);
        if(!domainService.getMtDomains().contains(domain.getDomain())) {
            throw new DomainNotAvailableException("Selected domain is not supported at the moment.");
        }
    }

    private void isParamEmpty(String paramName, Domain domain) throws RequiredParameterException {
        if(domain != null) {
            isParamEmpty(paramName, domain.getDomain());
        } else {
            throw new RequiredParameterException("Parameter " + paramName + " must be sent.");
        }
    }

    private void isParamEmpty(String paramName, Language language) throws RequiredParameterException {
        if(language != null) {
            isParamEmpty(paramName, language.getLanguageCode());
        } else {
            throw new RequiredParameterException("Parameter " + paramName + " must be sent.");
        }
    }

    private void isParamEmpty(String paramName, String param) throws RequiredParameterException {
        if(param == null || param.isEmpty()) {
            throw new RequiredParameterException("Parameter " + paramName + " must be sent.");
        }
    }
}
