package com.reactive.project.service;

import com.reactive.project.model.Stock;
import com.reactive.project.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StockService {


    @Autowired
    StockRepository stockRepository;


    public Mono<Stock> findById(String stockId){
        return stockRepository.findById(stockId);
    }

    public void save(Stock stock)
    {
        stockRepository.save(stock).subscribe(System.out::println);
    }

    public void delete(Stock stock)
    {
        stockRepository.deleteById(stock.getId()).subscribe(System.out::println);
    }

    public Flux<Stock> saveAll(Flux<Stock> stocks)
    {
        return stockRepository.saveAll(stocks);
    }

    public Flux<Stock> findAll()
    {
        return stockRepository.findAll();
    }

    public Mono<Stock> findOneById(Stock stock)
    {
        return stockRepository.findOneById(stock.getId());
    }
}
