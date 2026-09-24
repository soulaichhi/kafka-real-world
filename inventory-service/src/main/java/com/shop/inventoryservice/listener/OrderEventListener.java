package com.shop.inventoryservice.listener;

import com.shop.inventoryservice.event.OrderCreatedEvent;
import com.shop.inventoryservice.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {
    private final InventoryService inventoryService;
    public OrderEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order-created")
    public void onOrderCreated(OrderCreatedEvent event) {
        System.out.println("inventory-service a reçu : " + event);
        inventoryService.process(event);
    }
}
