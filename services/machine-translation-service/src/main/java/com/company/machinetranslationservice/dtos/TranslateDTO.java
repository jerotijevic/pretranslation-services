package com.company.machinetranslationservice.dtos;

import com.company.machinetranslationservice.entities.Domain;
import com.company.machinetranslationservice.entities.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TranslateDTO {
    private Language source_language;
    private Language target_language;
    private Domain domain;
    private String content;

}
