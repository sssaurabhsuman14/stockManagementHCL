package com.management.stock.exception;

import org.springframework.stereotype.Component;

@Component
public class StockException extends Exception{

	public StockException(String arg0) {
		super(arg0);
	}

	public StockException() {
		super();

	}

	public StockException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public StockException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public StockException(Throwable arg0) {
		super(arg0);
	}

	
	
}
