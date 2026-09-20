package com.shop.orderservice.event;

import java.math.BigDecimal;

public record OrderCreatedEvent(
        int orderId,
        String customer,
        BigDecimal amount
) {
}
