package com.company.companymtservice.controllers;

import com.company.companymtservice.constants.Constants;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api/cmt")
public class CMTController {

    Logger logger = LoggerFactory.getLogger(CMTController.class);

    @Autowired
    ValidatorService validatorService;

    @Value("${mt.api.url}" + "${mt.api.translate.uri}")
    String mtTranslateUrl;

    private final RestTemplate restTemplate = new RestTemplate();


    @PostMapping("/validated-translate")
    public ResponseEntity<String> validatedTranslate(@RequestBody TranslationRequestDTO translationRequestDTO) {
        ResponseEntity<String> responseEntity;
        ResponseEntity<String> mtResponseEntity;
        logger.info(Constants.REQ_RECEIVED);
        try {
            validatorService.validate(translationRequestDTO);
            logger.info(Constants.REQ_VALIDATED);
        } catch (WordCountLimitExceededException | LanguageNotAvailableException |
                DomainNotAvailableException | RequiredParameterException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TranslationRequestDTO> requestHttpEntity = new HttpEntity<>(translationRequestDTO, headers);
        try {
            mtResponseEntity = restTemplate.postForEntity(mtTranslateUrl, requestHttpEntity, String.class);

            responseEntity = new ResponseEntity<>(mtResponseEntity.getBody(), HttpStatus.OK);
        } catch (ResourceAccessException e) {
            String errorMsg = mtTranslateUrl + Constants.NOT_AVAILABLE;
            logger.error(errorMsg);
            responseEntity = new ResponseEntity<>(errorMsg, HttpStatus.SERVICE_UNAVAILABLE);
        }

        return responseEntity;
    }
}
