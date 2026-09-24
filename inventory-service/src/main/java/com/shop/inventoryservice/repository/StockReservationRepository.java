package com.shop.inventoryservice.repository;

import com.shop.inventoryservice.model.StockReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockReservationRepository extends JpaRepository<StockReservation,Integer> {
}
