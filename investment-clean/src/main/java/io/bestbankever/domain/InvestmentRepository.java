package io.bestbankever.domain;

import java.util.Set;
import java.util.UUID;

public interface InvestmentRepository {

    ActiveInvestment getActiveInvestmentById(UUID investmentUuid);

    void saveRedeemedInvestment(RedeemedInvestment redeemedInvestment);

    Set<ActiveInvestment> getAllActiveByInvestor(UUID investorId);

}
