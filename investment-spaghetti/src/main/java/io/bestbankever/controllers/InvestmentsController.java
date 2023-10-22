package io.bestbankever.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bestbankever.entities.Investment;
import io.bestbankever.repositories.InvestmentJpaRepository;
import io.bestbankever.services.RedeemInvestmentService;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/investments")
class InvestmentsController {

    @Autowired
    private InvestmentJpaRepository investmentJpaRepository;

    @Autowired
    private RedeemInvestmentService redeemInvestmentService;

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

        BigDecimal redeemedAmount = redeemInvestmentService.redeem(investment);

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

        ObjectMapper mapper = new ObjectMapper();
        Map<String, BigDecimal> responseMap = new HashMap<>();
        responseMap.put("redeemedAmount", redeemedAmount);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(responseMap));
    }

}
