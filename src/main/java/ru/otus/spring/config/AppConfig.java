package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.*;

@Configuration
@ComponentScan(basePackages = "ru.otus.spring")
@PropertySource("classpath:application.yml")
@ConfigurationProperties("spring")
public class AppConfig {

    @Bean
    FileReader fileReaderCSVImpl(
            @Value("${filePath}") String filePath,
            @Value("${locale}") String locale) {
        return new FileReaderCSVImpl(filePath, locale);
    }

    @Bean
    MessageService messageServiceImpl(
            @Value("${locale}") String locale,
            MessageSource messageSource) {
        return new MessageServiceImpl(locale, messageSource);
    }

    @Bean
    ConsoleService ConsoleService() {
        return new ConsoleServiceImpl();
    }

    @Bean
    StudentTestService studentTestService(MessageService ms, ConsoleService cs, FileReader fr) {
        return new StudentTestServiceImpl(ms, cs, fr);
    }
}
