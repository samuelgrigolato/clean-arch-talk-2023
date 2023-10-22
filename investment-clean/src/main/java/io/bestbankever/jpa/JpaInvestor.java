package io.bestbankever.jpa;

import io.bestbankever.domain.Investor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "investors")
public class JpaInvestor implements Investor {
    @Id
    private UUID id;
    private String email;

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
