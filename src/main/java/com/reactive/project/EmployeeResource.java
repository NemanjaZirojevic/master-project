package com.reactive.project;

import com.reactive.project.model.Employee;
import com.reactive.project.model.EmployeeEvent;
import com.reactive.project.model.Stock;
import com.reactive.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest/employees")
public class EmployeeResource
{

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/all")
    Flux<Employee>getAll()
    {
     return  employeeRepository.findAll();

    }

    @GetMapping("/{id}")
    Mono<Employee> getById(@PathVariable("id") final String id)
    {
        return employeeRepository.findById(id);
    }

    @GetMapping(value = "/events",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Stock> getEvents()
    {
        return employeeRepository.findById("testId")
        .flatMapMany(employee -> {
            Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
            Flux<Stock> stockEventFlux =
                    Flux.fromStream(
                            Stream.generate(()->new Stock(getRandomNumber(0,100),new Date()))
                    );

          return    Flux.zip(interval,stockEventFlux)
                    .map(Tuple2::getT2);

        });


    }

    private int getRandomNumber(int min,int max)
    {
        return ThreadLocalRandom.current().nextInt(min,max+1);
    }




}
