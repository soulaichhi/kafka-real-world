package com.shop.orderservice.listener;

import com.shop.orderservice.event.PaymentProcessedEvent;
import com.shop.orderservice.event.StockProcessedEvent;
import com.shop.orderservice.service.OrderSagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SagaEventListener {
    @Autowired
    private OrderSagaService orderSagaService;
    @KafkaListener(topics = "payment-processed")
    public void onPaymentProcessed(PaymentProcessedEvent event) {
        orderSagaService.applyPayment(event);
        System.out.println("📦 order-service ← payment : " + event);
    }

    @KafkaListener(topics = "stock-processed")
    public void onStockProcessed(StockProcessedEvent event) {
        orderSagaService.applyStock(event);
        System.out.println("📦 order-service ← stock : " + event);
    }
}
