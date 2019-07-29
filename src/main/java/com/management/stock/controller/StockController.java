package com.management.stock.controller;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.stock.model.ResponseData;
import com.management.stock.model.StockModel;
import com.management.stock.service.StockService;
import com.management.stock.service.StockService;


import com.management.stock.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController 
{
	@Autowired
	StockService stockService;

	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/all")
	public ResponseEntity<ResponseData> showAllStock() {
		List<StockModel> allStockList = stockService.getAllStock();
		Map<Integer, String> status = new HashMap();
		status.put(200, "Successfull fetch");
		ResponseData response = new ResponseData("Stock List", status, allStockList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/quotes")
	public void getQuotation() {

	}

	
}
