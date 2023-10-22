package io.bestbankever.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class Investment {
    private UUID id;
    private LocalDate investmentDate;
    private BigDecimal amount;
    private UUID investorId;

    Investment(UUID id, LocalDate investmentDate, BigDecimal amount, UUID investorId) {
        this.id = id;
        this.investmentDate = investmentDate;
        this.amount = amount;
        this.investorId = investorId;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getInvestmentDate() {
        return investmentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getInvestorId() {
        return investorId;
    }
}
