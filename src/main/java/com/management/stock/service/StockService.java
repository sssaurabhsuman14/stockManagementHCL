package com.management.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.management.stock.model.StockModel;

@Service
public interface StockService 
{
	public List<StockModel> getAllStock();

}
