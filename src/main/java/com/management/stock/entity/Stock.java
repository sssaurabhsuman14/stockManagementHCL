package com.management.stock.entity;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock implements Serializable{

	private static final long serialVersionUID = -1563307638290991874L;
	
	@Id
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


}
