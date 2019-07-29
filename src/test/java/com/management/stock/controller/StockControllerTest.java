package com.management.stock.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.management.stock.exception.StockException;
import com.management.stock.model.ResponseData;
import com.management.stock.model.StockModel;
import com.management.stock.model.StockOrderModel;
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


	@Test
	public void testProcessOrder() throws StockException {
		StockOrderModel model = new StockOrderModel();
		Mockito.when(stockServiceMock.processOrder(Matchers.any(String.class),Matchers.any(StockOrderModel.class))).thenReturn(model);
		ResponseEntity<ResponseData> response = stockController.processOrder("CONFIRM",new StockOrderModel());
		assertNotNull(response);
		assertEquals(true,response.getBody().getStatus().containsKey(200));
	}

	@Test 
	public void getQuotation() {


		StockModel stockModel = new StockModel(); 
		stockModel.setPrice(100.00);
		stockModel.setSymbol("SBI");

		Mockito.when(stockServiceMock.getQuotationService(1L, "SBI", 100, LocalDate.now())).thenReturn(stockModel);
		ResponseEntity<ResponseData> response = stockController.getQuotation(1L, "SBI", 100);
		assertNotNull(response); 
		StockModel model = (StockModel) response.getBody().getData();
		assertEquals("SBI", model.getSymbol());

	}


}
