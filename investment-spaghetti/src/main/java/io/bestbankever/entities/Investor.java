package io.bestbankever.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "investors")
public class Investor {
    @Id
    private UUID id;
    private String email;

    public String getEmail() {
        return email;
    }
}
