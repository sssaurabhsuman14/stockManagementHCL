package com.management.stock.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_orders")
public class StockOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3023282230694055977L;

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
