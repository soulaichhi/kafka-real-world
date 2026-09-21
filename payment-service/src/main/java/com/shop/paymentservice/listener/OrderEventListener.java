package com.shop.paymentservice.listener;

import com.shop.paymentservice.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {
    @KafkaListener(topics = "order-created")
    public void onOrderCreated(OrderCreatedEvent event){
        System.out.println("💰 payment-service a reçu : " + event);
    }
}
