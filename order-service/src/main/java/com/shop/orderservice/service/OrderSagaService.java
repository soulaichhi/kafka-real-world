package com.shop.orderservice.service;

import com.shop.orderservice.enums.PaymentStatus;
import com.shop.orderservice.enums.Status;
import com.shop.orderservice.enums.StockStatus;
import com.shop.orderservice.event.PaymentProcessedEvent;
import com.shop.orderservice.event.StockProcessedEvent;
import com.shop.orderservice.model.Order;
import com.shop.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderSagaService {
    @Autowired
    private OrderRepository orderRepository;
    public void applyPayment(PaymentProcessedEvent event) {
        Order order = orderRepository.findById(event.orderId()).orElse(null);
        if (order == null) return;
        order.setPaymentStatus(event.status());
        orderRepository.save(order);
        evaluate(order);
    }

    public void applyStock(StockProcessedEvent event) {
        Order order = orderRepository.findById(event.orderId()).orElse(null);
        if (order == null) return;
        order.setStockStatus(event.status());
        orderRepository.save(order);
        evaluate(order);
    }

    private void evaluate(Order order) {
        if (order.getPaymentStatus()==null || order.getStockStatus()==null) return;
        boolean ok = order.getPaymentStatus() == PaymentStatus.SUCCEEDED
                && order.getStockStatus() == StockStatus.RESERVED;
        order.setStatus(ok ? Status.CONFIRMED : Status.CANCELLED);
        orderRepository.save(order);
        System.out.println("🏁 Commande " + order.getId() + " → " + order.getStatus());
    }
}
