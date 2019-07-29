package com.management.stock.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.stock.entity.StockOrder;
import com.management.stock.model.ResponseData;
import com.management.stock.model.StockModel;
import com.management.stock.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController 
{
	@Autowired
	StockService stockService;

	@GetMapping("/all") public ResponseEntity<ResponseData> showAllStock() {
		List<StockModel> allStockList = stockService.getAllStock(); 
		Map<Integer,String> status = new HashMap(); 
		status.put(200, "Successfull fetch");
		ResponseData response = new ResponseData("Stock List", status, allStockList);
		return new ResponseEntity<>(response, HttpStatus.OK); 
	}


	@GetMapping("/quotes/{userId}/{symbol}/{numberOfUnits}")
	public ResponseEntity<ResponseData> getQuotation(@PathVariable("userId") Long userId, @PathVariable("symbol") String symbol, @PathVariable("numberOfUnits") int numberOfUnits) {
		StockModel stockModel = stockService.getQuotationService(userId, symbol, numberOfUnits, LocalDate.now());
		Map<Integer, String> status = new HashMap<Integer, String>(); 
		status.put(200, "Successfull fetching ");  

		ResponseData response = new ResponseData("Stock Quotes ",
				status, stockModel);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/order")
	public ResponseEntity<StockOrder> processOrder(@RequestParam("status") String status,@RequestBody StockOrder order)
	{

		return null;
	}


}
