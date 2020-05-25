package ru.gds.spring.config;

import feign.codec.Encoder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import ru.gds.spring.constant.Constant;
import ru.gds.spring.encode.FeignSpringFormEncoder;

import javax.servlet.MultipartConfigElement;

@Component
@Configuration
@ConfigurationProperties("app")
@ComponentScan(basePackages = "ru.gds.spring")
@EnableFeignClients(basePackages = "ru.gds.spring.microservice")
@ServletComponentScan
public class AppProperties {

    private String fileDirectory;

    // если указать только в конфиге, тогда большие файлы все равно не пропускает
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(Constant.MAX_FILE_SIZE));
        factory.setMaxRequestSize(DataSize.ofBytes(Constant.MAX_FILE_SIZE));
        return factory.createMultipartConfig();
    }

    @Bean
    public Encoder feignSpringFormEncoder() {
        return new FeignSpringFormEncoder();
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }
}
