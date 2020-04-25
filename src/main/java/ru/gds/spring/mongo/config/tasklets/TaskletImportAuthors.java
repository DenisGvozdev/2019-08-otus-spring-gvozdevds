package ru.gds.spring.mongo.config.tasklets;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import ru.gds.spring.jpa.domain.Author;
import ru.gds.spring.jpa.interfaces.AuthorJpaRepository;
import ru.gds.spring.mongo.domain.AuthorMongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TaskletImportAuthors implements Tasklet, StepExecutionListener {

    private static final Logger logger = Logger.getLogger(TaskletImportAuthors.class);

    private final MongoTemplate mongoTemplate;
    private final AuthorJpaRepository authorJpaRepository;

    private List<AuthorMongo> authorMongoList;
    private List<Author> authorList;

    private static final int BATCH = 5;

    public TaskletImportAuthors(MongoTemplate mongoTemplate, AuthorJpaRepository authorJpaRepository) {
        this.mongoTemplate = mongoTemplate;
        this.authorJpaRepository = authorJpaRepository;
        authorMongoList = new ArrayList<>();
        authorList = new ArrayList<>();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("TaskletImportAuthors initialized");
        authorList.clear();
        authorMongoList = mongoTemplate.findAll(AuthorMongo.class);
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {

            for (AuthorMongo authorMongo : authorMongoList) {
                authorList.add(
                        new Author(
                                authorMongo.getFirstName(),
                                authorMongo.getSecondName(),
                                authorMongo.getThirdName(),
                                authorMongo.getBirthDate()));
            }

            int counter = 0;
            List<Author> authorBatchList = new ArrayList<Author>();
            for (Author author : authorList) {
                authorBatchList.add(author);
                counter += 1;

                if (counter == BATCH || counter == authorList.size()) {
                    authorJpaRepository.saveAll(authorBatchList);
                    authorBatchList.clear();
                    counter = 0;
                }
            }

        } catch (Exception e) {
            logger.error("TaskletImportAuthors execute error: " + Arrays.asList(e.getStackTrace()));
            return null;
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("TaskletImportAuthors finished");
        return ExitStatus.COMPLETED;
    }
}