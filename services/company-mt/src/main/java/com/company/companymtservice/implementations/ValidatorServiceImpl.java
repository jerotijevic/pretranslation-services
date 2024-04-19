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

    /**
     * Entry method used for validation of parameters sent for translation.
     * @param translationRequestDTO - DTO object with parameters.
     * @throws WordCountLimitExceededException - Exception for when the maximum word limit is exceeded.
     * @throws LanguageNotAvailableException - Exception for when the selected language is not available.
     * @throws DomainNotAvailableException - Exception for when the selected domain is not available.
     * @throws RequiredParameterException - Exception for when there is a missing parameter.
     */
    @Override
    public void validate(TranslationRequestDTO translationRequestDTO)
            throws WordCountLimitExceededException, LanguageNotAvailableException,
            DomainNotAvailableException, RequiredParameterException {
        validateContent(translationRequestDTO.getContent());
        validateLanguages(translationRequestDTO.getSource_language(), translationRequestDTO.getTarget_language());
        validateDomain(translationRequestDTO.getDomain());
    }

    /**
     * Method for validating Content parameter.
     * @param content - The content to be validated
     * @throws WordCountLimitExceededException - Exception for when the maximum word limit is exceeded.
     * @throws RequiredParameterException - Exception for when there is a missing parameter.
     */
    private void validateContent(String content) throws WordCountLimitExceededException, RequiredParameterException {
        isParamEmpty(Constants.PARAM_CONTENT, content);
        if(getWordCount(content) > Constants.MAX_CONTENT_WORD_SIZE) {
            throw new WordCountLimitExceededException(Constants.VAL_MAX_WORD_MSG);
        }
    }

    /**
     * Helper method to get the word count.
     * @param content - String with words.
     * @return - Number of words in content.
     */
    private int getWordCount(String content) {
        String[] contentWords = content.trim().split("\\s+");
        return contentWords.length;
    }

    /**
     * Method for validating Language parameters.
     * @param sourceLanguage - Source language parameter to be checked.
     * @param targetLanguage - Target language parameter to be checked.
     * @throws RequiredParameterException - Exception for when there is a missing parameter.
     * @throws LanguageNotAvailableException - Exception for when the selected language is not available.
     */
    private void validateLanguages(Language sourceLanguage, Language targetLanguage)
            throws RequiredParameterException, LanguageNotAvailableException {
        isParamEmpty(Constants.PARAM_SOURCE_LANG, sourceLanguage);
        isParamEmpty(Constants.PARAM_TARGET_LANG, targetLanguage);
        List<Language> languageCodeList = languageService.getMtLanguageCodes();
        if(!languageCodeList.toString().contains(sourceLanguage.getLanguageCode())) {
            throw new LanguageNotAvailableException(Constants.PARAM_SOURCE_LANG + Constants.VAL_NOT_SUPPORTED);
        }
        if(!languageCodeList.toString().contains(targetLanguage.getLanguageCode())) {
            throw new LanguageNotAvailableException(Constants.PARAM_TARGET_LANG + Constants.VAL_NOT_SUPPORTED);
        }
    }

    /**
     * Method for validating Domain parameter.
     * @param domain - parameter to be validated.
     * @throws DomainNotAvailableException - Exception for when the selected domain is not available.
     * @throws RequiredParameterException - Exception for when there is a missing parameter.
     */
    private void validateDomain(Domain domain) throws DomainNotAvailableException, RequiredParameterException {
        isParamEmpty(domain);
        if(!domainService.getMtDomains().toString().contains(domain.getDomainName())) {
            throw new DomainNotAvailableException(Constants.PARAM_DOMAIN + Constants.VAL_NOT_SUPPORTED);
        }
    }

    /**
     * Helper method for checking if the Domain param is null or empty.
     * @param domain - param to be checked.
     * @throws RequiredParameterException - Exception for when there is a missing parameter.
     */
    private void isParamEmpty(Domain domain) throws RequiredParameterException {
        if(domain != null) {
            isParamEmpty(Constants.PARAM_DOMAIN, domain.getDomainName());
        } else {
            throw new RequiredParameterException(Constants.PARAM_DOMAIN + Constants.VAL_SEND_PARAM);
        }
    }

    /**
     * Helper method for checking if the Language param is null or empty.
     * @param paramName - Source or Target.
     * @param language - param to be checked
     * @throws RequiredParameterException - Exception for when there is a missing parameter.
     */
    private void isParamEmpty(String paramName, Language language) throws RequiredParameterException {
        if(language != null) {
            isParamEmpty(paramName, language.getLanguageCode());
        } else {
            throw new RequiredParameterException(paramName + Constants.VAL_SEND_PARAM);
        }
    }

    /**
     * Helper method for checking if the param is null or empty.
     * @param paramName - name of parameter to be checked.
     * @param param - param to be checked
     * @throws RequiredParameterException - Exception for when there is a missing parameter.
     */
    private void isParamEmpty(String paramName, String param) throws RequiredParameterException {
        if(param == null || param.isEmpty()) {
            throw new RequiredParameterException(paramName + Constants.VAL_SEND_PARAM);
        }
    }
}
