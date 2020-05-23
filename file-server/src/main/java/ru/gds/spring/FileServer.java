package ru.gds.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "ru.gds.spring")
public class FileServer {

    public static void main(String[] args) {
        SpringApplication.run(FileServer.class, args);
    }
}
