package io.bestbankever.domain;

import java.math.BigDecimal;
import java.util.Comparator;

public record MonthlyInterestRate(
        BigDecimal rate,
        MonthYear monthYear
) implements Comparable<MonthlyInterestRate> {

    @Override
    public int compareTo(MonthlyInterestRate o) {
        return Comparator.comparing(MonthlyInterestRate::monthYear)
                .thenComparing(MonthlyInterestRate::rate)
                .compare(this, o);
    }
}
