package com.reactive.project.repository;

import com.reactive.project.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock,String> {
  Flux<Stock> findAll();
  Mono<Stock> save(Stock stock);
  Mono<Void> deleteById(String id);
  Mono<Stock> findOneById(String id);
}
