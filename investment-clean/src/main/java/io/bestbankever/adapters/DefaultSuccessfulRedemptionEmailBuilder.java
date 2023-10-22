package io.bestbankever.adapters;

import io.bestbankever.application.EmailMessage;
import io.bestbankever.application.SuccessfulRedemptionEmailMessageBuilder;
import io.bestbankever.domain.RedeemedInvestment;
import org.springframework.stereotype.Component;

@Component
class DefaultSuccessfulRedemptionEmailBuilder implements SuccessfulRedemptionEmailMessageBuilder {
    @Override
    public EmailMessage buildEmailMessage(RedeemedInvestment redeemedInvestment) {
        return new EmailMessage(
                "Investment " + redeemedInvestment.getId() + " Redeemed Successfully",
                """
                <h1>Your investment has been redeemed successfully!</h1>
                <p>Redeemed amount: %s</p>
                """.formatted(redeemedInvestment.getRedeemedAmount())
        );
    }
}
