package com.bacchoterra.financetracker.model;

public class StockInformation {

    private String stockInfoTitle;
    private String stockInfoContent;
    private int infoDrawable;

    public StockInformation(String stockInfoField, String stockInfoContent, int infoDrawable) {
        this.stockInfoTitle = stockInfoField;
        this.stockInfoContent = stockInfoContent;
        this.infoDrawable = infoDrawable;
    }

    public StockInformation() {
    }

    public String getStockInfoTitle() {
        return stockInfoTitle;
    }

    public void setStockInfoTitle(String stockInfoTitle) {
        this.stockInfoTitle = stockInfoTitle;
    }

    public String getStockInfoContent() {
        return stockInfoContent;
    }

    public void setStockInfoContent(String stockInfoContent) {
        this.stockInfoContent = stockInfoContent;
    }

    public int getInfoDrawable() {
        return infoDrawable;
    }

    public void setInfoDrawable(int infoDrawable) {
        this.infoDrawable = infoDrawable;
    }
}
