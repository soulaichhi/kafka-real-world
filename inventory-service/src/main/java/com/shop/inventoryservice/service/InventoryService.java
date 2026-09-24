package com.shop.inventoryservice.service;

import com.shop.inventoryservice.enums.StockStatus;
import com.shop.inventoryservice.event.OrderCreatedEvent;
import com.shop.inventoryservice.event.StockProcessedEvent;
import com.shop.inventoryservice.model.StockReservation;
import com.shop.inventoryservice.repository.StockReservationRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InventoryService {
    private static final String STOCK_PROCESSED_TOPIC = "stock-processed";
    private final StockReservationRepository stockReservationRepository;
    private final KafkaTemplate<String, StockProcessedEvent> kafkaTemplate;
    public InventoryService(StockReservationRepository stockReservationRepository, KafkaTemplate<String, StockProcessedEvent> kafkaTemplate) {
        this.stockReservationRepository = stockReservationRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public StockReservation process(OrderCreatedEvent event){
        StockStatus status = event.amount().compareTo(BigDecimal.valueOf(500)) > 0
                ? StockStatus.OUT_OF_STOCK
                : StockStatus.RESERVED;
        StockReservation reservation = StockReservation.builder()
                .orderId(event.orderId())
                .status(status)
                .build();
        StockReservation saved = stockReservationRepository.save(reservation);
        kafkaTemplate.send(
                STOCK_PROCESSED_TOPIC,
                String.valueOf(event.orderId()),
                new StockProcessedEvent(event.orderId(), status)
                );
        return saved;
    }
}
