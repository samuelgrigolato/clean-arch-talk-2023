package io.bestbankever.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bestbankever.entities.Investment;
import io.bestbankever.repositories.InvestmentJpaRepository;
import io.bestbankever.vos.MonthYear;
import io.bestbankever.vos.MonthlyInterestRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class RedeemInvestmentService {

    @Autowired
    private InvestmentJpaRepository investmentJpaRepository;

    public BigDecimal redeem(Investment investment) throws URISyntaxException, IOException, InterruptedException {

        String awesomeTaxUrl = System.getenv("AWESOMETAX_URL");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(awesomeTaxUrl + "/interest-rates.json"))
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .build();
        String response = httpClient
                .send(httpRequest, HttpResponse.BodyHandlers.ofString())
                .body();
        ObjectMapper mapper = new ObjectMapper();
        MonthlyInterestRate[] rates = mapper.readValue(response, MonthlyInterestRate[].class);

        LocalDate redemptionDate = LocalDate.now();
        BigDecimal redeemedAmount = investment.getAmount();
        List<BigDecimal> sortedRates = Arrays.stream(rates)
                .filter(x -> x.monthYear().compareTo(MonthYear.from(investment.getInvestmentDate())) >= 0)
                .filter(x -> x.monthYear().compareTo(MonthYear.from(redemptionDate)) < 0)
                .sorted(Comparator.comparing(MonthlyInterestRate::monthYear))
                .map(MonthlyInterestRate::rate)
                .toList();
        for (BigDecimal rate : sortedRates) {
            redeemedAmount = redeemedAmount.multiply(BigDecimal.ONE.add(rate));
        }
        investment.setRedeemedAmount(redeemedAmount);
        investment.setRedemptionDate(redemptionDate);

        this.investmentJpaRepository.save(investment);

        return redeemedAmount;
    }

}
