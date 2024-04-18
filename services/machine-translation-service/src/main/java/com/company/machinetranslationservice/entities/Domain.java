package com.company.machinetranslationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Domain {
    ACADEMIC("academic"),
    BUSINESS("business"),
    GENERAL("general"),
    CASUAL("casual"),
    CREATIVE("creative");

    private String value;
}
