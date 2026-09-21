package com.shop.paymentservice.web;

import com.shop.paymentservice.event.OrderCreatedEvent;
import com.shop.paymentservice.model.Payment;
import com.shop.paymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/create")
    public Payment createPayment(OrderCreatedEvent event) {
        return paymentService.process(event);

    }
}
