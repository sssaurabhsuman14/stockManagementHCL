package com.management.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.stock.entity.StockOrder;

@Repository
public interface StockOrderRepository extends JpaRepository<StockOrder, Long>{

}
