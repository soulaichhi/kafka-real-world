package com.shop.orderservice.event;

import com.shop.orderservice.enums.StockStatus;

public record StockProcessedEvent(
        int orderId, StockStatus status
) {
}
