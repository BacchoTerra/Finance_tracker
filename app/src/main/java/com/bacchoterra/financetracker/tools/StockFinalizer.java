package com.bacchoterra.financetracker.tools;

import com.bacchoterra.financetracker.model.Stock;

import java.text.DecimalFormat;
import java.util.Calendar;

public class StockFinalizer {

    private Stock stock;

    public StockFinalizer(Stock stock) {
        this.stock = stock;
    }

    public Stock buildFinalizedStock(String price){

        long finalTimeStamp = System.currentTimeMillis();
        float finalPrice = Float.parseFloat(price);
        float profit = calculateProfit(finalPrice);
        stock.setFinalPrice(finalPrice);
        stock.setProfit(profit);
        stock.setFinalTimestamp(finalTimeStamp);
        stock.setFinished(true);


        return stock;

    }

    private float calculateProfit(float finalPrice){

        return (finalPrice * stock.getQuantity()) - (stock.getAveragePrice() * stock.getQuantity());


    }
}
