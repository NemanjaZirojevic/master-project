package com.reactive.project.model;

import java.util.Date;

public class Stock {

    private int price;

    private Date date;

    public Stock(int price,Date date)
    {
        this.price = price;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
