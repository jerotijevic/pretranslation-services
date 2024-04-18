package com.company.companymtservice.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api/cmt")
public class CMTController {

    @PostMapping("/validated-translate")
    public ResponseEntity validatedTranslate() {
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
