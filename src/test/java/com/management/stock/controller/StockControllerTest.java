package com.management.stock.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.management.stock.exception.StockException;
import com.management.stock.model.ResponseData;
import com.management.stock.model.StockModel;
import com.management.stock.service.StockService;

@RunWith(MockitoJUnitRunner.class)
public class StockControllerTest 
{
	@InjectMocks
	StockController stockController;
	
	@Mock
	StockService stockServiceMock;
	
	List<StockModel> stockModelList = new ArrayList<>();
	StockModel model1 = new StockModel();
	
	@Before
	public void setUp()
	{
		model1.setSymbol("SBI");
		stockModelList.add(model1);
		
	}

	
	@Test
	public void testShowAllStock() throws StockException
	{
		Mockito.when(stockServiceMock.getAllStock()).thenReturn(stockModelList);
		ResponseEntity<ResponseData> response = stockController.showAllStock();
		
		List<StockModel> modelList = (List<StockModel>) response.getBody().getData();
		assertNotNull(response);
		assertEquals(1, modelList.size());
		assertEquals(true, response.getBody().getStatus().containsKey(200));
		
	}

}
