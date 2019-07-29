package com.management.stock.exception;

import org.springframework.stereotype.Component;

@Component
public class StockException extends Exception{

	public StockException(String arg0) {
		super(arg0);
	}

	
}
