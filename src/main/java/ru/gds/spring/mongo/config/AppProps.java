package ru.gds.spring.mongo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppProps {

    @Value("${spring.data.mongodb.database}")
    private String mongoDBName;

    @Value("${app.input-file:''}")
    private String inputFileName;

    @Value("${app.output-file:''}")
    private String outputFileName;

}
