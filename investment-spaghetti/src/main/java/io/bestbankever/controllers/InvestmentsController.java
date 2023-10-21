package io.bestbankever.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bestbankever.entities.Investment;
import io.bestbankever.repositories.InvestmentJpaRepository;
import io.bestbankever.vos.MonthYear;
import io.bestbankever.vos.MonthlyInterestRate;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/investments")
class InvestmentsController {

    @Autowired
    private InvestmentJpaRepository investmentJpaRepository;

    @PostMapping("/{uuid}/redeem")
    ResponseEntity<?> redeem(@PathVariable UUID uuid) throws URISyntaxException, IOException, InterruptedException, MessagingException {

        Optional<Investment> maybeInvestment = this.investmentJpaRepository
                .findById(uuid);

        if (maybeInvestment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Investment investment = maybeInvestment.get();

        if (investment.getRedemptionDate() != null) {
            return ResponseEntity.badRequest().body("Already redeemed");
        }

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

        String emailHtml = """
        <h1>Your investment has been redeemed successfully!</h1>
        <p>Redeemed amount: %s</p>
        """.formatted(redeemedAmount);
        String investorEmail = investment.getInvestor().getEmail();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", System.getenv("SMTP_HOST"));
        prop.put("mail.smtp.port", "1025");

        Session session = Session.getInstance(prop);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("investments@bestbankever.io"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(investorEmail));
        message.setSubject("Investment " + investment.getId() + " Redeemed Successfully");

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(emailHtml, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);

        Map<String, BigDecimal> responseMap = new HashMap<>();
        responseMap.put("redeemedAmount", redeemedAmount);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(responseMap));
    }

}
