package com.management.stock.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.management.stock.entity.Stock;
import com.management.stock.entity.StockOrder;
import com.management.stock.exception.StockException;
import com.management.stock.model.StockModel;
import com.management.stock.model.StockOrderModel;
import com.management.stock.repository.StockOrderRepository;
import com.management.stock.repository.StockRepository;
import com.management.stock.serviceImp.StockServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest 
{

	@InjectMocks
	StockServiceImpl stockServiceImpl;
	
	@Mock
	StockRepository stockRepositoryMock;
	
	@Mock
	StockOrderRepository stockOrderRepositoryMock;
	
	List<StockModel> stockModelList = new ArrayList<>();
	List<Stock> stockList = new ArrayList<>();
	StockModel model1 = new StockModel();
	Stock stock1 = new Stock();
	Optional<Stock> stockOptional = Optional.of(stock1);
	StockOrder order1 = new StockOrder();
	StockOrderModel orderModel1 = new StockOrderModel();
	
	@Before
	public void setUp()
	{
		model1.setSymbol("SBI");
		stockModelList.add(model1);
		
		stock1.setSymbol("SBI");
		stock1.setPrice(100.00);
		stockList.add(stock1);
		
		
		order1.setSymbol("SBI");
		order1.setStockOrderId(1L);
		order1.setStatus("PENDING");
		order1.setUnits(500);
		
		orderModel1.setSymbol("SBI");
		orderModel1.setStockOrderId(1L);
		orderModel1.setStatus("PENDING");
		orderModel1.setUnits(500);
	}
	
	@Test
	public void testGetAllStockSuccess() throws StockException
	{
		Mockito.when(stockRepositoryMock.findAll()).thenReturn(stockList);
		List<StockModel> allStockModelList = stockServiceImpl.getAllStock();
		
		
		assertNotNull(allStockModelList);
		assertEquals(1, allStockModelList.size());
	}
	
	@Test(expected = StockException.class)
	public void testGetAllStockFailure() throws StockException
	{
		stockList.remove(0);
		Mockito.when(stockRepositoryMock.findAll()).thenReturn(stockList);
		stockServiceImpl.getAllStock();

	}
	
	@Test(expected = StockException.class)
	public void testProcessOrderFailure() throws StockException
	{
		Mockito.when(stockRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		stockServiceImpl.processOrder("CONFIRM", orderModel1);
		
	}
	
	@Test
	public void testProcessOrderSuccessPending() throws StockException
	{
		Optional<Stock> stockOptional = Optional.of(stock1);
		Mockito.when(stockRepositoryMock.findById(Mockito.anyString())).thenReturn(stockOptional);
		Mockito.when(stockOrderRepositoryMock.save(Mockito.any(StockOrder.class))).thenReturn(order1);
		
		StockOrderModel processOrderModel = stockServiceImpl.processOrder("PENDING", orderModel1);
		
		assertNotNull(processOrderModel);
		assertEquals("PENDING", processOrderModel.getStatus());
	}
	
	@Test
	public void testProcessOrderSuccessConfirm() throws StockException
	{
		order1.setStatus("CONFIRM");
		orderModel1.setStatus("CONFIRM");
		Optional<Stock> stockOptional = Optional.of(stock1);
		Mockito.when(stockRepositoryMock.findById(Mockito.anyString())).thenReturn(stockOptional);
		Mockito.when(stockOrderRepositoryMock.save(Mockito.any(StockOrder.class))).thenReturn(order1);
		
		StockOrderModel processOrderModel = stockServiceImpl.processOrder("CONFIRM", orderModel1);
		
		assertNotNull(processOrderModel);
		assertEquals("CONFIRM", processOrderModel.getStatus());
	}
	
	@Test
	public void testProcessOrderSuccessUnitGreaterThan500() throws StockException
	{
		order1.setUnits(700);

		orderModel1.setUnits(700)
		;
		Optional<Stock> stockOptional = Optional.of(stock1);
		Mockito.when(stockRepositoryMock.findById(Mockito.anyString())).thenReturn(stockOptional);
		Mockito.when(stockOrderRepositoryMock.save(Mockito.any(StockOrder.class))).thenReturn(order1);
		
		StockOrderModel processOrderModel = stockServiceImpl.processOrder("PENDING", orderModel1);
		
		assertNotNull(processOrderModel);
		assertEquals("PENDING", processOrderModel.getStatus());
	}
	@Test
	public void testGetQuotationService() {
		Stock stock = new Stock();
		stock.setPrice(100.00);
		stock.setName("ABC");
		stock.setSymbol("SBIO");
		Optional<Stock> stockOptional =  Optional.of(stock);

		Mockito.when(stockRepositoryMock.findById("SBIO")).thenReturn(stockOptional);
		StockModel stockModel = stockServiceImpl.getQuotationService(1L, "SBIO",  100, LocalDate.now());
		assertNotNull(stockModel);
		assertEquals(stockOptional.get().getName(),stockModel.getName());
	}
}

