package io.bestbankever.adapters;

import io.bestbankever.domain.Investor;
import io.bestbankever.domain.InvestorRepository;
import io.bestbankever.jpa.JpaInvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultInvestorRepository implements InvestorRepository {

    @Autowired
    private JpaInvestorRepository jpaInvestorRepository;

    @Override
    public Investor getById(UUID investorUuid) {
        return this.jpaInvestorRepository.findById(investorUuid)
                .orElseThrow();
    }
}
