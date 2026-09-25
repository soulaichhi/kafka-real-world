package com.shop.orderservice.event;

import com.shop.orderservice.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentProcessedEvent(
        int orderId, PaymentStatus status, BigDecimal amount
) {
}
