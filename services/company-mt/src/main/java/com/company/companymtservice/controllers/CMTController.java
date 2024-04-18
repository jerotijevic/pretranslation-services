package com.company.companymtservice.controllers;

import com.company.companymtservice.dtos.TranslationRequestDTO;
import com.company.companymtservice.exceptions.DomainNotAvailableException;
import com.company.companymtservice.exceptions.LanguageNotAvailableException;
import com.company.companymtservice.exceptions.RequiredParameterException;
import com.company.companymtservice.exceptions.WordCountLimitExceededException;
import com.company.companymtservice.services.ValidatorService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api/cmt")
public class CMTController {

    Logger logger = LoggerFactory.getLogger(CMTController.class);

    @Autowired
    ValidatorService validatorService;

    @PostMapping("/validated-translate")
    public ResponseEntity validatedTranslate(@RequestBody TranslationRequestDTO translationRequestDTO) {

        ResponseEntity responseEntity;
        logger.info("Received request for validation.");
        try {
            validatorService.validate(translationRequestDTO);
            logger.info("Request validated successfully. Sending to translation.");
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (WordCountLimitExceededException | LanguageNotAvailableException |
                DomainNotAvailableException | RequiredParameterException e) {
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
