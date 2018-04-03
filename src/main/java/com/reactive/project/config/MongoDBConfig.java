package com.reactive.project.config;

import com.reactive.project.repository.EmployeeRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = EmployeeRepository.class)
@Configuration
public class MongoDBConfig {
}
