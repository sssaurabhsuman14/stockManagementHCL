package com.management.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.management.stock.exception.StockException;
import com.management.stock.model.ResponseData;
import com.management.stock.model.StockHistoryModel;
import com.management.stock.model.StockModel;
import com.management.stock.model.StockOrderModel;
import com.management.stock.service.StockService;

public class UserController {

	@Autowired
	StockService stockService;
	
	@GetMapping("/history")
	public ResponseEntity<ResponseData> getHistory(@RequestParam("User_id")Long userId) throws StockException {
		if(Objects.isNull(userId))
			throw new StockException("User Id cannot be null");
		else
		{
			List<StockHistoryModel> stockOrderList=stockService.getAllStockOrders(userId);
			Map<Integer, String> status = new HashMap();
			status.put(200, "Successfully fetch");
			ResponseData response=new ResponseData("Please find below stock order history of userId: "+userId,status,stockOrderList);
			return new ResponseEntity<>(response, HttpStatus.OK);	
			}
		}
	
	@GetMapping("/history/{symbol}")
	public ResponseEntity<ResponseData> getHistory(@PathVariable("symbol")String symbol,@RequestParam() Long userId) throws StockException {
		if(symbol.isEmpty())
			throw new StockException("User Id cannot be null");
		else
		{
			StockOrderModel stockModel=stockService.getStockOrder(userId,symbol);
			Map<Integer, String> status = new HashMap();
			status.put(200, "Successfully fetch");
            ResponseData response=new ResponseData("Please find below stock order history of userId: "+userId,status,stockModel);
        	return new ResponseEntity<>(response, HttpStatus.OK);
				
			}
		}

}
