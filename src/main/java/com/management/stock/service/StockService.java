package com.management.stock.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.management.stock.exception.StockException;
import com.management.stock.model.StockHistoryModel;
import com.management.stock.model.StockDTO;
import com.management.stock.model.StockOrderModel;

@Service
public interface StockService 
{


	public List<StockDTO> getAllStock() throws StockException;

	public StockDTO getQuotationService(Long userId, String symbol, int numberOfUnits, LocalDate quotationDate);

	public List<StockHistoryModel> getAllStockOrders(Long userId) throws StockException;
     
	public StockOrderModel getStockOrder(Long stockOrderId) throws StockException;
	
	public StockOrderModel processOrder(String status, StockOrderModel order) throws StockException;

	StockDTO saveStock(StockDTO model);


}
