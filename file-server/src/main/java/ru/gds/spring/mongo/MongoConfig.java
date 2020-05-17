package ru.gds.spring.mongo;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MongoConfig {

    private static final Logger logger = Logger.getLogger(MongoConfig.class);

    private static final String CHANGELOGS_PACKAGE = "ru.gds.spring.mongo.changelog";

    @Bean
    public Mongock mongock(MongoProps mongoProps, MongoClient mongoClient) {
        try {
            logger.debug("MongoProps FileServer info: dataBase=" + mongoProps.getDatabase()
                    + " uri=" + mongoProps.getUri()
                    + " host=" + mongoProps.getHost()
                    + " port=" + mongoProps.getPort());

            return new SpringMongockBuilder(mongoClient, mongoProps.getDatabase(), CHANGELOGS_PACKAGE)
                    .build();

        } catch (Exception e) {
            logger.error("mongock error: " + Arrays.asList(e.getStackTrace()));
            return null;
        }
    }
}
