package com.reactive.project.model;

public class StockEvent {
    Stock stock;
    int stockPrice;

    public StockEvent(Stock stock, int stockPrice) {
        this.stock = stock;
        this.stockPrice = stockPrice;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(int stockPrice) {
        this.stockPrice = stockPrice;
    }
}
