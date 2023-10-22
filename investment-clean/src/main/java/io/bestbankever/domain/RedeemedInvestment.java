package io.bestbankever.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class RedeemedInvestment extends Investment {

    private final BigDecimal redeemedAmount;
    private final LocalDate redemptionDate;

    RedeemedInvestment(UUID id, LocalDate investmentDate, BigDecimal amount, BigDecimal redeemedAmount, UUID investorId, LocalDate redemptionDate) {
        super(id, investmentDate, amount, investorId);
        this.redeemedAmount = redeemedAmount;
        this.redemptionDate = redemptionDate;
    }

    public BigDecimal getRedeemedAmount() {
        return redeemedAmount;
    }

    public LocalDate getRedemptionDate() {
        return redemptionDate;
    }
}
