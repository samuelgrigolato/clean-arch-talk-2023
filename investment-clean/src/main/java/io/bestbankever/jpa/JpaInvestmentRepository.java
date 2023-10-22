package io.bestbankever.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaInvestmentRepository extends JpaRepository<JpaInvestment, UUID> {
}
