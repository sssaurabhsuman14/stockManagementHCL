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
	public List<StockModel> getAllStock() 
	{
		List<StockModel> stockModelList = new ArrayList<>();

		List<Stock> stockList = stockRepository.findAll();

		if(stockList.isEmpty())
		{}
		else
			stockModelList = entityListToModelList(stockList, stockModelList);

		return stockModelList;
	}

	@Override
	public List<StockModel> entityListToModelList(List<Stock> stockList, List<StockModel> stockModelList) 
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

			 Double fees = 0.00;
			 Stock stock = new Stock();
			 if(!(ObjectUtils.isEmpty(userId) && ObjectUtils.isEmpty(symbol))) {

			  Optional<Stock> stockOptional = stockRepository.findById(symbol);

			  if (stockOptional.isPresent()) {
			   stock = stockOptional.get();
			  }
			  if(numberOfUnits<500) {
			   fees = (0.10D * numberOfUnits * stock.getPrice())/100;//logic to get it according to date
			  }
			  else {
			   fees = (0.15D * 500 * stock.getPrice() + (0.10D *  (numberOfUnits-500)*  stock.getPrice()))/100;

			  }

			  BeanUtils.copyProperties(stock, stockModel);
			  stockModel.setTotalCharge(stock.getPrice()+ fees);

			 }

			 return stockModel;
			}


	@Override
	public StockOrderModel processOrder(String status, StockOrder order) throws StockException {
		Stock stock = new Stock();
		
		Optional<Stock> optionalStock = stockRepository.findById(order.getSymbol());
		
		boolean isOptionalPresent = optionalStock.isPresent();
		
		if(isOptionalPresent)
		{
			stock = optionalStock.get();
		}
		else
		{
			throw new StockException("Stock is not found with Id : "+order.getSymbol());
		}

		if("PENDING".equalsIgnoreCase(status) && stock!=null) {
						
			order.setTotalPrice(calculatePrices(stock,Double.valueOf(order.getUnits())));
			order.setBrokerageFees(calculateBrokarage(Double.valueOf(order.getUnits()), stock.getPrice()));
			order.setStatus("PENDING");
			 BeanUtils.copyProperties(stockOrderRepository.save(order),new StockOrderModel());
		}
		else if("CONFIRM".equalsIgnoreCase(status) && stock!=null) {
			order.setTotalPrice(calculatePrices(stock,Double.valueOf(order.getUnits())));
			order.setBrokerageFees(calculateBrokarage(Double.valueOf(order.getUnits()), stock.getPrice()));
			order.setStatus("CONFIRM");
			BeanUtils.copyProperties(stockOrderRepository.save(order),new StockOrderModel());
		}
		return null;
	}
	@Override
	public List<StockHistoryModel> getAllStockOrders(Long userId) throws StockException{
		List<StockHistoryModel> stockList=new ArrayList<>();
		
		stockList=stockOrderRepository.findAllByUserId(userId);
		if(stockList.isEmpty())
			throw new StockException("There is no stock order history for userId : "+userId);
		return stockList;
		
	}
	@Override
	public StockOrderModel getStockOrder(Long userId,String symbol) throws StockException{
		
		StockOrderModel stockModel=stockOrderRepository.findByUserIdAndSymbol(userId,symbol);
		
		if(stockModel==null)
			throw new StockException("There is no stock order history for userId : "+userId);
		return stockModel;
		

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


