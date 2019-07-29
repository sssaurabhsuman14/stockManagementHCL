package com.management.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.management.stock.service.StockService;

@RestController
public class StockController 
{
	@Autowired
	StockService stockService;

}
