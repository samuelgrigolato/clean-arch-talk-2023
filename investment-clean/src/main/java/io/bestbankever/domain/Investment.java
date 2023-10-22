package io.bestbankever.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

class Investment {
    private LocalDate investmentDate;
    private BigDecimal amount;

    Investment(LocalDate investmentDate, BigDecimal amount) {
        this.investmentDate = investmentDate;
        this.amount = amount;
    }

    protected LocalDate getInvestmentDate() {
        return investmentDate;
    }

    protected BigDecimal getAmount() {
        return amount;
    }
}
