package io.bestbankever.application;

import io.bestbankever.domain.RedeemedInvestment;

public interface SuccessfulRedemptionEmailMessageBuilder {
    EmailMessage buildEmailMessage(RedeemedInvestment redeemedInvestment);
}
