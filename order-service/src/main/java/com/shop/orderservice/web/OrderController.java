package com.shop.orderservice.web;

import com.shop.orderservice.dtos.CreateOrderRequest;
import com.shop.orderservice.model.Order;
import com.shop.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public Order createOrder(@RequestBody CreateOrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }
}
