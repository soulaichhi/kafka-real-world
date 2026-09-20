package com.shop.orderservice.dtos;

import java.math.BigDecimal;

public record CreateOrderRequest(
        String customer,
        BigDecimal amount
) {
}
