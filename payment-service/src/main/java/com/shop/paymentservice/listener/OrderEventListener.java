package com.shop.paymentservice.listener;

import com.shop.paymentservice.event.OrderCreatedEvent;
import com.shop.paymentservice.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {
    private final PaymentService paymentService;

    public OrderEventListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @KafkaListener(topics = "order-created")
    public void onOrderCreated(OrderCreatedEvent event){
        System.out.println("💰 payment-service a reçu : " + event);
        paymentService.process(event);
    }
}
