package io.bestbankever.adapters;

import io.bestbankever.domain.ActiveInvestment;
import io.bestbankever.domain.InvestmentRepository;
import io.bestbankever.domain.RedeemedInvestment;
import io.bestbankever.jpa.JpaInvestment;
import io.bestbankever.jpa.JpaInvestmentRepository;
import io.bestbankever.jpa.JpaInvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
class DefaultInvestmentRepository implements InvestmentRepository {

    @Autowired
    private JpaInvestmentRepository jpaInvestmentRepository;

    @Autowired
    private JpaInvestorRepository jpaInvestorRepository;

    @Override
    public ActiveInvestment getActiveInvestmentById(UUID investmentUuid) {
        JpaInvestment jpaInvestment = this.jpaInvestmentRepository
                .findById(investmentUuid)
                .orElseThrow();
        if (jpaInvestment.getRedemptionDate() != null) {
            throw new IllegalStateException("Not active.");
        }
        return toActiveInvestment(jpaInvestment);
    }

    @Override
    public void saveRedeemedInvestment(RedeemedInvestment redeemedInvestment) {
        JpaInvestment jpaInvestment = this.jpaInvestmentRepository
                .findById(redeemedInvestment.getId())
                .orElseThrow();
        jpaInvestment.setInvestor(this.jpaInvestorRepository.getReferenceById(redeemedInvestment.getInvestorId()));
        jpaInvestment.setInvestmentDate(redeemedInvestment.getInvestmentDate());
        jpaInvestment.setAmount(redeemedInvestment.getAmount());
        jpaInvestment.setRedeemedAmount(redeemedInvestment.getRedeemedAmount());
        jpaInvestment.setRedemptionDate(redeemedInvestment.getRedemptionDate());
        this.jpaInvestmentRepository.save(jpaInvestment);
    }

    @Override
    public Set<ActiveInvestment> getAllActiveByInvestor(UUID investorId) {
        return this.jpaInvestmentRepository
                .findAllWhereRedemptionDateIsNullAndByInvestorId(investorId)
                .stream().map(DefaultInvestmentRepository::toActiveInvestment)
                .collect(Collectors.toSet());
    }

    private static ActiveInvestment toActiveInvestment(JpaInvestment jpaInvestment) {
        return new ActiveInvestment(jpaInvestment.getId(),
                jpaInvestment.getInvestmentDate(),
                jpaInvestment.getAmount(),
                jpaInvestment.getInvestor().getId());
    }
}
