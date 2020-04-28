package ru.gds.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import ru.gds.spring.interfaces.Library;
import ru.gds.spring.mongo.dto.BookContentDto;
import ru.gds.spring.utils.Utils;

import java.util.Collection;

@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
public class Main {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class);

        Library library = context.getBean(Library.class);
        Collection<BookContentDto> list = library.process(Utils.generateOrders());
        list.forEach(Utils::printPages);
    }
}
