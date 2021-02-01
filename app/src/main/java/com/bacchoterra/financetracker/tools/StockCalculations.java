package com.bacchoterra.financetracker.tools;

public class StockCalculations {


    public static Float calculateProfit(float initialPrice,float finalPrice,int quantity){

        return (finalPrice * quantity) - (initialPrice *quantity);


    }

}
