package com.management.stock.model;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockHistoryModel {
	
	private String symbol;
	private int units;
	private double totalPrice;
	
	List<StockHistoryModel> stockOrderList;
}
