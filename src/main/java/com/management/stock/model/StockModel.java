package com.management.stock.model;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockModel 
{
	
	private String  symbol;
	private String  name;
	private String  type;
	private String  region;
	private LocalTime  marketOpen;
	private LocalTime  marketClose;
	private String  timezone;
	private String  currency;
	private Double  matchScore;
	private Double  price;
	private Double totalCharge;

}
