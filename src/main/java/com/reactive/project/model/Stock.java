package com.reactive.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document
public class Stock {

    @Id
    private String id;

    private int price;

    private String name;


    public Stock()
    {

    }

    public Stock(String id, int price,String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
