package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.service.ConsoleServiceImpl;
import ru.otus.spring.service.FileReaderCSVImpl;
import ru.otus.spring.service.MessageService;
import ru.otus.spring.service.MessageServiceImpl;

/**
 * Конфигурационный класс
 */
@Configuration
@ComponentScan(basePackages = "ru.otus.spring")
@PropertySource("classpath:application.properties")
public class AppConfig {

    private MessageSource messageSource;
    private MessageService messageService;

    /**
     * Подключаем бандлы
     *
     * @return MessageSource
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:bundle");
        ms.setDefaultEncoding("UTF-8");
        messageSource = ms;
        return ms;
    }

    /**
     * Сервис локализации сообщений
     *
     * @param locale текущая локаль из application.properties
     * @return MessageServiceImpl
     */
    @Bean
    MessageService messageServiceImpl(@Value("${app.locale}") String locale) {
        messageService = new MessageServiceImpl(locale, messageSource);
        return messageService;
    }

    /**
     * Сервис чтения файла questions.csv
     *
     * @param filePath путь к CSV файлу с вопросами
     * @return FileReaderCSVImpl
     */
    @Bean
    FileReaderCSVImpl fileReaderCSVImpl(
            @Value("${app.filePath}") String filePath,
            @Value("${app.locale}") String locale) {
        return new FileReaderCSVImpl(filePath, locale, messageService);
    }

    /**
     * Сервис работы с консолью
     *
     * @return ConsoleServiceImpl
     */
    @Bean
    ConsoleServiceImpl ConsoleService() {
        return new ConsoleServiceImpl();
    }
}
