package com.reactive.project;

import com.reactive.project.model.Employee;
import com.reactive.project.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ProjectApplication {

	@Bean
	CommandLineRunner employees(EmployeeRepository employeeRepository)
	{
		return  args -> {
			employeeRepository.deleteAll()
					           .subscribe(null,null,()->
							   {
							   	Stream.of(new Employee("testId","Petar",23000L),
										new Employee(UUID.randomUUID().toString(),"Marko",24000L),
										new Employee(UUID.randomUUID().toString(),"Milan",25000L),
										new Employee(UUID.randomUUID().toString(),"Tamara",26000L),
										new Employee(UUID.randomUUID().toString(),"Nina",28000L))
										.forEach(employee -> {
											employeeRepository.save(employee)
													.subscribe(employee1 -> System.out.println(employee1));
										});
							   });
		};

	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
}
