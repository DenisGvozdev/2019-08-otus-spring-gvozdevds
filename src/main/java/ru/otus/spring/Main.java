package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    /**
     * Запуск приложения осуществляется в StudentTestServiceImpl.testStart()
     * StudentTestServiceImpl создается в AppConfig
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
