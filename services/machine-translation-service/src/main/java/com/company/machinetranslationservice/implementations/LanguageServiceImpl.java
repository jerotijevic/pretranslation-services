package com.company.machinetranslationservice.implementations;

import com.company.machinetranslationservice.services.LanguageService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    @Override
    public List<String> getAllLanguageCodes() {
        List<String> languageCodes = new ArrayList<>();
        languageCodes.add("en-US");
        languageCodes.add("en-GB");
        languageCodes.add("fr-FR");
        languageCodes.add("de-DE");
        return languageCodes;
    }
}
