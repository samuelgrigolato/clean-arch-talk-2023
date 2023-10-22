package io.bestbankever.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class Investment {
    private LocalDate investmentDate;
    private BigDecimal amount;
    private UUID investorId;

    Investment(LocalDate investmentDate, BigDecimal amount, UUID investorId) {
        this.investmentDate = investmentDate;
        this.amount = amount;
        this.investorId = investorId;
    }

    protected LocalDate getInvestmentDate() {
        return investmentDate;
    }

    protected BigDecimal getAmount() {
        return amount;
    }

    public UUID getInvestorId() {
        return investorId;
    }
}
