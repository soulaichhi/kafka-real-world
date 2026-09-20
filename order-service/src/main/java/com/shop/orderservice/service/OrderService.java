package com.shop.orderservice.service;

import com.shop.orderservice.dtos.CreateOrderRequest;
import com.shop.orderservice.enums.Status;
import com.shop.orderservice.event.OrderCreatedEvent;
import com.shop.orderservice.model.Order;
import com.shop.orderservice.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private static final String TOPIC = "order-created";
    public OrderService(OrderRepository orderRepository, KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Order createOrder(CreateOrderRequest orderRequest) {
        Order order = new Order();
        order.setAmount(orderRequest.amount());
        order.setCustomer(orderRequest.customer());
        order.setStatus(Status.CREATED);
        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent event = new OrderCreatedEvent(savedOrder.getId(), savedOrder.getCustomer(), savedOrder.getAmount());
        kafkaTemplate.send(TOPIC, String.valueOf(savedOrder.getId()), event);
        return savedOrder;
    }
}
