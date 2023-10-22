package io.bestbankever.adapters;

import io.bestbankever.application.InvestmentEventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RabbitMQInvestmentEventPublisher implements InvestmentEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publishAllActiveInvestmentsRedeemed(UUID investorId) {
        this.rabbitTemplate.convertAndSend("investor.all-investments-redeemed", null, investorId);
    }
}
