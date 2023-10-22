package io.bestbankever.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "investments")
public class JpaInvestment {
    @Id
    private UUID id;
    private BigDecimal amount;
    private LocalDate investmentDate;
    private LocalDate redemptionDate;
    private BigDecimal redeemedAmount;
    @ManyToOne
    private JpaInvestor investor;

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

    public JpaInvestor getInvestor() {
        return investor;
    }

    public void setInvestor(JpaInvestor investor) {
        this.investor = investor;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setInvestmentDate(LocalDate investmentDate) {
        this.investmentDate = investmentDate;
    }
}
