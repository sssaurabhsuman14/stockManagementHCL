package com.management.stock.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.management.stock.model.StockHistoryModel;
import com.management.stock.model.StockDTO;
import com.management.stock.model.StockOrderModel;
import com.management.stock.repository.StockOrderRepository;
import com.management.stock.repository.StockRepository;
import com.management.stock.serviceimpl.StockServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest 
{

	@InjectMocks
	StockServiceImpl stockServiceImpl;
	
	@Mock
	StockRepository stockRepositoryMock;
	
	@Mock
	StockOrderRepository stockOrderRepositoryMock;
	
	List<StockDTO> stockModelList = new ArrayList<>();
	List<Stock> stockList = new ArrayList<>();
	StockDTO model1 = new StockDTO();
	Stock stock1 = new Stock();
	Optional<Stock> stockOptional = Optional.of(stock1);
	StockOrder order1 = new StockOrder();
	StockOrderModel orderModel1 = new StockOrderModel();
    Optional<StockOrder> optionalList =Optional.of(order1);
	List<StockHistoryModel> stockOrderList;
	List<StockOrder> stockOrder;
	StockHistoryModel model;
	
	StockOrderModel orderModel;
	
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
		
		stockOrderList =new ArrayList<>();
		model=new StockHistoryModel();
		model.setStockOrderId(123L);
		model.setTotalPrice(345.5);
		model.setUnits(23);
		stockOrderList.add(model);
		
		stockOrder =new ArrayList<>();
	    orderModel=new StockOrderModel();
		orderModel.setBookingPrice(1234);
		orderModel.setBrokerageFees(1234);
		orderModel.setStatus("completed");
		orderModel.setStockOrderId(1L);
		orderModel.setSymbol("SBI");
		orderModel.setTotalPrice(1234);
		orderModel.setUnits(23);
		orderModel.setUserId(1L);
		stockOrder.add(order1);
	}
	
	@Test
	public void testGetAllStockSuccess() throws StockException
	{
		Mockito.when(stockRepositoryMock.findAll()).thenReturn(stockList);
		List<StockDTO> allStockModelList = stockServiceImpl.getAllStock();
		
		
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
	public void testGetAllStockOrders() throws StockException
	{
		Long userId=1L;
		Mockito.when(stockOrderRepositoryMock.findAllByUserId(userId)).thenReturn(stockOrder);
		List<StockHistoryModel> stockHistoryList=stockServiceImpl.getAllStockOrders(userId);
		assertNotNull(stockHistoryList);
		
	}
	
	@Test(expected = StockException.class)
	public void testGetAllStockOrdersFailure() throws StockException
	{
		stockServiceImpl.getAllStockOrders(null);
	}

	@Test
	public void testGetStockOrder() throws StockException {
		
		Long stockOrderId=1L;
		Mockito.when(stockOrderRepositoryMock.findById(stockOrderId)).thenReturn(optionalList);
		orderModel1=stockServiceImpl.getStockOrder(stockOrderId);
		assertNotNull(orderModel1);
	}
	
	@Test(expected = StockException.class)
	public void testGetStockOrderFailure() throws StockException
	{
		stockServiceImpl.getStockOrder(null);
	}
	
	@Test
	public void testSaveStock()
	{
		StockDTO stockDTO = new StockDTO();
		stockDTO.setSymbol("SBI");
		
		Stock stock123 = new Stock();
		stock123.setSymbol("SBI");
		
		Mockito.when(stockRepositoryMock.save(Mockito.any(Stock.class))).thenReturn(stock123);
		
		StockDTO saveStock = stockServiceImpl.saveStock(stockDTO);
		
		assertNotNull(saveStock);
		assertEquals("SBI", saveStock.getSymbol());
	}
	
	@Test
	public void testGetQuotationServiceSuccess() {
		Stock stock = new Stock();
		stock.setPrice(100.00);
		stock.setName("ABC");
		stock.setSymbol("SBIO");
		Optional<Stock> stockOptional =  Optional.of(stock);

		Mockito.when(stockRepositoryMock.findById("SBIO")).thenReturn(stockOptional);
		StockDTO stockDTO = stockServiceImpl.getQuotationService(1L, "SBIO",  100, LocalDate.now());
		assertNotNull(stockDTO);
		assertEquals(stockOptional.get().getName(),stockDTO.getName());
	}
	
	@Test
	public void testGetQuotationServiceSuccessUnitsGreaterThan500() {
		Stock stock = new Stock();
		stock.setPrice(100.00);
		stock.setName("ABC");
		stock.setSymbol("SBIO");
		Optional<Stock> stockOptional =  Optional.of(stock);

		Mockito.when(stockRepositoryMock.findById("SBIO")).thenReturn(stockOptional);
		StockDTO stockDTO = stockServiceImpl.getQuotationService(1L, "SBIO",  500, LocalDate.now());
		assertNotNull(stockDTO);
		assertEquals(stockOptional.get().getName(),stockDTO.getName());
	}
}


