package com.company.companymtservice.implementations;

import com.company.companymtservice.constants.Constants;
import com.company.companymtservice.entities.Domain;
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

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DomainServiceImpl  implements MTService {

    @Getter
    private List<Domain> mtDomains = new ArrayList<>();

    @Value("${mt.api.url}" + "${mt.api.domains.uri}")
    private String mtDomainUrl;

    private Logger logger = LoggerFactory.getLogger(DomainServiceImpl.class);

    /**
     * Cron method to run on a predefined time set in Constants class
     */
    @Override
    @Scheduled(cron = Constants.CRON_DAILY_AT_MIDNIGHT)
    public void updateMTParameters() {
        String infoGetDomains = Constants.DOMAINS_GET + mtDomainUrl;
        logger.info(infoGetDomains);
        try {
            mtDomains = restTemplate.getForObject(mtDomainUrl, List.class);
            String infoReceivedDomains = Constants.DOMAINS_REC + mtDomains;
            logger.info(infoReceivedDomains);
        } catch (ResourceAccessException e) {
            String errorURLNotAvailable = mtDomainUrl + Constants.NOT_AVAILABLE;
            logger.error(errorURLNotAvailable);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Method to run on initialization in order to get all available domains from Machine Translation Service
     * @param args String
     */
    @Override
    public void run(String... args) {
        updateMTParameters();
    }
}
