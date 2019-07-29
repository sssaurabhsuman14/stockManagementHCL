package com.management.stock.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.management.stock.entity.Stock;
import com.management.stock.entity.StockOrder;
import com.management.stock.exception.StockException;
import com.management.stock.model.StockHistoryModel;
import com.management.stock.model.StockModel;
import com.management.stock.model.StockOrderModel;

@Service
public interface StockService 
{


	public List<StockModel> getAllStock() throws StockException;

	public StockModel getQuotationService(Long userId, String symbol, int numberOfUnits, LocalDate quotationDate);

	public List<StockHistoryModel> getAllStockOrders(Long userId) throws StockException;
     
	public StockOrderModel getStockOrder(Long userId, String symbol) throws StockException;
	
	public StockOrderModel processOrder(String status, StockOrder order) throws StockException;

	void saveStock(StockModel model);


}
