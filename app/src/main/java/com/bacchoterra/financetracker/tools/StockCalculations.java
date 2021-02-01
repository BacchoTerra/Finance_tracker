package com.bacchoterra.financetracker.tools;

public class StockCalculations {


    public static Float calculateProfit(float initialPrice,float finalPrice,int quantity){

        return (finalPrice * quantity) - (initialPrice *quantity);


    }

    public static float calculateNewAveragePrice(float oldPrice,int oldQuantity,int newQuantity,float newPrice){


        return ((oldPrice * oldQuantity) + (newPrice * newQuantity)) / (oldQuantity + newQuantity);
    }

}
