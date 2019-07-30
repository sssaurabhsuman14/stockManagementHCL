package com.management.stock.schedular;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.management.stock.exception.StockException;
import com.management.stock.model.StockDTO;
import com.management.stock.service.StockService;

@Component
public class StockSchedular {

@Autowired
StockService stockService;

@Scheduled(fixedRate = 2000)
public void scheduleTaskWithFixedRate() throws  StockException, NoSuchAlgorithmException {

 List<StockDTO> entityList = stockService.getAllStock();

 if (!entityList.isEmpty()) {
  for (StockDTO model : entityList) {

   Random random = SecureRandom.getInstanceStrong();
   double randomNumber = (random.nextInt(655) - 327);

   if (randomNumber < 0) {
    randomNumber = randomNumber * (-1);
    model.setPrice(randomNumber);
    stockService.saveStock(model);
   }

  }
 }
}
}