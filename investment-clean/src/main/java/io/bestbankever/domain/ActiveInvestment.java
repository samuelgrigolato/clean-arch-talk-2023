package io.bestbankever.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.SortedSet;
import java.util.UUID;

public class ActiveInvestment extends Investment {

    public RedeemedInvestment redeem(FindMonthlyInterestRatesByPeriodFunction ratesByPeriodFinder,
                              TodayProvider todayProvider) {
        LocalDate investmentDate = getInvestmentDate();
        BigDecimal amount = getAmount();
        LocalDate today = todayProvider.today();
        SortedSet<MonthlyInterestRate> monthlyRates = ratesByPeriodFinder.apply(
                MonthYear.from(investmentDate),
                MonthYear.from(today)
        );
        BigDecimal redeemedAmount = getAmount();
        for (MonthlyInterestRate monthlyRate : monthlyRates) {
            redeemedAmount = redeemedAmount.multiply(BigDecimal.ONE.add(monthlyRate.rate()));
        }
        return new RedeemedInvestment(getId(), investmentDate, amount, redeemedAmount, getInvestorId(), today);
    }

    public ActiveInvestment(UUID id, LocalDate investmentDate, BigDecimal amount, UUID investorId) {
        super(id, investmentDate, amount, investorId);
    }
}
