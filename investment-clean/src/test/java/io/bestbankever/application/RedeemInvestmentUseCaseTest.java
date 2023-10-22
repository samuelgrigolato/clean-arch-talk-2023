package io.bestbankever.application;

import io.bestbankever.domain.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RedeemInvestmentUseCaseTest {

    @Test
    void testRedemption() {
        UUID investmentUuid = UUID.randomUUID();
        UUID investorUuid = UUID.randomUUID();
        ActiveInvestment activeInvestmentMock = Mockito.mock(ActiveInvestment.class);
        RedeemedInvestment redeemedInvestmentMock = Mockito.mock(RedeemedInvestment.class);

        when(activeInvestmentMock.getInvestorId()).thenReturn(investorUuid);

        InvestmentRepository investmentRepositoryMock = Mockito.mock(InvestmentRepository.class);
        InvestorRepository investorRepositoryMock = Mockito.mock(InvestorRepository.class);
        FindMonthlyInterestRatesByPeriodFunction findRatesByPeriodMock = Mockito.mock(FindMonthlyInterestRatesByPeriodFunction.class);
        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);
        TodayProvider todayProviderMock = Mockito.mock(TodayProvider.class);
        SuccessfulRedemptionEmailMessageBuilder successfulEmailBuilderMock = Mockito.mock(SuccessfulRedemptionEmailMessageBuilder.class);

        when(activeInvestmentMock.redeem(findRatesByPeriodMock, todayProviderMock)).thenReturn(redeemedInvestmentMock);
        when(investmentRepositoryMock.getActiveInvestmentById(investmentUuid)).thenReturn(activeInvestmentMock);

        String emailAddress = "expected@address.com";
        Investor investorMock = Mockito.mock(Investor.class);
        when(investorMock.getEmailAddress()).thenReturn(emailAddress);
        when(investorRepositoryMock.getById(investorUuid)).thenReturn(investorMock);

        EmailMessage emailMessage = new EmailMessage("subject", "<p>content</p>");
        when(successfulEmailBuilderMock.buildEmailMessage(redeemedInvestmentMock)).thenReturn(emailMessage);

        RedeemInvestmentUseCase subject = new RedeemInvestmentUseCase(investmentRepositoryMock,
                investorRepositoryMock,
                findRatesByPeriodMock,
                emailSenderMock,
                todayProviderMock,
                successfulEmailBuilderMock);
        RedeemedInvestment obtainedRedeemedInvestment = subject.redeem(investmentUuid);

        verify(investmentRepositoryMock).saveRedeemedInvestment(redeemedInvestmentMock);
        verify(emailSenderMock).sendEmail(emailMessage, emailAddress);
        assertEquals(redeemedInvestmentMock, obtainedRedeemedInvestment);
    }

}
