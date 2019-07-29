package com.management.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockOrder {

	@Id
	@Column(name="stock_oder_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stockOrderId;
	
	@Column(name="user_id")
	private Long userId;
	
	//stock reference id
	@Column(name="symbol")
	private String symbol;
	
	@Column(name="units")
	private int units;
	
	@Column(name="booking_price")
	private double bookingPrice;
	
	@Column(name="brokerage_fees")
	private double brokerageFees;
	
	@Column(name="total_price")
	private double totalPrice;
	
	@Column(name="status")
	private String status;
	
}
