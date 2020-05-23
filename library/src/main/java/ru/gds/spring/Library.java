package ru.gds.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients(basePackages = "ru.gds.spring.microservice.interfaces")
public class Library {

    public static void main(String[] args) {
        SpringApplication.run(Library.class, args);
    }
}