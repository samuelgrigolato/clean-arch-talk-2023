package io.bestbankever.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "investments")
public class Investment {
    @Id
    private UUID id;
    private BigDecimal amount;
    private LocalDate investmentDate;
    private LocalDate redemptionDate;
    private BigDecimal redeemedAmount;
    @ManyToOne
    private Investor investor;

    public UUID getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getInvestmentDate() {
        return investmentDate;
    }

    public LocalDate getRedemptionDate() {
        return redemptionDate;
    }

    public void setRedemptionDate(LocalDate redemptionDate) {
        this.redemptionDate = redemptionDate;
    }

    public void setRedeemedAmount(BigDecimal redeemedAmount) {
        this.redeemedAmount = redeemedAmount;
    }

    public Investor getInvestor() {
        return investor;
    }
}
