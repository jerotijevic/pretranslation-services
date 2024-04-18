package com.company.companymtservice.implementations;

import com.company.companymtservice.constants.Constants;
import com.company.companymtservice.entities.Language;
import com.company.companymtservice.services.MTService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class LanguageServiceImpl implements MTService {

    @Getter
    private List<Language> mtLanguageCodes = new ArrayList<>();

    @Value("${mt.api.url}" + "${mt.api.languages.uri}")
    private String mtLanguageUrl;

    private Logger logger = LoggerFactory.getLogger(LanguageServiceImpl.class);
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Cron method to run on a predefined time set in Constants class.
     */
    @Override
    @Scheduled(cron = Constants.CRON_DAILY_AT_MIDNIGHT)
    public void updateMTParameters() {
        logger.info(Constants.LANG_CODES_GET + mtLanguageUrl);
        try {
            mtLanguageCodes = restTemplate.getForObject(mtLanguageUrl, List.class);
            logger.info(Constants.LANG_CODES_REC + mtLanguageCodes);
        } catch (ResourceAccessException e) {
            logger.error(mtLanguageUrl + Constants.NOT_AVAILABLE);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Method to run on initialization in order to get all available language codes from Machine Translation Service
     * @param args
     */
    @Override
    public void run(String... args) {
         updateMTParameters();
    }
}
