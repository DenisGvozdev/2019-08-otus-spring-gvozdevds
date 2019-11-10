package ru.gds.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@ImportResource("classpath:app-config.xml")
public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        SpringApplication.run(Main.class, args);
    }
}