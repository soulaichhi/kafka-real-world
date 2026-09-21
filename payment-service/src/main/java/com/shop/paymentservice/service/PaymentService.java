package com.shop.paymentservice.service;

import com.shop.paymentservice.enums.PaymentStatus;
import com.shop.paymentservice.event.OrderCreatedEvent;
import com.shop.paymentservice.event.PaymentProcessedEvent;
import com.shop.paymentservice.model.Payment;
import com.shop.paymentservice.repository.PaymentRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, PaymentProcessedEvent> kafkaTemplate;
    private static final String PAYMENT_PROCESSED_TOPIC = "payment-processed";
    public PaymentService(
            PaymentRepository paymentRepository,
            KafkaTemplate<String, PaymentProcessedEvent> kafkaTemplate
    ) {
        this.paymentRepository = paymentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Payment process(OrderCreatedEvent event) {
        PaymentStatus status = event.amount().compareTo(BigDecimal.valueOf(1000)) >0 ?
                PaymentStatus.FAILED
                : PaymentStatus.SUCCEEDED;
        Payment payment = Payment.builder()
                .orderId(event.orderId())
                .amount(event.amount())
                .status(status)
                .build();
        Payment savedPayment = paymentRepository.save(payment);
        kafkaTemplate.send(
                PAYMENT_PROCESSED_TOPIC,
                String.valueOf(event.orderId()),
                new PaymentProcessedEvent(event.orderId(), status,event.amount()));
        return savedPayment;
    }
}
