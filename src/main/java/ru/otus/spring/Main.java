package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    /**
     * Для запуска необходимо наличие настройки testMode: false в файле application.yml
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
