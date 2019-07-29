package com.management.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.stock.entity.StockOrder;
import com.management.stock.model.StockHistoryModel;
import com.management.stock.model.StockOrderModel;

@Repository
public interface StockOrderRepository extends JpaRepository<StockOrder, Long>{

	public List<StockHistoryModel> findAllByUserId();

	public StockOrderModel findByUserIdAndSymbol();
   
}
