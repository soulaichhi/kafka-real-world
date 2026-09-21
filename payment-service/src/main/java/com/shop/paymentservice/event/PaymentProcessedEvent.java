package com.shop.paymentservice.event;

import com.shop.paymentservice.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentProcessedEvent(
        int orderId,
        PaymentStatus status,
        BigDecimal amount
) {
}
