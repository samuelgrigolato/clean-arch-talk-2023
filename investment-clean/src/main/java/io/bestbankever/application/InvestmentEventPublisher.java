package io.bestbankever.application;

import java.util.UUID;

public interface InvestmentEventPublisher {
    void publishAllActiveInvestmentsRedeemed(UUID investorId);
}
