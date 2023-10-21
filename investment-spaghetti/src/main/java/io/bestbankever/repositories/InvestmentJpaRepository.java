package io.bestbankever.repositories;

import io.bestbankever.entities.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvestmentJpaRepository extends JpaRepository<Investment, UUID> {
}
