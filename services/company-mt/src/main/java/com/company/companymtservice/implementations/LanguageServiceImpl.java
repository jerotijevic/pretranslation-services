package com.company.companymtservice.implementations;

import com.company.companymtservice.constants.Constants;
import com.company.companymtservice.entities.Language;
import com.company.companymtservice.exceptions.ResourceNotAvailable;
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

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LanguageServiceImpl implements MTService {

    private List<Language> mtLanguageCodes;

    @Value("${mt.api.url}" + "${mt.api.languages.uri}")
    private String mtLanguageUrl;

    private Logger logger = LoggerFactory.getLogger(LanguageServiceImpl.class);
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Cron method to run on a predefined time set in Constants class.
     */
    @Override
    @Scheduled(cron = Constants.CRON_EVERY_MINUTE)
    public void getMTParameters() {
        logger.info("Getting language code list from: " + mtLanguageUrl);
        try {
            mtLanguageCodes = restTemplate.getForObject(mtLanguageUrl, List.class);
            logger.info("Language codes received: " + mtLanguageCodes.toString());
        } catch (ResourceNotAvailable e) {
            logger.error("Resource " + mtLanguageUrl + " is not available.");
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    /**
     * Method to run on initialization in order to get all available language codes from Machine Translation Service
     * @param args
     */
    @Override
    public void run(String... args) {
         getMTParameters();
    }
}
