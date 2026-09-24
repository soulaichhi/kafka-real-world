package com.shop.inventoryservice.event;

import com.shop.inventoryservice.enums.StockStatus;

public record StockProcessedEvent(
        int orderId, StockStatus status
) {
}
