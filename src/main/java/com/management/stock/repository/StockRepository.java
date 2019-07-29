package com.management.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.stock.entity.Stock;
import com.management.stock.entity.StockOrder;

@Repository
public interface StockRepository extends JpaRepository<Stock, String>{


	List<StockOrder> findAllByUserId();
	Stock findOne(Long stockId);


}
