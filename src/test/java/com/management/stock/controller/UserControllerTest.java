package com.management.stock.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

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
import com.management.stock.model.StockHistoryModel;
import com.management.stock.model.StockModel;
import com.management.stock.model.StockOrderModel;
import com.management.stock.service.StockService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	@Mock
	StockService stockService;
	
	List<StockHistoryModel> stockOrderList;
	StockHistoryModel model;
	
	StockOrderModel orderModel;
	@Before
	public void setup() {
		stockOrderList =new ArrayList<>();
		 model=new StockHistoryModel();
		model.setStockOrderId(123L);
		model.setTotalPrice(345.5);
		model.setUnits(23);
		stockOrderList.add(model);
		
	    orderModel=new StockOrderModel();
		orderModel.setBookingPrice(1234);
		orderModel.setBrokerageFees(1234);
		orderModel.setStatus("completed");
		orderModel.setStockOrderId(1L);
		orderModel.setSymbol("SBI");
		orderModel.setTotalPrice(1234);
		orderModel.setUnits(23);
		orderModel.setUserId(1L);
		
	}
	@Test
	public void testGetAllHistory() throws StockException {
		Long userId=1L;
		Mockito.when(stockService.getAllStockOrders(userId)).thenReturn(stockOrderList);
		ResponseEntity<ResponseData> response = userController.getAllHistory(userId);
		List<StockHistoryModel> modelList = (List<StockHistoryModel>) response.getBody().getData();
		assertNotNull(response);
		
	}
	
	@Test(expected = StockException.class)
	public void testGetAllHistoryFailure() throws StockException {
		ResponseEntity<ResponseData> response = userController.getAllHistory(null);
	}
	
	@Test(expected = StockException.class)
	public void testHistoryOrderFailure() throws StockException {
		ResponseEntity<ResponseData> response = userController.getHistory(null);
	}
	
	@Test
	public void testHistoryOrder() throws StockException {
		Long stockOrderId=1L;
		Mockito.when(stockService.getStockOrder(stockOrderId)).thenReturn(orderModel);
		ResponseEntity<ResponseData> response = userController.getHistory(stockOrderId);
		StockOrderModel model = (StockOrderModel) response.getBody().getData();
		assertNotNull(response);
	}

}
