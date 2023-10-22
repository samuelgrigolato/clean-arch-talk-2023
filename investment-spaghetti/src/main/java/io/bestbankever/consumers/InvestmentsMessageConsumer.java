package io.bestbankever.consumers;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InvestmentsMessageConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void redeemAllInvestments(String investorUuidStr) {
        UUID ivnestorUuid = UUID.fromString(investorUuidStr);
        System.out.println(ivnestorUuid);
        this.rabbitTemplate.convertAndSend("investor.all-investments-redeemed", null, investorUuidStr);
    }

}
