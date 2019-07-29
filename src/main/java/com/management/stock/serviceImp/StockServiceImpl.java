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
import com.management.stock.model.StockModel;
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

	@Override

	public List<StockOrder> getAllStockOrders(Long userId){
		List<StockOrder> stockList=new ArrayList<>();
		stockList=stockRepository.findAllByUserId();
		return stockList;
		
	}

	public StockModel getQuotationService(Long userId, String symbol, int numberOfUnits, LocalDate quotationDate ) {

		Double fees = 0.00;
		StockModel stockModel = new StockModel();
		if(!(ObjectUtils.isEmpty(userId) && ObjectUtils.isEmpty(symbol))) {

			Optional<Stock> stock = stockRepository.findById(symbol);
			if (ObjectUtils.isEmpty(stock)) {
				if(numberOfUnits<500) {
					fees = 0.10D * numberOfUnits * stock.get().getPrice();//logic to get it according to date
				}
				else {
					fees = 0.15D * 500 * stock.get().getPrice() + (0.10D *  (numberOfUnits-500)*  stock.get().getPrice());

				}
				stockModel.setTotalCharge(stock.get().getPrice() + fees); 
				BeanUtils.copyProperties(stock, stockModel);
			}
		}

		return null;
	}
	
	@Override
	public StockOrder processOrder(String status, StockOrder order) {
		Optional<Stock> optional = stockRepository.findById(order.getSymbol());
		Stock stock=optional.isPresent()?optional.get():null;
		
		if("PENDING".equalsIgnoreCase(status)) {
						
			order.setTotalPrice(calculatePrices(stock,Double.valueOf(order.getUnits())));
			order.setBrokerageFees(calculateBrokarage(Double.valueOf(order.getUnits()), stock.getPrice()));
			order.setStatus("PENDING");
			return stockOrderRepository.save(order);
		}
		else if("CONFIRM".equalsIgnoreCase(status)) {
			order.setTotalPrice(calculatePrices(stock,Double.valueOf(order.getUnits())));
			order.setBrokerageFees(calculateBrokarage(Double.valueOf(order.getUnits()), stock.getPrice()));
			order.setStatus("CONFIRM");
		return	stockOrderRepository.save(order);
		}
		return null;
	}

}
