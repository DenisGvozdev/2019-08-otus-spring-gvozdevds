package ru.gds.spring.mongo;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ru.gds.spring.mongo.changelog.DatabaseChangelog;

@Configuration
public class MongoConfig {

    private MongoClient mongo;

    MongoConfig(MongoClient mongo){
        this.mongo = mongo;
    }

    @Bean
    public Mongobee mongobee(Environment environment) {
        Mongobee runner = new Mongobee(mongo);
        runner.setDbName("mydb");
        runner.setChangeLogsScanPackage(DatabaseChangelog.class.getPackage().getName());
        runner.setSpringEnvironment(environment);
        return runner;
    }
}
