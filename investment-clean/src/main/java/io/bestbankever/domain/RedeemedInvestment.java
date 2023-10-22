package io.bestbankever.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

class RedeemedInvestment extends Investment {

    private final BigDecimal redeemedAmount;
    private final LocalDate redemptionDate;

    RedeemedInvestment(LocalDate investmentDate, BigDecimal amount, BigDecimal redeemedAmount, LocalDate redemptionDate) {
        super(investmentDate, amount);
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
