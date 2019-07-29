package com.management.stock.serviceImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.management.stock.entity.Stock;
import com.management.stock.entity.StockOrder;
import com.management.stock.exception.StockException;
import com.management.stock.model.StockHistoryModel;
import com.management.stock.model.StockModel;
import com.management.stock.model.StockOrderModel;
import com.management.stock.repository.StockOrderRepository;
import com.management.stock.repository.StockRepository;
import com.management.stock.service.StockService;

@Service
public class StockServiceImpl implements StockService{

	@Autowired
	StockRepository stockRepository;

	
	@Autowired
	StockOrderRepository stockOrderRepository;

	@Override
	public List<StockModel> getAllStock() throws StockException 
	{
		List<StockModel> stockModelList = new ArrayList<>();

		List<Stock> stockList = stockRepository.findAll();

		if(stockList.isEmpty())
			throw new StockException("N stocks are available");
		else
			stockModelList = entityListToModelList(stockList, stockModelList);

		return stockModelList;
	}

	
	private List<StockModel> entityListToModelList(List<Stock> stockList, List<StockModel> stockModelList) 
	{
		for(Stock entity : stockList)
		{
			StockModel model = new StockModel();
			BeanUtils.copyProperties(entity, model);
			stockModelList.add(model);
		}
		return stockModelList;

	}


	//calculate Price for quote as well as order confirmation
		private Double calculatePrices(Stock stock,Double units) {
			Double totalPrice;
			Double brockaragePrice;
			brockaragePrice = calculateBrokarage(units,stock.getPrice());
			totalPrice = units * stock.getPrice()+ brockaragePrice;
		
			return totalPrice;
		}

		private Double calculateBrokarage(Double units, Double price) {
			Double brokarageAmount =null;
			if(units<= 500) {
				brokarageAmount= ((0.15D * (units*price))/100);
			}
			else if(units>500)
			{
				brokarageAmount =  ((0.15D * (500*price))/100);
				units=units-500;
				brokarageAmount = brokarageAmount + ((0.10D * (units*price))/100);
			}
			return brokarageAmount;
		}


	

		public StockModel getQuotationService(Long userId, String symbol, int numberOfUnits, LocalDate quotationDate ) {

			 StockModel stockModel = new StockModel();
			 Double fees;
			 Stock stock = new Stock();
			 if(!(ObjectUtils.isEmpty(userId) && ObjectUtils.isEmpty(symbol))) {

			  Optional<Stock> stockOptional = stockRepository.findById(symbol);

			  if (stockOptional.isPresent()) {
			   stock = stockOptional.get();
			  }
			  if(numberOfUnits<500) {
			   fees = (0.10D *(numberOfUnits * stock.getPrice()))/100;//logic to get it according to date
			  }
			  else {
			   fees = (0.15D *( 500 * stock.getPrice()) + (0.10D * ((numberOfUnits-500)*  stock.getPrice())))/100;

			  }

			  BeanUtils.copyProperties(stock, stockModel);
			  stockModel.setTotalCharge((numberOfUnits*stock.getPrice())+ fees);

			 }

			 return stockModel;
			}


	@Override
	public StockOrderModel processOrder(String status, StockOrderModel order) throws StockException {
		Stock stock;
		StockOrder stockOrder = new StockOrder();
		BeanUtils.copyProperties(order,stockOrder);
		Optional<Stock> optionalStock = stockRepository.findById(order.getSymbol());
		
		boolean isOptionalPresent = optionalStock.isPresent();
		
		if(isOptionalPresent)
		{
			stock = optionalStock.get();
			
			if("PENDING".equalsIgnoreCase(status) ) {
				
				stockOrder.setTotalPrice(calculatePrices(stock,Double.valueOf(stockOrder.getUnits())));
				stockOrder.setBrokerageFees(calculateBrokarage(Double.valueOf(stockOrder.getUnits()), stock.getPrice()));
				stockOrder.setStatus("PENDING");
				 BeanUtils.copyProperties(stockOrderRepository.save(stockOrder),new StockOrderModel());
			}
			else if("CONFIRM".equalsIgnoreCase(status) ) {
				stockOrder.setTotalPrice(calculatePrices(stock,Double.valueOf(stockOrder.getUnits())));
				stockOrder.setBrokerageFees(calculateBrokarage(Double.valueOf(stockOrder.getUnits()), stock.getPrice()));
				stockOrder.setStatus("CONFIRM");
				BeanUtils.copyProperties(stockOrderRepository.save(stockOrder),new StockOrderModel());
			}
		}
		else
		{
			throw new StockException("Stock is not found with Id : "+order.getSymbol());
		}


		return order;
	}
	@Override
	public List<StockHistoryModel> getAllStockOrders(Long userId) throws StockException{
		List<StockHistoryModel> stockModelList=new ArrayList<>();
		List<StockOrder> stockList=stockOrderRepository.findAllByUserId(userId);
		if(stockList.isEmpty())
			throw new StockException("There is no stock order history for userId : "+userId);
		else {
			for(StockOrder stockOrder:stockList)
			{
				StockHistoryModel stockModel=new StockHistoryModel();
				stockModel.setStockOrderId(stockOrder.getStockOrderId());
				stockModel.setTotalPrice(stockOrder.getTotalPrice());
				stockModel.setUnits(stockOrder.getUnits());
				stockModelList.add(stockModel);
			}
		   
		}
		return stockModelList;
		
	}
	@Override
	public StockOrderModel getStockOrder(Long stockOrderId) throws StockException{
		
		Optional<StockOrder> stockOrderList =stockOrderRepository.findById(stockOrderId);
		if(stockOrderList.isPresent()) {
		StockOrderModel stackOrderModel=new StockOrderModel();
		StockOrder stockOrder= stockOrderList.get();
		BeanUtils.copyProperties(stockOrder, stackOrderModel);
		return stackOrderModel;
		}
		else
			throw new StockException("There is no stock order history for userId : "+stockOrderId);
		
		

}

	@Override
	public void saveStock(StockModel model) {
	 if (model != null) {
	  Stock entity = new Stock();
	  BeanUtils.copyProperties(model, entity);
	  stockRepository.save(entity);
	 }

	}
		

}


