package com.management.stock.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.management.stock.entity.Stock;
import com.management.stock.entity.StockOrder;
import com.management.stock.exception.StockException;
import com.management.stock.model.StockModel;

@Service
public interface StockService 
{
	public List<StockModel> getAllStock() throws StockException;

    public List<StockOrder> getAllStockOrders(Long userId);

	public StockModel getQuotationService(Long userId, String symbol, int numberOfUnits, LocalDate quotationDate);
	


}
