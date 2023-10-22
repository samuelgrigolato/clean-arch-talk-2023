package io.bestbankever.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class RedeemedInvestment extends Investment {

    private final BigDecimal redeemedAmount;
    private final LocalDate redemptionDate;

    RedeemedInvestment(LocalDate investmentDate, BigDecimal amount, BigDecimal redeemedAmount, UUID investorId, LocalDate redemptionDate) {
        super(investmentDate, amount, investorId);
        this.redeemedAmount = redeemedAmount;
        this.redemptionDate = redemptionDate;
    }

    BigDecimal getRedeemedAmount() {
        return redeemedAmount;
    }

    LocalDate getRedemptionDate() {
        return redemptionDate;
    }
}
