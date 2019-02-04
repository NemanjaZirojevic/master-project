package com.reactive.project.domain;

import com.reactive.project.model.Stock;
import reactor.core.publisher.Mono;


import java.util.List;

public class CompaniesWrapper {
    public List<Stock> getData() {
        return data;
    }

    public void setData( List<Stock>  data) {
        this.data = data;
    }

    private  List<Stock> data;
}
