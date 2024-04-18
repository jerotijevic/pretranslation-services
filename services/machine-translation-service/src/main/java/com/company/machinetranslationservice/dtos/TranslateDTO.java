package com.company.machinetranslationservice.dtos;

import com.company.machinetranslationservice.entities.Domain;
import com.company.machinetranslationservice.entities.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranslateDTO {
    private Language sourceLanguage;
    private Language targetLanguage;
//    private Domain domain;
    private String content;

}
