package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.service.*;

@Configuration
@ComponentScan(basePackages = "ru.otus.spring")
@PropertySource("classpath:application.yml")
@ConfigurationProperties("app")
public class AppConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

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
    @ConditionalOnProperty(name = "testMode", matchIfMissing = true, havingValue = "false")
    StudentTestService studentTestService(MessageService ms, ConsoleService cs, FileReader fileReader) {
        StudentTestServiceImpl studentTestServiceImpl = new StudentTestServiceImpl(ms, cs, fileReader);
        studentTestServiceImpl.testStart();
        return studentTestServiceImpl;
    }
}
