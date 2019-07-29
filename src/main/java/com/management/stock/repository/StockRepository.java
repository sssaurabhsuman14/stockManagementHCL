package com.management.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.stock.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String>{

}
