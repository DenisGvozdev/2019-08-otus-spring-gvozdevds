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
import ru.gds.spring.jpa.domain.Genre;
import ru.gds.spring.jpa.interfaces.GenreJpaRepository;
import ru.gds.spring.mongo.domain.GenreMongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TaskletImportGenres implements Tasklet, StepExecutionListener {

    private static final Logger logger = Logger.getLogger(TaskletImportGenres.class);

    private final MongoTemplate mongoTemplate;
    private final GenreJpaRepository genreJpaRepository;

    private List<GenreMongo> genreMongoList;
    private List<Genre> genreList;

    private static final int BATCH = 5;

    public TaskletImportGenres(MongoTemplate mongoTemplate, GenreJpaRepository genreJpaRepository) {
        this.mongoTemplate = mongoTemplate;
        this.genreJpaRepository = genreJpaRepository;
        genreMongoList = new ArrayList<>();
        genreList = new ArrayList<>();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("TaskletImportGenres initialized");
        genreList.clear();
        genreMongoList = mongoTemplate.findAll(GenreMongo.class);
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {

            for (GenreMongo genreMongo : genreMongoList)
                genreList.add(new Genre(genreMongo.getName()));

            int counter = 0;
            List<Genre> genreBatchList = new ArrayList<Genre>();
            for (Genre genre : genreList) {
                genreBatchList.add(genre);
                counter += 1;

                if (counter == BATCH || counter == genreList.size()) {
                    genreJpaRepository.saveAll(genreBatchList);
                    genreBatchList.clear();
                    counter = 0;
                }
            }

        } catch (Exception e) {
            logger.error("TaskletImportGenres execute error: " + Arrays.asList(e.getStackTrace()));
            return null;
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("TaskletImportGenres finished");
        return ExitStatus.COMPLETED;
    }
}
