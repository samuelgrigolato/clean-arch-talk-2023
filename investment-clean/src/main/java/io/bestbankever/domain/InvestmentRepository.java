package io.bestbankever.domain;

import java.util.UUID;

public interface InvestmentRepository {

    ActiveInvestment getActiveInvestmentById(UUID investmentUuid);

    void saveRedeemedInvestment(RedeemedInvestment redeemedInvestment);

}
