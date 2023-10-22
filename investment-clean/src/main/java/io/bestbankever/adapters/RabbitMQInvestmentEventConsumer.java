package io.bestbankever.adapters;

import io.bestbankever.application.InvestmentEventPublisher;
import io.bestbankever.application.RedeemAllActiveInvestmentsUseCase;
import io.bestbankever.domain.FindMonthlyInterestRatesByPeriodFunction;
import io.bestbankever.domain.InvestmentRepository;
import io.bestbankever.domain.TodayProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RabbitMQInvestmentEventConsumer {

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private FindMonthlyInterestRatesByPeriodFunction findMonthlyInterestRatesByPeriodFunction;

    @Autowired
    private TodayProvider todayProvider;

    @Autowired
    private InvestmentEventPublisher investmentEventPublisher;

    public void redeemAllInvestments(String investorIdStr) {
        UUID investorId = UUID.fromString(investorIdStr);
        RedeemAllActiveInvestmentsUseCase useCase = new RedeemAllActiveInvestmentsUseCase(this.investmentRepository,
                this.findMonthlyInterestRatesByPeriodFunction,
                this.investmentEventPublisher,
                this.todayProvider);
        useCase.redeemAllActiveInvestments(investorId);
    }

}
