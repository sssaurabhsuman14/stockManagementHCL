package com.management.stock.model;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.management.stock.entity.Stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

	
	private String responseMessage;
	private Map<Integer, String> status;
	private Object data;

}
