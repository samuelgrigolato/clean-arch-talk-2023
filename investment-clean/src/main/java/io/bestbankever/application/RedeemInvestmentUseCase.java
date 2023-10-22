package io.bestbankever.application;

import io.bestbankever.domain.*;

import java.util.UUID;

public class RedeemInvestmentUseCase {
    private final InvestmentRepository investmentRepository;
    private final InvestorRepository investorRepository;
    private final FindMonthlyInterestRatesByPeriodFunction findMonthlyInterestRatesByPeriodFunction;
    private final EmailSender emailSender;
    private final TodayProvider todayProvider;
    private final SuccessfulRedemptionEmailMessageBuilder successfulRedemptionEmailMessageBuilder;

    public RedeemInvestmentUseCase(InvestmentRepository investmentRepository,
                                   InvestorRepository investorRepository,
                                   FindMonthlyInterestRatesByPeriodFunction findMonthlyInterestRatesByPeriodFunction,
                                   EmailSender emailSender,
                                   TodayProvider todayProvider,
                                   SuccessfulRedemptionEmailMessageBuilder successfulRedemptionEmailMessageBuilder) {
        this.investmentRepository = investmentRepository;
        this.investorRepository = investorRepository;
        this.findMonthlyInterestRatesByPeriodFunction = findMonthlyInterestRatesByPeriodFunction;
        this.emailSender = emailSender;
        this.todayProvider = todayProvider;
        this.successfulRedemptionEmailMessageBuilder = successfulRedemptionEmailMessageBuilder;
    }

    public RedeemedInvestment redeem(UUID investmentUuid) {
        ActiveInvestment activeInvestment = this.investmentRepository.getActiveInvestmentById(investmentUuid);
        RedeemedInvestment redeemedInvestment = activeInvestment.redeem(this.findMonthlyInterestRatesByPeriodFunction, this.todayProvider);
        this.investmentRepository.saveRedeemedInvestment(redeemedInvestment);
        EmailMessage message = this.successfulRedemptionEmailMessageBuilder.buildEmailMessage(redeemedInvestment);
        Investor investor = this.investorRepository.getById(activeInvestment.getInvestorId());
        this.emailSender.sendEmail(message, investor.getEmail());
        return redeemedInvestment;
    }
}
