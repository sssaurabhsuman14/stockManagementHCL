package com.management.stock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockOrderModel {
	private Long stockOrderId;
	private Long userId;
	//stock reference id
	private String symbol;
	private int units;
	private double bookingPrice;
	private double brokerageFees;
	private double totalPrice;
	private String status;
}
