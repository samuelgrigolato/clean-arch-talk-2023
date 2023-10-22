package io.bestbankever.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ActiveInvestmentTest {

    @Test
    void testRedemption() {
        SortedSet<MonthlyInterestRate> rates = new TreeSet<>();
        rates.add(new MonthlyInterestRate(
                new BigDecimal("0.01"),
                new MonthYear(Month.APRIL, 2023)));
        rates.add(new MonthlyInterestRate(
                new BigDecimal("0.02"),
                new MonthYear(Month.MAY, 2023)));
        FindMonthlyInterestRatesByPeriodFunction ratesByPeriodFunctionMock = Mockito.mock(FindMonthlyInterestRatesByPeriodFunction.class);
        when(ratesByPeriodFunctionMock.apply(
                new MonthYear(Month.APRIL, 2023),
                new MonthYear(Month.JUNE, 2023)
        )).thenReturn(rates);

        LocalDate today = LocalDate.of(2023, 6, 13);
        TodayProvider todayProviderMock = Mockito.mock(TodayProvider.class);
        when(todayProviderMock.today()).thenReturn(today);

        UUID investmentId = UUID.randomUUID();
        LocalDate investmentDate = LocalDate.of(2023, 4, 20);
        BigDecimal amount = new BigDecimal("10.12");
        UUID investorId = UUID.randomUUID();
        ActiveInvestment subject = new ActiveInvestment(investmentId, investmentDate, amount, investorId);
        RedeemedInvestment redeemedInvestment = subject.redeem(ratesByPeriodFunctionMock, todayProviderMock);

        BigDecimal expectedRedeemedAmount = new BigDecimal("10.425624");
        assertEquals(expectedRedeemedAmount, redeemedInvestment.getRedeemedAmount());
        assertEquals(today, redeemedInvestment.getRedemptionDate());
    }

}
