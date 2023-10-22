package io.bestbankever.application;

import io.bestbankever.domain.*;

import java.util.Set;
import java.util.UUID;

public class RedeemAllActiveInvestmentsUseCase {
    private final InvestmentRepository investmentRepository;
    private final FindMonthlyInterestRatesByPeriodFunction findMonthlyInterestRatesByPeriodFunction;
    private final InvestmentEventPublisher investmentEventPublisher;
    private final TodayProvider todayProvider;

    public RedeemAllActiveInvestmentsUseCase(InvestmentRepository investmentRepository,
                                   FindMonthlyInterestRatesByPeriodFunction findMonthlyInterestRatesByPeriodFunction,
                                   InvestmentEventPublisher investmentEventPublisher,
                                   TodayProvider todayProvider) {
        this.investmentRepository = investmentRepository;
        this.findMonthlyInterestRatesByPeriodFunction = findMonthlyInterestRatesByPeriodFunction;
        this.investmentEventPublisher = investmentEventPublisher;
        this.todayProvider = todayProvider;
    }

    public void redeemAllActiveInvestments(UUID investorId) {
        Set<ActiveInvestment> activeInvestments = this.investmentRepository.getAllActiveByInvestor(investorId);
        for (ActiveInvestment activeInvestment : activeInvestments) {
            RedeemedInvestment redeemedInvestment = activeInvestment.redeem(this.findMonthlyInterestRatesByPeriodFunction, this.todayProvider);
            this.investmentRepository.saveRedeemedInvestment(redeemedInvestment);
        }
        this.investmentEventPublisher.publishAllActiveInvestmentsRedeemed(investorId);
    }
}
