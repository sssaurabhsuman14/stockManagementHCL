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
	private Double calculatePrices(String symbol,Double units) {
		Double totalPrice;
		Double brockaragePrice;
		Optional<Stock> optional = stockRepository.findById(symbol);
		Stock stock=optional.get();

		brockaragePrice = calclulateBrokarage(units,stock.getPrice());
		totalPrice = units * stock.getPrice()+ brockaragePrice;

		return totalPrice;
	}

	private Double calclulateBrokarage(Double units, Double price) {
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
		//stockList=stockRepository.findAllByUserId();
		return stockList;

	}

	public StockModel getQuotationService(Long userId, String symbol, int numberOfUnits, LocalDate quotationDate ) {

		StockModel stockModel = new StockModel();

		Double fees = 0.00;
		Stock stock = new Stock();
		if(!(ObjectUtils.isEmpty(userId) && ObjectUtils.isEmpty(symbol))) {

			Optional<Stock> stockOptional = stockRepository.findById(symbol);

			if (!ObjectUtils.isEmpty(stockOptional)) {
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

}
