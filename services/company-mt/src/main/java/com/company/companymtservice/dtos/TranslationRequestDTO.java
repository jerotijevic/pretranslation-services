package com.company.companymtservice.dtos;

import com.company.companymtservice.entities.Domain;
import com.company.companymtservice.entities.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object with parameters that need to be validated before being forwarded for translation
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TranslationRequestDTO {
    private Language source_language;   //not in camel case because that's how it is stated in the assignment
    private Language target_language;   //not in camel case because that's how it is stated in the assignment
    private Domain domain;
    private String content;
}
