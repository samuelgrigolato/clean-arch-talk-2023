package io.bestbankever.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.SortedSet;

class ActiveInvestment extends Investment {

    RedeemedInvestment redeem(FindMonthlyInterestRatesByPeriodFunction ratesByPeriodFinder,
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
        return new RedeemedInvestment(investmentDate, amount, redeemedAmount, today);
    }

    ActiveInvestment(LocalDate investmentDate, BigDecimal amount) {
        super(investmentDate, amount);
    }
}
