package com.company.machinetranslationservice.controllers;

import com.company.machinetranslationservice.dtos.TranslateDTO;
import com.company.machinetranslationservice.services.DomainService;
import com.company.machinetranslationservice.services.LanguageService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api/mt")
public class MTController {

    Logger logger = LoggerFactory.getLogger(MTController.class);

    @Autowired
    LanguageService languageService;

    @Autowired
    DomainService domainService;

    /**
     * A GET method from third-party service that returns hard coded values of language codes
     * @return a list of the supported language codes
     */
    @GetMapping("/languages")
    public ResponseEntity<List<String>> getLanguageCodes() {

        logger.info("Getting all the language codes.");
        List<String> entities = new ArrayList<>();
        entities.addAll(languageService.getAllLanguageCodes());

        logger.info("Successfully returning a list of language codes: " + entities.toString());
        return new ResponseEntity(entities, HttpStatus.OK);
    }

    /**
     * A GET method from third-party service that return hard coded values of supported content domains
     * which describes the content and provides better translation.
     * @return a list of the supported content domains
     */
    @GetMapping("/domains")
    public ResponseEntity getDomains() {

        logger.info("Getting all the domains.");
        List<String> entities = new ArrayList<>();
        entities.addAll(domainService.getAllDomains());

        logger.info("Successfully returning a list of domains: " + entities.toString());
        return new ResponseEntity(entities, HttpStatus.OK);
    }

    @PostMapping("/translate")
    public ResponseEntity translate(@RequestBody TranslateDTO translateDTO) {
        logger.info("Processing requested translation: " + translateDTO.toString());
        return new ResponseEntity(HttpStatus.PAYMENT_REQUIRED);
    }
}
