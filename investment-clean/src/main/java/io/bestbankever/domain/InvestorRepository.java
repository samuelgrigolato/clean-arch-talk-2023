package io.bestbankever.domain;

import java.util.UUID;

public interface InvestorRepository {
    Investor getById(UUID investorUuid);
}
