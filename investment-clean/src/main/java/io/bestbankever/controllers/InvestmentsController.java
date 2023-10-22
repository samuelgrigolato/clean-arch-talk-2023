package io.bestbankever.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bestbankever.application.EmailSender;
import io.bestbankever.application.RedeemInvestmentUseCase;
import io.bestbankever.application.SuccessfulRedemptionEmailMessageBuilder;
import io.bestbankever.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/investments")
class InvestmentsController {

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private InvestorRepository investorRepository;

    @Autowired
    private FindMonthlyInterestRatesByPeriodFunction findMonthlyInterestRatesByPeriodFunction;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private TodayProvider todayProvider;

    @Autowired
    private SuccessfulRedemptionEmailMessageBuilder successfulRedemptionEmailMessageBuilder;

    @PostMapping("/{uuid}/redeem")
    ResponseEntity<?> redeem(@PathVariable UUID uuid) throws JsonProcessingException {
        RedeemInvestmentUseCase useCase = new RedeemInvestmentUseCase(this.investmentRepository,
                this.investorRepository,
                this.findMonthlyInterestRatesByPeriodFunction,
                this.emailSender,
                this.todayProvider,
                this.successfulRedemptionEmailMessageBuilder);

        RedeemedInvestment redeemedInvestment = useCase.redeem(uuid);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, BigDecimal> responseMap = new HashMap<>();
        responseMap.put("redeemedAmount", redeemedInvestment.getRedeemedAmount());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(responseMap));
    }

}
