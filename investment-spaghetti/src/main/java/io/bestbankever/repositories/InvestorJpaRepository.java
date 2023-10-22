package io.bestbankever.repositories;

import io.bestbankever.entities.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvestorJpaRepository extends JpaRepository<Investor, UUID> {
}
