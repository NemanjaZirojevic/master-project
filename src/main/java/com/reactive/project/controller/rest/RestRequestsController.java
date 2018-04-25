package com.reactive.project.controller.rest;


import com.reactive.project.model.StockEvent;
import com.reactive.project.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;


@EnableMongoRepositories
@RestController
@RequestMapping("/rest")
public class RestRequestsController {



    @Autowired
    StockRepository stockRepository;



    @RequestMapping(value = "/stock/events/{stockId}",method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<StockEvent> getStockPrice(@PathVariable("stockId") String stockId)
    {
        Random rand = new Random();
        return stockRepository.findById(stockId)
                           .flatMapMany(stock -> {
                            Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
                            Flux<StockEvent> stockEventFlux = Flux.fromStream(Stream.generate(()-> new StockEvent(stock,rand.nextInt(98) + 1)));
                            return Flux.zip(interval,stockEventFlux).map(Tuple2::getT2);
                        });
    };

}
