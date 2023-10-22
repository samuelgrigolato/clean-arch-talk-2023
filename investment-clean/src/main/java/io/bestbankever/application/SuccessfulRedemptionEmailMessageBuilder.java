package io.bestbankever.application;

import io.bestbankever.domain.RedeemedInvestment;

interface SuccessfulRedemptionEmailMessageBuilder {
    EmailMessage buildEmailMessage(RedeemedInvestment redeemedInvestment);
}
