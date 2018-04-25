package com.reactive.project;

import com.reactive.project.model.Stock;
import com.reactive.project.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.stream.Stream;


@SpringBootApplication
public class ProjectApplication {

	@Autowired
	StockRepository stockRepository;


	@Bean
	CommandLineRunner stocks(StockRepository stockRepository)
	{
		return args ->
				stockRepository
						.deleteAll()
						.subscribe(null,null,() ->{

							Stream.of(new Stock("1",200,"Google"),new Stock("2",300,"Yahoo"),new Stock("3",200,"Amazon"),new Stock("4",400,"Microsoft"))
									       .forEach(stock ->
											stockRepository
													.save(stock)
													.subscribe(System.out::println));

						});
	}


	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);

	}



}
