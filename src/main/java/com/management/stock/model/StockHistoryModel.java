package com.management.stock.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockHistoryModel {
	
	private Long stockOrderId;
	private int units;
	private double totalPrice;
	
	
}
