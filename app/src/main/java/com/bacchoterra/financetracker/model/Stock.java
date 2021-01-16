package com.bacchoterra.financetracker.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "stock_table")
public class Stock  implements Serializable {

    private String stockName; //mandatory
    private long initialTimestamp; //mandatory
    private long finalTimestamp; //mandatory after finished
    private float initialPrice; //mandatory
    private int quantity; //mandatory
    private float totalSpent; //mandatory (auto generate after initialPrice and quantity)
    private boolean isFinished; //false by default
    private float profit; // mandatory (autogenerated after isFinished becomes true)
    private String expectedTimeInvested; //optional
    private String techniqueUsed; //optional
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Stock(String stockName, long initialTimestamp, long finalTimestamp, float initialPrice, int quantity, float totalSpent, int id, String expectedTimeInvested, boolean isFinished, float profit, String techniqueUsed) {
        this.stockName = stockName;
        this.initialTimestamp = initialTimestamp;
        this.finalTimestamp = finalTimestamp;
        this.initialPrice = initialPrice;
        this.quantity = quantity;
        this.totalSpent = totalSpent;
        this.id = id;
        this.expectedTimeInvested = expectedTimeInvested;
        this.isFinished = isFinished;
        this.profit = profit;
        this.techniqueUsed = techniqueUsed;
    }

    public Stock() {
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public long getInitialTimestamp() {
        return initialTimestamp;
    }

    public void setInitialTimestamp(long initialTimestamp) {
        this.initialTimestamp = initialTimestamp;
    }

    public long getFinalTimestamp() {
        return finalTimestamp;
    }

    public void setFinalTimestamp(long finalTimestamp) {
        this.finalTimestamp = finalTimestamp;
    }

    public float getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(float initialPrice) {
        this.initialPrice = initialPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(float totalSpent) {
        this.totalSpent = totalSpent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpectedTimeInvested() {
        return expectedTimeInvested;
    }

    public void setExpectedTimeInvested(String expectedTimeInvested) {
        this.expectedTimeInvested = expectedTimeInvested;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public String getTechniqueUsed() {
        return techniqueUsed;
    }

    public void setTechniqueUsed(String techniqueUsed) {
        this.techniqueUsed = techniqueUsed;
    }
}
