package com.ars.dris.sim;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

    @RabbitListener(queues = RabbitMQConfig.E1QUEUE_NAME)
    public void receiveE1Message(String message) {
        System.out.println("E1 Received <" + message + ">");
    }

    @RabbitListener(queues = RabbitMQConfig.E2QUEUE_NAME)
    public void receiveE2Message(String message) {
        System.out.println("E2 Received <" + message + ">");
    }

    @RabbitListener(queues = RabbitMQConfig.P1QUEUE_NAME)
    public void receiveP1Message(String message) {
        System.out.println("P1 Received <" + message + ">");
    }

    // ... declare other listener methods for other queues
}