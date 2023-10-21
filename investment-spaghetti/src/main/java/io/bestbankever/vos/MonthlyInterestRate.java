package io.bestbankever.vos;

import java.math.BigDecimal;

public record MonthlyInterestRate(
        BigDecimal rate,
        MonthYear monthYear
) {
}
