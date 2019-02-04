package com.reactive.project.controller.reactive;

import com.reactive.project.domain.CompaniesWrapper;
import com.reactive.project.model.Stock;
import com.reactive.project.model.StockEvent;
import com.reactive.project.repository.StockRepository;
import com.reactive.project.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest")
public class ReactiveRequestsController {

    @Autowired
    StockService stockService;

    @Autowired
    StockRepository stockRepository;

    @RequestMapping(value = "/stock/events/{stockId}",method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StockEvent> getStockPrice(@PathVariable("stockId") String stockId)
    {
        Random rand = new Random();
        return stockService.findById(stockId)
                .flatMapMany(stock -> {
                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
                    Flux<StockEvent> stockEventFlux = Flux.fromStream(Stream.generate(()-> new StockEvent(stock,rand.nextInt(98) + 1)));
                    return Flux.zip(interval,stockEventFlux).map(Tuple2::getT2);
                });
    };

    @RequestMapping(value = "/stocks/findAll",method = RequestMethod.GET)
    public CompaniesWrapper findAllStocks()
    {
        CompaniesWrapper companiesWrapper = new CompaniesWrapper();
        Flux<Stock> stockFlux = stockService.findAll();
        Mono<List<Stock>> stockList= stockFlux.collectList();
        companiesWrapper.setData(stockList.block());
        return companiesWrapper;
    }

    @RequestMapping(value = "/stocks/add/stock",method = RequestMethod.POST)
    public int updateStock(@RequestBody Stock stock)
    {
        Mono<Stock> stockMono = stockService.findById(stock.getId());
        Stock stock1 = stockMono.block();
        System.out.println(stock1);
        if(stock1!=null)
        {
            return -1;
        }else {
            stock.setPrice(400);
            stockService.save(stock);
        }return 1;
    }

    @RequestMapping(value = "/stocks/delete/stock",method = RequestMethod.DELETE)
    public void deleteStock(@RequestBody Stock stock)
    {

        stockService.delete(stock);
    }

}
