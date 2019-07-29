package com.management.stock.service;

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

import com.management.stock.entity.Stock;
import com.management.stock.exception.StockException;
import com.management.stock.model.StockModel;
import com.management.stock.repository.StockRepository;
import com.management.stock.serviceImp.StockServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest 
{

	@InjectMocks
	StockServiceImpl stockServiceImpl;
	
	@Mock
	StockRepository stockRepositoryMock;
	
	List<StockModel> stockModelList = new ArrayList<>();
	List<Stock> stockList = new ArrayList<>();
	StockModel model1 = new StockModel();
	Stock stock1 = new Stock();
	
	@Before
	public void setUp()
	{
		model1.setSymbol("SBI");
		stockModelList.add(model1);
		
		stock1.setSymbol("SBI");
		stockList.add(stock1);
		
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
}