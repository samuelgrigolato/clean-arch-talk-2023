package io.bestbankever.consumers;

import io.bestbankever.entities.Investment;
import io.bestbankever.entities.Investor;
import io.bestbankever.repositories.InvestmentJpaRepository;
import io.bestbankever.repositories.InvestorJpaRepository;
import io.bestbankever.services.RedeemInvestmentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@Component
public class InvestmentsMessageConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private InvestmentJpaRepository investmentJpaRepository;

    @Autowired
    private InvestorJpaRepository investorJpaRepository;

    @Autowired
    private RedeemInvestmentService redeemInvestmentService;

    public void redeemAllInvestments(String investorUuidStr) throws URISyntaxException, IOException, InterruptedException {
        UUID investorUuid = UUID.fromString(investorUuidStr);
        System.out.println(investorUuid);

        Investor investor = this.investorJpaRepository
                .findById(investorUuid)
                .orElseThrow();

        Investment exampleInvestment = new Investment();
        exampleInvestment.setInvestor(investor);
        List<Investment> investments = this.investmentJpaRepository.findAll(Example.of(exampleInvestment));

        for (Investment investment : investments) {
            if (investment.getRedemptionDate() == null) {
                this.redeemInvestmentService.redeem(investment);
            }
        }

        this.rabbitTemplate.convertAndSend("investor.all-investments-redeemed", null, investorUuidStr);
    }

}
