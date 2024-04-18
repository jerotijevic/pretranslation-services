package com.company.machinetranslationservice.implementations;

import com.company.machinetranslationservice.services.DomainService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class DomainServiceImpl implements DomainService {

    @Override
    public List<String> getAllDomains() {
        List<String> domains = new ArrayList<>();
        domains.add("academic");
        domains.add("business");
        domains.add("general");
        domains.add("casual"); //probably a typo in the assignment paper as it states "causal" not "casual
        return domains;
    }
}
