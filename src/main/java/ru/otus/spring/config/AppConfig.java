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
import ru.otus.spring.service.ConsoleServiceImpl;
import ru.otus.spring.service.FileReaderCSVImpl;
import ru.otus.spring.service.MessageServiceImpl;
import ru.otus.spring.service.StudentTestServiceImpl;

/**
 * Конфигурационный класс
 */
@Configuration
@ComponentScan(basePackages = "ru.otus.spring")
@PropertySource("classpath:application.yml")
@ConfigurationProperties("app")
public class AppConfig {

    private static FileReaderCSVImpl fileReaderCSVer;
    private static MessageServiceImpl messageService;
    private static ConsoleServiceImpl consoleService;

    /**
     * Подключаем бандлы
     *
     * @return MessageSource
     */
    @Bean
    public MessageSource messageSource() {
        //
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    /**
     * Сервис чтения файла questions.csv
     *
     * @param filePath путь к CSV файлу с вопросами
     * @return FileReaderCSVImpl
     */
    @Bean
    FileReaderCSVImpl fileReaderCSVImpl(@Value("${app.filePath}") String filePath) {
        fileReaderCSVer = new FileReaderCSVImpl(filePath);
        return fileReaderCSVer;
    }

    /**
     * Сервис локализации сообщений
     *
     * @param locale текущая локаль из application.properties
     * @return MessageServiceImpl
     */
    @Bean
    MessageServiceImpl messageServiceImpl(@Value("${app.locale}") String locale) {
        messageService = new MessageServiceImpl(locale);
        return messageService;
    }

    /**
     * Сервис работы с консолью
     *
     * @return ConsoleServiceImpl
     */
    @Bean
    ConsoleServiceImpl ConsoleService() {
        consoleService = new ConsoleServiceImpl();
        return consoleService;
    }

    /**
     * Главный сервис запускающий приложение
     */
    @Bean
    @ConditionalOnProperty(name = "app.testMode", matchIfMissing = true, havingValue = "false")
    void StudentTestServiceImpl() {
        StudentTestServiceImpl studentTestServiceImpl = new StudentTestServiceImpl();
        studentTestServiceImpl.testStart();
    }

    public static FileReaderCSVImpl getFileReaderCSVer() {
        return fileReaderCSVer;
    }

    public static MessageServiceImpl getMessageService() {
        return messageService;
    }

    public static ConsoleServiceImpl getConsoleService() {
        return consoleService;
    }
}
