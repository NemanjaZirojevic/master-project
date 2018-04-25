package com.reactive.project.repository;

import com.reactive.project.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock,String> {


}
