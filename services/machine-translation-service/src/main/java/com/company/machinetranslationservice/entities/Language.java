package com.company.machinetranslationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Language {
    ENUS("en-US"),
    ENGB("en-GB"),
    FRFR("fr-FR"),
    DEDE("de-DE");

    private String value;
}
