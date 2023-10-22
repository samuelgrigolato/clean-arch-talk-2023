package io.bestbankever.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public interface JpaInvestmentRepository extends JpaRepository<JpaInvestment, UUID> {

    Set<JpaInvestment> findAllWhereRedemptionDateIsNullAndByInvestorId(UUID investorId);

}
