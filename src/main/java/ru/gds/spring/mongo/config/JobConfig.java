package ru.gds.spring.mongo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.gds.spring.mongo.config.tasklets.*;

import javax.persistence.EntityManagerFactory;

@SuppressWarnings("all")
@EnableBatchProcessing
@Configuration
public class JobConfig {

    private static final int CHUNK_SIZE = 5;
    private static final Logger logger = LoggerFactory.getLogger("JobConfig");

    public static final String OUTPUT_FILE_NAME = "outputFileName";
    public static final String INPUT_FILE_NAME = "inputFileName";
    public static final String IMPORT_USER_JOB_NAME = "importUserJob";

    private EntityManagerFactory emf;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final TaskletTablesClear taskletTablesClear;
    private final TaskletImportAuthors taskletImportAuthors;
    private final TaskletImportGenres taskletImportGenres;
    private final TaskletImportStatuses taskletImportStatuses;
    private final TaskletImportBooks taskletImportBooks;
    private final TaskletImportComments taskletImportComments;

    private final MongoTemplate mongoTemplate;

    JobConfig(
            EntityManagerFactory emf,
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory,
            MongoTemplate mongoTemplate,
            TaskletTablesClear taskletTablesClear,
            TaskletImportAuthors taskletImportAuthors,
            TaskletImportGenres taskletImportGenres,
            TaskletImportStatuses taskletImportStatuses,
            TaskletImportBooks taskletImportBooks,
            TaskletImportComments taskletImportComments) {

        this.emf = emf;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.mongoTemplate = mongoTemplate;
        this.taskletTablesClear = taskletTablesClear;
        this.taskletImportAuthors = taskletImportAuthors;
        this.taskletImportGenres = taskletImportGenres;
        this.taskletImportStatuses = taskletImportStatuses;
        this.taskletImportBooks = taskletImportBooks;
        this.taskletImportComments = taskletImportComments;
    }

    @Bean
    @Qualifier("importDataBseJob")
    public Job job() {
        return jobBuilderFactory
                .get("taskletsJob")
                .start(clearTables())
                .next(stepImportAuthors())
                .next(stepImportGenres())
                .next(stepImportStatuses())
                .next(stepImportBooks())
                .next(stepImportComments())
                .build();
    }

    @Bean
    protected Step clearTables() {
        return stepBuilderFactory
                .get("clearTables")
                .tasklet(taskletTablesClear)
                .build();
    }

    @Bean
    protected Step stepImportAuthors() {
        return stepBuilderFactory
                .get("importAuthors")
                .tasklet(taskletImportAuthors)
                .build();
    }

    @Bean
    protected Step stepImportGenres() {
        return stepBuilderFactory
                .get("importGenres")
                .tasklet(taskletImportGenres)
                .build();
    }

    @Bean
    protected Step stepImportStatuses() {
        return stepBuilderFactory
                .get("importStatuses")
                .tasklet(taskletImportStatuses)
                .build();
    }

    @Bean
    protected Step stepImportBooks() {
        return stepBuilderFactory
                .get("importBooks")
                .tasklet(taskletImportBooks)
                .build();
    }

    @Bean
    protected Step stepImportComments() {
        return stepBuilderFactory
                .get("importComments")
                .tasklet(taskletImportComments)
                .build();
    }
}