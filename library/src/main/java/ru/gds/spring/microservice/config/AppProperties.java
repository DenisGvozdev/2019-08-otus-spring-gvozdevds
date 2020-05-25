package ru.gds.spring.microservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import ru.gds.spring.microservice.constant.Constant;

import javax.servlet.MultipartConfigElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@Configuration
@ConfigurationProperties("app")
@ComponentScan(basePackages = "ru.gds.spring")
@ServletComponentScan
public class AppProperties {

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    private String fileServerUrl;
    private String fileDirectory;

    AppProperties(ObjectFactory<HttpMessageConverters> messageConverters){
        this.messageConverters = messageConverters;
    }

    // если указать только в конфиге, тогда большие файлы все равно не пропускает
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(Constant.MAX_FILE_SIZE));
        factory.setMaxRequestSize(DataSize.ofBytes(Constant.MAX_FILE_SIZE));
        return factory.createMultipartConfig();
    }

    @Bean
    public Encoder feignEncoder () {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:ss");
        objectMapper.setDateFormat(smt);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON );
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        return mappingJackson2HttpMessageConverter;
    }

    public String getFileServerUrl() {
        return fileServerUrl;
    }

    public void setFileServerUrl(String fileServerUrl) {
        this.fileServerUrl = fileServerUrl;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }
}
