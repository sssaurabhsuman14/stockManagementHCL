package com.management.stock.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.stock.entity.Stock;
import com.management.stock.model.StockModel;
import com.management.stock.repository.StockRepository;
import com.management.stock.service.StockService;

@Service
public class StockServiceImpl implements StockService{

	@Autowired
	StockRepository stockRepository;
	
	@Override
	public List<StockModel> getAllStock() 
	{
		List<StockModel> stockModelList = new ArrayList<>();
		List<Stock> stockList = stockRepository.findAll();
		return stockModelList;
	}

}
