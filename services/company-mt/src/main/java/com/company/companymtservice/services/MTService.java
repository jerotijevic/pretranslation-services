package com.company.companymtservice.services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.web.client.RestTemplate;

public interface MTService extends CommandLineRunner {
    RestTemplate restTemplate = new RestTemplate();

    void updateMTParameters();
}
