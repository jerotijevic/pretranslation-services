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
        isParamEmpty(Constants.PARAM_CONTENT, content);
        if(getWordCount(content) > Constants.MAX_CONTENT_WORD_SIZE) {
            throw new WordCountLimitExceededException(Constants.VAL_MAX_WORD_MSG);
        }
    }

    private int getWordCount(String content) {
        String[] contentWords = content.trim().split("\\s+");
        return contentWords.length;
    }

    private void validateLanguages(Language sourceLanguage, Language targetLanguage)
            throws RequiredParameterException, LanguageNotAvailableException {
        isParamEmpty(Constants.PARAM_SOURCE_LANG, sourceLanguage);
        isParamEmpty(Constants.PARAM_TARGET_LANG, targetLanguage);
        List<Language> languageCodeList = languageService.getMtLanguageCodes();
        if(!languageCodeList.contains(sourceLanguage.getLanguageCode())) {
            throw new LanguageNotAvailableException(Constants.PARAM_SOURCE_LANG + Constants.VAL_NOT_SUPPORTED);
        }
        if(!languageCodeList.contains(targetLanguage.getLanguageCode())) {
            throw new LanguageNotAvailableException(Constants.PARAM_TARGET_LANG + Constants.VAL_NOT_SUPPORTED);
        }
    }

    private void validateDomain(Domain domain) throws DomainNotAvailableException, RequiredParameterException {
        isParamEmpty(Constants.PARAM_DOMAIN, domain);
        if(!domainService.getMtDomains().contains(domain.getDomain())) {
            throw new DomainNotAvailableException(Constants.PARAM_DOMAIN + Constants.VAL_NOT_SUPPORTED);
        }
    }

    private void isParamEmpty(String paramName, Domain domain) throws RequiredParameterException {
        if(domain != null) {
            isParamEmpty(paramName, domain.getDomain());
        } else {
            throw new RequiredParameterException(paramName + Constants.VAL_SEND_PARAM);
        }
    }

    private void isParamEmpty(String paramName, Language language) throws RequiredParameterException {
        if(language != null) {
            isParamEmpty(paramName, language.getLanguageCode());
        } else {
            throw new RequiredParameterException(paramName + Constants.VAL_SEND_PARAM);
        }
    }

    private void isParamEmpty(String paramName, String param) throws RequiredParameterException {
        if(param == null || param.isEmpty()) {
            throw new RequiredParameterException(paramName + Constants.VAL_SEND_PARAM);
        }
    }
}
